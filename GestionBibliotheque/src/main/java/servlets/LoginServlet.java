package servlets;

import dao.UtilisateurDAO;
import Model.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UtilisateurDAO utilisateurDAO;

    @Override
    public void init() throws ServletException {
        // Récupère la connexion à la base de données depuis le contexte de l'application
        Connection connection = (Connection) getServletContext().getAttribute("dbConnection");
        if (connection == null) {
            throw new ServletException("Connexion à la base de données non initialisée.");
        }
        utilisateurDAO = new UtilisateurDAO(connection);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Authentifie l'utilisateur via la DAO
            Utilisateur utilisateur = utilisateurDAO.authentifierUtilisateur(email, password);

            if (utilisateur != null) {
                // Authentification réussie
                HttpSession session = request.getSession();
                session.setAttribute("utilisateur", utilisateur);

                // Redirection selon le rôle de l'utilisateur
                if ("admin".equals(utilisateur.getRole())) {
                    response.sendRedirect("adminDashboard.jsp");
                } else {
                    response.sendRedirect("readerDashboard.jsp");
                }
            } else {
                // Authentification échouée
                request.setAttribute("erreur", "Email ou mot de passe incorrect !");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            // Gère les exceptions SQL
            throw new ServletException("Erreur lors de l'authentification de l'utilisateur.", e);
        }
    }
}

