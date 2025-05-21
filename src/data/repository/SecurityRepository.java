package data.repository;

import data.model.Security;

import java.util.List;
import java.util.Optional;

public interface SecurityRepository {
    Security save(Security security);
    Security findByEmployeeId(String employeeId);
    void delete(Security security);
    void deleteById(int id);
    Optional<Security> findById(long id);
    List<Security> findAllByName(String name);
    long count();
}