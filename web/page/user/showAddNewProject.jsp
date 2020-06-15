

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Учебная карта</title>
    </head>
    <body>
        <h1>Добавить новость</h1>
        ${info}<br>
        <a href="index">Главная страница</a><br>
        <a href="showUploadFile">Загрузить фото новости</a>
        <form action="addNewProject" method="POST">
            <div class="form-group">
            Название новости:<br>
            <input class="form-control" type="text" name="name"><br>
            Описание:<br>
            <input class="form-control" type="text" name="description"><br>
            Ссылка на источник:<br>
            <input class="form-control" type="text" name="git"><br>            
            <br>
            Фото новости:<br>
            <select class="form-control" name="coverId">
                <c:forEach var="cover" items="${listCovers}">
                    <option value="${cover.id}">${cover.name}</option>
                </c:forEach>
            </select>
            <br>
            <input class="form-control" type="submit" value="Добавить новость">
            </div>
        </form>
    </body>
</html>
