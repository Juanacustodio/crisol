package com.example.crisol.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crisol.R;
import com.example.crisol.adapters.HomeBooksAdapter;
import com.example.crisol.model.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView rvRecents, rvDiscover;
    private ArrayList<Book> recentsList, discoverList;
    private HomeBooksAdapter recentsAdapter, discoverAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        rvRecents = root.findViewById(R.id.rvRecents);
        rvRecents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recentsAdapter = new HomeBooksAdapter(getContext());
        rvRecents.setAdapter(recentsAdapter);
        recentsList = new ArrayList<>();


        rvDiscover = root.findViewById(R.id.rvDiscover);
        rvDiscover.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        discoverAdapter = new HomeBooksAdapter(getContext());
        rvDiscover.setAdapter(discoverAdapter);
        discoverList = new ArrayList<>();
        getBooks();

        return root;
    }

    private void getBooks()
    {
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("book")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                count++;
                                if (count <= 5) {
                                    recentsList.add(new Book(
                                            document.getId(),
                                            document.getString("title"),
                                            document.getString("summary"),
                                            document.getString("authorId"),
                                            document.getString("image"),
                                            document.getDouble("price")
                                    ));
                                }
                                else {
                                    discoverList.add(new Book(
                                            document.getId(),
                                            document.getString("title"),
                                            document.getString("summary"),
                                            document.getString("authorId"),
                                            document.getString("image"),
                                            document.getDouble("price")
                                    ));
                                }
                            }
                            recentsAdapter.agregarelementos(recentsList);
                            discoverAdapter.agregarelementos(discoverList);
                        } else {
                            System.out.println("ERROR LIBROS"+ task.getException());
                        }
                    }
                });
    }
}
