package com.eleven.group.myrecipiebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.eleven.group.myrecipiebook.R;
import com.eleven.group.myrecipiebook.fragment.DualPaneFragment;
import com.eleven.group.myrecipiebook.fragment.GridFragment;
import com.eleven.group.myrecipiebook.fragment.ListFragment;
import com.eleven.group.myrecipiebook.fragment.ViewPagerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Sid testing
public class MainActivity extends AppCompatActivity
        implements ListFragment.OnRecipeSelectedInterface, GridFragment.OnRecipeSelectedInterface {
    public static final String LIST_FRAGMENT = "list_fragment";
    public static final String VIEW_PAGER_FRAGMENT = "viewpager_fragment";
    Button btnRecipe, btnAddMeal, btnLogIn, btnSignOut, btnSignUp, btnCamera;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

            GridFragment savedFragment = (GridFragment) getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT);

            // Prevents the fragment from being recreated every time the activty's onCreate() method is called
            if (savedFragment == null) {
                GridFragment fragment = new GridFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.placeholder, fragment, LIST_FRAGMENT);
                fragmentTransaction.commit();
            } // if

        btnRecipe = (Button) findViewById(R.id.button);
        btnRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SearchRecipeActivity.class);
                startActivity(i);
            }
        });

        btnAddMeal = (Button) findViewById(R.id.btn_add_meal);
        btnAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddMealActivity.class);
                startActivity(i);
            }
        });

        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignUp.class);
                startActivity(i);
            }
        });



        btnLogIn = (Button) findViewById(R.id.btn_log_in);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });

        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(auth.getCurrentUser() != null){
                    auth.signOut();
                    btnSignOut.setVisibility(View.GONE);
                    btnLogIn.setVisibility(View.VISIBLE);
                }
            }
        });

        btnCamera = (Button) findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Camera2Activity.class);
                startActivity(i);
            }
        });
    } // onCreate()

    @Override
    protected void onResume() {
        super.onResume();
        if(auth.getCurrentUser() != null){
            btnSignOut.setVisibility(View.VISIBLE);
            btnLogIn.setVisibility(View.GONE);
        }
        else {
            btnSignOut.setVisibility(View.GONE);
            btnLogIn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onListRecipeSelected(int index) {
        ViewPagerFragment fragment = new ViewPagerFragment();

        // Store the index in the bundle to pass it to the next fragment instead of using a constructor
        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerFragment.KEY_RECIPE_INDEX, index);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, fragment, VIEW_PAGER_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    } // onListRecipeSelected()

    @Override
    public void onGridRecipeSelected(int index) {
        DualPaneFragment fragment = new DualPaneFragment();

        // Store the index in the bundle to pass it to the next fragment instead of using a constructor
        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerFragment.KEY_RECIPE_INDEX, index);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, fragment, VIEW_PAGER_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    } // onGridRecipeSelected()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

} // MainActivity