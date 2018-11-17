package com.example.katy.aplicacin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ComprasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText idprod,nom,txcantidad;
    Button botonagregar;
    private SQLite sqlite;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ComprasFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_compras, null);
        nom = (EditText) view.findViewById(R.id.nomproducto);
        txcantidad= (EditText) view.findViewById(R.id.txcantidad);
        botonagregar = (Button) view.findViewById(R.id.botonagregar);
        sqlite = new SQLite(getContext());
        sqlite.abrir();

        botonagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (
                        !nom.getText().toString().equals("") &&
                        !txcantidad.getText().toString().equals("")
                        ) {

                    int b = Integer.parseInt(txcantidad.getText().toString());


                    //Toast.makeText(getContext(), a + " " + nom.getText().toString()  + b, Toast.LENGTH_LONG).show();

                    if (sqlite.addRegistroProducto(nom.getText().toString(),b)) {
                        //recuperar id del ultimo registtro y pasa como parmetro

                        Toast.makeText(getContext(), "Registro añadido", Toast.LENGTH_SHORT).show();
                        nom.setText("");
                        txcantidad.setText("");
                    } else {
                        Toast.makeText(getContext(), "Error: compruebe que los datos sean correctos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error: no puede haber campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
