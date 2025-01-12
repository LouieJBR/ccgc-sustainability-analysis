package com.ccgc.cggcbackend.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OAuthTokenRequest {

    private String token;
    private String email;
    private String name;
    private String provider;
    private String providerId;

}

