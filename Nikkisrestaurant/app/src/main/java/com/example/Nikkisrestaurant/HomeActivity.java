package com.example.Nikkisrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Nikkisrestaurant.Model.Products;
import com.example.Nikkisrestaurant.Prevalent.Prevalent;
import com.example.Nikkisrestaurant.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    private DatabaseReference ProductRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private String type = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            type = getIntent().getExtras().get("Admin").toString();
        }

        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");

        Paper.init(this);


        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.my_toolbar);
        navigationView = findViewById(R.id.navigationView);
        recyclerView = findViewById(R.id.recycler_menu);



        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_photo);

        if(!type.equals("Admin")){

            userNameTextView.setText(Prevalent.currentOnlineUser.getName());
            Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);


        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);

            }
        });

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductRef, Products.class).build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Products products) {

                        productViewHolder.textProductName.setText(products.getPname());
                        productViewHolder.textProductDescription.setText(products.getDescription());
                        productViewHolder.textProductPrice.setText("Price = $ "+ products.getPrice());
                        Picasso.get().load(products.getImage()).into(productViewHolder.imageView);



                        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if(type.equals("Admin")){
                                    Intent intent = new Intent(HomeActivity.this, AdminMaintainProductsActivity.class);
                                    intent.putExtra("pid", products.getPid());
                                    startActivity(intent);



                                }else {

                                    Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid", products.getPid());
                                    startActivity(intent);

                                }


                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_cart:
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
                Toast.makeText(HomeActivity.this, "Cart Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_search:
                intent = new Intent(HomeActivity.this, SearchProductsActivity.class);
                startActivity(intent);
                Toast.makeText(HomeActivity.this, "Orders Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_categories:
                Toast.makeText(HomeActivity.this, "Categories Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(HomeActivity.this, "Settings Selected", Toast.LENGTH_SHORT).show();
                intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:

                Paper.book().destroy();

                intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);

                Toast.makeText(HomeActivity.this, "Logout Selected", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
