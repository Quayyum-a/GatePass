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
        if (!accessTokenService.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        AccessToken accessToken = accessTokenRepository.findByToken(token);

        return mapToAccessTokenResponse(accessToken);
    }

    @Override
    public void grantAccess(String token) {

        accessTokenService.markTokenAsUsed(token);
    }

    @Override
    public AccessToken getAccessTokenDetails(String token) {
        AccessToken accessToken = accessTokenRepository.findByToken(token);
        if (accessToken == null) {
            throw new IllegalArgumentException("Token not found");
        }
        return accessToken;
    }
}