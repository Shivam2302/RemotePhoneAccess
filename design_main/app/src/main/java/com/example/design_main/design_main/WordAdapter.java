package com.example.design_main.design_main;

/**
 * Created by User on 12/31/2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import com.example.design_main.design_main.R;

/**
 * Created by shivam on 23/12/17.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    public WordAdapter(@NonNull Context context, ArrayList<Word> word) {
        super(context, 0, word);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(convertView==null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);

        Word currentWord = getItem(position);

        TextView heading = (TextView) listItemView.findViewById(R.id.heading_points);
        TextView content = (TextView) listItemView.findViewById(R.id.content_points);

        heading.setText(currentWord.getHeading());
        content.setText(currentWord.getContent());

        return listItemView;
    }
}