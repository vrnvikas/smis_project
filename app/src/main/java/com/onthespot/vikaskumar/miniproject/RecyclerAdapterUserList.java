package com.onthespot.vikaskumar.miniproject;

import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vikas Kumar on 25-07-2016.
 */

public class RecyclerAdapterUserList extends RecyclerView.Adapter<RecyclerAdapterUserList.MyUserListViewHolder> {


    private final LayoutInflater inflater;
    List<String> listData;
    private List<Integer> listFollowing;
    private List<User> listUser;

    public RecyclerAdapterUserList(Context context, List<Integer> listFollowing, List<User> listUser) {
        inflater = LayoutInflater.from(context);
        this.listFollowing = listFollowing;
        this.listUser = listUser;
    }

    @Override
    public RecyclerAdapterUserList.MyUserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row_user_list,parent,false);
        return new RecyclerAdapterUserList.MyUserListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterUserList.MyUserListViewHolder holder, int position) {

        if(listUser != null && !listUser.isEmpty()){
            User current = listUser.get(position);
            holder.userName.setText(current.getFullName());
            if(listFollowing != null && !listFollowing.isEmpty()){
                if(isInFollow(current.getUserId())){
                    holder.follow.setChecked(true);
                }

            }

        }else {
            holder.userName.setText("Empty");
        }


    }

    private boolean isInFollow(Integer userId) {

        for (int id : listFollowing){
            if(id == userId){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void swap(List<Integer> listFollowing, List<User> listUser) {
        this.listFollowing = listFollowing;
        this.listUser = listUser;
        notifyDataSetChanged();
    }

    public class MyUserListViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        CheckBox follow;

        public MyUserListViewHolder(View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.user_name);
            follow = (CheckBox) itemView.findViewById(R.id.follow);

        }
    }
}
