<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<header>
    <a href="Controller">Go home</a>
</header>
<main>
    <h1>Edit</h1>
    <form method="POST" class="editform"
          action="Controller?command=edit">
        <input type="hidden" name="url" id="url"
               value="${sol.url}">
        <label for="category">Category:</label>
        <input type="text" name="category" id="category"
        value="${sol.category}">
        <label for="question">Question:</label>
        <textarea name="question" id="question">
                  ${sol.question}
        </textarea>
        <label for="answer">Answer:</label>
        <textarea name="answer" id="answer">
            ${sol.answer}
        </textarea>

        <input type="submit" value="Save Changes">
    </form>
</main>
<footer>
    <p>Disclaimer: Dit zijn niet altijd de juiste antwoorden!!!
        <br>Er zijn vaak meerdere mogelijkheden!!!
        <br>Fout gevonden? laat het mij weten he makker :)
        <br>Stuur een sappig mailtje naar
        arthur.joppart@student.ucll.be
    </p>
</footer>
</body>
</html>