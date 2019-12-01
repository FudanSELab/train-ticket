package food.entity;


import java.io.Serializable;

public class Food implements Serializable{

    private String foodName;
    private double price;
    public Food(){
		// do nothing
    }
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
