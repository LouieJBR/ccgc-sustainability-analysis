package com.ccgc.cggcbackend.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String auth0Id;
    private String email;
    private String name;
}
