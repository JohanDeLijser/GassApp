package nl.gassapp.gassapp.Views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import es.dmoral.toasty.Toasty;
import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.databinding.ActivityAddRefuelBinding;
import nl.gassapp.gassapp.viewmodels.AddRefuelViewModel;
import nl.gassapp.gassapp.viewmodels.RegisterViewModel;

public class AddRefuelActivity extends AppCompatActivity {

    private AddRefuelViewModel viewModel;

    private Button addRefuelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(AddRefuelViewModel.class);

        ActivityAddRefuelBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_refuel);

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
            openMainActivity();

        } else if (message == RegisterViewModel.REGISTER_FALSE) {
            Toasty.error(this, "Something went wrong while registering, either this e-mail is already taken, or some of the other fields aren't filled in correctly", Toast.LENGTH_LONG, true).show();
        } else {
            Toasty.error(this, "Network error, something went wrong", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void openMainActivity() {
        Intent openMain = new Intent(this, MainActivity.class);
        openMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openMain);
    }

    private void onLoading(Boolean loading) {

        if (addRefuelButton == null) {
            addRefuelButton = findViewById(R.id.addRefuelButton);
        }

        addRefuelButton.setEnabled(!loading);
    }
}
