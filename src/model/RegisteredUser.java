package model;


/**
 * Created by jonathan on 26-10-15.
 */
public class RegisteredUser extends User {

    private final String password;

    public RegisteredUser(String email, String password) {
        super(email);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
