package com.el70n1.futbolbase;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by el70n on 06/12/2016.
 */

public class Clasificacion {
    private ArrayList<ClasificacionItem> m_clasificacion = new ArrayList<ClasificacionItem>();
    public String tmp = "";
    public String cmp = "";
    public String jor = "";
    ClasificacionFragment clasificacionFragment;

    public Clasificacion(ClasificacionFragment clasificacionFragment, String cmp, String jor, String tmp) {
        this.clasificacionFragment = clasificacionFragment;
        this.tmp = tmp;
        this.cmp = cmp;
        this.jor = jor;
        new ActualizarDatos().execute();
    }


    public Iterator<ClasificacionItem> getIter() {
        return m_clasificacion.iterator();
    }

    public ClasificacionItem item (int posicion){
        ClasificacionItem salida = null;
        if (m_clasificacion.size()>posicion) {
            salida = m_clasificacion.get(posicion);
        }
        return salida;
    }

    public int Lenght() {
        int salida = 0;
        salida = m_clasificacion.size();

        return salida;
    }

    private class ActualizarDatos extends AsyncTask<String, String, JSONArray> {
        Conexiones conexion;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(String... strings) {
            conexion = new Conexiones();
            return conexion.getClasificacion(cmp, jor, tmp);
        }

        @Override
        protected void onPostExecute(JSONArray jsonarray) {
            m_clasificacion.clear();
            if (jsonarray != null && jsonarray.length()>0) {
                for (int i = 0; i < jsonarray.length(); i++) {  // **line 2**
                    try {
                        JSONArray json = jsonarray.getJSONArray(i);
                        ClasificacionItem clasificacionItem = new ClasificacionItem();
                        clasificacionItem.posicion = json.getString(0);
                        clasificacionItem.idClub = json.getString(1);
                        clasificacionItem.nombre_equipo = json.getString(2);
                        clasificacionItem.puntos = json.getString(3);
                        clasificacionItem.jugados = json.getString(4);
                        clasificacionItem.ganados = json.getString(5);
                        clasificacionItem.empatados = json.getString(6);
                        clasificacionItem.perdidos = json.getString(7);
                        clasificacionItem.goles_favor = json.getString(8);
                        clasificacionItem.goles_contra = json.getString(9);
                        clasificacionItem.escudo_equipo = conexion.base_url + json.getString(10);
                        clasificacionItem.subir_bajar = conexion.base_url + json.getString(11);
                        m_clasificacion.add(clasificacionItem);
                    } catch (Exception e){
                        Log.e("Clasificacion", "Clasificacion: Error al sacar del Array JSON los datos " + e.toString());
                    }
                }
            }
            clasificacionFragment.adapter.notifyDataSetChanged();
        }
    }
}
