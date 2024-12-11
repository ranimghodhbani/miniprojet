package servlets;



import dao.LivreDAO;
import Model.Livre;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/livres")
public class LivreServlet extends HttpServlet {
    private LivreDAO livreDAO;

    @Override
    public void init() throws ServletException {
        // Récupère la connexion à la base de données depuis le contexte de l'application
        Connection connection = (Connection) getServletContext().getAttribute("dbConnection");
        if (connection == null) {
            throw new ServletException("Connexion à la base de données non initialisée.");
        }
        livreDAO = new LivreDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("list".equals(action)) {
                handleList(request, response);
            } else if ("delete".equals(action)) {
                handleDelete(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue : " + action);
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du traitement de l'action : " + action, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                handleAdd(request, response);
            } else if ("update".equals(action)) {
                handleUpdate(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue : " + action);
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du traitement de l'action : " + action, e);
        }
    }

    private void handleList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Livre> livres = livreDAO.getAllBooks();
        request.setAttribute("livres", livres);
        request.getRequestDispatcher("listBooks.jsp").forward(request, response);
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        livreDAO.supprimerLivre(id);
        response.sendRedirect("livres?action=list");
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        String genre = request.getParameter("genre");
        boolean disponibilite = Boolean.parseBoolean(request.getParameter("disponibilite"));

        Livre livre = new Livre(0, titre, auteur, genre, disponibilite);
        livreDAO.ajouterLivre(livre);
        response.sendRedirect("livres?action=list");
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        String genre = request.getParameter("genre");
        boolean disponibilite = Boolean.parseBoolean(request.getParameter("disponibilite"));

        Livre livre = new Livre(id, titre, auteur, genre, disponibilite);
        livreDAO.mettreAJourLivre(livre);
        response.sendRedirect("livres?action=list");
    }
}
