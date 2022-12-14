package com.example.message.data;

public class Message {
    private String id;
    private String fromUser;
    private String toUser;
    private String text;
    private long time;

    public Message(String id, String fromUser, String toUser, String text, long time) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.text = text;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
