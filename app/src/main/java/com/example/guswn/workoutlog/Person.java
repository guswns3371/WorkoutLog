package com.example.guswn.workoutlog;

public class Person {
    private String Name;
    private String Id;
   private String Email;
   private String Password;

//    public Person(String name, String id, String email, String password) {
//        Name = name;
//        Id = id;
//        Email = email;
//        Password = password;
//    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
