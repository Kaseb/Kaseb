package mjkarbasian.moshtarimadar.Helpers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by family on 6/27/2016.
 */
public class Samples {
    public static Integer[] customerName = {R.string.sample_ali_ghorbani_name, R.string.sample_mohammad_alikhani_name, R.string.sample_sima_saberzadeh};
    public static ArrayList<Uri> customerAvatar = new ArrayList<Uri>();

    public static Integer[] paymentModels = {R.string.payment_model_cash, R.string.payment_model_pos, R.string.payment_model_czech, R.string.payment_model_credit};

    public static Integer[] debatorName = {customerName[0], customerName[2]};
    public static Integer[] offTaxSticks = {R.string.sale_tax_tax_stick, R.string.sale_tax_off_stick};
    public static Integer[] debtorsCodeNums = {3, 4};
    public static double[] debatorOneCodes = {12430, 13450, 13900};
    public static double[] debatorTwoCodes = {10253, 11276, 13438, 14864};
    public static Integer[] numberOfPurchase = {2, 3, 4};
    public static Integer[] customerMembership = {2, 1, 3};

    public static double[][] debtorsCode = {debatorOneCodes, debatorTwoCodes};
    public static String[] debtorOneDue = {"2016/6/30", "2016/10/8", "2016/12/9"};
    public static String[] debtorTwoDue = {"2016/6/15", "2016/9/11", "2016/10/13", "2016/11/27"};
    public static String[] debtor3oDue = {"2016/2/10", "2016/3/11"};
    public static String[][] debtorsDue = {debtorOneDue, debtorTwoDue, debtor3oDue};
    public static double[] debtorOneBalance = {900000, 250000, 350000};
    public static double[] debtorTwoBalance = {600000, 250000, 250000, 100000};
    public static double[][] debtorBalance = {debtorOneBalance, debtorTwoBalance};
    public static Float[] customerRating = new Float[]{4.4f, 3.2f, 2.5f};
    public static String[] customerPhoneNumber = {"09124383454", "09121343254", "09122515237"};
    public static String[] customerEmail = {"a.ghorbani@yahoo.com", "maA1367@gmail.com", "saberzade.sima@gmail.com"};
    public static double[] customerPurchaseAmount = {5590000, 8320000, 732000};
    public static double[] customerDebateBalance = {1500000, 1200000};
    public static String[] customerDebtAmount = {"0", "455,000", "100,000"};
    public static String[] debtorsMobileNumber = {"09124381232", "09124543212"};


    public static ArrayList<String> salesCode = new ArrayList<String>();
    public static ArrayList<String> salesDue = new ArrayList<String>();
    public static ArrayList<ArrayList<String>> sales = new ArrayList<ArrayList<String>>();
    public static ArrayList<String> salesCustomer = new ArrayList<String>();
    public static ArrayList<String> salesAmount = new ArrayList<String>();
    public static ArrayList<String> salesFinalAmount = new ArrayList<String>();
    public static ArrayList<String> amountOfSale = new ArrayList<String>();

    public static double[] productsPrice = {600000, 900000, 1200000, 332000, 400000};
    public static ArrayList<String> costNames = new ArrayList<String>();
    public static ArrayList<String> costsDue = new ArrayList<String>();
    public static ArrayList<String> costsCode = new ArrayList<String>();
    public static ArrayList<String> costsAmount = new ArrayList<String>();
    public static ArrayList<ArrayList<String>> costs = new ArrayList<ArrayList<String>>();

    public static ArrayList<String> productName = new ArrayList<String>();
    public static ArrayList<String> productCode = new ArrayList<String>();
    public static ArrayList<String> productDate = new ArrayList<String>();
    public static ArrayList<String> productPrice = new ArrayList<String>();
    public static ArrayList<String> productPics = new ArrayList<String>();
    public static ArrayList<ArrayList<String>> products = new ArrayList<ArrayList<String>>();

    //sale detail tables and columns
    public static ArrayList<ArrayList<String>> saleProductList = new ArrayList<ArrayList<String>>();
    public static ArrayList<ArrayList<String>> salePaymentList = new ArrayList<ArrayList<String>>();
    public static ArrayList<ArrayList<String>> saleOffTaxList = new ArrayList<ArrayList<String>>();
    public static ArrayList<ArrayList<String>> saleSummary = new ArrayList<ArrayList<String>>();

    //product detail tables and columns
    public static ArrayList<ArrayList<String>> productPriceList = new ArrayList<ArrayList<String>>();
    public static Bundle customer_1 = new Bundle();
    public static Bundle product_1 = new Bundle();
    public static Bundle cost_1 = new Bundle();
    public static Bundle sale_1 = new Bundle();
    public static Bundle sale_2 = new Bundle();
    public static Bundle detail_sale_1 = new Bundle();
    //detail sale mdata: salecode,products , payments code ,discount,saleAmount,saleCustomer
    //payments mdata: salecode,method,date,amount,
    //discount mdata:salecode,discountPercentOfsaleAmount,discountAmount
    public static Bundle detail_sale_2 = new Bundle();
    public static Bundle product_history = new Bundle();
    public static Bundle ds1ProductItems = new Bundle();
    public static Bundle ds2ProductItems = new Bundle();
    public static Bundle ds1PaymentItems = new Bundle();
    public static Bundle ds2PaymentItems = new Bundle();
    public static Bundle ds1TaxItems = new Bundle();
    public static Bundle ds2TaxItems = new Bundle();
    public static Float product_1_sale_price = 1400.0f;

