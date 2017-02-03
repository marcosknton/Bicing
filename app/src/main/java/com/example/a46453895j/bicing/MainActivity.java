package com.example.a46453895j.bicing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MapView map;
    private MyLocationNewOverlay myLocationOverlay;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private CompassOverlay mCompassOverlay;
    private IMapController mapController;
    public ArrayList <Estaciones> Aestaciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map=(MapView) findViewById(R.id.map);
        initializeMap();


        //cuando cargue el mapa le indicamos las coordenadas que queremos que nos muestre inicialmente
        //GeoPoint startPoint = new GeoPoint(41.38, 2.16);
        mapController = map.getController();
        //setZoom(startPoint);

        setOverlays();
        RefreshDataTask task=new RefreshDataTask(this);
        task.execute();
        Aestaciones=(ArrayList<Estaciones>) getIntent().getSerializableExtra("parametro");

        Log.d(String.valueOf(Aestaciones.size()), "------------------------------");

        for (int i = 0; i <Aestaciones.size() ; i++) {
            Double latitud=Aestaciones.get(i).getLatitude();
            Double longitud=Aestaciones.get(i).getLongitude();
            String calle=Aestaciones.get(i).getStreetName();
            GeoPoint estationpoint = new GeoPoint(latitud, longitud);
            Marker startMaker = new Marker(map);
            startMaker.setPosition(estationpoint);
            startMaker.setTitle(calle);
            map.getOverlays().add(startMaker);
        }
        map.invalidate();
        /*
        Marker startMaker = new Marker(map);
        startMaker.setPosition(startPoint);
        startMaker.setTitle("YO");
        startMaker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(startMaker);
        */


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeMap() {
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setTilesScaledToDpi(true);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
    }
    private void setZoom(GeoPoint startPoint) {
        //  Setteamos el zoom al mismo nivel y ajustamos la posición a un geopunto
        mapController.setZoom(15);
        mapController.setCenter(startPoint);
    }


    private void setOverlays(){
        final DisplayMetrics dm=getResources().getDisplayMetrics();
        myLocationOverlay=new MyLocationNewOverlay(map);
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                mapController.animateTo(myLocationOverlay.getMyLocation());
            }
        });

        mScaleBarOverlay=new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mCompassOverlay= new CompassOverlay(this,map);
        mCompassOverlay.enableCompass();

        map.getOverlays().add(myLocationOverlay);
        map.getOverlays().add(this.mMinimapOverlay);
        map.getOverlays().add(this.mScaleBarOverlay);
        map.getOverlays().add(this.mCompassOverlay);
    }

}
