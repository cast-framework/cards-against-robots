package com.davidtschida.android.cards.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.davidtschida.android.cards.CardItem;
import com.davidtschida.android.cards.CastmanagerHost;
import com.davidtschida.android.cards.R;
import com.davidtschida.android.cast.framework.OnMessageReceivedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by david on 7/27/14.
 */
public class CzarFragment extends CastFragment implements OnMessageReceivedListener {

    private String title;

    public CzarFragment() {
    }

    public static CzarFragment newInstance(CardItem card) {
        Bundle b = new Bundle();
        b.putString("card", card.getTitle());
        CzarFragment f = new CzarFragment();
        f.setArguments(b);
        return f;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        title = getArguments().getString("card", "ERROR!");

        host.getCastmanager().setOnMessageRecievedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.czarlayout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView) view.findViewById(R.id.textView)).setText(title);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout back = (FrameLayout) v.findViewById(R.id.back);
                FrameLayout front = (FrameLayout) v.findViewById(R.id.front);

                back.setVisibility(View.GONE);
                front.setVisibility(View.VISIBLE);


                host.getCastmanager().setOnMessageRecievedListener(CzarFragment.this);
                JSONObject json = new JSONObject();

                try {
                    json.put("command", "czarFlip");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                host.getCastmanager().sendMessage(json);
            }
        });
    }

    @Override
    public void onMessageRecieved(JSONObject json) {

        try {
            String command = json.getString("command");

            if(command.equals("czar")) {
                JSONArray cards = json.getJSONArray("content");

                ArrayList<String> cardNames = new ArrayList<String>();

                for(int i = 0; i < cards.length(); i++) {
                    JSONObject card = cards.getJSONObject(i);
                    String name = card.getString("name");

                    cardNames.add(name);
                }

                String black =  cardNames.get(0);

                cardNames.remove(0);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, CardListFragment.newInstance("czar", "Choose a winner\n" + black, cardNames)).commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
