package org.uade.algorithm;

public class Repartidor {
    private int id;
    private String nombre;
    private boolean disponible;
    private int cantidadEntregas;

    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.disponible = true;
        this.cantidadEntregas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean estaDisponible() {
        return disponible;
    }

    public int getCantidadEntregas() {
        return cantidadEntregas;
    }

    public void asignarPedido(Pedido pedido) {
        disponible = false;
    }

    public void entregarPedido(Pedido pedido) {
        System.out.println(" " + nombre + " entregando pedido #" + pedido.getId() + "...");
        pedido.setEstado("finalizado");
        cantidadEntregas++;
        disponible = true;
        System.out.println("Pedido #" + pedido.getId() + " entregado por " + nombre + ".\n");
    }

}
