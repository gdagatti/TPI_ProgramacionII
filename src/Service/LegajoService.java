package Service;

import Dao.LegajoDao;
import Entities.Legajo;
import config.DatabaseConnection;

import java.sql.Connection;
import java.util.List;

public class LegajoService implements GenericService<Legajo> {

    private final LegajoDao legajoDao;

    public LegajoService() {
        this.legajoDao = new LegajoDao();
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

    @Override
    public Legajo crear(Legajo legajo) throws Exception {
        validarLegajo(legajo);
        try (Connection conn = DatabaseConnection.getConnection()) {
            return legajoDao.crear(legajo, conn);
        }
    }

    @Override
    public Legajo leer(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return legajoDao.leer(id, conn);
        }
    }

    @Override
    public List<Legajo> leerTodos() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return legajoDao.leerTodos(conn);
        }
    }

    @Override
    public Legajo actualizar(Legajo legajo) throws Exception {
        validarLegajo(legajo);
        try (Connection conn = DatabaseConnection.getConnection()) {
            return legajoDao.actualizar(legajo, conn);
        }
    }

    @Override
    public boolean eliminar(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return legajoDao.eliminar(id, conn);
        }
    }
}
