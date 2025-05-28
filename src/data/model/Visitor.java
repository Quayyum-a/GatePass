package data.model;

import java.util.Collection;
import java.util.List;

public class Visitor {
    private int id;
    private String fullName;
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Collection<? extends AccessToken> getAccessTokens() {
        return List.of();
    }
}
