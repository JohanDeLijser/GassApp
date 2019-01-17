package nl.gassapp.gassapp.DataModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Refuel {

    private float liters;
    private float price;
    private float kilometers;

    private String picturePath;
    private String latitude;
    private String longitude;

    public Refuel(JSONObject object) {

        try {

            this.liters = object.getInt("liters");
            this.price = object.getInt("price");
            this.kilometers = object.getInt("kilometers");

        } catch (JSONException e) {

            //no code

        }

    }
}
