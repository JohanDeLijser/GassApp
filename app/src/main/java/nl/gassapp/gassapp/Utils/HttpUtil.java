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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import nl.gassapp.gassapp.DataModel.User;
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

    public void loginRequest(User user, final RequestResponseListener<User> listener)
    {

        String url = API_URL + "/authenticate/user";

        Map<String, String> params = new HashMap<String, String>();

        params.put("email", user.getEmail());
        params.put("password", user.getPassword());

        JSONObject jsonParams = new JSONObject(params);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonParams,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        if(response != null) {

                            try {

                                JSONObject data = response.getJSONObject("data");

                                User responseUser = new User(
                                        data.getString("email"),
                                        data.getString("firstname"),
                                        data.getString("lastname"),
                                        data.getString("token"));

                                listener.getResult(responseUser);

                            } catch (JSONException e) {

                                listener.getError(0);

                            }

                        } else {

                            listener.getError(0);

                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        if (error.networkResponse != null)
                        {

                            listener.getError(error.networkResponse.statusCode);

                        } else {

                            listener.getError(0);

                        }

                    }
                }
        );

        requestQueue.add(request);

    }

    public void getRequest(final User user, final RequestResponseListener<JSONObject> listener)
    {

        String url = API_URL + "/user/get";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        if(response != null) {

                            listener.getResult(response);

                        } else {

                            listener.getError(0);

                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        if (error.networkResponse != null)
                        {
                            listener.getError(error.networkResponse.statusCode);
                        } else {

                            listener.getError(0);

                        }

                    }
                }
            ) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("authentication", user.getToken());

                    return params;

                }

        };

        requestQueue.add(request);
    }

}
