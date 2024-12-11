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
import java.util.List;

@WebServlet("/historique")
public class HistoriqueServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Vérifie si l'utilisateur est connecté
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("utilisateur") != null) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            int utilisateurId = utilisateur.getId();

            try {
                // Récupère les emprunts de l'utilisateur
                List<Emprunt> emprunts = empruntDAO.obtenirEmpruntsParUtilisateur(utilisateurId);
                request.setAttribute("emprunts", emprunts);

                // Redirige vers la page historique des emprunts
                request.getRequestDispatcher("historiqueEmprunts.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Erreur lors de la récupération des emprunts.", e);
            }
        } else {
            // Redirige vers la page de connexion si l'utilisateur n'est pas connecté
            response.sendRedirect("login.jsp");
        }
    }
}

