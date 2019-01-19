package nl.gassapp.gassapp.ViewModel;

import nl.gassapp.gassapp.DataModel.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;

public class LoginViewModal {

    private User applicationUser;

    public void loginUser(String email, String password, final RequestResponseListener<Boolean> listener) {

        System.out.println(email);
        System.out.println(password);

        User loginUser = new User(email, password);

        HttpUtil.getInstance().authenticateUser(loginUser, new RequestResponseListener<User>() {
            @Override
            public void getResult(User object) {
                applicationUser = object;
                SharedPreferencesUtil.getInstance().setUser(applicationUser);

                System.out.println(applicationUser.getFirstname());

                listener.getResult(true);
            }

            @Override
            public void getError(int errorCode) {
                listener.getError(errorCode);
            }
        });

    }

}
