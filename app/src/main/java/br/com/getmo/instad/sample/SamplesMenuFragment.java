package br.com.getmo.instad.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.getmo.ads.AdNotification;
import br.com.getmo.ads.AdView;

/**
 * Created by fabio.licks on 15/10/16.
 */

public class SamplesMenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.samples, container, false );
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // SHOW INTERSTITIAL SAMPLE
        ((Button)view.findViewById( R.id.btn_ad_interstitital )).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity( new Intent( getActivity(), InterstitialAdsActivity.class ) );
            }
        });

        // Request a NOTIFICATION AD SAMPLE
        ((Button)view.findViewById( R.id.btn_ad_notification )).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AdNotification( getActivity() ).load( );
            }
        });

        // BANNER AD
        ( ( AdView )view.findViewById( R.id.banner ) ).load();

        // NATIVE
        ((Button)view.findViewById( R.id.btn_ad_native )).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add/Replace the fragment to the 'fragment_container' FrameLayout
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out );
                transaction.replace( R.id.content, new ListWithNativeAdsFragment() );
                transaction.addToBackStack( "list" );
                transaction.commit();
            }
        });
    }
}