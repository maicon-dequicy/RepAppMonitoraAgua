package br.com.aplicativomonitoramentoagua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sensor02 extends AppCompatActivity
{
    private Toolbar t;
    private Button Sensor01;
    private Button Sensor02;
    private Button Sensor03;
    private Button Sensor04;
    private TextView resultadoSensorPh;

    private String previsaoTemperatura;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference valorTeste = FirebaseDatabase.getInstance().getReference();
    private TextView resultadoSensor;
    private DatabaseReference ref3;

    private FloatingActionButton adicionarSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor02);
        cria_botoes();

        ref3 = referencia.child("PAI").child("LastRecord").child("PH");
        ref3.addListenerForSingleValueEvent(
                new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        String resultado = snapshot.getValue().toString();
                        resultadoSensor.setText(resultado);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        System.out.println("Erro!");
                    }
                }
        );

        t = (Toolbar) findViewById(R.id.toollbarSensor02);
        setSupportActionBar(t);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    private void cria_botoes()
    {
        adicionarSensor = (FloatingActionButton)findViewById(R.id.adicionarNovoSensorSensor02);
        resultadoSensor = (TextView) findViewById(R.id.resultadoSensor02);
        Sensor01 = (Button) findViewById(R.id.BotaoTemp);
        Sensor03 = (Button) findViewById(R.id.Turbidez);
        Sensor04 = (Button) findViewById(R.id.TelaPH_TDS);
    }
    public void atualizarPH(View view) {

        ref3.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String resultado = snapshot.getValue().toString();
                        resultadoSensor.setText(resultado);
                        toastAtualizarPh(view);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Erro!");
                    }
                }
        );
    }
    public void toastAtualizarPh(View view)
    {
        Toast.makeText(
                getApplicationContext(), "Valores Atualizados!",Toast.LENGTH_LONG
        ).show();
    }

    public void irTelaTemperatura(View view)
    {
        Intent intent = new Intent(Sensor02.this, nova_perfilGeral.class);
        startActivity(intent);
    }
    public void irTelaTurbidez(View view)
    {
        Intent intent = new Intent(Sensor02.this, Sensor03.class);
        startActivity(intent);
    }
    public void irTelaTDS(View view)
    {
        Intent intent = new Intent(Sensor02.this, Sensor04.class);
        startActivity(intent);
    }
    public void irTelaAdicionaSensorPh(View view)
    {
        Intent intent = new Intent(Sensor02.this, Adicionar_Sensor.class);
        startActivity(intent);
    }
}