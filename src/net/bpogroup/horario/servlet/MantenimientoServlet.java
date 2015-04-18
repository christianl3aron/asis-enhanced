package net.bpogroup.horario.servlet;

import net.bpogroup.horario.dao.bean.AreaBean;
import net.bpogroup.horario.dao.bean.AsistenciaBean;
import net.bpogroup.horario.dao.bean.UsuarioBean;
import net.bpogroup.horario.service.MantenimientoService;
import net.bpogroup.horario.service.imp.MantenimientoServiceImp;
import net.bpogroup.horario.util.UtilApp;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Christianl3aron on 14/03/2015.
 */
public class MantenimientoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // a traves de AJAX
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");

        Logger logger = Logger.getLogger(this.getClass());
        String action = request.getParameter("act");

        if (action.equals("lusers")) {
            // Si la accion es lusers("Load usuarios") enviar List<UsuarioBean> al Select de HTML
            try {
                MantenimientoService mantenimientoService = new MantenimientoServiceImp();
                List<UsuarioBean> listaUsuarios = mantenimientoService.getUsuariosParaCombobox();
                List<AreaBean> listaAreas = mantenimientoService.getAreaParaCombobox();

                request.setAttribute("listaUsuarios", listaUsuarios);
                request.setAttribute("listaAreas", listaAreas);

                RequestDispatcher dispa = getServletContext().getRequestDispatcher("/modificaAsistencia.jsp");
                dispa.forward(request, response);

            } catch (Exception e) {
                logger.error(e.toString().replace("\'", ""), e);
            }

        } else if (action.equals("showAsi")) {
            // Si la accion es showAsi("Show Asistencia") enviar List<AsistenciaBena> al div
            try {
                String listaDni = request.getParameter("cods");
                Date ti = UtilApp.getDate(request.getParameter("ti"));
                Date tf= UtilApp.addADay(request.getParameter("tf"));

                MantenimientoService mantenimientoService = new MantenimientoServiceImp();
                List<AsistenciaBean> la = mantenimientoService.getAsistenciasPorUsuarios(ti, tf, listaDni);
                if (la.size() > 0) {
                    out.println("<table id=\"tAsistencia\">");
                    out.println("<tr id=\"tHeader\">");
                    out.println("<th id=\"tdBig\">Nombres y apellidos</th>");
                    out.println("<th id=\"tdSmall\">dia</th>");
                    out.println("<th id=\"tdSmall\">Inicio</th>");
                    out.println("<th id=\"tdSmall\">Inicio Break</th>");
                    out.println("<th id=\"tdSmall\">Finalizo Break</th>");
                    out.println("<th id=\"tdSmall\">Fin</th>");
                    out.println("</tr>");
                }
                for (int i = 0; i < la.size(); i++) {
                    out.println("<tr>");
                    out.println("<td id=\"tdBig\">" + la.get(i).getUsuarioBean().getNombres() + " " + la.get(i).getUsuarioBean().getApellidos() + "</td>");
                    out.println("<td id=\"tdSmall\"><input type=\"date\" value=\"" + la.get(i).getDia() + "\" min=\"2014-11-01\" disabled=\"\"></td>");
                    out.println("<td id=\"tdSmall\"><div class=\"input-group clockpicker pull-center\" data-placement=\"left\" data-align=\"top\" data-autoclose=\"true\"><input type=\"text\" id=\"" + la.get(i).getUsuarioBean().getDni() + la.get(i).getDia() + "1\" class=\"form-control\" value=\"" + la.get(i).getIniDia() + "\"><span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-time\"></span></span></div></td>");
                    out.println("<td id=\"tdSmall\"><div class=\"input-group clockpicker pull-center\" data-placement=\"left\" data-align=\"top\" data-autoclose=\"true\"><input type=\"text\" id=\"" + la.get(i).getUsuarioBean().getDni() + la.get(i).getDia() + "2\" class=\"form-control\" value=\"" + la.get(i).getIniBreak() + "\"><span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-time\"></span></span></div></td>");
                    out.println("<td id=\"tdSmall\"><div class=\"input-group clockpicker pull-center\" data-placement=\"left\" data-align=\"top\" data-autoclose=\"true\"><input type=\"text\" id=\"" + la.get(i).getUsuarioBean().getDni() + la.get(i).getDia() + "3\" class=\"form-control\" value=\"" + la.get(i).getFinBreak() + "\"><span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-time\"></span></span></div></td>");
                    out.println("<td id=\"tdSmall\"><div class=\"input-group clockpicker pull-center\" data-placement=\"left\" data-align=\"top\" data-autoclose=\"true\"><input type=\"text\" id=\"" + la.get(i).getUsuarioBean().getDni() + la.get(i).getDia() + "4\" class=\"form-control\" value=\"" + la.get(i).getFinDia() + "\"><span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-time\"></span></span></div></td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("<script src=\"js/mantenimiento/modificaAsistencia.js\" type=\"text/javascript\"></script>");

            } catch (Exception e) {
                logger.error(e.toString().replace("\'", ""), e);
            }
        } else if (action.equals("saveCha")) {
            try {
                String dnis = request.getParameter("ids");
                String vals = request.getParameter("vals");
                MantenimientoService mantenimientoService = new MantenimientoServiceImp();
                mantenimientoService.saveAsistencia(dnis, vals);
            } catch (Exception e) {
                logger.error(e.toString().replace("\'", ""), e);
            }
        }
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }
}
