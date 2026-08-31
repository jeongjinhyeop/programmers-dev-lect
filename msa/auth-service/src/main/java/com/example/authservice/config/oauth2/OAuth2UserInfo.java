package com.example.authservice.config.oauth2;

import java.util.Map;

public interface OAuth2UserInfo {

    Map<String, Object> attributes();

    String id();

    String emai();

    String name();

    String imageUrl();

}
