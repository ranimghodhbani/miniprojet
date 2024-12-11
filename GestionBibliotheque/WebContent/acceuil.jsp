<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="Model.Utilisateur" %>
<!DOCTYPE html>
<html>
<head>
    <title>Accueil</title>
</head>
<body>
    <h2>Bienvenue dans la bibliothèque</h2>
    <% 
        if (session != null) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            if (utilisateur != null) {
                out.print("Bonjour, " + utilisateur.getNom() + " (" + utilisateur.getRole() + ")");
            }
        }
    %>
    <ul>
        <% if (session != null && session.getAttribute("utilisateur") != null && ((Utilisateur) session.getAttribute("utilisateur")).getRole().equals("admin")) { %>
            <li><a href="livres?action=list">Gérer les livres</a></li>
        <% } else { %>
            <li><a href="livres?action=list">Consulter les livres</a></li>
        <% } %>
        <li><a href="historique">Historique des emprunts</a></li>
        <li><a href="logout.jsp">Déconnexion</a></li>
    </ul>
</body>
</html>
