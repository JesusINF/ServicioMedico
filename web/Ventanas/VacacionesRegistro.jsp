<%-- 
    Document   : inicio
    Created on : 11/01/2021, 05:20:30 PM
    Author     : jesus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es_mx">
    <head>
        <title>Inicio</title>
        <meta http-equiv="Content-Type" content="text/html" charset=UTF-8">
        <script src='https://kit.fontawesome.com/a076d05399.js'></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="icon" type="image/vnd.microsoft.icon" href="Imagenes/favicon.ico" sizes="16x16">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./Estilos/EstiloMenus.css">
        <link rel="stylesheet" href="./Estilos/EstiloLogin.css">
    </head>
    <body>
        <div class="Form inicio-sesion">
            <h1>Registro Vacaciones</h1>
            <form class="login-form" action="MenuVacaciones?accion=Insert" method="POST" autocomplete="off" accept-charset="utf-8">
                <input list="personal" name="personal" class="login-username" placeholder="Busca Personal" required autofocus>
                <datalist id="personal">
                    <c:forEach var="Emp" items="${Lista}">
                        <option value="<c:out value="${Emp}"/>">
                        </c:forEach>
                </datalist>
                <input type="date" id="fecha" name="fecha" class="login-username" required>
                <label for="inicio">Fecha de vacación</label>
                <input type="submit" name="Resgistrar" value="Registrar" class="login-submit" />
            </form>
        </div>
        <div id="popup1" class="<c:out value="${pop}"/> overlay">
            <div class="popup">
                <h2>Registro Erroneo</h2>
                <a class="close" href="MenuVacaciones?accion=Registrar">&times;</a>
                <div class="content">
                    Algo salio mal, intente de nuevo.
                </div>
            </div>
        </div>
        <div class="fondo Sis6"></div>
        <nav class="main-menu" id="<c:out value="${Nivel}"/>">
            <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script><script  src="Scripts/navegacion.js"></script>
        </nav>
    </body>
</html>