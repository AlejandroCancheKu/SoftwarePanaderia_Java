/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author UMT
 */
public class ClienteEstandar extends Cliente{

    public ClienteEstandar(String tipoCliente, int idCliente, String nombreCliente, String aPaternoCliente, Direccion direccion, int conchapuntos) {
        super(tipoCliente, idCliente, nombreCliente, aPaternoCliente, direccion, conchapuntos);
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getaPaternoCliente() {
        return aPaternoCliente;
    }

    public void setaPaternoCliente(String aPaternoCliente) {
        this.aPaternoCliente = aPaternoCliente;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public int getConchapuntos() {
        return conchapuntos;
    }

    public void setConchapuntos(int conchapuntos) {
        this.conchapuntos = conchapuntos;
    }
    
    @Override
    public int calcularConchapuntos(float monto) {
       int conchapuntos = (int) (monto/10);

       return conchapuntos;
    }
    
}
