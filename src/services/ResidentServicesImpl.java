package services;

import data.model.Resident;
import data.repository.ResidentRepository;
import data.repository.Residents;
import dtos.request.RegisterResidentRequest;
import dtos.response.RegisterResidentResponse;

import static utils.Mapper.map;

public class ResidentServicesImpl implements ResidentServices {
    private ResidentRepository residentRepository = new Residents();


    @Override
    public RegisterResidentResponse register(RegisterResidentRequest request) {
        Resident savedResident = residentRepository.save(map(request));
        return map(savedResident);
    }
}
