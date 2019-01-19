package nl.gassapp.gassapp.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import nl.gassapp.gassapp.DataModels.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;

public class LoginViewModel extends ViewModel {

    private User user;

    private MutableLiveData<String> returnMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    public LoginViewModel() {

        user = new User();

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
                user = object;
                SharedPreferencesUtil.getInstance().setUser(user);
                returnMessage.setValue("good");
                loadingState.setValue(false);
            }

            @Override
            public void getError(int errorCode) {
                returnMessage.setValue("false");
                loadingState.setValue(false);
            }
        });

    }

    public MutableLiveData<String> getReturnMessage() {

        return this.returnMessage;

    }

    public MutableLiveData<Boolean> getLoadingState() {

        return this.loadingState;

    }

}
