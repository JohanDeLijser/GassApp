package nl.gassapp.gassapp.DataModels;

import android.support.annotation.NonNull;

/**
 * A EditUser Modal
 *
 * This modal is for some setters that are needed during the login / register process
 *
 * After the processes are complete and verified by the api there will be a user object created.
 * so the setters are not available at authenticated user objects
 *
 */
public class EditUser extends User {

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

}
