package preserveother.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

/**
 * @author fdu
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contacts {

    private UUID id;

    private UUID accountId;

    private String name;

    private int documentType;

    private String documentNumber;

    private String phoneNumber;

    public Contacts() {
        //Default Constructor
    }

    public Contacts(UUID id, UUID accountId, String name, int documentType, String documentNumber, String phoneNumber) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.phoneNumber = phoneNumber;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        Contacts other = (Contacts) obj;
        return name.equals(other.getName())
                && accountId.equals(other.getAccountId())
                && documentNumber.equals(other.getDocumentNumber())
                && phoneNumber.equals(other.getPhoneNumber())
                && documentType == other.getDocumentType();
    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(accountId);
        sb.append(documentNumber);
        sb.append(phoneNumber);
        sb.append(documentType);
        char[] charArr = sb.toString().toCharArray();
        int hash = 0;
        for (char c : charArr) {
            hash = hash * 131 + c;
        }
        return hash;
    }

}
