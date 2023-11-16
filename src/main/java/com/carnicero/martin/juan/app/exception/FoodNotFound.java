package com.carnicero.martin.juan.app.exception;

public class FoodNotFound extends RuntimeException{
    public FoodNotFound(String message) {
        super(message);
    }
}
