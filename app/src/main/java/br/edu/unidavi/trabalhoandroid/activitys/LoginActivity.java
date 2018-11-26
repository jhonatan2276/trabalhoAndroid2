package br.edu.unidavi.trabalhoandroid.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.tooltip.Tooltip;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.edu.unidavi.trabalhoandroid.R;
import br.edu.unidavi.trabalhoandroid.dados.Session;
import br.edu.unidavi.trabalhoandroid.eventbus.Mensagem;
import br.edu.unidavi.trabalhoandroid.eventbus.Usuario;
import br.edu.unidavi.trabalhoandroid.web.GerenciadorWebUsuario;

public class LoginActivity extends AppCompatActivity {
    private String nome;
    private String senha;
    private EditText edtNome;
    private EditText edtSenha;
    private Switch swtMemorizaUsuario;
    private ProgressDialog msgCarregando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtNome = findViewById(R.id.log_edtNome);
        edtSenha = findViewById(R.id.log_edtSenha);
        swtMemorizaUsuario = findViewById(R.id.log_swtMemorizaUsuario);
        Button btnEntrar = findViewById(R.id.log_btnEntrar);
        Button btnInfo = findViewById(R.id.log_btnInfo);

        //Resgata (do SharedPreferences - se houver) dados salvos do Usuário
        Session session = new Session(this);
        edtNome.setText(session.retornaUsuarioSession());

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logar();
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verInformacoes();
            }
        });
    }

    public void logar () {
        if (edtNome.getText().toString().trim().equals("")) {
            mostraTooltip(edtNome, "Informe o Usuário", "#FFFFFF", "#3F51B5");
        } else
        if (edtSenha.getText().toString().trim().equals("")) {
            mostraTooltip(edtSenha, "Informe a Senha", "#FFFFFF", "#3F51B5");
        } else {

            nome = edtNome.getText().toString();
            senha = edtSenha.getText().toString();

            carregando();

            GerenciadorWebUsuario gerenciadorWebUsuario = new GerenciadorWebUsuario(this, nome, senha);
            gerenciadorWebUsuario.execute();
        }
    }

    public void verInformacoes () {
        Intent informacoes = new Intent(this, InformacoesActivity.class);
        startActivity(informacoes);
    }

    public void mostraTooltip (TextView campo, String texto, String corTexto, String corFundo) {
        int toolTipCorTexto = Color.parseColor(corTexto);
        int tooltipCorFundo = Color.parseColor(corFundo);
        float borda = 50;

        Tooltip tooltip = new Tooltip.Builder(campo)
                .setText(texto)
                .setCornerRadius(borda)
                .setTextColor(toolTipCorTexto)
                .setBackgroundColor(tooltipCorFundo)
                .setDismissOnClick(true)
                .setCancelable(true)
                .show();
    }

    public void carregando (){
        msgCarregando = new ProgressDialog(this);
        msgCarregando.setCancelable(false);
        msgCarregando.setMessage("Aguarde Carregando...");
        msgCarregando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        msgCarregando.setProgress(0);
        msgCarregando.show();
    }

    public void fimCarregando(){
        if (msgCarregando != null && msgCarregando.isShowing()) {
            msgCarregando.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(Usuario usuario){
        //Salva no SharedPreferences) os dados digitados no campo Nome
        edtNome = findViewById(R.id.log_edtNome);

        Session session = new Session(this);
        if (swtMemorizaUsuario.isChecked()) {
            session.salvaUsuarioSession(edtNome.getText().toString());
        } else {
            session.salvaUsuarioSession("");
        }

        //Evento criado para fins de teste de EventBus
        Mensagem mensagem = new Mensagem();
        mensagem.setTexto01(usuario.getNome1());
        mensagem.setTexto02(usuario.getNome2());
        EventBus.getDefault().postSticky(mensagem);

        fimCarregando();

        Intent principal = new Intent(this, PrincipalActivity.class);
        startActivity(principal);
        finish();

        Log.d("EVENTO ======= ", "RECEBENDO USUÁRIO");
    }

    @Subscribe
    public void onEvent(Error error){
        fimCarregando();
        mostraTooltip(edtSenha, "Problema de Conexão "+error.getMessage(), "#FFFFFF", "#FF0000");

        Log.d("ERRO =========== ", error.getMessage());
    }
}