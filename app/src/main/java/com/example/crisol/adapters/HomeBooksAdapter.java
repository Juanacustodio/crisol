package com.example.crisol.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crisol.R;
import com.example.crisol.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeBooksAdapter extends RecyclerView.Adapter<HomeBooksAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Book> lista;

    public HomeBooksAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    @NonNull
    @Override
    public HomeBooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // View view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_book, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBooksAdapter.ViewHolder holder, int position) {
        Book item = lista.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvAuthor.setText(item.getAuthorId());
        // Utilizando la librería Picasso
        Picasso.get().load(item.getImage()).into(holder.ivImage);
    }

    public void agregarelementos(ArrayList<Book> data){
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle, tvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
        }
    }
}
