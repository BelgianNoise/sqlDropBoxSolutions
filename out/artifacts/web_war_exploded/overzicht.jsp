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
                <a class="delete"
                   href="Controller?command=delete&url=${sol.url}">
                    DELETE
                </a>
                <a class="edit"
                    href="Controller?command=editredirect&url=${sol.url}">
                    EDIT
                </a>
                </c:if>
                <div class="question">
                    <p><c:out value="${sol.question}"/> </p>
                </div>
                <div class="answer">
                    <p><c:out value="${sol.answer}"/> </p>
                </div>
            </div>
        </c:forEach>
    </main>
    <footer>
        <p>Disclaimer: Dit zijn niet altijd de juiste antwoorden!!!
            <br>Er zijn vaak meerdere mogelijkheden!!!
            <br>Fout gevonden? laat het mij weten he makker :)</p>
    </footer>
</body>
</html>
