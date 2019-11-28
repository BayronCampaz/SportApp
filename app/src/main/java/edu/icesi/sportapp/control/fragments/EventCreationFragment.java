package edu.icesi.sportapp.control.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.icesi.sportapp.MainActivity;
import edu.icesi.sportapp.MapsActivity;
import edu.icesi.sportapp.R;
import edu.icesi.sportapp.control.fragments.FeedFragment;
import edu.icesi.sportapp.model.entity.EventSport;
import edu.icesi.sportapp.model.remote.DatabaseConstants;

public class EventCreationFragment extends Fragment {

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int monthCalendar = c.get(Calendar.MONTH);
    final int dayCalendar = c.get(Calendar.DAY_OF_MONTH);
    final int yearCalendar = c.get(Calendar.YEAR);
    final int hour = c.get(Calendar.HOUR_OF_DAY);
    final int minute = c.get(Calendar.MINUTE);


    private EditText nameEt;
    private EditText descriptionEt;
    private EditText numberPeopleEt;
    private EditText moneyEt;
    private EditText dateEt;
    private ImageButton datePickerBtn;
    private EditText timeEt;
    private ImageButton timePickerBtn;
    private Spinner sportSp;
    private Button addLocationBtn;
    private Button createEventBtn;
    private LatLng latLng;

    FirebaseDatabase db;
    FirebaseAuth auth;

    public EventCreationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_creation, container, false);
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        nameEt = view.findViewById(R.id.event_name_et);
        descriptionEt = view.findViewById(R.id.event_description_et);
        numberPeopleEt = view.findViewById(R.id.number_people_et);
        moneyEt = view.findViewById(R.id.event_money_et);
        dateEt = view.findViewById(R.id.date_picker_et);
        timeEt = view.findViewById(R.id.time_picker_et);
        datePickerBtn = view.findViewById(R.id.event_date_btn);
        timePickerBtn = view.findViewById(R.id.time_btn);
        addLocationBtn = view.findViewById(R.id.add_location_btn);
        createEventBtn = view.findViewById(R.id.create_event_btn);
        sportSp = view.findViewById(R.id.event_sports_spinner);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(container.getContext(),
                R.array.sports_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportSp.setAdapter(adapter);

        datePickerBtn.setOnClickListener(view1 -> {
            DatePickerDialog getDate = new DatePickerDialog(container.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                    final int currentMonth = month + 1;
                    //Formateo el día obtenido: antepone el 0 si son menores de 10
                    String formattedDay = (dayOfMonth < 10)? "0" + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                    //Formateo el mes obtenido: antepone el 0 si son menores de 10
                    String formattedMonth = (currentMonth < 10)? "0" + String.valueOf(currentMonth):String.valueOf(currentMonth);
                    //Muestro la fecha con el formato deseado
                    dateEt.setText(formattedDay + "/" + formattedMonth + "/" + year);


                }
                //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
                /**
                 *También puede cargar los valores que usted desee
                 */
            },yearCalendar, monthCalendar, dayCalendar);
            //Muestro el widget
            getDate.show();
        });

        timePickerBtn.setOnClickListener(view1 -> {
            TimePickerDialog getTime = new TimePickerDialog(container.getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    //Formateo el hora obtenido: antepone el 0 si son menores de 10
                    String formattedHour =  (hourOfDay < 10)? String.valueOf("0" + hourOfDay) : String.valueOf(hourOfDay);
                    //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                    String formattedMinutes = (minute < 10)? String.valueOf("0" + minute):String.valueOf(minute);
                    //Muestro la hora con el formato deseado
                    timeEt.setText(formattedHour + ":" + formattedMinutes);
                }
                //Estos valores deben ir en ese orden
                //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
                //Pero el sistema devuelve la hora en formato 24 horas
            }, hour, minute, false);

            getTime.show();
        });

        addLocationBtn.setOnClickListener(view1 -> {

        });

        createEventBtn.setOnClickListener(view1 -> {
            if (nameEt.getText().toString().trim().isEmpty()) {
                Toast.makeText(container.getContext(), "El campo de nombre esta vacio", Toast.LENGTH_LONG).show();
                return;
            }

            if (numberPeopleEt.getText().toString().trim().isEmpty()) {
                Toast.makeText(container.getContext(), "El campo de número de personas esta vacio", Toast.LENGTH_LONG).show();
                return;
            }

            if (moneyEt.getText().toString().trim().isEmpty()) {
                Toast.makeText(container.getContext(), "El campo de costo por persona esta vacio", Toast.LENGTH_LONG).show();
                return;
            }

            if (dateEt.getText().toString().trim().isEmpty()) {
                Toast.makeText(container.getContext(), "El campo de fecha esta vacio", Toast.LENGTH_LONG).show();
                return;
            }

            if (timeEt.getText().toString().trim().isEmpty()) {
                Toast.makeText(container.getContext(), "El campo de dia esta vacio", Toast.LENGTH_LONG).show();
                return;
            }
            try{
                String uid =  db.getReference().child(DatabaseConstants.EVENTS).child(auth.getCurrentUser().getUid()).push().getKey();
                int photo = 0;
                String name = nameEt.getText().toString();
                String ownerID = auth.getCurrentUser().getUid();
                String description = descriptionEt.getText().toString();
                int numberPeople = Integer.parseInt(numberPeopleEt.getText().toString());
                double price = Double.parseDouble(moneyEt.getText().toString());
                String sport = sportSp.getSelectedItem().toString();
                String stringDate = dateEt.getText().toString() + " " + timeEt.getText().toString();
                DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date dateAndTime = sourceFormat.parse(stringDate);
                long date = dateAndTime.getTime();
                String status = EventSport.ACTIVE;

                EventSport event = new EventSport(uid, ownerID, photo, name, description, numberPeople, price, sport, date, latLng, status);
                db.getReference().child("sportEvents").child(auth.getCurrentUser().getUid())
                        .child(uid).setValue(event);

                Toast toast = Toast.makeText(container.getContext(), "El evento ha sido creado exitosamente", Toast.LENGTH_SHORT);
                toast.show();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new FeedFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }catch (Exception e){
                e.printStackTrace();
            }

        });

        addLocationBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(container.getContext(), MapsActivity.class);
            startActivityForResult(intent, 11);

        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 11 && resultCode == MainActivity.RESULT_OK){
            latLng =  data.getExtras().getParcelable("location");

        }
    }

}
