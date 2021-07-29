package com.thecat.user.model;

import java.util.Random;

public enum Status {
    ACTIVE,
    INACTIVE;

    public static Status getRandomGender() {
        Random random = new Random();
        return values()[random.nextInt(values().length)]; 
    }
}