    public static void setSalesCode() {
        salesCode.add("12430");
        salesCode.add("12431");
        salesCode.add("12432");
        salesCode.add("12433");
        salesCode.add("12434");
        salesCode.add("12435");
        salesCode.add("12436");
        salesCode.add("12437");
        salesCode.add("12438");
    }

    public static void setSaleDueDate() {
        Calendar calendar = Calendar.getInstance();
        //add today
        Date today_time = calendar.getTime();
        String today = Utility.formatDate(calendar);
        salesDue.add(today);
        //add today two hours ago
        calendar.roll(Calendar.HOUR_OF_DAY, -2);
        String todayTwoHourAgo = Utility.formatDate(calendar);
        salesDue.add(todayTwoHourAgo);
        //add yesterday;
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        String yesterday = Utility.formatDate(calendar);
        salesDue.add(yesterday);
        //add two days ago
        calendar.roll(Calendar.DAY_OF_MONTH, -2);
        String twoDaysAgo = Utility.formatDate(calendar);
        salesDue.add(twoDaysAgo);
        //add three days ago
        calendar.roll(Calendar.DAY_OF_WEEK, -3);
        String threeDaysAgo = Utility.formatDate(calendar);
        salesDue.add(threeDaysAgo);
        //add last weeks ago
        calendar.roll(Calendar.WEEK_OF_MONTH, -2);
        String lastWeek = Utility.formatDate(calendar);
        salesDue.add(lastWeek);
        //add last weeks 2 days ago
        calendar.roll(Calendar.DAY_OF_WEEK, -2);
        String lastWeekDays = Utility.formatDate(calendar);
        salesDue.add(lastWeekDays);
        //add last weeks 1 days ago
        calendar.roll(Calendar.DAY_OF_WEEK, -1);
        String lastWeek2Days = Utility.formatDate(calendar);
        salesDue.add(lastWeek2Days);
        //add last month and before
        calendar.roll(Calendar.MONTH, -1);
        String lastMonthDays = Utility.formatDate(calendar);
        salesDue.add(lastMonthDays);

    }

    public static void setSalesCustomer(Context context) {
        salesCustomer.add(context.getString(customerName[0]));
        salesCustomer.add(context.getString(customerName[2]));
        salesCustomer.add(context.getString(customerName[1]));
        salesCustomer.add(context.getString(customerName[1]));
        salesCustomer.add(context.getString(customerName[2]));
        salesCustomer.add(context.getString(customerName[0]));
        salesCustomer.add(context.getString(customerName[1]));
        salesCustomer.add(context.getString(customerName[2]));
        salesCustomer.add(context.getString(customerName[2]));

    }

    public static void setSalesAmount() {

        setSaleProductList();
        for (String code : salesCode) {
            Integer totalAmount = 0;
            for (int i = 0; i < amountOfSale.size(); i++) {
                if (saleProductList.get(0).get(i).equals(code)) {
                    totalAmount += Integer.parseInt(saleProductList.get(4).get(i));
                }
            }
            salesAmount.add(Integer.toString(totalAmount));
        }

    }

    public static void setSaleFinalAmount(Context context) {
        for (String code : salesCode) {
            Integer offTaxSum = 0;
            int j = 0;
            for (int i = 0; i < saleOffTaxList.get(0).size(); i++) {
                if (saleOffTaxList.get(0).get(i).equals(code)) {
                    if (saleOffTaxList.get(1).get(i).equals(context.getResources().getString(offTaxSticks[0]))) {
                        offTaxSum += Integer.parseInt(saleOffTaxList.get(2).get(i));
                    } else {
                        offTaxSum -= Integer.parseInt(saleOffTaxList.get(2).get(i));
                    }
                }
            }
            salesFinalAmount.add(j, Integer.toString(Integer.parseInt(salesAmount.get(j)) - offTaxSum));
            j++;
        }
    }

    public static void setSale() {
        Collections.reverse(salesCode);
        sales.add(salesDue);
        sales.add(salesCode);
        sales.add(salesCustomer);
        sales.add(salesAmount);
        sales.add(salesFinalAmount);
    }

    public static void setCostNames(Context context) {
        costNames.add(context.getString(R.string.sample_cost_name));
        costNames.add(context.getString(R.string.sample_cost_name_2));
        costNames.add(context.getString(R.string.sample_cost_name_3));
        costNames.add(context.getString(R.string.sample_cost_name_4));
    }

    public static void setCostDue() {
        Calendar calendar = Calendar.getInstance();
        //add today
        Date today_time = calendar.getTime();
        String today = Utility.formatDate(calendar);
        costsDue.add(today);
        //add today two hours ago
        calendar.roll(Calendar.HOUR_OF_DAY, -2);
        String todayTwoHourAgo = Utility.formatDate(calendar);
        costsDue.add(todayTwoHourAgo);
        //add last weeks 2 days ago
        calendar.roll(Calendar.DAY_OF_MONTH, -2);
        String lastWeekDays = Utility.formatDate(calendar);
        costsDue.add(lastWeekDays);
        //add last month and before
        calendar.roll(Calendar.MONTH, -1);
        String lastMonthDays = Utility.formatDate(calendar);
        costsDue.add(lastMonthDays);
    }

