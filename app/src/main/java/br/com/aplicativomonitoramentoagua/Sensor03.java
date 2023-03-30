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

public class Sensor03 extends AppCompatActivity
{
    private Toolbar t;
    private Button BTSensor01;
    private Button BTSensor02;
    private Button BTSensor03;
    private Button BTSensor04;
    private String previsaoTemperatura;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference valorTeste = FirebaseDatabase.getInstance().getReference();
    private TextView resultadoSensor;
    private DatabaseReference ref4;
    private FloatingActionButton adicionaSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor03);
        cria_botoes();

        ref4 = referencia.child("PAI").child("LastRecord").child("Turbidez");
        ref4.addListenerForSingleValueEvent(
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

        t = (Toolbar) findViewById(R.id.toollbarSensor03);
        setSupportActionBar(t);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void cria_botoes()
    {
        adicionaSensor = (FloatingActionButton) findViewById(R.id.adicionarNovoSensor03);
        resultadoSensor = (TextView) findViewById(R.id.resultadoSensor03);
        BTSensor01 = (Button) findViewById(R.id.TelaTB_SensorTemperatura);
        BTSensor02 = (Button) findViewById(R.id.TelaTB_SensorPH);
        BTSensor04 = (Button) findViewById(R.id.TelaTB_Sensor04);
    }
    public void atualizarTurb(View view) {

        ref4.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String resultado = snapshot.getValue().toString();
                        resultadoSensor.setText(resultado);
                        toastAtualizarTurb(view);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Erro!");
                    }
                }
        );
    }
    public void toastAtualizarTurb(View view)
    {
        Toast.makeText(
                getApplicationContext(), "Valores Atualizados!",Toast.LENGTH_LONG
        ).show();
    }

    //transicoes a partir da tela do sensor de turbidez
    public void irTelaTemperatura_TB(View view)
    {
        Intent intent = new Intent(Sensor03.this, nova_perfilGeral.class);
        startActivity(intent);
    }

    public void irTelaPH_TB(View view)
    {
        Intent intent = new Intent(Sensor03.this, Sensor02.class);
        startActivity(intent);
    }
    public void irTelaTDS(View view)
    {
        Intent intent = new Intent(Sensor03.this, Sensor04.class);
        startActivity(intent);
    }
    public void irTelaAdicionaSensorTurb(View view)
    {
        Intent intent = new Intent(Sensor03.this, Adicionar_Sensor.class);
        startActivity(intent);
    }
}