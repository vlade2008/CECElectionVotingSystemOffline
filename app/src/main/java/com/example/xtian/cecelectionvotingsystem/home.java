package com.example.xtian.cecelectionvotingsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xtian.cecelectionvotingsystem.ResultFragment.ResultFragment;
import com.example.xtian.cecelectionvotingsystem.Sqlite_database.DatabaseHandler;
import com.example.xtian.cecelectionvotingsystem.adapter.NavDrawerListAdapter;
import com.example.xtian.cecelectionvotingsystem.fragment.CandidatesFragment;
import com.example.xtian.cecelectionvotingsystem.fragment.HomeFragment;
import com.example.xtian.cecelectionvotingsystem.fragment.SetFragment;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_vote;
import com.example.xtian.cecelectionvotingsystem.model.NavDrawerItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Xtian on 11/12/2015.
 */
public class home extends ActionBarActivity {

    //statement and async for database
    private ProgressDialog pDialog;
    boolean authenticated = false;
    String statement;
    DBConnection dburl = new DBConnection();
    Connection conn;
    boolean connectionlost = true;


    //private CountDownTimer countDownTimer; // built in android class
    // CountDownTimer
    private long totalTimeCountInMilliseconds; // total count down time in
    // milliseconds
    int time_progress; // time progress election
    int time_elapsed;  // time elapsed  election
    int hour_value; // admin set time
    String set_election_sidebar;
    String voters_sidebar;
    String party_sidebar;
    String candidates_sidebar;
    String admin_password;




    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;


    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;




    //intent displayView
    private static Integer IntentdisplyViews = 0;
    String studentID,studentName,studentFlag;

    DatabaseHandler db;
    int count;

    String mobile_version;
    String app_version;
    private final ArrayList<item_vote> vote_list = new ArrayList<item_vote>();
    String confirmation;

