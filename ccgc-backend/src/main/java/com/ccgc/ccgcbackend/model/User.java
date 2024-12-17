package com.ccgc.ccgcbackend.model;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "users")
@Data //lombok generate getters/setters/toString
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
@Column(nullable = false, unique = true)
private String email;

@Column(nullable = false)
private String name;

@Column(nullable = false)
private String password;
}