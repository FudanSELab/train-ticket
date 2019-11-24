package travelto.entity;

import java.util.Map;

/**
 * TravelResult class
 *
 * @author fdu
 * @date 2019/11/10
 */
public class TravelResult {

    private boolean status;

    private double percent;

    private TrainType trainType;

    private Map<String, String> prices;

    public TravelResult() {
        //Default Constructor
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public TrainType getTrainType() {
        return trainType;
    }

    public void setTrainType(TrainType trainType) {
        this.trainType = trainType;
    }

    public Map<String, String> getPrices() {
        return prices;
    }

    public void setPrices(Map<String, String> prices) {
        this.prices = prices;
    }
}
