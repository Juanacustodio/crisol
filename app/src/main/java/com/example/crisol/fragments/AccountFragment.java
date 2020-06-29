package com.example.crisol.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crisol.R;
import com.example.crisol.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AccountFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    //user fields
    TextView name, email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_account, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        LinearLayout guestview = root.findViewById(R.id.guestview);
        LinearLayout userview = root.findViewById(R.id.userview);
        if (currentUser != null) {
            guestview.setVisibility(LinearLayout.GONE);

            email = root.findViewById(R.id.email);
            name = root.findViewById(R.id.name);

            buildUser(currentUser.getUid());
        }
        else {
            userview.setVisibility(LinearLayout.GONE);
        }


        return root;
    }

    private void buildUser(String id){
        db.collection("user")
            .whereEqualTo("id", id)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // user
                            name.setText(document.getString("name")+ " " + document.getString("lastname"));
                            email.setText(document.getString("email"));
                        }
                    } else {
                        //
                    }
                }
            });
    }
}
