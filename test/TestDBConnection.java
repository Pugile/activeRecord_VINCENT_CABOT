import activeRecord.DBConnection;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestDBConnection {

    /**
     * Test qui permet de vérifier qu'il y a toujours 1 seule instance de Connection.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void testSingleton() throws SQLException, ClassNotFoundException {

        Connection c1 = DBConnection.getConnection();
        Connection c2 = DBConnection.getConnection();
        assertSame(c1, c2, "Les deux appels devraient retourner la même instance de Connection");

        assertNotNull(c1);
    }

    /**
     * Test la méthode setNomDB qui change le nom de la base de données et crée une nouvelle connexion
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void testChangementBase() throws SQLException, ClassNotFoundException {

        Connection c1 = DBConnection.getConnection();
        DBConnection.setNomDB("testpersonne2"); //J'ai créer une deuxième base de données pour tester cette méthode
        Connection c2 = DBConnection.getConnection();

        assertNotSame(c1, c2, "Après changement de nom de DB, l'instance devrait être nouvelle");

        DBConnection.setNomDB("testpersonne");
    }
}