package nl.gassapp.gassapp.DataModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Refuel {

    private Double liters;
    private Double price;
    private Double kilometers;

    private String picturePath;
    private String latitude;
    private String longitude;
    private static DecimalFormat df2 = new DecimalFormat(".##");


    public Refuel(JSONObject object) {

        try {

            this.liters = object.getDouble("liters");
            this.price = object.getDouble("price");
            this.kilometers = object.getDouble("kilometers");

        } catch (JSONException e) {

            //no code

        }

    }

    public Double getLiters() {
        return liters;
    }

    public Double getPrice() {
        return price;
    }

    public Double getKilometers() {
        return kilometers;
    }

    public String getPricePerKilometer() {
        df2.setRoundingMode(RoundingMode.UP);
        return df2.format(getPrice() / getKilometers());
    }
}
