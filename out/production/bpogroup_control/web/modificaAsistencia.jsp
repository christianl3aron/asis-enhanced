<%@ page import="net.bpogroup.horario.dao.bean.UsuarioBean" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Christianl3aron
  Date: 14/03/2015
  Time: 09:23 AM
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>BPO Group</title>
    <!-- imports del Chosen -->
    <link rel="stylesheet" href="js/chosen/chosen.min.css">
    <script src="js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="js/chosen/chosen.jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/chosen/chosen-dump.js"></script>
    <!-- imports del Clockpicker -->
    <link rel="stylesheet" type="text/css" href="js/clockpicker/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="js/clockpicker/css/bootstrap-clockpicker.min.css">
    <link rel="stylesheet" type="text/css" href="js/clockpicker/css/github.min.css">
    <script type="text/javascript" src="js/clockpicker/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/clockpicker/js/bootstrap-clockpicker.min.js"></script>
    <script type="text/javascript" src="js/clockpicker/js/highlight.min.js"></script>
    <!-- Aditional-->
    <script type="text/javascript" src="js/mantenimiento/ajaxMantenimiento.js"></script>
    <link rel="stylesheet" type="text/css" href="css/mantenimiento.css">


    <script src="js/clockpicker/js/html5shiv.js"></script>
    <script src="js/clockpicker/js/respond.min.js"></script>

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

<div id="divModAsistencia">

    <table id="tcAsistencia">
        <tr>
            <td>
                <label for="timeIni">Fecha Inicio :</label>
                <input type="date" name="timeIni" id="timeIni" value="<%= dateFormat.format(today) %>"
                       min="2014-11-01"/></td>
            <td>
                <span>
                    <button type='button' id="btnShow">Ver Registros</button>
                </span>
            </td>
        </tr>
        <tr>
            <td>
                <label for="timeFin">Fecha Fin :</label>
                <input type="date" name="timeFin" id="timeFin" value="<%= dateFormat.format(today) %>"
                       min="2014-11-01"/></td>
            <td>
                 <span>
                    <button type='button' id="btnSave">Grabar</button>
                  </span>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <div id="container-chosen">
                    <select id="sltPersonal" data-placeholder="Your Favorite Football Team" style="width:500px"
                            class="chosen-select" multiple tabindex="6">
                        <option value=""></option>

                        <c:forEach items="${requestScope.listaAreas}" var="area">

                            <optgroup label="<c:out value="${area.getDenominacion()}"/>">

                                <c:forEach items="${requestScope.listaUsuarios}" var="usuario">
                                    <c:if test="${usuario.getAreaBean().getCodigo()==area.getCodigo()}">
                                        <option value="<c:out value="${usuario.getDni()}"/>"><c:out
                                                value="${usuario.getNombres()} ${usuario.getApellidos()}"/></option>
                                    </c:if>
                                </c:forEach>
                            </optgroup>
                        </c:forEach>
                    </select>
                </div>
            </td>
        </tr>
    </table>


</div>

<div id="container-loader">
    <img id="img-loader" src="img/calmingcatsmall.gif" alt="loading">
</div>
<div id="container"></div>

</body>
</html>