    public static void setCostCode() {
        costsCode.add("102");
        costsCode.add("103");
        costsCode.add("104");
        costsCode.add("105");
    }

    public static void setCostAmount() {
        costsAmount.add("17500");
        costsAmount.add("43000");
        costsAmount.add("25000");
        costsAmount.add("78000");
    }

    public static void setCost(Context context) {
        Collections.reverse(costsCode);
        costs.add(costsDue);
        costs.add(costsCode);
        costs.add(costNames);
        costs.add(costsAmount);

    }

    public static void setProductName(Context context) {
        productName.add(context.getString(R.string.sample_product_name));
        productName.add(context.getString(R.string.sample_product_name_2));
        productName.add(context.getString(R.string.sample_product_name_3));
        productName.add(context.getString(R.string.sample_product_name_4));
        productName.add(context.getString(R.string.sample_product_name_5));
        productName.add(context.getString(R.string.sample_product_name_6));
    }

    public static void setProductCode() {
        productCode.add("110");
        productCode.add("111");
        productCode.add("112");
        productCode.add("113");
        productCode.add("114");
        productCode.add("115");
    }

    public static void setProductDate() {
        Calendar calendar = Calendar.getInstance();
        //add today
        Date today_time = calendar.getTime();
        String today = Utility.formatDate(calendar);
        productDate.add(today);
        //add today two hours ago
        calendar.roll(Calendar.HOUR_OF_DAY, -2);
        String todayTwoHourAgo = Utility.formatDate(calendar);
        productDate.add(todayTwoHourAgo);
        //add yesterday;
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        String yesterday = Utility.formatDate(calendar);
        productDate.add(yesterday);
        //add two days ago
        calendar.roll(Calendar.DAY_OF_MONTH, -2);
        String twoDaysAgo = Utility.formatDate(calendar);
        productDate.add(twoDaysAgo);
        //add three days ago
        calendar.roll(Calendar.DAY_OF_WEEK, -3);
        String threeDaysAgo = Utility.formatDate(calendar);
        productDate.add(threeDaysAgo);
        //add last weeks ago
        calendar.roll(Calendar.WEEK_OF_MONTH, -2);
        String lastWeek = Utility.formatDate(calendar);
        productDate.add(lastWeek);
    }

    public static void setProductPrice() {
        productPrice.add("1300000");
        productPrice.add("670000");
        productPrice.add("230000");
        productPrice.add("120000");
        productPrice.add("560000");
        productPrice.add("760000");
    }

    public static void setProductPicture(Context context) {
        int[] productPicture = new int[]{R.raw.pr01, R.raw.pr02, R.raw.pr03, R.raw.pr04, R.raw.pr05, R.raw.pr06};
        for (int pic : productPicture) {
            productPics.add(Integer.toString(pic));
        }
    }

    public static void setProducts() {
        Collections.reverse(productCode);
        products.add(productDate);
        products.add(productCode);
        products.add(productName);
        products.add(productPrice);
    }

    public static void setSaleProductList() {
        //Coloumns are : salecode,productName,Price,numbers,total amount
        //fill in salecode
        ArrayList codeOfSale = new ArrayList();
        codeOfSale.add(salesCode.get(0));
        codeOfSale.add(salesCode.get(0));
        codeOfSale.add(salesCode.get(0));

        codeOfSale.add(salesCode.get(1));
        codeOfSale.add(salesCode.get(1));

        codeOfSale.add(salesCode.get(2));

        codeOfSale.add(salesCode.get(3));
        codeOfSale.add(salesCode.get(3));
        codeOfSale.add(salesCode.get(3));
        codeOfSale.add(salesCode.get(3));

        codeOfSale.add(salesCode.get(4));

        codeOfSale.add(salesCode.get(5));
        codeOfSale.add(salesCode.get(5));

        codeOfSale.add(salesCode.get(6));
        codeOfSale.add(salesCode.get(6));
        codeOfSale.add(salesCode.get(6));
        codeOfSale.add(salesCode.get(6));
        codeOfSale.add(salesCode.get(6));

        codeOfSale.add(salesCode.get(7));

        codeOfSale.add(salesCode.get(8));

        saleProductList.add(0, codeOfSale);
        //fill in productName
        ArrayList<String> productsOfSale = new ArrayList<String>();

        productsOfSale.add(productName.get(0));
        productsOfSale.add(productName.get(2));
        productsOfSale.add(productName.get(3));

        productsOfSale.add(productName.get(1));
        productsOfSale.add(productName.get(3));

        productsOfSale.add(productName.get(4));

        productsOfSale.add(productName.get(1));
        productsOfSale.add(productName.get(2));
        productsOfSale.add(productName.get(4));
        productsOfSale.add(productName.get(5));

        productsOfSale.add(productName.get(5));

        productsOfSale.add(productName.get(1));
        productsOfSale.add(productName.get(2));

        productsOfSale.add(productName.get(1));
        productsOfSale.add(productName.get(2));
        productsOfSale.add(productName.get(3));
        productsOfSale.add(productName.get(4));
        productsOfSale.add(productName.get(5));

        productsOfSale.add(productName.get(5));

        productsOfSale.add(productName.get(5));

        saleProductList.add(1, productsOfSale);

        //fill in price
        ArrayList<String> priceOfSale = new ArrayList<String>();

        for (String product : productsOfSale) {
            priceOfSale.add(products.get(3).get(products.get(2).indexOf(product)));
        }
        saleProductList.add(2, priceOfSale);

        //fill in numbers
        ArrayList<String> numberOfSale = new ArrayList<String>();

        numberOfSale.add("1");
        numberOfSale.add("1");
        numberOfSale.add("2");

        numberOfSale.add("1");
        numberOfSale.add("3");

        numberOfSale.add("5");

        numberOfSale.add("1");
        numberOfSale.add("3");
        numberOfSale.add("5");
        numberOfSale.add("2");

        numberOfSale.add("10");

        numberOfSale.add("9");
        numberOfSale.add("7");

        numberOfSale.add("3");
        numberOfSale.add("4");
        numberOfSale.add("1");
        numberOfSale.add("2");
        numberOfSale.add("5");

        numberOfSale.add("2");

        numberOfSale.add("3");

        saleProductList.add(3, numberOfSale);

        //fill in total price


        for (int i = 0; i < numberOfSale.size(); i++) {
            amountOfSale.add(Integer.toString(Integer.parseInt(priceOfSale.get(i)) * Integer.parseInt(numberOfSale.get(i))));
        }
        saleProductList.add(4, amountOfSale);
    }

