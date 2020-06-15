

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Библиотека</title>
    </head>
    <body>
        <h1>Редактировать новость</h1>
        ${info}<br>
        <a href="index">Главная страница</a><br>
        <a href="showUploadFile">Загрузить фото к новости</a>
        <form action="editProject" method="POST">
            <input type="hidden" name="projectId" value="${project.id}"
            Название новости:<br>
            <input type="text" name="name" value="${project.name}"><br>
            Описание:<br>
            <input type="text" name="description" value="${project.description}"><br>
            Ссылка источник:<br>
            <input type="text" name="git" value="${project.git}"><br>
           
            Фото новости:<br>
            <img src="insertFile/${cover.path}"><br>
            <select name="coverId">
                <c:forEach var="cover" items="${listCovers}">
                    <c:if test="${cover eq selectedCover}">
                        <option selected value="${cover.id}">${cover.name}</option>
                    </c:if>
                    <c:if test="${cover ne selectedCover}">
                        <option value="${cover.id}">${cover.name}</option>
                    </c:if>
                </c:forEach>
            </select>
            <br>
            <input type="submit" value="Изменить">
        </form>
    </body>
</html>
