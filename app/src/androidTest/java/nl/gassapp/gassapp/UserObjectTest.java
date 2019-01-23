package nl.gassapp.gassapp;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

import nl.gassapp.gassapp.DataModels.User;

public class UserObjectTest {

    private String email = "test@gassapp.nl";

    private String firstName = "Gassapp";
    private String lastName = "Test";

    @Test
    public void testJsonObjectToUserObject() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("email", email);
            jsonObject.put("firstname", firstName);
            jsonObject.put("lastname", lastName);

        } catch (JSONException e) {

            fail();

        }

        User user = new User(jsonObject);

        assertEquals(user.getEmail(), email);
        assertEquals(user.getFirstname(), firstName);
        assertEquals(user.getLastname(), lastName);

    }

    @Test
    public void testUserObjectToJsonObject() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("email", email);
            jsonObject.put("firstname", firstName);
            jsonObject.put("lastname", lastName);
            jsonObject.put("token", "token");

        } catch (JSONException e) {

            fail();

        }

        User user = new User(jsonObject);
        assertEquals(user.toJSON(), jsonObject.toString());

    }

}
