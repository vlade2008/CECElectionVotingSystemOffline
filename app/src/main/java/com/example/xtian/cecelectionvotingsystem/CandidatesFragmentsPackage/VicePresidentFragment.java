package com.example.xtian.cecelectionvotingsystem.CandidatesFragmentsPackage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.xtian.cecelectionvotingsystem.DBConnection;
import com.example.xtian.cecelectionvotingsystem.R;
import com.example.xtian.cecelectionvotingsystem.Sqlite_database.DatabaseHandler;
import com.example.xtian.cecelectionvotingsystem.adapter.SqliteCandidateListAdapater;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.candidateItem;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_views_candidates;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Xtian on 11/20/2015.
 */
public class VicePresidentFragment extends Fragment {
    String fragment_candidate_postion; // candiate position fragment load


    String student_ID ;    //listview display candidate ID
    String student_Name ;  //listview display candidate name
    String position_Name; //listview display candidate position_Name
    String party_Name;  //listview display candidate party_Name
    String candidate_vote;  //listview display candidate party_Name


    String Candidate_value;     //value of create new candidate name
    String textCandidateId;     //update and delete candidate ID
    String textCandidateName;  //update and delete candidate Name
    String textCandidatePosition;
    String textCandidateParty;

    ListView list;
    Button buttonCreate;
    TextView empty;

    ProgressBar pb;
    private ProgressDialog pDialog;

    //ArrayList<partyitem> partyArray;
    ArrayList<candidateItem> candidateArray;
    SqliteCandidateListAdapater candidateListAdapater;

    //statement and async for database
    boolean authenticated = false;
    boolean authenticated_2 = false;
    String statement;
    DBConnection dburl = new DBConnection();
    Connection conn;
    boolean connectionlost = true;

    //CandidateAsync candidateAsync;


    //offline
    DatabaseHandler db;
    ArrayList<item_views_candidates> candidates_list = new ArrayList<item_views_candidates>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tabhost_layout_fragment, container, false);

        pb = (ProgressBar)rootView.findViewById(R.id.candidate_br);

        final Animation anim_alpha = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_alpha);
        final Animation anim_blink = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);

        //list view
        //adapter=new VoterListAdapter(this.getActivity(),R.layout.voterlist,studentArray);
        list=(ListView)rootView.findViewById(R.id.listviewcandidate);
        empty = (TextView)rootView.findViewById(R.id.candidate_empty);
        list.setEmptyView(empty);

        //new CandidateAsync().execute();
        displaydate();
        pb.setVisibility(View.GONE);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    public void displaydate(){
        candidates_list.clear();
        db = new DatabaseHandler(getActivity());
        ArrayList<item_views_candidates> candidates_list_array = db.Get_VicePresident();
        for (int i = 0; i < candidates_list_array.size(); i++){
            String student_ID = candidates_list_array.get(i).getStudent_ID();
            String student_Name = candidates_list_array.get(i).getStudent_Name();
            String student_Course = candidates_list_array.get(i).getStudent_Course();
            String position_ID = candidates_list_array.get(i).getPosition_ID();
            String position_Name = candidates_list_array.get(i).getPosition_Name();
            String party_ID = candidates_list_array.get(i).getParty_ID();
            String party_Name = candidates_list_array.get(i).getParty_Name();
            String vote_Number = candidates_list_array.get(i).getVote_Number();
            String candidate_coin = candidates_list_array.get(i).getCandidate_coin();
            candidates_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));
        }
        candidateListAdapater=new SqliteCandidateListAdapater(getActivity(),R.layout.sqlitecandidates_list,candidates_list);
        list.setAdapter((ListAdapter) candidateListAdapater);
    }
}
