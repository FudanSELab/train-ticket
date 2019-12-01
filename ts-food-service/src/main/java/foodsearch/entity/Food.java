package foodsearch.entity;


import java.io.Serializable;


public class Food implements Serializable{

    public Food(){
        //do nothing
    }

//    @Id

    private String foodName;
    private double price;




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
