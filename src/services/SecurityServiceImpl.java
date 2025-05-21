package services;

import data.model.AccessToken;
import data.repository.AccessTokenRepository;
import data.repository.AccessTokens;
import dtos.response.GenerateAccessTokenResponse;

import static utils.Mapper.mapToAccessTokenResponse;

public class SecurityServiceImpl implements SecurityService {
    private AccessTokenRepository accessTokenRepository = new AccessTokens();
    private AccessTokenService accessTokenService = new AccessTokenServiceImpl();

    @Override
    public GenerateAccessTokenResponse validateVisitorAccess(String token) {
        // Validate the token
        if (!accessTokenService.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        // Get the token details
        AccessToken accessToken = accessTokenRepository.findByToken(token);

        // Map and return the response
        return mapToAccessTokenResponse(accessToken);
    }

    @Override
    public void grantAccess(String token) {
        // Mark the token as used
        accessTokenService.markTokenAsUsed(token);
    }

    @Override
    public AccessToken getAccessTokenDetails(String token) {
        // Get the token details
        AccessToken accessToken = accessTokenRepository.findByToken(token);
        if (accessToken == null) {
            throw new IllegalArgumentException("Token not found");
        }
        return accessToken;
    }
}