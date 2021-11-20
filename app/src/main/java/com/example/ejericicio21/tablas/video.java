package com.example.ejericicio21.tablas;

public class video {
    private Integer id;
    private byte[] imagen;

    public video(Integer id, byte[] imagen) {
        this.id = id;
        this.imagen = imagen;
    }

    public video(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
