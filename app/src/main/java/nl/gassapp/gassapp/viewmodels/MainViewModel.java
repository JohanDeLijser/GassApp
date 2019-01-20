package nl.gassapp.gassapp.viewmodels;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import nl.gassapp.gassapp.DataModels.NetworkError;
import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.DataModels.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;

/**
 *
 * The biggest difference between MainViewModel and the other ViewModals
 * is that MainViewModal does not contain any MutatebleLiveData.
 *
 * This is because the sharedPreferencesUtil has a MutatebleLiveData return function so no
 * is need here because the view directly observes the sharedPreferencesUtil one
 *
 */
public class MainViewModel extends ViewModel {

    /**
     *
     * Fetches the user refuel trips from the api and saves them in the sharedPreferences
     *
     */
    public void fetchRefuels() {

        User user = SharedPreferencesUtil.getInstance().getUser();

        HttpUtil.getInstance().getRefuels(user, new RequestResponseListener<ArrayList<Refuel>>() {
            @Override
            public void getResult(ArrayList<Refuel> object) {
                SharedPreferencesUtil.getInstance().setRefuels(object);
            }

            @Override
            public void getError(NetworkError error) {
                System.out.println(error.getMessage());
            }
        });

    }


}
