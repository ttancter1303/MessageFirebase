package com.example.message.ui.messagelist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.message.R;
import com.example.message.data.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageListFragment extends Fragment {

    private MessageListViewModel mViewModel;
    private EditText mContent;
    private ImageButton mSend;
    private TextView mCurrentUser;
    private TextView mToUser;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private RecyclerView mMessageRecyclerView;
    private MessageListAdapter mMessageListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContent = view.findViewById(R.id.edt_content);
        mSend = view.findViewById(R.id.btn_send);
        mCurrentUser = view.findViewById(R.id.txt_current_user);
        mToUser = view.findViewById(R.id.txt_to_user);

        Bundle bundle = requireArguments();
        String to_email = bundle.getString("extra_email");

        mCurrentUser.setText("Current user: " + mFirebaseUser.getEmail());
        mToUser.setText("To user: " + to_email);

        mSend.setOnClickListener(v -> {
            mViewModel.sendMessage(mFirebaseUser.getEmail(), to_email, mContent.getText().toString());
            mContent.setText("");
        });

        mMessageRecyclerView = view.findViewById(R.id.rcv_message);
        mMessageListAdapter = new MessageListAdapter(mFirebaseUser.getEmail());
        mMessageRecyclerView.setAdapter(mMessageListAdapter);
        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel = new ViewModelProvider(this).get(MessageListViewModel.class);
        Bundle bundle = requireArguments();
        String to_email = bundle.getString("extra_email");
        mViewModel
                .getMessageSend(mFirebaseUser.getEmail(), to_email)
                .observe(getViewLifecycleOwner(), new Observer<List<Message>>() {
                    @Override
                    public void onChanged(List<Message> messages) {
                        mMessageListAdapter.setDataSend(messages);
                        mMessageRecyclerView.scrollToPosition(mMessageListAdapter.getItemCount() - 1);
                    }
                });
        mViewModel
                .getMessageReceiver(mFirebaseUser.getEmail(), to_email)
                .observe(getViewLifecycleOwner(), new Observer<List<Message>>() {
                    @Override
                    public void onChanged(List<Message> messages) {
                        mMessageListAdapter.setDataReceiver(messages);
                        mMessageRecyclerView.scrollToPosition(mMessageListAdapter.getItemCount() - 1);
                    }
                });
    }
}