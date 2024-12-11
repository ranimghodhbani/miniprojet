<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des livres</title>
</head>
<body>
    <h2>Gestion des livres</h2>
    <form action="livres" method="post">
        <input type="hidden" name="action" value="add">
        <label>Titre :</label>
        <input type="text" name="titre" required><br>
        <label>Auteur :</label>
        <input type="text" name="auteur" required><br>
        <label>Genre :</label>
        <input type="text" name="genre" required><br>
        <label>Disponible :</label>
        <select name="disponibilite">
            <option value="true">Oui</option>
            <option value="false">Non</option>
        </select><br>
        <button type="submit">Ajouter</button>
    </form>
    <h3>Liste des livres</h3>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Titre</th>
            <th>Auteur</th>
            <th>Genre</th>
            <th>Disponible</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="livre" items="${livres}">
            <tr>
                <td>${livre.id}</td>
                <td>${livre.titre}</td>
                <td>${livre.auteur}</td>
                <td>${livre.genre}</td>
                <td>${livre.disponible}</td>
                <td>
                    <a href="livres?action=delete&id=${livre.id}">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
