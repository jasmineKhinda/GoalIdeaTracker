package com.example.jasmine.goalideatracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
/**
 * Created by jasmine on 21/12/17.
 */

public class EditGoal extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mMyRef;
    EditText mEditGoal;
    EditText mReasonGoal;
    private String task_key;
    String selectedSpinnerPriority;
    String selectedSpinnerType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final String mGoals= getResources().getString(R.string.db_parent_Goals);
        final String mName= getResources().getString(R.string.db_Goal_Name);
        final String mReason= getResources().getString(R.string.db_Goal_Reason);
        final String mTime= getResources().getString(R.string.db_Goal_Time);
        final String mPriority= getResources().getString(R.string.db_Goal_Priority);
        final String mType= getResources().getString(R.string.db_Goal_Type);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        task_key = getIntent().getExtras().getString(getResources().getString(R.string.goal_Id));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setTitle(R.string.goal_edit_title_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mMyRef = FirebaseDatabase.getInstance().getReference().child(mGoals);

        mEditGoal = (EditText) findViewById(R.id.editGoal);
        mReasonGoal = (EditText) findViewById(R.id.addReason);


        final Spinner spinner1 = (Spinner) findViewById(R.id.spinnerPriority);
        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.priority, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);


        final Spinner spinner2 = (Spinner) findViewById(R.id.spinnerType);
        final ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);



        mMyRef.child(task_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String goal_title = (String) dataSnapshot.child(mName).getValue();
                String goal_Reason = (String) dataSnapshot.child(mReason).getValue();
                String goal_time= (String) dataSnapshot.child(mTime).getValue();
                String priority= (String) dataSnapshot.child(mPriority).getValue();
                String type= (String) dataSnapshot.child(mType).getValue();

                mEditGoal.setText(goal_title);
                mReasonGoal.setText(goal_Reason);
                spinner1.setSelection(adapter1.getPosition(priority));
                spinner2.setSelection(adapter2.getPosition(type));


                Log.d("SINGLET", "2 onOptionsItemSelected: + "+ priority);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_goal, menu);
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
        mMyRef = FirebaseDatabase.getInstance().getReference().child(mGoals).child(task_key);

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




            mMyRef.child(mName).setValue(mGoalName);
            mMyRef.child(mTime).setValue(mDateString);
            mMyRef.child(mReason).setValue(mReasonGoalText);
            mMyRef.child(mPriority).setValue(selectedPriority);
            mMyRef.child(mType).setValue(selectedType);



            Intent addGoalIntent = new Intent(EditGoal.this, MainActivity.class);
            startActivity(addGoalIntent);
        }else if (item.getItemId() == R.id.delete){

            mMyRef.removeValue();
            Intent addGoalIntent = new Intent(EditGoal.this, MainActivity.class);
            startActivity(addGoalIntent);

        }
        return super.onOptionsItemSelected(item);


    }





}
