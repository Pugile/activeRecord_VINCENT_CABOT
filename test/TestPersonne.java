
import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestPersonne {

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        Personne.deleteTable();
        Personne.createTable();
        Personne p1 = new Personne("Spielberg", "Steven");
        p1.save();

        Personne p2 = new Personne("Scott", "Ridley");
        p2.save();

        Personne p3 = new Personne("Kubrick", "Stanley");
        p3.save();

        Personne p4 = new Personne("Fincher", "David");
        p4.save();
    }

    @AfterEach
    public void supprTable() throws SQLException, ClassNotFoundException {
        Personne.deleteTable();
    }

    @Test
    public void testFindAll() throws SQLException, ClassNotFoundException {
        ArrayList<Personne> liste = Personne.findAll();

        assertEquals(4, liste.size(), "La table devrait contenir exactement 4 personnes");

        Personne p1 = liste.get(0);
        assertEquals(1, p1.getId());
        assertEquals("Spielberg", p1.getNom());
    }


    @Test
    public void testFindById() throws SQLException, ClassNotFoundException {
        Personne p1 = Personne.findById(1);
        assertNotNull(p1, "L'id 1 doit exister");
        assertEquals("Spielberg", p1.getNom());
        assertEquals("Steven", p1.getPrenom());

        Personne p3 = Personne.findById(3);
        assertNotNull(p3, "L'id 3 doit exister");
        assertEquals("Kubrick", p3.getNom());

        Personne pInconnu = Personne.findById(10);
        assertNull(pInconnu, "L'id 10 ne doit pas exister");
    }

    @Test
    public void testFindByName() throws SQLException, ClassNotFoundException {

        ArrayList<Personne> listeScott = Personne.findByName("Scott");
        assertEquals(1, listeScott.size(), "Il ne devrait y avoir qu'un seul Scott");
        assertEquals("Ridley", listeScott.get(0).getPrenom());
        assertEquals(2, listeScott.get(0).getId());

        ArrayList<Personne> listeInconnue = Personne.findByName("Tarantino");
        assertTrue(listeInconnue.isEmpty(), "La liste devrait être vide pour un nom inconnu");
    }
}