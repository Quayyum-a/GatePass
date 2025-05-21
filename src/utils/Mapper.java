package utils;

import data.model.AccessToken;
import data.model.Resident;
import dtos.request.LoginResidentRequest;
import dtos.request.RegisterResidentRequest;
import dtos.response.GenerateAccessTokenResponse;
import dtos.response.LoginResidentResponse;
import dtos.response.RegisterResidentResponse;

public class Mapper {
    public static Resident map(RegisterResidentRequest request) {
        Resident resident = new Resident();
        resident.setFullName(request.getFullName());
        resident.setAddress(request.getAddress());
        resident.setPhoneNumber(request.getPhoneNumber());
        resident.setEmail(request.getEmail());
        resident.setPassword(request.getPassword());
        return resident;
    }
    public static RegisterResidentResponse map(Resident resident) {
        RegisterResidentResponse response = new RegisterResidentResponse();
        response.setId(resident.getId());
        response.setFullName(resident.getFullName());
        response.setAddress(resident.getAddress());
        response.setPhoneNumber(resident.getPhoneNumber());
        response.setEmail(resident.getEmail());
        response.setPassword(resident.getPassword());
        return response;
    }
    public static LoginResidentResponse mapToLoginResponse(Resident resident) {
        LoginResidentResponse response = new LoginResidentResponse();
        response.setId(resident.getId());
        response.setFullName(resident.getFullName());
        response.setAddress(resident.getAddress());
        response.setPhoneNumber(resident.getPhoneNumber());
        response.setEmail(resident.getEmail());
        return response;
    }

    public static GenerateAccessTokenResponse mapToAccessTokenResponse(AccessToken accessToken) {
        GenerateAccessTokenResponse response = new GenerateAccessTokenResponse();
        response.setToken(accessToken.getToken());
        response.setExpiryDate(accessToken.getExpiryDate());
        response.setVisitorName(accessToken.getVisitorName());
        response.setVisitorPhoneNumber(accessToken.getVisitorPhoneNumber());
        return response;
    }
}
