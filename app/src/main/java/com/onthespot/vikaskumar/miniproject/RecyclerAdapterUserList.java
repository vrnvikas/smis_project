package com.onthespot.vikaskumar.miniproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Vikas Kumar on 25-07-2016.
 */

public class RecyclerAdapterUserList extends RecyclerView.Adapter<RecyclerAdapterUserList.MyUserListViewHolder> {


    private final LayoutInflater inflater;
    private final Context context;
    List<String> listData;
    private List<Integer> listFollowing;
    private List<User> listUser;

    public RecyclerAdapterUserList(Context context, List<Integer> listFollowing, List<User> listUser) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.listFollowing = listFollowing;
        this.listUser = listUser;
    }

    @Override
    public RecyclerAdapterUserList.MyUserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row_user_list, parent, false);
        return new RecyclerAdapterUserList.MyUserListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterUserList.MyUserListViewHolder holder, int position) {

        if (listUser != null && !listUser.isEmpty()) {
            User current = listUser.get(position);
            holder.userName.setText(current.getFullName());
            if (listFollowing != null && !listFollowing.isEmpty()) {
                if (isInFollow(current.getUserId())) {
                    holder.follow.setChecked(true);
                }

            }

        } else {
            holder.userName.setText("Empty");
        }


    }

    private boolean isInFollow(Integer userId) {

        for (int id : listFollowing) {
            if (id == userId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if(listUser != null){
            if (!listUser.isEmpty()) {
                return listUser.size();
            }
            else {
                return 1;
            }

        }
        else return 1;
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
            follow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    makeFollowRequest(context,getPosition());

                }
            });

        }
    }

    private void makeFollowRequest(Context context, int position) {
        if (Utility.isNetworkAvailable(context)) {
            RequestUtility.followRequest(constructHeader(), constructJsonObject(listUser.get(position).getUserId()), context);
        } else {
            Toast.makeText(context, "InterNet Not Working", Toast.LENGTH_LONG).show();
        }
    }

    private FollowPostPojo constructJsonObject(int userId) {
        FollowPostPojo followPostPojo = new FollowPostPojo();
        followPostPojo.setUserID(userId);
        return followPostPojo;
    }

    private String constructHeader() {
        String userEmail = Utility.getUserEmail(context);
        String userToken = Utility.getUserToken(context);
        String hashPassword = Utility.getHashString(userToken, "SHA-1");
        String header = userToken + ":" + "None";
        return "Basic " + Base64.encodeToString(header.getBytes(), Base64.NO_WRAP);

    }
}