    private long startTime = 0;
    private final long interval = 1000;
    private MalibuCountDownTimer countDownTimer;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                home.this);

        // Setting Dialog Title
        alertDialog2.setTitle("Confirm choose...");

        // Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want to logout?");



        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Intent intent = new Intent(home.this, Main_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        // Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        // Showing Alert Dialog
        alertDialog2.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        mobile_version = String.valueOf(DatabaseHandler.DATABASE_VERSION);
        //important for the id and name for the student voters!!!!! ======
        Intent intent = getIntent();
        studentID = intent.getStringExtra("studentID");
        studentName = intent.getStringExtra("studentName");
        studentFlag = intent.getStringExtra("studentFlag");
       //=========================================================================



        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(3, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(5, -1)));
        navMenuIcons.recycle();
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
//        adapter = new NavDrawerListAdapter(getApplicationContext(),
//                navDrawerItems);
//        mDrawerList.setAdapter(adapter);


        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.mipmap.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item

            IntentdisplyViews = intent.getIntExtra("IntentdisplyView",0);

            displayView(IntentdisplyViews);
        }

        check_vote();


    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_logout:
                onBackPressed();
                return true;
            case R.id.action_aboutus:
                Intent intent = new Intent(home.this, About.class);
                intent.putExtra("studentID",studentID);
                intent.putExtra("studentName",studentName);
                intent.putExtra("IntentdisplyView",IntentdisplyViews);
                intent.putExtra("studentFlag", studentFlag);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_sync:
                if (count==0){
                    Toast.makeText(getApplicationContext(), "No Vote saved!", Toast.LENGTH_LONG).show();
                }else{
                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                            home.this);
                    // Setting Dialog Title
                    alertDialog2.setTitle("Confirm choose...");
                    // Setting Dialog Message
                    alertDialog2.setMessage("Are you sure you want to Sync your vote to the server?");
                    // Setting Positive "Yes" Btn
                    alertDialog2.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    flagElectionAsync flagElectionAsync = new flagElectionAsync();
                                    flagElectionAsync.execute();
                                }
                            });
                    // Setting Negative "NO" Btn
                    alertDialog2.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    dialog.cancel();
                                }
                            });
                    // Showing Alert Dialog
                    alertDialog2.show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new CandidatesFragment();
                break;
            case 2:
                fragment = new SetFragment();
                break;
            case 3:
                fragment = new ResultFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
            //fragmentManager.popBackStack ();
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
            mDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);

    }



    public void check_vote(){
        db = new DatabaseHandler(this);
         count = db.check_vote();
        if (count==0){
            //empty vote
        }else{
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    home.this);

            // Setting Dialog Title
            alertDialog2.setTitle("Please Confirm...");

            // Setting Dialog Message
            alertDialog2.setMessage("Submit your vote now!");



            // Setting Positive "Yes" Btn
            alertDialog2.setPositiveButton("SUBMIT",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            dialog.cancel();
                            pDialog = new ProgressDialog(home.this);
                            pDialog.setMessage("Submit vote. Please wait..");
                            pDialog.setIndeterminate(false);
                            pDialog.setCancelable(false);
                            pDialog.show();
                            List<String> num1 = new ArrayList<>();
                            for (int i=0; i<=4;i++){
                                num1.add("1");
                                num1.add("2");
                                num1.add("3");
                                num1.add("4");
                                num1.add("5");
                            }
                            Collections.shuffle(num1);
                            String a = num1.get(1);
                            String b = num1.get(2);
                            String c = num1.get(3);
                            String d = num1.get(4);
                            String start = a+b+c+d;
                            startTime = Long.parseLong(start);
                            countDownTimer = new MalibuCountDownTimer(startTime, interval);
                            countDownTimer.start();

                        }
                    });
            // Setting Negative "NO" Btn
            alertDialog2.setNegativeButton("NOT NOW",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            dialog.cancel();
                        }
                    });

            // Showing Alert Dialog
            alertDialog2.show();
        }
    }
    public class MalibuCountDownTimer extends CountDownTimer {

        public MalibuCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onFinish()
        {
            flagElectionAsync flagElectionAsync = new flagElectionAsync();
            flagElectionAsync.execute();
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
        }
    }
    public void drop_and_confirmation(){

        AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(home.this);
        alertConnectionLost.setTitle("Successful..");
        alertConnectionLost.setMessage("Vote successful. All saved voted will be deleted! \n" + confirmation);
        alertConnectionLost.setCancelable(false);
        alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(home.this,home.class);
                intent.putExtra("studentID", studentID);
                intent.putExtra("studentName", studentName);
                intent.putExtra("IntentdisplyView", IntentdisplyViews);
                intent.putExtra("studentFlag", studentFlag);
                startActivityForResult(intent, 0);
                finish();
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });
        alertConnectionLost.show();

    }


    /// ================ online method ====================================


    class flagElectionAsync extends AsyncTask<Void,Integer,Void>{

        boolean flag_NotStart = false;
        boolean flag_Start = false;
        boolean flag_finish = false;
        int count_students;
        int count_party;
        int count_candidates;

        @Override
        protected Void doInBackground(Void... params) {
            statement = "SELECT COUNT(*)AS COUNT FROM students";

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try {
                    conn = DriverManager.getConnection(dburl.getUrl());
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(statement);

                    while (rs.next()) {
                        count_students = rs.getInt("COUNT");
                        ResultSet party_rs = st.executeQuery("SELECT COUNT(*)AS COUNT FROM party");
                        while (party_rs.next()){
                            count_party = party_rs.getInt("COUNT");
                        }
                        ResultSet candidates_rs = st.executeQuery("SELECT COUNT(*)AS COUNT FROM candidates");
                        while (candidates_rs.next()){
                            count_candidates = candidates_rs.getInt("COUNT");
                        }

                        rs = st.executeQuery("SELECT * FROM flag");
                        //set time for the election
                        while (rs.next()) {
                            int flag_election = rs.getInt("set_election");
                            publishProgress(flag_election);
                            if (flag_election == 0) {
                                flag_NotStart = true;
                            } else if (flag_election == 1) {
                                flag_Start = true;
                            } else if (flag_election == 2) {
                                flag_finish = true;
                            }
                        }
                    }




                } catch (SQLException e) {
                    e.printStackTrace();
                    connectionlost = false;
                }
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //connection status
            if (connectionlost){
                if (flag_NotStart){
                    pDialog.dismiss();
                    AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(home.this);
                    alertConnectionLost.setTitle("Attention..");
                    alertConnectionLost.setMessage("Election is not yet started!");
                    alertConnectionLost.setCancelable(false);
                    alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    alertConnectionLost.show();
                }else if (flag_Start){
                    new StartTimerElectionAsync().execute();
                }else if (flag_finish){
                    pDialog.dismiss();
                    //lost Connection
                    AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(home.this);
                    alertConnectionLost.setTitle("Data not send..");
                    alertConnectionLost.setMessage("The election is already closed!");
                    alertConnectionLost.setCancelable(false);
                    alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    alertConnectionLost.show();

                }
            }else{
                //lost Connection
                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(home.this);
                alertConnectionLost.setTitle("Connection..");
                alertConnectionLost.setMessage("No connection in database!");
                alertConnectionLost.setCancelable(false);
                alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        System.exit(0);
                    }
                });
                alertConnectionLost.show();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
