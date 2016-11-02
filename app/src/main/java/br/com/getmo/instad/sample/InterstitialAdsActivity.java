package br.com.getmo.instad.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import br.com.getmo.ads.Ad;
import br.com.getmo.ads.AdInterstitial;
import br.com.getmo.ads.AdListener;
import br.com.getmo.ads.AdRequest;

public class InterstitialAdsActivity extends AppCompatActivity {

    AdInterstitial mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.interstitialads);

        mInterstitialAd = new AdInterstitial();
        mInterstitialAd.load( this, new AdRequest.Builder().build(), new AdListener() {
            @Override
            public void onAdLoaded(Ad ad) {
                Log.d( "instad", "onAdLoaded" );
                Log.d( "instad", ( ad != null ) ? ad.toString() : "{}" );

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // do something else...
                        ((TextView)findViewById( R.id.msg ))
                                .setText( "Interstitial Ads was loaded!\nClick to close!" );
                    }
                } );
            }

            @Override
            public void onAdClosed( Ad ad ) {
                Log.d( "instad", "onAdClosed" );

                // do something else...
                finish();
            }
        } );
    }

    public void close(View v) {
        if ( mInterstitialAd.isLoaded() ) {
            mInterstitialAd.show( this );
        } else {
            finish();
        }
    }
}