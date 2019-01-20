package nl.gassapp.gassapp.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;

import nl.gassapp.gassapp.DataModels.EditUser;
import nl.gassapp.gassapp.DataModels.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;
import nl.gassapp.gassapp.Views.RegisterActivity;

public class LoginViewModel extends ViewModel {

    public static final int LOGIN_OK = 0;
    public static final int LOGIN_FALSE = 1;
    public static final int LOGIN_ERROR = 2;

    private EditUser user;

    private MutableLiveData<Integer> returnMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    public LoginViewModel() {

        user = new EditUser();

    }

    public void afterEmailTextChanged(CharSequence s) {

        user.setEmail(s.toString());

    }

    public void afterPasswordTextChanged(CharSequence s) {

        user.setPassword(s.toString());

    }

    public void onLoginClicked() {

        this.loadingState.setValue(true);

        HttpUtil.getInstance().authenticateUser(user, new RequestResponseListener<User>() {
            @Override
            public void getResult(User object) {
                SharedPreferencesUtil.getInstance().setUser(object);
                returnMessage.setValue(LOGIN_OK);
                loadingState.setValue(false);
            }

            @Override
            public void getError(int errorCode) {
                if (errorCode != 400) {

                    returnMessage.setValue(LOGIN_ERROR);

                } else {

                    returnMessage.setValue(LOGIN_FALSE);

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
