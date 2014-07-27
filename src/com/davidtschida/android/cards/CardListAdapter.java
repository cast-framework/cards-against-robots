package com.davidtschida.android.cards;

import android.app.Service;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by david on 7/27/14.
 */
public class CardListAdapter extends ArrayAdapter<String> {


    public CardListAdapter(Context context, int resource, List<String> objects) {
        super(context, R.layout.card_listitem, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v;
        if (position == 0) {
            v = inflater.inflate(R.layout.card_listitem, null, false);
        } else if (position == getCount() -1) {
            v = inflater.inflate(R.layout.card_listitem_full, null, false);
        } else {
            v = inflater.inflate(R.layout.card_listitem_stack, null, false);
        }

        ((TextView)v.findViewById(R.id.textView)).setText(getItem(position).toString());

        return v;
    }
}
