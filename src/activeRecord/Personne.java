
package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Personne {

    int id;
    String nom;
    String prenom;

    public Personne( String nom, String prenom) {
        this.id = -1;
        this.nom = nom;
        this.prenom = prenom;
    }

    public static ArrayList<Personne> findAll() throws SQLException, ClassNotFoundException {
        ArrayList<Personne> personnes = new ArrayList<>();
        String SQL = "SELECT * FROM Personne";
        Connection connect = DBConnection.getConnection();

        PreparedStatement prep = connect.prepareStatement(SQL);
        prep.execute();
        ResultSet rs = prep.getResultSet();

        while (rs.next()) {
            Personne p = new Personne(rs.getString("nom"), rs.getString("prenom"));
            p.id = rs.getInt("id");
            personnes.add(p);
        }
        return personnes;
    }

    public static Personne findById(int id) throws SQLException, ClassNotFoundException {
        Personne p = null;
        String SQL = "SELECT * FROM Personne WHERE id = ?";
        Connection connect = DBConnection.getConnection();

        PreparedStatement prep = connect.prepareStatement(SQL);
        prep.setInt(1, id);
        prep.execute();
        ResultSet rs = prep.getResultSet();

        if (rs.next()) {
            p = new Personne(rs.getString("nom"), rs.getString("prenom"));
            p.id = rs.getInt("id");
        }
        return p;
    }

    public static ArrayList<Personne> findByName(String nom) throws SQLException, ClassNotFoundException {
        ArrayList<Personne> personnes = new ArrayList<>();
        String SQL = "SELECT * FROM Personne WHERE nom = ?";
        Connection connect = DBConnection.getConnection();

        PreparedStatement prep = connect.prepareStatement(SQL);
        prep.setString(1, nom);
        prep.execute();
        ResultSet rs = prep.getResultSet();

        while (rs.next()) {
            Personne p = new Personne(rs.getString("nom"), rs.getString("prenom"));
            p.id = rs.getInt("id");
            personnes.add(p);
        }
        return personnes;
    }

    public static void createTable() throws SQLException, ClassNotFoundException {
        String SQL = "CREATE TABLE IF NOT EXISTS Personne ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nom VARCHAR(100) NOT NULL,"
                + "prenom VARCHAR(100) NOT NULL"
                + ");";
        Connection connect = DBConnection.getConnection();

        PreparedStatement prep = connect.prepareStatement(SQL);
        prep.execute();
    }

    public static void deleteTable() throws SQLException, ClassNotFoundException {
        String SQL = "DROP TABLE IF EXISTS Personne;";
        Connection connect = DBConnection.getConnection();

        PreparedStatement prep = connect.prepareStatement(SQL);
        prep.execute();
    }

    public void delete() throws SQLException, ClassNotFoundException {
        String SQL = "DELETE FROM Personne WHERE id = ?";
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep = connect.prepareStatement(SQL);
        prep.setInt(1, this.id);
        prep.execute();
        this.id = -1;
    }

    public void save() throws SQLException, ClassNotFoundException {
        Connection connect = DBConnection.getConnection();
        if (this.id == -1) {
            saveNew();
        } else {
            update();
        }
    }

    private void saveNew() throws SQLException, ClassNotFoundException {
        String SQL = "INSERT INTO Personne (nom, prenom) VALUES (?, ?);";
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep = connect.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.executeUpdate();
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()) {
            this.id = rs.getInt(1);
        }
    }

    private void update() throws SQLException, ClassNotFoundException {
        String SQL = "UPDATE Personne SET nom = ?, prenom = ? WHERE id = ?;";
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep = connect.prepareStatement(SQL);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.setInt(3, this.id);
        prep.executeUpdate();
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
}
