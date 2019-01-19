package nl.gassapp.gassapp.Views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.databinding.ActivityRegisterBinding;
import nl.gassapp.gassapp.viewmodels.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel viewModel;

    private Button backButton;

    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        binding.setViewModel(viewModel);

        setupListeners();
    }

    private void setupListeners() {

        viewModel.getReturnMessage().observe(this, response -> onResponseMessage(response));
        viewModel.getLoadingState().observe(this, response -> onLoading(response));

    }

    private void onResponseMessage(Integer message) {

        if (message == RegisterViewModel.REGISTER_OK) {

            Toasty.success(this, "Successfully registered! You can now log in!", Toast.LENGTH_SHORT, true).show();
            openLoginActivity();

        } else if (message == RegisterViewModel.REGISTER_FALSE) {
            Toasty.error(this, "Something went wrong while registering", Toast.LENGTH_SHORT, true).show();
        } else {
            Toasty.error(this, "Network error", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void openLoginActivity() {
        Intent openLogin = new Intent(this, LoginActivity.class);
        openLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openLogin);
    }

    private void onLoading(Boolean loading) {

        if (registerButton == null) {
            registerButton = findViewById(R.id.registerButton);
        }

        registerButton.setEnabled(!loading);
    }
}
