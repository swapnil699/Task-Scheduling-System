package org.example.taskscheduling.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Roles {
    MANEGER, SDE, TESTER;

    @JsonCreator
    public static Roles fromValue(String value) {
        for (Roles role : Roles.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }
}
