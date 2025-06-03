package com.example.edutrack;

public class Message {
    public String id;
    public String senderId;
    public String receiverId;
    public String content;
    public long timestamp;


    public Message() {

    }


    public Message(String id, String senderId, String receiverId, String content, long timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = timestamp;
    }
}
