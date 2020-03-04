package com.example.Nikkisrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView traditional_pizza, gourment_pizza, pasta;
    private ImageView  burger, salads, ribs_n_wings;
    private ImageView deserts, milkshakes;

    private Button logoutButton, checkorderButton, maintainProductsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        logoutButton = findViewById(R.id.admin_logout_button);
        checkorderButton = findViewById(R.id.admin_check_orders_button);
        maintainProductsButton = findViewById(R.id.maintain_button);

        maintainProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);

            }
        });


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

        checkorderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);

            }
        });

        traditional_pizza = findViewById(R.id.traditional_pizza_id);
        gourment_pizza = findViewById(R.id.gourmet_pizza_id);
        pasta = findViewById(R.id.pastas_id);

        burger = findViewById(R.id.burgers_id);
        salads = findViewById(R.id.salads_id);
        ribs_n_wings = findViewById(R.id.ribs_n_wings_id);

        deserts = findViewById(R.id.deserts_id);
        milkshakes = findViewById(R.id.milkshakes_id);

        traditional_pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Traditional Pizza");
                startActivity(intent);


            }
        });

        gourment_pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Gourment Pizza");
                startActivity(intent);

            }
        });

        pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Pasta");
                startActivity(intent);

            }
        });

        burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Burger");
                startActivity(intent);


            }
        });

        salads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Salads");
                startActivity(intent);


            }
        });



        ribs_n_wings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Ribs n Wings");
                startActivity(intent);


            }
        });

        deserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Deserts");
                startActivity(intent);

            }
        });

        milkshakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Milkshakes");
                startActivity(intent);

            }
        });


    }
}
