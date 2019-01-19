package nl.gassapp.gassapp.Views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;
import nl.gassapp.gassapp.viewmodels.LoginViewModel;
import nl.gassapp.gassapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.setViewModel(viewModel);

        setupListeners();

    }

    private void setupListeners() {

        viewModel.getReturnMessage().observe(this, response -> onResponseMessage(response));
        viewModel.getLoadingState().observe(this, response -> onLoading(response));

    }

    private void onResponseMessage(Integer message) {

        if (message == LoginViewModel.LOGIN_OK) {

            String firstName = SharedPreferencesUtil.getInstance().getUser().getFirstname();

            Toasty.success(
                    this,
                    "Welcome " + firstName,
                    Toast.LENGTH_SHORT,
                    true)
                    .show();

            openMainActivity();

        } else if (message == LoginViewModel.LOGIN_FALSE) {

            Toasty.error(
                    this,
                    "Email or password is incorrect",
                    Toast.LENGTH_SHORT,
                    true)
                    .show();

        } else {

            Toasty.error(
                    this,
                    "Network error",
                    Toast.LENGTH_SHORT,
                    true)
                    .show();

        }

    }

    private void onLoading(Boolean loading) {

        if (loginButton == null) {

            loginButton = findViewById(R.id.loginButton);

        }

        loginButton.setEnabled(!loading);

    }

    private void openMainActivity() {

        Intent openMain = new Intent(this, MainActivity.class);
        openMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openMain);

    }

}
