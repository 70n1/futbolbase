package com.el70n1.futbolbase;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by el70n on 06/12/2016.
 */

public class Resultados {
    private ArrayList<Resultado> m_resultados = new ArrayList<Resultado>();
    public String tmp = "";
    public String cmp = "";
    public String jor = "";
    ResultadosFragment resultadosFragment;

    public Resultados(ResultadosFragment resultadosFragment, String cmp, String jor, String tmp) {
        this.resultadosFragment = resultadosFragment;
        this.tmp = tmp;
        this.cmp = cmp;
        this.jor = jor;
        new ActualizarDatos().execute();
    }


    public Iterator<Resultado> getIter() {
        return m_resultados.iterator();
    }

    public Resultado resultado (int posicion){
        Resultado salida = null;
        if (m_resultados.size()>posicion) {
            salida = m_resultados.get(posicion);
        }
        return salida;
    }

    public int Lenght() {
        int salida = 0;
        salida = m_resultados.size();

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
            return conexion.getResultados(cmp, jor, tmp);
        }

        @Override
        protected void onPostExecute(JSONArray jsonarray) {
            m_resultados.clear();
            if (jsonarray != null && jsonarray.length()>0) {
                for (int i = 0; i < jsonarray.length(); i++) {  // **line 2**
                    try {
                        JSONObject json = jsonarray.getJSONObject(i);
                        Resultado resultado = new Resultado();
                        resultado.idLocal = json.getString("idLocal");
                        resultado.idClubLocal = json.getString("idClubLocal");
                        resultado.nomLocal = json.getString("nomLocal");
                        resultado.resulLocal = json.getString("resulLocal");
                        resultado.escudoLocal = conexion.base_url + json.getString("escudoLocal");
                        resultado.idVisitante = json.getString("idVisitante");
                        resultado.idClubVisitante = json.getString("idClubVisitante");
                        resultado.nomVisitante = json.getString("nomVisitante");
                        resultado.resulVisitante = json.getString("resulVisitante");
                        resultado.escudoVisitante = conexion.base_url + json.getString("escudoVisitante");
                        resultado.codInfo = json.getString("codInfo");
                        resultado.hayActa = json.getBoolean("hayActa");
                        resultado.fecha = json.getString("fecha");
                        resultado.hora = json.getString("hora");
                        resultado.estadoPartido = json.getString("estadoPartido");
                        resultado.abreviaturaEstado = json.getString("abreviaturaEstado");
                        m_resultados.add(resultado);
                    } catch (Exception e){
                        Log.e("Resultados", "Resultados: Error al sacar del Array JSON los datos " + e.toString());
                    }
                }
            }
            resultadosFragment.adapter.notifyDataSetChanged();
        }
    }
}


