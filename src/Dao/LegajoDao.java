package Dao;

import Entities.EstadoLegajo;
import Entities.Legajo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LegajoDao implements GenericDao<Legajo> {

    @Override
    public Legajo crear(Legajo legajo, Connection conn) throws Exception {
        String sql = "INSERT INTO legajo " +
                "(id, eliminado, nro_legajo, categoria, estado, fecha_alta, observaciones) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, legajo.getId());
            ps.setBoolean(2, legajo.isEliminado());
            ps.setString(3, legajo.getNroLegajo());
            ps.setString(4, legajo.getCategoria());
            ps.setString(5, legajo.getEstado().name());

            if (legajo.getFechaAlta() != null) {
                ps.setDate(6, Date.valueOf(legajo.getFechaAlta()));
            } else {
                ps.setNull(6, Types.DATE);
            }

            ps.setString(7, legajo.getObservaciones());

            ps.executeUpdate();
            return legajo;
        }
    }

    @Override
    public Legajo leer(Long id, Connection conn) throws Exception {
        String sql = "SELECT * FROM legajo WHERE id = ? AND eliminado = 0";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Legajo> leerTodos(Connection conn) throws Exception {
        String sql = "SELECT * FROM legajo WHERE eliminado = 0";
        List<Legajo> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }
        return lista;
    }

    @Override
    public Legajo actualizar(Legajo legajo, Connection conn) throws Exception {
        String sql = "UPDATE legajo SET " +
                "eliminado = ?, nro_legajo = ?, categoria = ?, estado = ?, " +
                "fecha_alta = ?, observaciones = ? " +
                "WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, legajo.isEliminado());
            ps.setString(2, legajo.getNroLegajo());
            ps.setString(3, legajo.getCategoria());
            ps.setString(4, legajo.getEstado().name());

            if (legajo.getFechaAlta() != null) {
                ps.setDate(5, Date.valueOf(legajo.getFechaAlta()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            ps.setString(6, legajo.getObservaciones());
            ps.setLong(7, legajo.getId());

            ps.executeUpdate();
            return legajo;
        }
    }

    @Override
    public boolean eliminar(Long id, Connection conn) throws Exception {
        String sql = "UPDATE legajo SET eliminado = 1 WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;
        }
    }

    private Legajo mapRow(ResultSet rs) throws Exception {
        Legajo l = new Legajo();
        l.setId(rs.getLong("id"));
        l.setEliminado(rs.getBoolean("eliminado"));
        l.setNroLegajo(rs.getString("nro_legajo"));
        l.setCategoria(rs.getString("categoria"));
        l.setEstado(EstadoLegajo.valueOf(rs.getString("estado")));
        Date fa = rs.getDate("fecha_alta");
        if (fa != null) {
            l.setFechaAlta(fa.toLocalDate());
        }
        l.setObservaciones(rs.getString("observaciones"));
        return l;
    }
}
