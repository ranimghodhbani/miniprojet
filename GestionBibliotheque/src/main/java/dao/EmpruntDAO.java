package dao;


import Model.Emprunt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAO {
    private Connection connection;

    // Constructeur
    public EmpruntDAO(Connection connection) {
        this.connection = connection;
    }

    // Ajouter un emprunt
    public void ajouterEmprunt(Emprunt emprunt) throws SQLException {
        String sql = "INSERT INTO emprunts (utilisateur_id, livre_id, date_emprunt, date_retour) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, emprunt.getUtilisateurId());
            statement.setInt(2, emprunt.getLivreId());
            statement.setDate(3, new java.sql.Date(emprunt.getDateEmprunt().getTime()));
            statement.setDate(4, emprunt.getDateRetour() != null ? new java.sql.Date(emprunt.getDateRetour().getTime()) : null);
            statement.executeUpdate();
        }
    }

    // Obtenir les emprunts par utilisateur
    public List<Emprunt> obtenirEmpruntsParUtilisateur(int utilisateurId) throws SQLException {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT * FROM emprunts WHERE utilisateur_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, utilisateurId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                emprunts.add(new Emprunt(
                        resultSet.getInt("id"),
                        resultSet.getInt("utilisateur_id"),
                        resultSet.getInt("livre_id"),
                        resultSet.getDate("date_emprunt"),
                        resultSet.getDate("date_retour")
                ));
            }
        }
        return emprunts;
    }

    // Mettre à jour la date de retour
    public void retournerLivre(int empruntId, Date dateRetour) throws SQLException {
        String sql = "UPDATE emprunts SET date_retour = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(dateRetour.getTime()));
            statement.setInt(2, empruntId);
            statement.executeUpdate();
        }
    }
}
