package data.repository;

import data.model.Resident;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Residents implements ResidentRepository{
    private static int currentId = 0;
    private static List<Resident> residents = new ArrayList<>();

    public static void reset() {
        residents.clear();
        currentId = 0;
    }
    @Override
    public Resident save(Resident resident) {
        if(isNew(resident)) {
            saveNew(resident);
        }else update(resident);
        return resident;
    }

    private void update(Resident resident) {
        deleteById(resident.getId());
        residents.add(resident);
    }

    private void saveNew(Resident resident) {
        resident.setId(generateId());
        residents.add(resident);
    }

    private boolean isNew(Resident resident) {
        return resident.getId() == 0;
    }


    private int generateId() {
    return ++currentId;
}

@Override
public Resident findByPhoneNumber(String phoneNumber) {
    return null;
}

@Override
public void delete(Resident resident) {
        residents.remove(resident);

}

@Override
public void deleteById(int id) {
        delete(findById(id).get());
}


@Override
public Optional<Resident> findById(long id) {
    for (Resident resident : residents) {
        if (resident.getId() == id) {
            return Optional.of(resident);
        }
    }
    return Optional.empty();
}

@Override
public Resident findByAddress(String address) {
    return null;
}

    @Override
    public Resident findByEmail(String email) {
        if (email == null) {
            return null;
        }
        for (Resident resident : residents) {
            if (resident.getEmail() != null && resident.getEmail().equals(email)) {
                return resident;
            }
        }
        return null;
    }

    @Override
    public List<Resident> findAllByFullName(String fullName) {
        if (fullName == null) {
            return new ArrayList<>();
        }
        List<Resident> foundResidents = new ArrayList<>();
        for(Resident resident : residents) {
            if(resident.getFullName() != null && resident.getFullName().equals(fullName)) {
                foundResidents.add(resident);
            }
        }
        return foundResidents;
    }

    @Override
    public long count() {
        return residents.size();
    }
}
