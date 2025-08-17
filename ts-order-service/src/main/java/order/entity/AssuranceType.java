package order.entity;

import java.io.Serializable;

/**
 * Insurance type enumeration.
 */
public enum AssuranceType implements Serializable {
    /**
     * Traffic Accident Assurance
     */
    TRAFFIC_ACCIDENT(1, "Traffic Accident Assurance", 3.0);

    private int index;
    private String name;
    private double price;

    AssuranceType(int index, String name, double price) {
        this.index = index;
        this.name = name;
        this.price = price;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Helper to get type by index.
     */
    public static AssuranceType getTypeByIndex(int index) {
        for (AssuranceType at : AssuranceType.values()) {
            if (at.index == index) {
                return at;
            }
        }
        return null;
    }
}
