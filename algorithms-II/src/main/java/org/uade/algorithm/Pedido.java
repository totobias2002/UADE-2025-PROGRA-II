package org.uade.algorithm;

public class Pedido {

    private int id;
    private Cliente cliente;
    private Plato[] platos;
    private int totalPlatos;

    private String metodoEntrega;
    private String zona;
    private Repartidor repartidor;
    private String estado;

    private double distanciaKm;
    private double costoDelivery;

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.platos = new Plato[20];
        this.totalPlatos = 0;
        this.estado = " Pedido tomado";
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }

    public void setMetodoEntrega(String metodoEntrega) { this.metodoEntrega = metodoEntrega; }
    public String getMetodoEntrega() { return metodoEntrega; }

    public void setZona(String zona) { this.zona = zona; }
    public String getZona() { return zona; }

    public void setRepartidor(Repartidor r) { this.repartidor = r; }
    public Repartidor getRepartidor() { return repartidor; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public void setDistanciaKm(double d) { distanciaKm = d; }
    public double getDistanciaKm() { return distanciaKm; }

    public void setCostoDelivery(double c) { costoDelivery = c; }
    public double getCostoDelivery() { return costoDelivery; }

    public void agregarPlato(Plato p) {
        platos[totalPlatos++] = p;
    }

    public int cantidadDePlatos() { return totalPlatos; }
    public Plato obtenerPlato(int i) { return platos[i]; }

    public Plato obtenerPlatoPendiente() {
        for (int i = 0; i < totalPlatos; i++) {
            if (!platos[i].getEstado().equals("listo")) return platos[i];
        }
        return null;
    }

    public boolean todosPlatosListos() {
        for (int i = 0; i < totalPlatos; i++) {
            if (!platos[i].getEstado().equals("listo")) return false;
        }
        return true;
    }

    public double getSubtotal() {
        double total = 0;
        for (int i = 0; i < totalPlatos; i++) {
            total += platos[i].getPrecio();
        }
        return total;
    }

    public double getTotal() {
        return getSubtotal() + costoDelivery;
    }
}
