package com.example.dowhile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class Marcacao_activity extends AppCompatActivity implements LocationListener{

    private ViewHolder mViewHolder = new ViewHolder();
    private Handler handler = new Handler();
    private Runnable runnable;
    private boolean runnableStopped = false;

    LocationManager locationManager;
    TextView textView_localizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_marcacao);

        textView_localizacao = findViewById(R.id.tex_localizacao);
        mViewHolder.relogio= findViewById(R.id.relogio);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        buscarInformacoesGPS();

    }

    @Override
    protected void onResume() {
        super.onResume();
        runnableStopped = false;
        AtualizarHora();
    }

    @Override
    protected void onStop() {
        super.onStop();
        runnableStopped = true;
    }

    private void AtualizarHora() {

        runnable = new Runnable() {
            @Override
            public void run() {
                if (runnableStopped)
                    return;

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                String horasMinutosFormatado = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE));

                mViewHolder.relogio.setText(horasMinutosFormatado);

                long agora = SystemClock.uptimeMillis();
                long proximo = agora + (1000 - (agora % 1000));

                handler.postAtTime(runnable, proximo);
            }
        };
        runnable.run();
    }


    public void buscarInformacoesGPS() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Marcacao_activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(Marcacao_activity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            ActivityCompat.requestPermissions(Marcacao_activity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
            return;
        }
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5000, (LocationListener) Marcacao_activity.this);
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(Marcacao_activity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            textView_localizacao.setText(address);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static class ViewHolder {
        TextView relogio;
        TextView tv_Segundos;
        CheckBox cb_nivelBateria;
        TextView tv_nivelBateria;
        ImageView iv_preferencias;
        ImageView iv_sair;
        LinearLayout ll_menu;
    }
}
