package br.edu.unidavi.trabalhoandroid.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import br.edu.unidavi.trabalhoandroid.R;
import br.edu.unidavi.trabalhoandroid.dados.LocalDatabaseController;
import br.edu.unidavi.trabalhoandroid.eventbus.Carro;
import br.edu.unidavi.trabalhoandroid.eventbus.Favorito;

public class CarroDetalheActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Context context;
    private TextView marca;
    private TextView modelo;
    private TextView ano;
    private TextView preco;
    private TextView observacoes;
    private Button addFavoritos;
    private ImageView imagem;

    private int idServer;
    private String marcaTxt;
    private String modeloTxt;
    private String anoTxt;
    private String imagemTxt;
    private String precoTxt;
    private String observacoesTxt;
    private String latitudeTxt;
    private String longitudeTxt;

    private ArrayList<Favorito> favoritoArrayList;
    private LocalDatabaseController db;

    private GoogleMap mMap;
    private boolean enableMyLocation = false;

    private LatLng locCarro = new LatLng(-27.2104016, -49.6466032);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro_detalhe);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        db = new LocalDatabaseController(this);

        Button det_btnVoltar;

        marca = findViewById(R.id.det_txtMarca);
        modelo = findViewById(R.id.det_txtModelo);
        ano = findViewById(R.id.det_txtAno);
        preco = findViewById(R.id.det_txtPreco);
        observacoes = findViewById(R.id.det_txtObservacoesDetalhe);
        imagem = findViewById(R.id.det_imagemIcone);
        addFavoritos = findViewById(R.id.det_btnAddFavoritos);
        det_btnVoltar = findViewById(R.id.det_btnVoltar);

        det_btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Busca todos os favoritos no SQLite cada vez que a tela é aberta
        favoritoArrayList = db.getAllFavs();

        addFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.inserirFavorito(idServer, marcaTxt, modeloTxt, anoTxt, imagemTxt, precoTxt, observacoesTxt, latitudeTxt, longitudeTxt);
                addFavoritos.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Adicionado a Lista de Favorito", Toast.LENGTH_SHORT).show();
            }
        });
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

    @Subscribe (sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent (Carro carro) {
        idServer = carro.getIdServer();
        marcaTxt = carro.getMarca();
        modeloTxt = carro.getModelo();
        anoTxt = carro.getAno();
        imagemTxt = carro.getImagem();
        precoTxt = carro.getPreco();
        observacoesTxt = carro.getObservacoes();
        latitudeTxt = carro.getLatitude();
        longitudeTxt = carro.getLongitude();

        locCarro = new LatLng(Double.parseDouble(latitudeTxt), Double.parseDouble(longitudeTxt));

        marca.setText(carro.getMarca());
        modelo.setText(carro.getModelo());

        ano.setText(carro.getAno());
        preco.setText(carro.getPreco());
        observacoes.setText(carro.getObservacoes());

        Picasso.with(context)
                .load(carro.getImagem())
                .into(imagem);

        //Se um carro já está na lista de favoritos oculta o botão "Add aos Favoritos"
        //Essa função foi colocada aqui para dar tempo desta classe receber os dados que vem da no EventBus
        for (int i = 0; i < favoritoArrayList.size(); i++) {
            if (favoritoArrayList.get(i).getIdServer() == idServer) {
                addFavoritos.setVisibility(View.GONE);
            }
        }
    }
    private List<Marker> markers = new ArrayList<>();

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                this, R.raw.mapstyle
        ));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(enableMyLocation);
        mMap.setMyLocationEnabled(enableMyLocation);

        MarkerOptions marker = new MarkerOptions()
                .draggable(true)
                .position(locCarro)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("Remover");
        markers.add(mMap.addMarker(marker));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                locCarro, 17f));


        mMap.addMarker(new MarkerOptions()
                .draggable(true)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_buiding))
                .position(locCarro));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation = true;
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Por favor, sua localização é necessária",
                        Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mMap != null) {
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                    mMap.setMyLocationEnabled(true);
                } else {
                    enableMyLocation = true;
                }
            }
        }
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}