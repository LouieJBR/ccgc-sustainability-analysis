package com.ccgc.cggcbackend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

    @Entity
    @Table(name = "users")
    public class User {
        @Setter
        @Getter
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Setter
        @Getter
        private String auth0Id;

        @Setter
        @Getter
        private String name;

        @Setter
        @Getter
        private String email;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private List<ProfilingResult> results;
    }

