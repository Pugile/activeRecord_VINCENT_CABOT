package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Film {
    protected int id;
    protected int id_real;
    protected String titre;

    public Film(String titre, Personne realisateur) {
        this.id = -1;
        this.titre = titre;
        this.id_real = realisateur.getId();
    }

    private Film(int id, String titre, int id_real) {
        this.id = id;
        this.titre = titre;
        this.id_real = id_real;
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

    public Personne getRealisateur() throws SQLException, ClassNotFoundException {
        return Personne.findById(id_real);
    }

    public static void createTable() throws SQLException, ClassNotFoundException {
        String SQL = "CREATE TABLE IF NOT EXISTS Film ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "titre VARCHAR(100) NOT NULL,"
                + "id_real INT,"
                + "FOREIGN KEY (id_real) REFERENCES Personne(id)" // Contrainte de clé étrangère recommandée
                + ");";
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep = connect.prepareStatement(SQL);
        prep.execute();
    }

    public static void deleteTable() throws SQLException, ClassNotFoundException {
        String SQL = "DROP TABLE IF EXISTS Film;";
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep = connect.prepareStatement(SQL);
        prep.execute();
    }

    public void delete() throws SQLException, ClassNotFoundException {
        String SQL = "DELETE FROM Film WHERE id = ?";
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep = connect.prepareStatement(SQL);
        prep.setInt(1, this.id);
        prep.execute();
        this.id = -1;
    }

    public void save() throws SQLException, ClassNotFoundException, RealisateurAbsentException {
        if (this.id_real == -1) {
            throw new RealisateurAbsentException("Le réalisateur n'existe pas dans la base");
        }
        if (this.id == -1) {
            saveNew();
        } else {
            update();
        }
    }

    private void saveNew() throws SQLException, ClassNotFoundException {
        String SQL = "INSERT INTO Film (titre, id_real) VALUES (?, ?);";
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep = connect.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_real);
        prep.executeUpdate();
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()) {
            this.id = rs.getInt(1);
        }
    }

    private void update() throws SQLException, ClassNotFoundException {
        String SQL = "UPDATE Film SET titre = ?, id_real = ? WHERE id = ?;";
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep = connect.prepareStatement(SQL);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_real);
        prep.setInt(3, this.id);
        prep.executeUpdate();
    }

    public static ArrayList<Film> findByRealisateur(Personne p) throws SQLException, ClassNotFoundException {
        ArrayList<Film> films = new ArrayList<>();
        String SQL = "SELECT * FROM Film WHERE id_real = ?";
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep = connect.prepareStatement(SQL);
        prep.setInt(1, p.getId());
        prep.execute();
        ResultSet rs = prep.getResultSet();

        while (rs.next()) {
            films.add(new Film(rs.getInt("id"), rs.getString("titre"), rs.getInt("id_real")));
        }
        return films;
    }

    public int getId() {
        return id;
    }
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

}
