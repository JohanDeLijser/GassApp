package nl.gassapp.gassapp.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.DataModels.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.Utils.HttpUtil;

public class AddRefuelViewModel extends ViewModel {

    public static final int ADD_REFUEL_OK = 0;
    public static final int ADD_REFUEL_FALSE = 1;
    public static final int ADD_REFUEL_ERROR = 2;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Refuel refuel;

    private ImageView refuelImageThumbnail;

    private EditText imageBase64;

    private String base64Image;

    private MutableLiveData<Integer> returnMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    public AddRefuelViewModel() {
        refuel = new Refuel();

        imageBase64 = (EditText) findViewById(R.id.imageBase64);
        refuelImageThumbnail = (ImageView) findViewById(R.id.refuelImageThumbnail);
    }

    public void afterLitersTextChanged(CharSequence s) {
        if (!s.toString().isEmpty()) {
            refuel.setLiters(Double.parseDouble(s.toString()));
        }
    }

    public void afterPriceTextChanged(CharSequence s) {
        if (!s.toString().isEmpty()) {
            refuel.setPrice(Double.parseDouble(s.toString()));
        }
    }

    public void afterKilometersTextChanged(CharSequence s) {
        if (!s.toString().isEmpty()) {
            refuel.setKilometers(Double.parseDouble(s.toString()));
        }
    }

    public void afterImageTextChanged(CharSequence s) {
        if (!s.toString().isEmpty()) {
            refuel.setPicturePath(s.toString());
        }
    }

    public void onAddRefuelClicked() {
        this.loadingState.setValue(true);

        HttpUtil.getInstance().addRefuel(refuel, new RequestResponseListener<JSONObject>() {
            @Override
            public void getResult(JSONObject object) {
                returnMessage.setValue(ADD_REFUEL_OK);
                loadingState.setValue(false);
            }

            @Override
            public void getError(int errorCode) {
                if (errorCode != 400) {
                    returnMessage.setValue(ADD_REFUEL_FALSE);
                } else {
                    returnMessage.setValue(ADD_REFUEL_ERROR);
                }
                loadingState.setValue(false);
            }
        });
    }

    public MutableLiveData<Integer> getReturnMessage() {
        return this.returnMessage;
    }

    public MutableLiveData<Boolean> getLoadingState() {
        return this.loadingState;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            refuelImageThumbnail.setImageBitmap(imageBitmap);


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);

            imageBase64.setText(base64Image);
        }
    }
}
