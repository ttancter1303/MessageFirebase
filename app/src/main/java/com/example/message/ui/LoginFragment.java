package com.example.message.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.message.R;
import com.example.message.data.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    TextView mCreateAccount;
    TextView mForgotPassword;
    EditText mEmail;
    EditText mPassword;
    Button mLogin;
    ProgressBar mProgressBar;
    FirebaseAuth mFirebaseAuth;
    NavController mController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);

        mCreateAccount = view.findViewById(R.id.txt_create_account);
        mCreateAccount.setOnClickListener(v -> {
            mController.navigate(R.id.signUpFragment);
        });

        mForgotPassword = view.findViewById(R.id.txt_forget_password);
        mForgotPassword.setOnClickListener(v -> {
            mController.navigate(R.id.forgotPasswordFragment);
        });

        mEmail = view.findViewById(R.id.edt_email);
        mPassword = view.findViewById(R.id.edt_password);
        mLogin = view.findViewById(R.id.btn_login);
        mLogin.setOnClickListener(v -> {
            login(mEmail.getText().toString(), mPassword.getText().toString());
        });

        mProgressBar = view.findViewById(R.id.progress_bar);
    }

    public void login(String email, String password){
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(0, true);
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressBar.setVisibility(View.GONE);
                        mProgressBar.setProgress(0, false);
                        if(task.isSuccessful()){
                            mController.popBackStack();
                            mController.navigate(R.id.userListFragment);
                        } else {
                            Toast.makeText(requireContext(),
                                    task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}