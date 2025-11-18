package main;


import Entities.Empleado;
import Entities.EstadoLegajo;
import Entities.Legajo;
import Service.EmpleadoService;
import Service.LegajoService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class AppMenu {

    private final Scanner scanner;
    private final EmpleadoService empleadoService;
    private final LegajoService legajoService;

    public AppMenu() {
        this.scanner = new Scanner(System.in);
        this.empleadoService = new EmpleadoService();
        this.legajoService = new LegajoService();
    }

    // ========= MÉTODO PÚBLICO QUE USA MAIN =========
    public void iniciar() {
        try (scanner) {
            boolean salir = false;
            
            while (!salir) {
                try {
                    mostrarMenuPrincipal();
                    int opcion = leerEntero("Elija una opción: ");
                    
                    switch (opcion) {
                        case 1 -> menuEmpleados();
                        case 2 -> menuLegajos();
                        case 3 -> buscarEmpleadoPorDni();
                        case 0 -> {
                            System.out.println("Saliendo de la aplicación...");
                            salir = true;
                        }
                        default -> System.out.println("Opción inválida.");
                    }

                } catch (Exception e) {
                    System.out.println("Ocurrió un error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    // ========= MENÚS =========

    private void mostrarMenuPrincipal() {
        System.out.println("\n========== MENÚ PRINCIPAL ==========");
        System.out.println("1) Gestión de Empleados");
        System.out.println("2) Gestión de Legajos");
        System.out.println("3) Buscar empleado por DNI");
        System.out.println("0) Salir");
        System.out.println("====================================");
    }

    private void menuEmpleados() throws Exception {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n------ MENÚ EMPLEADOS ------");
            System.out.println("1) Crear Empleado + Legajo (transacción)");
            System.out.println("2) Ver Empleado por ID");
            System.out.println("3) Listar Empleados");
            System.out.println("4) Actualizar Empleado");
            System.out.println("5) Eliminar lógico Empleado");
            System.out.println("0) Volver");
            System.out.println("----------------------------");

            int opcion = leerEntero("Elija una opción: ");

            switch (opcion) {
                case 1 -> crearEmpleadoConLegajo();
                case 2 -> verEmpleadoPorId();
                case 3 -> listarEmpleados();
                case 4 -> actualizarEmpleado();
                case 5 -> eliminarEmpleado();
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void menuLegajos() throws Exception {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n------ MENÚ LEGAJOS ------");
            System.out.println("1) Crear Legajo");
            System.out.println("2) Ver Legajo por ID");
            System.out.println("3) Listar Legajos");
            System.out.println("4) Actualizar Legajo");
            System.out.println("5) Eliminar lógico Legajo");
            System.out.println("0) Volver");
            System.out.println("--------------------------");

            int opcion = leerEntero("Elija una opción: ");

            switch (opcion) {
                case 1 -> crearSoloLegajo();
                case 2 -> verLegajoPorId();
                case 3 -> listarLegajos();
                case 4 -> actualizarLegajo();
                case 5 -> eliminarLegajo();
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // ========= OPERACIONES EMPLEADO =========

    // Crea Empleado + Legajo en la misma transacción (usa EmpleadoService.crearEmpleadoConLegajo)
    private void crearEmpleadoConLegajo() {
        try {
            System.out.println("\n== Datos del LEGAJO ==");

            Long idLegajo = leerLong("ID de Legajo: ");
            String nroLegajo = leerTextoObligatorio("Nro de Legajo: ").toUpperCase(Locale.ROOT);
            String categoria = leerTexto("Categoría: ").toUpperCase(Locale.ROOT);
            EstadoLegajo estado = leerEstadoLegajo();
            LocalDate fechaAlta = leerFecha("Fecha de alta (YYYY-MM-DD): ");
            String observaciones = leerTexto("Observaciones: ");

            Legajo legajo = new Legajo(
                    idLegajo,
                    false,
                    nroLegajo,
                    categoria,
                    estado,
                    fechaAlta,
                    observaciones
            );

            System.out.println("\n== Datos del EMPLEADO ==");

            Long idEmpleado = leerLong("ID de Empleado: ");
            String nombre = leerTextoObligatorio("Nombre: ");
            String apellido = leerTextoObligatorio("Apellido: ");
            String dni = leerTextoObligatorio("DNI: ");
            String email = leerTexto("Email: ");
            LocalDate fechaIngreso = leerFecha("Fecha de ingreso (YYYY-MM-DD): ");
            String area = leerTexto("Área: ").toUpperCase(Locale.ROOT);

            Empleado empleado = new Empleado(
                    idEmpleado,
                    false,
                    nombre,
                    apellido,
                    dni,
                    email,
                    fechaIngreso,
                    area,
                    null
            );

            empleadoService.crearEmpleadoConLegajo(empleado, legajo);
            System.out.println(">>> Empleado y Legajo creados correctamente.");

        } catch (Exception e) {
            System.out.println("Error al crear empleado con legajo: " + e.getMessage());
        }
    }

    private void verEmpleadoPorId() {
        try {
            Long id = leerLong("Ingrese ID de empleado: ");
            Empleado emp = empleadoService.leer(id);
            if (emp == null) {
                System.out.println("No se encontró un empleado con ese ID.");
            } else {
                System.out.println(emp);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar empleado: " + e.getMessage());
        }
    }

    private void listarEmpleados() {
        try {
            List<Empleado> lista = empleadoService.leerTodos();
            if (lista.isEmpty()) {
                System.out.println("No hay empleados cargados.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
        }
    }

    private void actualizarEmpleado() {
        try {
            Long id = leerLong("ID de empleado a actualizar: ");
            Empleado emp = empleadoService.leer(id);
            if (emp == null) {
                System.out.println("No existe empleado con ese ID.");
                return;
            }

            System.out.println("Dejar vacío para mantener el valor actual.");

            String nombre = leerTexto("Nombre (" + emp.getNombre() + "): ");
            if (!nombre.isBlank()) emp.setNombre(nombre);

            String apellido = leerTexto("Apellido (" + emp.getApellido() + "): ");
            if (!apellido.isBlank()) emp.setApellido(apellido);

            String dni = leerTexto("DNI (" + emp.getDni() + "): ");
            if (!dni.isBlank()) emp.setDni(dni);

            String email = leerTexto("Email (" + emp.getEmail() + "): ");
            if (!email.isBlank()) emp.setEmail(email);

            String area = leerTexto("Área (" + emp.getArea() + "): ");
            if (!area.isBlank()) emp.setArea(area.toUpperCase(Locale.ROOT));

            String fechaStr = leerTexto("Fecha Ingreso (" + emp.getFechaIngreso() + ") [YYYY-MM-DD]: ");
            if (!fechaStr.isBlank()) {
                try {
                    emp.setFechaIngreso(LocalDate.parse(fechaStr));
                } catch (DateTimeParseException ex) {
                    System.out.println("Formato de fecha inválido. Se mantiene la anterior.");
                }
            }

            empleadoService.actualizar(emp);
            System.out.println(">>> Empleado actualizado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
        }
    }

    private void eliminarEmpleado() {
        try {
            Long id = leerLong("ID de empleado a eliminar (baja lógica): ");
            boolean ok = empleadoService.eliminar(id);
            if (ok) {
                System.out.println(">>> Empleado marcado como eliminado.");
            } else {
                System.out.println("No se encontró empleado con ese ID.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar empleado: " + e.getMessage());
        }
    }

    // ========= BÚSQUEDA POR DNI (parte de la consigna) =========

    private void buscarEmpleadoPorDni() {
        try {
            String dni = leerTextoObligatorio("Ingrese DNI a buscar: ");

            // búsqueda simple recorriendo todos (para el TPI alcanza)
            List<Empleado> lista = empleadoService.leerTodos();
            boolean encontrado = false;

            for (Empleado e : lista) {
                if (e.getDni().equals(dni)) {
                    System.out.println("Empleado encontrado:");
                    System.out.println(e);
                    encontrado = true;
                }
            }

            if (!encontrado) {
                System.out.println("No se encontró empleado con ese DNI.");
            }

        } catch (Exception e) {
            System.out.println("Error al buscar empleado por DNI: " + e.getMessage());
        }
    }

    // ========= OPERACIONES LEGAJO =========

    private void crearSoloLegajo() {
        try {
            Long id = leerLong("ID de Legajo: ");
            String nroLegajo = leerTextoObligatorio("Nro de Legajo: ").toUpperCase(Locale.ROOT);
            String categoria = leerTexto("Categoría: ").toUpperCase(Locale.ROOT);
            EstadoLegajo estado = leerEstadoLegajo();
            LocalDate fechaAlta = leerFecha("Fecha de alta (YYYY-MM-DD): ");
            String observaciones = leerTexto("Observaciones: ");

            Legajo legajo = new Legajo(
                    id,
                    false,
                    nroLegajo,
                    categoria,
                    estado,
                    fechaAlta,
                    observaciones
            );

            legajoService.crear(legajo);
            System.out.println(">>> Legajo creado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al crear legajo: " + e.getMessage());
        }
    }

    private void verLegajoPorId() {
        try {
            Long id = leerLong("Ingrese ID de legajo: ");
            Legajo l = legajoService.leer(id);
            if (l == null) {
                System.out.println("No se encontró legajo con ese ID.");
            } else {
                System.out.println(l);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar legajo: " + e.getMessage());
        }
    }

    private void listarLegajos() {
        try {
            List<Legajo> lista = legajoService.leerTodos();
            if (lista.isEmpty()) {
                System.out.println("No hay legajos cargados.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error al listar legajos: " + e.getMessage());
        }
    }

    private void actualizarLegajo() {
        try {
            Long id = leerLong("ID de legajo a actualizar: ");
            Legajo legajo = legajoService.leer(id);
            if (legajo == null) {
                System.out.println("No existe legajo con ese ID.");
                return;
            }

            System.out.println("Dejar vacío para mantener el valor actual.");

            String nro = leerTexto("Nro Legajo (" + legajo.getNroLegajo() + "): ");
            if (!nro.isBlank()) legajo.setNroLegajo(nro.toUpperCase(Locale.ROOT));

            String cat = leerTexto("Categoría (" + legajo.getCategoria() + "): ");
            if (!cat.isBlank()) legajo.setCategoria(cat.toUpperCase(Locale.ROOT));

            String estStr = leerTexto("Estado (" + legajo.getEstado() + ") [ACTIVO/INACTIVO]: ");
            if (!estStr.isBlank()) {
                try {
                    legajo.setEstado(EstadoLegajo.valueOf(estStr.toUpperCase(Locale.ROOT)));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Estado inválido. Se mantiene el anterior.");
                }
            }

            String fechaStr = leerTexto("Fecha Alta (" + legajo.getFechaAlta() + ") [YYYY-MM-DD]: ");
            if (!fechaStr.isBlank()) {
                try {
                    legajo.setFechaAlta(LocalDate.parse(fechaStr));
                } catch (DateTimeParseException ex) {
                    System.out.println("Formato de fecha inválido. Se mantiene la anterior.");
                }
            }

            String obs = leerTexto("Observaciones (" + legajo.getObservaciones() + "): ");
            if (!obs.isBlank()) legajo.setObservaciones(obs);

            legajoService.actualizar(legajo);
            System.out.println(">>> Legajo actualizado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al actualizar legajo: " + e.getMessage());
        }
    }

    private void eliminarLegajo() {
        try {
            Long id = leerLong("ID de legajo a eliminar (baja lógica): ");
            boolean ok = legajoService.eliminar(id);
            if (ok) {
                System.out.println(">>> Legajo marcado como eliminado.");
            } else {
                System.out.println("No se encontró legajo con ese ID.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar legajo: " + e.getMessage());
        }
    }

    // ========= HELPERS DE LECTURA / VALIDACIÓN =========

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = scanner.nextLine();
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero.");
            }
        }
    }

    private Long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = scanner.nextLine();
                return Long.parseLong(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número long válido.");
            }
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private String leerTextoObligatorio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine();
            if (!linea.trim().isEmpty()) {
                return linea.trim();
            }
            System.out.println("Este campo es obligatorio.");
        }
    }

    private LocalDate leerFecha(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = scanner.nextLine().trim();
                if (linea.isEmpty()) {
                    return null;
                }
                return LocalDate.parse(linea);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use YYYY-MM-DD.");
            }
        }
    }

    private EstadoLegajo leerEstadoLegajo() {
        while (true) {
            System.out.print("Estado [ACTIVO/INACTIVO]: ");
            String valor = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
            try {
                return EstadoLegajo.valueOf(valor);
            } catch (IllegalArgumentException e) {
                System.out.println("Valor inválido. Ingrese ACTIVO o INACTIVO.");
            }
        }
    }
}
