package nl.gassapp.gassapp;

import org.junit.Test;

import nl.gassapp.gassapp.DataModels.EditUser;

import static org.junit.Assert.*;

public class LoginViewTest {

    private String email = "jeroenfrenken@icloud.com";
    private String password = "Jeroen12";

    private String wrongEmail = "false-email";
    private String wrongPassword = "password";


    @Test
    public void testUserObjectSetEmail() {

        EditUser user = new EditUser();

        user.setEmail(email);

        assertEquals(email, user.getEmail());

    }

    @Test
    public void testUserObjectPassword() {

        EditUser user = new EditUser();

        user.setPassword(password);

        assertEquals(password, user.getPassword());

    }

}
