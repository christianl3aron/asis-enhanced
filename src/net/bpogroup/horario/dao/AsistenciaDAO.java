package net.bpogroup.horario.dao;

import net.bpogroup.horario.dao.bean.AsistenciaBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Christianl3aron on 14/03/2015.
 */
public interface AsistenciaDAO {

    List<AsistenciaBean> getAsistenciasPorUsuarios(Date ti, Date tf, String dnis) throws Exception;

    void saveAsistencia(String dnis, String vals) throws Exception;
}
