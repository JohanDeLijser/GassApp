package nl.gassapp.gassapp.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import nl.gassapp.gassapp.DataModel.Refuel;
import nl.gassapp.gassapp.DataModel.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;
import nl.gassapp.gassapp.ViewModel.RefuelViewModal;


public class MainActivity extends AppCompatActivity {

    private Button logoutButton;
    private Button addButton;

    private Button showImageAndLocationButton;
    private Button editButton;
    private Button deleteButton;
    private final RefuelViewModal refuelViewModal = new RefuelViewModal();
    private ArrayList<Refuel> allRefuels;

    private LinearLayout singleRefuelContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instance http util
        HttpUtil.getInstance(this);
        SharedPreferencesUtil.getInstance(this);

        setContentView(R.layout.activity_main);

        //Load the login when no user is loaded
        //TODO: validate if the token is still valid

        final User user = SharedPreferencesUtil.getInstance().getUser();

        if (user == null) {

            openLoginActivity();

        } else {

          HttpUtil.getInstance().getUser(user, new RequestResponseListener<User>() {

                @Override
                public void getResult(User object) {

                    if (!user.getEmail().equals(object.getEmail())) {

                        SharedPreferencesUtil.getInstance().setUser(null);
                        openLoginActivity();

                    } else {

                        SharedPreferencesUtil.getInstance().setUser(null);

                        openLoginActivity();

                    }

                }

                @Override
                public void getError(int error) {

                    SharedPreferencesUtil.getInstance().setUser(null);

                    openLoginActivity();

                }

            });
          
          
            refuelViewModal.getTrips(
                            new RequestResponseListener<Boolean>() {

                                @Override
                                public void getResult(Boolean bool) {
                                    if (bool) {
                                        setSingleRefuelFields(refuelViewModal.getAllTrips());
                                    }
                                }

                                @Override
                                public void getError(int errorCode) {
                                    System.out.println(errorCode);
                                }

                            }
                    );
          
            

        }

        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        initTabs(tabHost);

        logoutButton = (Button) findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.getInstance().setUser(null);
                openLoginActivity();
            }
        });

        addButton = (Button) findViewById(R.id.addButton);
        handleAddButton(addButton);


        //Single tab
        showImageAndLocationButton = (Button) findViewById(R.id.showImageAndLocationButton);
        handleShowImageAndLocationButton(showImageAndLocationButton);

        editButton = (Button) findViewById(R.id.editButton);
        handleEditButton(editButton);

        deleteButton = (Button) findViewById(R.id.deleteButton);
        handleDeleteButton(deleteButton);
    }

    /**
     * Initializes tabs and their content
     * @param tabHost
     */
    private static void initTabs(TabHost tabHost) {
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("Single");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Single");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Month");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Month");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Year");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Year");
        tabHost.addTab(spec);
    }

    /**
     * Handles onclick for specific button
     * @param addButton
     */
    private void handleAddButton(Button addButton) {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddRefuelActivity();
            }
        });
    }

    private void handleShowImageAndLocationButton(Button showImageAndLocationButton) {
        showImageAndLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageAndLocationActivity();
            }
        });
    }

    private void handleEditButton(Button editButton) {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditActivity();
            }
        });
    }

    private void handleDeleteButton(Button deleteButton) {

    }
    /**
     * Opens the login screen
     */
    private void openLoginActivity() {

        Intent openLoginIntent = new Intent(this, LoginActivity.class);
        openLoginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openLoginIntent);

    }

    /**
     * Opens the add refuel activity
     */
    private void openAddRefuelActivity() {
        Intent addRefuelIntent = new Intent(this, AddRefuelActivity.class);
        startActivity(addRefuelIntent);
    }

    /**
     * Opens the image and location activity
     */
    private void openImageAndLocationActivity() {
        Intent imageAndLoctionIntent = new Intent(this, ImageAndLocationActivity.class);
        startActivity(imageAndLoctionIntent);
    }

    /**
     * Opens the image and location activity
     */
    private void openEditActivity() {
        Intent editIntent = new Intent(this, EditRefuelActivity.class);
        startActivity(editIntent);
    }

    private void setSingleRefuelFields(ArrayList<Refuel> refuels) {
        singleRefuelContent = (LinearLayout) findViewById(R.id.singleRefuelContent);

        if (!refuels.isEmpty()) {

            for (Refuel refuel : refuels) {

                // init all layouts and fields
                LinearLayout refuelLayout = new LinearLayout(this);

                LinearLayout litersRow = new LinearLayout(this);
                litersRow.setOrientation(LinearLayout.HORIZONTAL);
                TextView refuelLitersTitle = new TextView(this);
                TextView refuelLiters = new TextView(this);

                LinearLayout priceRow = new LinearLayout(this);
                priceRow.setOrientation(LinearLayout.HORIZONTAL);
                TextView refuelPriceTitle = new TextView(this);
                TextView refuelPrice = new TextView(this);

                LinearLayout kilometersRow = new LinearLayout(this);
                kilometersRow.setOrientation(LinearLayout.HORIZONTAL);
                TextView refuelKilometersTitle = new TextView(this);
                TextView refuelKilometers = new TextView(this);

                LinearLayout priceKmRow = new LinearLayout(this);
                priceKmRow.setOrientation(LinearLayout.HORIZONTAL);
                TextView refuelPriceKmTitle = new TextView(this);
                TextView refuelPriceKm = new TextView(this);

                // set values of above initialized fields
                refuelLitersTitle.setText("Liters: ");
                refuelPriceTitle.setText("Price: ");
                refuelKilometersTitle.setText("Kilometers: ");
                refuelPriceKmTitle.setText("Price p/km: ");

                refuelLiters.setText(refuel.getLiters().toString() + "L");
                refuelPrice.setText("€" + refuel.getPrice().toString());
                refuelKilometers.setText(refuel.getKilometers().toString() + "km");
                refuelPriceKmTitle.setText("€" + refuel.getPricePerKilometer());

                litersRow.addView(refuelLitersTitle);
                litersRow.addView(refuelLiters);

                priceRow.addView(refuelPriceTitle);
                priceRow.addView(refuelPrice);

                kilometersRow.addView(refuelKilometersTitle);
                kilometersRow.addView(refuelKilometers);

                priceKmRow.addView(refuelPriceKmTitle);
                priceKmRow.addView(refuelPriceKm);

                refuelLayout.setOrientation(LinearLayout.VERTICAL);

                refuelLayout.addView(litersRow);
                refuelLayout.addView(priceRow);
                refuelLayout.addView(kilometersRow);
                refuelLayout.addView(priceKmRow);

                singleRefuelContent.addView(refuelLayout);
            }
        }
    }

    private void setMonthRefuelFields(ArrayList<Refuel> refuels) {

    }
}
