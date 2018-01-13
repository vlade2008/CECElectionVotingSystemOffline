package com.example.xtian.cecelectionvotingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Xtian on 1/11/2016.
 */
public class About extends AppCompatActivity {

    String studentID,studentName;
    private static Integer IntentdisplyViews;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(About.this, home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("studentID", studentID);
        intent.putExtra("studentName",studentName);
        intent.putExtra("IntentdisplyView",IntentdisplyViews);
        startActivityForResult(intent, 0);
        finish();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        //action header bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Intent intent = getIntent();
        studentID = intent.getStringExtra("studentID");
        studentName = intent.getStringExtra("studentName");
        IntentdisplyViews = intent.getIntExtra("IntentdisplyView", 0);

    }
}
