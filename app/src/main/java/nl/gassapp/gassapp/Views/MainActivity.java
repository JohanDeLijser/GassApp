package nl.gassapp.gassapp.Views;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import java.util.ArrayList;

import nl.gassapp.gassapp.Adapters.RefuelListAdapter;
import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;
import nl.gassapp.gassapp.viewmodels.AddRefuelViewModel;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button logoutButton;
    private FloatingActionButton addButton;

    private final AddRefuelViewModel addRefuelViewModal = new AddRefuelViewModel();
    private ArrayList<Refuel> allRefuels;

    private LinearLayout singleRefuelContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.refuelRecyclerView);

        ArrayList<Refuel> refuels = new ArrayList<>();

        Refuel refuel = new Refuel(12.0, 19.8, 8.6);

        refuels.add(refuel);

        refuel = new Refuel(15.0, 19.9, 8.6);

        refuels.add(refuel);

        RefuelListAdapter refuelListAdapter = new RefuelListAdapter(this, refuels);
        mRecyclerView.setAdapter(refuelListAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Load the login when no user is loaded
        //TODO: validate if the token is still valid


        logoutButton = (Button) findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.getInstance().setUser(null);
                openLoginActivity();
            }
        });

        addButton = findViewById(R.id.addButton);
        handleAddButton(addButton);
    }
    /**
     * Handles onclick for specific button
     * @param addButton
     */
    private void handleAddButton(FloatingActionButton addButton) {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddRefuelActivity();
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

}
