package br.com.aplicativomonitoramentoagua;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
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


import java.util.ArrayList;
import java.util.List;

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
    private float val = 1f;
    private LineChart lineChart;

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

        //pega id
        lineChart = (LineChart) findViewById(R.id.grafTemperatura);
        /*
        LineDataSet lineDataSet = new LineDataSet(dataValues(), "Temperatura");
        ArrayList<ILineDataSet> dataSets= new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();
        */

        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(0, 30f));
        yValues.add(new Entry(1, 20f));
        yValues.add(new Entry(2, 10f));
        yValues.add(new Entry(3, 15f));
        yValues.add(new Entry(4, 21f));

        LineDataSet set1 = new LineDataSet(yValues, "Temperatura");

        set1.setFillAlpha(110);
        set1.setLineWidth(3f);
        set1.setValueTextSize(1f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);

        String[] dias = new String[]{"Seg", "Ter", "Qua", "Qui", "Sex"};

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new MyAxisFormatter(dias));
        xAxis.setGranularity(1f);
    }

   public class MyAxisFormatter extends ValueFormatter {
       public String[] mdias;

       public MyAxisFormatter(String[] dias) {
           this.mdias = dias;
       }

       @Override
       public String getFormattedValue(float value) {
           return mdias[(int) value];
       }
   }

    //valores a serem plotados
    private List<Entry> dataValues()
    {
        ArrayList<Entry> dataValue = new ArrayList<>();
        dataValue.add(new Entry(0, 15f));
        dataValue.add(new Entry(1, 10f));
        dataValue.add(new Entry(2, 28f));
        dataValue.add(new Entry(3, 20f));
        dataValue.add(new Entry(4, 12f));
        return dataValue;
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
    public void atualizar(View view)
    {

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