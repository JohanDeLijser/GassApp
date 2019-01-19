package nl.gassapp.gassapp.DataModels;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private String email;
    private String password;
    private String token;
    private String firstname;
    private String lastname;

    public User() {
    }

    public User(JSONObject object) {

        try {

            this.email = object.getString("email");
            this.firstname = object.getString("firstname");
            this.lastname = object.getString("lastname");
            this.token = object.getString("token");

        } catch (JSONException e) {

            //no code

        }

    }

    public User(String email, String firstname, String lastname, String token) {

        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.token = token;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public void setLastname(String lastname) { this.firstname = lastname; }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public String toJSON(){

        JSONObject jsonObject= new JSONObject();

        try {
            jsonObject.put("email", getEmail());
            jsonObject.put("firstname", getFirstname());
            jsonObject.put("lastname", getLastname());
            jsonObject.put("token", getToken());

            return jsonObject.toString();
        } catch (JSONException e) {
            return "";
        }

    }

}
