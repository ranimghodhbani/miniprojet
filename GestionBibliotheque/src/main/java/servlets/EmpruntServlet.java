package servlets;




import dao.EmpruntDAO;
import Model.Emprunt;
import Model.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("/emprunts")
public class EmpruntServlet extends HttpServlet {
    private EmpruntDAO empruntDAO;

    @Override
    public void init() throws ServletException {
        // Récupère la connexion à la base de données depuis le contexte de l'application
        Connection connection = (Connection) getServletContext().getAttribute("dbConnection");
        if (connection == null) {
            throw new ServletException("Connexion à la base de données non initialisée.");
        }
        empruntDAO = new EmpruntDAO(connection);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("emprunter".equals(action)) {
                handleEmprunter(request, response);
            } else if ("retourner".equals(action)) {
                handleRetourner(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue : " + action);
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du traitement de l'action : " + action, e);
        }
    }

    private void handleEmprunter(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        // Vérifie la présence des paramètres nécessaires
        String utilisateurIdParam = request.getParameter("utilisateurId");
        String livreIdParam = request.getParameter("livreId");

        if (utilisateurIdParam == null || livreIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres manquants pour emprunter un livre.");
            return;
        }

        int utilisateurId = Integer.parseInt(utilisateurIdParam);
        int livreId = Integer.parseInt(livreIdParam);

        Emprunt emprunt = new Emprunt();
        emprunt.setUtilisateurId(utilisateurId);
        emprunt.setLivreId(livreId);
        emprunt.setDateEmprunt(new Date());

        empruntDAO.ajouterEmprunt(emprunt);
        response.sendRedirect("livres?action=list");
    }

    private void handleRetourner(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        // Vérifie la présence du paramètre nécessaire
        String empruntIdParam = request.getParameter("empruntId");

        if (empruntIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre manquant pour retourner un livre.");
            return;
        }

        int empruntId = Integer.parseInt(empruntIdParam);

        // Conversion de java.util.Date en java.sql.Date
        java.sql.Date dateRetour = new java.sql.Date(new Date().getTime());

        empruntDAO.retournerLivre(empruntId, dateRetour);

        response.sendRedirect("emprunts?action=list");
    }
}
