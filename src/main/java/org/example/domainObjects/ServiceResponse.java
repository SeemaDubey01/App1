package org.example.domainObjects;

public class ServiceResponse {
    private Long id;
    private String name;

    private long roleId;

    public ServiceResponse() {
    }

    public ServiceResponse(Long id, String name, long roleId) {
        this.id = id;
        this.name = name;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
