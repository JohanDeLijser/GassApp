package nl.gassapp.gassapp.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import nl.gassapp.gassapp.DataModels.EditUser;
import nl.gassapp.gassapp.DataModels.NetworkError;
import nl.gassapp.gassapp.DataModels.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;

public class LoginViewModel extends ViewModel {

    public EditUser user;

    private MutableLiveData<NetworkError> returnMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    private Boolean checkInput = true;

    public LoginViewModel() {

        /*

            Here a EditUser modal is chosen because it gives us
            access to the methods setEmail() and setPassword()

         */
        user = new EditUser();

        loadingState.setValue(true);

    }

    /**
     *
     * Checks if the login button can be enabled
     *
     */
    private void checkEnabled() {

        /*

            CheckInput checks if the inputs need to be checked.

            This is done because the network request can disable the button.
            But if the user types in a input field it could enable the button
            but the request is still loading

            So checkinput = true when there is no network request in progress
            checkinput = false when there is a network request in progress

         */
        if (checkInput) {

            if (
                    user.getEmail() != null &&
                    user.getPassword() != null &&
                    !user.getEmail().isEmpty() &&
                    !user.getPassword().isEmpty()
            ) {

                loadingState.setValue(false);

            } else {

                loadingState.setValue(true);

            }

        }

    }

    /*

        Methods for updating the EditUser object

     */


    public void afterEmailTextChanged(CharSequence s) {

        user.setEmail(s.toString());

        checkEnabled();

    }

    public void afterPasswordTextChanged(CharSequence s) {

        user.setPassword(s.toString());

        checkEnabled();

    }

    /**
     *
     * Starts the login authentication.
     *
     * After the authentication the mutablelivedata will be updated so the view will be updated
     *
     */
    public void onLoginClicked() {

        this.loadingState.setValue(true);

        this.checkInput = false;

        HttpUtil.getInstance().authenticateUser(user, new RequestResponseListener<User>() {
            @Override
            public void getResult(User object) {
                SharedPreferencesUtil.getInstance().setUser(object);
                returnMessage.setValue(new NetworkError(NetworkError.OK, "Login successful"));
                loadingState.setValue(false);
            }

            @Override
            public void getError(NetworkError networkError) {
                returnMessage.setValue(networkError);
                loadingState.setValue(false);
            }
        });

    }

    /*

        MutableLivedata so the view can observe for changes

     */

    public MutableLiveData<NetworkError> getReturnMessage() {

        this.checkInput = true;

        return this.returnMessage;

    }

    public MutableLiveData<Boolean> getLoadingState() {

        return this.loadingState;

    }

}
