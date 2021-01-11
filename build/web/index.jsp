<%-- 
    Document   : index
    Created on : 13/12/2020, 06:57:16 PM
    Author     : jesus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es_mx">
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
            <p class="<c:out value="${Login}"/>">*El usuario o contraseña que ingresaste es incorrecta</p>
            <form class="login-form" action="Login?accion=validar" method="POST" autocomplete="off">
                <input type="text" id="usuario" name="usuario" class="login-username" autofocus required placeholder="Usuario" />
                <input type="password" id="password" name="password" class="login-password" required placeholder="Contraseña" />
                <input type="submit" name="Login" value="Ingresar" class="login-submit" />
            </form>
        </div>
        <div class="fondo"></div>
    </body>
</html>