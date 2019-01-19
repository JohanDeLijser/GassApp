package nl.gassapp.gassapp.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nl.gassapp.gassapp.DataModel.Refuel;
import nl.gassapp.gassapp.DataModel.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;

public class AddRefuelViewModal {

    private User applicationUser;
    private ArrayList<Refuel> refuels = new ArrayList<Refuel>();

    public void getTrips(final RequestResponseListener<Boolean> listener) {

        applicationUser = SharedPreferencesUtil.getInstance().getUser();

        HttpUtil.getInstance().getRefuels(applicationUser, new RequestResponseListener<JSONObject>()
        {

            @Override
            public void getResult(JSONObject result)
            {
                try {
                    if (result.getJSONArray("data").length() > 0) {
                        for (int i = 0; i < result.getJSONArray("data").length(); i++) {
                            Refuel refuel = new Refuel(result.getJSONArray("data").getJSONObject(i));
                            refuels.add(refuel);
                        }

                        listener.getResult(true);
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

    public ArrayList<Refuel> getAllTrips() {
        return refuels;
    }
}
