package com.ajira.test.fileoperations.util;

import com.ajira.test.fileoperations.exceptions.InvalidConfigurationFileException;

import java.util.Properties;

public class CustomProperties extends Properties {
    static Properties INSTANCE = new CustomProperties();

    private CustomProperties(){
    }

    @Override
    public String getProperty(String key) {
        String value = super.getProperty(key);
        if(value == null || value.trim().length() == 0){
            throw new InvalidConfigurationFileException("Value for key \""+key+"\" is null or empty");
        }
        return value.trim();
    }
}
