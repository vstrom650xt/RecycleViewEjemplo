package com.example.recycleviewejemplo;

import java.io.Serializable;
import java.util.Comparator;

public class Usuario implements Serializable {
    private String nombre;
    private String apellidos;
    private int imgProfesion;

    public static  final Comparator<Usuario> SORT_BY_NAME = new Comparator<Usuario>() {
        @Override
        public int compare(Usuario usuario1, Usuario usuario2) {
            if (usuario1.apellidos.compareToIgnoreCase(usuario2.getApellidos())==0)
                return usuario1.apellidos.compareToIgnoreCase(usuario2.getNombre());
            return usuario1.apellidos.compareToIgnoreCase(usuario2.getApellidos());

        }
    };

    public static  final Comparator<Usuario> SORT_BY_JOB =(usuario1, usuario2) -> {

        Profesion p1, p2;
        p1 = ProfesionRepository.getInstance().getProfessionByImg(usuario1.imgProfesion);
        p2 = ProfesionRepository.getInstance().getProfessionByImg(usuario2.imgProfesion);
        return p1.getNombre().compareToIgnoreCase(p2.getNombre());

    };



    public Usuario(String nombre, String apellidos, int imgProfesion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.imgProfesion = imgProfesion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getImgProfesion() {
        return imgProfesion;
    }

    @Override
    public String toString() {
        return nombre + apellidos;
    }
}
