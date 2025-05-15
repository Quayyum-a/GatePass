package services;

import dtos.request.LoginResidentRequest;
import dtos.request.RegisterResidentRequest;
import dtos.response.LoginResidentResponse;
import dtos.response.RegisterResidentResponse;

public interface ResidentServices {
    RegisterResidentResponse register(RegisterResidentRequest request);
    LoginResidentResponse login(LoginResidentRequest request);
}

