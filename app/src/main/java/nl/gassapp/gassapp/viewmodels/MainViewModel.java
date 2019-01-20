package nl.gassapp.gassapp.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.DataModels.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;

public class MainViewModel extends ViewModel {

    public void fetchRefuels() {

        User user = SharedPreferencesUtil.getInstance().getUser();

        HttpUtil.getInstance().getRefuels(user, new RequestResponseListener<ArrayList<Refuel>>() {
            @Override
            public void getResult(ArrayList<Refuel> object) {
                SharedPreferencesUtil.getInstance().setRefuels(object);
            }

            @Override
            public void getError(int error) {
                System.out.println(error);
            }
        });

    }


}
