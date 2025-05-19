package services;

import data.model.Resident;
import data.repository.ResidentRepository;
import data.repository.Residents;
import dtos.request.LoginResidentRequest;
import dtos.request.RegisterResidentRequest;
import dtos.response.LoginResidentResponse;
import dtos.response.RegisterResidentResponse;

import static utils.Mapper.*;

public class ResidentServicesImpl implements ResidentServices {
    private ResidentRepository residentRepository = new Residents();


    @Override
    public RegisterResidentResponse register(RegisterResidentRequest request) {
        Resident existingResident = residentRepository.findByEmail(request.getEmail());
        if (existingResident != null) {
            return null;
        }
        Resident savedResident = residentRepository.save(map(request));
        return map(savedResident);
    }

    @Override
    public LoginResidentResponse login(LoginResidentRequest request) {
        Resident resident = residentRepository.findByEmail(request.getEmail());
        if (resident == null || !resident.getPassword().equals(request.getPassword())) {
            return null;
        }
        return mapToLoginResponse(resident);
    }
}
