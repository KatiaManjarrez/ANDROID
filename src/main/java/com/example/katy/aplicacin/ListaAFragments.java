package com.example.katy.aplicacin;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;



public class ListaAFragments extends Fragment {


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view= inflater.inflate(R.layout.fragment_lista_afragments, container, false);

            ListView l=(ListView)view.findViewById(R.id.listaa);
            SQLite sqlite;

            //base de datos
            sqlite = new SQLite(getContext());
            sqlite.abrir();
            Cursor cursor = sqlite.getRegistrosA();
            ArrayList<String> reg = sqlite.getA(cursor);

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,reg);
            l.setAdapter(adaptador);

            return view;
        }


    }
