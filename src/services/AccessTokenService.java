package services;

import data.model.AccessToken;

public interface AccessTokenService {
    AccessToken generateToken(int residentId);
    AccessToken generateTokenForVisitor(int residentId, String visitorName, String visitorPhoneNumber);
    boolean validateToken(String token);
    void markTokenAsUsed(String token);
    boolean isTokenExpired(String token);
}
