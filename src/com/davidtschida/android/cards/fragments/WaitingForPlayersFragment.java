package com.davidtschida.android.cards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidtschida.android.cards.CardItem;
import com.davidtschida.android.cards.R;
import com.davidtschida.android.cast.framework.OnMessageReceivedListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by david on 7/27/14.
 */
public class WaitingForPlayersFragment extends CastFragment implements OnMessageReceivedListener {

    enum Responses {
        READY, CZAR_SELECTION
    }

    Responses mWaitingOn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.waiting_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        host.getCastmanager().setOnMessageRecievedListener(this);


        JSONObject json = new JSONObject();
        try {
            json.put("command", "join");
            mWaitingOn = Responses.READY;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageRecieved(JSONObject json) {

        switch(mWaitingOn) {
            case READY:
                //All players have joined!
                TextView text = (TextView) getView().findViewById(R.id.textView);
                text.setText("Selecting Czar");
                mWaitingOn = Responses.CZAR_SELECTION;

                break;
            case CZAR_SELECTION:
                try {
                    String s = json.getString("command");
                    if(!s.equals("czar")) {
                        return;
                    }

                    JSONObject content = json.getJSONObject("content");

                    if(content.getBoolean("val")) {
                        //Is the czar

                        String card = content.getJSONObject("card").getString("name");
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, CzarFragment.newInstance(new CardItem(card))).commit();
                    } else {
                        //not czar
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, new CardListFragment()).commit();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
