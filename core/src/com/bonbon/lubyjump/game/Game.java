package com.bonbon.lubyjump.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

/** THINGS TO DO:
 *  Interstitial Ads (next build)
 *  make explosion animation for player and poop (next build)
 *  add google play features (next build)
 */

/**
 * THINGS DONE:
 * fixed font alignment
 * banner ads
 * add load screen
 * draw pause button
 * change font and size for score
 * Menu Screen
 * Music
 * Post Score
 * Running animation
 * Save/Retrieve HS
 * Restart Menu
 * change walls depending on score
 * added bird to screen
 */

public class Game extends ApplicationAdapter {

	public static final float WALL_RESPAWN_TIME = 2700;
	public static final float GROUND_VELOCITY = 0.3f;
	public static final int NO_WALL_TYPES = 5;
	public static final int LEVEL_JUMP_SCORE = 15;
	public static final float HOME_SCREEN_JUMP_VEL = -20;

	private float GROUND_HEIGHT = 250;
	private float INIT_PLAYER_Y = 308;
	private float WALL_GAP = 400f;

	public SpriteBatch batch;
	public Player player;
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<Bird> birds = new ArrayList<Bird>();

	public ScaleFactor factor;
	private Preferences prefs;
	private GlyphLayout layout;
	private Texture logo, background, ground, grass, info;
	private Music themeSong, gamePlaySong;
	private Sound jumpSound, fartSound, gameOverSound;
	private BitmapFont scoreFont, highscoreFont, tapFont, gameOverFont;
	private Trophy trophy;

	private Random random = new Random();
	private Button playButton, pauseButton;
	private ToggleButton muteButton;

	private int level = 1;
	private int score = 0;
	private int highscore = 0;
	private float timeSinceSpawningWall = 0;
	private float groundXOffset = 0;

	private boolean gameStarted = false;
	private boolean firstTouchMade = false;
	private boolean gameOver = false;
	private boolean paused = false;
	private boolean muted = false;
	private boolean newHS = false;

	private float scoreLblX, scoreLblY, HScoreLblX, HScoreLblY, tapLblX, tapLblY, pauseLblX;
	private float pauseLblY, overLblX, overLblY, infoY, logoY, scoreNodeX, scoreNodeY, trophyY;
	private float logoWidth, logoHeight, bgWidth, bgHeight, infoWidth, infoHeight, groundWidth;
	private float groundHeight, grassWidth, grassHeight;
	private float soundVolume = 0.5f;

	@Override
	public void create () {

		factor = new ScaleFactor();
		GROUND_HEIGHT *= factor.y;
		INIT_PLAYER_Y *= factor.y;

		batch = new SpriteBatch();
		player = new Player(Gdx.graphics.getWidth()/2, INIT_PLAYER_Y, factor);
		loadImages();
		loadSounds();
		loadFonts();
		createButtons();
		loadHighscore();
		setPositions();
	}

