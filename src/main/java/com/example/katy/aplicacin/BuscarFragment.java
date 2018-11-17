package com.example.katy.aplicacin;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class BuscarFragment extends Fragment {

    Spinner org;
    String a = "", orgSelected = "", sexoSelected = "";
    EditText txtID, txtNombre, txtCumple, txtTel;
    TextView txteligeSexo,Nombre,Nombre1,Nombre2,Nombre3;
    EditText checkSexo;
    Button btnBuscar, btnLimpiar;
    ImageView foto;
    private SQLite sqlite;
    ArrayAdapter<CharSequence> adapter;
    int es;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        org = (Spinner) view.findViewById(R.id.spinnerOrgBus);
        txtID = (EditText) view.findViewById(R.id.txtIDBus);
        txtNombre = (EditText) view.findViewById(R.id.txtNombreBus);
        txtCumple= (EditText) view.findViewById(R.id.txtCumpleBus);
        txtTel = (EditText) view.findViewById(R.id.txtTelefonoBus);
        foto = view.findViewById(R.id.fotoBus);
        btnBuscar = (Button) view.findViewById(R.id.btnBuscarBus);
        btnLimpiar = view.findViewById(R.id.botonLimpiar);
        Nombre = view.findViewById(R.id.Nombre);
        Nombre1 = view.findViewById(R.id.Nombre1);
        Nombre2 = view.findViewById(R.id.Nombre2);
        Nombre3 = view.findViewById(R.id.Nombre3);

        org.setVisibility(View.INVISIBLE);
        txtNombre.setVisibility(View.INVISIBLE);
        txtCumple.setVisibility(View.INVISIBLE);
        txtTel.setVisibility(View.INVISIBLE);
        btnLimpiar.setVisibility(View.INVISIBLE);
        Nombre.setVisibility(View.INVISIBLE);
        Nombre1.setVisibility(View.INVISIBLE);
        Nombre2.setVisibility(View.INVISIBLE);
        Nombre3.setVisibility(View.INVISIBLE);

        sqlite = new SQLite(getContext());
        foto.setVisibility(View.INVISIBLE);
        sqlite.abrir();

        adapter = ArrayAdapter.createFromResource(getContext(), R.array.Opciones, android.R.layout.simple_spinner_item);
        org.setAdapter(adapter);

        org.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                orgSelec();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foto.setImageResource(R.drawable.camara);
                txtID.setText("");
                txtNombre.setText("");
                txtCumple.setText("");
                txtTel.setText("");
                org.setSelection(0);
                org.setVisibility(View.INVISIBLE);
                txtNombre.setVisibility(View.INVISIBLE);
                txtCumple.setVisibility(View.INVISIBLE);
                txtTel.setVisibility(View.INVISIBLE);
                btnLimpiar.setVisibility(View.INVISIBLE);
                foto.setVisibility(View.INVISIBLE);
                Nombre.setVisibility(View.INVISIBLE);
                Nombre1.setVisibility(View.INVISIBLE);
                Nombre2.setVisibility(View.INVISIBLE);
                Nombre3.setVisibility(View.INVISIBLE);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!txtID.getText().toString().equals("")) {
                    if (sqlite.getCant(Integer.parseInt(txtID.getText().toString())).getCount() == 1) {
                        org.setVisibility(View.VISIBLE);
                        txtNombre.setVisibility(View.VISIBLE);
                        txtCumple.setVisibility(View.VISIBLE);
                        txtTel.setVisibility(View.VISIBLE);
                        btnLimpiar.setVisibility(View.VISIBLE);
                        Nombre.setVisibility(View.VISIBLE);
                        Nombre1.setVisibility(View.VISIBLE);
                        Nombre2.setVisibility(View.VISIBLE);
                        Nombre3.setVisibility(View.VISIBLE);
                        foto.setVisibility(View.VISIBLE);
                        int f = Integer.parseInt(txtID.getText().toString());
                        Cursor cursor = sqlite.getCant(f);
                        String nombre = null, organizacion = null, cumple = null, sexo = null, telefono = null, fotoPath=null;
                        if (cursor.moveToFirst()) {
                            do {
                                nombre = cursor.getString(1);
                                organizacion = cursor.getString(2);
                                cumple = cursor.getString(3);
                                sexo = cursor.getString(4);
                                telefono = cursor.getString(5);
                                fotoPath = cursor.getString(6);
                            } while (cursor.moveToNext());
                        }

                        switch (organizacion) {
                            case "Alacena":
                                org.setSelection(1);
                                break;
                            case "Refrigerador":
                                org.setSelection(2);
                                break;
                            case "Congelador":
                                org.setSelection(3);
                                break;
                            case "Otros":
                                org.setSelection(4);
                                break;
                        }

                        txtNombre.setText(nombre);
                        txtCumple.setText(cumple);
                        txtTel.setText(telefono);
                        txtNombre.setEnabled(false);
                        org.setEnabled(false);
                        txtCumple.setEnabled(false);
                        txtTel.setEnabled(false);
                        foto.setImageURI(Uri.parse(fotoPath));
                    } else
                        Toast.makeText(getContext(), "Error: No existe ese ID" +
                                "", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error: No has puesto un ID" +
                            "", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;


    }

    public void orgSelec(){
        String opcion = String.valueOf(org.getSelectedItemId());
        int op = Integer.parseInt(opcion);
        System.out.println(opcion);
        if (op == 0) {

        }
        if (op == 1) {

            orgSelected = adapter.getItem(1).toString();
        } else if (op == 2) {

            orgSelected = adapter.getItem(2).toString();
        } else if (op == 3) {

            orgSelected = adapter.getItem(3).toString();
        } else if (op == 4) {

            orgSelected = adapter.getItem(4).toString();

        }
    }
}
