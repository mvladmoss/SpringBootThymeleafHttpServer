package com.bsuir.second.model;

import lombok.Getter;

@Getter
public enum GenderEnum {

    MALE("male"),
    FEMALE("female");


    private String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }

}
