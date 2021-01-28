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
            <h1>Actualización Citas</h1>
            <div class="<c:out value="${Actualiza}"/>">
                <form class="login-form" action="MenuCitas?accion=Busca"method="POST" autocomplete="off" accept-charset="utf-8">
                    <input list="browsers" name="browsers" class="login-username" placeholder="Busca Cita" required autofocus>
                    <datalist id="browsers">
                        <c:forEach var="Emp" items="${Lista}">
                            <option value="<c:out value="${Emp}"/>">
                            </c:forEach>
                    </datalist>
                    <input type="submit" name="Buscar" value="Buscar" class="login-submit" />
                </form>
            </div>
            <div class="<c:out value="${Busca}"/>">
                <form class="login-form" action="MenuCitas?accion=Actualiza" method="POST" autocomplete="off" accept-charset="utf-8">
                    <input list="medico" name="medico" class="login-username" placeholder="Busca Medico" value="<c:out value="${DatoMedico}"/>" required autofocus>
                    <datalist id="medico">
                        <c:forEach var="Emp" items="${Lista1}">
                            <option value="<c:out value="${Emp}"/>">
                            </c:forEach>
                    </datalist>
                    <input list="paciente" name="paciente" class="login-username" placeholder="Busca Paciente" value="<c:out value="${DatoPaciente}"/>" required>
                    <datalist id="paciente">
                        <c:forEach var="Emp" items="${Lista2}">
                            <option value="<c:out value="${Emp}"/>">
                            </c:forEach>
                    </datalist>
                    <input type="datetime-local" id="inicio" name="inicio" class="login-username" required value="<c:out value="${Emp.inicio}"/>" >
                    <label for="inicio">Fecha de inicio de consulta estimada</label>
                    <input type="datetime-local" id="fin" name="fin" class="login-username" required value="<c:out value="${Emp.fin}"/>" >
                    <label for="fin">Fecha de fin de consulta estimada</label>
                    <input type="submit" name="Actualizar" value="Actualizar" class="login-submit" />
                </form>
            </div>
        </div>
        <div id="popup1" class="<c:out value="${pop1}"/> overlay">
            <div class="popup">
                <h2>Busqueda Erronea</h2>
                <a class="close" href="MenuCitas?accion=Actualizar">&times;</a>
                <div class="content">
                    Algo salio mal, intente de nuevo.
                </div>
            </div>
        </div>

        <div id="popup1" class="<c:out value="${pop2}"/> overlay">
            <div class="popup">
                <h2>Actualización Erronea</h2>
                <a class="close" href="MenuCitas?accion=Actualizar">&times;</a>
                <div class="content">
                    Algo salio mal, intente de nuevo.
                </div>
            </div>
        </div>
        <div class="fondo Sis5"></div>
        <nav class="main-menu" id="<c:out value="${Nivel}"/>">
            <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script><script  src="Scripts/navegacion.js"></script>
        </nav>
    </body>
</html>