    public static void setSalePaymentList(Context context) throws ParseException {
        //Coloumns are : salecode,totalAmount,paymentDate,paymentModel,paymentAmount,paymentBalance

        //fill in salecode
        ArrayList codeOfSale = new ArrayList();
        codeOfSale.add(salesCode.get(0));


        codeOfSale.add(salesCode.get(1));
        codeOfSale.add(salesCode.get(1));

        codeOfSale.add(salesCode.get(2));

        codeOfSale.add(salesCode.get(3));
        codeOfSale.add(salesCode.get(3));
        codeOfSale.add(salesCode.get(3));


        codeOfSale.add(salesCode.get(4));

        codeOfSale.add(salesCode.get(5));

        codeOfSale.add(salesCode.get(6));
        codeOfSale.add(salesCode.get(6));


        codeOfSale.add(salesCode.get(7));

        codeOfSale.add(salesCode.get(8));

        salePaymentList.add(0, codeOfSale);

        //fill in total amount
        ArrayList totalAmount = new ArrayList();
        totalAmount.add(salesAmount.get(0));


        totalAmount.add(salesAmount.get(1));
        totalAmount.add(salesAmount.get(1));

        totalAmount.add(salesAmount.get(2));

        totalAmount.add(salesAmount.get(3));
        totalAmount.add(salesAmount.get(3));
        totalAmount.add(salesAmount.get(3));


        totalAmount.add(salesAmount.get(4));

        totalAmount.add(salesAmount.get(5));

        totalAmount.add(salesAmount.get(6));
        totalAmount.add(salesAmount.get(6));


        totalAmount.add(salesAmount.get(7));

        totalAmount.add(salesAmount.get(8));

        salePaymentList.add(1, totalAmount);

        //fill in payment day
        ArrayList paymentDate = new ArrayList();
        paymentDate.add(salesDue.get(0));

        paymentDate.add(salesDue.get(1));
        paymentDate.add(Utility.changeDate(salesDue.get(1), Calendar.DATE, 5));

        paymentDate.add(salesDue.get(2));

        paymentDate.add(salesDue.get(3));
        paymentDate.add(Utility.changeDate(salesDue.get(3), Calendar.DATE, 2));
        paymentDate.add(Utility.changeDate(salesDue.get(3), Calendar.MONTH, 2));


        paymentDate.add(salesDue.get(4));

        paymentDate.add(salesDue.get(5));

        paymentDate.add(salesDue.get(6));
        paymentDate.add(Utility.changeDate(salesDue.get(6), Calendar.DATE, 6));


        paymentDate.add(salesDue.get(7));

        paymentDate.add(salesDue.get(8));

        salePaymentList.add(2, paymentDate);

        //fill in payment method
        ArrayList paymentMethod = new ArrayList();
        paymentMethod.add(context.getResources().getString(paymentModels[0]));

        paymentMethod.add(context.getResources().getString(paymentModels[0]));
        paymentMethod.add(context.getResources().getString(paymentModels[1]));

        paymentMethod.add(context.getResources().getString(paymentModels[2]));

        paymentMethod.add(context.getResources().getString(paymentModels[0]));
        paymentMethod.add(context.getResources().getString(paymentModels[1]));
        paymentMethod.add(context.getResources().getString(paymentModels[1]));


        paymentMethod.add(context.getResources().getString(paymentModels[3]));

        paymentMethod.add(context.getResources().getString(paymentModels[3]));

        paymentMethod.add(context.getResources().getString(paymentModels[2]));
        paymentMethod.add(context.getResources().getString(paymentModels[0]));


        paymentMethod.add(context.getResources().getString(paymentModels[1]));

        paymentMethod.add(context.getResources().getString(paymentModels[0]));

        salePaymentList.add(3, paymentMethod);

        //fill in payment amount
        ArrayList paymentAmount = new ArrayList();
        paymentAmount.add(salesAmount.get(0));


        paymentAmount.add(Integer.toString(Integer.parseInt(salesAmount.get(1)) / 2));
        paymentAmount.add(Integer.toString(Integer.parseInt(salesAmount.get(1)) / 2 - 100000));

        paymentAmount.add(salesAmount.get(2));

        paymentAmount.add(Integer.toString(Integer.parseInt(salesAmount.get(3)) / 4));
        paymentAmount.add(Integer.toString(Integer.parseInt(salesAmount.get(3)) / 2));
        paymentAmount.add(Integer.toString(Integer.parseInt(salesAmount.get(3)) / 4));


        paymentAmount.add("0");

        paymentAmount.add("0");

        paymentAmount.add(Integer.toString(Integer.parseInt(salesAmount.get(6)) - 185000));
        paymentAmount.add("185000");


        paymentAmount.add(Integer.toString(Integer.parseInt(salesAmount.get(7)) * 3 / 4));

        paymentAmount.add(salesAmount.get(8));

        salePaymentList.add(4, paymentAmount);

        //fill in balance
        ArrayList balanceState = new ArrayList();
        balanceState.add(String.valueOf(true));


        balanceState.add(String.valueOf(false));
        balanceState.add(String.valueOf(false));

        balanceState.add(salesAmount.get(2));

        balanceState.add(String.valueOf(false));
        balanceState.add(String.valueOf(false));
        balanceState.add(String.valueOf(true));


        balanceState.add(String.valueOf(false));

        balanceState.add(String.valueOf(false));

        balanceState.add(String.valueOf(false));
        balanceState.add(String.valueOf(true));


        balanceState.add(String.valueOf(false));

        balanceState.add(String.valueOf(true));

        salePaymentList.add(5, balanceState);

    }

