package com.MSReabastecimiento.Reabastecimiento.DTO;


public class ProveedorDTO {
    private int idProveedor;
    private String nomProv;
    private String correoProv;
    private String telProv;
    private String dirProv;

    public String getCorreoProv() {
        return correoProv;
    }
    public int getIdProveedor() {
        return idProveedor;
    }
    public String getNomProv() {
        return nomProv;
    }
    public String getDirProv() {
        return dirProv;
    }
    public String getTelProv() {
        return telProv;
    }
    public void setCorreoProv(String correoProv) {
        this.correoProv = correoProv;
    }
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
    public void setNomProv(String nombreProv) {
        this.nomProv = nombreProv;
    }
    public void setDirProv(String dirProv) {
        this.dirProv = dirProv;
    }
    public void setTelProv(String telProv) {
        this.telProv = telProv;
    }
}
