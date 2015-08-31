package android.example.com;

import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.text.NumberFormat;


/**
 * Created by Administrator on 2015-08-28.
 */
public class Order implements IOrderBook, Serializable {
    protected String mCoffeeMenu;
    protected int mPriceCoffee;
    protected int mPriceWhippingcream;
    protected int mPriceChocolate;
    protected int mQuantity;
    
    Order() {};
    Order(String coffeemenu, int pricecoffee,
          int pricewhippedcream, int pricechocolate, int quantity) {
        mCoffeeMenu= coffeemenu;
        mPriceCoffee= pricecoffee;
        mPriceWhippingcream= pricewhippedcream;
        mPriceChocolate= pricechocolate;
        mQuantity= quantity;
    }
    public String getOrderList(){
        return mCoffeeMenu+ ","+ mPriceCoffee+ ","+
                mPriceWhippingcream+ ","+ mPriceChocolate+ ","+ mQuantity;
    }
    public int calcTotal() {
        return (mPriceCoffee+ mPriceWhippingcream+ mPriceChocolate)* mQuantity;
    }

    public String getOderSummary(TextView summary, EditText name) {
        int totalSum= mPriceCoffee* mQuantity;
        totalSum+= (mPriceWhippingcream + mPriceChocolate)* mQuantity;

        String strWhipped= "Add whipping cream ? ";
        strWhipped+= mPriceWhippingcream> 0 ? true: false;

        String strChocolate= "Add Chocolate ? ";
        strChocolate+= mPriceChocolate> 0 ? true: false;

        String ordersummary= "Order Summary";
        summary.setText(ordersummary);

        String whoim= "Name: "+ name.getText();
        String price= NumberFormat.getCurrencyInstance().format(totalSum);
        String message= whoim+ "\n"+ strWhipped+ "\n"+ strChocolate+ "\n"+
                "Quantity: "+ mQuantity+ "\n"+
                "Total: "+ price+ "\n"+ R.string.thankyou;

        return message;
    }
}
