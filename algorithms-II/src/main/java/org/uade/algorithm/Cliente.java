package org.uade.algorithm;

public class Cliente {
    private String nombre;
    private String direccion;
    private String telefono;
    private boolean vip;

    public Cliente(String nombre, String direccion, String telefono, boolean vip) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.vip = vip;
    }

    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public boolean isVip() { return vip; }

    @Override
    public String toString() {
        return nombre + " (" + (vip ? "VIP" : "Normal") + ")";
    }
}
