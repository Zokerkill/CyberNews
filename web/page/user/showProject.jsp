

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Новость - ${project.name} </title>
    </head>
    <body>
        <h1>Новостная карта - новость</h1>
        <p>${info}</p>
        <a href="showListProjects">К списку</a><br>
        Скриншот: <br>
        <img src="insertFile/${cover.path}"><br>
        Название новости: <h3>${project.name}</h3><br>
        Описание: ${project.description}<br>
        Ссылка на источник: <a href="${project.git}">${project.git}</a><br>
        
            <a href="showEditProject?projectId=${project.id}">Редактировать</a>
      
        
        
        
    </body>
</html>
