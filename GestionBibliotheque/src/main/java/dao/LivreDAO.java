package dao;


import Model.Livre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {
    private Connection connection;

    // Constructeur
    public LivreDAO(Connection connection) {
        this.connection = connection;
    }

    // Ajouter un livre
    public void ajouterLivre(Livre livre) throws SQLException {
        String sql = "INSERT INTO livres (titre, auteur, genre, disponibilite) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, livre.getTitre());
            statement.setString(2, livre.getAuteur());
            statement.setString(3, livre.getGenre());
            statement.setBoolean(4, livre.isDisponible());
            statement.executeUpdate();
        }
    }

    // Obtenir tous les livres
    public List<Livre> getAllBooks() throws SQLException {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livres";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                livres.add(new Livre(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("auteur"),
                        resultSet.getString("genre"),
                        resultSet.getBoolean("disponibilite")
                ));
            }
        }
        return livres;
    }

    // Supprimer un livre
    public void supprimerLivre(int id) throws SQLException {
        String sql = "DELETE FROM livres WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Mettre à jour un livre
    public void mettreAJourLivre(Livre livre) throws SQLException {
        String sql = "UPDATE livres SET titre = ?, auteur = ?, genre = ?, disponibilite = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, livre.getTitre());
            statement.setString(2, livre.getAuteur());
            statement.setString(3, livre.getGenre());
            statement.setBoolean(4, livre.isDisponible());
            statement.setInt(5, livre.getId());
            statement.executeUpdate();
        }
    }
}
