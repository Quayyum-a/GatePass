package services;

import data.model.AccessToken;
import dtos.response.GenerateAccessTokenResponse;

public interface SecurityService {
    /**
     * Validates a token and returns the visitor information if the token is valid.
     * @param token The token to validate
     * @return The visitor information associated with the token
     * @throws IllegalArgumentException if the token is invalid, expired, or already used
     */
    GenerateAccessTokenResponse validateVisitorAccess(String token);
    
    /**
     * Marks a token as used after a visitor has been granted access.
     * @param token The token to mark as used
     * @throws IllegalArgumentException if the token is not found
     */
    void grantAccess(String token);
    
    /**
     * Gets the details of an access token.
     * @param token The token to get details for
     * @return The access token details
     * @throws IllegalArgumentException if the token is not found
     */
    AccessToken getAccessTokenDetails(String token);
}