package com.example.message.ui.messagelist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.message.data.Message;
import com.example.message.data.MessageRepository;

import java.util.List;

public class MessageListViewModel extends ViewModel {
    MessageRepository mMessageRepository;

    LiveData<List<Message>> mMessageSend;
    LiveData<List<Message>> mMessageReceiver;

    public MessageListViewModel(){
        mMessageRepository = new MessageRepository();
    }

    public LiveData<List<Message>> getMessageSend(String from, String to){
        if(mMessageSend == null){
            mMessageSend = mMessageRepository.getMessageOfUserToUser(from, to);
        }
        return mMessageSend;
    }

    public LiveData<List<Message>> getMessageReceiver(String from, String to){
        if(mMessageReceiver == null){
            mMessageReceiver = mMessageRepository.getMessageOfUserToUser(to, from);
        }
        return mMessageReceiver;
    }

    public void sendMessage(String from, String to, String text){
        mMessageRepository.sendMessageFromUserToUser(from, to, text);
    }
}