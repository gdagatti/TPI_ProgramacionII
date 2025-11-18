package test;

import Entities.Empleado;
import Entities.EstadoLegajo;
import Entities.Legajo;
import Service.EmpleadoService;

import java.time.LocalDate;

public class TestService {

    public static void main(String[] args) {

        try {
            EmpleadoService service = new EmpleadoService();

            // ==========================
            // PRUEBA 1: CASO EXITOSO
            // ==========================
            Legajo legajoOk = new Legajo(
                    100L,
                    false,
                    "LEG-100",
                    "Junior",
                    EstadoLegajo.ACTIVO,
                    LocalDate.of(2024, 1, 1),
                    "Alta normal"
            );

            Empleado empleadoOk = new Empleado(
                    100L,
                    false,
                    "Ayelen",
                    "Masseroni",
                    "40123456",
                    "ayelen@example.com",
                    LocalDate.of(2024, 1, 10),
                    "Sistemas",
                    null
            );

            System.out.println("====== PRUEBA 1: CASO EXITOSO ======");
            service.crearEmpleadoConLegajo(empleadoOk, legajoOk);
            System.out.println(">> Fin de PRUEBA 1\n");

            // ==========================
            // PRUEBA 2: CASO CON ERROR
            // ==========================
            Legajo legajoError = new Legajo(
                    101L,
                    false,
                    "", // nroLegajo vacío -> error
                    "Senior",
                    EstadoLegajo.ACTIVO,
                    LocalDate.of(2024, 2, 1),
                    "Prueba con error"
            );

            Empleado empleadoError = new Empleado(
                    101L,
                    false,
                    "",             // nombre vacío -> error
                    "Gomez",
                    "40999888",
                    "correo_malformado", // email inválido
                    LocalDate.of(2024, 2, 10),
                    "IT",
                    null
            );

            System.out.println("====== PRUEBA 2: CASO CON ERROR (VALIDACIÓN + ROLLBACK) ======");
            service.crearEmpleadoConLegajo(empleadoError, legajoError);
            System.out.println(">> Fin de PRUEBA 2\n");

        } catch (Exception e) {
            System.out.println("Ocurrió una excepción en TestService:");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
