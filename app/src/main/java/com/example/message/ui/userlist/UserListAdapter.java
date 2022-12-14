package com.example.message.ui.userlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.message.R;
import com.example.message.data.User;

import java.util.HashSet;
import java.util.Set;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    Set<User> mData = new HashSet<>();

    public void setData(Set<User> data){
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserListAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserViewHolder holder, int position) {
        holder.bindView(getByIndex(mData, position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public User getByIndex(Set<User> set, int position) {
        int i = 0;
        for (User entry:set) {
            if (i == position) return entry;
            i++;
        }
        return null;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView mEmail;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            mEmail = itemView.findViewById(R.id.txt_email);
            itemView.setOnClickListener(v -> {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onClick(itemView, getByIndex(mData, getLayoutPosition()));
                }
            });
        }

        public void bindView(User user){
            mEmail.setText(user.getEmail());
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onClick(View view, User user);
    }
}
