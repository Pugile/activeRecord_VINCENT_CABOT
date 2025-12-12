import activeRecord.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test les méthodes de la classe Film
 */
public class TestFilm {

    Personne realisateur;

    /**
     * Ce lance avant chaque test et permet de construire une table Film et une table Personne
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        //On supprime les tables existantent pour partir sur une base nouvelle.
        Film.deleteTable();
        Personne.deleteTable();

        // On crée la table Personne puis la table Film.
        Personne.createTable();
        Film.createTable();

        realisateur = new Personne("Spielberg", "Steven");
        realisateur.save();
    }

    /**
     * Supprime après chaque test les tables Personne et Film
     */
    @AfterEach
    public void supprTable() throws SQLException, ClassNotFoundException {
        Film.deleteTable();
        Personne.deleteTable();
    }

    /**
     * Test de la méthode findById qui retourne un film suivant son id
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws RealisateurAbsentException
     */
    @Test
    public void testFindById() throws SQLException, ClassNotFoundException, RealisateurAbsentException {
        Film f = new Film("Arche perdue", realisateur);
        f.save();

        assertNotEquals(-1, f.getId(), "Le film devrait avoir un ID après sauvegarde");

        Film fRecupere = Film.findById(f.getId());
        assertEquals("Arche perdue", fRecupere.getTitre());
        assertEquals(realisateur.getId(), fRecupere.getRealisateur().getId());
    }

    /**
     * Test du fonctionnement de l'exception RealisateurAbsentException
     */
    @Test
    public void testRealisateurAbsentException() {
        Personne p = new Personne("Inconnu", "Jean");
        //Ici on ne save pas  p donc il ne doit pas y aboir de réalisateur

        Film f = new Film("Arche perdue", p);

        assertThrows(RealisateurAbsentException.class, () -> {
            f.save();
        }, "Une exception devrait être levée si le réalisateur n'a pas d'ID en base");
    }

    /**
     * Test de la méthode FindByRealisateur qui renvoit la liste des film d'un réalisateur
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws RealisateurAbsentException
     */
    @Test
    public void testFindByRealisateur() throws SQLException, ClassNotFoundException, RealisateurAbsentException {
        Film f1 = new Film("Arche perdue", realisateur);
        f1.save();
        Film f2 = new Film("Alien", realisateur);
        f2.save();

        ArrayList<Film> films = Film.findByRealisateur(realisateur);
        assertEquals(2, films.size(), "Il devrait y avoir 2 films pour ce réalisateur");
    }
}