	private void loadImages(){

		try {
			logo = new Texture(Gdx.files.internal("logo.png"));
			logoWidth = logo.getWidth()*factor.x;
			logoHeight = logo.getHeight()*factor.y;

			background = new Texture(Gdx.files.internal("bg.png"));
			bgWidth = background.getWidth()*factor.x;
			bgHeight = background.getHeight()*factor.y;

			grass = new Texture(Gdx.files.internal("grass.png"));
			grassWidth = grass.getWidth()*factor.x;
			grassHeight = grass.getHeight()*factor.y;

			ground = new Texture(Gdx.files.internal("ground.png"));
			groundWidth = ground.getWidth()*factor.x;
			groundHeight = ground.getHeight()*factor.y;

			info = new Texture(Gdx.files.internal("info.png"));
			infoWidth = info.getWidth()*factor.x;
			infoHeight = info.getHeight()*factor.y;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void loadSounds() {
		try {
			themeSong = Gdx.audio.newMusic(Gdx.files.internal("Theme.mp3"));
			gamePlaySong = Gdx.audio.newMusic(Gdx.files.internal("Gameplay.mp3"));
			jumpSound = Gdx.audio.newSound(Gdx.files.internal("Jump.mp3"));
			fartSound = Gdx.audio.newSound(Gdx.files.internal("Fart.mp3"));
			gameOverSound = Gdx.audio.newSound(Gdx.files.internal("GameOver.mp3"));
		}catch(Exception e){
			e.printStackTrace();
		}
		initialiseSounds();
	}

	private void initialiseSounds(){
		themeSong.setLooping(true);
		themeSong.setVolume(soundVolume);
		themeSong.play();
		gamePlaySong.setVolume(soundVolume);
	}

	private void loadFonts(){
		try{
			scoreFont = new BitmapFont(Gdx.files.internal("score-font.fnt"));
			scoreFont.getData().setScale(factor.x*1.2f, factor.y);

			highscoreFont = new BitmapFont(Gdx.files.internal("highscoreFont.fnt"));
			highscoreFont.getData().setScale(factor.x, factor.y);

			tapFont = new BitmapFont(Gdx.files.internal("score-font.fnt"));
			tapFont.getData().setScale(factor.x, factor.y);

			gameOverFont = new BitmapFont(Gdx.files.internal("gameOverFont.fnt"));
			gameOverFont.getData().setScale(1.5f*factor.x, 1.5f*factor.y);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setPositions(){

		float centreX = Gdx.graphics.getWidth()/2;
		float centreY = Gdx.graphics.getHeight()/2;

		scoreNodeY = centreY*2-200;

		layout = new GlyphLayout(tapFont, "TAP TO BEGIN");
		tapLblX = centreX - layout.width/2;
		tapLblY = centreY;

		layout = new GlyphLayout(tapFont, "GAME PAUSED");
		pauseLblX = centreX - layout.width/2;
		pauseLblY = centreY + centreY/4;

		infoY = centreY;

		layout = new GlyphLayout(gameOverFont, "GAME OVER");
		overLblX = centreX - layout.width/2;
		overLblY = infoY + infoHeight+100*factor.y;

		trophyY = infoY + 3*infoHeight/5.0f;
		trophy = new Trophy(factor, trophyY);

		logoY = centreY*2 - 200*factor.y - logoHeight;
	}

	private void createButtons(){
		playButton = new Button("play_button.png",Gdx.graphics.getWidth()/2,
				Gdx.graphics.getHeight()/2.5f, factor);
		muteButton = new ToggleButton("unmute.png", "mute.png",
				Gdx.graphics.getWidth()-80*factor.x, Gdx.graphics.getHeight()
				-80*factor.y, factor);
		pauseButton = new Button("pause.png", 80*factor.x,
				Gdx.graphics.getHeight()-80*factor.y, factor);
	}

	private void loadHighscore(){
		prefs = Gdx.app.getPreferences("game preferences");
		highscore = prefs.getInteger("highscore");
	}

	private void update(){

		if (Gdx.input.justTouched()) {
			handleTouches();
		}

		float delta = Gdx.graphics.getDeltaTime() * 1000;

		if (gameStarted) {

			if (!paused) {

				groundXOffset += delta * GROUND_VELOCITY;
				if (groundXOffset >= groundWidth) {
					groundXOffset = 0;
				}

				for (int i = 0; i < walls.size(); i++) {
					walls.get(i).update(delta);
					if (walls.get(i).hasLeftScreen()) {
						walls.get(i).dispose();
						walls.remove(i);
					}
				}

				for (int i = 0; i < birds.size(); i++){
					birds.get(i).update(delta);
					if (birds.get(i).hasLeftScreen()){
						birds.get(i).dispose();
						birds.remove(i);
					}
				}

				timeSinceSpawningWall += delta;
				if (timeSinceSpawningWall >= WALL_RESPAWN_TIME) {
					timeSinceSpawningWall = 0;
					spawnWall();
				}

				if (!gameOver) {
					player.update(delta);
					doCollisionTestsAndHandling();
				}
			}
		}else {

			if (player.getY() == INIT_PLAYER_Y) {
				player.jump(HOME_SCREEN_JUMP_VEL);
			}
			player.update(delta);
		}
	}

	private void handleTouches(){

		float x = Gdx.input.getX(), y = Gdx.input.getY();

		if (firstTouchMade){
			gameStarted = true;
			firstTouchMade = false;
			gamePlaySong.setLooping(true);
			gamePlaySong.play();
			return;
		}

		if (paused && playButton.isPressed(x, y)){
			paused = false;
			gamePlaySong.play();
		}

		if (!gameStarted && playButton.isPressed(x, y)) {
			firstTouchMade = true;
			player.setX(player.getX() - 100);
			themeSong.stop();
			return;
		}

		if (muteButton.isPressed(x,y)) {

			if (soundVolume == 0){
				soundVolume = 0.5f;
			}else{
				soundVolume = 0;
				gameOverSound.stop();
			}

			themeSong.setVolume(soundVolume);
			gamePlaySong.setVolume(soundVolume);

			muted = !muted;
			return;
		}

		if (pauseButton.isPressed(x,y)) {
			gamePlaySong.pause();
			paused = true;
			return;
		}

		if (gameOver && playButton.isPressed(x,y)) {
			restartGame();
			return;
		}

		if ((gameStarted && !paused && !gameOver) || (!gameStarted)){
			player.jump();
			if (gameStarted) {
				jumpSound.play(soundVolume);
			}
		}
	}


	private void spawnWall(){
		float centreY = random.nextInt(Gdx.graphics.getHeight()-(int)GROUND_HEIGHT -
				2*(int)WALL_GAP) + 2*GROUND_HEIGHT;

		int wallType = random.nextInt(100)%level;

		switch (wallType){
			case 1:
				walls.add(new UpDownWall(centreY, factor));
				break;
			case 2:
				walls.add(new InOutWall(centreY,
						random.nextFloat()*InOutWall.MAX_VEL_CHANGE +
								InOutWall.MIN_IN_OUT_VELOCTIY, factor));
				break;
			case 3:
				walls.add(new NWall(centreY, factor));
				break;
			case 4:
				walls.add(new UWall(centreY, factor));
				break;
			default:
				walls.add(new Wall(centreY, factor));
				break;
		}

		if (level==NO_WALL_TYPES && score%3==0 && random.nextInt(1000)%17==0){
			birds.add(new Bird(walls.get(walls.size()-1).getCentreX(), factor));
			fartSound.play(soundVolume);
		}
	}

	private void doCollisionTestsAndHandling(){

		for (Wall wall: walls){

			if (wall.collidedWithScoreNode(player)){
				score += 1;
				if (score % LEVEL_JUMP_SCORE == 0 && level <= NO_WALL_TYPES) {
					level += 1;
				}
			}

			if (wall.collidedWithWall(player)){
				setGameOver();
			}
		}

		for (Bird bird: birds){
			if (bird.collidedWithPoop(player)){
				setGameOver();
			}
		}
	}

	private void setGameOver(){
		gameOver = true;
		gameOverSound.play(soundVolume);

		if (score > highscore){
			newHS = true;
			highscore = score;
			prefs.putInteger("highscore", highscore);
			prefs.flush();
		}

		gamePlaySong.stop();

		try {
			background = new Texture(Gdx.files.internal("fail_bg.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void restartGame(){
		gameOverSound.stop();
		birds.clear();
		walls.clear();
		gameOver = false;
		newHS = false;
		score = 0;
		level = 1;
		timeSinceSpawningWall = 0;

		try{
			background = new Texture(Gdx.files.internal("bg.png"));
		}catch(Exception e){
			e.printStackTrace();
		}

		gamePlaySong.setLooping(true);
		gamePlaySong.play();
	}


	@Override
	public void render () {

		update();

		batch.begin();

		batch.draw(background, 0, 0, bgWidth, bgHeight);

		if (!gameStarted && !firstTouchMade){
			playButton.render(batch);
			batch.draw(logo, Gdx.graphics.getWidth()/2 - logoWidth/2, logoY, logoWidth,
					logoHeight);
		}

		for (int i=0; i<2; i++){
			batch.draw(grass,100-groundXOffset+i*groundWidth, GROUND_HEIGHT, grassWidth,
					grassHeight);
		}

		if (gameStarted) {

			for (Wall wall: walls){
				wall.render(batch);
			}

			for (Bird bird: birds){
				bird.render(batch);
			}

			if (gameOver){
				displayRestartMenu();
			}else{
				if (!paused) {
					pauseButton.render(batch);
				}
				layout = new GlyphLayout(tapFont, Integer.toString(score));
				scoreNodeX = Gdx.graphics.getWidth() / 2 - layout.width / 2;
				scoreFont.draw(batch, Integer.toString(score), scoreNodeX, scoreNodeY);
			}
		}

		for (int i=0; i<2; i++){
			batch.draw(ground, 0-groundXOffset+i*groundWidth,
					GROUND_HEIGHT - groundHeight, groundWidth, groundHeight);
		}

		if (paused){
			layout = new GlyphLayout(tapFont, "GAME PAUSED");
			tapFont.draw(batch,"GAME PAUSED", pauseLblX,pauseLblY);
			playButton.render(batch);
		}

		if (firstTouchMade){
			layout = new GlyphLayout(tapFont, "TAP TO BEGIN");
			tapFont.draw(batch, "TAP TO BEGIN", tapLblX, tapLblY);
		}

		muteButton.render(batch);

		player.render(batch);

		batch.end();
	}

	private void displayRestartMenu(){
		batch.draw(info, Gdx.graphics.getWidth()/2-infoWidth/2, infoY, infoWidth, infoHeight);
		gameOverFont.draw(batch,"GAME OVER", overLblX, overLblY);

		layout = new GlyphLayout(scoreFont, String.format("Score%5d", score));
		scoreLblX = Gdx.graphics.getWidth()/2 - layout.width/2;
		scoreLblY = trophyY - layout.height/2 - 30*factor.y;
		scoreFont.draw(batch, String.format("Score%5d", score), scoreLblX, scoreLblY);

		layout = new GlyphLayout(highscoreFont, String.format("Highscore%5d", highscore));
		HScoreLblX = Gdx.graphics.getWidth()/2 - layout.width/2;
		HScoreLblY = scoreLblY - 100*factor.y - layout.height/2;
		highscoreFont.draw(batch, String.format("Highscore%5d", highscore), HScoreLblX,
				HScoreLblY);

		trophy.render(batch, newHS);
		playButton.render(batch);
	}
	
	@Override
	public void dispose () {
		super.dispose();
		themeSong.dispose();
		gamePlaySong.dispose();
		fartSound.dispose();
		jumpSound.dispose();
		gameOverSound.dispose();
	}
}


