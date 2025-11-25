import activeRecord.DBConnection;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestDBConnection {

    @Test
    public void testSingleton() throws SQLException, ClassNotFoundException {

        Connection c1 = DBConnection.getConnection();
        Connection c2 = DBConnection.getConnection();
        assertSame(c1, c2, "Les deux appels devraient retourner la même instance de Connection");

        assertNotNull(c1);
    }

    @Test
    public void testChangementBase() throws SQLException, ClassNotFoundException {

        Connection c1 = DBConnection.getConnection();
        DBConnection.setNomDB("testpersonne2"); //J'ai créer une deuxième base de donnée pour tester cette méthode
        Connection c2 = DBConnection.getConnection();

        assertNotSame(c1, c2, "Après changement de nom de DB, l'instance devrait être nouvelle");

        DBConnection.setNomDB("testpersonne");
    }
}