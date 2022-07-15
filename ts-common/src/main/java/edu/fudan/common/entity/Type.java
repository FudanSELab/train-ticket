package edu.fudan.common.entity;

import java.io.Serializable;

/**
 * @author fdse
 */
public enum Type implements Serializable{
    /**
     * G
     */
    G("G", 1),
    /**
     * D
     */
    D("D", 2),
    /**
     * Z
     */
    Z("Z",3),
    /**
     * T
     */
    T("T", 4),
    /**
     * K
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

    void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    void setIndex(int index) {
        this.index = index;
    }
}
