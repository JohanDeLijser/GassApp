package nl.gassapp.gassapp.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.json.JSONObject;

import nl.gassapp.gassapp.DataModels.EditUser;
import nl.gassapp.gassapp.DataModels.NetworkError;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.Utils.HttpUtil;

public class RegisterViewModel extends ViewModel {

    private EditUser user;

    private MutableLiveData<NetworkError> returnMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    /**
     * Using a EditUser because this gives us access to the needed setters
     */
    public RegisterViewModel() {
        user = new EditUser();
    }

    /*

        Methods to update the EditUser modal

     */

    public void afterEmailTextChanged(CharSequence s) {

        if (s.toString().contains("@")) {
            user.setEmail(s.toString());
        }
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

    /*

        This view uses the same architecture as the LoginViewModal
        Refer to LoginViewModal.onLoginClicked for more information about what
        the function does

        The sames goes for the MutableLiveData methods

     */

    public void onRegisterClicked() {
        this.loadingState.setValue(true);

        HttpUtil.getInstance().registerUser(user, new RequestResponseListener<JSONObject>() {
            @Override
            public void getResult(JSONObject object) {
                returnMessage.setValue(new NetworkError(NetworkError.OK, "Account created"));
                loadingState.setValue(false);
            }

            @Override
            public void getError(NetworkError networkError) {
                returnMessage.setValue(networkError);
                loadingState.setValue(false);
            }
        });
    }

    public MutableLiveData<NetworkError> getReturnMessage() {
        return this.returnMessage;
    }

    public MutableLiveData<Boolean> getLoadingState() {
        return this.loadingState;
    }
}
