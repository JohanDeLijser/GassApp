package nl.gassapp.gassapp.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import nl.gassapp.gassapp.DataModels.EditRefuel;
import nl.gassapp.gassapp.DataModels.NetworkError;
import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;

public class AddEditRefuelViewModel extends ViewModel {

    private EditRefuel refuel;

    private MutableLiveData<NetworkError> returnMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    public AddEditRefuelViewModel() {
        refuel = new EditRefuel();
    }


    /*

        methods below will check for changes in the input fields
        and update the EditRefuel object accordingly

     */

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

    /**
     *
     * The button handler for the created button.
     *
     * All field validation will be done by the api and the wrong information
     * will be returned in the NetworkError object
     *
     */
    public void onAddRefuelClicked() {
        this.loadingState.setValue(true);

        HttpUtil.getInstance().addRefuel(refuel, new RequestResponseListener<Refuel>() {
            @Override
            public void getResult(Refuel object) {

                ArrayList<Refuel> refuels = SharedPreferencesUtil.getInstance().getRefuels();

                refuels.add(object);

                SharedPreferencesUtil.getInstance().setRefuels(refuels);

                returnMessage.setValue(new NetworkError(NetworkError.OK, "Created refuel"));
                loadingState.setValue(false);
            }

            @Override
            public void getError(NetworkError errorCode) {
                returnMessage.setValue(errorCode);
                loadingState.setValue(false);
            }
        });
    }

    /*

        Below methods are used for returning loading state and api response

     */

    public MutableLiveData<NetworkError> getReturnMessage() {
        return this.returnMessage;
    }

    public MutableLiveData<Boolean> getLoadingState() {
        return this.loadingState;
    }
}
