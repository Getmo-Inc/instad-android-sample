package br.com.getmo.instad.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.getmo.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main );

        // Initialize Mobile Ads SDK as early you can.
        MobileAds.getInstance(this)
                .addDevelopmentDevices( "2dad4943-a741-43df-8539-518d7d91dfbf" )
                .setAppKey( "43b0ce3c3e5455324265bc43eec333" )
                .setLogEnalbled( true )
                .init();

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        if ( findViewById( R.id.content ) != null ) {
            if ( savedInstanceState != null ) {
                return;
            }

            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            transaction.add( R.id.content, new SamplesMenuFragment() );
            transaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Mobile Ads SDK
        MobileAds.getInstance(this).finish();
    }
}