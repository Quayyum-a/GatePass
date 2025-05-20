package data.repository;

import data.model.AccessToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccessTokens implements AccessTokenRepository {
    private static int currentId = 0;
    private static List<AccessToken> accessTokens = new ArrayList<>();

    public static void reset() {
        accessTokens.clear();
        currentId = 0;
    }

    @Override
    public AccessToken save(AccessToken accessToken) {
        if (isNew(accessToken)) {
            saveNew(accessToken);
        } else {
            update(accessToken);
        }
        return accessToken;
    }

    private void update(AccessToken accessToken) {
        deleteById(accessToken.getId());
        accessTokens.add(accessToken);
    }

    private void saveNew(AccessToken accessToken) {
        accessToken.setId(generateId());
        accessTokens.add(accessToken);
    }

    private boolean isNew(AccessToken accessToken) {
        return accessToken.getId() == 0;
    }

    private int generateId() {
        return ++currentId;
    }

    @Override
    public AccessToken findByToken(String token) {
        for (AccessToken accessToken : accessTokens) {
            if (accessToken.getToken().equals(token)) {
                return accessToken;
            }
        }
        return null;
    }

    @Override
    public void delete(AccessToken accessToken) {
        accessTokens.remove(accessToken);
    }

    @Override
    public void deleteById(int id) {
        findById(id).ifPresent(this::delete);
    }

    @Override
    public Optional<AccessToken> findById(long id) {
        for (AccessToken accessToken : accessTokens) {
            if (accessToken.getId() == id) {
                return Optional.of(accessToken);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<AccessToken> findAllByResidentId(int residentId) {
        List<AccessToken> foundTokens = new ArrayList<>();
        for (AccessToken accessToken : accessTokens) {
            if (accessToken.getResidentId() == residentId) {
                foundTokens.add(accessToken);
            }
        }
        return foundTokens;
    }

    @Override
    public long count() {
        return accessTokens.size();
    }
}
