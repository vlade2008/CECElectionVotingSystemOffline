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
import com.example.xtian.cecelectionvotingsystem.getSetterItem.candidateItem;

import java.util.ArrayList;

/**
 * Created by Xtian on 12/1/2015.
 */
public class ConfirmAdapater extends ArrayAdapter<candidateItem> {

    Context context;
    int layoutResourceId;
    ArrayList<candidateItem> candidateArray = new ArrayList<candidateItem>();

    public ConfirmAdapater(Context context, int layoutResourceId, ArrayList<candidateItem> candidateArray) {
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
        TextView positionName = (TextView) rowView.findViewById(R.id.textPositionName);
        TextView ID = (TextView) rowView.findViewById(R.id.textcandidateID);
        ImageView Image = (ImageView) rowView.findViewById(R.id.imageView3);

        candidateItem candidateItem = candidateArray.get(position);
        Bitmap scale;
        scale = Bitmap.createScaledBitmap(candidateItem.getImageIcon(),100,100,true);

        Name.setText(candidateItem.getStudent_Name());
        Party.setText(candidateItem.getParty_Name());
        ID.setText(candidateItem.getStudent_ID());
        Image.setImageBitmap(candidateItem.getImageIcon());
        positionName.setText(candidateItem.getPosition_Name());

        ID.setAlpha(0f);

        return rowView;
    }
}
