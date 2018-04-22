package com.example.demo.dto.stats;

import java.util.Arrays;
import java.util.UUID;

public class Message {
    private UUID id;
    private StringBuilder message;
    private long created;
    private String[] topics;
    private User user;

    public Message() {
        id = UUID.randomUUID();
    }

    public Message(UUID id, StringBuilder message, long created, String[] topics, User user) {
        this.id = id;
        this.message = message;
        this.created = created;
        this.topics = topics;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StringBuilder getMessage() {
        return message;
    }

    public void appendMessage(String str) {
        if(str != null) {
            if(this.message == null) {
                this.message = new StringBuilder(str);
            } else {
                this.message.append(str);
            }
        }
    }

    public void setMessage(StringBuilder message) {
        this.message = message;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", message=" + message +
                ", created=" + created +
                ", topics=" + Arrays.toString(topics) +
                ", user=" + user +
                '}';
    }
}
