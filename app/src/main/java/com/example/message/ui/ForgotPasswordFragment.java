package com.example.message.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.message.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordFragment extends Fragment {

    EditText mEmail;
    Button mResetPassword;
    ProgressBar mProgressBar;
    FirebaseAuth mFirebaseAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmail = view.findViewById(R.id.edt_email);
        mResetPassword = view.findViewById(R.id.btn_reset_password);
        mResetPassword.setOnClickListener(v -> {
            resetPassword(mEmail.getText().toString());
        });
        mProgressBar = view.findViewById(R.id.progress_bar);
    }

    public void resetPassword(String email){
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(0, true);
        mFirebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mProgressBar.setVisibility(View.GONE);
                        mProgressBar.setProgress(0, false);
                        if(task.isSuccessful()){
                            Toast.makeText(requireContext(),
                                    "Check your email " + email + " to change your password", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(requireContext(),
                                    task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}