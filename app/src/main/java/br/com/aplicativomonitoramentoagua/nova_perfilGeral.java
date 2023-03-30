package br.com.aplicativomonitoramentoagua;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.eazegraph.lib.charts.ValueLineChart;

public class nova_perfilGeral extends AppCompatActivity{

    private Button EditandoSensor;
    private Button Sensor01;
    private Button Sensor02;
    private Button Sensor03;
    private Button Sensor04;
    private FloatingActionButton adicionarSensor;

    private Toolbar t;
    private String previsaoTemperatura;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference valorTeste = FirebaseDatabase.getInstance().getReference();
    private TextView resultadoSensor;
    private DatabaseReference ref2;
    private BottomNavigationView navigationView;

    private ValueLineChart valueLineChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_perfil_geral);
        criaBotoes();

        //pega o valor do nó sensores do firebase e exibe na tela

        ref2 = referencia.child("PAI").child("LastRecord").child("Temperatura");
        ref2.addListenerForSingleValueEvent(
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

        t = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toollbarPerfil01);
        setSupportActionBar(t);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    //pega ids de botoes, layouts e textviews
    public void criaBotoes()
    {
        adicionarSensor = (FloatingActionButton) findViewById(R.id.adicionarNovoSensorBt);
        EditandoSensor = (Button) findViewById(R.id.EditSensor);
        resultadoSensor = (TextView) findViewById(R.id.resultadoSensor01);
        Sensor02 = (Button) findViewById(R.id.PH);
        Sensor03 = (Button) findViewById(R.id.Turbidez);
        Sensor04 = (Button) findViewById(R.id.SensorTDS);

    }


    //metodo para pegar o valor mais recente da temperatura
    public void atualizar(View view) {

        ref2.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String resultado = snapshot.getValue().toString();
                        resultadoSensor.setText(resultado);
                        toastAtualizar(view);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Erro!");
                    }
                }
        );
    }
    //mostra mensagem dizendo que os valores foram atualizados
    public void toastAtualizar(View view)
    {
        Toast.makeText(
                getApplicationContext(), "Valores Atualizados!",Toast.LENGTH_LONG
        ).show();
    }

    // vai à tela do Sensor02 (PH)
    public void irTelaSensor02(View view)
    {
        Intent intent = new Intent(nova_perfilGeral.this, Sensor02.class);
        startActivity(intent);
    }
    public void irTelaSensor03(View view)
    {
        Intent intent = new Intent(nova_perfilGeral.this, Sensor03.class);
        startActivity(intent);
    }

    public void irTelaSensor04(View view)
    {
        Intent intent = new Intent(nova_perfilGeral.this, Sensor04.class);
        startActivity(intent);
    }
    public void irTelaAdicionarNovoSensor(View view)
    {
        Intent intent = new Intent(nova_perfilGeral.this, Adicionar_Sensor.class);
        startActivity(intent);
    }


}