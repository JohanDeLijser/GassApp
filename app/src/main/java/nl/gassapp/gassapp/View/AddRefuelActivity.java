package nl.gassapp.gassapp.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nl.gassapp.gassapp.R;
import nl.gassapp.gassapp.ViewModel.RefuelViewModal;

public class AddRefuelActivity extends AppCompatActivity {

    private final RefuelViewModal refuelViewModal = new RefuelViewModal();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_refuel);

        refuelViewModal.getTrips();

    }

    @Override
    public void onDetachedFromWindow() {

        super.onDetachedFromWindow();

    }

}
