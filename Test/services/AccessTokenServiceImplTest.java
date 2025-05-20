package services;

import data.model.AccessToken;
import data.repository.AccessTokenRepository;
import data.repository.AccessTokens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class AccessTokenServiceImplTest {

    private AccessTokenService service;
    private AccessTokenRepository repository;

    @BeforeEach
    void setUp() {
        AccessTokens.reset();
        service = new AccessTokenServiceImpl();
        repository = new AccessTokens();
    }

    @Test
    public void generateToken_CreatesTokenWithCorrectResidentId() {
        AccessToken token = service.generateToken(1);

        // Instead of assertNotNull, we'll verify specific properties
        assertEquals(1, token.getResidentId());
        assertFalse(token.getToken().isEmpty());
        assertFalse(token.isUsed());
        assertTrue(token.getCreationDate().isBefore(LocalDateTime.now().plusSeconds(1)));
        assertTrue(token.getExpiryDate().isAfter(LocalDateTime.now()));

        // Verify expiry date is 24 hours after creation date
        LocalDateTime expectedExpiryDate = token.getCreationDate().plusHours(24);
        assertEquals(expectedExpiryDate, token.getExpiryDate());
    }

    @Test
    public void validateToken_ValidToken_ReturnsTrue() {
        AccessToken token = service.generateToken(1);

        boolean isValid = service.validateToken(token.getToken());

        assertTrue(isValid);
    }

    @Test
    public void validateToken_NonExistentToken_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.validateToken("non-existent-token");
        });
    }

    @Test
    public void markTokenAsUsed_TokenIsMarkedAsUsed_AndBecomesInvalid() {
        AccessToken token = service.generateToken(1);

        service.markTokenAsUsed(token.getToken());

        AccessToken updatedToken = repository.findByToken(token.getToken());
        assertTrue(updatedToken.isUsed());

        // Validate that a used token is no longer valid
        assertThrows(IllegalArgumentException.class, () -> {
            service.validateToken(token.getToken());
        });
    }

    @Test
    public void isTokenExpired_NewToken_ReturnsFalse() {
        AccessToken token = service.generateToken(1);

        boolean isExpired = service.isTokenExpired(token.getToken());

        assertFalse(isExpired);
    }

    @Test
    public void isTokenExpired_ExpiredToken_ReturnsTrue() throws Exception {
        AccessToken token = service.generateToken(1);

        // Manually set the expiry date to the past using reflection
        Field expiryDateField = AccessToken.class.getDeclaredField("expiryDate");
        expiryDateField.setAccessible(true);
        expiryDateField.set(token, LocalDateTime.now().minusHours(1));

        // Save the modified token
        repository.save(token);

        boolean isExpired = service.isTokenExpired(token.getToken());

        assertTrue(isExpired);
    }

    @Test
    public void generateTokenWithService_CreateAnotherTokenWithRepository() {
        // Generate a token using the service
        AccessToken token = service.generateToken(1);
        assertEquals(1, repository.count());

        // Create another token directly with the repository
        AccessToken newToken = new AccessToken();
        newToken.setResidentId(2);
        repository.save(newToken);

        // Verify both tokens exist
        assertEquals(2, repository.count());
        assertEquals(1, token.getId());
        assertEquals(2, newToken.getId());
    }
}
