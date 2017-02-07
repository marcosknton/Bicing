package com.example.a46453895j.bicing;

import android.content.Context;
import android.os.AsyncTask;

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

/**
 * Created by MICHUS on 04/01/2017.
 */

public class RefreshDataTask extends AsyncTask<Void,Void,ArrayList<Estaciones>> {

    Context context;
    MapView map;
    private MyLocationNewOverlay myLocationOverlay;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private CompassOverlay mCompassOverlay;
    private IMapController mapController;





    public RefreshDataTask(Context context, MapView map){
       this.context=context;
       this.map = map;

   }

    @Override
    protected ArrayList<Estaciones> doInBackground(Void... voids) {
        ArrayList<Estaciones> Aestaciones=Estaciones_api.obtenerEstaciones();
        return Aestaciones;
    }


    @Override
    protected void onPostExecute(ArrayList<Estaciones> estaciones) {
        super.onPostExecute(estaciones);
        mapController = map.getController();
        initializeMap();
        //setOverlays();

        for (int i = 0; i <estaciones.size() ; i++) {
            Double latitud=estaciones.get(i).getLatitude();
            Double longitud=estaciones.get(i).getLongitude();
            String calle=estaciones.get(i).getStreetName();
            GeoPoint estationpoint = new GeoPoint(latitud, longitud);
            Marker startMaker = new Marker(map);
            startMaker.setPosition(estationpoint);
            startMaker.setTitle(calle);
            map.getOverlays().add(startMaker);
        }
        GeoPoint startPoint = new GeoPoint(41.38, 2.16);
        setZoom(startPoint);
        //setOverlays();
        map.invalidate();
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
        //final DisplayMetrics dm=getResources().getDisplayMetrics();
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
        //mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mCompassOverlay= new CompassOverlay(this.context,map);
        mCompassOverlay.enableCompass();

        map.getOverlays().add(myLocationOverlay);
        map.getOverlays().add(this.mMinimapOverlay);
        map.getOverlays().add(this.mScaleBarOverlay);
        map.getOverlays().add(this.mCompassOverlay);
    }


}
