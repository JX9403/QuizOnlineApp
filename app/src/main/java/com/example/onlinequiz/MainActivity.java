package com.example.onlinequiz;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.onlinequiz.Adapters.CategoryAdapter;
import com.example.onlinequiz.Models.CategoryModel;
import com.example.onlinequiz.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menu;
    View header;
    FirebaseDatabase database;
    FirebaseStorage storage;
    CategoryAdapter adapter;
    ArrayList<CategoryModel> list;

    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();

        list = new ArrayList<>();

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(true);
        loadingDialog.show();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.rvCategory.setLayoutManager(layoutManager);

        adapter = new CategoryAdapter(this, list);
        binding.rvCategory.setAdapter(adapter);

        database.getReference().child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if( snapshot.exists()){
                    list.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                        CategoryModel model = dataSnapshot.getValue(CategoryModel.class);
                        model.setKey(dataSnapshot.getKey());
                        list.add(model);

                    }

                    adapter.notifyDataSetChanged();
                    loadingDialog.dismiss();
                }
                else {
                    loadingDialog.dismiss();
                    Toast.makeText(MainActivity.this, "catefory not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingDialog.dismiss();
                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        menu = findViewById(R.id.menu);

        header = navigationView.getHeaderView(0);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.share){
                    String shareBody = "Heloo quiz app " + "http://play.google.com/store/apps/details?id="+MainActivity.this.getPackageName();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(item.getItemId()==R.id.rate){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+MainActivity.this.getPackageName())));
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(item.getItemId()==R.id.privacy){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")));
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });


    }
}