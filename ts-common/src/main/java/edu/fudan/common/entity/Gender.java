package edu.fudan.common.entity;

/**
 * @author fdse
 */
public enum Gender {

    /**
     * null
     */
    NONE   (0, "Null"),
    /**
     * male
     */
    MALE   (1, "Male"),
    /**
     * female
     */
    FEMALE (2, "Female"),
    /**
     * other
     */
    OTHER  (3, "Other");

    private int code;
    private String name;

    Gender(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode(){
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(int code){
        Gender[] genderSet = Gender.values();
        for(Gender gender : genderSet){
            if(gender.getCode() == code){
                return gender.getName();
            }
        }
        return genderSet[0].getName();
    }

}
