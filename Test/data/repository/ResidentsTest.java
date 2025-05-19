package data.repository;


import data.model.Resident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResidentsTest {
    ResidentRepository residents;

    @BeforeEach
    public void setUp() {
        Residents.reset();
        residents = new Residents();
    }


    @Test
    public void testThatResidentsIsZero_WithoutSaving() {

        assertEquals(0, residents.count());
    }
    @Test
    public void testThatResidentsIncrease_WhenSaved() {
        residents.save(new Resident());
        assertEquals(1, residents.count());
    }

    @Test
    public void testThatResidentsCanBeFoundById() {
        Resident resident = new Resident();
        Residents residents = new Residents();
        Resident savedResident = residents.save(resident);
        Resident foundResident = residents.findById(savedResident.getId()).get();
        assertEquals(savedResident, foundResident);
    }

    @Test
    public void saveTwoResidents_IdOfEachResidentIsAssignedIncrementallyTest() {
        Resident resident1 = new Resident();
        Resident resident2 = new Resident();
        residents.save(resident1);
        residents.save(resident2);
        assertEquals(1, resident1.getId());
        assertEquals(2, resident2.getId());
    }

    @Test
    public void saveResident_saveAnotherWithSameId_CountIsOneTest(){
        Resident resident1 = new Resident();
        residents.save(resident1);
        assertEquals(1, resident1.getId());
        Resident resident2 = new Resident();
        residents.save(resident2);
        resident2.setId(resident1.getId());
        assertEquals(1, resident2.getId());
    }

    @Test
    public void saveResident_saveAnotherWithTheSameId_residentDetailsIsUpdated(){
        Resident resident1 = new Resident();
        resident1.setFullName("John Doe");
        residents.save(resident1);
        assertEquals(1, resident1.getId());
        assertEquals("John Doe", resident1.getFullName());
        Resident resident2 = new Resident();
        resident2.setId(resident1.getId());
        residents.save(resident2);
        resident2.setFullName("Jane Doe");
        assertEquals(1, resident2.getId());
        assertEquals("Jane Doe", resident2.getFullName());
        assertEquals(1,residents.count());
    }
    @Test
    public void addResident_deleteResident_CountIsZeroTest(){
        Resident resident = new Resident();
        residents.save(resident);
        resident.setFullName("John Doe");
        residents.delete(resident);
        assertEquals(0, residents.count());
    }

    @Test
    public void addThreeResidents_findAllByFullName_ReturnsMatchingResidentsTest(){
        Resident resident1 = new Resident();
        resident1.setFullName("John Doe");
        residents.save(resident1);
        Resident resident2 = new Resident();
        resident2.setFullName("Jane Doe");
        residents.save(resident2);
        Resident resident3 = new Resident();
        resident3.setFullName("John Doe");
        residents.save(resident3);
        List<Resident> foundResidents = residents.findAllByFullName("John Doe");

        assertEquals(2, foundResidents.size());
    }
}
