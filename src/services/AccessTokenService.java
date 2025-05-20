package services;

import data.model.AccessToken;

public interface AccessTokenService {
    AccessToken generateToken(int residentId);
    boolean validateToken(String token);
    void markTokenAsUsed(String token);
    boolean isTokenExpired(String token);
}