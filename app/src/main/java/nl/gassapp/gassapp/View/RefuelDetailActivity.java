package nl.gassapp.gassapp.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import nl.gassapp.gassapp.R;

public class RefuelDetailActivity extends AppCompatActivity {

    private Button backButton;
    private Button addButton;

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

        backButton = (Button) findViewById(R.id.backButton);
        addButton = (Button) findViewById(R.id.addButton);

        price = (TextView) findViewById(R.id.price);
        liters = (TextView) findViewById(R.id.liters);
        kilometers = (TextView) findViewById(R.id.kilometers);
        litersPKm = (TextView) findViewById(R.id.litersPKm);
        pricePKm = (TextView) findViewById(R.id.pricePKm);

        editRefuel = (Button) findViewById(R.id.editRefuel);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainView();
            }
        });

        editRefuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRefuelView();
            }
        });

        Bundle revievedParams = getIntent().getExtras();

        System.out.println(revievedParams.getInt("position"));
    }

    private void openMainView() {
        Intent mainViewIntent = new Intent(this, MainActivity.class);
        startActivity(mainViewIntent);
    }

    private void editRefuelView() {
        Intent editViewIntent = new Intent(this, EditRefuelActivity.class);
        startActivity(editViewIntent);
    }
}
