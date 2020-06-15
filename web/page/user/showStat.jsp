

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Новостная карта</title>
    </head>
    <body>
        <h1>Статистика - новости</h1>
        ${info}<br>
        <a href="index">Главная</a><br>
        <ul class="list-group">
            
        </ul>
         <form action="showStat" method="POST">
             <hr>
            Период от:
            
            ${dateFrom}
            <br>День:
            <select class="form-control" name="fromDay">
                <c:forEach begin="1" end="31" var="i">
                    <option value="${i}" <c:if test="${i eq fromDay}">selected</c:if>>${i}</option>
                </c:forEach>
            </select>
            Месяц:
            <select class="form-control" name="fromMonth">
                <c:forEach begin="1" end="12" var="i">
                    <option value="${i}" <c:if test="${i eq fromMonth}">selected</c:if>>${i}</option>
                </c:forEach>
            </select>
            Год:
            <select class="form-control" name="fromYear">
                <c:forEach begin="2014" end="2019" var="i">
                    <option value="${i}" <c:if test="${i eq fromYear}">selected</c:if>>${i}</option>
                </c:forEach>
            </select>
            <br> Период до: ${dateTo}
            День:
            <select class="form-control" name="toDay">
                <c:forEach begin="1" end="31" var="i">
                    <option value="${i}" <c:if test="${i eq toDay}">selected</c:if>>${i}</option>
                </c:forEach>
            </select>
            Месяц:
            <select class="form-control" name="toMonth">
                <c:forEach begin="1" end="12" var="i">
                    <option value="${i}" <c:if test="${i eq toMonth}">selected</c:if>>${i}</option>
                </c:forEach>
            </select>
            Год:
            <select class="form-control" name="toYear">
                <c:forEach begin="2014" end="2019" var="i">
                    <option value="${i}" <c:if test="${i eq toYear}">selected</c:if>>${i}</option>
                </c:forEach>
            </select>
            <br>
            <input class="form-control" type="submit" name="timeRange" value="Показать">
        
        <br>
        <h4>В период от <b style="color:red;">${fromDay}.${fromMonth}.${fromYear}</b> до <b style="color:red;">${toDay}.${toMonth}.${toYear}</b> были добавлены следующие проекты:</h4>
        <c:forEach var="history" items="${listHistories}">
            <h2>${history.project.name} (<a href="${history.project.git}">${history.project.git}</a>) </h2>
            
            <p> Новость была добавлена - ${history.dateBegin}</p>
        </c:forEach>
    </body>
</html>
