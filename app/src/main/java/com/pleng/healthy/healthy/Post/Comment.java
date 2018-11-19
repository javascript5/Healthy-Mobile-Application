package com.pleng.healthy.healthy.Post;

public class Comment {
    int postId , id;
    String name,email, body;

    public Comment(int postId, int id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }
}
