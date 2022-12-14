package com.example.message.data;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRepository {
    FirebaseFirestore mFirestore;

    public UserRepository(){
        mFirestore = FirebaseFirestore.getInstance();
    }

    public LiveData<Set<User>> getAllUsers(){
        MutableLiveData<Set<User>> users = new MutableLiveData<>(new HashSet<>());

        mFirestore.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Set<User> userSet = new HashSet<>();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            User user = new User(document.getId(), document.get("email", String.class));
                            userSet.add(user);
                        }
                        users.setValue(userSet);
                    }
                });

        return users;
    }
}
