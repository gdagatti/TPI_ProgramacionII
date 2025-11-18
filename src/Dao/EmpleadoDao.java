package Dao;

import Entities.Empleado;
import Entities.Legajo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDao implements GenericDao<Empleado> {

    @Override
    public Empleado crear(Empleado emp, Connection conn) throws Exception {
        String sql = "INSERT INTO empleado " +
                "(id, eliminado, nombre, apellido, dni, email, fecha_ingreso, area, legajo_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, emp.getId());
            ps.setBoolean(2, emp.isEliminado());
            ps.setString(3, emp.getNombre());
            ps.setString(4, emp.getApellido());
            ps.setString(5, emp.getDni());
            ps.setString(6, emp.getEmail());

            if (emp.getFechaIngreso() != null) {
                ps.setDate(7, Date.valueOf(emp.getFechaIngreso()));
            } else {
                ps.setNull(7, Types.DATE);
            }

            ps.setString(8, emp.getArea());

            Legajo legajo = emp.getDetalle();
            if (legajo != null) {
                ps.setLong(9, legajo.getId());
            } else {
                ps.setNull(9, Types.BIGINT);
            }

            ps.executeUpdate();
            return emp;
        }
    }

    @Override
    public Empleado leer(Long id, Connection conn) throws Exception {
        String sql = "SELECT * FROM empleado WHERE id = ? AND eliminado = 0";

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
    public List<Empleado> leerTodos(Connection conn) throws Exception {
        String sql = "SELECT * FROM empleado WHERE eliminado = 0";
        List<Empleado> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }
        return lista;
    }

    @Override
    public Empleado actualizar(Empleado emp, Connection conn) throws Exception {
        String sql = "UPDATE empleado SET " +
                "eliminado = ?, nombre = ?, apellido = ?, dni = ?, email = ?, " +
                "fecha_ingreso = ?, area = ?, legajo_id = ? " +
                "WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, emp.isEliminado());
            ps.setString(2, emp.getNombre());
            ps.setString(3, emp.getApellido());
            ps.setString(4, emp.getDni());
            ps.setString(5, emp.getEmail());

            if (emp.getFechaIngreso() != null) {
                ps.setDate(6, Date.valueOf(emp.getFechaIngreso()));
            } else {
                ps.setNull(6, Types.DATE);
            }

            ps.setString(7, emp.getArea());

            Legajo legajo = emp.getDetalle();
            if (legajo != null) {
                ps.setLong(8, legajo.getId());
            } else {
                ps.setNull(8, Types.BIGINT);
            }

            ps.setLong(9, emp.getId());

            ps.executeUpdate();
            return emp;
        }
    }

    @Override
    public boolean eliminar(Long id, Connection conn) throws Exception {
        String sql = "UPDATE empleado SET eliminado = 1 WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;
        }
    }

    private Empleado mapRow(ResultSet rs) throws Exception {
        Empleado e = new Empleado();
        e.setId(rs.getLong("id"));
        e.setEliminado(rs.getBoolean("eliminado"));
        e.setNombre(rs.getString("nombre"));
        e.setApellido(rs.getString("apellido"));
        e.setDni(rs.getString("dni"));
        e.setEmail(rs.getString("email"));
        Date fi = rs.getDate("fecha_ingreso");
        if (fi != null) {
            e.setFechaIngreso(fi.toLocalDate());
        }
        e.setArea(rs.getString("area"));
        // detalle (Legajo) se puede cargar luego, si hace falta
        return e;
    }
}
