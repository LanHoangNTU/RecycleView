package vn.edu.ntu.nguyendinhhoanglan.recycleview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

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
        addEvents();
    }

    private void addViews() {
        rvListProduct = findViewById(R.id.rvListProduct);
        rvListProduct.setLayoutManager(new LinearLayoutManager(this));
        ICartController cartController = (ICartController) getApplication();
        listProducts = cartController.getAllProducts();
        adapter = new ProductAdapter(listProducts);
        rvListProduct.setAdapter(adapter);
    }

    private void addEvents() {
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView txtName, txtPrice, txtDescription;
        ImageView imvAddToCart;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = this.itemView.findViewById(R.id.txtName);
            txtPrice = this.itemView.findViewById(R.id.txtPrice);
            txtDescription = this.itemView.findViewById(R.id.txtDescription);
            imvAddToCart = this.itemView.findViewById(R.id.imvAddToCart);
        }

        public void bind(Product p){
            txtName.setText(p.getName());
            txtPrice.setText(new Integer(p.getPrice()).toString());
            txtDescription.setText(p.getDescription());
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
