package com.example.message.ui.userlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.message.data.User;
import com.example.message.data.UserRepository;

import java.util.Set;

public class UserListViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    UserRepository mUserRepository;
    LiveData<Set<User>> mAllUsers;

    public UserListViewModel(){
        mUserRepository = new UserRepository();
        mAllUsers = mUserRepository.getAllUsers();
    }

    public LiveData<Set<User>> getAllUsers(){
        return mAllUsers;
    }
}