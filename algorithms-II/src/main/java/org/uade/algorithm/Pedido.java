package org.uade.algorithm;

public class Pedido {
    private int id;
    private Cliente cliente;
    private String tipo; // "para llevar" o "a domicilio"
    private String estado; // "pendiente", "en preparación", etc.
    private Plato[] platos; // Platos elegidos por el cliente
    private int cantidadPlatos; // Cantidad de platos agregados

    public Pedido(int id, Cliente cliente, String tipo) {
        this.id = id;
        this.cliente = cliente;
        this.tipo = tipo;
        this.estado = "pendiente";
        this.platos = new Plato[10]; // Capacidad máxima por pedido
        this.cantidadPlatos = 0;
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public String getTipo() { return tipo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public boolean esVip() {
        return cliente.isVip();
    }

    // ----------------- Platos -----------------
    public void agregarPlato(Plato plato) {
        if (cantidadPlatos < platos.length) {
            platos[cantidadPlatos++] = plato;
        } else {
            System.out.println("⚠️ No se pueden agregar más platos a este pedido.");
        }
    }

    public Plato[] getPlatos() {
        Plato[] resultado = new Plato[cantidadPlatos];
        for (int i = 0; i < cantidadPlatos; i++) {
            resultado[i] = platos[i];
        }
        return resultado;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido #").append(id)
                .append(" - ").append(cliente.toString())
                .append(" | Tipo: ").append(tipo)
                .append(" | Estado: ").append(estado)
                .append(" | Platos: ");
        for (int i = 0; i < cantidadPlatos; i++) {
            sb.append(platos[i].getNombre());
            if (i < cantidadPlatos - 1) sb.append(", ");
        }
        return sb.toString();
    }
}
