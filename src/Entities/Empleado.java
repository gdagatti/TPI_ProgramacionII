package Entities;

import java.time.LocalDate;

public class Empleado {

    private Long id;
    private boolean eliminado;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private LocalDate fechaIngreso;
    private String area;
    private Legajo detalle;      // referencia 1→1 a Legajo

    public Empleado() {
    }

    public Empleado(Long id, boolean eliminado, String nombre, String apellido,
                    String dni, String email, LocalDate fechaIngreso,
                    String area, Legajo detalle) {
        this.id = id;
        this.eliminado = eliminado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.area = area;
        this.detalle = detalle;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public Legajo getDetalle() { return detalle; }
    public void setDetalle(Legajo detalle) { this.detalle = detalle; }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", area='" + area + '\'' +
                ", detalle=" + (detalle != null ? detalle.getId() : null) +
                '}';
    }
}
