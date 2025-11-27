package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Film {
    int id;
    static int id_real;
    String titre;

    public Film(String titre, Personne realisateur) {
        new Film(-1, titre, realisateur.id);
    }

    private Film(int id, String titre, int id_real) {
        this.id = id;
        this.titre = titre;
        Film.id_real = id_real;
    }

    public static Film findById(int id) throws SQLException, ClassNotFoundException {
        Film f = null;
        String SQL = "SELECT * FROM Film WHERE id = ?";
        Connection connect = DBConnection.getConnection();
        PreparedStatement ps = connect.prepareStatement(SQL);
        ps.setInt(1, id);
        ps.execute();
        ResultSet rs = ps.getResultSet();
        if (rs.next()) {
            f = new Film(rs.getInt("id"), rs.getString("titre"), rs.getInt("id_real"));
        }
        return f;
    }

    public static Personne getRealisateur() throws SQLException, ClassNotFoundException {
        return Personne.findById(id_real);
    }

}
