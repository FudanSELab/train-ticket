package preserveother.entity;

/**
 * @author fdu
 */
public class NotifyInfo {

    private String email;
    private String orderNumber;
    private String username;
    private String startingPlace;
    private String endPlace;
    private String startingTime;
    private String date;
    private String seatClass;
    private String seatNumber;
    private String price;

    public NotifyInfo() {
        //default constructor
    }

    public NotifyInfo(String email, String orderNumber, String username, String startingPlace, String endPlace, String startingTime, String date, String seatClass, String seatNumber, String price) {
        this.email = email;
        this.orderNumber = orderNumber;
        this.username = username;
        this.startingPlace = startingPlace;
        this.endPlace = endPlace;
        this.startingTime = startingTime;
        this.date = date;
        this.seatClass = seatClass;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStartingPlace() {
        return startingPlace;
    }

    public void setStartingPlace(String startingPlace) {
        this.startingPlace = startingPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }
}
