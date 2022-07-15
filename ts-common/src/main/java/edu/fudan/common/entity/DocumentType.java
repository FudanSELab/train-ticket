package edu.fudan.common.entity;

/**
 * @author fdse
 */
public enum DocumentType {

    /**
     * null
     */
    NONE      (0,"Null"),
    /**
     * id card
     */
    ID_CARD   (1,"ID Card"),
    /**
     * passport
     */
    PASSPORT  (2,"Passport"),
    /**
     * other
     */
    OTHER     (3,"Other");

    private int code;
    private String name;

    DocumentType(int code, String name){
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
        DocumentType[] documentTypeSet = DocumentType.values();
        for(DocumentType documentType : documentTypeSet){
            if(documentType.getCode() == code){
                return documentType.getName();
            }
        }
        return documentTypeSet[0].getName();
    }
}
