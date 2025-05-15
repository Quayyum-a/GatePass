package data.repository;

import data.model.Resident;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Residents implements ResidentRepository{
    private int currentId = 0;
    private List<Resident> residents = new ArrayList<>();
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
    return null;
}

@Override
public List<Resident> findAllByFullName(String fullName) {
    List<Resident> foundResidents = new ArrayList<>();
    for(Resident resident : residents) {
        if(resident.getFullName().equals(fullName)) {
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
