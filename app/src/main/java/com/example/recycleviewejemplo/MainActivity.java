package com.example.recycleviewejemplo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Switch switchSort;
    private FloatingActionButton addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        switchSort = findViewById(R.id.switchSort);
        addUser = findViewById(R.id.addUser);


        Adaptador adaptador = new Adaptador(this);
        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    switchSort.setText("profesion");
                    UsuarioRepository.getInstance().sort(Usuario.SORT_BY_JOB);
                    adaptador.notifyDataSetChanged();
                } else {
                    switchSort.setText("nombre");
                    UsuarioRepository.getInstance().sort(Usuario.SORT_BY_NAME);
                    adaptador.notifyDataSetChanged();
                }
            }
        });
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {


            if (result.getResultCode() == RESULT_CANCELED)
                Toast.makeText(this, "Cancelado por el usuario",
                        Toast.LENGTH_LONG).show();
            else if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                Usuario usuario = (Usuario)
                        data.getExtras().getSerializable("usuario");
                UsuarioRepository.getInstance().add(usuario);
                adaptador.notifyDataSetChanged(); // refresca la pantalla
                Toast.makeText(this, "Nuevo Usuario " +
                                usuario.getNombre() + " " + usuario.getApellidos(),
                        Toast.LENGTH_LONG).show();
            }


        }); // esto siempre antes de lanzarlo

        addUser.setOnClickListener(view -> {
            Intent intent = new Intent(this, FormularyActivity.class);
            activityResultLauncher.launch(intent);
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull
            RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder
                                          target) {
                int posTarget = target.getAdapterPosition();
                Usuario usuario =
                        UsuarioRepository.getInstance().get(viewHolder.getAdapterPosition());
                UsuarioRepository.getInstance().remove(usuario);
                UsuarioRepository.getInstance().add(posTarget, usuario);
                recyclerView.getAdapter().notifyItemMoved(viewHolder.getAdapterPosition(),
                        target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int
                    direction) {
                int position = viewHolder.getAdapterPosition();
                Usuario usuario = UsuarioRepository.getInstance().get(position);
                UsuarioRepository.getInstance().remove(usuario);
                adaptador.notifyItemRemoved(position);
                Snackbar.make(recyclerView, "Deleted " + usuario.getNombre() + " "
                                + usuario.getApellidos(), Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                UsuarioRepository.getInstance().add(position,
                                        usuario);
                                adaptador.notifyItemInserted(position);
                            }
                        }).show();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }
}