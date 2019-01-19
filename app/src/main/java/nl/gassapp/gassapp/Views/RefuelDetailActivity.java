package nl.gassapp.gassapp.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Ref;
import java.util.ArrayList;

import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;

public class RefuelDetailActivity extends AppCompatActivity {

    private ImageView image;
    private TextView price;
    private TextView liters;
    private TextView kilometers;
    private TextView litersPKm;
    private TextView pricePKm;

    private Button editRefuel;
    private Button deleteRefuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refuel_detail);

        price = (TextView) findViewById(R.id.price);
        liters = (TextView) findViewById(R.id.liters);
        kilometers = (TextView) findViewById(R.id.kilometers);
        litersPKm = (TextView) findViewById(R.id.litersPKm);
        pricePKm = (TextView) findViewById(R.id.pricePKm);

        editRefuel = (Button) findViewById(R.id.editRefuel);

        editRefuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRefuelView();
            }
        });

        Bundle revievedParams = getIntent().getExtras();

        ArrayList<Refuel> refuels = SharedPreferencesUtil.getInstance().getRefuels();

        Integer position = revievedParams.getInt("position");

        Refuel refuel = refuels.get(position);


        price.setText(Double.toString(refuel.getPrice()));

        liters.setText(Double.toString(refuel.getLiters()));

        kilometers.setText(Double.toString(refuel.getKilometers()));

        litersPKm.setText(refuel.getLitersPerKilometers());
        pricePKm.setText(refuel.getPricePerKilometer());

    }

    private void editRefuelView() {
        Intent editViewIntent = new Intent(this, EditRefuelActivity.class);
        startActivity(editViewIntent);
    }

}
