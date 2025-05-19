package services;


import data.model.Resident;
import data.repository.ResidentRepository;
import data.repository.Residents;
import dtos.request.LoginResidentRequest;
import dtos.request.RegisterResidentRequest;
import dtos.response.LoginResidentResponse;
import dtos.response.RegisterResidentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class ResidentServicesImplTest {

    private ResidentServices service;
    private ResidentRepository repo;
    private RegisterResidentRequest request;
    private LoginResidentRequest loginRequest;

    @BeforeEach
    void setUp() {
        Residents.reset();
        service = new ResidentServicesImpl();
        repo = new Residents();
        request = new RegisterResidentRequest();
        loginRequest = new LoginResidentRequest();
    }
    @Test
    public void registerResident_CountIsOne() {
        request.setFullName("John Doe");
        request.setAddress("123 Main St");
        request.setEmail("john@gmail.com");
        request.setPhoneNumber("5555555");

        RegisterResidentResponse response = service.register(request);

        assertEquals(1, repo.count());
        assertEquals("John Doe", response.getFullName());
        assertEquals("123 Main St", response.getAddress());
        assertEquals("john@gmail.com", response.getEmail());
        assertEquals("5555555", response.getPhoneNumber());
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
        assertNull(service.login(loginRequest));
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
}
