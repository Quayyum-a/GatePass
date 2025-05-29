package data.repository;

import data.model.AccessToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccessTokensTest {
    private AccessTokenRepository accessTokens;

    @BeforeEach
    public void setUp() {
        AccessTokens.reset();
        accessTokens = new AccessTokens();
    }

    @Test
    public void testCountIsZeroWhenNoAccessTokensSaved() {
        assertEquals(0, accessTokens.count());
    }

    @Test
    public void testCountIncreasesWhenAccessTokenSaved() {
        AccessToken token = new AccessToken();
        accessTokens.save(token);
        assertEquals(1, accessTokens.count());
    }

    @Test
    public void testSaveAssignsIncrementalId() {
        AccessToken token1 = new AccessToken();
        AccessToken token2 = new AccessToken();
        accessTokens.save(token1);
        accessTokens.save(token2);
        assertEquals(1, token1.getId());
        assertEquals(2, token2.getId());
    }

    @Test
    public void testSaveUpdatesExistingAccessToken() {
        AccessToken token = new AccessToken();
        token.setToken("ABC123");
        accessTokens.save(token);
        assertEquals(1, token.getId());
        assertEquals("ABC123", token.getToken());

        AccessToken updatedToken = new AccessToken();
        updatedToken.setId(1);
        updatedToken.setToken("XYZ789");
        accessTokens.save(updatedToken);

        Optional<AccessToken> found = accessTokens.findById(1);
        assertTrue(found.isPresent());
        assertEquals("XYZ789", found.get().getToken());
        assertEquals(1, accessTokens.count());
    }

    @Test
    public void testFindByIdReturnsCorrectAccessToken() {
        AccessToken token = new AccessToken();
        token.setToken("ABC123");
        accessTokens.save(token);
        Optional<AccessToken> found = accessTokens.findById(token.getId());
        assertTrue(found.isPresent());
        assertEquals(token, found.get());
    }

    @Test
    public void testFindByIdReturnsEmptyForNonExistentId() {
        Optional<AccessToken> found = accessTokens.findById(999);
        assertFalse(found.isPresent());
    }

    @Test
    public void testFindByTokenReturnsCorrectAccessToken() {
        AccessToken token = new AccessToken();
        token.setToken("ABC123");
        accessTokens.save(token);
        AccessToken found = accessTokens.findByToken("ABC123");
        assertNotNull(found);
        assertEquals(token, found);
    }

    @Test
    public void testFindByTokenReturnsNullForNonExistentToken() {
        assertNull(accessTokens.findByToken("NONEXISTENT"));
    }

    @Test
    public void testFindAllByResidentIdReturnsMatchingTokens() {
        AccessToken token1 = new AccessToken();
        token1.setResidentId(1);
        AccessToken token2 = new AccessToken();
        token2.setResidentId(2);
        AccessToken token3 = new AccessToken();
        token3.setResidentId(1);
        accessTokens.save(token1);
        accessTokens.save(token2);
        accessTokens.save(token3);

        List<AccessToken> found = accessTokens.findAllByResidentId(1);
        assertEquals(2, found.size());
        assertTrue(found.contains(token1));
        assertTrue(found.contains(token3));
    }

    @Test
    public void testFindAllByResidentIdReturnsEmptyListForNonExistentId() {
        List<AccessToken> found = accessTokens.findAllByResidentId(999);
        assertTrue(found.isEmpty());
    }

    @Test
    public void testDeleteReducesCount() {
        AccessToken token = new AccessToken();
        accessTokens.save(token);
        assertEquals(1, accessTokens.count());
        accessTokens.delete(token);
        assertEquals(0, accessTokens.count());
    }

    @Test
    public void testDeleteByIdRemovesAccessToken() {
        AccessToken token = new AccessToken();
        accessTokens.save(token);
        accessTokens.deleteById(token.getId());
        assertEquals(0, accessTokens.count());
        assertFalse(accessTokens.findById(token.getId()).isPresent());
    }

    @Test
    public void testDeleteByIdNonExistentIdDoesNotAffectCount() {
        AccessToken token = new AccessToken();
        accessTokens.save(token);
        accessTokens.deleteById(999);
        assertEquals(1, accessTokens.count());
    }
}