package com.example.katy.aplicacin;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;


public class NuevoFragment extends Fragment {

    private static final String CARPETA_PRINCIPAL = "Pictures/";
    private static final String CARPETA_IMAGEN = "imagenes";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;
    private static final int NOTIFICATION_ID = 1;
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;


    private String path;
    File fileImage;
    Bitmap bitmap;

    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    final Calendar calendario = Calendar.getInstance();
    final int yy = calendario.get(Calendar.YEAR);
    final int mm = calendario.get(Calendar.MONTH);
    final int dd = calendario.get(Calendar.DAY_OF_MONTH);
    final Calendar c = Calendar.getInstance();
    int hora, min,dia,mes,ano,hora11;
    String cadenaF, cadenaH,fecha_sistema,hora_sistema;


    final int horas = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    Spinner org;
    String a = "", orgSelected = "", sexoSelected="";
    EditText txtID, txtNombre, txtCumple, txtTel,cantidad, fecha_cad;
    EditText fechaCad;
    TextView checkSexo;
    Button registra, listar, btnFecha, btnFoto,btnHora, btncad;
    ImageView foto;
    final int tiempo=5;
    private SQLite sqlite;
    ArrayAdapter<CharSequence> adapter;

    public NuevoFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view= inflater.inflate(R.layout.fragment_nuevo, container, false);
//View view=inflater.inflate(R.layout.fragment_nuevo,container,false);


        final View view = inflater.inflate(R.layout.fragment_nuevo, null);


