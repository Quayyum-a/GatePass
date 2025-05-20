package data.repository;

import data.model.AccessToken;

import java.util.List;
import java.util.Optional;

public interface AccessTokenRepository {
    AccessToken save(AccessToken accessToken);
    AccessToken findByToken(String token);
    void delete(AccessToken accessToken);
    void deleteById(int id);
    Optional<AccessToken> findById(long id);
    List<AccessToken> findAllByResidentId(int residentId);
    long count();
}