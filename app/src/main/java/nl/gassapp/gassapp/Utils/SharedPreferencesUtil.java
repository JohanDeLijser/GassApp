package nl.gassapp.gassapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import nl.gassapp.gassapp.DataModels.User;

public class SharedPreferencesUtil {

    private SharedPreferences sharedPreferences;

    private static SharedPreferencesUtil instance = null;

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

        String user = this.getItem("user");

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

            this.setItem("user", user.toJSON());

        } else {

            this.setItem("user", null);

        }

    }

}
