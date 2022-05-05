package com.example.crudspinner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    private EditText editNombre;
    private EditText editComentario;
    private EditText txtNombre;
    private EditText txtComentario;

    //Declaración del spinner y su Adapter
    private Spinner spinComentarios;
    private ArrayAdapter<Comentario> spinnerAdapter;

    //Lista de comentarios y comentario actual
    private ArrayList<Comentario> lista;
    private Comentario c;

    //Controlador de bases de datos
    private MyOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos los elementos de la interfaz
        editNombre=(EditText) findViewById(R.id.editNombre);
        editComentario=(EditText)findViewById(R.id.editComentario);
        txtNombre=(EditText) findViewById(R.id.txtNombre);
        txtComentario=(EditText)findViewById(R.id.txtComentario);

        //Los elementos del panel inferior no seran editables
        txtNombre.setEnabled(false);
        txtComentario.setEnabled(false);

        Button btnCrear = (Button) findViewById(R.id.btnCrear);
        Button btnVer = (Button) findViewById(R.id.btnVer);
        Button btnEliminar = (Button) findViewById(R.id.btnEliminar);

        btnCrear.setOnClickListener(this);
        btnVer.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);

        //Iniciamos el controlador de la base de datos
        db=new MyOpenHelper(this);

        //Iniciamos el spinner y la lista de comentarios
        spinComentarios= findViewById(R.id.spinComentarios);
        lista=db.getComments();

        //Creamos el adapter y lo asociamos al spinner
        spinnerAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,lista);
        spinComentarios.setAdapter(spinnerAdapter);
        spinComentarios.setOnItemSelectedListener(this);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        //Acciones de cada boton
        switch(v.getId()) {
            case R.id.btnCrear:
                //Insertamos un nuevo elemento en base de datos
                db.insertar(editNombre.getText().toString(), editComentario.getText().toString());
                //Actualizamos la lista de comentarios
                lista = db.getComments();
                //Actualizamos el adapter y lo asociamos de nuevo al spinner
                spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista);
                spinComentarios.setAdapter(spinnerAdapter);
                //Limpiamos el formulario
                editNombre.setText("");
                editComentario.setText("");

                break;
            case R.id.btnVer:
                //Si hay algun comentario seleccionado mostramos sus valores en la parte inferior
                if (c != null) {
                    txtNombre.setText((String) c.getNombre());
                    txtComentario.setText( (String) c.getComentario());
                }
                break;
            case R.id.btnEliminar:
                //Si hay algun comentario seleccionado lo borramos de la base de datos y actualizamos el spinner
                if (c != null) {
                    db.borrar(c.getId());
                    lista = db.getComments();
                    spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista);
                    spinComentarios.setAdapter(spinnerAdapter);
                    //Limpiamos los datos del panel inferior
                    txtNombre.setText("");
                    txtComentario.setText("");
                    //Eliminamos el Comentario actual puesto que ya no existe en base de datos
                    c = null;
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
        }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //if (((View) spinComentarios.getParent()).getId() == R.id.spinComentarios) {
            //Si hay elementos en la base de datos, establecemos el comentario actual a partir del
            //indice del elemento seleccionado en el spinner
            if(lista.size()>0) {
                c = lista.get(adapterView.getSelectedItemPosition());
            }
        //}
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}