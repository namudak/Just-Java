package android.example.com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private int ith= 0;
    private final int PRICE_COFFEE= 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Order process
     * @param view
     */
    public void submitOrder(View view){

        //Log.d(this.getLocalClassName(), "Order button pressed! "+ ith++);
        display(2);
        displayPrice(PRICE_COFFEE* 2);
    }

    /**
     * Quantity display
     * @param number
     */
    private void display(int number) {
        TextView quantityTextView= (TextView)findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Price display
     * @param number
     */
    private void displayPrice(int number){
        TextView priceTextView= (TextView)findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
