package com.example.recycleviewejemplo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    private UsuarioRepository listaUsuario;
    private  LayoutInflater inflater;

    public Adaptador(Context context) {
        listaUsuario = UsuarioRepository.getInstance();
        inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.simple_element,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder holder, int position) {
        Usuario usuario = listaUsuario.get(position);
        Profesion profesion = ProfesionRepository.getInstance().getProfessionByImg(usuario.getImgProfesion());
        holder.nombre.setText(usuario.getApellidos()+" "+ usuario.getNombre());
        holder.profesion.setText(profesion.getNombre());
        holder.imagen.setImageResource(profesion.getImg());



    }

    @Override
    public int getItemCount() {
        return listaUsuario.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombre;
        private TextView profesion;
        private ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.textViewNombre);
            this.profesion = itemView.findViewById(R.id.textViewOficio);
            imagen = itemView.findViewById(R.id.imageView);
        }
    }
}
