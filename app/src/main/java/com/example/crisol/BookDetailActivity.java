package com.example.crisol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crisol.model.Book;
import com.example.crisol.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookDetailActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private Book book;

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
            book = getIntent().getParcelableExtra("book");
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

        // toolbar
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        initMenu();
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.topfavorite:
                        item.setIcon(R.drawable.ic_favorite_black_24dp);
                        addFavoriteBook();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void addFavoriteBook()
    {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        if (currentUser != null) {
            db.collection("user")
                    .whereEqualTo("id", currentUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);
                                    user.setFavoriteBook(book.getId());

                                    db.collection("user").document(document.getId())
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(BookDetailActivity.this, "Marcado como favorito.", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(BookDetailActivity.this, "Error al agregar favorito.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        }
                    });
        }
        else {
            // local storage
        }
    }

    private void initMenu() {

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            db.collection("user")
                    .whereEqualTo("id", currentUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);
                                    List<String> favorites = user.getFavoriteBooks();
                                    if (!favorites.isEmpty()) {
                                        for (String fav : favorites) {
                                            if (fav.equals(book.getId())) {
                                                ActionMenuItemView menu = findViewById(R.id.topfavorite);
                                                menu.setIcon(ContextCompat.getDrawable(BookDetailActivity.this, R.drawable.ic_favorite_black_24dp));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
        else {
            // local storage
        }
    }
}
