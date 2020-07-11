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

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        ImageView ivImage = findViewById(R.id.ivImage);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvAuthor = findViewById(R.id.tvAuthor);

        if(getIntent().hasExtra("book")){
            final Book book = getIntent().getParcelableExtra("book");
            // Utilizando la librería Picasso
            Picasso.get().load(book.getImage()).into(ivImage);
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthorId());

            // btnSeeBook
            Button btnSeeBook = findViewById(R.id.btnSeeBook);
            btnSeeBook.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intConfirm = new Intent(ConfirmActivity.this, BookDetailActivity.class);
                    intConfirm.putExtra("book", book);
                    startActivity(intConfirm);
                }
            });

            // btnGoHome
            Button btnGoHome = findViewById(R.id.btnGoHome);
            btnGoHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ConfirmActivity.this, MainActivity.class));
                }
            });
        }
    }
}
