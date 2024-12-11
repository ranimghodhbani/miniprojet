<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Historique des emprunts</title>
</head>
<body>
    <h2>Historique des emprunts</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>ID du livre</th>
            <th>Date d'emprunt</th>
            <th>Date de retour</th>
        </tr>
        <c:forEach var="emprunt" items="${emprunts}">
            <tr>
                <td>${emprunt.id}</td>
                <td>${emprunt.livreId}</td>
                <td>${emprunt.dateEmprunt}</td>
                <td>${emprunt.dateRetour != null ? emprunt.dateRetour : "Non retourné"}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
