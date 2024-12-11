<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consulter les livres</title>
</head>
<body>
    <h2>Liste des livres disponibles</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Titre</th>
            <th>Auteur</th>
            <th>Genre</th>
            <th>Disponible</th>
        </tr>
        <c:forEach var="livre" items="${livres}">
            <tr>
                <td>${livre.id}</td>
                <td>${livre.titre}</td>
                <td>${livre.auteur}</td>
                <td>${livre.genre}</td>
                <td>${livre.disponible ? "Oui" : "Non"}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
