package services;

import dtos.request.RegisterResidentRequest;
import dtos.response.RegisterResidentResponse;

public interface ResidentServices {
    RegisterResidentResponse register(RegisterResidentRequest request);
}
