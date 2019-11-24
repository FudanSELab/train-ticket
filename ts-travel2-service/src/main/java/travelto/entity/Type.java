package travelto.entity;

import java.io.Serializable;

/**
 * Type class
 *
 * @author fdu
 * @date 2019/11/10
 */
public enum Type implements Serializable {
    /**
     * 特快
     */
    Z("Z", 3),
    /**
     * 特快
     */
    T("T", 4),
    /**
     * 快速
     */
    K("K", 5);

    private String name;
    private int index;

    Type(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (Type type : Type.values()) {
            if (type.getIndex() == index) {
                return type.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
