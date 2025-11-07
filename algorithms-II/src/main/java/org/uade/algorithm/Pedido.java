package org.uade.algorithm;

public class Pedido {
    private int id;
    private Cliente cliente;
    private Plato[] platos;
    private int cantidadPlatos;
    private String tipo;     // "Para llevar" o "A domicilio"
    private String estado;   // "pendiente", "en preparación", "entregado"

    public Pedido(int id, Cliente cliente, String tipo) {
        this.id = id;
        this.cliente = cliente;
        this.tipo = tipo;
        this.estado = "pendiente";
        this.platos = new Plato[20]; // máximo 20 platos por pedido
        this.cantidadPlatos = 0;
    }


    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void agregarPlato(Plato plato) {
        if (cantidadPlatos < platos.length) {
            platos[cantidadPlatos++] = plato;
        }
    }

    public int cantidadDePlatos() {
        return cantidadPlatos;
    }

    public Plato obtenerPlato(int index) {
        if (index < 0 || index >= cantidadPlatos) {
            return null;
        }
        return platos[index];
    }

    public void removerPlato(int index) {
        if (index < 0 || index >= cantidadPlatos) {
            return;
        }
        for (int i = index; i < cantidadPlatos - 1; i++) {
            platos[i] = platos[i + 1];
        }
        cantidadPlatos--;
    }

    public boolean todosPlatosListos() {
        for (int i = 0; i < cantidadPlatos; i++) {
            if (!platos[i].getEstado().equals("listo")) {
                return false;
            }
        }
        return true;
    }

    public Plato obtenerPlatoPendiente() {
        for (int i = 0; i < cantidadPlatos; i++) {
            if (platos[i].getEstado().equals("pendiente")) {
                return platos[i];
            }
        }
        return null;
    }

    public boolean esVip() {
        return cliente.isVip();
    }

    @Override
    public String toString() {
        String info = "Pedido #" + id + " (" + tipo + ") - " + cliente.getNombre() + "\n";
        for (int i = 0; i < cantidadPlatos; i++) {
            info += "   • " + platos[i].getNombre() + " - $" + platos[i].getPrecio() + "\n";
        }
        return info;
    }
}
