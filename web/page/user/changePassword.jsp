

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Изменение пароля</title>
    </head>
    <body>
        <h3><a href="index">Главная</a></h3>
        <h1>Изменить пароль</h1>
        <p>${info}</p>
        <p>Здравствуйте, ${username}, вы вошли как ${login}</p>
        <form action="changePassword" method="POST">
            <br>
            Введите действующий пароль:
            <input class="form-control" type="password" name="oldPassword">
            <br>
            Введите новый пароль:
            <input class="form-control" type="password" name="newPassword1">
            <br>
            Повторите пароль:
            <input class="form-control" type="password" name="newPassword2">
            <br><br>
            <input class="form-control" type="submit" value="Изменить пароль">
        </form>
    </body>
</html>
