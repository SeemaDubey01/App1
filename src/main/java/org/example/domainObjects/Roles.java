package org.example.domainObjects;

public enum Roles {
    ADMIN(1),
    USER(2),
    MANAGER(3);

    private final long id;

    Roles(long id) {
        this.id = id;
    }

    // Constructor to set the ID for each role



    // Getter method for the ID
    public long getId() {
        return id;
    }
}