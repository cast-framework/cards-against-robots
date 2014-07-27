package com.davidtschida.android.cards.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidtschida.android.cards.R;
import com.davidtschida.android.cast.framework.OnCastConnectedListener;
import com.davidtschida.android.cast.framework.OnMessageReceivedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by david on 7/27/14.
 */
public class WelcomeFragment extends CastFragment implements OnMessageReceivedListener, OnCastConnectedListener {


    public WelcomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.welcome, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onMessageRecieved(JSONObject json) {
        //Recieve ready with cards.

        String command = null;
        try {
            command = json.getString("command");

            if(command.equals("cards")) {
                JSONArray cards = json.getJSONArray("content");

                ArrayList<String> cardNames = new ArrayList<String>();

                for (int i = 0; i < cards.length(); i++) {
                    JSONObject card = cards.getJSONObject(i);
                    String name = card.getString("name");

                    cardNames.add(name);
                }

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, WaitingForPlayersFragment.newInstance(cardNames)).commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCastConnected() {
        JSONObject json = new JSONObject();
        try {
            json.put("command", "join");
            host.getCastmanager().setOnMessageRecievedListener(this);
            host.getCastmanager().sendMessage(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
