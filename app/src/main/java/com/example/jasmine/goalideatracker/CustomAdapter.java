package com.example.jasmine.goalideatracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by jasmine on 21/12/17.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Goal> goal;
    private Context mCtx;



    public CustomAdapter(List<Goal> goal, Context mCtx) {
        this.goal = goal;
        this.mCtx = mCtx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        Goal myList = goal.get(position);
        holder.goal_name.setText(myList.getName());
        holder.goal_date.setText(myList.getTime());


//        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                PopupMenu popup = new PopupMenu(mCtx, view);
//                MenuInflater inflater = popup.getMenuInflater();
//                inflater.inflate(R.menu.popup_menu, popup.getMenu());
//
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.delete:
//                                //handle menu1 click
//                                break;
//                            case R.id.share:
//                                //handle menu2 click
//                                break;
//                        }
//                        return false;
//                    }
//                });
//                //displaying the popup
//                popup.show();
//
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return goal.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView goal_date;
        TextView goal_name;
        ImageButton buttonViewOption;
        private DatabaseReference mRef;


        public ViewHolder(final View itemView) {
            super(itemView);

            goal_name = (TextView) itemView.findViewById(R.id.goalName);
            goal_date = (TextView) itemView.findViewById(R.id.goalTimeAdded);
            mRef = FirebaseDatabase.getInstance().getReference().child("Goals");


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mCtx, EditGoal.class);
                    i.putExtra("GoalId", goal.get(getAdapterPosition()).getKey());
                    mCtx.startActivity(i);
                }
            });

            buttonViewOption = (ImageButton) itemView.findViewById(R.id.imageButton);
            buttonViewOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(mCtx, v);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.popup_menu, popup.getMenu());


                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int position = getAdapterPosition();
                            Log.d("DELETE", "adapter position " + position);

                            switch (item.getItemId()) {
                                case R.id.delete:
                                    mRef.child(goal.get(getAdapterPosition()).getKey()).removeValue();
                                   goal.remove(getAdapterPosition());
//                                    Log.d("DELETE", "adapter position " + position);
                                    notifyItemRemoved(getAdapterPosition());
                                    break;
                                case R.id.share:
                                    //handle menu2 click
                                    break;
                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();

                }
            });
        }


    }



}

