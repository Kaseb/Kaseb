package mjkarbasian.moshtarimadar.Sales;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mjkarbasian.moshtarimadar.Adapters.CostSaleProductAdapter;
import mjkarbasian.moshtarimadar.Adapters.CustomerAdapter;
import mjkarbasian.moshtarimadar.Adapters.TypesSettingAdapter;
import mjkarbasian.moshtarimadar.Dashboard;
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Data.KasebDbHelper;
import mjkarbasian.moshtarimadar.Data.KasebProvider;
import mjkarbasian.moshtarimadar.Helpers.RoundImageView;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;

public class DetailSaleInsert extends AppCompatActivity {

    //region declare Values
    Context mContext;
    KasebDbHelper mOpenHelper;
    SQLiteDatabase mDb;
    FragmentManager frm;
    Bundle bundleCardViewFragments;
    Uri insertUri;
    String mWhereStatement;
    int mNumberOfChooseProduct = 0;
    CheckBox isPassCheckBox;
    ListView modeList;
    ImageButton mImageButtonAddProduct;
    ScrollView mScrollViewDetailSale;
    RoundImageView customerAvatar;

    AlertDialog.Builder builder;
    AlertDialog dialogView;
    AlertDialog.Builder builderTour;
    AlertDialog dialogViewTour;

    SharedPreferences kasebSharedPreferences;
    SharedPreferences.Editor editor;

    Map<String, String> paymentMapRow;
    Map<String, String> taxMapRow;

    String _idOfProduct;
    String _nameOfProduct;

    CardViewProducts mCardViewProducts;
    CardViewPayments mCardViewPayments;
    CardViewTaxes mCardViewTaxes;

    ArrayList<Map<String, String>> mChosenProductListMap;
    ArrayList<Map<String, String>> mPaymentListMap;
    ArrayList<Map<String, String>> mTaxListMap;

    ContentValues[] itemsValuesArray;
    ContentValues[] paymentValuesArray;
    ContentValues[] taxValuesArray;
    ContentValues saleValues = new ContentValues();
    ContentValues detailSaleValues = new ContentValues();
    ContentValues itemsValues;
    ContentValues paymentValues;
    ContentValues taxValues;

    String[] mProjection;
    String[] mProjectionProductHistory;
    String[] mSelection;

    Long sTotalAmount = 0l;
    Long sTotalTax = 0l;
    Long sTotalDiscount = 0l;
    Long sFinalAmount = 0l;
    Long sPaidAmount = 0l;
    Long sBalanceAmount = 0l;
    Long customerId = 0l;
    Long cost = 0l;
    Long differneceOfBuy_Sale;

    Cursor mCursor1;
    Cursor mCursor2;

    Spinner paymentMethod;
    Spinner taxTypes;

    TextView totalAmountSummary;
    TextView taxSummary;
    TextView discountSummary;
    TextView finalAmountSummary;
    TextView paidSummary;
    TextView balanceSummary;
    TextView nameCustomer;
    TextView listItemsTitle;

    TextInputLayout saleCodeTextInputLayout;
    TextInputLayout saleDateTextInputLayout;
    TextInputLayout quantityTextInputLayout;
    TextInputLayout paymentAmountTextInputLayout;
    TextInputLayout paymentDueDateTextInputLayout;
    TextInputLayout taxDiscountAmountTextInputLayout;
    TextInputLayout taxDiscountPercentTextInputLayout;

    EditText saleCode;
    EditText saleDate;
    EditText paymentAmount;
    EditText paymentDueDate;
    EditText taxAmount;
    EditText taxPercent;
    EditText quantityEditText;

    TypesSettingAdapter cursorAdapter = null;
    CostSaleProductAdapter mAdapter = null;
    CustomerAdapter mCAdapter = null;
    //endregion declare Values

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sale_insert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //region Initialize Some Views & Values
        mContext = this;
        mOpenHelper = KasebProvider.mOpenHelper;
        mDb = mOpenHelper.getWritableDatabase();

        listItemsTitle = (TextView) findViewById(R.id.text_view_title_list_items);

