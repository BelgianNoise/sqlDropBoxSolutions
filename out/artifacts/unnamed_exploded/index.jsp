<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>SQL DROPBOX OPLOSSINGEN</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="style.css">
  </head>

  <body>
        <header>
            <a href="http://bananass.myqnapcloud.com:9080/SQLBot/">Voeg
                oplossingen toe!</a>
        </header>
      <main>
          <c:if test="${foutje != null}">
          <div class="error">
              <p>${foutje}</p>
          </div>
          </c:if>
          <div id="cats">
          <c:forEach items="${categories}" var="cat">
              <a href="Controller?command=getbycat&category=${cat}">
                  <c:out value="${cat}"/>
              </a>
          </c:forEach>
          </div>

          <form id="zoeken" method="POST"
                action="Controller?command=searchbyurl">
              <p>Plak hier de url van de oefening die je zoekt.
                  Heel de link!<br>(vb: .../maak/17/oefening/149)</p>
              <input type="text" name="urll" id="urll"
                     placeholder="url">
              <input type="submit" value="Zoek oplossing">
          </form>

          <c:if test="${user == null}">
          <form method="POST" action="Controller?command=login">
              <p>Upper-management kan inloggen:</p>
              <input type="text" name="user" id="user" placeholder="user">
            <input type="password" name="password" id="password"
            placeholder="password">
            <input type="submit" value="Log In">
        </form>
          </c:if>
        <c:if test="${user != null}">
            <form method="POST" action="Controller?command=add">
                <input type="text" name="url" id="url"
                       placeholder="url">
                <input type="text" name="question" id="question"
                placeholder="question">
                <input type="text" name="answer" id="answer"
                placeholder="answer">
                <input type="text" list="categoriess" id="category"
                name="category">
                <datalist id="categoriess">
                    <c:forEach items="${categories}" var="cat">
                        <option>${cat}</option>
                    </c:forEach>
                </datalist>
                <input type="submit" value="Voeg dees schoon vraagskie toe">
            </form>
        </c:if>
          <p></p>
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