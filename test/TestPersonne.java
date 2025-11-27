
import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test les méthodes de la classe Personne
 */
public class TestPersonne {

    /**
     * Ce lance avant chaque test et permet de construire la table personne
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        //On supprime la table existante pour partir sur une base nouvelle.
        Personne.deleteTable();
        Personne.createTable();

        //On ajoute les objets Personnes
        Personne p1 = new Personne("Spielberg", "Steven");
        p1.save();

        Personne p2 = new Personne("Scott", "Ridley");
        p2.save();

        Personne p3 = new Personne("Kubrick", "Stanley");
        p3.save();

        Personne p4 = new Personne("Fincher", "David");
        p4.save();
    }

    /**
     * Supprime après chaque test la table personne
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @AfterEach
    public void supprTable() throws SQLException, ClassNotFoundException {
        Personne.deleteTable();
    }

    /**
     * Test de la méthode testFindAll qui retourne tous les lignes de la table personne
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void testFindAll() throws SQLException, ClassNotFoundException {
        ArrayList<Personne> liste = Personne.findAll();

        //On test que la taille de la liste est correct
        assertEquals(4, liste.size(), "La table devrait contenir exactement 4 personnes");

        //On test si la première personne de la liste est bien la première qui est présente dans la base
        Personne p1 = liste.get(0);
        assertEquals(1, p1.getId());
        assertEquals("Spielberg", p1.getNom());
    }


    /**
     * Test la méthode FindById qui retourne une personne suivant l'id passé en paramètre
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void testFindById() throws SQLException, ClassNotFoundException {

        //On test que la personne qui a l'id 1 est la bonne
        Personne p1 = Personne.findById(1);
        assertNotNull(p1, "L'id 1 doit exister");
        assertEquals("Spielberg", p1.getNom());
        assertEquals("Steven", p1.getPrenom());

        //On test que la personne qui a l'id 3 est la bonne
        Personne p3 = Personne.findById(3);
        assertNotNull(p3, "L'id 3 doit exister");
        assertEquals("Kubrick", p3.getNom());
        assertEquals("Stanley", p3.getPrenom());

        //On vérifie que la méthode renvoie bien null s'il n'y a pas d'utilisateur à cette id
        Personne pInconnu = Personne.findById(10);
        assertNull(pInconnu, "L'id 10 ne doit pas exister");
    }

    /**
     * Test la méthode FindByName qui retourne une personne suivant le nom passer en paramètre
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void testFindByName() throws SQLException, ClassNotFoundException {

        ArrayList<Personne> listeScott = Personne.findByName("Scott");
        assertEquals(1, listeScott.size(), "Il ne devrait y avoir qu'un seul Scott");
        assertEquals("Ridley", listeScott.get(0).getPrenom());
        assertEquals(2, listeScott.get(0).getId());

        //On vérifie que la méthode renvoie bien une liste vide si le nom n'existe pas
        ArrayList<Personne> listeInconnue = Personne.findByName("Tarantino");
        assertTrue(listeInconnue.isEmpty(), "La liste devrait être vide pour un nom inconnu");
    }
}