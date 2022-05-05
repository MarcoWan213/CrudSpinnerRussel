package com.example.crudspinner;

public class Comentario {
    int id;
    String nombre;
    String comentario;

    public Comentario(int id, String nombre, String comentario) {
        this.id = id;
        this.nombre = nombre;
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return nombre ;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getComentario() {
        return comentario;
    }
}
