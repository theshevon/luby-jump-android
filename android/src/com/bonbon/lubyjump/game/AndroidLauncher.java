package com.bonbon.lubyjump.game;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.MobileAds;

public class AndroidLauncher extends AndroidApplication{

	//These are test unit ids
	private static final String AD_UNIT_ID = "ca-app-pub-4429211222323819~8604982674";
	//private static final String BANNER_ID = "ca-app-pub-4429211222323819/5878746987";
	//private static final String INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712";

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MobileAds.initialize(this, AD_UNIT_ID);

		//Parent view
		RelativeLayout layout = new RelativeLayout(this);

		//Make game view
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		View gameView = initializeForView(new Game());

		//Make banner ad view
//		AdView bannerAdView = new AdView(this);
//		bannerAdView.setAdSize(AdSize.BANNER);
//		bannerAdView.setAdUnitId(BANNER_ID);
//		AdRequest bannerAdRequest = new AdRequest.Builder().build();
//		bannerAdView.loadAd(bannerAdRequest);
//		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//		adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//
		layout.addView(gameView);
//		layout.addView(bannerAdView, adParams);

		setContentView(layout);

//		//Load interstitial ad
//		interstitialAd = new InterstitialAd(this);
//		interstitialAd.setAdUnitId(INTERSTITIAL_ID);
//		interstitialAd.setAdListener(new AdListener() {
//			@Override
//			public void onAdLoaded() {}
//
//			@Override
//			public void onAdClosed() {
//				loadIntersitialAd();
//			}
//		});
//
//		loadIntersitialAd();
	}

}


