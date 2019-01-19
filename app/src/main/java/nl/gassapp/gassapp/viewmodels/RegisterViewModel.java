package nl.gassapp.gassapp.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import nl.gassapp.gassapp.DataModels.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;

public class RegisterViewModel extends ViewModel {

    public static final int REGISTER_OK = 0;
    public static final int REGISTER_FALSE = 1;
    public static final int REGISTER_ERROR = 2;

    private User user;

    private MutableLiveData<Integer> returnMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    public RegisterViewModel() {
        user = new User();
    }

    public void afterEmailTextChanged(CharSequence s) {
        user.setEmail(s.toString());
    }

    public void afterFirstnameTextChanged(CharSequence s) {
        user.setFirstname(s.toString());
    }

    public void afterLastnameTextChanged(CharSequence s) {
        user.setLastname(s.toString());
    }

    public void afterPasswordTextChanged(CharSequence s) {
        user.setPassword(s.toString());
    }

    public void onRegisterClicked() {

        this.loadingState.setValue(true);

        HttpUtil.getInstance().registerUser(user, new RequestResponseListener<User>() {
            @Override
            public void getResult(User object) {
                SharedPreferencesUtil.getInstance().setUser(user);
                returnMessage.setValue(REGISTER_OK);
                loadingState.setValue(false);
            }

            @Override
            public void getError(int errorCode) {
                if (errorCode != 400) {
                    returnMessage.setValue(REGISTER_ERROR);
                } else {
                    returnMessage.setValue(REGISTER_FALSE);
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
