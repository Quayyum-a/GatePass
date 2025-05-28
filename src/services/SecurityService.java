package services;

import data.model.AccessToken;
import dtos.response.GenerateAccessTokenResponse;

public interface SecurityService {
    GenerateAccessTokenResponse validateVisitorAccess(String token);
    void grantAccess(String token);
    AccessToken getAccessTokenDetails(String token);
}