package nl.gassapp.gassapp.Views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jesusm.kfingerprintmanager.KFingerprintManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import nl.gassapp.gassapp.DataModels.NetworkError;
import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.DataModels.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;
import nl.gassapp.gassapp.viewmodels.LoginViewModel;
import nl.gassapp.gassapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;

    private Button loginButton;

    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instance http util
        HttpUtil.getInstance(this);

        //Instance shared preferences util
        SharedPreferencesUtil.getInstance(this);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.setViewModel(viewModel);

        setupListeners();

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterView();
            }
        });

        final User user = SharedPreferencesUtil.getInstance().getUser();

        if (user != null) {

            String key = "USER_STORAGE_EMAIL";

            KFingerprintManager fingerPrintManager = new KFingerprintManager(this, key);

            Context context = this;

            fingerPrintManager.authenticate(new KFingerprintManager.AuthenticationCallback() {
                @Override
                public void onAuthenticationSuccess() {
                    HttpUtil.getInstance().getUser(user, new RequestResponseListener<User>() {

                        @Override
                        public void getResult(User object) {

                            if (user.getEmail().equals(object.getEmail())) {

                                String firstName = SharedPreferencesUtil.getInstance().getUser().getFirstname();

                                Toasty.success(
                                        context,
                                        "Welcome back " + firstName,
                                        Toast.LENGTH_SHORT,
                                        true)
                                        .show();

                                openMainActivity();

                            } else {

                                SharedPreferencesUtil.getInstance().setUser(null);

                                Toasty.error(
                                        context,
                                        "Token expired. Login again",
                                        Toast.LENGTH_SHORT,
                                        true)
                                        .show();

                            }

                        }

                        @Override
                        public void getError(NetworkError error) {

                            Toasty.error(
                                    context,
                                    error.getMessage(),
                                    Toast.LENGTH_SHORT,
                                    true)
                                    .show();

                            SharedPreferencesUtil.getInstance().setUser(null);

                        }

                    });
                }

                @Override
                public void onSuccessWithManualPassword(@NotNull String s) {
                    viewModel.user.setEmail(user.getEmail());
                    viewModel.user.setPassword(s);
                    viewModel.onLoginClicked();
                }

                @Override
                public void onFingerprintNotRecognized() {
                    Toasty.error(
                            context,
                            "Fingerprint not recognized",
                            Toast.LENGTH_SHORT,
                            true)
                            .show();
                }

                @Override
                public void onAuthenticationFailedWithHelp(@Nullable String s) {
                    SharedPreferencesUtil.getInstance().setUser(null);

                    Toasty.error(
                            context,
                            "Please login again",
                            Toast.LENGTH_SHORT,
                            true)
                            .show();
                }

                @Override
                public void onFingerprintNotAvailable() {
                    SharedPreferencesUtil.getInstance().setUser(null);

                    Toasty.error(
                            context,
                            "Fingerprint not available. Please login",
                            Toast.LENGTH_SHORT,
                            true)
                            .show();
                }

                @Override
                public void onCancelled() {
                    SharedPreferencesUtil.getInstance().setUser(null);

                    Toasty.error(
                            context,
                            "Please login again",
                            Toast.LENGTH_SHORT,
                            true)
                            .show();
                }
            }, getSupportFragmentManager());

        }

    }

    private void setupListeners() {

        viewModel.getReturnMessage().observe(this, this::onResponseMessage);
        viewModel.getLoadingState().observe(this, this::onLoading);

    }

    private void onResponseMessage(NetworkError networkError) {

        if (networkError.getCode().equals(NetworkError.OK)) {

            String firstName = SharedPreferencesUtil.getInstance().getUser().getFirstname();

            Toasty.success(
                    this,
                    "Welcome " + firstName,
                    Toast.LENGTH_SHORT,
                    true)
                    .show();

            openMainActivity();

        } else {

            Toasty.error(
                    this,
                    networkError.getMessage(),
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

    private void openRegisterView() {
        Intent openRegister = new Intent(this, RegisterActivity.class);
        startActivity(openRegister);
    }

}