    public static void setSaleOffTaxList(Context context) {
        //Coloumns are : salecode,Off/Tax,amount
        //fill in Off/Tax
        ArrayList codeOfSale = new ArrayList();
        codeOfSale.add(salesCode.get(0));
        codeOfSale.add(salesCode.get(0));

        codeOfSale.add(salesCode.get(1));
        codeOfSale.add(salesCode.get(1));

        codeOfSale.add(salesCode.get(2));

        codeOfSale.add(salesCode.get(3));

        codeOfSale.add(salesCode.get(5));

        codeOfSale.add(salesCode.get(6));
        codeOfSale.add(salesCode.get(6));

        codeOfSale.add(salesCode.get(7));

        codeOfSale.add(salesCode.get(8));
        codeOfSale.add(salesCode.get(8));
        saleOffTaxList.add(0, codeOfSale);
        //fill in off/Tax'
        ArrayList offTax = new ArrayList();
        offTax.add(context.getResources().getString(offTaxSticks[0]));
        offTax.add(context.getResources().getString(offTaxSticks[1]));

        offTax.add(context.getResources().getString(offTaxSticks[0]));
        offTax.add(context.getResources().getString(offTaxSticks[1]));

        offTax.add(context.getResources().getString(offTaxSticks[0]));

        offTax.add(context.getResources().getString(offTaxSticks[0]));

        offTax.add(context.getResources().getString(offTaxSticks[0]));

        offTax.add(context.getResources().getString(offTaxSticks[0]));
        offTax.add(context.getResources().getString(offTaxSticks[1]));

        offTax.add(context.getResources().getString(offTaxSticks[0]));

        offTax.add(context.getResources().getString(offTaxSticks[0]));
        offTax.add(context.getResources().getString(offTaxSticks[1]));
        saleOffTaxList.add(1, offTax);

        //fill in amount

        ArrayList amount = new ArrayList();
        amount.add(Integer.toString(Integer.parseInt(salesAmount.get(0)) * 9 / 100));
        amount.add(Integer.toString(50000));

        amount.add(Integer.toString(Integer.parseInt(salesAmount.get(1)) * 9 / 100));
        amount.add(Integer.toString(Integer.parseInt(salesAmount.get(1)) * 2 / 100));

        amount.add(Integer.toString(Integer.parseInt(salesAmount.get(2)) * 5 / 100));

        amount.add(Integer.toString(Integer.parseInt(salesAmount.get(3)) * 6 / 100));

        amount.add(Integer.toString(Integer.parseInt(salesAmount.get(4)) * 3 / 100));

        amount.add(Integer.toString(Integer.parseInt(salesAmount.get(5)) * 9 / 100));
        amount.add(Integer.toString(Integer.parseInt(salesAmount.get(5)) * 3 / 100));

        amount.add(Integer.toString(Integer.parseInt(salesAmount.get(6)) * 9 / 100));

        amount.add(Integer.toString(Integer.parseInt(salesAmount.get(7)) * 9 / 100));
        amount.add(Integer.toString(Integer.parseInt(salesAmount.get(8)) * 3 / 100));
        saleOffTaxList.add(2, amount);

    }

