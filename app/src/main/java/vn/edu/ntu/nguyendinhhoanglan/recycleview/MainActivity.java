package vn.edu.ntu.nguyendinhhoanglan.recycleview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import vn.edu.ntu.nguyendinhhoanglan.controller.CartController;
import vn.edu.ntu.nguyendinhhoanglan.controller.ICartController;
import vn.edu.ntu.nguyendinhhoanglan.model.Product;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvListProduct;
    ProductAdapter adapter;
    List<Product> listProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addViews();
    }

    private void addViews() {
        rvListProduct = findViewById(R.id.rvListProduct);
        rvListProduct.setLayoutManager(new LinearLayoutManager(this));
        ICartController cartController = (ICartController) getApplication();
        listProducts = cartController.getAllProducts();
        adapter = new ProductAdapter(listProducts);
        rvListProduct.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnu_mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.mnuClose:
                finish();
            case R.id.mnuShoppingCart:
                showShoppingCart();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showShoppingCart() {
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        startActivity(intent);
    }

    //Apdater class
    private class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtName, txtPrice, txtDescription;
        ImageView imvAddToCart;
        Product p;
        private ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = this.itemView.findViewById(R.id.txtName);
            txtPrice = this.itemView.findViewById(R.id.txtPrice);
            txtDescription = this.itemView.findViewById(R.id.txtDescription);
            imvAddToCart = this.itemView.findViewById(R.id.imvAddToCart);
            imvAddToCart.setOnClickListener(this);
        }

        private void bind(Product p){
            this.p = p;
            txtName.setText(p.getName());
            txtPrice.setText(String.format(Locale.ENGLISH, "%d", p.getPrice()));
            txtDescription.setText(p.getDescription());
        }

        @Override
        public void onClick(View v) {
            ICartController controller = (ICartController) getApplication();
            if(controller.addToCart(p))
                Toast.makeText(MainActivity.this, "Added " + p.getName() + " into shopping cart", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, p.getName() + " already exist in your shopping cart", Toast.LENGTH_SHORT).show();
        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{
        List<Product> listProduct;

        public ProductAdapter(List<Product> listProduct) {
            this.listProduct = listProduct;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.product, parent, false);
            // view: res/layout/product.xml
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            holder.bind(listProduct.get(position));
        }

        @Override
        public int getItemCount() {
            return listProduct.size();
        }
    }
}
