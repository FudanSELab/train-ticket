package preserveother.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author fdu
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    private UUID id;

    private Date boughtDate;

    private Date travelDate;

    private Date travelTime;

    /**
     * Which Account Bought it
     */
    private UUID accountId;

    /**
     * Tickets bought for whom....
     */
    private String contactsName;

    private int documentType;

    private String contactsDocumentNumber;

    private String trainNumber;

    private int coachNumber;

    private int seatClass;

    private String seatNumber;

    private String from;

    private String to;

    private int status;

    private String price;

    public Order() {
        boughtDate = new Date(System.currentTimeMillis());
        travelDate = new Date(123456789);
        trainNumber = "G1235";
        coachNumber = 5;
        seatClass = SeatClass.FIRSTCLASS.getCode();
        seatNumber = "5A";
        from = "shanghai";
        to = "taiyuan";
        status = OrderStatus.PAID.getCode();
        price = "0.0";
    }

    public Order(UUID id, Date boughtDate, Date travelDate, Date travelTime, UUID accountId, String contactsName,
                 int documentType, String contactsDocumentNumber, String trainNumber, int coachNumber,
                 int seatClass, String seatNumber, String from, String to, int status, String price) {
        this.id = id;
        this.boughtDate = boughtDate;
        this.travelDate = travelDate;
        this.travelTime = travelTime;
        this.accountId = accountId;
        this.contactsName = contactsName;
        this.documentType = documentType;
        this.contactsDocumentNumber = contactsDocumentNumber;
        this.trainNumber = trainNumber;
        this.coachNumber = coachNumber;
        this.seatClass = seatClass;
        this.seatNumber = seatNumber;
        this.from = from;
        this.to = to;
        this.status = status;
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Order other = (Order) obj;
        return boughtDate.equals(other.getBoughtDate())
                && travelDate.equals(other.getTravelDate())
                && travelTime.equals(other.getTravelTime())
                && accountId.equals(other.getAccountId())
                && contactsName.equals(other.getContactsName())
                && contactsDocumentNumber.equals(other.getContactsDocumentNumber())
                && documentType == other.getDocumentType()
                && trainNumber.equals(other.getTrainNumber())
                && coachNumber == other.getCoachNumber()
                && seatClass == other.getSeatClass()
                && seatNumber.equals(other.getSeatNumber())
                && from.equals(other.getFrom())
                && to.equals(other.getTo())
                && status == other.getStatus()
                && price.equals(other.price);
    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(boughtDate);
        sb.append(travelDate);
        sb.append(accountId);
        sb.append(contactsName);
        sb.append(contactsDocumentNumber);
        sb.append(documentType);
        sb.append(trainNumber);
        sb.append(coachNumber);
        sb.append(seatClass);
        sb.append(seatNumber);
        sb.append(from);
        sb.append(to);
        sb.append(status);
        sb.append(price);
        char[] charArr = sb.toString().toCharArray();
        int hash = 0;

        for (char c : charArr) {
            hash = hash * 131 + c;
        }
        return hash;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public Date getBoughtDate() {
        return boughtDate;
    }

    public void setBoughtDate(Date boughtDate) {
        this.boughtDate = boughtDate;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public void setTravelDate(int year, int month, int day) {
        Calendar cld = Calendar.getInstance();
        cld.set(year, month - 1, day, 0, 0, 0);
        this.travelDate = cld.getTime();
    }

    public Date getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Date travelTime) {
        this.travelTime = travelTime;
    }

    public void setTravelTime(int hour, int minute) {
        Calendar cld = Calendar.getInstance();
        cld.set(1970, 0, 1, hour, minute, 0);
        this.travelTime = cld.getTime();
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getCoachNumber() {
        return coachNumber;
    }

    public void setCoachNumber(int coachNumber) {
        this.coachNumber = coachNumber;
    }

    public int getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(int seatClass) {
        this.seatClass = seatClass;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getContactsDocumentNumber() {
        return contactsDocumentNumber;
    }

    public void setContactsDocumentNumber(String contactsDocumentNumber) {
        this.contactsDocumentNumber = contactsDocumentNumber;
    }
}
