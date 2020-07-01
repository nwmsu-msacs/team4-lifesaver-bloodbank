package com.example.lifesaverbloodbank.ui.contactus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.lifesaverbloodbank.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ContactUsFragment extends Fragment {

    private ContactUsViewModel contactUsViewModel;
    private Button helpBTN;
    FirebaseFirestore firebaseFirestore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactUsViewModel =
                ViewModelProviders.of(this).get(ContactUsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_contactus, container, false);
        helpBTN = root.findViewById(R.id.helpBTN);
        firebaseFirestore = FirebaseFirestore.getInstance();
        helpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText queryET=root.findViewById(R.id.queryET);
                String query=queryET.getText().toString();
                if(!query.isEmpty()){
                    Toast.makeText(getActivity(), "Your queries are submitted successfully", Toast.LENGTH_LONG).show();
                    DocumentReference documentReference = firebaseFirestore.collection("Queries List").document();
                    Map<String, Object> user = new HashMap<>();
                    user.put("Query", query);
                    queryET.setText("");
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {



                        }
                    });

                }else{
                    Toast.makeText(getActivity(), "Please enter the query which needs to be addressed", Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }
}