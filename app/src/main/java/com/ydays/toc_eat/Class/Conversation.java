package com.ydays.toc_eat.Class;

/**
 * Created by clemb on 24/02/2017.
 */

public class Conversation {
    public String id;
    public String title;
    public String recipient_id;
    public String sender_id;
    public String created_at;
    public String updated_at;

    public Conversation(String id,String title,String recipient_id,String sender_id,String created_at,String updated_at){
        this.id = id;
        this.title = title;
        this.recipient_id = recipient_id;
        this.sender_id = sender_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
