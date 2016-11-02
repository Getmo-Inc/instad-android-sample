package br.com.getmo.instad.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TreeSet;

import br.com.getmo.ads.Ad;
import br.com.getmo.ads.AdListener;
import br.com.getmo.ads.AdNative;

/**
 * Created by fabio.licks on 15/10/16.
 *
 * RecyclerView with multiple view
 * http://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-type
 *
 * ListView with multiple view
 * http://android.amberfog.com/?p=296
 * 
 * http://karnshah8890.blogspot.com.br/2013/04/listview-animation-tutorial.html
 */

public class ListWithNativeAdsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.list_with_nativeads, container, false );
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ListView list = ( ListView )view.findViewById( R.id.list );

        MyCustomAdapter mAdapter = new MyCustomAdapter( );
        for ( int i = 1; i < 50; i++ ) {
            Log.d( "instad", "populate!" );
            mAdapter.addItem( "Item " + i );
            if ( i % 8 == 0 ) {
                Log.d( "instad", "ad!" );
                mAdapter.addAdItem( new Ad() );
            }
        }

        list.setAdapter( mAdapter );
        list.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getAdapter().getItem( position );
                Toast.makeText( getContext(), item.toString(), Toast.LENGTH_SHORT ).show();
            }
        });
    }

    private class MyCustomAdapter extends BaseAdapter {

        private static final int TYPE_ITEM = 0;
        private static final int TYPE_AD_ITEM = 1;
        private static final int TYPE_MAX_COUNT = TYPE_AD_ITEM + 1;

        private TreeSet mAdsSet = new TreeSet();
        private ArrayList mData = new ArrayList();

        public void addItem( final String item ) {
            mData.add( item );
            notifyDataSetChanged();
        }

        public void addAdItem( final Ad item ) {
            mData.add( item );
            // save AD position
            mAdsSet.add( mData.size() - 1 );
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType( int position ) {
            return mAdsSet.contains(position) ? TYPE_AD_ITEM : TYPE_ITEM;
        }

        @Override
        public int getViewTypeCount() {
            return TYPE_MAX_COUNT;
        }

        @Override
        public int getCount() {
            return ( mData != null ) ? mData.size() :0;
        }

        @Override
        public Object getItem( int position ) {
            return mData.get( position );
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView( final int position, View convertView, ViewGroup parent ) {
            int type = getItemViewType( position );

            Log.d( "instad", "getView " + position + ", " + convertView + ", type = " + type );

            if ( convertView == null ) {
                switch ( type ) {
                    case TYPE_ITEM:
                        convertView =
                                LayoutInflater.from( getContext() )
                                        .inflate( android.R.layout.simple_list_item_1, null );
                        break;
                    case TYPE_AD_ITEM:
                        convertView =
                                new AdNative( getContext() )
                                        .setType( AdNative.NativeType.BIG );
                        break;
                }
            }

            switch ( type ) {
                case TYPE_ITEM:
                    ((TextView)convertView).setText( ( String )getItem( position ) );
                    break;
                case TYPE_AD_ITEM:
                    AdNative ad = ( AdNative )convertView;
                    ad.load( new AdListener() {
                        @Override
                        public void onAdLoaded( Ad ad ) {
                            super.onAdLoaded( ad );
                            mData.set( position, ad );
                        }
                    });

//                    if ( ( ( Ad )mData.get( position )).getId() == null ) {
//                        Log.d( "instad", "loading AD" );
//                        ad.load( new AdListener() {
//                            @Override
//                            public void onAdLoaded( Ad ad ) {
//                                super.onAdLoaded( ad );
//                                mData.set( position, ad );
//                            }
//                        });
//                    }

                    break;
            }

            return convertView;
        }
    }
}