package com.davidtschida.android.cards.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.davidtschida.android.cards.CARCard;
import com.davidtschida.android.cards.R;
import com.davidtschida.android.cast.framework.OnMessageReceivedListener;
import com.fima.cardsui.objects.Card;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by david on 7/27/14.
 */
public class CardListFragment extends CastFragment implements OnMessageReceivedListener {


    public static CardListFragment newInstance(String rel, String command, List<String> cards) {
        Bundle b = new Bundle();
        b.putStringArray("cards", cards.toArray(new String[]{}));
        b.putString("command", command);
        b.putString("rel", rel);
        CardListFragment frag = new CardListFragment();
        frag.setArguments(b);
        return frag;
    }


    String[] cards;
    String command;
    String rel;

    public CardListFragment() {

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private CardUI mCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.cardlistview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        cards = getArguments().getStringArray("cards");
        command = getArguments().getString("command");
        rel = getArguments().getString("rel");


        ((TextView)view.findViewById(R.id.title)).setText(command);


        // init CardView
        mCardView = (CardUI) view.findViewById(R.id.cardsview);
        mCardView.setSwipeable(true);

        CardStack hand = new CardStack();

        mCardView.addStack(hand);


        Card.OnCardSwiped onCardSwiped = new Card.OnCardSwiped() {

            @Override
            public void onCardSwiped(Card card, View layout) {
                JSONObject json = new JSONObject();
                try {
                    json.put("command", rel+"_pick");
                    json.put("content", card.getData().toString());
                    host.getCastmanager().sendMessage(json);

                    mCardView.setSwipeable(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        for(String card : cards) {

            Card c = new CARCard(card);
            c.setOnCardSwipedListener(onCardSwiped);
            mCardView.addCardToLastStack(c);
        }

//send player_pick or czar_pick
        mCardView.refresh();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onMessageRecieved(JSONObject json) {
        //The alert of the winner and the move to the waiting state.

        try {
            String command = json.getString("command");

            if(command.equals("winner")) {
                ((TextView) getView().findViewById(R.id.title)).setText("Winner selected");

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, new WaitingForPlayersFragment()).commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
