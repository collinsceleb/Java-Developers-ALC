package com.example.collinsceleb.developersdiary;

/**
 * Created by Collinsceleb on 9/8/2017.
 */

public class Developer {
    public String avatar_url;       /*"avatar_url"*/
    public String login;            /*"login"*/
    public String html_url;

    public Developer(String profilePictureUrl, String userName, String developerUrl) {
        this.avatar_url = profilePictureUrl;
        this.login = userName;
        this.html_url = developerUrl;
    }
}