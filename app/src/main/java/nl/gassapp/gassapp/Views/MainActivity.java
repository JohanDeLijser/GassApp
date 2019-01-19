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
import nl.gassapp.gassapp.viewmodels.MainViewModel;


public class MainActivity extends AppCompatActivity {

    private Button logoutButton;
    private FloatingActionButton addButton;

    private final AddRefuelViewModel addRefuelViewModal = new AddRefuelViewModel();
    private ArrayList<Refuel> allRefuels;

    private MainViewModel mainViewModel;

    private RefuelListAdapter refuelListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mainViewModel = new MainViewModel();

        this.allRefuels = SharedPreferencesUtil.getInstance().getRefuels();

        initListeners();
        initRecyclerView();

        mainViewModel.fetchRefuels();

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

    private void initRecyclerView() {

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.refuelRecyclerView);

        refuelListAdapter = new RefuelListAdapter(this, allRefuels);
        mRecyclerView.setAdapter(refuelListAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    private void initListeners() {

        mainViewModel.getRefuels().observe(this, this::updateRefuelObject);

    }

    private void updateRefuelObject(ArrayList<Refuel> refuels) {

        allRefuels.clear();
        allRefuels.addAll(refuels);
        refuelListAdapter.notifyDataSetChanged();

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