//            String flag_election = values[0].toString();
//            //Toast.makeText(getApplicationContext(), "" + flag_election, Toast.LENGTH_SHORT).show();
//            if (flag_election.equals("0")) {
//                //flag_NotStart = true;
//            } else if (flag_election.equals("1")) {
//                //flag_Start = true;
//            } else if (flag_election.equals("2")) {
//                Notification n =
//                        new Notification(R.mipmap.ic_launcher, getString(R.string.noticeMe),
//                                System.currentTimeMillis());
//
//                Intent notIntent = new Intent(home.this, com.example.xtian.cecelectionvotingsystem.splash.class);
//
//                PendingIntent wrappedIntent = PendingIntent.getActivity(home.this, 0, notIntent,0);
//
//                n.setLatestEventInfo(getApplicationContext(), getString(R.string.title), getString(R.string.message), wrappedIntent);
//                n.flags |= Notification.FLAG_AUTO_CANCEL;
//                //n.flags |= Notification.DEFAULT_SOUND;
//                n.sound=(Settings.System.DEFAULT_NOTIFICATION_URI);
//                //n.flags |= Notification.DEFAULT_VIBRATE;
//                n.ledARGB = 0xff0000ff;
//                n.flags |= Notification.FLAG_SHOW_LIGHTS;
//
//                // Now invoke the Notification Service
//                String notifService = Context.NOTIFICATION_SERVICE;
//                NotificationManager mgr =
//                        (NotificationManager) getSystemService(notifService);
//                mgr.notify(0, n);
//            }
        }
    }
    class StartTimerElectionAsync  extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            statement = "SELECT UNIX_TIMESTAMP() as time_span";

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try {
                    conn = DriverManager.getConnection(dburl.getUrl());
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(statement);

                    while (rs.next()) {
                        //set time for the election
                        int current_time = rs.getInt("time_span");
                        rs = st.executeQuery("SELECT * FROM election");
                        while (rs.next()){
                            int end_time = rs.getInt("end_election");
                            time_progress = end_time-rs.getInt("start_election");
                            if (end_time <= current_time){ // times up
                                // flag the student to vote
                                PreparedStatement stInsert = conn.prepareStatement("UPDATE flag SET set_election=?");
                                stInsert.setInt(1, 2);
                                stInsert.executeUpdate();
                                authenticated = false;
                            }else{ // start election
                                authenticated = true;
                                time_elapsed = end_time - current_time;
                            }
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    connectionlost = false;
                }
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();

            //connection status
            if (connectionlost){
                //result
                if (authenticated){
                    //election start
                   //startTimer();
                    //Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
                    checkingAsyncTask checkingAsyncTask = new  checkingAsyncTask();
                    checkingAsyncTask.execute();

                }else{
                    //election times up
//                    startTimer();
//                    time_elapsed = 0;
//                    time_progress=0;
//                    countDownTimer.cancel();
//                    set_election_sidebar = "Times Up!";
                    flagElectionAsync flagElectionAsync = new flagElectionAsync();
                    flagElectionAsync.execute();


                }
            }else{
                //lost Connection
                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(home.this);
                alertConnectionLost.setTitle("Connection..");
                alertConnectionLost.setMessage("No connection in database!");
                alertConnectionLost.setCancelable(false);
                alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        System.exit(0);
                    }
                });
                alertConnectionLost.show();
            }

        }
    }
    //checking version
    private class checkingAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            statement = "SELECT *  FROM version";
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try {
                    conn = DriverManager.getConnection(dburl.getUrl());
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(statement);
                    ResultSetMetaData rsmd = rs.getMetaData();
                    while (rs.next()) {
                        app_version = rs.getString("app_version");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    connectionlost = false;
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(home.this);
            pDialog.setMessage("Checking version. Please wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            //connection status
            if (connectionlost) {
                //result
                if (app_version.equals(mobile_version)){
                    submitingAsync submitingAsync = new submitingAsync();
                    submitingAsync.execute();
                }else{
                    //lost Connection
                    AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(home.this);
                    alertConnectionLost.setTitle("Update new version..");
                    alertConnectionLost.setMessage("This app won't run unless you update!");
                    alertConnectionLost.setCancelable(false);
                    alertConnectionLost.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    alertConnectionLost.show();
                }
            } else {
                //lost Connection
                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(home.this);
                alertConnectionLost.setTitle("Connection..");
                alertConnectionLost.setMessage("No connection in database!");
                alertConnectionLost.setCancelable(false);
                alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        System.exit(0);
                    }
                });
                alertConnectionLost.show();
            }

        }
    }
    //sumbiting data
    class  submitingAsync extends AsyncTask<Void,Integer,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            db = new DatabaseHandler(home.this);
            ArrayList<item_vote> vote_list_array = db.Get_Vote();
            for (int i = 0; i < vote_list_array.size(); i++){
                String studentID = vote_list_array.get(i).getStudentID();
                String presidentID = vote_list_array.get(i).getPresidentID();
                String VicepresidentID = vote_list_array.get(i).getVicepresidentID();
                String secretaryID = vote_list_array.get(i).getSecretaryID();
                String tresureID = vote_list_array.get(i).getTresureID();
                String auditorID = vote_list_array.get(i).getAuditorID();
                String proFirstID = vote_list_array.get(i).getProFirstID();
                String proSecondID = vote_list_array.get(i).getProSecondID();
                String firstyearID = vote_list_array.get(i).getFirstyearID();
                String secondyearID = vote_list_array.get(i).getSecondyearID();
                String thirdyearID = vote_list_array.get(i).getThirdyearID();
                String fourthyearID = vote_list_array.get(i).getFourthyearID();
                String voteflag = vote_list_array.get(i).getVoteflag();

                String check_flag;
                statement = "SELECT * FROM students where student_ID= '"+ studentID +"'";
                try {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    try {
                        conn = DriverManager.getConnection(dburl.getUrl());
                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery(statement);
                        while (rs.next()) {
                            check_flag = rs.getString("flag");
                            if(check_flag.equals("1")){
                                confirmation = "One of the account has already voted it will not be counted!";
                                db.Delete_Vote(studentID);
                            }else{
                                PreparedStatement stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
                                stInsert.setInt(1, 1);
                                stInsert.setString(2, presidentID);
                                stInsert.executeUpdate();
                                //vice president
                                stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
                                stInsert.setInt(1, 1);
                                stInsert.setString(2, VicepresidentID);
                                stInsert.executeUpdate();
                                //Secretary
                                stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
                                stInsert.setInt(1, 1);
                                stInsert.setString(2, secretaryID);
                                stInsert.executeUpdate();
                                //Treasure
                                stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
                                stInsert.setInt(1, 1);
                                stInsert.setString(2, tresureID);
                                stInsert.executeUpdate();
                                //Auditor
                                stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
                                stInsert.setInt(1, 1);
                                stInsert.setString(2, auditorID);
                                stInsert.executeUpdate();
                                //Pro-1
                                stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
                                stInsert.setInt(1, 1);
                                stInsert.setString(2, proFirstID);
                                stInsert.executeUpdate();
                                //Pro-2
                                stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
                                stInsert.setInt(1, 1);
                                stInsert.setString(2, proSecondID);
                                stInsert.executeUpdate();
                                //1st year Representative
                                stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
                                stInsert.setInt(1, 1);
                                stInsert.setString(2, firstyearID);
                                stInsert.executeUpdate();
                                //2nd year Representative
                                stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
                                stInsert.setInt(1, 1);
                                stInsert.setString(2, secondyearID);
                                stInsert.executeUpdate();
                                //3rd year Representative
                                stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
                                stInsert.setInt(1, 1);
                                stInsert.setString(2, thirdyearID);
                                stInsert.executeUpdate();
                                //4th year Representative
                                stInsert = conn.prepareStatement("UPDATE candidates SET vote_Number=vote_Number+? WHERE student_ID=? ");
                                stInsert.setInt(1, 1);
                                stInsert.setString(2, fourthyearID);
                                stInsert.executeUpdate();

                                //student flag
                                stInsert = conn.prepareStatement("UPDATE students SET flag=? WHERE student_ID=? ");
                                stInsert.setInt(1, 1);
                                stInsert.setString(2, studentID);
                                stInsert.executeUpdate();
                                confirmation = "";

                                db.Delete_Vote(studentID);
                            }
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                        connectionlost = false;
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(home.this);
            pDialog.setMessage("Syncing vote. Please wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //connection status
            if (connectionlost){
                //result
                drop_and_confirmation();
            }else{
                //lost Connection
                AlertDialog.Builder alertConnectionLost = new AlertDialog.Builder(home.this);
                alertConnectionLost.setTitle("Connection..");
                alertConnectionLost.setMessage("No connection in database!");
                alertConnectionLost.setCancelable(false);
                alertConnectionLost.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        System.exit(0);
                    }
                });
                alertConnectionLost.show();
            }
        }
    }
}

