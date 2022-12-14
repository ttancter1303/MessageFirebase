package com.example.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavGraph navGraph = navHostFragment.getNavController().getNavInflater().inflate(R.navigation.main_nav);
        if(mFirebaseUser != null){
            navGraph.setStartDestination(R.id.userListFragment);
        } else {
            navGraph.setStartDestination(R.id.loginFragment);
        }
        navHostFragment.getNavController().setGraph(navGraph);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}