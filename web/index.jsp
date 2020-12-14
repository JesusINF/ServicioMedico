<%-- 
    Document   : index
    Created on : 13/12/2020, 06:57:16 PM
    Author     : jesus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Inicio de sesión</title>
        <link rel="icon" type="image/vnd.microsoft.icon" href="Imagenes/favicon.ico" sizes="16x16">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="Estilos/EstiloIncioSesion.css">
    </head>
    <body>
        <div class="inicio-sesion">
            <h1> Inicia Sesión </h1>
            <img src="Imagenes/icon-login.png">
            <form class="login-form">
                <input type="text" class="login-username" autofocus required placeholder="Usuario" />
                <input type="password" class="login-password" required placeholder="Contraseña" />
                <input type="submit" name="Login" value="Ingresar" class="login-submit" />
            </form>
        </div>
        <div class="fondo"></div>
    </body>
</html>
