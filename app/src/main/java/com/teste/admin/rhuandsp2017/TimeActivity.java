package com.teste.admin.rhuandsp2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.teste.admin.rhuandsp2017.dao.Banco;
import com.teste.admin.rhuandsp2017.model.Time;

public class TimeActivity extends AppCompatActivity {

    private Banco banco;
    private String data;
    private EditText edtCodigo;
    private EditText edtNome;
    private EditText edtFundacao;
    private EditText edtCadastro;
    private Time time = new Time();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        banco = new Banco(this);

        edtCodigo = (EditText) findViewById(R.id.edtCodigo);
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtFundacao = (EditText) findViewById(R.id.edtFundacao);
        edtCadastro = (EditText) findViewById(R.id.edtCadastro);
        data = getIntent().getCharSequenceExtra("data").toString();

        try {
            time = (Time) getIntent().getSerializableExtra("time");
            if(time != null){
                edtCodigo.setText(String.valueOf(time.getId()));
                edtNome.setText(String.valueOf(time.getNome()));
                edtFundacao.setText(String.valueOf(time.getFundacao()));
            }
        }catch(Exception e){
            System.out.println("");
        }

        edtCadastro.setText(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_salvar:
                this.salvarTime();
                Toast.makeText(getApplicationContext(), R.string.opsalvo, Toast.LENGTH_LONG).show();
                this.limparCampos();
                finish();
                break;

            case R.id.action_cancelar:
                Toast.makeText(getApplicationContext(), R.string.opcancela, Toast.LENGTH_LONG).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void salvarTime() {
        time = new Time();
        time.setNome(edtNome.getText().toString());
        time.setFundacao(edtFundacao.getText().toString());
        time.setCadastro(edtCadastro.getText().toString());
        if(!edtCodigo.getText().toString().isEmpty()){
            time.setId(Integer.parseInt(edtCodigo.getText().toString()));
            banco.updateTime(time);
        }else{
            banco.inserirTime(time);
        }
    }

    private void limparCampos() {
        edtCodigo.setText("");
        edtNome.setText("");
        edtFundacao.setText("");
        edtCadastro.setText("");
    }
}


