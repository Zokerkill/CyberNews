     <div class="col-md-5 offset-md-3 align-self-center">        
        <h1 class="text-center">Войдите!</h1>
        <p class="text-center">${info}</p>
        
        <form action="login" method="POST">
            <div class="form-group">
              <label for="login">Логин:</label>
              <input type="text" class="form-control" id="login" name="login" placeholder="Login">
            </div>
            <div class="form-group">
              <label for="password">Пароль:</label>
              <input type="password" class="form-control" id="password" name="password" placeholder="Password">
            </div>
            
            <button type="submit" class="btn btn-primary">Войти</button>
        </form>
     </div>
                