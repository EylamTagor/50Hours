package com.fiftyhours.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fiftyhours.LoginActivity;
import com.fiftyhours.R;
import com.fiftyhours.util.ImageRotator;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private CircleImageView pfp;
    private TextView name, id;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private ImageRotator imageRotator;
    private Button signOutButton, resetProgressButton;
    private GoogleSignInClient mGoogleSignInClient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();

        auth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        pfp = root.findViewById(R.id.pfp);
        name = root.findViewById(R.id.name);
        id = root.findViewById(R.id.user_id);
        imageRotator = new ImageRotator(getActivity());

        db = FirebaseFirestore.getInstance();
        db.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("pfp pfp pfp", auth.getCurrentUser().getPhotoUrl().toString());
                Picasso.get().load(auth.getCurrentUser().getPhotoUrl()).into(pfp);
            }
        });

        name.setText(auth.getCurrentUser().getDisplayName());
        id.setText("UID: " + auth.getCurrentUser().getUid());

        signOutButton = root.findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        resetProgressButton = root.findViewById(R.id.reset_button);
        resetProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetProgress();
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    public void signOut() {
        auth.signOut();
        mGoogleSignInClient.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    private void resetProgress() {
        ArrayList<Integer> day = new ArrayList<>(), night = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            day.add(0);
            night.add(0);
        }
        db.collection("users").document(auth.getCurrentUser().getUid()).update("day", day, "night", night).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(getActivity(), "Progress reset!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Progress could not reset.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}