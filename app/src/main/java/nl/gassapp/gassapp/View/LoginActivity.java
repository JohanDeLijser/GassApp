package nl.gassapp.gassapp.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import nl.gassapp.gassapp.DataModel.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.Utils.HttpUtil;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    private Button loginButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.emailfield);
        password = (EditText)findViewById(R.id.passwordField);

        loginButton = (Button) findViewById(R.id.loginButton);
        handleLoginButton(loginButton);

    }

    private void handleLoginButton(Button loginButton) {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }

}
