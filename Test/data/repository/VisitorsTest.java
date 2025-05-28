package data.repository;

import data.model.AccessToken;
import data.model.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VisitorsTest {
    private VisitorRepository visitors;

    @BeforeEach
    public void setUp() {
        Visitors.reset();
        visitors = new Visitors();
    }

    @Test
    public void testCountIsZeroWhenNoVisitorsSaved() {
        assertEquals(0, visitors.count());
    }

    @Test
    public void testCountIncreasesWhenVisitorSaved() {
        Visitor visitor = new Visitor();
        visitors.save(visitor);
        assertEquals(1, visitors.count());
    }

    @Test
    public void testSaveAssignsIncrementalId() {
        Visitor visitor1 = new Visitor();
        Visitor visitor2 = new Visitor();
        visitors.save(visitor1);
        visitors.save(visitor2);
        assertEquals(1, visitor1.getId());
        assertEquals(2, visitor2.getId());
    }

    @Test
    public void testSaveUpdatesExistingVisitor() {
        Visitor visitor = new Visitor();
        visitor.setPhoneNumber("1234567890");
        visitors.save(visitor);
        assertEquals(1, visitor.getId());
        assertEquals("1234567890", visitor.getPhoneNumber());

        Visitor updatedVisitor = new Visitor();
        updatedVisitor.setId(1);
        updatedVisitor.setPhoneNumber("0987654321");
        visitors.save(updatedVisitor);

        Optional<Visitor> found = visitors.findById(1);
        assertTrue(found.isPresent());
        assertEquals("0987654321", found.get().getPhoneNumber());
        assertEquals(1, visitors.count());
    }

    @Test
    public void testFindByIdReturnsCorrectVisitor() {
        Visitor visitor = new Visitor();
        visitor.setPhoneNumber("1234567890");
        visitors.save(visitor);
        Optional<Visitor> found = visitors.findById(visitor.getId());
        assertTrue(found.isPresent());
        assertEquals(visitor, found.get());
    }

    @Test
    public void testFindByIdReturnsEmptyForNonExistentId() {
        Optional<Visitor> found = visitors.findById(999);
        assertFalse(found.isPresent());
    }

    @Test
    public void testFindByPhoneNumberReturnsCorrectVisitor() {
        Visitor visitor = new Visitor();
        visitor.setPhoneNumber("1234567890");
        visitors.save(visitor);
        Visitor found = visitors.findByPhoneNumber("1234567890");
        assertNotNull(found);
        assertEquals(visitor, found);
    }

    @Test
    public void testFindByPhoneNumberReturnsNullForNonExistentPhone() {
        assertNull(visitors.findByPhoneNumber("NONEXISTENT"));
    }

    @Test
    public void testFindAllByVisitorIdReturnsAccessTokens() {
        Visitor visitor = new Visitor();
        visitor.setId(1);
        AccessToken token1 = new AccessToken();
        AccessToken token2 = new AccessToken();
        visitor.getAccessTokens().add(token1);
        visitor.getAccessTokens().add(token2);
        visitors.save(visitor);

        List<AccessToken> tokens = visitors.findAllByVisitorId(1);
        assertEquals(2, tokens.size());
        assertTrue(tokens.contains(token1));
        assertTrue(tokens.contains(token2));
    }

    @Test
    public void testFindAllByVisitorIdReturnsEmptyListForNonExistentId() {
        List<AccessToken> tokens = visitors.findAllByVisitorId(999);
        assertTrue(tokens.isEmpty());
    }

    @Test
    public void testDeleteReducesCount() {
        Visitor visitor = new Visitor();
        visitors.save(visitor);
        assertEquals(1, visitors.count());
        visitors.delete(visitor);
        assertEquals(0, visitors.count());
    }

    @Test
    public void testDeleteByIdRemovesVisitor() {
        Visitor visitor = new Visitor();
        visitors.save(visitor);
        visitors.deleteById(visitor.getId());
        assertEquals(0, visitors.count());
        assertFalse(visitors.findById(visitor.getId()).isPresent());
    }

    @Test
    public void testDeleteByIdNonExistentIdDoesNotAffectCount() {
        Visitor visitor = new Visitor();
        visitors.save(visitor);
        visitors.deleteById(999);
        assertEquals(1, visitors.count());
    }
}