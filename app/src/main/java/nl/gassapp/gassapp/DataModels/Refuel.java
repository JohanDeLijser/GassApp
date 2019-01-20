package nl.gassapp.gassapp.DataModels;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *  A Refuel Model
 *
 *  A Refuel model contains a refuel trip
 *  a refuel model has only one optional parameter that is picture path
 *  a refuel model does not contain a id when not created because this is the id of the refuel in the api
 */
public class Refuel {

    private Integer id;

    protected Double liters;
    protected Double price;
    protected Double kilometers;
    protected String picturePath;

    private static DecimalFormat df2 = new DecimalFormat(".##");

    public Refuel() {
    }

    public Refuel(JSONObject object) {

        try {

            this.id = object.getInt("_id");
            this.liters = Double.parseDouble(object.getString("liters"));
            this.price = Double.parseDouble(object.getString("price"));
            this.kilometers =  Double.parseDouble(object.getString("kilometers"));
            this.picturePath = object.getString("picturePath");

        } catch (JSONException e) {

            //A empty object will be created when the JSONObject is invalid

        }

    }

    public Integer getId() {
        return id;
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

    public String getPicturePath() {
        return picturePath;
    }

    /**
     * Calculation of the price per kilometer
     *
     * Price / Kilometers = price per kilometer
     *
     * @return String
     */
    public String getPricePerKilometer() {
        df2.setRoundingMode(RoundingMode.UP);
        return df2.format(getPrice() / getKilometers());
    }

    /**
     * Calculation of the liters per kilometer
     *
     * kilometers / liters = liters used in a kilometer
     *
     * @return String
     */
    public String getLitersPerKilometers() {
        df2.setRoundingMode(RoundingMode.UP);
        return df2.format(getKilometers() / getLiters());
    }

    public JSONObject toJSON(){

        JSONObject jsonObject= new JSONObject();

        try {
            jsonObject.put("_id", getId());
            jsonObject.put("liters", getLiters());
            jsonObject.put("price", getPrice());
            jsonObject.put("kilometers", getKilometers());
            jsonObject.put("picturePath", getPicturePath());

            return jsonObject;
        } catch (JSONException e) {
            return jsonObject;
        }
    }
}
