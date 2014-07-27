package com.davidtschida.android.cards.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.davidtschida.android.cards.CARCard;
import com.davidtschida.android.cards.R;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;

import java.util.Arrays;

/**
 * Created by david on 7/27/14.
 */
public class CardListFragment extends Fragment {

    private CardUI mCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.cardlistview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init CardView
        mCardView = (CardUI) view.findViewById(R.id.cardsview);
        mCardView.setSwipeable(true);

        CardStack hand = new CardStack();

        mCardView.addStack(hand);

        mCardView.addCardToLastStack(new CARCard("Passable Transvestites"));
        mCardView.addCardToLastStack(new CARCard("My collection of high tech sex toys"));
        mCardView.addCardToLastStack(new CARCard("Micropenis"));
        mCardView.addCardToLastStack(new CARCard("Obama"));
        mCardView.addCardToLastStack(new CARCard("Passable Transvestites"));
        mCardView.addCardToLastStack(new CARCard("My collection of high tech sex toys"));
        mCardView.addCardToLastStack(new CARCard("Micropenis"));



        mCardView.refresh();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
}
