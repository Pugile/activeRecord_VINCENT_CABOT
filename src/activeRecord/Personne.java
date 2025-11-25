
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

}
