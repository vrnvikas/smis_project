package com.onthespot.vikaskumar.miniproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vikas Kumar on 24-07-2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    List<String> listData;


    public RecyclerAdapter(Context context, List<String> listData){
        inflater = LayoutInflater.from(context);
        this.listData = listData;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_row_status,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

//        Log.i("vikas",listData.size()+"--size");
        if(listData != null && !listData.isEmpty()){
            String current = listData.get(position);
            holder.textView.setText(current);
        }else {
            holder.textView.setText("Empty");
        }
    }
    public void swap(List<String> list){
        listData = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(listData != null){
            if (!listData.isEmpty()) {
                return listData.size();
            }
            else {
                return 1;
            }

        }
        else return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
