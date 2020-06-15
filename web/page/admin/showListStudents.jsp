

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Библиотека</title>
    </head>
    <body>
        <h1>Список читателей</h1>
        ${info}<br>
        <a href="index">Главная страница</a><br>
        <ul>
            <c:forEach var="reader" items="${listReaders}">
                <li>${reader.name}, ${reader.surname}, ${reader.email}
            </c:forEach>
        </ul>
    </body>
</html>
