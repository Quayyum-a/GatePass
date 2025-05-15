package services;

import data.model.Resident;
import data.repository.ResidentRepository;
import data.repository.Residents;
import dtos.request.RegisterResidentRequest;
import dtos.response.RegisterResidentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ResidentServicesImplTest {

    private ResidentServices service;
    private ResidentRepository repo;
    private RegisterResidentRequest request;

    @BeforeEach
    void setUp() {
        service = new ResidentServicesImpl();
        repo = new Residents();
        request = new RegisterResidentRequest();
    }

    @Test
    public void registerResident_CountIsOne() {
        Resident resident = new Resident();
        request.setFullName("John Doe");
        request.setAddress("123 Main St");
        request.setEmail("john@gmail.com");
        request.setPhoneNumber("5555555");
        resident.setFullName(request.getFullName());
        resident.setAddress(request.getAddress());
        resident.setEmail(request.getEmail());
        resident.setPhoneNumber(request.getPhoneNumber());
        repo.save(resident);

       RegisterResidentResponse response = service.register(request);
        response.setFullName(request.getFullName());
        response.setAddress(request.getAddress());
        response.setEmail(request.getEmail());
        response.setPhoneNumber(request.getPhoneNumber());
        response.setId(resident.getId());

        assertEquals(1, repo.count());
        assertEquals("John Doe", response.getFullName());
        assertEquals("123 Main St", response.getAddress());
        assertEquals("john@gmail.com", response.getEmail());
        assertEquals("5555555",response.getPhoneNumber());
    }
}