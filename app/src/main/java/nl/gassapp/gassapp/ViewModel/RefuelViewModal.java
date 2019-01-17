package nl.gassapp.gassapp.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import nl.gassapp.gassapp.DataModel.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.Utils.HttpUtil;

public class RefuelViewModal {

    private User applicationUser;

    public void getTrips() {

        User loginUser = new User("jeroenfrenken@icloud.com", "Jeroen12");

        HttpUtil.getInstance().authenticateUser(loginUser, new RequestResponseListener<User>() {
            @Override
            public void getResult(User object) {
                applicationUser = object;

                HttpUtil.getInstance().getUser(applicationUser, new RequestResponseListener<JSONObject>()
                {

                    @Override
                    public void getResult(JSONObject result)
                    {

                        try {
//                    System.out.println(result.getJSONObject("data").getString("email"));
                            System.out.println(result.getJSONObject("data"));

                        } catch (JSONException e) {

                            System.out.println("Exception while output");

                        }

                    }

                    @Override
                    public void getError(int error)
                    {

                        System.out.println(error);

                    }

                });

            }

            @Override
            public void getError(int object) {
                System.out.println("Error while log in");
            }
        });



    }

}
