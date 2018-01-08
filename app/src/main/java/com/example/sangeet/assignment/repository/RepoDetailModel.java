package com.example.sangeet.assignment.repository;

/**
 * Created by Sangeet on 1/7/2018.
 */

public class RepoDetailModel {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    String description;
    String html_url;
}