    public static void setSaleSummary() {
        //Coloumns are : salecode,TotalItemAmount,Tax,Discount,finalAmount,Payment,Balance

        //filll in saleCode
        ArrayList codeOfSale = new ArrayList();
        codeOfSale.add(salesCode.get(0));
        codeOfSale.add(salesCode.get(1));
        codeOfSale.add(salesCode.get(2));
        codeOfSale.add(salesCode.get(3));
        codeOfSale.add(salesCode.get(4));
        codeOfSale.add(salesCode.get(5));
        codeOfSale.add(salesCode.get(6));
        codeOfSale.add(salesCode.get(7));
        codeOfSale.add(salesCode.get(8));

        saleSummary.add(0, codeOfSale);

        //fill in TotlaAmounnt
        saleSummary.add(1, salesAmount);

        //fill in Tax and discount
        ArrayList tax = new ArrayList();
        tax.add(Integer.toString(Integer.parseInt(salesAmount.get(0)) * 9 / 100));
        tax.add(Integer.toString(Integer.parseInt(salesAmount.get(1)) * 9 / 100));
        tax.add(Integer.toString(Integer.parseInt(salesAmount.get(2)) * 5 / 100));
        tax.add(Integer.toString(Integer.parseInt(salesAmount.get(3)) * 6 / 100));
        tax.add("0");
        tax.add(Integer.toString(Integer.parseInt(salesAmount.get(4)) * 3 / 100));
        tax.add(Integer.toString(Integer.parseInt(salesAmount.get(5)) * 9 / 100));
        tax.add(Integer.toString(Integer.parseInt(salesAmount.get(6)) * 9 / 100));
        tax.add(Integer.toString(Integer.parseInt(salesAmount.get(7)) * 9 / 100));
        saleSummary.add(2, tax);

        //fill in off
        ArrayList off = new ArrayList();
        off.add(Integer.toString(50000));
        off.add(Integer.toString(Integer.parseInt(salesAmount.get(1)) * 2 / 100));
        off.add("0");
        off.add("0");
        off.add("0");
        off.add("0");
        off.add(Integer.toString(Integer.parseInt(salesAmount.get(5)) * 3 / 100));
        off.add("0");
        off.add(Integer.toString(Integer.parseInt(salesAmount.get(8)) * 3 / 100));
        saleSummary.add(3, off);

        //fill in payment
        ArrayList paymentAmount = new ArrayList();
        paymentAmount.add(salesAmount.get(0));
        paymentAmount.add(Integer.toString(Integer.parseInt(salesAmount.get(1)) / 2 + Integer.parseInt(salesAmount.get(1)) / 2 - 100000));
        paymentAmount.add(salesAmount.get(2));
        paymentAmount.add(Integer.toString(Integer.parseInt(salesAmount.get(3)) / 4 + Integer.parseInt(salesAmount.get(3)) / 2 + Integer.parseInt(salesAmount.get(3)) / 4));
        paymentAmount.add("0");
        paymentAmount.add("0");
        paymentAmount.add(Integer.toString(Integer.parseInt(salesAmount.get(6))));
        paymentAmount.add(Integer.toString(Integer.parseInt(salesAmount.get(7)) * 3 / 4));
        paymentAmount.add(salesAmount.get(8));
        saleSummary.add(4, paymentAmount);

        //final amount
        saleSummary.add(5, salesFinalAmount);

        //fill in Balance
        ArrayList balance = new ArrayList();
        for (int i = 0; i < codeOfSale.size(); i++) {
            balance.add(i, Integer.toString(Integer.parseInt(saleSummary.get(1).get(i)) - Integer.parseInt(saleSummary.get(4).get(i))));
        }
        saleSummary.add(6, balance);

    }

    public static void setProductsPriceList() {
        //Coloumns are productCode,Date,time,buyPrice,Price for sale,picture

        //fill in productCode
        ArrayList prCode = new ArrayList();
        prCode.add(productCode.get(0));
        prCode.add(productCode.get(0));

        prCode.add(productCode.get(1));
        prCode.add(productCode.get(1));
        prCode.add(productCode.get(1));

        prCode.add(productCode.get(2));

        prCode.add(productCode.get(3));

        prCode.add(productCode.get(4));
        prCode.add(productCode.get(4));

        prCode.add(productCode.get(5));


        productPriceList.add(0, prCode);

        //fill in Date
        ArrayList date = new ArrayList();
        date.add("2016/8/12");
        date.add("2016/8/13");

        date.add("2016/8/10");
        date.add("2016/8/09");
        date.add("2016/8/11");

        date.add("2016/8/16");

        date.add("2016/8/18");

        date.add("2016/8/20");
        date.add("2016/8/21");

        date.add("2016/8/22");


        productPriceList.add(1, date);

        //fill in time
        ArrayList time = new ArrayList();
        time.add("9:28");
        time.add("10:15");

        time.add("11:45");
        time.add("16:30");
        time.add("12:22");

        time.add("18:48");

        time.add("9:20");

        time.add("11:38");
        time.add("19:20");

        time.add("15:48");


        productPriceList.add(2, time);

        //fill in buy Price
        ArrayList bPrice = new ArrayList();
        bPrice.add(Integer.toString(Integer.parseInt(productPrice.get(0)) - 5000));
        bPrice.add(Integer.toString(Integer.parseInt(productPrice.get(0)) - 3000));

        bPrice.add(Integer.toString(Integer.parseInt(productPrice.get(1)) - 5000));
        bPrice.add(Integer.toString(Integer.parseInt(productPrice.get(1)) - 4000));
        bPrice.add(Integer.toString(Integer.parseInt(productPrice.get(1)) - 3000));

        bPrice.add(Integer.toString(Integer.parseInt(productPrice.get(2)) - 3000));

        bPrice.add(Integer.toString(Integer.parseInt(productPrice.get(3)) - 3000));

        bPrice.add(Integer.toString(Integer.parseInt(productPrice.get(4)) - 2000));
        bPrice.add(Integer.toString(Integer.parseInt(productPrice.get(4)) - 6000));

        bPrice.add(Integer.toString(Integer.parseInt(productPrice.get(5)) - 2000));


        productPriceList.add(3, bPrice);

        //fill in sell Price
        ArrayList sPrice = new ArrayList();
        sPrice.add(Integer.toString(Integer.parseInt(productPrice.get(0)) - 1000));
        sPrice.add(Integer.toString(Integer.parseInt(productPrice.get(0))));

        sPrice.add(Integer.toString(Integer.parseInt(productPrice.get(1)) - 3000));
        sPrice.add(Integer.toString(Integer.parseInt(productPrice.get(1)) - 2000));
        sPrice.add(Integer.toString(Integer.parseInt(productPrice.get(1))));

        sPrice.add(Integer.toString(Integer.parseInt(productPrice.get(2))));

        sPrice.add(Integer.toString(Integer.parseInt(productPrice.get(3))));

        sPrice.add(Integer.toString(Integer.parseInt(productPrice.get(4))));
        sPrice.add(Integer.toString(Integer.parseInt(productPrice.get(4))));

        sPrice.add(Integer.toString(Integer.parseInt(productPrice.get(5))));

        productPriceList.add(4, sPrice);

        //fill in picture
        productPriceList.add(5, productPics);
    }

