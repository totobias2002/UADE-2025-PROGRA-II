package org.uade.algorithm;

public class Pedido {
    private int id;
    private Cliente cliente;
    private String estado; // "pendiente", "en preparación", "listo", "entregado"

    public Pedido(int id, Cliente cliente, String tipo) {
        this.id = id;
        this.cliente = cliente;
        this.estado = "pendiente";
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean esVip() {
        return cliente.isVip();
    }

    @Override
    public String toString() {

        return "Pedido #" + id + " - " + cliente.toString() + " - Estado: " + estado;
    }
}
