package model;


/**
 * Created by jonathan on 12-10-15.
 */
public class User {

    private final String email;


    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return getEmail();
    }
}
