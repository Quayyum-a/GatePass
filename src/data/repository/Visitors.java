package data.repository;

import data.model.AccessToken;
import data.model.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Visitors implements VisitorRepository {
    private static int currentId = 0;
    private static List<Visitor> visitors = new ArrayList<>();

    public static void reset() {
        visitors.clear();
        currentId = 0;
    }

    @Override
    public Visitor save(Visitor visitor) {
        if (isNew(visitor)) {
            saveNew(visitor);
        } else {
            update(visitor);
        }
        return visitor;
    }

    private void update(Visitor visitor) {
        deleteById(visitor.getId());
        visitors.add(visitor);
    }

    private boolean isNew(Visitor visitor) {
        return visitor.getId() == 0;
    }

    private void saveNew(Visitor visitor) {
        visitor.setId(++currentId);
        visitors.add(visitor);
    }

    @Override
    public Visitor findByPhoneNumber(String phoneNumber) {
        for (Visitor visitor : visitors) {
            if (visitor.getPhoneNumber().equals(phoneNumber)) {
                return visitor;
            }
        }
        return null;
    }

    @Override
    public void delete(Visitor visitor) {
        visitors.remove(visitor);
    }

    @Override
    public void deleteById(int id) {
        visitors.removeIf(visitor -> visitor.getId() == id);
    }

    @Override
    public Optional<Visitor> findById(long id) {
        for (Visitor visitor : visitors) {
            if (visitor.getId() == id) {
                return Optional.of(visitor);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<AccessToken> findAllByVisitorId(int visitorId) {
        for (Visitor visitor : visitors) {
            if (visitor.getId() == visitorId) {
                return new ArrayList<>(visitor.getAccessTokens());
            }
        }
        return List.of();
    }

    @Override
    public long count() {
        return visitors.size();
    }
}