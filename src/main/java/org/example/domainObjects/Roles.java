package org.example.domainObjects;

public enum Roles {
    ADMIN(1),
    USER(2),
    MANAGER(3);

    private final long id;

    // Constructor to set the ID for each role
    Roles(int id) {
        this.id = id;
    }


    // Getter method for the ID
    public long getId() {
        return id;
    }
}