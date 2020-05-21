package vn.edu.ntu.nguyendinhhoanglan.recycleview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.edu.ntu.nguyendinhhoanglan.controller.ICartController;
import vn.edu.ntu.nguyendinhhoanglan.model.Product;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtShopping;
    Button btnSubmit, btnCancel;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        this.message = "There's nothing in your shopping cart";
        addViews();
    }

    private void addViews() {
        txtShopping = findViewById(R.id.txtShoppingCart);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        showShoppingCart();
    }

    private void showShoppingCart() {
        ICartController controller = (ICartController) getApplication();
        List<Product> products = controller.getShoppingCart();
        StringBuilder builder = new StringBuilder();
        for (Product p : products) {
            builder.append(p.getName())
                    .append(": \t\t\t")
                    .append(p.getPrice())
                    .append("\n");
        }
        if(builder.length() > 0)
            txtShopping.setText(builder.toString());
        else
            txtShopping.setText(this.message);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnSubmit:
                submit();
                break;
            case R.id.btnCancel:
                cancel();
                break;
        }
    }

    private void submit() {
        Intent intent = new Intent(this, ThankYouActivity.class);
        startActivity(intent);
    }

    private void cancel() {
        txtShopping.setText(this.message);
        Toast.makeText(this, "Cart removed", Toast.LENGTH_SHORT).show();
    }
}
