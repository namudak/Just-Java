package android.example.com;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
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

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private List orderbook= new ArrayList<Order>();
    private Order order;

    private Spinner mSpinner;

    private String mMenuCoffee;
    private int mPriceCoffee;
    private int mPriceWhippingcream;
    private int mPriceChocolate;
    private int mQuantity;

    private TextView mPriceTextView;
    private TextView mQuantityTextView;
    private TextView mSummaryTextView;
    private CheckBox mWhipingCreamCheckBox;
    private CheckBox mChocolateCheckBox;
    private EditText mNameEditText;

    private Button mOrderButton;
    private Button mViewOrderButton;

    private boolean mOnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add Spinner Listener
        addListenerOnSpinnerItemSelection();

        // Add Member variables
        mPriceTextView = (TextView)findViewById(R.id.price_text_view);
        mQuantityTextView = (TextView)findViewById(R.id.quantity_text_view);
        mSummaryTextView = (TextView)findViewById(R.id.order_summary_title);

        mWhipingCreamCheckBox= (CheckBox)findViewById(R.id.topping_checkbox);
        mChocolateCheckBox= (CheckBox)findViewById(R.id.topping2_checkbox);

        mNameEditText= (EditText)findViewById(R.id.name_text_edit);
        mOrderButton= (Button)findViewById(R.id.order_button);
        mViewOrderButton= (Button)findViewById(R.id.view_order_button);

        // Now new order
        mOnOrder= true;

        // Set intial value
        mQuantity= 0;
        mPriceWhippingcream= 0;
        mPriceChocolate= 0;

        // Set price to local currency
        displayPrice(0);
    }

    public void addListenerOnSpinnerItemSelection() {

        mSpinner = (Spinner) findViewById(R.id.coffee_spinner);
        mSpinner.setOnItemSelectedListener(MainActivity.this);
    }


    /**
     * Order process
     * @param view
     */
    public void submitOrder(View view){

        // Next new order ready
        if(!mOnOrder) {
            mQuantity= 0;
            mPriceWhippingcream = 0;
            mPriceChocolate = 0;
            mWhipingCreamCheckBox.setChecked(false);
            mChocolateCheckBox.setChecked(false);
            mSummaryTextView.setText("Price");
            mPriceTextView.setText(NumberFormat.getCurrencyInstance().format(0));
            display(0);
            displayPrice(0);
            mOnOrder= true;
            mOrderButton.setText("주문");
            return;
        }

        // Do return if quantity== 0
        if(mQuantity== 0) return;

        // Do order summary
        displayMessage(order.getOderSummary(mSummaryTextView, mNameEditText));

        // Next order button
        mOnOrder= false;
        mOrderButton.setText("다음 주문");
        
        // Add order to OrderBook
        order= new Order(mMenuCoffee,mPriceCoffee,
                mPriceWhippingcream,mPriceChocolate,mQuantity);

        orderbook.add(order);

    }

    /**
     * List orders at another activity
     * @param view
     */
    public void viewOrder(View view) {
        // Intent에 OrderBook객체 저장
        Intent intent = new Intent(MainActivity.this, ListOrderActivity.class);
        intent.putExtra("orderbook", (ArrayList<Order>) orderbook);

        // ListOrderActivity로 Activity 전환
        startActivity(intent);

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
     * Display user warning using notification
     */
    private void diplayNotification(int num) {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mCompatBuilder = new NotificationCompat.Builder(this);
        mCompatBuilder.setSmallIcon(R.drawable.justjava);
        mCompatBuilder.setTicker("NotificationCompat.Builder");
        mCompatBuilder.setWhen(System.currentTimeMillis());
        mCompatBuilder.setNumber(10);
        mCompatBuilder.setContentTitle("Order error");
        mCompatBuilder.setContentText("Cant order under 0 or over 100 cup(s).");
        mCompatBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mCompatBuilder.setContentIntent(pendingIntent);
        mCompatBuilder.setAutoCancel(true);

        nm.notify(222, mCompatBuilder.build());
    }
    /**
     * Quantity decrease
     * @param view
     */
    public void decreaseQuantity(View view){

        if(--mQuantity< 0) {
            mQuantity = 0;
            diplayNotification(mQuantity);
            //Toast.makeText(getApplicationContext(), "Cant order under 0 cup", Toast.LENGTH_SHORT).show();
        }
        display(mQuantity);

        checkTopping(view);
        checkTopping2(view);

        displayPrice((mPriceCoffee + mPriceWhippingcream + mPriceChocolate) * mQuantity);

    }

    /**
     * Quantity increase
     * @param view
     */
    public void increaseQuantity(View view){

        if(++mQuantity> 100){
            mQuantity= 100;
            diplayNotification(mQuantity);
            //Toast.makeText(getApplicationContext(), "Cant order over 100cups", Toast.LENGTH_SHORT).show();
        }

        display(mQuantity);

        checkTopping(view);
        checkTopping2(view);

        displayPrice((mPriceCoffee + mPriceWhippingcream + mPriceChocolate) * mQuantity);
    }

    /**
     * Check whipped cream
     * @param view
     */
    public void checkTopping(View view) {
        if(mWhipingCreamCheckBox.isChecked()){
            mPriceWhippingcream = 500;
        } else {
            mPriceWhippingcream = 0;
        }
        displayPrice((mPriceCoffee + mPriceWhippingcream + mPriceChocolate) * mQuantity);

    }

    /**
     * Check chocolate
     * @param view
     */
    public void checkTopping2(View view) {
        if (mChocolateCheckBox.isChecked()) {
            mPriceChocolate = 300;
        } else {
            mPriceChocolate = 0;
        }
        displayPrice((mPriceCoffee + mPriceWhippingcream + mPriceChocolate) * mQuantity);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String coffeemenu= parent.getItemAtPosition(position).toString();
        String[] arraymenu= coffeemenu.split(" ");

        mMenuCoffee= arraymenu[0];

        String str= arraymenu[1].replace(",", "");
        mPriceCoffee= Integer.parseInt(str.replace("원", ""));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        String coffeemenu= parent.getItemAtPosition(0).toString();
        String[] arraymenu= coffeemenu.split(" ");

        mMenuCoffee= arraymenu[0];

        String str= arraymenu[1].replace(",", "");
        mPriceCoffee= Integer.parseInt(str.replace("원", ""));
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
