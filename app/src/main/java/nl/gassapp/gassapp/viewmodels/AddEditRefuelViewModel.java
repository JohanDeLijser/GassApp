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
import java.util.ArrayList;

import nl.gassapp.gassapp.DataModels.EditRefuel;
import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.DataModels.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;

public class AddEditRefuelViewModel extends ViewModel {

    public static final int ADD_REFUEL_OK = 0;
    public static final int ADD_REFUEL_FALSE = 1;
    public static final int ADD_REFUEL_ERROR = 2;

    private EditRefuel refuel;

    private String base64Image;

    private MutableLiveData<Integer> returnMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    public AddEditRefuelViewModel() {
        refuel = new EditRefuel();
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

    public void afterImageTextChanged(String s) {
        if (!s.isEmpty()) {
            refuel.setImage(s);
        }
    }

    public void onAddRefuelClicked() {
        this.loadingState.setValue(true);

        HttpUtil.getInstance().addRefuel(refuel, new RequestResponseListener<Refuel>() {
            @Override
            public void getResult(Refuel object) {


                ArrayList<Refuel> refuels = SharedPreferencesUtil.getInstance().getRefuels();

                refuels.add(object);

                SharedPreferencesUtil.getInstance().setRefuels(refuels);

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
}
