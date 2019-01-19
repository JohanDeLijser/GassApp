package nl.gassapp.gassapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;

import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.ViewModel.LoginViewModal;
import nl.gassapp.gassapp.ViewModel.RefuelViewModal;

public class LoginActivity extends AppCompatActivity {

    private final LoginViewModal loginViewModal = new LoginViewModal();

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

    private void openMainActivity() {

        Intent openMain = new Intent(this, MainActivity.class);
        openMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openMain);

    }

    private void handleLoginButton(Button loginButton) {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginViewModal.loginUser(
                        username.getText().toString(),
                        password.getText().toString(),
                        new RequestResponseListener<Boolean>() {

                            @Override
                            public void getResult(Boolean bool) {

                                System.out.println(bool);

                                if (bool) {

                                    openMainActivity();

                                }
                            }

                            @Override
                            public void getError(int errorCode) {
                                System.out.println(errorCode);
                            }

                        }
                );

            }
        });
    }

}
