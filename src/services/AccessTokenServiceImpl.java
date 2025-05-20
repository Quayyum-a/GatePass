package services;

import data.model.AccessToken;
import data.repository.AccessTokenRepository;
import data.repository.AccessTokens;

public class AccessTokenServiceImpl implements AccessTokenService {
    private AccessTokenRepository accessTokenRepository = new AccessTokens();

    @Override
    public AccessToken generateToken(int residentId) {
        AccessToken accessToken = new AccessToken();
        accessToken.setResidentId(residentId);
        return accessTokenRepository.save(accessToken);
    }

    @Override
    public boolean validateToken(String token) {
        AccessToken accessToken = accessTokenRepository.findByToken(token);
        if (accessToken == null) {
            throw new IllegalArgumentException("Token not found");
        }
        if (!accessToken.isValid()) {
            throw new IllegalArgumentException("Token is invalid");
        }
        return true;
    }

    @Override
    public void markTokenAsUsed(String token) {
        AccessToken accessToken = accessTokenRepository.findByToken(token);
        if (accessToken == null) {
            throw new IllegalArgumentException("Token not found");
        }
        accessToken.markAsUsed();
        accessTokenRepository.save(accessToken);
    }

    @Override
    public boolean isTokenExpired(String token) {
        AccessToken accessToken = accessTokenRepository.findByToken(token);
        if (accessToken == null) {
            throw new IllegalArgumentException("Token not found");
        }
        return accessToken.isExpired();
    }
}
