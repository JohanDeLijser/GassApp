package nl.gassapp.gassapp.DataModels;

import android.support.annotation.NonNull;

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
