package com.example.jasmine.goalideatracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class AddGoal extends AppCompatActivity {
private FirebaseDatabase mDatabase;
private DatabaseReference mMyRef;
EditText mEditGoal;
EditText mReasonGoal;
private DatabaseReference mRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        mDatabase = FirebaseDatabase.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setTitle(R.string.goal_title_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinnerPriority);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
        R.array.priority, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);


        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_goal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final String mGoals= getResources().getString(R.string.db_parent_Goals);
        final String mName= getResources().getString(R.string.db_Goal_Name);
        final String mReason= getResources().getString(R.string.db_Goal_Reason);
        final String mTime= getResources().getString(R.string.db_Goal_Time);
        final String mPriority= getResources().getString(R.string.db_Goal_Priority);
        final String mType= getResources().getString(R.string.db_Goal_Type);
        mMyRef = mDatabase.getInstance().getReference().child(mGoals);


        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }else if (item.getItemId() == R.id.action_settings_done){

            mEditGoal = (EditText) findViewById(R.id.editGoal);
            String mGoalName = mEditGoal.getText().toString();

            mReasonGoal = (EditText) findViewById(R.id.addReason);
            String mReasonGoalText = mReasonGoal.getText().toString();

            Spinner spinner2 = (Spinner) findViewById(R.id.spinnerType);
            String selectedType = spinner2.getSelectedItem().toString();

            Spinner spinner1= (Spinner) findViewById(R.id.spinnerPriority);
            String selectedPriority = spinner1.getSelectedItem().toString();

            long mDate = System.currentTimeMillis();
            SimpleDateFormat mSdf = new SimpleDateFormat("MMM MM dd,yyy h:mm a");
            String mDateString = mSdf.format(mDate);



            DatabaseReference newGoal = mMyRef.push();
            newGoal.child(mName).setValue(mGoalName);
            newGoal.child(mTime).setValue(mDateString);
            newGoal.child(mReason).setValue(mReasonGoalText);
            newGoal.child(mPriority).setValue(selectedPriority);
            newGoal.child(mType).setValue(selectedType);

            Intent addGoalIntent = new Intent(AddGoal.this, MainActivity.class);
            startActivity(addGoalIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
