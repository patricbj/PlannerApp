package no.hiof.patricbj.plannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View navigationHeader = navigationView.getHeaderView(0);
        Menu navigationMenu = navigationView.getMenu();
        FloatingActionButton addEventButton = findViewById(R.id.addEventButton);
        TextView username = navigationHeader.findViewById(R.id.current_username);
        TextView email = navigationHeader.findViewById(R.id.current_email);

        MenuItem signoutBtn = navigationMenu.findItem(R.id.signoutBtn);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(navigationView, navController);

        mAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build());

                // Create and launch sign-in intent
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN);
            }
        };

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            username.setText(currentUser.getDisplayName());
            email.setText(currentUser.getEmail());
        }

        signoutBtn.setOnMenuItemClickListener(item -> {
            if (item == signoutBtn) {
                mAuth.signOut();
            } else {
                return false;
            }
            return true;
        });

        addEventButton.setOnClickListener(view -> {
            Intent createEventIntent = new Intent(MainActivity.this, CreateEventActivity.class);
            startActivity(createEventIntent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != RC_SIGN_IN) {
            return;
        }

        if (resultCode == RESULT_OK) {
            // Successfully signed in
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                Toast.makeText(getBaseContext(), "Welcome, " + currentUser.getDisplayName(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "Sign in cancelled, you need to log in", Toast.LENGTH_LONG).show();
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(authStateListener);
    }
}