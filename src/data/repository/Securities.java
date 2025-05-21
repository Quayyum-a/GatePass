package data.repository;

import data.model.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Securities implements SecurityRepository {
    private static int currentId = 0;
    private static List<Security> securities = new ArrayList<>();

    public static void reset() {
        securities.clear();
        currentId = 0;
    }

    @Override
    public Security save(Security security) {
        if (isNew(security)) {
            saveNew(security);
        } else {
            update(security);
        }
        return security;
    }

    private void update(Security security) {
        deleteById(security.getId());
        securities.add(security);
    }

    private void saveNew(Security security) {
        security.setId(generateId());
        securities.add(security);
    }

    private boolean isNew(Security security) {
        return security.getId() == 0;
    }

    private int generateId() {
        return ++currentId;
    }

    @Override
    public Security findByEmployeeId(String employeeId) {
        if (employeeId == null) {
            return null;
        }
        for (Security security : securities) {
            if (security.getEmployeeId() != null && security.getEmployeeId().equals(employeeId)) {
                return security;
            }
        }
        return null;
    }

    @Override
    public void delete(Security security) {
        securities.remove(security);
    }

    @Override
    public void deleteById(int id) {
        findById(id).ifPresent(this::delete);
    }

    @Override
    public Optional<Security> findById(long id) {
        for (Security security : securities) {
            if (security.getId() == id) {
                return Optional.of(security);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Security> findAllByName(String name) {
        if (name == null) {
            return new ArrayList<>();
        }
        List<Security> foundSecurities = new ArrayList<>();
        for (Security security : securities) {
            if (security.getName() != null && security.getName().equals(name)) {
                foundSecurities.add(security);
            }
        }
        return foundSecurities;
    }

    @Override
    public long count() {
        return securities.size();
    }
}