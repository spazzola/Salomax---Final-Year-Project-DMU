package Salomax.employee;

public enum WorkRole {

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