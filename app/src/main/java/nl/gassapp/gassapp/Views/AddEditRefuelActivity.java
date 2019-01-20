package nl.gassapp.gassapp.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import es.dmoral.toasty.Toasty;
import nl.gassapp.gassapp.R;
import android.databinding.DataBindingUtil;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import nl.gassapp.gassapp.databinding.ActivityAddRefuelBinding;
import nl.gassapp.gassapp.viewmodels.AddEditRefuelViewModel;

public class AddEditRefuelActivity extends AppCompatActivity {

    private AddEditRefuelViewModel viewModel;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(AddEditRefuelViewModel.class);

        ActivityAddRefuelBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_refuel);

        binding.setViewModel(viewModel);

        setupListeners();

        Button takeImage = (Button) findViewById(R.id.addImage);

        takeImage.setOnClickListener((View v) -> {

            dispatchTakePictureIntent();

        });
    }

    private void setupListeners() {

        viewModel.getReturnMessage().observe(this, this::onResponseMessage);
        viewModel.getLoadingState().observe(this, this::onLoading);

    }

    private void onResponseMessage(Integer message) {

        if (message == AddEditRefuelViewModel.ADD_REFUEL_OK) {

            Toasty.success(
                    this,
                    "Refuel added ",
                    Toast.LENGTH_SHORT,
                    true)
                    .show();

            openMainActivity();

        } else if (message == AddEditRefuelViewModel.ADD_REFUEL_FALSE) {

            Toasty.error(
                    this,
                    "Not able to add the refuel",
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

        if (saveButton == null) {

            saveButton = (Button) findViewById(R.id.addRefuelButton);

        }

        saveButton.setEnabled(!loading);

    }

    private void dispatchTakePictureIntent() {

        try {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        } catch (SecurityException e) {

            Toasty.error(
                    this,
                    "Can not access the camera",
                    Toast.LENGTH_SHORT,
                    true)
                    .show();

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);

            viewModel.afterImageTextChanged("data:image/png;base64," + base64Image);
        }
    }

    private void openMainActivity() {

        Intent openMain = new Intent(this, MainActivity.class);
        openMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openMain);

    }

}
