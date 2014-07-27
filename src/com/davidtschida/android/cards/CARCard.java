package com.davidtschida.android.cards;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.objects.RecyclableCard;

/**
 * Created by david on 7/27/14.
 */
public class CARCard extends RecyclableCard {

    private String title;

    public CARCard(String title) {

        setData(title);
        this.title = title;
    }

    @Override
    protected void applyTo(View convertView) {
        ((TextView) convertView.findViewById(R.id.textView)).setText(title);
    }

    @Override
    protected int getCardLayoutId() {
        return R.layout.card_listitem_full;
    }

    @Override
    protected int getCardLayout() {
        return R.layout.card_listitem_full;
    }

    @Override
    protected int getId() {
        return R.layout.card_listitem_full;
    }

    @Override
    protected int getLastCardLayout() {
        return R.layout.card_listitem_full;
    }

    @Override
    protected int getFirstCardLayout() {
        return R.layout.card_listitem_full;
    }
}