    public static void setCustomer_1(Context context) {
        customer_1.putString(KasebContract.Customers.COLUMN_FIRST_NAME,
                context.getResources().getString(R.string.sample_ali_ghorbani_name));
        customer_1.putString(KasebContract.Customers.COLUMN_LAST_NAME,
                context.getResources().getString(R.string.sample_ali_ghorbani_last_name));
        customer_1.putString(KasebContract.Customers.COLUMN_EMAIL, "a.ghorbani@gmail.com");
        customer_1.putString(KasebContract.Customers.COLUMN_PHONE_MOBILE, "09124383454");
        customer_1.putString(KasebContract.Customers.COLUMN_PHONE_FAX, "02144552345");
        customer_1.putString(KasebContract.Customers.COLUMN_PHONE_HOME, "02143546576");
        customer_1.putString(KasebContract.Customers.COLUMN_PHONE_WORK, "+12354657856");
        customer_1.putString(KasebContract.Customers.COLUMN_PHONE_OTHER, "+1234351267");
        customer_1.putString(KasebContract.Customers.COLUMN_ADDRESS_CITY,
                context.getResources().getString(R.string.sample_ali_ghorbani_city));
        customer_1.putString(KasebContract.Customers.COLUMN_ADDRESS_COUNTRY,
                context.getResources().getString(R.string.sample_ali_ghorbani_country));
        customer_1.putString(KasebContract.Customers.COLUMN_ADDRESS_STREET,
                context.getResources().getString(R.string.sample_ali_ghorbani_street));
        customer_1.putString(KasebContract.Customers.COLUMN_ADDRESS_POSTAL_CODE,
                context.getResources().getString(R.string.sample_ali_ghorbani_postal_code));
        customer_1.putString(KasebContract.Customers.COLUMN_BIRTHDAY, "1985/08/12");
        customer_1.putString(KasebContract.Customers.COLUMN_DESCRIPTION,
                context.getResources().getString(R.string.sample_ali_ghorbani_description));
        customer_1.putInt(KasebContract.Customers.COLUMN_STATE_ID,
                2);
    }

    public static void setProduct_1(Context context) {
        product_1.putString(KasebContract.Products.COLUMN_PRODUCT_NAME,
                context.getResources().getString(R.string.sample_product_name_2));
        product_1.putString(KasebContract.Products.COLUMN_PRODUCT_CODE, Utility.preInsertProductCode(context));
        product_1.putString(KasebContract.Products.COLUMN_DESCRIPTION, "very light");
        product_1.putString(KasebContract.Products.COLUMN_UNIT,
                context.getResources().getString(R.string.sample_unit));
    }

    public static void setCost_1(Context context) {
        cost_1.putString(KasebContract.Costs.COLUMN_COST_NAME, context.getResources().getString(R.string.sample_cost_name));
        cost_1.putString(KasebContract.Costs.COLUMN_COST_CODE, Utility.preInsertCostCode(context));
        cost_1.putString(KasebContract.Costs.COLUMN_DATE, Utility.preInsertDate(context));
        cost_1.putString(KasebContract.Costs.COLUMN_DESCRIPTION, context.getResources().getString(R.string.sample_cost_description));
        cost_1.putFloat(KasebContract.Costs.COLUMN_AMOUNT, 1500.0f);
        cost_1.putInt(KasebContract.Costs.COLUMN_COST_TYPE_ID, 2);

    }

    public static void setSale_1(Context context) {
        sale_1.putString(KasebContract.Sales.COLUMN_SALE_CODE, Utility.preInsertSaleCode(context));
        sale_1.putInt(KasebContract.Sales.COLUMN_CUSTOMER_ID, 1);
    }

    public static void setSale_2(Context context) {
        sale_2.putString(KasebContract.Sales.COLUMN_SALE_CODE, Utility.preInsertSaleCode(context));
        sale_2.putInt(KasebContract.Sales.COLUMN_CUSTOMER_ID, 1);
    }

    public static void setProduct_history(Context context) {
        product_history.putInt(KasebContract.ProductHistory.COLUMN_PRODUCT_ID, 1);
        product_history.putInt(KasebContract.ProductHistory.COLUMN_QUANTITY, 20);
        product_history.putString(KasebContract.ProductHistory.COLUMN_DATE, Utility.preInsertDate(context));
        product_history.putFloat(KasebContract.ProductHistory.COLUMN_COST, 1100);
        product_history.putFloat(KasebContract.ProductHistory.COLUMN_SALE_PRICE, product_1_sale_price);
    }

