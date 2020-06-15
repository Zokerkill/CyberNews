            <H1 class="text-center">Добро пожаловать в CyberNews</H1>
            <c:if test="${info ne null}">
                <div class="alert alert-primary" role="alert">${info}</div>
            </c:if>
                <h4 class="text-center">Для удобного использования нашими услугами необходимо войти в систему.</h4>
                <div class="dropdown text-center">
                    <button class="btn btn-ligh dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Здесь вы можете войти <br>или зарегистрироваться
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                        <a class="dropdown-item" href="showLogin">Войти</a>
                        <a class="dropdown-item" href="showRegistration">Зарегистрироваться</a>
                    </div>
                </div>
        
