

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Загрузка файла</title>
    </head>
    <body>
        <h1>Загрузка файла!</h1>
        <form action="uploadFile" method="POST" enctype="multipart/form-data">
            <label>Название фото:</label>
            <input class="form-control" type="text" name="description"><br>
            <input class="form-control" type="file" name="file"><br>
            <input class="form-control" type="submit" value="Загрузить">
        </form>
        
    </body>
</html>
