// package com.example.demo.model;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "user_accounts")
// public class UserAccount {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String username;

//     @Column(unique = true)
//     private String email;

//     private String password;
//     private String role;

//     @OneToOne
//     private EmployeeProfile employeeProfile;

// }
