

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Новостная карта</title>
    </head>
    <body>
        <h1>Список новостей</h1>
        ${info}<br>
        <a href="index">Главная</a><br>
        <ul class="list-group">
            <c:forEach var="project" items="${listProjects}">
                <li class="list-group-item"><a href="showProject?projectId=${project.project.id}"><h3>${project.project.name}</h3></a>  
                    <a href="showEditProject?projectId=${project.project.id}">Изменить</a> <i class="fas fa-pen-square"></i><br>
                    <a href="deleteProject?projectId=${project.project.id}">Удалить <i class="far fa-trash-alt"></i></a></li>
                   
            </c:forEach>
        </ul>
    </body>
</html>
