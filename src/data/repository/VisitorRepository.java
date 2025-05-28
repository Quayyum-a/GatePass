package data.repository;

import data.model.AccessToken;
import data.model.Visitor;

import java.util.List;
import java.util.Optional;

public interface VisitorRepository {
    Visitor save(Visitor visitor);
   Visitor findByPhoneNumber(String phoneNumber);
    void delete(Visitor accessToken);
    void deleteById(int id);
    Optional<Visitor> findById(long id);
    List<AccessToken> findAllByVisitorId(int visitorId);
    long count();
}
