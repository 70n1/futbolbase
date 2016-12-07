package com.el70n1.futbolbase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

/**
 * Created by el70n on 06/12/2016.
 */

public class ClasificacionFragment extends Fragment {
    private static Clasificacion clasificacion;
    public static ContentAdapter adapter;
    public static RequestQueue colaPeticiones;
    public static ImageLoader lectorImagenes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        clasificacion = new Clasificacion(this,"1","14","2016/2017");

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
        public NetworkImageView subir_bajar;
        public NetworkImageView escudo_equipo;
        public TextView posicion;
        public TextView nombre_equipo;
        public TextView puntos;
        public TextView jugados;
        public TextView ganados;
        public TextView empatados;
        public TextView perdidos;
        public TextView goles_favor;
        public TextView goles_contra;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.clasificacion_item, parent, false));
            subir_bajar = (NetworkImageView) itemView.findViewById(R.id.subir_bajar);
            escudo_equipo = (NetworkImageView) itemView.findViewById(R.id.escudo_equipo);
            posicion = (TextView) itemView.findViewById(R.id.posicion);
            nombre_equipo = (TextView) itemView.findViewById(R.id.nombre_equipo);
            puntos = (TextView) itemView.findViewById(R.id.puntos);
            jugados = (TextView) itemView.findViewById(R.id.jugados);
            ganados = (TextView) itemView.findViewById(R.id.ganados);
            empatados = (TextView) itemView.findViewById(R.id.empatados);
            perdidos = (TextView) itemView.findViewById(R.id.perdidos);
            goles_favor = (TextView) itemView.findViewById(R.id.goles_favor);
            goles_contra = (TextView) itemView.findViewById(R.id.goles_contra);
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

    public static class ViewHolderHeader extends RecyclerView.ViewHolder {


        public ViewHolderHeader(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.clasificacion_header, parent, false));

        }
    }
    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;

        public ContentAdapter(Context context) {

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == TYPE_ITEM) {
                //inflate your layout and pass it to view holder
                return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
            } else if (viewType == TYPE_HEADER) {
                //inflate your layout and pass it to view holder
                return new ViewHolderHeader(LayoutInflater.from(parent.getContext()), parent);
            }
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ViewHolder) {
                ((ViewHolder)holder).subir_bajar.setImageUrl(getItem(position).subir_bajar, lectorImagenes);
                ((ViewHolder)holder).escudo_equipo.setImageUrl(getItem(position).escudo_equipo, lectorImagenes);
                ((ViewHolder)holder).posicion.setText(getItem(position).posicion);
                ((ViewHolder)holder).nombre_equipo.setText(getItem(position).nombre_equipo);
                ((ViewHolder)holder).puntos.setText(getItem(position).puntos);
                ((ViewHolder)holder).jugados.setText(getItem(position).jugados);
                ((ViewHolder)holder).ganados.setText(getItem(position).ganados);
                ((ViewHolder)holder).empatados.setText(getItem(position).empatados);
                ((ViewHolder)holder).perdidos.setText(getItem(position).perdidos);
                ((ViewHolder)holder).goles_favor.setText(getItem(position).goles_favor);
                ((ViewHolder)holder).goles_contra.setText(getItem(position).goles_contra);
            } else {

            }
        }

        @Override
        public int getItemCount() {
            return clasificacion.Lenght() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (isPositionHeader(position))
                return TYPE_HEADER;

            return TYPE_ITEM;
        }
        private boolean isPositionHeader(int position) {
            return position == 0;
        }

        private ClasificacionItem getItem(int position) {
            return clasificacion.item(position - 1);
        }
    }
}
