<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${category}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <header>
        <a href="Controller">Go home</a>
    </header>
    <main>
        <h1><c:out value="${category}"/></h1>
        <c:forEach items="${solutions}" var="sol">
            <div class="sol">
                <c:if test="${user != null}">
                    <div class="floatierighty">
                <form class="delete" method="POST"
                      action="Controller?command=delete&url=${sol.url}">
                    <input type="submit" value="DELETE">
                </form>
                <form class="edit" method="POST"
                      action="Controller?command=editredirect&url=${sol.url}">
                    <input type="submit" value="EDIT">
                </form>
                    </div>
                </c:if>
                <div class="question">
                    <p>${sol.question}</p>
                </div>
                <div class="answer">
                    <p>${sol.answer} </p>
                </div>
            </div>
        </c:forEach>
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
