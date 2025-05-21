package services;

import dtos.request.GenerateAccessTokenRequest;
import dtos.request.LoginResidentRequest;
import dtos.request.RegisterResidentRequest;
import dtos.response.GenerateAccessTokenResponse;
import dtos.response.LoginResidentResponse;
import dtos.response.RegisterResidentResponse;

public interface ResidentServices {
    RegisterResidentResponse register(RegisterResidentRequest request);
    LoginResidentResponse login(LoginResidentRequest request);
    GenerateAccessTokenResponse generateAccessToken(GenerateAccessTokenRequest request);
}
