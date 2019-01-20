package nl.gassapp.gassapp.DataModels;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;

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

            //no code

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

    public String getPricePerKilometer() {
        df2.setRoundingMode(RoundingMode.UP);
        return df2.format(getPrice() / getKilometers());
    }

    public String getLitersPerKilometers() {
        df2.setRoundingMode(RoundingMode.UP);
        return df2.format(getLiters() / getKilometers());
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
