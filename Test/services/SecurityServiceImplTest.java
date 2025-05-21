package services;

import data.model.AccessToken;
import data.repository.AccessTokenRepository;
import data.repository.AccessTokens;
import dtos.response.GenerateAccessTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityServiceImplTest {

    private SecurityService securityService;
    private AccessTokenService accessTokenService;
    private AccessTokenRepository accessTokenRepository;

    @BeforeEach
    void setUp() {
        AccessTokens.reset();
        securityService = new SecurityServiceImpl();
        accessTokenService = new AccessTokenServiceImpl();
        accessTokenRepository = new AccessTokens();
    }

    @Test
    void validateVisitorAccess_ValidToken_ReturnsVisitorInformation() {
        // Generate a token for a visitor
        AccessToken token = accessTokenService.generateTokenForVisitor(1, "John Doe", "1234567890");
        
        // Validate the token
        GenerateAccessTokenResponse response = securityService.validateVisitorAccess(token.getToken());
        
        // Verify the response
        assertEquals(token.getToken(), response.getToken());
        assertEquals(token.getExpiryDate(), response.getExpiryDate());
        assertEquals("John Doe", response.getVisitorName());
        assertEquals("1234567890", response.getVisitorPhoneNumber());
    }
    
    @Test
    void validateVisitorAccess_InvalidToken_ThrowsException() {
        // Try to validate a non-existent token
        assertThrows(IllegalArgumentException.class, () -> {
            securityService.validateVisitorAccess("non-existent-token");
        });
    }
    
    @Test
    void grantAccess_ValidToken_MarksTokenAsUsed() {
        // Generate a token for a visitor
        AccessToken token = accessTokenService.generateTokenForVisitor(1, "John Doe", "1234567890");
        
        // Grant access
        securityService.grantAccess(token.getToken());
        
        // Verify the token is marked as used
        AccessToken updatedToken = accessTokenRepository.findByToken(token.getToken());
        assertTrue(updatedToken.isUsed());
        
        // Verify that a used token is no longer valid
        assertThrows(IllegalArgumentException.class, () -> {
            securityService.validateVisitorAccess(token.getToken());
        });
    }
    
    @Test
    void getAccessTokenDetails_ValidToken_ReturnsTokenDetails() {
        // Generate a token for a visitor
        AccessToken token = accessTokenService.generateTokenForVisitor(1, "John Doe", "1234567890");
        
        // Get the token details
        AccessToken tokenDetails = securityService.getAccessTokenDetails(token.getToken());
        
        // Verify the details
        assertEquals(token.getId(), tokenDetails.getId());
        assertEquals(token.getToken(), tokenDetails.getToken());
        assertEquals(token.getCreationDate(), tokenDetails.getCreationDate());
        assertEquals(token.getExpiryDate(), tokenDetails.getExpiryDate());
        assertEquals(token.isUsed(), tokenDetails.isUsed());
        assertEquals(token.getResidentId(), tokenDetails.getResidentId());
        assertEquals("John Doe", tokenDetails.getVisitorName());
        assertEquals("1234567890", tokenDetails.getVisitorPhoneNumber());
    }
    
    @Test
    void getAccessTokenDetails_InvalidToken_ThrowsException() {
        // Try to get details for a non-existent token
        assertThrows(IllegalArgumentException.class, () -> {
            securityService.getAccessTokenDetails("non-existent-token");
        });
    }
}