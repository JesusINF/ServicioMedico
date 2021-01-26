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
            <h1>Registro Empleados</h1>
            <form class="login-form" action="MenuEmpleados?accion=Insert" method="POST" autocomplete="off" accept-charset="utf-8">
                <input type="text" id="Nombre" name="Nombre" class="login-username" autofocus required maxlength="50" placeholder="Nombre" />
                <input type="text" id="Direccion" name="Direccion" class="login-username"  required maxlength="50" placeholder="Dirección" />
                <input type="text" id="Telefono" name="Telefono" class="login-username" maxlength="10" required placeholder="Telefono" />
                <input type="text" id="Cp" name="Cp" class="login-username" required maxlength="5" placeholder="Código Postal" />
                <input type="text" id="Curp" name="Curp" class="login-username" required maxlength="18"placeholder="CURP" />
                <input type="text" id="Nss" name="Nss" class="login-username" required maxlength="11" placeholder="Numero de Seguro Social" />
                <select id="Tipo" name="Tipo" class="login-username">
                    <option value="Administrador">Administrador</option>
                    <option value="Programador">Programador</option>
                    <option value="Enfermera">Enfermera</option>
                    <option value="Recepcionista">Recepcionista</option>
                    <option value="Otro">Otro</option>
                </select>
                <input type="text" id="Usuario" name="Usuario" class="login-username"  required maxlength="50" placeholder="Usuario" />
                <input type="password" id="Password" name="Password" class="login-password" required placeholder="Contraseña" />
                <input type="submit" name="Resgistrar" value="Registrar" class="login-submit" />
            </form>
        </div>
        <div class="fondo"></div>
        <nav class="main-menu" id="<c:out value="${Nivel}"/>">
            <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script><script  src="Scripts/navegacion.js"></script>
        </nav>
    </body>
</html>