        totalAmountSummary = (TextView) findViewById(R.id.card_detail_sale_summary_total_amount);
        taxSummary = (TextView) findViewById(R.id.card_detail_sale_summary_tax);
        discountSummary = (TextView) findViewById(R.id.card_detail_sale_summary_discount);
        finalAmountSummary = (TextView) findViewById(R.id.card_detail_sale_summary_final_amount);
        paidSummary = (TextView) findViewById(R.id.card_detail_sale_summary_payed);
        balanceSummary = (TextView) findViewById(R.id.card_detail_sale_summary_balance);
        nameCustomer = (TextView) findViewById(R.id.detail_sales_info_customer_name);
        customerAvatar = (RoundImageView) findViewById(R.id.detail_sale_customer_image);
        saleCode = (EditText) findViewById(R.id.detail_sales_info_sale_code);
        saleCodeTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_detail_sales_info_sale_code);
        saleCode.setText(Utility.preInsertSaleCode(this));
        saleCode.setSelection(saleCode.getText().length());

        mImageButtonAddProduct = (ImageButton) findViewById(R.id.content_detail_sale_insert_add_product_image_button);
        mScrollViewDetailSale = (ScrollView) findViewById(R.id.scroll_view_content_detail_sale_insert);

        saleDate = (EditText) findViewById(R.id.detail_sales_info_sale_date);
        saleDateTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_detail_sales_info_sale_date);
        saleDate.setText(Utility.preInsertDate(mContext));

        //region handle asterisk for necessary fields

        //region sale code
        Utility.setAsteriskToTextInputLayout(saleCodeTextInputLayout, getResources().getString(R.string.detail_sales_info_sale_code), true);
        //endregion sale code

        //region sale date
        Utility.setAsteriskToTextInputLayout(saleDateTextInputLayout, getResources().getString(R.string.hint_date), true);
        //endregion sale date

        //endregion handle asterisk for necessary fields

        saleCode.requestFocus();

        totalAmountSummary.setText(
                Utility.formatPurchase(mContext, Utility.DecimalSeperation(mContext, 0)));
        taxSummary.setText(
                Utility.formatPurchase(mContext, Utility.DecimalSeperation(mContext, 0)));
        discountSummary.setText(
                Utility.formatPurchase(mContext, Utility.DecimalSeperation(mContext, 0)));
        finalAmountSummary.setText(
                Utility.formatPurchase(mContext, Utility.DecimalSeperation(mContext, 0)));
        paidSummary.setText(
                Utility.formatPurchase(mContext, Utility.DecimalSeperation(mContext, 0)));
        balanceSummary.setText(
                Utility.formatPurchase(mContext, Utility.DecimalSeperation(mContext, 0)));

        bundleCardViewFragments = new Bundle();
        bundleCardViewFragments.putString("activity", "insert");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        frm = getSupportFragmentManager();

        mChosenProductListMap = new ArrayList<Map<String, String>>();
        mPaymentListMap = new ArrayList<Map<String, String>>();
        mTaxListMap = new ArrayList<Map<String, String>>();
        //endregion Initialize Some Views & Values

        //region Set Fragments
        mCardViewProducts = new CardViewProducts();
        mCardViewPayments = new CardViewPayments();
        mCardViewTaxes = new CardViewTaxes();

        mCardViewProducts.setArguments(bundleCardViewFragments);
        mCardViewPayments.setArguments(bundleCardViewFragments);
        mCardViewTaxes.setArguments(bundleCardViewFragments);

        frm.beginTransaction().replace(R.id.my_container_1, mCardViewProducts, "Frag_CardViewProducts_tag").commit();
        frm.beginTransaction().replace(R.id.my_container_2, mCardViewPayments, "Frag_CardViewPayments_tag").commit();
        frm.beginTransaction().replace(R.id.my_container_3, mCardViewTaxes, "Frag_CardViewTaxes_tag").commit();
        //endregion Set Fragments

        //region handle sharepreference
        kasebSharedPreferences = getSharedPreferences(getString(R.string.kasebPreference), MODE_PRIVATE);
        editor = kasebSharedPreferences.edit();
        //endregion handle sharepreference

        //region create alertdialog tour
        builderTour = new AlertDialog.Builder(DetailSaleInsert.this)
                .setPositiveButton(R.string.finish_tour, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).setNegativeButton(R.string.back_tour, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).setTitle(R.string.title_sale_insert);

        dialogViewTour = builderTour.create();
        //endregion create alertdialog tour

        try {
            if (kasebSharedPreferences.getBoolean("getStarted", false)) {

                dialogViewTour.setMessage(getResources().getString(R.string.tour_text_sale_insert));
                dialogViewTour.show();

                dialogViewTour.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialogViewTour.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //region back tour
                        finish();

                        dialogViewTour.dismiss();
                        //endregion back tour
                    }
                });

                dialogViewTour.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //region end tour
                        editor.putBoolean("getStarted", false);
                        editor.apply();

                        dialogViewTour.dismiss();

                        Intent intent = new Intent(DetailSaleInsert.this, Dashboard.class);
                        finish();
                        startActivity(intent);
                        Utility.setActivityTransition(DetailSaleInsert.this);
                        //endregion end tour
                    }
                });

                dialogViewTour.setCancelable(false);
                dialogViewTour.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //region end tour
                        editor.putBoolean("getStarted", false);
                        editor.apply();
                        //endregion end tour
                    }
                });

            }
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_sale_insert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.save:

                //region Save
                //region SetValues
                saleCode = (EditText) findViewById(R.id.detail_sales_info_sale_code);
                //endregion

                if (checkValidityWithChangeColorOfHelperText()) {

                    mDb.beginTransaction();

                    //region Insert Sale
                    String mSaleId = "0";

                    saleValues.put(KasebContract.Sales.COLUMN_CUSTOMER_ID, customerId);
                    saleValues.put(KasebContract.Sales.COLUMN_IS_DELETED, 0);
                    saleValues.put(KasebContract.Sales.COLUMN_SALE_CODE, saleCode.getText().toString());

                    insertUri = getContentResolver().insert(
                            KasebContract.Sales.CONTENT_URI,
                            saleValues
                    );

                    mSaleId = insertUri.getLastPathSegment();

                    saleCode.setEnabled(false);
                    //endregion

                    //region Insert DetailSale
                    detailSaleValues.put(KasebContract.DetailSale.COLUMN_DATE, saleDate.getText().toString());
                    detailSaleValues.put(KasebContract.DetailSale.COLUMN_IS_BALANCED, sFinalAmount.equals(sPaidAmount));
                    detailSaleValues.put(KasebContract.DetailSale.COLUMN_ITEMS_NUMBER, mChosenProductListMap.size());
                    detailSaleValues.put(KasebContract.DetailSale.COLUMN_SALE_ID, mSaleId);
                    detailSaleValues.put(KasebContract.DetailSale.COLUMN_SUB_TOTAL,
                            Utility.convertFarsiNumbersToDecimal(String.valueOf(sTotalAmount)));
                    detailSaleValues.put(KasebContract.DetailSale.COLUMN_TOTAL_DISCOUNT,
                            Utility.convertFarsiNumbersToDecimal(String.valueOf(sTotalDiscount)));
                    detailSaleValues.put(KasebContract.DetailSale.COLUMN_TOTAL_DUE,
                            Utility.convertFarsiNumbersToDecimal(String.valueOf(sFinalAmount)));
                    detailSaleValues.put(KasebContract.DetailSale.COLUMN_TOTAL_PAID,
                            Utility.convertFarsiNumbersToDecimal(String.valueOf(sPaidAmount)));
                    detailSaleValues.put(KasebContract.DetailSale.COLUMN_TOTAL_TAX,
                            Utility.convertFarsiNumbersToDecimal(String.valueOf(sTotalTax)));

                    insertUri = getContentResolver().insert(
                            KasebContract.DetailSale.CONTENT_URI,
                            detailSaleValues
                    );
                    //endregion

                    //region Insert DetailSaleProducts
                    int count = mChosenProductListMap.size();
                    itemsValuesArray = new ContentValues[count];

                    for (int i = 0; i < count; i++) {
                        itemsValues = new ContentValues();

                        itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_AMOUNT,
                                Utility.convertFarsiNumbersToDecimal(String.valueOf(
                                        Long.valueOf(mChosenProductListMap.get(i).get("price").toString()) *
                                                Long.valueOf(mChosenProductListMap.get(i).get("quantity").toString()))));

                        itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID, insertUri.getLastPathSegment());
                        itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID, mChosenProductListMap.get(i).get("id").toString());
                        itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_QUANTITY,
                                Utility.convertFarsiNumbersToDecimal(mChosenProductListMap.get(i).get("quantity").toString()));

                        itemsValuesArray[i] = itemsValues;
                    }

                    getContentResolver().bulkInsert(
                            KasebContract.DetailSaleProducts.CONTENT_URI,
                            itemsValuesArray
                    );
                    //endregion Insert DetailSaleProducts

                    //region Insert DetailSalePayments
                    count = mPaymentListMap.size();
                    paymentValuesArray = new ContentValues[count];

                    for (int i = 0; i < count; i++) {
                        paymentValues = new ContentValues();

                        paymentValues.put(KasebContract.DetailSalePayments.COLUMN_DUE_DATE, mPaymentListMap.get(i).get("duedate").toString());
                        paymentValues.put(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID, insertUri.getLastPathSegment());
                        paymentValues.put(KasebContract.DetailSalePayments.COLUMN_AMOUNT,
                                Utility.convertFarsiNumbersToDecimal(String.valueOf(
                                        Long.valueOf(mPaymentListMap.get(i).get("amount").toString()))));
                        paymentValues.put(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, mPaymentListMap.get(i).get("id").toString());
                        paymentValues.put(KasebContract.DetailSalePayments.COLUMN_IS_PASS, Boolean.parseBoolean(mPaymentListMap.get(i).get("isPass")));

                        paymentValuesArray[i] = paymentValues;
                    }

                    getContentResolver().bulkInsert(
                            KasebContract.DetailSalePayments.CONTENT_URI,
                            paymentValuesArray
                    );
                    //endregion Insert DetailSalePayments

                    //region Insert DetailSaleTaxes
                    count = mTaxListMap.size();
                    taxValuesArray = new ContentValues[count];

                    for (int i = 0; i < count; i++) {
                        taxValues = new ContentValues();

                        taxValues.put(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, insertUri.getLastPathSegment());
                        taxValues.put(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT,
                                Utility.convertFarsiNumbersToDecimal(String.valueOf(
                                        Long.valueOf(mTaxListMap.get(i).get("amount").toString()))));
                        taxValues.put(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID, mTaxListMap.get(i).get("id").toString());

                        taxValuesArray[i] = taxValues;
                    }

                    getContentResolver().bulkInsert(
                            KasebContract.DetailSaleTaxes.CONTENT_URI,
                            taxValuesArray
                    );
                    //endregion Insert DetailSaleTaxes

                    //just a message to show everything are under control
                    Toast.makeText(DetailSaleInsert.this, getApplicationContext().getResources().getString(R.string.msg_insert_succeed), Toast.LENGTH_LONG).show();

                    mDb.setTransactionSuccessful();
                    mDb.endTransaction();

                    //region Print Factor
                    ArrayList<Long> mSummaryOfInvoice = new ArrayList<Long>();
                    mSummaryOfInvoice.add(sTotalAmount);
                    mSummaryOfInvoice.add(sTotalTax);
                    mSummaryOfInvoice.add(sTotalDiscount);
                    mSummaryOfInvoice.add(sPaidAmount);
                    mSummaryOfInvoice.add(sBalanceAmount);

                    try {
                        Utility.printInvoice(mContext, saleDate.getText().toString(), saleCode.getText().toString(),
                                nameCustomer.getText().toString(),
                                mSummaryOfInvoice, customerId, insertUri.getLastPathSegment().toString(),
                                mChosenProductListMap, mTaxListMap, mPaymentListMap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(DetailSaleInsert.this, R.string.problem_in_pdf_reader, Toast.LENGTH_LONG).show();
                    }
                    //endregion Print Factor

                    getHelperText();
                    finish();
                    break;
                }
                //endregion Save
        }
        return super.onOptionsItemSelected(item);
    }

    public void fab_detail_sale_add_customer(View v) {
        mProjection = new String[]{
                KasebContract.Customers._ID,
                KasebContract.Customers.COLUMN_FIRST_NAME,
                KasebContract.Customers.COLUMN_LAST_NAME,
                KasebContract.Customers.COLUMN_STATE_ID,
                KasebContract.Customers.COLUMN_CUSTOMER_PICTURE};

        builder = new AlertDialog.Builder(DetailSaleInsert.this)
                .setView(getLayoutInflater().inflate(R.layout.dialog_add_customers_for_sale, null))
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
        builder.setTitle(R.string.fab_add_customer);

        dialogView = builder.create();
        dialogView.show();

        modeList = (ListView) dialogView.findViewById(R.id.list_view_customers_for_detail_sale);
        mCAdapter = new CustomerAdapter(
                DetailSaleInsert.this,
                getContentResolver().query(
                        KasebContract.Customers.CONTENT_URI,
                        mProjection,
                        null,
                        null,
                        null),
                0);
        modeList.setAdapter(mCAdapter);
        modeList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {

                        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                        if (cursor != null) {
                            nameCustomer.setText(cursor.getString(cursor.getColumnIndex(KasebContract.Customers.COLUMN_FIRST_NAME))
                                    + " " + cursor.getString(cursor.getColumnIndex(KasebContract.Customers.COLUMN_LAST_NAME)));
                            customerId = Long.parseLong(cursor.getString(cursor.getColumnIndex(KasebContract.Customers._ID)));
                            final byte[] imagegBytes = cursor.getBlob(cursor.getColumnIndex(KasebContract.Customers.COLUMN_CUSTOMER_PICTURE));
                            try {
                                Boolean mWhat = false;
                                if (imagegBytes == null)
                                    mWhat = true;
                                else if (imagegBytes.length == 0)
                                    mWhat = true;

                                if (mWhat)
                                    customerAvatar.setImageDrawable(mContext.getResources().getDrawable(
                                            mContext.getResources().getIdentifier("@drawable/kaseb_pic", null, mContext.getPackageName())));
                                else {
                                    customerAvatar.setImageBitmap(BitmapFactory.decodeByteArray(imagegBytes, 0, imagegBytes.length));
                                }
                            } catch (Exception e) {
                            }
                            dialogView.dismiss();
                        }
                        cursor.close();

                        nameCustomer.setError(null);
                    }
                }
        );
    }

    public void fab_detail_sale_add_product(View v) {

        //region List all products
        builder = new AlertDialog.Builder(DetailSaleInsert.this)
                .setView(getLayoutInflater().inflate(R.layout.dialog_add_product_for_sale, null))
                .setNegativeButton(R.string.discard_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialogView.dismiss();
                    }
                }).setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .setTitle(R.string.fab_add_product)
                .setMessage(R.string.less_than_stock_explain_text);

        dialogView = builder.create();
        dialogView.show();

        dialogView.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = false;

                //region insert product
                if (!quantityEditText.getText().toString().equals("") && !quantityEditText.getText().toString().equals(null)) {
                    String num = quantityEditText.getText().toString();

                    if (differneceOfBuy_Sale >= Long.valueOf(num)) {
                        Map<String, String> mProductsRowMap = new HashMap<>();

                        mProductsRowMap.put("id", _idOfProduct);
                        mProductsRowMap.put("name", _nameOfProduct);
                        mProductsRowMap.put("quantity", num);
                        mProductsRowMap.put("price", String.valueOf(cost));

                        sTotalAmount += cost * Long.valueOf(num);

                        int mIndex = Utility.indexOfRowsInMap(mChosenProductListMap, "id", _idOfProduct);

                        if (mIndex == -1) {
                            mChosenProductListMap.add(mProductsRowMap);
                            mCardViewProducts.getChosenProductAdapter(mChosenProductListMap);

                            wantToCloseDialog = true;
                        }
                    } else {
                        quantityTextInputLayout.setError(getResources().getString(R.string.not_enough_stock));
                        quantityEditText.setSelectAllOnFocus(true);
                        quantityEditText.selectAll();
                        quantityEditText.requestFocus();
                    }
                } else {
                    quantityTextInputLayout.setError(getResources().getString(R.string.example_quantity));
                    quantityEditText.setSelectAllOnFocus(true);
                    quantityEditText.selectAll();
                    quantityEditText.requestFocus();
                }
                //endregion insert product

                if (wantToCloseDialog)
                    dialogView.dismiss();
            }
        });
        //endregion List all products

        //region Set Adapter To Dialog
        mProjection = new String[]{
                KasebContract.Products._ID,
                KasebContract.Products.COLUMN_PRODUCT_NAME,
                KasebContract.Products.COLUMN_PRODUCT_CODE};

        mNumberOfChooseProduct = mChosenProductListMap.size();
        mSelection = new String[(mNumberOfChooseProduct > 0 ? mNumberOfChooseProduct : 1)];
        for (int i = 0; i < mNumberOfChooseProduct; i++) {
            mSelection[i] = mChosenProductListMap.get(i).get("id");
        }

        if (mSelection[0] == null)
            mSelection[0] = "-1";

        mWhereStatement = KasebContract.Products._ID + " NOT IN (" +
                Utility.makePlaceholders((mNumberOfChooseProduct > 0 ? mNumberOfChooseProduct : 1)) + ")";

        modeList = (ListView) dialogView.findViewById(R.id.list_view_product_for_sale_number);
        mAdapter = new CostSaleProductAdapter(
                DetailSaleInsert.this,
                getContentResolver().query(
                        KasebContract.Products.CONTENT_URI,
                        mProjection,
                        mWhereStatement,
                        mSelection,
                        null),
                0,
                "product");
        modeList.setAdapter(mAdapter);

        quantityEditText = (EditText) dialogView.findViewById(R.id.add_number_of_product_for_sale_number);
        quantityTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_number_of_product_for_sale_number);
        //endregion Set Adapter To Dialog

        //region ClickListener ListView Dialog
        modeList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (int i = 0; i < parent.getChildCount(); i++) {
                            parent.getChildAt(i).setBackgroundColor(0x000000);
                        }
                        view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                        if (cursor != null) {
                            _idOfProduct = cursor.getString(
                                    cursor.getColumnIndex(KasebContract.Products._ID));

                            differneceOfBuy_Sale = Utility.checkNumberOfProductsForDetailSale(DetailSaleInsert.this,
                                    0l, "SaleInsert", Long.parseLong(_idOfProduct));

                            _nameOfProduct = cursor.getString(
                                    cursor.getColumnIndex(KasebContract.Products.COLUMN_PRODUCT_NAME));

                            mProjectionProductHistory = new String[]{
                                    KasebContract.ProductHistory._ID,
                                    KasebContract.ProductHistory.COLUMN_SALE_PRICE};

                            Cursor mCursor = getContentResolver().query(
                                    KasebContract.ProductHistory.aProductHistory(Long.parseLong(_idOfProduct)),
                                    mProjectionProductHistory,
                                    null,
                                    null,
                                    null);

                            if (mCursor != null)
                                if (mCursor.moveToLast())
                                    cost = mCursor.getLong(mCursor.getColumnIndex(KasebContract.ProductHistory.COLUMN_SALE_PRICE));

                            quantityTextInputLayout.setHint(getString(R.string.stock_product) + " " + differneceOfBuy_Sale);
                            quantityEditText.setVisibility(View.VISIBLE);

                            quantityEditText.setSelectAllOnFocus(true);
                            quantityEditText.selectAll();
                            quantityEditText.requestFocus();
                        }
                    }
                }
        );
        //endregion ClickListener ListView Dialog
    }

    public void fab_detail_sale_add_payment(View v) {

        //region create payment dialog
        builder = new AlertDialog.Builder(DetailSaleInsert.this)
                .setView(getLayoutInflater().inflate(R.layout.dialog_add_payment_for_sale, null))
                .setNegativeButton(R.string.discard_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialogView.dismiss();
                    }
                }).setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .setTitle(R.string.fab_add_payment);
        dialogView = builder.create();
        dialogView.show();

        dialogView.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = false;

                //region insert payment
                paymentMapRow.put("amount", paymentAmount.getText().toString());
                paymentMapRow.put("duedate", paymentDueDate.getText().toString());

                try {
                    Float amount = Utility.createFloatNumberWithString(DetailSaleInsert.this, paymentAmount.getText().toString());

                    if (amount > sFinalAmount) {
                        paymentAmountTextInputLayout.setError(getResources().getString(R.string.more_than_balance_amount));
                        paymentAmount.setSelectAllOnFocus(true);
                        paymentAmount.selectAll();
                        paymentAmount.requestFocus();
                        return;
                    } else if (!Utility.checkForValidityForEditTextDate(DetailSaleInsert.this, paymentDueDate)) {
                        paymentDueDateTextInputLayout.setError(getResources().getString(R.string.example_date));
                        paymentDueDate.setSelectAllOnFocus(true);
                        paymentDueDate.selectAll();
                        paymentDueDate.requestFocus();
                        return;
                    }

                    paymentAmountTextInputLayout.setError(null);
                    paymentDueDateTextInputLayout.setError(null);

                    if (!paymentMapRow.get("type").equals(getResources().getString(R.string.payment_method_cheque)))
                        paymentMapRow.put("isPass", "true");
                    else
                        paymentMapRow.put("isPass", String.valueOf(isPassCheckBox.isChecked()));

                    mPaymentListMap.add(paymentMapRow);
                    mCardViewPayments.getPaymentAdapter(mPaymentListMap);

                    wantToCloseDialog = true;

                } catch (Exception e) {
                    paymentAmountTextInputLayout.setError(getResources().getString(R.string.example_price));
                    paymentAmount.setSelectAllOnFocus(true);
                    paymentAmount.selectAll();
                    paymentAmount.requestFocus();
                }
                //endregion insert payment

                if (wantToCloseDialog)
                    dialogView.dismiss();
            }
        });
        //endregion create payment dialog

        //region declare views in dialog
        paymentMapRow = new HashMap<>();

        paymentAmount = (EditText) dialogView.findViewById(R.id.dialog_add_payment_for_sale_payment_amount);
        paymentDueDate = (EditText) dialogView.findViewById(R.id.dialog_add_payment_for_sale_input_due_date);
        paymentDueDate.setText(Utility.preInsertDate(mContext));
        isPassCheckBox = (CheckBox) dialogView.findViewById(R.id.dialog_add_payment_is_passed_check_box);
        paymentAmountTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_dialog_add_payment_for_sale_payment_amount);
        paymentDueDateTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_dialog_add_payment_for_sale_input_due_date);

        paymentMethod = (Spinner) dialogView.findViewById(R.id.input_payment_method_spinner);
        mCursor1 = getContentResolver().query(KasebContract.PaymentMethods.CONTENT_URI
                , null, null, null, null);

        cursorAdapter = new TypesSettingAdapter(mContext,
                mCursor1,
                0,
                KasebContract.PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER);

        paymentMethod.setAdapter(cursorAdapter);

        paymentMethod.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View
                            arg1, int arg2, long arg3) {
                        Cursor mCursor3 = (Cursor) paymentMethod.getSelectedItem();

                        paymentMapRow.put("id", mCursor3.getString(
                                mCursor3.getColumnIndex(KasebContract.PaymentMethods._ID)));

                        paymentMapRow.put("type", mCursor3.getString(
                                mCursor3.getColumnIndex(KasebContract.PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER)));
                        LinearLayout isPassed = (LinearLayout) dialogView.findViewById(R.id.dialog_add_payment_is_passed_view);

                        if (mCursor3.getString(mCursor3.getColumnIndex(KasebContract.PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER))
                                .equals(getResources().getString(R.string.cheque_title)))
                            isPassed.setVisibility(View.VISIBLE);
                        else
                            isPassed.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    }
                });
        //endregion declare views in dialog
    }

    public void fab_detail_sale_add_taxDiscount(View v) {

        //region create taxDiscount dialog
        builder = new AlertDialog.Builder(DetailSaleInsert.this)
                .setView(getLayoutInflater().inflate(R.layout.dialog_add_tax_for_sale, null))
                .setNegativeButton(R.string.discard_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialogView.dismiss();
                    }
                }).setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .setTitle(R.string.fab_add_product);
        dialogView = builder.create();
        dialogView.show();

        dialogView.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = false;

                //region insert taxDiscount
                taxMapRow.put("amount", taxAmount.getText().toString());
                taxMapRow.put("percent", taxPercent.getText().toString());

                try {
                    Float amount = Utility.createFloatNumberWithString(DetailSaleInsert.this, taxAmount.getText().toString());

                    if (amount > sTotalAmount) {
                        taxDiscountAmountTextInputLayout.setError(getResources().getString(R.string.more_than_total_amount));
                        taxAmount.setSelectAllOnFocus(true);
                        taxAmount.selectAll();
                        taxAmount.requestFocus();
                        return;
                    } else if (taxPercent.getText().toString().length() == 0)
                        taxMapRow.put("percent", String.format("%.2f", 100 * amount / sTotalAmount));

                    taxDiscountAmountTextInputLayout.setError(null);

                    mTaxListMap.add(taxMapRow);
                    mCardViewTaxes.getTaxAdapter(mTaxListMap);

                    wantToCloseDialog = true;

                } catch (Exception e) {
                    taxDiscountAmountTextInputLayout.setError(getResources().getString(R.string.example_price));
                    taxAmount.setSelectAllOnFocus(true);
                    taxAmount.selectAll();
                    taxAmount.requestFocus();
                }
                //endregion insert taxDiscount

                if (wantToCloseDialog)
                    dialogView.dismiss();
            }
        });
        //endregion create taxDiscount dialog

        //region declare views in dialog
        taxMapRow = new HashMap<>();

        taxAmount = (EditText) dialogView.findViewById(R.id.dialog_add_tax_for_sale_tax_discount_amount);
        taxPercent = (EditText) dialogView.findViewById(R.id.dialog_add_tax_for_sale_tax_discount_percent);
        taxDiscountAmountTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_dialog_add_tax_for_sale_tax_discount_amount);
        taxDiscountPercentTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_dialog_add_tax_for_sale_tax_discount_percent);

        taxPercent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Float percent = Float.valueOf(taxPercent.getText().toString());
                    if (percent > 100)
                        taxPercent.setText("100");
                } catch (Exception e) {
                    taxAmount.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Float percent = Utility.createFloatNumberWithString(DetailSaleInsert.this, taxPercent.getText().toString());
                    taxAmount.setText(String.format("%.0f", Float.valueOf(percent * sTotalAmount / 100)));
                } catch (Exception e) {
                    taxAmount.setText("");
                }
            }
        });

        taxTypes = (Spinner) dialogView.findViewById(R.id.dialog_add_tax_input_tax_type_spinner);
        mCursor2 = getContentResolver().query(KasebContract.TaxTypes.CONTENT_URI
                , null, null, null, null);

        cursorAdapter = new TypesSettingAdapter(mContext,
                mCursor2,
                0,
                KasebContract.TaxTypes.COLUMN_TAX_TYPE_POINTER);
        taxTypes.setAdapter(cursorAdapter);

        taxTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Cursor mCursor5 = (Cursor) taxTypes.getSelectedItem();

                taxMapRow.put("id", mCursor5.getString(
                        mCursor5.getColumnIndex(KasebContract.TaxTypes._ID)));

                taxMapRow.put("type", mCursor5.getString(
                        mCursor5.getColumnIndex(KasebContract.TaxTypes.COLUMN_TAX_TYPE_POINTER)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }

        });
        //endregion declare views in dialog
    }

    public void setValuesOfFactor() {
        sTotalAmount = 0l;
        for (int i = 0; i < mChosenProductListMap.size(); i++) {
            sTotalAmount +=
                    Long.valueOf(mChosenProductListMap.get(i).get("quantity").toString()) *
                            Long.valueOf(mChosenProductListMap.get(i).get("price").toString());
        }

        totalAmountSummary.setText(
                Utility.formatPurchase(
                        mContext,
                        Utility.DecimalSeperation(mContext, sTotalAmount)));

        sTotalDiscount = 0l;
        sTotalTax = 0l;
        for (int i = 0; i < mTaxListMap.size(); i++) {
            String type = mTaxListMap.get(i).get("type").toString();

            if (type.equals(getResources().getString(R.string.tax_types_discount))
                    || type.equals(getResources().getString(R.string.discount_title)))
                sTotalDiscount += Long.valueOf(mTaxListMap.get(i).get("amount").toString());
            else
                sTotalTax += Long.valueOf(mTaxListMap.get(i).get("amount").toString());
        }

        taxSummary.setText(
                Utility.formatPurchase(
                        mContext,
                        Utility.DecimalSeperation(mContext, sTotalTax)));

        discountSummary.setText(
                Utility.formatPurchase(
                        mContext,
                        Utility.DecimalSeperation(mContext, sTotalDiscount)));


        sFinalAmount = sTotalAmount + sTotalTax - sTotalDiscount;

        finalAmountSummary.setText(
                Utility.formatPurchase(
                        mContext,
                        Utility.DecimalSeperation(mContext, sFinalAmount)));

        sPaidAmount = 0l;
        for (int i = 0; i < mPaymentListMap.size(); i++) {
            if (mPaymentListMap.get(i).get("isPass").equals("true"))
                sPaidAmount += Long.valueOf(mPaymentListMap.get(i).get("amount").toString());
        }

        paidSummary.setText(
                Utility.formatPurchase(
                        mContext,
                        Utility.DecimalSeperation(mContext, sPaidAmount)));

        sBalanceAmount = sFinalAmount - sPaidAmount;

        balanceSummary.setText(
                Utility.formatPurchase(
                        mContext,
                        Utility.DecimalSeperation(mContext, sBalanceAmount)));

        if (sFinalAmount > 0)
            finalAmountSummary.setError(null);

        if (sBalanceAmount > 0)
            balanceSummary.setError(null);
    }

    private void getHelperText() {

        saleCodeTextInputLayout.setError(null);
        saleDateTextInputLayout.setError(null);
    }

    // this method check the validation and correct entries. its check fill first and then check the validation rules.
    private boolean checkValidityWithChangeColorOfHelperText() {

        if (!Utility.checkForValidityForEditTextNullOrEmptyAndItterative(
                mContext, saleCode, saleCodeTextInputLayout, KasebContract.Sales.CONTENT_URI,
                KasebContract.Sales.COLUMN_SALE_CODE + " = ? ",
                KasebContract.Sales._ID, new String[]{saleCode.getText().toString()})) {
            saleCodeTextInputLayout.setError(String.format("%s %s",
                    getResources().getString(R.string.example_sale_code),
                    getResources().getString(R.string.non_repetitive)));
            return false;
        }

        if (!Utility.checkForValidityForEditTextDate(DetailSaleInsert.this, saleDate)) {
            saleDateTextInputLayout.setError(getResources().getString(R.string.example_date));
            saleDate.setSelectAllOnFocus(true);
            saleDate.selectAll();
            saleDate.requestFocus();
            return false;
        } else
            saleDateTextInputLayout.setError(null);

        if (customerId == 0) {
            Utility.setErrorForTextView(nameCustomer);
            Snackbar.make(findViewById(android.R.id.content), R.string.choose_customer_error_for_sale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(getResources().getString(R.string.ok_button), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mScrollViewDetailSale.scrollTo(0, 0);
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.colorAccent))
                    .show();
            return false;
        } else if (mChosenProductListMap.size() == 0) {
            Snackbar.make(findViewById(android.R.id.content), R.string.validity_error_dsale_select_product, Snackbar.LENGTH_INDEFINITE)
                    .setAction(getResources().getString(R.string.ok_button), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mImageButtonAddProduct.requestFocus();
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.colorAccent))
                    .show();
            return false;
        } else if (sFinalAmount < 0) {
            Utility.setErrorForTextView(finalAmountSummary);
            Snackbar.make(findViewById(android.R.id.content), R.string.final_amount_not_minus_number, Snackbar.LENGTH_INDEFINITE)
                    .setAction(getResources().getString(R.string.ok_button), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mScrollViewDetailSale.scrollTo(0, mScrollViewDetailSale.getBottom());
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.colorAccent))
                    .show();
            return false;
        } else if (sBalanceAmount < 0) {
            Utility.setErrorForTextView(balanceSummary);
            Snackbar.make(findViewById(android.R.id.content), R.string.balance_amount_not_minus_number, Snackbar.LENGTH_INDEFINITE)
                    .setAction(getResources().getString(R.string.ok_button), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mScrollViewDetailSale.scrollTo(0, mScrollViewDetailSale.getBottom());
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.colorAccent))
                    .show();
            return false;
        }

        return true;
    }
}