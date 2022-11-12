package Salomax.employee;

public enum WorkRole {

    OWNER("OWNER"),
    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE");

    private final String role;

    WorkRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}