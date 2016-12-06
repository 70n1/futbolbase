package com.el70n1.futbolbase;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

/**
 * Created by AMARTIN on 05/12/2016.
 */

public class ResultadosFragment extends Fragment{

    private static Resultados resultados;
    public static ContentAdapter adapter;
    public static RequestQueue colaPeticiones;
    public static ImageLoader lectorImagenes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        resultados = new Resultados(this,"1","14","2016/2017");

         colaPeticiones = Volley.newRequestQueue(this.getContext());
         lectorImagenes = new ImageLoader(colaPeticiones, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
        });

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView escudo_local;
        public NetworkImageView escudo_visitante;
        public TextView equipo_local;
        public TextView equipo_visitante;
        public TextView partido_fecha;
        public TextView partido_hora;
        public TextView goles_local;
        public TextView goles_visitante;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.partido_lista, parent, false));
            escudo_local = (NetworkImageView) itemView.findViewById(R.id.escudo_equipo_local);
            escudo_visitante = (NetworkImageView) itemView.findViewById(R.id.escudo_equipo_visitante);
            equipo_local = (TextView) itemView.findViewById(R.id.nombre_equipo_local);
            equipo_visitante = (TextView) itemView.findViewById(R.id.nombre_equipo_visitante);
            partido_fecha = (TextView) itemView.findViewById(R.id.partido_fecha);
            partido_hora = (TextView) itemView.findViewById(R.id.partido_hora);
            goles_local = (TextView) itemView.findViewById(R.id.goles_local);
            goles_visitante = (TextView) itemView.findViewById(R.id.goles_visitante);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        //private static int LENGTH = resultados.Lenght();

        //TODO poner como una estructura o una clase
        /*private final String[] mEquiposLocales;
        private final String[] mEquiposVisitantes;
        private final String[] mGolesLocal;
        private final String[] mGolesVisitante;
        private final String[] mHoras;
        private final String[] mFechas;
        private final Drawable[] mEscudo_locales;
        private final Drawable[] mEscudo_visitantes;*/

        public ContentAdapter(Context context) {
            /*Resources resources = context.getResources();
            mEquiposLocales = resources.getStringArray(R.array.partido_equipos_local);
            mEquiposVisitantes = resources.getStringArray(R.array.partido_equipos_visitante);

            mGolesLocal = resources.getStringArray(R.array.partido_goles_local);
            mGolesVisitante = resources.getStringArray(R.array.partido_goles_visitante);

            mHoras = resources.getStringArray(R.array.partido_horas);
            mFechas = resources.getStringArray(R.array.partido_fechas);

            TypedArray a = resources.obtainTypedArray(R.array.partido_escudos_local);
            mEscudo_locales = new Drawable[a.length()];
            for (int i = 0; i < mEscudo_locales.length; i++) {
                mEscudo_locales[i] = a.getDrawable(i);
            }
            a.recycle();
            TypedArray av = resources.obtainTypedArray(R.array.partido_escudos_visitante);
            mEscudo_visitantes = new Drawable[av.length()];
            for (int i = 0; i < mEscudo_visitantes.length; i++) {
                mEscudo_visitantes[i] = av.getDrawable(i);
            }
            av.recycle();*/
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.escudo_local.setImageUrl(resultados.resultado(position).escudoLocal, lectorImagenes);
            holder.escudo_visitante.setImageUrl(resultados.resultado(position).escudoVisitante, lectorImagenes);
            holder.equipo_local.setText(resultados.resultado(position).nomLocal);
            holder.equipo_visitante.setText(resultados.resultado(position).nomVisitante);
            holder.goles_local.setText(resultados.resultado(position).resulLocal);
            holder.goles_visitante.setText(resultados.resultado(position).resulVisitante);
            holder.partido_hora.setText(resultados.resultado(position).hora);
            holder.partido_fecha.setText(resultados.resultado(position).fecha);
        }

        @Override
        public int getItemCount() {
            return resultados.Lenght();
        }
    }

}
