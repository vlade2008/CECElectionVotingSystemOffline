package com.example.xtian.cecelectionvotingsystem.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xtian.cecelectionvotingsystem.R;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_views_candidates;

import java.util.ArrayList;

/**
 * Created by Xtian on 12/1/2015.
 */
public class SqliteCandidateListAdapater extends ArrayAdapter<item_views_candidates> {

    Context context;
    int layoutResourceId;
    ArrayList<item_views_candidates> candidateArray = new ArrayList<item_views_candidates>();

    public SqliteCandidateListAdapater(Context context, int layoutResourceId, ArrayList<item_views_candidates> candidateArray) {
        super(context,layoutResourceId,candidateArray);
        this.layoutResourceId = layoutResourceId;
        this.context=context;
        this.candidateArray=candidateArray;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View rowView = inflater.inflate(layoutResourceId, parent, false);

        TextView Name = (TextView) rowView.findViewById(R.id.textviewCandidate_Name);
        TextView Party = (TextView) rowView.findViewById(R.id.textviewCandidate_Party);
        TextView Position = (TextView) rowView.findViewById(R.id.textviewCandidate_Position);
        TextView Vote = (TextView) rowView.findViewById(R.id.textviewCanidate_Vote);
        TextView ID = (TextView) rowView.findViewById(R.id.textviewCandidate_ID);
        ImageView Image = (ImageView) rowView.findViewById(R.id.candidateImage);

        item_views_candidates item_views_candidates = candidateArray.get(position);
        Bitmap scale;
        //scale = Bitmap.createScaledBitmap(candidateItem.getImageIcon(),100,100,true);

        Name.setText(item_views_candidates.getStudent_Name());
        Party.setText(item_views_candidates.getParty_Name());
        Position.setText(item_views_candidates.getPosition_Name());
        Vote.setText(item_views_candidates.getVote_Number());
        ID.setText(item_views_candidates.getStudent_ID());

        //Image.setImageBitmap(candidateItem.getImageIcon());
        Image.setVisibility(View.GONE);

        Position.setAlpha(0f);
        ID.setAlpha(0f);

        return rowView;
    }
}
