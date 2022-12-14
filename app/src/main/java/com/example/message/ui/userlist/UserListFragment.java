package com.example.message.ui.userlist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.message.R;
import com.example.message.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Set;

public class UserListFragment extends Fragment {

    private UserListViewModel mViewModel;
    private RecyclerView mUserRecyclerView;
    private UserListAdapter mUserListAdapter;
    private Button mLogout;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    NavController mController;
    private TextView mEmail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserRecyclerView = view.findViewById(R.id.rcv_user_list);
        mUserListAdapter = new UserListAdapter();
        mUserRecyclerView.setAdapter(mUserListAdapter);
        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);

        mLogout = view.findViewById(R.id.btn_logout);
        mLogout.setOnClickListener(v -> {
            mFirebaseAuth.signOut();
            mController.popBackStack();
            mController.navigate(R.id.loginFragment);
        });

        mEmail = view.findViewById(R.id.txt_email);
        mEmail.setText("Current user: " + mFirebaseUser.getEmail());

        mUserListAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, User user) {
                Bundle bundle = new Bundle();
                bundle.putString("extra_email", user.getEmail());
                mController.navigate(R.id.messageListFragment, bundle);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel = new ViewModelProvider(this).get(UserListViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getAllUsers().observe(getViewLifecycleOwner(), new Observer<Set<User>>() {
            @Override
            public void onChanged(Set<User> users) {
                mUserListAdapter.setData(users);
            }
        });
    }
}