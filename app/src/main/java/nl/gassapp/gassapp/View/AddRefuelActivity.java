package nl.gassapp.gassapp.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import nl.gassapp.gassapp.R;

public class AddRefuelActivity extends AppCompatActivity {
    
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button backButton;
    private Button addImage;
    private ImageView refuelImageThumbnail;
    private Button saveRefuel;
    private String base64Image;
    private Double kilometersDriven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_refuel);

        backButton = (Button) findViewById(R.id.backButton);
        addImage = (Button) findViewById(R.id.addImage);
        refuelImageThumbnail = (ImageView) findViewById(R.id.refuelImageThumbnail);
        saveRefuel = (Button) findViewById(R.id.saveRefuel);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainView();
            }
        });


        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakePictureIntent();
                }
            });
        } else {
            System.out.println("No usable camera");
        }

        saveRefuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCurrentRefuel();
            }
        });
    }

    private void openMainView() {
        Intent mainViewIntent = new Intent(this, MainActivity.class);
        startActivity(mainViewIntent);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            refuelImageThumbnail.setImageBitmap(imageBitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }

    private void saveCurrentRefuel() {
        EditText kilometers = (EditText) findViewById(R.id.kilometers);
        kilometersDriven = Double.parseDouble(kilometers.getText().toString());

        if (kilometersDriven != 0 && !base64Image.isEmpty()) {
            System.out.println(kilometersDriven);
            System.out.println("Image is set");
        } else {
            System.out.println("Not all fields were filled in!");
        }
    }
}
