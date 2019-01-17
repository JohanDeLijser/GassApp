package nl.gassapp.gassapp.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nl.gassapp.gassapp.DataModel.Refuel;
import nl.gassapp.gassapp.DataModel.Refuels;
import nl.gassapp.gassapp.DataModel.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;

public class RefuelViewModal {

    private User applicationUser;

    public void getTrips() {

        applicationUser = SharedPreferencesUtil.getInstance().getUser();
        System.out.println(applicationUser);

        HttpUtil.getInstance().getRefuels(applicationUser, new RequestResponseListener<JSONObject>()
        {

            @Override
            public void getResult(JSONObject result)
            {
                try {
                    System.out.println(result.getJSONArray("data"));
                    Refuels refuels = new Refuels();

                    for (int i = 0; i < result.getJSONArray("data").length(); i++) {
                        Refuel refuel = new Refuel(result.getJSONArray("data").getJSONObject(i));

                    }
                } catch (JSONException e) {
                    System.out.println("Exception while output:" + e);
                }
            }

            @Override
            public void getError(int error)
            {
                System.out.println(error);
            }
        });
    }
}
