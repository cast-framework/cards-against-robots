package com.davidtschida.android.hellocast;

import android.app.Application;
import android.content.Context;

import com.google.sample.castcompanionlibrary.cast.DataCastManager;
import com.google.sample.castcompanionlibrary.cast.VideoCastManager;
import com.google.sample.castcompanionlibrary.utils.Utils;

/**
 * The {@link Application} for this demo application.
 */
public class CastApplication extends Application {

    public static final String APP_DESTRUCTION_KEY = "application_destruction";
    public static final String FTU_SHOWN_KEY = "ftu_shown";
    public static final String VOLUME_SELCTION_KEY = "volume_target";
    public static final String TERMINATION_POLICY_KEY = "termination_policy";
    public static final String STOP_ON_DISCONNECT = "1";
    public static final String CONTINUE_ON_DISCONNECT = "0";


    private static String APPLICATION_ID;
    private static DataCastManager mCastMgr = null;
    public static final double VOLUME_INCREMENT = 0.05;
    private static Context mAppContext;

    /*
     * (non-Javadoc)
     * @see android.app.Application#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        APPLICATION_ID = "4860FC59";
        Utils.saveFloatToPreference(getApplicationContext(),
                VideoCastManager.PREFS_KEY_VOLUME_INCREMENT, (float) VOLUME_INCREMENT);

    }

    public static DataCastManager getCastManager(Context context) {
        if (null == mCastMgr) {
            mCastMgr = DataCastManager.initialize(context, APPLICATION_ID,
                    null, null);
            mCastMgr.enableFeatures(
                    DataCastManager.FEATURE_NOTIFICATION |
                            DataCastManager.FEATURE_LOCKSCREEN |
                            DataCastManager.FEATURE_DEBUGGING);

        }
        mCastMgr.setContext(context);
        String destroyOnExitStr = Utils.getStringFromPreference(context,
                TERMINATION_POLICY_KEY);
        mCastMgr.setStopOnDisconnect(null != destroyOnExitStr
                && STOP_ON_DISCONNECT.equals(destroyOnExitStr));
        return mCastMgr;
    }

}