        org = (Spinner) view.findViewById(R.id.spinnerOrg);
        txtNombre = (EditText) view.findViewById(R.id.txtNombre);
        txtCumple= (EditText) view.findViewById(R.id.txtCumple);
        checkSexo= (TextView) view.findViewById(R.id.checkBoxSexo);
        fechaCad= (EditText) view.findViewById(R.id.fechaCad);
        fecha_cad =(EditText)view.findViewById(R.id.fecha_cadu);
        registra = (Button) view.findViewById(R.id.botonRegistrar);
        cantidad=(EditText) view.findViewById(R.id.cantidad);
        listar = (Button) view.findViewById(R.id.btnList);
        btnHora=(Button)view.findViewById(R.id.obtenerh);
        btnFoto = view.findViewById(R.id.btnAgregarFoto);
        foto = view.findViewById(R.id.foto);
        btnFecha = view.findViewById(R.id.btnDate);
        btncad=view.findViewById(R.id.btncad);

        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH)+1;
        ano = calendario.get(Calendar.YEAR);
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        min = calendario.get(Calendar.MINUTE);
        fecha_sistema=mes+"-"+dia+"-"+ano+" ";
        hora_sistema = hora+":"+min;


        super.onCreate(savedInstanceState);

        sqlite = new SQLite(getContext());
        sqlite.abrir();
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.Opciones, android.R.layout.simple_spinner_item);
        org.setAdapter(adapter);

        final Drawable no_foto = foto.getDrawable();

        org.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                orgSelec();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });

        btncad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int tiempo=5;
                final int año;


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        // final int resta = dd-tiempo;
                        // String resu= String.valueOf(resta);
                        //fechaCad.setText(resu,mm,yy);

                        fecha_cad.setText((dayOfMonth)+"/"+(monthOfYear+1)+"/"+year);
                        // fechaCad.setText((dayOfMonth-5)+"/"+(monthOfYear+1)+"/"+year);//Fecha  de notificación


                    }
                }, yy, mm, dd);

                datePickerDialog.show();

            }
        });

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TimePickerDialog  timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //Formateo el hora obtenido: antepone el 0 si son menores de 10
                        String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                        String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                        //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                        String AM_PM;
                        if(hourOfDay < 12) {
                            AM_PM = "a.m.";
                        } else {
                            AM_PM = "p.m.";
                        }
                        //Muestro la hora con el formato deseado

                     //   AlarmManager alarmManager =(AlarmManager)
                        fechaCad.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);



                    }

                },horas, minuto, false);

                timePickerDialog.show();



            }
        });

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int tiempo=5;
                final int año;


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                       // final int resta = dd-tiempo;
                       // String resu= String.valueOf(resta);
                        //fechaCad.setText(resu,mm,yy);

                                txtCumple.setText((dayOfMonth)+"/"+(monthOfYear+1)+"/"+year);
                               // fechaCad.setText((dayOfMonth-5)+"/"+(monthOfYear+1)+"/"+year);//Fecha  de notificación


                    }
                }, yy, mm, dd);

                datePickerDialog.show();
            }
        });



        final Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR); //obtenemos el año
        int mes = c.get(Calendar.MONTH); //obtenemos el mes
        mes = mes + 1;
        int dia = c.get(Calendar.DAY_OF_MONTH); // obtemos el día.
        checkSexo.setText(dia+" : "+mes+" : "+anio); //cambiamos el texto que tiene el TextView por la fecha actual.




        registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (
                                !txtNombre.getText().toString().equals("") &&
                                !fecha_cad.getText().toString().equals("") &&
                                !cantidad.getText().toString().equals("") &&
                                !fechaCad.getText().toString().equals("") &&
                                !txtCumple.getText().toString().equals("") &&
                                        !orgSelected.equals("") &&
                                        !(no_foto == foto.getDrawable())) {
                    setAlarm(); //No se donde agregar este método

                    int x = Integer.parseInt(cantidad.getText().toString());


                    //Toast.makeText(getContext(), t + " " + txtNombre.getText().toString() + orgSelected + " " + txtCumple.getText().toString() + " " + checkSexo + " " +
                          // x, Toast.LENGTH_LONG).show();

                    if (sqlite.addRegistro(txtNombre.getText().toString(), orgSelected,
                            fecha_cad.getText().toString(), checkSexo.getText().toString(),
                            x,path) &&  (sqlite.addRegistroAlarma(txtNombre.getText().toString(),txtCumple.getText().toString(),fechaCad.getText().toString())))

                    {
                        //recuperar id del ultimo registtro y pasa como parmetro

                        Toast.makeText(getContext(), "Producto añadido", Toast.LENGTH_SHORT).show();
                        txtNombre.setText("");
                        fecha_cad.setText("");
                        cantidad.setText("");
                        fechaCad.setText("");
                        org.setSelection(0);
                        foto.setImageResource(R.drawable.ic_launcher_background);


                    } else {
                        Toast.makeText(getContext(), "Error: compruebe que los datos sean correctos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error: no puede haber campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentPrincipal, new ListarFragment()).commit();
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

    public void mostrarDialogOpciones(){

        final CharSequence[] opciones = {"Tomar foto","Elegir de la galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (opciones[i].equals("Tomar foto")){
                    abrirCamara();
                }else if (opciones[i].equals("Elegir de la galeria")){
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/");
                    startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                }else {
                    dialogInterface.dismiss();
                }

            }
        });
        builder.show();
    }

    private void abrirCamara() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File file = new File(Environment.getExternalStorageDirectory(), DIRECTORIO_IMAGEN);
        boolean isCreated=file.exists();

        if (!isCreated){
            isCreated=file.mkdir();
        }

        if (isCreated){


            Long consecutivo = System.currentTimeMillis()/1000;
            String nombre=consecutivo.toString()+".jpg";

            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN+
                    File.separator+nombre;

            fileImage=new File(path);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImage));

            startActivityForResult(intent,COD_FOTO);
        }

    }






    public void setAlarm() {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        Intent  alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, 0);

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_MONTH, dd);
        calendar.set(Calendar.MONTH, mm);
        calendar.set(Calendar.YEAR, yy);
        calendar.set(Calendar.HOUR_OF_DAY, horas);
        calendar.set(Calendar.MINUTE, minuto);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        //alarmManager.setRepeating(AlarmManager.RTC, AlarmManager.INTERVAL_HALF_DAY, AlarmManager.INTERVAL_HALF_DAY, pendingIntent);

    }









    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case COD_SELECCIONA:
                Uri mipath=data.getData();
                foto.setImageURI(mipath);
                path=mipath.toString();
                break;
            case COD_FOTO:
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String s, Uri uri) {
                                Log.i("Path: ",path);
                            }
                        });

                bitmap= BitmapFactory.decodeFile(path);
                foto.setImageBitmap(bitmap);
                break;
        }

    }

}
