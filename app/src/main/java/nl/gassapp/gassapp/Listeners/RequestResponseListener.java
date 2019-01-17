package nl.gassapp.gassapp.Listeners;

import org.json.JSONObject;

public interface RequestResponseListener<T> {

    public void getResult(T object);

    public void getError(int error);

}
