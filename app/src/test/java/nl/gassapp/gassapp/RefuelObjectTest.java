package nl.gassapp.gassapp;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.DataModels.User;

import static org.junit.Assert.*;

public class RefuelObjectTest {

    private Integer id = 1;
    private String liters = "12.00";
    private String kilometers = "25.50";
    private String price = "12.25";

    @Test
    public void testJsonObjectToRefuelObject() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("_id", id);
            jsonObject.put("liters", liters);
            jsonObject.put("kilometers", kilometers);
            jsonObject.put("price", price);


        } catch (JSONException e) {

           fail();

        }

        Refuel refuel = new Refuel(jsonObject);

        assertEquals(refuel.getId(), id);
//        assertEquals(refuel.getLiters(), liters);
//        assertEquals(refuel.getKilometers(), kilometers);
//        assertEquals(refuel.getPrice(), price);



    }

    @Test
    public void testRefuelObjectToJsonObject() {


    }

}
