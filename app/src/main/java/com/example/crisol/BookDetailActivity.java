package com.example.crisol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crisol.model.Book;
import com.squareup.picasso.Picasso;

public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ImageView ivImage = findViewById(R.id.ivImage);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvAuthor = findViewById(R.id.tvAuthor);
        Button btnBuy = findViewById(R.id.btnBuy);
        TextView tvSummary = findViewById(R.id.tvSummary);

        if(getIntent().hasExtra("book")){
            final Book book = getIntent().getParcelableExtra("book");
            // Utilizando la librería Picasso
            Picasso.get().load(book.getImage()).into(ivImage);
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthorId());
            btnBuy.setText("Comprar por " + book.getPrice());
            tvSummary.setText(book.getSummary());

            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intPay = new Intent(BookDetailActivity.this, PayActivity.class);
                    intPay.putExtra("book", book);
                    startActivity(intPay);
                }
            });
        }
    }
}
