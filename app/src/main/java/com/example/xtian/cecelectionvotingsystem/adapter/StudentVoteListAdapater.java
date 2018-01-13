package com.example.xtian.cecelectionvotingsystem.adapter;

import android.app.Activity;
import android.content.Context;
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
public class StudentVoteListAdapater extends ArrayAdapter<item_views_candidates> {

    Context context;
    int layoutResourceId;
    ArrayList<item_views_candidates> candidateArray = new ArrayList<item_views_candidates>();

    public StudentVoteListAdapater(Context context, int layoutResourceId, ArrayList<item_views_candidates> candidateArray) {
        super(context,layoutResourceId,candidateArray);
        this.layoutResourceId = layoutResourceId;
        this.context=context;
        this.candidateArray=candidateArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View rowView = inflater.inflate(layoutResourceId, parent, false);





        TextView Name = (TextView) rowView.findViewById(R.id.textCandidateName);
        TextView Party = (TextView) rowView.findViewById(R.id.textCandidateparty);
        TextView ID = (TextView) rowView.findViewById(R.id.textcandidateID);
        ImageView Image = (ImageView) rowView.findViewById(R.id.imageView3);

        item_views_candidates candidateItem = candidateArray.get(position);
        //Bitmap scale;
        //scale = Bitmap.createScaledBitmap(candidateItem.getImageIcon(),100,100,true);

        Name.setText(candidateItem.getStudent_Name());
        Party.setText(candidateItem.getParty_Name());
        ID.setText(candidateItem.getStudent_ID());
        //Image.setImageBitmap(candidateItem.getImageIcon());
        Image.setVisibility(View.GONE);

        ID.setAlpha(0f);

        return rowView;
    }
}
