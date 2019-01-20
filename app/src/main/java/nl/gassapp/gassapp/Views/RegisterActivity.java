package nl.gassapp.gassapp.Views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import nl.gassapp.gassapp.DataModels.NetworkError;
import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.databinding.ActivityRegisterBinding;
import nl.gassapp.gassapp.viewmodels.RegisterViewModel;

/**
 *
 * A lot of methods in this document are similar for all the views
 * refer to Views.AddEditRefuelActivity for more information on the methods used
 *
 */
public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel viewModel;

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

        viewModel.getReturnMessage().observe(this, this::onResponseMessage);
        viewModel.getLoadingState().observe(this, this::onLoading);

    }

    private void onResponseMessage(NetworkError networkError) {

        if (networkError.getCode().equals(NetworkError.OK)) {
            Toasty.success(this, networkError.getMessage(), Toast.LENGTH_SHORT, true).show();
            openLoginActivity();

        } else {
            Toasty.error(this, networkError.getMessage(), Toast.LENGTH_SHORT, true).show();
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
