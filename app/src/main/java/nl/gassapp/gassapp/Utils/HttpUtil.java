package nl.gassapp.gassapp.Utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.DataModels.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;

public class HttpUtil {

    private RequestQueue requestQueue;

    private final String API_URL = "https://api.gassapp.nl";

    private static HttpUtil instance = null;

    private HttpUtil(Context context)
    {

        requestQueue = Volley.newRequestQueue(context.getApplicationContext());

    }

    public static synchronized HttpUtil getInstance(Context context)
    {

        if (instance == null) {

            instance = new HttpUtil(context);

        }

        return instance;

    }

    //this is so you don't need to pass context each time
    public static synchronized HttpUtil getInstance()
    {
        if (instance == null)
        {
            throw new IllegalStateException(HttpUtil.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    private void request(
            int method,
            String url,
            final Map<String, String> headers,
            Response.Listener<JSONObject> okCallback,
            final RequestResponseListener listener
    ) {

        this.request(method, url, null, headers, okCallback, listener);

    }

    private void request(
            int method,
            String url,
            JSONObject params,
            final Map<String, String> headers,
            Response.Listener<JSONObject> okCallback,
            final RequestResponseListener listener
    ) {

        JsonObjectRequest request = new JsonObjectRequest(method, url, params,
            okCallback,
            (VolleyError error) ->
                {

                    if (error.networkResponse != null)
                    {

                        listener.getError(error.networkResponse.statusCode);

                    } else {

                        listener.getError(0);

                    }

                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return headers;

            }

        };

        requestQueue.add(request);

    }

    public void authenticateUser(User user, final RequestResponseListener<User> listener)
    {

        String url = API_URL + "/authenticate/user";

        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();

        params.put("email", user.getEmail());
        params.put("password", user.getPassword());

        JSONObject jsonParams = new JSONObject(params);

        this.request(Request.Method.POST, url, jsonParams, headers, (JSONObject response) ->
            {

                if(response != null) {

                    try {

                        JSONObject data = response.getJSONObject("data");

                        User responseUser = new User(data);

                        listener.getResult(responseUser);

                    } catch (JSONException e) {

                        listener.getError(0);

                    }

                } else {

                    listener.getError(0);

                }

            }, listener);

    }

    public void registerUser(User user, final RequestResponseListener<JSONObject> listener)
    {

        String url = API_URL + "/user/create";

        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();

        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("firstname", user.getFirstname());
        params.put("lastname", user.getLastname());

        JSONObject jsonParams = new JSONObject(params);

        this.request(Request.Method.POST, url, jsonParams, headers,(JSONObject response) ->
            {

                if(response != null) {

                    try {

                        JSONObject jUser = response.getJSONObject("data");

                        listener.getResult(jUser);

                    } catch (JSONException e) {

                        listener.getError(0);

                    }

                } else {

                    listener.getError(0);

                }

            }, listener);

    }

    public void addRefuel(Refuel refuel, final RequestResponseListener<JSONObject> listener)
    {
        String url = API_URL + "/refuel/create";

        Map<String, String> headers = new HashMap<String, String>();
        Map<String, Object> params = new HashMap<String, Object>();
        headers.put("authentication", SharedPreferencesUtil.getInstance().getUser().getToken());

        params.put("liters", refuel.getLiters());
        params.put("price", refuel.getPrice());
        params.put("kilometers", refuel.getKilometers());
        params.put("picture", refuel.getPicturePath());

        JSONObject jsonParams = new JSONObject(params);

        System.out.println(jsonParams);

        this.request(Request.Method.POST, url, jsonParams, headers, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {

                if(response != null) {

                    try {

                        JSONObject user = response.getJSONObject("data");

                        listener.getResult(user);

                    } catch (JSONException e) {

                        listener.getError(0);

                    }

                } else {

                    listener.getError(0);

                }

            }
        }, listener);

    }

    public void getUser(final User user, final RequestResponseListener<User> listener)
    {

        String url = API_URL + "/user/get";


        Map<String, String> headers = new HashMap<String, String>();
        headers.put("authentication", user.getToken());

        this.request(Request.Method.GET, url, headers, (JSONObject response) ->
            {

                if(response != null) {

                    try {

                        User rUser = new User(response.getJSONObject("data"));
                        listener.getResult(rUser);

                    } catch (JSONException e) {

                        listener.getResult(null);

                    }

                } else {

                    listener.getError(0);

                }

            }, listener);
    }

    public void getRefuels(final User user, final RequestResponseListener<ArrayList<Refuel>> listener)
    {

        String url = API_URL + "/refuel/get";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("authentication", user.getToken());

        this.request(Request.Method.GET, url, headers, (JSONObject response) ->
            {

                if(response != null) {

                    ArrayList<Refuel> refuels = new ArrayList<Refuel>();

                    try {

                        if (response.getJSONArray("data").length() > 0) {

                            for (int i = 0; i < response.getJSONArray("data").length(); i++) {

                                Refuel refuel = new Refuel(response.getJSONArray("data").getJSONObject(i));
                                refuels.add(refuel);

                            }

                        }

                    } catch (JSONException e) {

                        //EXCEPTION TODO

                    }

                    listener.getResult(refuels);

                } else {

                    listener.getError(0);

                }

            },
        listener);

    }

}
