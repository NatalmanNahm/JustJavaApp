package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox checkBoxCream = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = checkBoxCream.isChecked();

        CheckBox checkBoxChocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = checkBoxChocolate.isChecked();

        EditText nameText = (EditText) findViewById(R.id.name_text);
        String name = nameText.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String summary = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method increments the number of coffee to order
     */
    public void increment(View view){
        if (quantity == 100 ){

            // Show error as toast
            Toast.makeText(this, "you cannot have more than 100 cup of coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method decrements the number of coffee to order
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //show error as toast
            Toast.makeText(this, "you cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order.
     *return the total price
     */
    private int calculatePrice(boolean whippedcream, boolean chocolate) {
        // price for 1 cup of cofee
        int price = 0;

        //add $1 if the user wants whipped cream
        if (whippedcream){
            price = price + 1;
        }

        //add $2 if the user wants chocolate
        if (chocolate){
            price = price + 2;
        }
        return price * quantity;
    }

    /**
     * Diplay the summary of the order
     */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name){

        String summary = getString(R.string.order_summary_name, name);
        summary += "\nAdd Whipped cream? " + addWhippedCream;
        summary += "\nAdd Chocolate? " + addChocolate;
        summary += "\nQuantity: " + quantity;
        summary += "\nTotal: $" + price;
        summary += "\n" + getString(R.string.thank_you);

        return summary;
    }


}
