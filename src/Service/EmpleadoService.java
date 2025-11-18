package Service;

import Dao.EmpleadoDao;
import Dao.LegajoDao;
import Entities.Empleado;
import Entities.Legajo;
import config.DatabaseConnection;

import java.sql.Connection;
import java.util.List;

public class EmpleadoService implements GenericService<Empleado> {

    private final EmpleadoDao empleadoDao;
    private final LegajoDao legajoDao;

    public EmpleadoService() {
        this.empleadoDao = new EmpleadoDao();
        this.legajoDao = new LegajoDao();
    }

    // ===== VALIDACIONES =====
    private void validarEmpleado(Empleado empleado) throws Exception {
        if (empleado == null)
            throw new Exception("El empleado no puede ser null.");

        if (empleado.getNombre() == null || empleado.getNombre().trim().isEmpty())
            throw new Exception("El nombre del empleado es obligatorio.");

        if (empleado.getApellido() == null || empleado.getApellido().trim().isEmpty())
            throw new Exception("El apellido del empleado es obligatorio.");

        if (empleado.getDni() == null || empleado.getDni().trim().isEmpty())
            throw new Exception("El DNI es obligatorio.");

        if (!empleado.getDni().matches("\\d+"))
            throw new Exception("El DNI debe ser numérico.");

        if (empleado.getEmail() != null && !empleado.getEmail().isEmpty() &&
                !empleado.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$"))
            throw new Exception("El email no tiene un formato válido.");

        if (empleado.getArea() != null && empleado.getArea().length() > 50)
            throw new Exception("El área no puede superar los 50 caracteres.");
    }
    private void validarLegajo(Legajo legajo) throws Exception {
        if (legajo == null)
            throw new Exception("El legajo no puede ser null.");

        if (legajo.getNroLegajo() == null || legajo.getNroLegajo().trim().isEmpty())
            throw new Exception("El número de legajo es obligatorio.");

        if (legajo.getNroLegajo().length() > 20)
            throw new Exception("El número de legajo no puede superar los 20 caracteres.");

        if (legajo.getCategoria() != null && legajo.getCategoria().length() > 30)
            throw new Exception("La categoría no puede superar los 30 caracteres.");

        if (legajo.getEstado() == null)
            throw new Exception("El estado del legajo es obligatorio.");
    }

    // ===== CRUD SIMPLE =====

    @Override
    public Empleado crear(Empleado empleado) throws Exception {
        validarEmpleado(empleado);
        try (Connection conn = DatabaseConnection.getConnection()) {
            return empleadoDao.crear(empleado, conn);
        }
    }

    @Override
    public Empleado leer(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return empleadoDao.leer(id, conn);
        }
    }

    @Override
    public List<Empleado> leerTodos() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return empleadoDao.leerTodos(conn);
        }
    }

    @Override
    public Empleado actualizar(Empleado empleado) throws Exception {
        validarEmpleado(empleado);
        try (Connection conn = DatabaseConnection.getConnection()) {
            return empleadoDao.actualizar(empleado, conn);
        }
    }

    @Override
    public boolean eliminar(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return empleadoDao.eliminar(id, conn);
        }
    }

    // ===== MÉTODO TRANSACCIONAL A+B =====

    public void crearEmpleadoConLegajo(Empleado empleado, Legajo legajo) throws Exception {

        validarEmpleado(empleado);
        validarLegajo(legajo);

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 1) Crear Legajo
            legajoDao.crear(legajo, conn);

            // 2) Asociar legajo al empleado
            empleado.setDetalle(legajo);

            // 3) Crear empleado
            empleadoDao.crear(empleado, conn);

            conn.commit();
            System.out.println("Empleado y Legajo creados correctamente en una sola transacción.");

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            System.out.println("Error en la transacción. Se realizó rollback.");
            System.out.println("Detalle del error: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}
