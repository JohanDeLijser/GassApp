package nl.gassapp.gassapp.DataModels;

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
