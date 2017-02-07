package com.example.a46453895j.bicing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 46453895j on 31/01/17.
 */

public class Estaciones_api {

    private static String url = "http://wservice.viabicing.cat/v2/stations";

    public static ArrayList<Estaciones> obtenerEstaciones(){
        ArrayList<Estaciones> lista = new ArrayList<>();
        try{
            String JsonResponse = HttpUtils.get(url);
            JSONObject json = new JSONObject(JsonResponse);
            JSONArray jsonest = json.getJSONArray("stations");
            for (int i = 0; i < jsonest.length(); i++) {
                JSONObject object = jsonest.getJSONObject(i);
                int  id=object.getInt("id");
                String type=object.getString("type");
                double latitude=object.getDouble("latitude");
                double longitude=object.getDouble("longitude");
                String streetName=object.getString("streetName");
                String  streetNumber=object.getString("streetNumber");
                int altitude=object.getInt("altitude");
                int slots=object.getInt("slots");
                int bikes=object.getInt("bikes");
                String cercanos=object.getString("nearbyStations");
                String status=object.getString("status");
                Estaciones estaciones=new Estaciones(id,type,latitude,longitude,streetName,streetNumber,slots,altitude,bikes,cercanos,status);
                lista.add(estaciones);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;

    }
}
