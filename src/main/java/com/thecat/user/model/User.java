package com.thecat.user.model;

import java.time.LocalDate;
import java.util.List;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

public class User extends PanacheMongoEntity {
    
    public String name;
    public String gender;
    public LocalDate birthDate;
    public String email;
    public String password;
    public LocalDate createDate;
    public Status status;

    public static User findByName( String name ) {
        return find( "name", name ).firstResult();
    }

    public static List<User> findByStatus( String status ) {
        return list( "status", status );
    }

    public static List<User> findAllUser() {
        return listAll();
    }

    public static List<User> findActiveUser() {
        return list( "status", Status.ACTIVE );
    }
}
