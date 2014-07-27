package com.davidtschida.android.cards.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.davidtschida.android.cards.CastmanagerHost;
import com.davidtschida.android.cast.framework.CastManager;

/**
 * Created by david on 7/27/14.
 */
public class CastFragment extends Fragment {

    CastmanagerHost host;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof CastmanagerHost) {
            host = (CastmanagerHost) activity;
        } else throw new ClassCastException("Activity must implement CastmanagerHost!");
    }

    protected CastmanagerHost getCastManagerHost() {
        return host;
    }
}
