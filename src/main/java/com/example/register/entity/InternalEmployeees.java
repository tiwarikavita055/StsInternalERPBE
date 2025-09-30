//package com.example.register.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Data
//
//
//public class InternalEmployeees {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//
//    private Long id;
//
//    private String name;
//    private Integer age;
//    private String permanentAddress;
//    private String aadhaarNum;
//    private String pan;
//    private String epfoId;
//    private String esiId;
//    private String mobileNum;
//    private String altMobileNum;
//    private String BankName;
//    private String accNum;
//    private String ifscCode;
//
//    private String department;
//    private String position;
//
//    private Double salary;
//    @Column(unique = true, nullable = false)
//    private String emailId;
//    private String password;
//    private String role;
//
//}