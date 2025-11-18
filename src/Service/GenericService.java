package Service;

import java.util.List;

public interface GenericService<T> {

    T crear(T entity) throws Exception;

    T leer(Long id) throws Exception;

    List<T> leerTodos() throws Exception;

    T actualizar(T entity) throws Exception;

    boolean eliminar(Long id) throws Exception;
}
