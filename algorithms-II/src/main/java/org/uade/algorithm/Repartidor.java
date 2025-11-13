package org.uade.algorithm;

public class Repartidor {

    private int id;
    private String nombre;
    private String vehiculo;
    private boolean disponible;
    private int entregasRealizadas;

    public Repartidor(int id, String nombre, String vehiculo) {
        this.id = id;
        this.nombre = nombre;
        this.vehiculo = vehiculo;
        this.disponible = true;
        this.entregasRealizadas = 0;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getVehiculo() { return vehiculo; }
    public boolean estaDisponible() { return disponible; }
    public int getEntregasRealizadas() { return entregasRealizadas; }

    public void asignarPedido() {
        disponible = false;
    }

    public void registrarEntregaRealizada() {
        entregasRealizadas++;
        disponible = true;
    }

    @Override
    public String toString() {
        return nombre + " (" + vehiculo + ")";
    }
}
