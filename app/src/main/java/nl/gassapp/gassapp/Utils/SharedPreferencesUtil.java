package nl.gassapp.gassapp.Utils;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.DataModels.User;

public class SharedPreferencesUtil {

    private static final String USER_KEY = "user";
    private static final String REFUELS_KEY = "refuels";

    private SharedPreferences sharedPreferences;

    private static SharedPreferencesUtil instance = null;

    private MutableLiveData<ArrayList<Refuel>> refuels = new MutableLiveData<ArrayList<Refuel>>();

    private SharedPreferencesUtil(Context context)
    {

        String sharedDomainKey = "com.gassapp.sharedPreferences";

        sharedPreferences = context.getSharedPreferences(sharedDomainKey,Context.MODE_PRIVATE);

    }

    public static synchronized SharedPreferencesUtil getInstance(Context context)
    {

        if (instance == null) {

            instance = new SharedPreferencesUtil(context);

        }

        return instance;

    }

    public static synchronized SharedPreferencesUtil getInstance()
    {
        if (instance == null)
        {
            throw new IllegalStateException(SharedPreferencesUtil.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    private Boolean setItem(String key, String value) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, value);

        editor.commit();

        return true;

    }

    private String getItem(String key) {

        return sharedPreferences.getString(key, "{}");

    }

    public User getUser() {

        String user = this.getItem(USER_KEY);

        if (user.equals("{}")) {

            return null;

        }


        try {

            JSONObject object = new JSONObject(user);

            return new User(object);

        } catch (JSONException e) {

            return null;

        }

    }

    public void setUser(User user) {

        if (user != null) {

            this.setItem(USER_KEY, user.toJSON());

        } else {

            this.setItem(USER_KEY, null);

        }

    }

    public ArrayList<Refuel> getRefuels() {

        String refuels = this.getItem(REFUELS_KEY);

        ArrayList<Refuel> refuelsList = new ArrayList<Refuel>();

        try {

            JSONArray object = new JSONArray(refuels);

            for (int i = 0; i < object.length(); i++) {
                Refuel refuel = new Refuel(object.getJSONObject(i));
                refuelsList.add(refuel);
            }

        } catch (Exception e) { }

        if (refuels.equals("{}")) {

            return refuelsList;

        }

        return refuelsList;

    }

    public MutableLiveData<ArrayList<Refuel>> getReactiveRefuels() {

        return refuels;

    }

    public void setRefuels(ArrayList<Refuel> refuels) {

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < refuels.size(); i++) {

            jsonArray.put(refuels.get(i).toJSON());

        }

        this.refuels.setValue(refuels);

        this.setItem(REFUELS_KEY, jsonArray.toString());

    }

}
