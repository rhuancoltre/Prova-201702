package com.teste.admin.rhuandsp2017;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.teste.admin.rhuandsp2017.dao.Banco;
import com.teste.admin.rhuandsp2017.model.Time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Banco banco;
    private Time time;
    private ListView lvTimes;
    private List<Time> listarTimes = new ArrayList<>();
    private ArrayAdapter<Time> aaTimes;
    private static final int REQUEST_REG_TIME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvTimes = (ListView) findViewById(R.id.lvTimes);
        banco = new Banco(this);
        aaTimes = new ArrayAdapter<Time>(this, android.R.layout.simple_list_item_1, listarTimes);

        lvTimes.setAdapter(aaTimes);
        lvTimes.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener(){
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo){
                contextMenu.setHeaderTitle("Time");
                getMenuInflater().inflate( R.menu.menu_main, contextMenu);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTime();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        listarTimes = banco.getTimes();

        aaTimes = new ArrayAdapter<Time>(this, android.R.layout.simple_list_item_1, listarTimes);

        lvTimes.setAdapter(aaTimes);
    }

    @Override
    public boolean onContextItemSelected(MenuItem selectedEstado){
        final AdapterView.AdapterContextMenuInfo pListMenu = (AdapterView.AdapterContextMenuInfo) selectedEstado.getMenuInfo();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        time = ((Time) lvTimes.getItemAtPosition(pListMenu.position));

        switch (selectedEstado.getItemId()){
            case R.id.alterar:

                Intent iCadastro = new Intent(this, TimeActivity.class);
                iCadastro.putExtra("time", time);
                iCadastro.putExtra("data", time.getCadastro());
                startActivity(iCadastro);
                break;

            case R.id.deletar:
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    banco.removerTime(time);
                    listarTimes.remove(pListMenu.position);
                    aaTimes.notifyDataSetChanged();
                }

            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.setMessage("Deletar Time?");
            AlertDialog dialog = builder.create();
            dialog.show();
            Toast.makeText(this,R.string.deletarTime,Toast.LENGTH_LONG);
    }
        return super.onContextItemSelected(selectedEstado);
    }

    private void addTime(){

        Intent intent = new Intent(this, TimeActivity.class);
        Date date = new Date();
        DateFormat inFormat = new SimpleDateFormat( "dd/MM/yyyy");
        intent.putExtra("data",inFormat.format(date));
        startActivityForResult(intent, REQUEST_REG_TIME);
    }
}
