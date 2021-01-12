<%-- 
    Document   : inicio
    Created on : 11/01/2021, 05:20:30 PM
    Author     : jesus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es_mx">
    <head>
        <title>Inicio</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src='https://kit.fontawesome.com/a076d05399.js'></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="icon" type="image/vnd.microsoft.icon" href="Imagenes/favicon.ico" sizes="16x16">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="Estilos/EstiloMenus.css">
    </head>
    <body>
        <div class="area"></div>
        <nav class="main-menu">
            <ul>
                <li>
                    <a href="Inicio">
                        <i class="fas fa-home"></i>
                        <span class="nav-text">
                            Inicio
                        </span>
                    </a>

                </li>
                <li class="has-subnav">
                    <a href="">
                        <i class="fas fa-laptop-medical"></i>
                        <span class="nav-text">
                            Programar Citas
                        </span>
                    </a>

                </li>
                <li class="has-subnav">
                    <a href="">
                        <i class="fas fa-clipboard-list"></i>
                        <span class="nav-text">
                            Lista de Citas
                        </span>
                    </a>

                </li>
                <li class="has-subnav">
                    <a href="">
                        <i class="far fa-folder-open"></i>
                        <span class="nav-text">
                            Archivo de Pacientes
                        </span>
                    </a>

                </li>
                <li>
                    <a href="">
                        <i class="fas fa-tachometer-alt"></i>
                        <span class="nav-text">
                            Desempeño del Personal
                        </span>
                    </a>
                </li>
                <li>
                    <a href="">
                        <i class="fas fa-pencil-alt"></i>
                        <span class="nav-text">
                            Editar Información
                        </span>
                    </a>
                </li>
                <li>
                    <a href="">
                        <i class="fas fa-address-card"></i>
                        <span class="nav-text">
                            Información de Personal
                        </span>
                    </a>
                </li>
                <li>
                    <a href="">
                        <i class="fas fa-luggage-cart"></i>
                        <span class="nav-text">
                            Infromación Vacacional
                        </span>
                    </a>
                </li>
            </ul>

            <ul class="logout">
                <li>
                    <a href="Inicio?accion=Logout">
                        <i class="fas fa-power-off"></i>
                        <span class="nav-text">
                            Cerrar Sesión
                        </span>
                    </a>
                </li>
            </ul>
        </nav>
    </body>
</html>