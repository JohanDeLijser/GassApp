package nl.gassapp.gassapp.Listeners;

import org.json.JSONObject;

import nl.gassapp.gassapp.DataModels.NetworkError;

public interface RequestResponseListener<T> {

    public void getResult(T object);

    public void getError(NetworkError error);

}
