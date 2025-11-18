package Entities;

import java.time.LocalDate;

public class Legajo {

    private Long id;
    private boolean eliminado;
    private String nroLegajo;
    private String categoria;
    private EstadoLegajo estado;
    private LocalDate fechaAlta;
    private String observaciones;

    public Legajo() {
    }

    public Legajo(Long id, boolean eliminado, String nroLegajo,
                  String categoria, EstadoLegajo estado,
                  LocalDate fechaAlta, String observaciones) {
        this.id = id;
        this.eliminado = eliminado;
        this.nroLegajo = nroLegajo;
        this.categoria = categoria;
        this.estado = estado;
        this.fechaAlta = fechaAlta;
        this.observaciones = observaciones;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }

    public String getNroLegajo() { return nroLegajo; }
    public void setNroLegajo(String nroLegajo) { this.nroLegajo = nroLegajo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public EstadoLegajo getEstado() { return estado; }
    public void setEstado(EstadoLegajo estado) { this.estado = estado; }

    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return "Legajo{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", nroLegajo='" + nroLegajo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", estado=" + estado +
                ", fechaAlta=" + fechaAlta +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
