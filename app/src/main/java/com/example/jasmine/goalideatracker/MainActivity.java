package com.example.jasmine.goalideatracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.widget.PopupMenu;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mGoalList;
    private DatabaseReference mDatabaseReference;
    CustomAdapter mAdapter;
    List<Goal> list  = new ArrayList<>();;;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.home_title_toolbar);

        mGoalList = (RecyclerView) findViewById(R.id.goal_list);
        mGoalList.setHasFixedSize(true);
        mGoalList.setLayoutManager(new LinearLayoutManager(this));
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(getResources().getString(R.string.db_parent_Goals));


        //addedgfdgfdghdfh
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                     Goal mGoals = dataSnapshot.getValue(Goal.class);
                    mGoals.setKey(dataSnapshot.getKey());
                    list.add(mGoals);
                }

                mAdapter = new CustomAdapter(list,MainActivity.this);
                mGoalList.setAdapter(mAdapter);


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {



            }
        });

        ////addedgfdgfdghdfh




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGoal.class);
                startActivity(intent);
            }
        });







    }

    @Override
    protected void onStart() {
        super.onStart();

//        //Create the firebase recycler view adapter
//       FirebaseRecyclerAdapter<Goal, GoalViewHolder> FBRA = new FirebaseRecyclerAdapter<Goal, GoalViewHolder>(Goal.class, R.layout.goal_row, GoalViewHolder.class, mDatabaseReference) {
//
//            @Override
//            protected void populateViewHolder(GoalViewHolder viewHolder, Goal model, int position) {
//
//                //get position that is clicked
//                final String goal_key = getRef(position).getKey().toString();
//                viewHolder.setName(model.getName());
//                viewHolder.setDate(model.getTime());
//
//                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    @Overridedata
//                    public void onClick(View v) {
//
//                        Intent singleGoalActivity = new Intent(MainActivity.this, EditGoal.class);
//                        singleGoalActivity.putExtra(getResources().getString(R.string.goal_Id).toString(), goal_key);
//                        startActivity(singleGoalActivity);
//                    }
//                });
//
//
//
//
//            }
//
//
//        };
//
//
//        mGoalList.setAdapter(FBRA);

//        mAdapter = new CustomAdapter(mDatabaseReference.,this);
//        mGoalList.setAdapter(mAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    /**
     * This is the method which is called when the overflow menu is clicked on each card All goals
     *
     * @param v
     */
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());

        //popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }

}




