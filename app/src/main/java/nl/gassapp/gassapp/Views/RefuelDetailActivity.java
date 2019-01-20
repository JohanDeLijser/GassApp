package nl.gassapp.gassapp.Views;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import java.io.InputStream;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import nl.gassapp.gassapp.DataModels.NetworkError;
import nl.gassapp.gassapp.R;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import nl.gassapp.gassapp.DataModels.Refuel;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;
import nl.gassapp.gassapp.viewmodels.RefuelDetailViewModel;

public class RefuelDetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView price;
    private TextView liters;
    private TextView kilometers;
    private TextView litersPKm;
    private TextView pricePKm;

    private Button deleteRefuel;

    private RefuelDetailViewModel refuelDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refuel_detail);

        price = (TextView) findViewById(R.id.price);
        liters = (TextView) findViewById(R.id.liters);
        kilometers = (TextView) findViewById(R.id.kilometers);
        litersPKm = (TextView) findViewById(R.id.litersPKm);
        pricePKm = (TextView) findViewById(R.id.pricePKm);

        imageView = (ImageView) findViewById(R.id.imageView);

        /*

            Gets the parameters this contains the position of the selected item
            in the refuel list

         */
        Bundle revievedParams = getIntent().getExtras();

        ArrayList<Refuel> refuels = SharedPreferencesUtil.getInstance().getRefuels();

        Integer position = revievedParams.getInt("position");

        //Create a object
        Refuel refuel = refuels.get(position);


        /*

            Sets the properties

         */

        price.setText("€" + Double.toString(refuel.getPrice()));

        liters.setText(Double.toString(refuel.getLiters()) + "L");

        kilometers.setText(Double.toString(refuel.getKilometers()) + "km");

        litersPKm.setText("1/" + refuel.getLitersPerKilometers());
        pricePKm.setText("€0" + refuel.getPricePerKilometer());

        refuelDetailViewModel = new RefuelDetailViewModel();

        //Download the refuel image
        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute(refuel.getPicturePath() + "?token=" + SharedPreferencesUtil.getInstance().getUser().getToken());

        deleteRefuel = (Button) findViewById(R.id.deleteRefuel);

        //Show a dialog to confirm the users action on deleting the refuel

        deleteRefuel.setOnClickListener((View v) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Delete refuel");
            builder.setMessage("Are you sure to delete this refuel ?");
            builder.setPositiveButton("Confirm",
                    (DialogInterface dialog, int which) -> {
                        refuelDetailViewModel.deleteRefuel(refuel);
                    }
            );
            builder.setNegativeButton(android.R.string.cancel, (DialogInterface dialog, int which) -> {
                    System.out.println("cancel");
                }
            );
            AlertDialog dialog = builder.create();
            dialog.show();

        });

        setUpListners();

    }

    private void setUpListners() {

        refuelDetailViewModel.getLoadingState().observe(this, this::onLoading);
        refuelDetailViewModel.getReturnMessage().observe(this, this::onResponseMessage);

    }

    private void onResponseMessage(NetworkError networkError) {

        if (networkError.getCode().equals(NetworkError.OK)) {

            Toasty.success(
                    this,
                    networkError.getMessage(),
                    Toast.LENGTH_SHORT,
                    true)
                    .show();

            openMainActivity();

        } else {

            Toasty.error(
                    this,
                    networkError.getMessage(),
                    Toast.LENGTH_SHORT,
                    true)
                    .show();

        }

    }

    private void onLoading(Boolean loading) {

        if (deleteRefuel == null) {

            deleteRefuel = findViewById(R.id.deleteRefuel);

        }

        deleteRefuel.setEnabled(!loading);

    }

    private void openMainActivity() {

        Intent openMain = new Intent(this, MainActivity.class);
        openMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openMain);

    }

}

/**
 *
 * Downloads the image from the url and converts it to a bitmap
 *
 */
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}