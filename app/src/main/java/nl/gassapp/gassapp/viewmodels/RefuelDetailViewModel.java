package nl.gassapp.gassapp.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.InputStream;

import nl.gassapp.gassapp.DataModels.NetworkError;
import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.DataModels.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;

public class RefuelDetailViewModel extends ViewModel {

    private MutableLiveData<NetworkError> returnMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    public void deleteRefuel(Refuel refuel) {

        loadingState.setValue(true);

        User user = SharedPreferencesUtil.getInstance().getUser();

        HttpUtil.getInstance().deleteRefuel(user, refuel, new RequestResponseListener<Boolean>() {
            @Override
            public void getResult(Boolean object) {

                if (object) {

                    returnMessage.setValue(new NetworkError(NetworkError.OK, "Trip deleted"));

                } else {

                    returnMessage.setValue(new NetworkError(NetworkError.SERVER_ERROR, "Failed to delete trip"));

                }

                loadingState.setValue(false);
            }

            @Override
            public void getError(NetworkError error) {
                returnMessage.setValue(error);
                loadingState.setValue(false);
            }
        });


        System.out.println(refuel.getId());

    }

    public MutableLiveData<NetworkError> getReturnMessage() {

        return this.returnMessage;

    }

    public MutableLiveData<Boolean> getLoadingState() {

        return this.loadingState;

    }

}
