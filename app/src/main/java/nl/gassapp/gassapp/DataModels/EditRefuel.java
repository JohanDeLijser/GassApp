package nl.gassapp.gassapp.DataModels;

/**
 * A EditRefuel Modal
 *
 * This modal is for some setters that are needed during the creation of a refuel
 *
 * After the processes are complete and verified by the api there will be a refuel object created.
 * so the setters are not available at verified refuel objects
 *
 */
public class EditRefuel extends Refuel {

    private String image;

    public void setLiters(Double liters) {
        this.liters = liters;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setKilometers(Double kilometers) {
        this.kilometers = kilometers;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
