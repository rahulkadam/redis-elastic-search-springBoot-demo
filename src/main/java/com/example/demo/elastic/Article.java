package com.example.demo.elastic;

public class Article {
    public String author;
    public String content;

    public Article() {
    }

    public Article(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
