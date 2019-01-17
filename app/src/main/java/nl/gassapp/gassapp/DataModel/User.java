package nl.gassapp.gassapp.DataModel;

public class User {

    private String email;
    private String password;
    private String token;
    private String firstname;
    private String lastname;

    public User(String email, String password) {

        this.email = email;
        this.password = password;

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

}
