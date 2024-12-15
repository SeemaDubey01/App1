package org.example.domainObjects;


public class ServiceEmployeeDTO {

String name;
long roleId;

    public ServiceEmployeeDTO(String name, long roleId) {
        this.name = name;
        this.roleId = roleId;
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
