package entity.business_objects;

public enum User {
    PROTON_USER("kstu_aray@protonmail.com", "test123456");


    private final String username;
    private final String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
