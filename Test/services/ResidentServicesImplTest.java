package services;

import data.model.AccessToken;
import data.model.Resident;
import data.repository.AccessTokenRepository;
import data.repository.AccessTokens;
import data.repository.ResidentRepository;
import data.repository.Residents;
import dtos.request.GenerateAccessTokenRequest;
import dtos.request.LoginResidentRequest;
import dtos.request.RegisterResidentRequest;
import dtos.response.GenerateAccessTokenResponse;
import dtos.response.LoginResidentResponse;
import dtos.response.RegisterResidentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ResidentServicesImplTest {

    private ResidentServices service;
    private ResidentRepository repo;
    private AccessTokenRepository tokenRepo;
    private RegisterResidentRequest request;
    private LoginResidentRequest loginRequest;
    private GenerateAccessTokenRequest accessTokenRequest;

    @BeforeEach
    void setUp() {
        Residents.reset();
        AccessTokens.reset();
        service = new ResidentServicesImpl();
        repo = new Residents();
        tokenRepo = new AccessTokens();
        request = new RegisterResidentRequest();
        loginRequest = new LoginResidentRequest();
        accessTokenRequest = new GenerateAccessTokenRequest();
    }
    @Test
    public void registerResident_CountIsOne_AndAccessTokenIsGenerated() {
        request.setFullName("John Doe");
        request.setAddress("123 Main St");
        request.setEmail("john@gmail.com");
        request.setPhoneNumber("5555555");

        RegisterResidentResponse response = service.register(request);

        assertEquals(1, repo.count());
        assertEquals(1, tokenRepo.count());
        assertEquals("John Doe", response.getFullName());
        assertEquals("123 Main St", response.getAddress());
        assertEquals("john@gmail.com", response.getEmail());
        assertEquals("5555555", response.getPhoneNumber());
        assertNotNull(response.getAccessToken());

        AccessToken token = tokenRepo.findByToken(response.getAccessToken());

        assertEquals(1, token.getResidentId());
    }

    @Test
    public void loginResident_Success() {
        request.setFullName("John Doe");
        request.setAddress("123 Main St");
        request.setEmail("john@gmail.com");
        request.setPhoneNumber("5555555");
        request.setPassword("password123");
        service.register(request);

        loginRequest.setEmail("john@gmail.com");
        loginRequest.setPassword("password123");
        LoginResidentResponse response = service.login(loginRequest);

        assertEquals("John Doe", response.getFullName());
        assertEquals("123 Main St", response.getAddress());
        assertEquals("john@gmail.com", response.getEmail());
        assertEquals("5555555", response.getPhoneNumber());
    }

    @Test
    public void loginResident_InvalidCredentials_ThrowsException() {
        request.setFullName("John Doe");
        request.setAddress("123 Main St");
        request.setEmail("john@gmail.com");
        request.setPhoneNumber("5555555");
        request.setPassword("password123");
        service.register(request);

        loginRequest.setEmail("john@gmail.com");
        loginRequest.setPassword("wrong password");

        assertThrows(IllegalArgumentException.class, () -> {
            service.login(loginRequest);
        });
    }
    @Test
    public void registerAResidentWithServices_createAResidentWithRepository() {
        request.setFullName("John Doe");
        request.setAddress("123 Main St");
        request.setEmail("quayyum@gmail.com");
        request.setPhoneNumber("5555555");
        request.setPassword("password123");
        RegisterResidentResponse response = service.register(request);
        assertEquals(1, response.getId());
        Resident res = new Resident();

        res.setFullName(request.getFullName());

        repo.save(res);
        assertEquals(2, repo.count());
        assertEquals(2, res.getId());
    }

    @Test
    public void generateAccessToken_ForVisitor_ReturnsTokenWithVisitorInformation() {
        request.setFullName("John Doe");
        request.setAddress("123 Main St");
        request.setEmail("john@gmail.com");
        request.setPhoneNumber("5555555");
        request.setPassword("password123");
        RegisterResidentResponse registerResponse = service.register(request);

        accessTokenRequest.setResidentId(registerResponse.getId());
        accessTokenRequest.setVisitorName("Jane Smith");
        accessTokenRequest.setVisitorPhoneNumber("9876543210");

        GenerateAccessTokenResponse response = service.generateAccessToken(accessTokenRequest);

        assertEquals("Jane Smith", response.getVisitorName());
        assertEquals("9876543210", response.getVisitorPhoneNumber());

        AccessToken token = tokenRepo.findByToken(response.getToken());

        assertEquals(registerResponse.getId(), token.getResidentId());
        assertEquals("Jane Smith", token.getVisitorName());
        assertEquals("9876543210", token.getVisitorPhoneNumber());
    }

    @Test
    public void generateAccessToken_InvalidResidentId_ThrowsException() {

        accessTokenRequest.setResidentId(999);
        accessTokenRequest.setVisitorName("Jane Smith");
        accessTokenRequest.setVisitorPhoneNumber("9876543210");

        assertThrows(IllegalArgumentException.class, () -> {
            service.generateAccessToken(accessTokenRequest);
        });
    }
}
