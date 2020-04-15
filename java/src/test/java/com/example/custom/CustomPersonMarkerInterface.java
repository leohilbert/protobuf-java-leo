package com.example.custom;

public interface CustomPersonMarkerInterface {
    default void cheer() {
        System.out.println("yeah");
    }
}
