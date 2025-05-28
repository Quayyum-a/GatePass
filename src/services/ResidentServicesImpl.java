package services;

import data.model.AccessToken;
import data.model.Resident;
import data.repository.ResidentRepository;
import data.repository.Residents;
import dtos.request.GenerateAccessTokenRequest;
import dtos.request.LoginResidentRequest;
import dtos.request.RegisterResidentRequest;
import dtos.response.GenerateAccessTokenResponse;
import dtos.response.LoginResidentResponse;
import dtos.response.RegisterResidentResponse;

import java.util.Optional;

import static utils.Mapper.*;

public class ResidentServicesImpl implements ResidentServices {
    private ResidentRepository residentRepository = new Residents();
    private AccessTokenService accessTokenService = new AccessTokenServiceImpl();


    @Override
    public RegisterResidentResponse register(RegisterResidentRequest request) {
        Resident existingResident = residentRepository.findByEmail(request.getEmail());
        if (existingResident != null) {
            return null;
        }
        Resident savedResident = residentRepository.save(map(request));
        AccessToken accessToken = accessTokenService.generateToken(savedResident.getId());
        RegisterResidentResponse response = map(savedResident);
        response.setAccessToken(accessToken.getToken());
        return response;
    }

    @Override
    public LoginResidentResponse login(LoginResidentRequest request) {
        Resident resident = residentRepository.findByEmail(request.getEmail());
        if (resident == null || !resident.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return mapToLoginResponse(resident);
    }

    @Override
    public GenerateAccessTokenResponse generateAccessToken(GenerateAccessTokenRequest request) {
        Optional<Resident> residentOptional = residentRepository.findById(request.getResidentId());
        if (residentOptional.isEmpty()) {
            throw new IllegalArgumentException("Resident not found");
        }

        AccessToken accessToken = accessTokenService.generateTokenForVisitor(
            request.getResidentId(),
            request.getVisitorName(),
            request.getVisitorPhoneNumber()
        );

        return mapToAccessTokenResponse(accessToken);
    }
}
