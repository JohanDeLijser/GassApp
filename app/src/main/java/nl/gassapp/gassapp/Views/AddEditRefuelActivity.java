package nl.gassapp.gassapp.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import es.dmoral.toasty.Toasty;
import nl.gassapp.gassapp.DataModels.NetworkError;
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

        /*

            Code below sets the viewModal Variable in the xml file related to this activity

            This is needed in the xml file by using onClick methods for the ViewModal

         */

        viewModel = ViewModelProviders.of(this).get(AddEditRefuelViewModel.class);

        ActivityAddRefuelBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_refuel);

        binding.setViewModel(viewModel);

        //Setup the observables
        setupListeners();

        //Sets up the takeImageButton
        //takeImage is defined in the view because it is depended on functions that
        //AppCompactActivity provides us
        Button takeImage = (Button) findViewById(R.id.addImage);

        takeImage.setOnClickListener((View v) -> {

            dispatchTakePictureIntent();

        });

    }

    private void setupListeners() {

        viewModel.getReturnMessage().observe(this, this::onResponseMessage);
        viewModel.getLoadingState().observe(this, this::onLoading);

    }

    /*

        onResponseMessage and onLoading are functions used to update the view
        when a request is going to be execute or is executed

     */

    private void onResponseMessage(NetworkError message) {

        if (message.getCode().equals(NetworkError.OK)) {

            Toasty.success(
                    this,
                    message.getMessage(),
                    Toast.LENGTH_SHORT,
                    true)
                    .show();

            openMainActivity();

        } else {

            Toasty.error(
                    this,
                    message.getMessage(),
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

    /**
     *
     * Tries to open the camera and returns it when user is finished
     *
     * When no camera is not available it will notify the user
     *
     */
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

    /**
     *
     * If a image is captured the function will convert it to base64 because
     * that is needed by the api for uploading the image
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
            //Image is always in the png format
            viewModel.afterImageTextChanged("data:image/png;base64," + base64Image);
        }
    }

    private void openMainActivity() {

        Intent openMain = new Intent(this, MainActivity.class);
        openMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openMain);

    }

}
