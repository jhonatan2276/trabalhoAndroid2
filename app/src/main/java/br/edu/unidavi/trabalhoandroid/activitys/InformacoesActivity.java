package br.edu.unidavi.trabalhoandroid.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.unidavi.trabalhoandroid.R;

public class InformacoesActivity extends AppCompatActivity {

    TextView titulo, item01, item02, item03, item04, item05, item06;
    ImageView icone01, icone02, icone03, icone04, icone05, icone06;
    Button btnFecar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes);

        titulo = findViewById(R.id.trab_txtTitulo);
        item01 = findViewById(R.id.trab_txtItem01);
        item02 = findViewById(R.id.trab_txtItem02);
        item03 = findViewById(R.id.trab_txtItem03);
        item04 = findViewById(R.id.trab_txtItem04);
        item05 = findViewById(R.id.trab_txtItem05);
        item06 = findViewById(R.id.trab_txtItem06);
        icone01 = findViewById(R.id.trab_icone01);
        icone02 = findViewById(R.id.trab_icone02);
        icone03 = findViewById(R.id.trab_icone03);
        icone04 = findViewById(R.id.trab_icone04);
        icone05 = findViewById(R.id.trab_icone05);
        icone06 = findViewById(R.id.trab_icone06);
        btnFecar = findViewById(R.id.trab_btnFechar);

        btnFecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}