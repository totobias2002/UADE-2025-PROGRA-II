package org.uade.algorithm;

public class Plato {
    private int id;
    private String nombre;
    private double precio;
    private String estado; // "pendiente", "en preparación", "listo"

    public Plato(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.estado = "pendiente"; //No se esta usando
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return id + ". " + nombre + " - $" + precio;
    }
}
