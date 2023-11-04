package pl.piwowarski.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("user not found in repo");
    }
}
