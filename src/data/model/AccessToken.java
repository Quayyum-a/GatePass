package data.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class AccessToken {
    private int id;
    private String token;
    private LocalDateTime creationDate;
    private LocalDateTime expiryDate;
    private boolean isUsed;
    private int residentId;
    private String visitorName;
    private String visitorPhoneNumber;

    public AccessToken() {
        this.token = generateToken();
        this.creationDate = LocalDateTime.now();
        this.expiryDate = creationDate.plusHours(24);
        this.isUsed = false;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean isValid() {
        return !isUsed && LocalDateTime.now().isBefore(expiryDate);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public void markAsUsed() {
        this.isUsed = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public int getResidentId() {
        return residentId;
    }

    public void setResidentId(int residentId) {
        this.residentId = residentId;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorPhoneNumber() {
        return visitorPhoneNumber;
    }

    public void setVisitorPhoneNumber(String visitorPhoneNumber) {
        this.visitorPhoneNumber = visitorPhoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof AccessToken comparedToken) {
            return this.id == comparedToken.getId();
        }
        return false;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", creationDate=" + creationDate +
                ", expiryDate=" + expiryDate +
                ", isUsed=" + isUsed +
                ", residentId=" + residentId +
                ", visitorName='" + visitorName + '\'' +
                ", visitorPhoneNumber='" + visitorPhoneNumber + '\'' +
                '}';
    }
}
