package net.bpogroup.horario.dao.imp;

import net.bpogroup.horario.dao.AsistenciaDAO;
import net.bpogroup.horario.dao.bean.AsistenciaBean;
import net.bpogroup.horario.dao.bean.UsuarioBean;
import net.bpogroup.horario.util.ConexionBD;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Christianl3aron on 14/03/2015.
 */
public class AsistenciaDAOImp implements AsistenciaDAO {

    @Override
    public List<AsistenciaBean> getAsistenciasPorUsuarios(Date ti, Date tf, String listaDni) throws Exception {


        List<AsistenciaBean> listaAsistencias = new ArrayList<AsistenciaBean>();
        List<String> lista = Arrays.asList(listaDni.split("\\s*,\\s*"));

        System.out.println(listaAsistencias.size());
        System.out.println(lista.size());
        if (lista.size() > 0) {
            System.out.println("ingrese a jecutar el query");
            StringBuilder cadena = new StringBuilder();
            cadena.append("SELECT u.nombres, u.apellidos, h.dni, DATE_FORMAT(h.ini_jornada,'%Y-%m-%d') as dia, ");
            cadena.append("DATE_FORMAT(h.ini_jornada,'%H:%i') as inidia, DATE_FORMAT( h.fin_jornada,'%H:%i') as findia, ");
            cadena.append("DATE_FORMAT(h.ini_break,'%H:%i') as inibreak, DATE_FORMAT(h.fin_break,'%H:%i') as finbreak ");
            cadena.append("FROM bh_horario h INNER JOIN bh_usuario u ON u.dni = h.dni ");
            cadena.append("WHERE  u.flag='A' AND h.ini_jornada>? AND h.ini_jornada<? ");
            cadena.append("AND (");
            for (int i = 0; i < lista.size(); i++) {
                cadena.append("u.dni='").append(lista.get(i)).append("' ");
                if (i < lista.size() - 1) {
                    cadena.append("OR ");
                }
            }
            cadena.append(") ");
            cadena.append("ORDER BY h.ini_jornada ");
            System.out.println("yo soy el queyr");

            Connection cn = ConexionBD.getInstance().obtenerConexion();
            PreparedStatement ps = cn.prepareStatement(cadena.toString());
            java.sql.Date sql = new java.sql.Date(ti.getTime());
            ps.setDate(1, sql);
            java.sql.Date sql2 = new java.sql.Date(tf.getTime());
            ps.setDate(2, sql2);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AsistenciaBean asistenciaBean = new AsistenciaBean(
                        new UsuarioBean(rs.getString("dni"), rs.getString("nombres"), rs.getString("apellidos")),
                        rs.getString("dia"),
                        rs.getString("inidia"),
                        rs.getString("inibreak"),
                        rs.getString("finbreak"),
                        rs.getString("findia"));
                listaAsistencias.add(asistenciaBean);
            }
            System.out.println("el tamaño de la asistecia es : " + listaAsistencias.size());
        }
        return listaAsistencias;
    }

    @Override
    public void saveAsistencia(String ids, String vals) {
        Logger logger = Logger.getLogger(this.getClass());

        List<String> lId = Arrays.asList(ids.split("\\s*,\\s*"));
        List<String> lVal = Arrays.asList(vals.split("\\s*,\\s*"));

        Connection cn = null;
        try {
            cn = ConexionBD.getInstance().obtenerConexion();
            cn.setAutoCommit(false);

            for (int i = 0; i < lId.size(); i++) {
                PreparedStatement ps;
                if (lId.get(i).substring(18).equals("1")) {
                    ps = cn.prepareStatement("UPDATE bh_horario SET ini_jornada=? WHERE dni=? AND dia=?");
                } else if (lId.get(i).substring(18).equals("2")) {
                    ps = cn.prepareStatement("UPDATE bh_horario SET ini_break=? WHERE dni=? AND dia=?");
                } else if (lId.get(i).substring(18).equals("3")) {
                    ps = cn.prepareStatement("UPDATE bh_horario SET fin_break=? WHERE dni=? AND dia=?");
                } else {
                    ps = cn.prepareStatement("UPDATE bh_horario SET fin_jornada=? WHERE dni=? AND dia=?");
                }
                ps.setString(1, lId.get(i).substring(8, 18) + " " + lVal.get(i));
                ps.setString(2, lId.get(i).substring(0, 8));
                ps.setString(3, lId.get(i).substring(8, 18));
                ps.execute();
            }
            cn.commit();
            cn.close();
        } catch (
                Exception e
                )

        {
            logger.error(e.toString().replace("\'", ""), e);
        }


    }
}
