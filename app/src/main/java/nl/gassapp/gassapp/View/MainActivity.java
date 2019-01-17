package nl.gassapp.gassapp.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

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

    private Button showMonthGraphButton;
    private Button showYearGraphButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instance http util
        HttpUtil.getInstance(this);
        SharedPreferencesUtil.getInstance(this);

        setContentView(R.layout.activity_main);

        //Load the login when no user is loaded
        //TODO: validate if the token is still valid
        if (SharedPreferencesUtil.getInstance().getUser() == null) {

            openLoginActivity();

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

        refuelViewModal.getTrips();

        //Month tab
        showMonthGraphButton = (Button) findViewById(R.id.showMonthGraphButton);
        handleShowMonthGraphButton(showMonthGraphButton);

        //Year tab
        showYearGraphButton = (Button) findViewById(R.id.showYearGraphButton);
        handleYearGraphButton(showYearGraphButton);
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
     * Handles onclick for specific button
     * @param showMonthGraphButton
     */
    private void handleShowMonthGraphButton(Button showMonthGraphButton) {
        showMonthGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMonthGraphActivity();
            }
        });
    }

    /**
     * Handles onclick for specific button
     * @param showYearGraphButton
     */
    private void handleYearGraphButton(Button showYearGraphButton) {
        showYearGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYearGraphActivity();
            }
        });
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

    /**
     * Opens the month graph activity
     */
    private void openMonthGraphActivity() {
        Intent graphIntent = new Intent(this, GraphActivity.class);
        startActivity(graphIntent);
    }

    /**
     * Opens the year graph activity
     */
    private void openYearGraphActivity() {
        Intent graphIntent = new Intent(this, GraphActivity.class);
        startActivity(graphIntent);
    }

}
