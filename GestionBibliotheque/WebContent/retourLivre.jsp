<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Retourner un livre</title>
</head>
<body>
    <h2>Retourner un livre</h2>
    <form action="emprunts" method="post">
        <input type="hidden" name="action" value="retourner">
        <label>ID de l'emprunt :</label>
        <input type="text" name="empruntId" required><br>
        <button type="submit">Retourner</button>
    </form>
</body>
</html>
