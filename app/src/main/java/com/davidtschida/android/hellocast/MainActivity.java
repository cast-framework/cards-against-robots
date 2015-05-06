package com.davidtschida.android.hellocast;

import android.app.Activity;
import android.content.Context;
import android.media.MediaRouter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.davidtschida.android.hellocast.utils.Utils;
import com.google.sample.castcompanionlibrary.cast.DataCastManager;
import com.google.sample.castcompanionlibrary.cast.VideoCastManager;
import com.google.sample.castcompanionlibrary.cast.callbacks.DataCastConsumerImpl;
import com.google.sample.castcompanionlibrary.cast.callbacks.IDataCastConsumer;
import com.google.sample.castcompanionlibrary.cast.callbacks.IVideoCastConsumer;
import com.google.sample.castcompanionlibrary.cast.callbacks.VideoCastConsumerImpl;
import com.google.sample.castcompanionlibrary.widgets.MiniController;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "VideoBrowserActivity";
    private DataCastManager mCastManager;
    private DataCastConsumerImpl mCastConsumer;
    private MiniController mMini;
    private MenuItem mediaRouteMenuItem;
    boolean mIsHoneyCombOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VideoCastManager.checkGooglePlayServices(this);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();

        mCastManager = CastApplication.getCastManager(this);


        mCastConsumer = new DataCastConsumerImpl() {

            @Override
            public void onFailed(int resourceId, int statusCode) {

            }

            @Override
            public void onConnectionSuspended(int cause) {
                Log.d(TAG, "onConnectionSuspended() was called with cause: " + cause);
                Utils.
                        showToast(MainActivity.this, "Connection was list temporarily");
            }

            @Override
            public void onConnectivityRecovered() {
                Utils.showToast(MainActivity.this, "Connection recovered!");
            }

            @Override
            public void onCastDeviceDetected(final android.support.v7.media.MediaRouter.RouteInfo info) {
                 Log.d(TAG, "Route is visible: " + info);
            }
        };

        setupActionBar(actionBar);
        mCastManager.reconnectSessionIfPossible(this, false);
        mCastManager.addDataCastConsumer(mCastConsumer);
    }


    private void setupActionBar(ActionBar actionBar) {
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);

        mediaRouteMenuItem = mCastManager.
                addMediaRouterButton(menu, R.id.media_route_menu_item);

        return true;
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume() was called");
        mCastManager = CastApplication.getCastManager(this);
        if (null != mCastManager) {
            mCastManager.addDataCastConsumer(mCastConsumer);
            mCastManager.incrementUiCounter();
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        mCastManager.decrementUiCounter();
        mCastManager.removeDataCastConsumer(mCastConsumer);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy is called");
        if (null != mCastManager) {
            mCastManager.clearContext(this);
        }
        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
