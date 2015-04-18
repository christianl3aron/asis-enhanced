<%-- 
    Document   : reporte
    Created on : 11-nov-2014, 14:15:17
    Author     : CHRISTIAN-LAP
--%>

<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.text.DateFormat" %>
<%@page import="net.bpogroup.horario.dao.bean.UsuarioBean" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>BPO Group</title>
    <script src="js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="js/reporte/reporteAsistencia.js" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="css/main.css"/>

    <%
        UsuarioBean usuarioBean = (UsuarioBean) request.getSession().getAttribute("usuario");
        if (request.getSession(false) == null || request.getSession().getAttribute("usuario") == null || usuarioBean.getTipoUsuarioBean().getCodigo() == 2) {
            response.sendRedirect("index.jsp");
            return;
        }
    %>


</head>
<body>
<%@include file="cabecera.jspf" %>
<%
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date today = new Date();
%>

<div id="divReport">
    <table style="table-layout: fixed;width: 800px;margin: 0 auto">
        <tr>
            <td>
                <label for="timeIni">Fecha Inicio :</label>
            </td>
            <td>
                <input type="date" name="timeIni" id="timeIni" value="<%= dateFormat.format(today) %>"
                       min="2014-11-01"/></td>
            <td colspan="2">
                <button type='button' id="btnReport">Ver reporte</button>
                <br>
            </td>
        </tr>
        <tr>
            <td>
                <label for="timeFin">Fecha Fin :</label>
            </td>
            <td>
                <input type="date" name="timeFin" id="timeFin" value="<%= dateFormat.format(today) %>"
                       min="2014-11-01"/></td>
            </td>
            <td colspan="2" height="70px">
                <a id="descarga" target="_blank" download><img id="imgDescarga" src="img/download.png" alt="descargar"/></a>
            </td>
        </tr>

    </table>
</div>
</body>
</html>
