package net.bpogroup.horario.service.imp;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Date;

import net.bpogroup.horario.dao.ReportDAO;
import net.bpogroup.horario.dao.imp.ReportDAOImp;
import net.bpogroup.horario.service.ReportService;
import net.bpogroup.horario.util.UtilApp;

/**
 * Created by Christianl3aron on 12/03/2015.
 */
public class ReportServiceImp implements ReportService {
    public ReportServiceImp() {
    }

    public String obtenerReporte(Date ti, Date tf) throws Exception {

        StringBuilder archivo = new StringBuilder("temp/Reporte-").append(UtilApp.getDate4Report()).append(".xls");
        ReportDAO reportDAO = new ReportDAOImp();
        ResultSet rs = reportDAO.obtenerReporte(ti, tf);

        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream("/usr/local/tomcat7/webapps/bpogroup_control/" + archivo)), "UTF-8"));
        //PrintWriter writer = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream("E:\\IdeaProjects\\bpogroup_control\\web\\" + archivo.toString())), "UTF-8"));
        //PrintWriter writer = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream("C:\\Users\\Christian Baron\\Desktop\\bpogroup_control\\web\\" + archivo)), "UTF-8"));

        writer.append("Nro\tApellidos y Nombres\tDNI\tArea\tFecha de Ingreso\tHora de Ingreso\tFecha de Salida\tHora de Salida\tHorario de Inicio de Refrigerio\tHorario de Termino de Refrigerio\tGestion por dia").println();

        for(int i = 1; rs.next(); ++i) {
            writer.append(String.valueOf(i)).append("\t").append(rs.getString(1)).append("\t").append(rs.getString(2)).append("\t").append(rs.getString(3)).append("\t").append(rs.getString(4)).append("\t").append(rs.getString(5)).append("\t").append(rs.getString(6)).append("\t").append(rs.getString(7)).append("\t").append(rs.getString(8)).append("\t").append(rs.getString(9)).append("\t").append(rs.getString(10)).println();
        }


        writer.close();
        return archivo.toString();

    }
}