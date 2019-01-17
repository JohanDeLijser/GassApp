package nl.gassapp.gassapp.DataModel;

import java.util.ArrayList;

public class Refuels {

    private ArrayList<Refuel> refuels;

    public Refuels() {

    }

    public void addRefuels(Refuel refuel) {
        this.refuels.add(refuel);
    }

    public ArrayList<Refuel> getRefuels() {
        return refuels;
    }
}