    public static void setDS1ProductItems(Context context) {
        ds1ProductItems.putInt(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID, 1);
        ds1ProductItems.putInt(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID, 1);
        ds1ProductItems.putInt(KasebContract.DetailSaleProducts.COLUMN_QUANTITY, 5);
        ds1ProductItems.putFloat(KasebContract.DetailSaleProducts.COLUMN_AMOUNT, 5 * product_1_sale_price);
    }

    public static void setDS2ProductItems(Context context) {
        ds2ProductItems.putInt(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID, 2);
        ds2ProductItems.putInt(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID, 1);
        ds2ProductItems.putInt(KasebContract.DetailSaleProducts.COLUMN_QUANTITY, 10);
        ds2ProductItems.putFloat(KasebContract.DetailSaleProducts.COLUMN_AMOUNT, 10 * product_1_sale_price);
    }

    public static void setDS1PaymentItems(Context context) {
        ds1PaymentItems.putInt(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID, 1);
        ds1PaymentItems.putInt(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, 1);
        ds1PaymentItems.putFloat(KasebContract.DetailSalePayments.COLUMN_AMOUNT, product_1_sale_price);
        ds1PaymentItems.putString(KasebContract.DetailSalePayments.COLUMN_DUE_DATE, Utility.preInsertDate(context));
        ds1PaymentItems.putString(KasebContract.DetailSalePayments.COLUMN_MODIFIED_DATE, Utility.preInsertDate(context));
    }

    public static void setDS2PaymentItems(Context context) {
        ds2PaymentItems.putInt(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID, 2);
        ds2PaymentItems.putInt(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, 3);
        ds2PaymentItems.putFloat(KasebContract.DetailSalePayments.COLUMN_AMOUNT, product_1_sale_price - 200);
        ds2PaymentItems.putString(KasebContract.DetailSalePayments.COLUMN_DUE_DATE, Utility.preInsertDate(context));
        ds2PaymentItems.putString(KasebContract.DetailSalePayments.COLUMN_MODIFIED_DATE, Utility.preInsertDate(context));
    }

    public static void setDS1TaxItems(Context context) {
        ds1TaxItems.putInt(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, 1);
        ds1TaxItems.putInt(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID, 1);
        ds1TaxItems.putFloat(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT, 0.09f * 5.0f * product_1_sale_price);
    }

    public static void setDS2TaxItems(Context context) {
        ds2TaxItems.putInt(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, 2);
        ds2TaxItems.putInt(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID, 1);
        ds2TaxItems.putFloat(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT, 0.09f * 10.0f * product_1_sale_price);
    }

    public static void setDetailSale_1(Context context) {
        detail_sale_1.putInt(KasebContract.DetailSale.COLUMN_SALE_ID, 1);
        detail_sale_1.putString(KasebContract.DetailSale.COLUMN_DATE, Utility.preInsertDate(context));
        detail_sale_1.putInt(KasebContract.DetailSale.COLUMN_ITEMS_NUMBER, 1);
        detail_sale_1.putInt(KasebContract.DetailSale.COLUMN_IS_BALANCED, 1);
        detail_sale_1.putFloat(KasebContract.DetailSale.COLUMN_SUB_TOTAL, 5.0f * product_1_sale_price);
        detail_sale_1.putFloat(KasebContract.DetailSale.COLUMN_TOTAL_TAX, 0.09f * 5.0f * product_1_sale_price);
        detail_sale_1.putFloat(KasebContract.DetailSale.COLUMN_TOTAL_DISCOUNT, 0.0f);
        detail_sale_1.putFloat(KasebContract.DetailSale.COLUMN_TOTAL_DUE, 5.0f * product_1_sale_price - 0.09f * 5.0f * product_1_sale_price);
        detail_sale_1.putFloat(KasebContract.DetailSale.COLUMN_TOTAL_PAID, 5.0f * product_1_sale_price - 0.09f * 5.0f * product_1_sale_price);
    }

    public static void setDetailSale_2(Context context) {
        detail_sale_2.putInt(KasebContract.DetailSale.COLUMN_SALE_ID, 2);
        detail_sale_2.putString(KasebContract.DetailSale.COLUMN_DATE, Utility.preInsertDate(context));
        detail_sale_2.putInt(KasebContract.DetailSale.COLUMN_ITEMS_NUMBER, 1);
        detail_sale_2.putInt(KasebContract.DetailSale.COLUMN_IS_BALANCED, 0);
        detail_sale_2.putFloat(KasebContract.DetailSale.COLUMN_SUB_TOTAL, 10.0f * product_1_sale_price);
        detail_sale_2.putFloat(KasebContract.DetailSale.COLUMN_TOTAL_TAX, 0.09f * 10.0f * product_1_sale_price);
        detail_sale_2.putFloat(KasebContract.DetailSale.COLUMN_TOTAL_DISCOUNT, 0.0f);
        detail_sale_2.putFloat(KasebContract.DetailSale.COLUMN_TOTAL_DUE, 10.0f * product_1_sale_price - 0.1f * 10.0f * product_1_sale_price);
        detail_sale_2.putFloat(KasebContract.DetailSale.COLUMN_TOTAL_PAID, 5.0f * product_1_sale_price - 0.1f * 5.0f * product_1_sale_price);
    }
}

