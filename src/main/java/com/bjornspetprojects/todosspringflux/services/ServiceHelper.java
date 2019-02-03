package com.bjornspetprojects.todosspringflux.services;

class ServiceHelper {
    static String getPatchValue(String originalValue, String newValue){
        if(newValue == null){
            return originalValue;
        }
        return newValue;
    }
}
