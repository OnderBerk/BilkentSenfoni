package com.mobileproje.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.mobileproje.Concert;
import com.mobileproje.Database.ConcertDB;
import com.mobileproje.Database.DatabaseHelper;
import com.mobileproje.MyIntentService;
import com.mobileproje.MyRecyclerViewAdapter;
import com.mobileproje.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class SecondActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Concert> concertlist;
    Dialog customDialog;
    IntentFilter mIntentFilter;
    int total=0;
    DatabaseHelper databaseHelper;
    Button btnAdd, btnCancel;
    EditText etName,etSurname,etPrice,etTicket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_second);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        recyclerView=findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        databaseHelper= new DatabaseHelper(this);


        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("filename","concert.json");
        startService(intent);

        Log.d("Burda",":intentFilter" );
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("CONCERT_JSON_PARSE_ACTION");


        // Register the receiver
        registerReceiver(mIntentReceiver, mIntentFilter);

    }
    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Storing the received data into a Bundle

            Log.d("Service","Braodcast mesage taken" );
            Bundle b = intent.getExtras();
            concertlist = b.getParcelableArrayList("liste");
            fillListView();
        }
    };
    void fillListView() {
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, concertlist);
        recyclerView.setAdapter(adapter);
    }

    public void displayDialog(int selectedPoistion, Concert a ){
        final Concert selectedalbum = a;


        customDialog = new Dialog(this);
        customDialog.setContentView(R.layout.dialog);
        customDialog.setTitle("Custom Dialog");

        etName = customDialog.findViewById(R.id.etName);
        etSurname = customDialog.findViewById(R.id.etSurname);
        etPrice = customDialog.findViewById(R.id.etPrice);
        etTicket = customDialog.findViewById(R.id.etTicket);

        etName.setText(a.getName());
        etPrice.setText(a.getPrice()+"");

        btnAdd = (Button) customDialog.findViewById(R.id.btnBuy);
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name=etName.getText().toString();
                String surname=etSurname.getText().toString();
                int price=Integer.parseInt(etPrice.getText().toString());
                int ticket=Integer.parseInt(etTicket.getText().toString());
                total=price*ticket;
                ConcertDB.insertData(databaseHelper,name,surname,total);
                FancyToast.makeText(getBaseContext(),"Your ticket is reserved",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                customDialog.dismiss();
            }
        });

        btnCancel = (Button) customDialog.findViewById(R.id.btnClose);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }
}
