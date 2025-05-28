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

        AccessToken token = accessTokenService.generateTokenForVisitor(1, "John Doe", "1234567890");

        GenerateAccessTokenResponse response = securityService.validateVisitorAccess(token.getToken());

        assertEquals(token.getToken(), response.getToken());
        assertEquals(token.getExpiryDate(), response.getExpiryDate());
        assertEquals("John Doe", response.getVisitorName());
        assertEquals("1234567890", response.getVisitorPhoneNumber());
    }
    
    @Test
    void validateVisitorAccess_InvalidToken_ThrowsException() {

        assertThrows(IllegalArgumentException.class, () -> {
            securityService.validateVisitorAccess("non-existent-token");
        });
    }
    
    @Test
    void grantAccess_ValidToken_MarksTokenAsUsed() {

        AccessToken token = accessTokenService.generateTokenForVisitor(1, "John Doe", "1234567890");

        securityService.grantAccess(token.getToken());

        AccessToken updatedToken = accessTokenRepository.findByToken(token.getToken());
        assertTrue(updatedToken.isUsed());
        assertThrows(IllegalArgumentException.class, () -> {
            securityService.validateVisitorAccess(token.getToken());
        });
    }
    
    @Test
    void getAccessTokenDetails_ValidToken_ReturnsTokenDetails() {

        AccessToken token = accessTokenService.generateTokenForVisitor(1, "John Doe", "1234567890");

        AccessToken tokenDetails = securityService.getAccessTokenDetails(token.getToken());

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

        assertThrows(IllegalArgumentException.class, () -> {
            securityService.getAccessTokenDetails("non-existent-token");
        });
    }
}