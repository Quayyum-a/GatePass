package data.repository;

import data.model.Resident;

import java.util.List;
import java.util.Optional;

public interface ResidentRepository {
    Resident save(Resident resident);
    Resident findByPhoneNumber(String phoneNumber);
    void delete(Resident resident);
    void deleteById(int id);
    Optional<Resident> findById(long id);
    Resident findByAddress(String address);
    Resident findByEmail(String email);
    List<Resident> findAllByFullName(String fullName);
    long count();

}
