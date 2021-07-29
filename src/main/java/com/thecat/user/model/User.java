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

    public static long totalUsers() {
        return count();
    }

    public static long totalUsersActive() {
        return count( "status", Status.ACTIVE);
    }

    public static long totalUsersByStatus( String status ) {
        return count( "status", status.toUpperCase());
    }

    public static User findByName( String name ) {
        return find( "name = ", name ).firstResult();
    }

    public static List<User> findByStatus( String status ) {
        return list( "status", status.toUpperCase() );
    }
 
    public static List<User> findAllUser() {
        return listAll();
    }
}
