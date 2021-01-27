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
            <h1>Actualización Pacientes</h1>
            <div class="<c:out value="${Actualiza}"/>">
                <form class="login-form" action="MenuPacientes?accion=Busca"method="POST" autocomplete="off" accept-charset="utf-8">
                    <input list="browsers" name="browsers" class="login-username">
                    <datalist id="browsers">
                        <c:forEach var="Emp" items="${Lista}">
                            <option value="<c:out value="${Emp}"/>">
                            </c:forEach>
                    </datalist>
                    <input type="submit" name="Buscar" value="Buscar" class="login-submit" />
                </form>
            </div>
            <div class="<c:out value="${Busca}"/>">
                <form class="login-form" action="MenuPacientes?accion=Actualiza" method="POST" autocomplete="off" accept-charset="utf-8">
                    <input type="text" id="Nombre" name="Nombre" class="login-username" autofocus required maxlength="50" placeholder="Nombre" value="<c:out value="${Emp.nombre}"/>"/>
                    <input type="text" id="Direccion" name="Direccion" class="login-username"  required maxlength="50" placeholder="Dirección" value="<c:out value="${Emp.direccion}"/>"/>
                    <input type="text" id="Telefono" name="Telefono" class="login-username" maxlength="10" required placeholder="Telefono" value="<c:out value="${Emp.telefono}"/>"/>
                    <input type="text" id="Cp" name="Cp" class="login-username" required maxlength="5" placeholder="Código Postal" value="<c:out value="${Emp.cp}"/>"/>
                    <input type="text" id="Curp" name="Curp" class="login-username" required maxlength="18"placeholder="CURP" value="<c:out value="${Emp.curp}"/>"/>
                    <input type="text" id="Nss" name="Nss" class="login-username" required maxlength="11" placeholder="Numero de Seguro Social" value="<c:out value="${Emp.nss}"/>"/>
                    <input type="text" id="Padecimiento" name="Padecimiento" class="login-username" required maxlength="100" placeholder="Padecimiento" value="<c:out value="${Emp.padecimiento}"/>"/>
                    <input list="browsers" name="browsers" class="login-username" value="<c:out value="${DatoMedico}"/>">
                    <datalist id="browsers" >
                        <c:forEach var="Emp" items="${Lista}">
                            <option value="<c:out value="${Emp}"/>">
                            </c:forEach>
                    </datalist>
                    <input type="submit" name="Actualizar" value="Actualizar" class="login-submit" />
                </form>
            </div>
        </div>
        <div id="popup1" class="<c:out value="${pop1}"/> overlay">
            <div class="popup">
                <h2>Busqueda Erronea</h2>
                <a class="close" href="MenuPacientes?accion=Actualizar">&times;</a>
                <div class="content">
                    Algo salio mal, intente de nuevo.
                </div>
            </div>
        </div>

        <div id="popup1" class="<c:out value="${pop2}"/> overlay">
            <div class="popup">
                <h2>Actualización Erronea</h2>
                <a class="close" href="MenuPacientes?accion=Actualizar">&times;</a>
                <div class="content">
                    Algo salio mal, intente de nuevo.
                </div>
            </div>
        </div>
        <div class="fondo Sis4"></div>
        <nav class="main-menu" id="<c:out value="${Nivel}"/>">
            <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script><script  src="Scripts/navegacion.js"></script>
        </nav>
    </body>
</html>