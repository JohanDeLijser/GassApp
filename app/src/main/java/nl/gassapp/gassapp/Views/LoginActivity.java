package nl.gassapp.gassapp.Views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import nl.gassapp.gassapp.R;
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

    private void onResponseMessage(String message) {

        System.out.println(message);

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
