package com.example.message.ui.messagelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.message.R;
import com.example.message.data.Message;
import com.example.message.data.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_RIGHT = 0;
    public static final int TYPE_LEFT = 1;

    String mCurrentUser;
    List<Message> mDataSend = new ArrayList<>();
    List<Message> mDataReceiver = new ArrayList<>();
    final List<Message> mAllData = new ArrayList<>();

    public MessageListAdapter(String currentUser){
        mCurrentUser = currentUser;
    }

    public void setDataSend(List<Message> data){
        synchronized (mAllData) {
            mDataSend = data;
            mAllData.clear();
            mAllData.addAll(mDataSend);
            mAllData.addAll(mDataReceiver);
            Collections.sort(mAllData, new Comparator<Message>() {
                @Override
                public int compare(Message o1, Message o2) {
                    return (int) (o1.getTime() - o2.getTime());
                }
            });
            notifyDataSetChanged();
        }
    }

    public void setDataReceiver(List<Message> data){
        synchronized (mAllData) {
            mDataReceiver = data;
            mAllData.clear();
            mAllData.addAll(mDataSend);
            mAllData.addAll(mDataReceiver);
            Collections.sort(mAllData, new Comparator<Message>() {
                @Override
                public int compare(Message o1, Message o2) {
                    return (int) (o1.getTime() - o2.getTime());
                }
            });
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mAllData.get(position);
        if(message.getFromUser().equals(mCurrentUser)){
            return TYPE_RIGHT;
        } else {
            return TYPE_LEFT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_RIGHT){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.right_message_item, parent, false);
            return new RightMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.left_message_item, parent, false);
            return new LeftMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RightMessageViewHolder){
            ((RightMessageViewHolder)holder).bindView(mAllData.get(position));
        } else {
            ((LeftMessageViewHolder)holder).bindView(mAllData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mAllData.size();
    }

    public class LeftMessageViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public LeftMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.txt_message);
        }

        public void bindView(Message message){
            mTextView.setText(message.getText());
        }
    }

    public class RightMessageViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public RightMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.txt_message);
        }

        public void bindView(Message message){
            mTextView.setText(message.getText());
        }
    }
}
