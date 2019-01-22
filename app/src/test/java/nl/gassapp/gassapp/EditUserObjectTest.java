package nl.gassapp.gassapp;

import org.junit.Test;

import nl.gassapp.gassapp.DataModels.EditUser;

import static org.junit.Assert.*;

public class EditUserObjectTest {

    private String email = "test@gassapp.nl";
    private String password = "Test12";

    private String wrongEmail = "false-email";
    private String wrongPassword = "password";

//    private Context instrumentationCtx;
//
//    @Before
//    public void setup() {
//        instrumentationCtx = InstrumentationRegistry.getContext();
//    }

    @Test
    public void testUserObjectSetEmailMatch() {

        EditUser user = new EditUser();

        user.setEmail(email);

        assertEquals(email, user.getEmail());

    }

    @Test
    public void testUserObjectPasswordMatch() {

        EditUser user = new EditUser();

        user.setPassword(password);

        assertEquals(password, user.getPassword());

    }

    @Test
    public void testUserObjectSetEmailDoesNotMatch() {

        EditUser user = new EditUser();

        user.setEmail(wrongEmail);

        assertNotEquals(email, user.getEmail());

    }

    @Test
    public void testUserObjectSetPasswordDoesNotMatch() {

        EditUser user = new EditUser();

        user.setPassword(wrongPassword);

        assertNotEquals(password, user.getPassword());

    }


}
