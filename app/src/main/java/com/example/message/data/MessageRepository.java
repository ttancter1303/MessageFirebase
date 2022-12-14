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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MessageRepository {
    FirebaseFirestore mFirestore;

    public MessageRepository(){
        mFirestore = FirebaseFirestore.getInstance();
    }

    public LiveData<List<Message>> getMessageOfUserToUser(String fromEmail, String toEmail){
        MutableLiveData<List<Message>> messageList = new MutableLiveData<>(new ArrayList<>());

        mFirestore.collection("message")
                .document(fromEmail)
                .collection("to_user")
                .document(toEmail)
                .collection("content")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Set<Message> result = new HashSet<>();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            Map<String, Object> data = document.getData();
                            if(!data.containsKey("time")){
                                continue;
                            }
                            Message message =
                                    new Message(document.getId(), fromEmail, toEmail
                                            , (String) data.get("text")
                                            , (Long) data.get("time"));
                            result.add(message);
                        }
                        messageList.setValue(new ArrayList<>(result));
                    }
                });

        return messageList;
    }

    public void sendMessageFromUserToUser(String fromEmail, String toEmail, String text){
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("text", text);
        dataMap.put("time", new Date().getTime());
        mFirestore.collection("message")
                .document(fromEmail)
                .collection("to_user")
                .document(toEmail)
                .collection("content")
                .document()
                .set(dataMap);
    }
}
