
       <H3 class="text-center">Вы вошли как обычный пользователь</H3>
            <c:if test="${info ne null}">
                <div class="alert alert-primary" role="alert">${info}</div>
            </c:if>
        <a href="logout">Выйти</a><br>
        <a href="showListProjects">Показать список новостей</a><br>
        <a href="showAddNewProject">Добавить новость</a><br>  
        <a href="showStat">Показать статистику</a><br>
        <a href="showChangePassword">Изменить свой пароль</a><br>
        
    