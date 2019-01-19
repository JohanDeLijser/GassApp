package nl.gassapp.gassapp.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;

import java.util.ArrayList;

import nl.gassapp.gassapp.Adapters.RefuelListAdapter;
import nl.gassapp.gassapp.DataModels.Refuel;
import nl.gassapp.gassapp.DataModels.User;
import nl.gassapp.gassapp.Listeners.RequestResponseListener;
import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.Utils.HttpUtil;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;
import nl.gassapp.gassapp.viewmodels.AddRefuelViewModal;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button logoutButton;
    private Button addButton;

    private final AddRefuelViewModal addRefuelViewModal = new AddRefuelViewModal();
    private ArrayList<Refuel> allRefuels;

    private LinearLayout singleRefuelContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instance http util
        HttpUtil.getInstance(this);

        //Instance shared preferences util
        SharedPreferencesUtil.getInstance(this);

        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.refuelRecyclerView);

        ArrayList<Refuel> refuels = new ArrayList<>();

        Refuel refuel = new Refuel(12.0, 19.8, 8.6);

        refuels.add(refuel);

        RefuelListAdapter refuelListAdapter = new RefuelListAdapter(this, refuels);
        mRecyclerView.setAdapter(refuelListAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

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

                    }

                }

                @Override
                public void getError(int error) {

                    SharedPreferencesUtil.getInstance().setUser(null);

                    openLoginActivity();

                }

            });
          
            

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
