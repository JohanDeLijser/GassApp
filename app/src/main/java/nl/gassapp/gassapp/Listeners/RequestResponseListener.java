package nl.gassapp.gassapp.Listeners;

import nl.gassapp.gassapp.DataModels.NetworkError;

/**
 *
 * The RequestResponseListner is used as a Callback for all httputil requests
 *
 * @param <T>
 */
public interface RequestResponseListener<T> {

    public void getResult(T object);

    public void getError(NetworkError error);

}
