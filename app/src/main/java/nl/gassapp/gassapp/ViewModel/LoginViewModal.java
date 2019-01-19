package nl.gassapp.gassapp.ViewModel;

import nl.gassapp.gassapp.DataModel.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;

public class LoginViewModal {

    private User applicationUser;

    public void loginUser(String email, String password, final RequestResponseListener<Boolean> listener) {

        User loginUser = new User(email, password);

        HttpUtil.getInstance().authenticateUser(loginUser, new RequestResponseListener<User>() {
            @Override
            public void getResult(User object) {
                applicationUser = object;
                SharedPreferencesUtil.getInstance().setUser(applicationUser);

                listener.getResult(true);
            }

            @Override
            public void getError(int errorCode) {
                listener.getError(errorCode);
            }
        });

    }

}
