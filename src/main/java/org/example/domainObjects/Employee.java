package org.example.domainObjects;

public class Employee {
    private Long id;
    private String firstName;

    public Employee() {
    }

    private String surname;

    private Long roleId;

    public Employee(Long id, String firstName, String surname, Long roleId) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
