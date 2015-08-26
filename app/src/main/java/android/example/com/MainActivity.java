package android.example.com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private int ith= 0;
    private final int PRICE_COFFEE= 2000;
    private int mWhippingcream= 0;
    private int mChocolate= 0;
    private int mQuantity= 0;
    private TextView mPriceTextView;
    private TextView mQuantityTextView;
    private TextView mSummaryTextView;
    private CheckBox mWhipingCreamCheckBox;
    private CheckBox mChocolateCheckBox;
    private EditText mNameEditText;
    private Button mOrderButton;
    private boolean mOnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView)findViewById(R.id.price_text_view);
        mQuantityTextView = (TextView)findViewById(R.id.quantity_text_view);
        mSummaryTextView = (TextView)findViewById(R.id.order_summary_title);
        
        Spinner spinner = (Spinner) findViewById(R.id.coffee_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.coffee_menu, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        
        mWhipingCreamCheckBox= (CheckBox)findViewById(R.id.topping_checkbox);
        mChocolateCheckBox= (CheckBox)findViewById(R.id.topping2_checkbox);

        mNameEditText= (EditText)findViewById(R.id.name_text_edit);
        mOrderButton= (Button)findViewById(R.id.order_button);

        // Now new order
        mOnOrder= true;
    }


    /**
     * Order process
     * @param view
     */
    public void submitOrder(View view){

        if(!mOnOrder) {
            mQuantity= 0;
            mWhippingcream= 0;
            mChocolate= 0;
            mSummaryTextView.setText("Price");
            mPriceTextView.setText(NumberFormat.getCurrencyInstance().format(0));
            display(0);
            displayPrice(0);
            mOnOrder= true;
            return;
        }
        //Log.d(this.getLocalClassName(), "Order button pressed! "+ ith++);
        //display(mQuantity);
        //displayPrice(PRICE_COFFEE* mQuantity);
        int totalSum= PRICE_COFFEE* mQuantity;
        if(mWhipingCreamCheckBox.isChecked()) {
            totalSum+= (mWhippingcream+ mChocolate)* mQuantity;
        }

        String strWhipped= "Add whipping cream ? ";
        strWhipped+= mWhipingCreamCheckBox.isChecked();

        String strChocolate= "Add whipping cream ? ";
        strChocolate+= mChocolateCheckBox.isChecked();

        String ordersummary= "Order Summary";
        mSummaryTextView.setText(ordersummary);

        String whoim= "Name: "+ mNameEditText.getText();
        String price= NumberFormat.getCurrencyInstance().format(totalSum);
        String message= whoim+ "\n"+ strWhipped+ "\n"+ strChocolate+ "\n"+
                "Quantity: "+ mQuantity+ "\n"+
                "Total: "+ price+ "\n"+ getString(R.string.thankyou);
        displayMessage(message);

        mOnOrder= false;


    }
    /**
     * Quantity display
     * @param number
     */
    private void display(int number) {
        mQuantityTextView.setText("" + number);
    }

    /**
     * Price display
     * @param number
     */
    private void displayPrice(int number){

        mPriceTextView.setText(NumberFormat.getCurrencyInstance().format(number));

    }

    /**
     * Display order message
     * @param message
     */
    private void displayMessage(String message) {

        mPriceTextView.setText(message);
    }

    /**
     * Quantity decrease
     * @param view
     */
    public void decreaseQuantity(View view){
        if(--mQuantity< 0)
            mQuantity= 0;

        display(mQuantity);

        checkTopping(view);
        checkTopping2(view);

        displayPrice((PRICE_COFFEE + mWhippingcream + mChocolate) * mQuantity);

    }

    /**
     * Quantity increase
     * @param view
     */
    public void increaseQuantity(View view){

        if(++mQuantity> 100){
            Toast.makeText(getApplicationContext(), "Cant order over 100cups", Toast.LENGTH_SHORT).show();
            mQuantity= 100;
        }

        display(mQuantity);

        checkTopping(view);
        checkTopping2(view);

        displayPrice((PRICE_COFFEE + mWhippingcream + mChocolate) * mQuantity);
    }

    public void checkTopping(View view) {
        if(mWhipingCreamCheckBox.isChecked()){
            mWhippingcream= 500;
        } else {
            mWhippingcream= 0;
        }
        displayPrice((PRICE_COFFEE + mWhippingcream + mChocolate) * mQuantity);

    }
    public void checkTopping2(View view) {
        if (mChocolateCheckBox.isChecked()) {
            mChocolate= 300;
        } else {
            mChocolate= 0;
        }
        displayPrice((PRICE_COFFEE + mWhippingcream + mChocolate) * mQuantity);

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Spinner spinner = (Spinner) findViewById(R.id.coffee_spinner);
        spinner.setOnItemSelectedListener(this);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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
