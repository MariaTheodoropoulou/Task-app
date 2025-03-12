package gr.aueb.cf.taskapp.core.exceptions;

public class UserNotAuthorizedException extends Exception {
    public UserNotAuthorizedException (String message) {
        super(message);
    }
}
