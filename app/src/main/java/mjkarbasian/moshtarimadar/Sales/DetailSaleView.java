package mjkarbasian.moshtarimadar.Sales;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
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
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Data.KasebDbHelper;
import mjkarbasian.moshtarimadar.Data.KasebProvider;
import mjkarbasian.moshtarimadar.Helpers.RoundImageView;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;

public class DetailSaleView extends AppCompatActivity {

    //region declare Values
    Context mContext;
    KasebDbHelper mOpenHelper;
    SQLiteDatabase mDb;
    FragmentManager frm;
    Bundle bundleCardViewFragments;
    int mNumberOfChooseProduct = 0;
    boolean mSaved = true;
    LinearLayout addCustomerLayout;
    CheckBox isPassCheckBox;
    ImageButton mImageButtonAddProduct;
    ScrollView mScrollViewDetailSale;

    AlertDialog.Builder builder;
    AlertDialog dialogView;

    Map<String, String> paymentMapRow;
    Map<String, String> taxMapRow;

    MenuItem saveItem;
    MenuItem editItem;

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
    String[] mProjectionInitialize;
    String[] mSelectionInitialize;

    String mWhereStatement;
    String mWhereStatementInitialize;
    String _idOfProduct;
    String _nameOfProduct;

    Long sTotalAmount = 0l;
    Long sTotalTax = 0l;
    Long sTotalDiscount = 0l;
    Long sFinalAmount = 0l;
    Long sPaidAmount = 0l;
    Long sBalanceAmount = 0l;
    Long customerId;
    Long cost = 0l;
    Long whichSaleId;
    Long whichDetailSaleId;
    Long differneceOfBuy_Sale;

    Cursor mCursor1;
    Cursor mCursor2;
    Cursor mCursorInitialize;

    ImageButton imageButtonProducts;
    ImageButton imageButtonPayments;
    ImageButton imageButtonTaxes;

    Spinner paymentMethod;
    Spinner taxTypes;

    TextView totalAmountSummary;
    TextView taxSummary;
    TextView discountSummary;
    TextView finalAmountSummary;
    TextView paidSummary;
    TextView balanceSummary;
    TextView nameCustomer;

    EditText paymentAmount;
    EditText paymentDueDate;
    EditText taxAmount;
    EditText taxPercent;
    EditText saleCode;
    EditText saleDate;
    EditText quantityEditText;

    TextInputLayout saleCodeTextInputLayout;
    TextInputLayout saleDateTextInputLayout;
    TextInputLayout quantityTextInputLayout;
    TextInputLayout paymentAmountTextInputLayout;
    TextInputLayout paymentDueDateTextInputLayout;
    TextInputLayout taxDiscountAmountTextInputLayout;
    TextInputLayout taxDiscountPercentTextInputLayout;

    ListView modeList;
    LinearLayout mProductLinearLayout;
    LinearLayout mPaymentLinearLayout;
    LinearLayout mTaxLinearLayout;

    TypesSettingAdapter cursorAdapter = null;
    CostSaleProductAdapter mAdapter = null;
    CustomerAdapter mCAdapter = null;
    private RoundImageView customerAvatar;
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

        totalAmountSummary = (TextView) findViewById(R.id.card_detail_sale_summary_total_amount);
        taxSummary = (TextView) findViewById(R.id.card_detail_sale_summary_tax);
        discountSummary = (TextView) findViewById(R.id.card_detail_sale_summary_discount);
        finalAmountSummary = (TextView) findViewById(R.id.card_detail_sale_summary_final_amount);
        paidSummary = (TextView) findViewById(R.id.card_detail_sale_summary_payed);
        balanceSummary = (TextView) findViewById(R.id.card_detail_sale_summary_balance);
        nameCustomer = (TextView) findViewById(R.id.detail_sales_info_customer_name);
        customerAvatar = (RoundImageView) findViewById(R.id.detail_sale_customer_image);

        bundleCardViewFragments = new Bundle();
        bundleCardViewFragments.putString("activity", "view");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        frm = getSupportFragmentManager();

        saleCode = (EditText) findViewById(R.id.detail_sales_info_sale_code);
        saleCodeTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_detail_sales_info_sale_code);
        saleDate = (EditText) findViewById(R.id.detail_sales_info_sale_date);

        saleDateTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_detail_sales_info_sale_date);
        addCustomerLayout = (LinearLayout) findViewById(R.id.content_detail_sale_insert_linear_layout_add_customer);

        mImageButtonAddProduct = (ImageButton) findViewById(R.id.content_detail_sale_insert_add_product_image_button);
        mScrollViewDetailSale = (ScrollView) findViewById(R.id.scroll_view_content_detail_sale_insert);

        //endregion Initialize Some Views & Values

        //region Get SaleId
        whichSaleId = Long.parseLong(getIntent().getExtras().get("saleId").toString());
        saleCode.setText(getIntent().getExtras().get("saleCode").toString());
        customerId = Long.valueOf(getIntent().getExtras().get("customerId").toString());
        //endregion Get SaleId

        //region Get DetailSaleId
        mProjectionInitialize = new String[]{
                KasebContract.DetailSale._ID,
                KasebContract.DetailSale.COLUMN_DATE};

        mCursorInitialize = getContentResolver().query(
                KasebContract.DetailSale.saleDetailSale(whichSaleId),
                mProjectionInitialize,
                null,
                null,
                null);

        if (mCursorInitialize != null) {
            if (mCursorInitialize.moveToFirst()) {
                whichDetailSaleId = mCursorInitialize.getLong(mCursorInitialize.getColumnIndex(KasebContract.DetailSale._ID));
                saleDate.setText(mCursorInitialize.getString(mCursorInitialize.getColumnIndex(KasebContract.DetailSale.COLUMN_DATE)));
                bundleCardViewFragments.putLong("detailSaleId", whichDetailSaleId);
            }
        }
        //endregion Get DetailSaleId

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
    }

    @Override
    protected void onStart() {
        super.onStart();

        //region Initialize Some Views
        mChosenProductListMap = new ArrayList<Map<String, String>>();
        mPaymentListMap = new ArrayList<Map<String, String>>();
        mTaxListMap = new ArrayList<Map<String, String>>();

        imageButtonProducts = (ImageButton) findViewById(R.id.content_detail_sale_insert_add_product_image_button);
        imageButtonPayments = (ImageButton) findViewById(R.id.content_detail_sale_insert_add_payment_image_button);
        imageButtonTaxes = (ImageButton) findViewById(R.id.content_detail_sale_insert_add_taxDiscount_image_button);

        mProductLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_fragment_card_view_products);
        mPaymentLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_fragment_card_view_payments);
        mTaxLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_fragment_card_view_taxes);
        //endregion Initialize Some Views

        //region Disable Views
        saleCode.setEnabled(false);
        saleDate.setEnabled(false);

        addCustomerLayout.setEnabled(false);
        imageButtonProducts.setEnabled(false);
        imageButtonPayments.setEnabled(false);
        imageButtonTaxes.setEnabled(false);

        mSaved = true;
        //endregion Disable Views

        //region Get Products
        mProjectionInitialize = new String[]{
                KasebContract.DetailSaleProducts._ID,
                KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID,
                KasebContract.DetailSaleProducts.COLUMN_QUANTITY,
                KasebContract.DetailSaleProducts.COLUMN_AMOUNT};

        mCursorInitialize = getContentResolver().query(
                KasebContract.DetailSaleProducts.uriDetailSaleProductsWithDetailSaleId(whichDetailSaleId),
                mProjectionInitialize,
                null,
                null,
                null);

        if (mCursorInitialize != null) {
            if (mCursorInitialize.moveToFirst()) {
                for (int i = 0; i < mCursorInitialize.getCount(); i++) {
                    Map<String, String> mProductsRowMap = new HashMap<>();

                    String _id = mCursorInitialize.getString(
                            mCursorInitialize.getColumnIndex(
                                    KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID));

                    Cursor mCursor = getContentResolver().query(
                            KasebContract.Products.buildProductsUri(Long.valueOf(_id)),
                            new String[]{
                                    KasebContract.Products._ID,
                                    KasebContract.Products.COLUMN_PRODUCT_NAME},
                            null,
                            null,
                            null);

                    Long amount = mCursorInitialize.getLong(
                            mCursorInitialize.getColumnIndex(
                                    KasebContract.DetailSaleProducts.COLUMN_AMOUNT));

                    Long num = mCursorInitialize.getLong(
                            mCursorInitialize.getColumnIndex(
                                    KasebContract.DetailSaleProducts.COLUMN_QUANTITY));

                    mProductsRowMap.put("id", _id);

                    if (mCursor != null)
                        if (mCursor.moveToFirst())
                            mProductsRowMap.put("name",
                                    mCursor.getString(
                                            mCursor.getColumnIndex(
                                                    KasebContract.Products.COLUMN_PRODUCT_NAME)));

                    mProductsRowMap.put("quantity", String.valueOf(num));
                    mProductsRowMap.put("price", String.valueOf(amount / num));

                    mChosenProductListMap.add(mProductsRowMap);
                    mCursorInitialize.moveToNext();
                }

                mCardViewProducts.getChosenProductAdapter(mChosenProductListMap);
            }
        }
        //endregion Get Products

        //region Get Customer Name & Family
        mProjectionInitialize = new String[]{
                KasebContract.Customers.COLUMN_FIRST_NAME,
                KasebContract.Customers.COLUMN_LAST_NAME,
                KasebContract.Customers.COLUMN_CUSTOMER_PICTURE};

        mCursorInitialize = getContentResolver().query(
                KasebContract.Customers.buildCustomerUri(customerId),
                mProjectionInitialize,
                null,
                null,
                null);

        byte[] imagegBytes = null;

        if (mCursorInitialize != null)
            if (mCursorInitialize.moveToFirst()) {
                nameCustomer.setText(
                        mCursorInitialize.getString(
                                mCursorInitialize.getColumnIndex(
                                        KasebContract.Customers.COLUMN_FIRST_NAME))
                                + " " +
                                mCursorInitialize.getString(
                                        mCursorInitialize.getColumnIndex(
                                                KasebContract.Customers.COLUMN_LAST_NAME)));

                imagegBytes = mCursorInitialize.getBlob(mCursorInitialize.getColumnIndex(KasebContract.Customers.COLUMN_CUSTOMER_PICTURE));
            }

        //region set Image Avavtar
        try {
            Boolean mWhat = false;
            if (imagegBytes == null)
                mWhat = true;
            else if (imagegBytes.length == 0)
                mWhat = true;

            if (mWhat)
                customerAvatar.setImageDrawable(getResources().getDrawable(
                        getResources().getIdentifier("@drawable/kaseb_pic", null, getPackageName())));
            else {
                customerAvatar.setImageBitmap(BitmapFactory.decodeByteArray(imagegBytes, 0, imagegBytes.length));
            }
        } catch (Exception e) {
        }
        //endregion set Image Avavtar

        //endregion Get Customer Name & Family

        //region Get Payments
        mProjectionInitialize = new String[]{
                KasebContract.DetailSalePayments._ID,
                KasebContract.DetailSalePayments.COLUMN_AMOUNT,
                KasebContract.DetailSalePayments.COLUMN_DUE_DATE,
                KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID,
                KasebContract.DetailSalePayments.COLUMN_IS_PASS};

        mCursorInitialize = getContentResolver().query(
                KasebContract.DetailSalePayments.paymentOfDetailSale(whichDetailSaleId),
                mProjectionInitialize,
                null,
                null,
                null);

        mProjectionInitialize = new String[]{
                KasebContract.PaymentMethods._ID,
                KasebContract.PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER};

        if (mCursorInitialize != null)
            if (mCursorInitialize.moveToFirst()) {
                for (int i = 0; i < mCursorInitialize.getCount(); i++) {
                    String _id = mCursorInitialize.getString(
                            mCursorInitialize.getColumnIndex(
                                    KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID));

                    Map<String, String> mPaymentsRowMap = new HashMap<>();

                    Cursor mCursor2 = getContentResolver().query(
                            KasebContract.PaymentMethods.buildPaymentMethodsUri(Long.valueOf(_id)),
                            mProjectionInitialize,
                            null,
                            null,
                            null);

                    if (mCursor2 != null)
                        if (mCursor2.moveToFirst())
                            mPaymentsRowMap.put("type",
                                    mCursor2.getString(
                                            mCursor2.getColumnIndex(
                                                    KasebContract.PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER)));

                    mPaymentsRowMap.put("id", _id);

                    mPaymentsRowMap.put("amount", String.valueOf(
                            mCursorInitialize.getLong(
                                    mCursorInitialize.getColumnIndex(
                                            KasebContract.DetailSalePayments.COLUMN_AMOUNT))));

                    mPaymentsRowMap.put("duedate", String.valueOf(
                            mCursorInitialize.getString(
                                    mCursorInitialize.getColumnIndex(
                                            KasebContract.DetailSalePayments.COLUMN_DUE_DATE))));
                    mPaymentsRowMap.put("isPass", String.valueOf((
                            mCursorInitialize.getString(
                                    mCursorInitialize.getColumnIndex(
                                            KasebContract.DetailSalePayments.COLUMN_IS_PASS)).equals("1")) ? true : false));

                    mPaymentListMap.add(mPaymentsRowMap);
                    mCursorInitialize.moveToNext();
                }

                mCardViewPayments.getPaymentAdapter(mPaymentListMap);
            }

        //endregion Get Payments

        //region Get Taxes
        mProjectionInitialize = new String[]{
                KasebContract.DetailSaleTaxes._ID,
                KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID,
                KasebContract.DetailSaleTaxes.COLUMN_AMOUNT,
                KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID};

        mCursorInitialize = getContentResolver().query(
                KasebContract.DetailSaleTaxes.taxOfDetailSale(whichDetailSaleId),
                mProjectionInitialize,
                null,
                null,
                null);

        mProjectionInitialize = new String[]{
                KasebContract.TaxTypes._ID,
                KasebContract.TaxTypes.COLUMN_TAX_TYPE_POINTER};

        if (mCursorInitialize != null)
            if (mCursorInitialize.moveToFirst()) {
                for (int i = 0; i < mCursorInitialize.getCount(); i++) {
                    Map<String, String> mTaxesRowMap = new HashMap<>();

                    String _id = mCursorInitialize.getString(
                            mCursorInitialize.getColumnIndex(
                                    KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID));

                    Long taxAmount = mCursorInitialize.getLong(
                            mCursorInitialize.getColumnIndex(
                                    KasebContract.DetailSaleTaxes.COLUMN_AMOUNT));

                    mTaxesRowMap.put("id", _id);
                    mTaxesRowMap.put("amount", String.valueOf(taxAmount));

                    Cursor mCursor2 = getContentResolver().query(
                            KasebContract.TaxTypes.buildTaxTypesUri(Long.valueOf(_id)),
                            mProjectionInitialize,
                            null,
                            null,
                            null);

                    if (mCursor2 != null)
                        if (mCursor2.moveToFirst())
                            mTaxesRowMap.put("type",
                                    mCursor2.getString(
                                            mCursor2.getColumnIndex(
                                                    KasebContract.TaxTypes.COLUMN_TAX_TYPE_POINTER)));

                    Cursor mCursor3 = getContentResolver().query(
                            KasebContract.DetailSale.buildDetailSaleUri(
                                    mCursorInitialize.getLong(
                                            mCursorInitialize.getColumnIndex(
                                                    KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID))),
                            new String[]{
                                    KasebContract.DetailSale.COLUMN_SUB_TOTAL},
                            null,
                            null,
                            null);

                    if (mCursor3 != null)
                        if (mCursor3.moveToFirst()) {
                            Long totalAmount = mCursor3.getLong(
                                    mCursor3.getColumnIndex(
                                            KasebContract.DetailSale.COLUMN_SUB_TOTAL));

                            mTaxesRowMap.put("percent", String.format("%.2f", (taxAmount * 100 / (double) totalAmount)));
                        }
                    mTaxListMap.add(mTaxesRowMap);
                    mCursorInitialize.moveToNext();
                }

                mCardViewTaxes.getTaxAdapter(mTaxListMap);
            }
        //endregion Get Taxes

        Utility.setStatusEnableForAllChildrenOfLinearLayout(mProductLinearLayout, false);
        Utility.setStatusEnableForAllChildrenOfLinearLayout(mPaymentLinearLayout, false);
        Utility.setStatusEnableForAllChildrenOfLinearLayout(mTaxLinearLayout, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_sale_view, menu);
        saveItem = (MenuItem) menu.findItem(R.id.menu_detail_sale_view_save);
        editItem = (MenuItem) menu.findItem(R.id.menu_detail_sale_view_edit);
        saveItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.menu_detail_sale_view_print:
                if (mSaved) {
                    //region Print
                    ArrayList<Long> mSummaryOfInvoice = new ArrayList<Long>();
                    mSummaryOfInvoice.add(sTotalAmount);
                    mSummaryOfInvoice.add(sTotalTax);
                    mSummaryOfInvoice.add(sTotalDiscount);
                    mSummaryOfInvoice.add(sPaidAmount);
                    mSummaryOfInvoice.add(sBalanceAmount);

                    try {
                        Utility.printInvoice(mContext, saleDate.getText().toString(), saleCode.getText().toString(),
                                nameCustomer.getText().toString(),
                                mSummaryOfInvoice, customerId, String.valueOf(whichDetailSaleId),
                                mChosenProductListMap, mTaxListMap, mPaymentListMap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(DetailSaleView.this, R.string.problem_in_pdf_reader, Toast.LENGTH_LONG).show();
                    }
                    //endregion Print
                } else
                    Toast.makeText(DetailSaleView.this, R.string.save_factor_then_print, Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_detail_sale_view_save:
                if (checkValidityWithChangeColorOfHelperText()) {

                    mDb.beginTransaction();

                    mSelectionInitialize = new String[]{String.valueOf(whichDetailSaleId)};

                    //region Delete Old Rows Of DetailSaleProducts
                    mWhereStatementInitialize = KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID + " = ? ";

                    getContentResolver().delete(
                            KasebContract.DetailSaleProducts.CONTENT_URI,
                            mWhereStatementInitialize,
                            mSelectionInitialize
                    );
                    //endregion Delete Old Rows Of DetailSaleProducts

                    //region Delete Old Rows Of DetailSalePayments
                    mWhereStatementInitialize = KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID + " = ? ";

                    getContentResolver().delete(
                            KasebContract.DetailSalePayments.CONTENT_URI,
                            mWhereStatementInitialize,
                            mSelectionInitialize
                    );
                    //endregion Delete Old Rows Of DetailSalePayments

                    //region Delete Old Rows Of DetailSaleTaxes
                    mWhereStatementInitialize = KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID + " = ? ";

                    getContentResolver().delete(
                            KasebContract.DetailSaleTaxes.CONTENT_URI,
                            mWhereStatementInitialize,
                            mSelectionInitialize
                    );
                    //endregion Delete Old Rows Of DetailSaleTaxes

                    //region Update Sale
                    mWhereStatementInitialize = KasebContract.Sales._ID + " = ? ";
                    mSelectionInitialize = new String[]{String.valueOf(whichSaleId)};

                    saleValues.put(KasebContract.Sales.COLUMN_CUSTOMER_ID, customerId);
                    saleValues.put(KasebContract.Sales.COLUMN_IS_DELETED, 0);
                    saleValues.put(KasebContract.Sales.COLUMN_SALE_CODE, saleCode.getText().toString());

                    getContentResolver().update(
                            KasebContract.Sales.CONTENT_URI,
                            saleValues,
                            mWhereStatementInitialize,
                            mSelectionInitialize
                    );
                    //endregion Insert Sale

                    //region Update DetailSale
                    mWhereStatementInitialize = KasebContract.DetailSale._ID + " = ? ";
                    mSelectionInitialize = new String[]{String.valueOf(whichDetailSaleId)};

                    detailSaleValues.put(KasebContract.DetailSale.COLUMN_DATE, saleDate.getText().toString());
                    detailSaleValues.put(KasebContract.DetailSale.COLUMN_IS_BALANCED, sFinalAmount.equals(sPaidAmount));
                    detailSaleValues.put(KasebContract.DetailSale.COLUMN_ITEMS_NUMBER, mChosenProductListMap.size());
                    detailSaleValues.put(KasebContract.DetailSale.COLUMN_SALE_ID, whichSaleId);
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

                    getContentResolver().update(
                            KasebContract.DetailSale.CONTENT_URI,
                            detailSaleValues,
                            mWhereStatementInitialize,
                            mSelectionInitialize
                    );
                    //endregion Insert DetailSale

                    //region Insert DetailSaleProducts
                    int count = mChosenProductListMap.size();
                    itemsValuesArray = new ContentValues[count];

                    for (int i = 0; i < count; i++) {
                        itemsValues = new ContentValues();

                        itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_AMOUNT,
                                Utility.convertFarsiNumbersToDecimal(String.valueOf(
                                        Long.valueOf(mChosenProductListMap.get(i).get("price").toString()) *
                                                Long.valueOf(mChosenProductListMap.get(i).get("quantity").toString()))));

                        itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID, whichDetailSaleId);
                        itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID, mChosenProductListMap.get(i).get("id").toString());
                        itemsValues.put(KasebContract.DetailSaleProducts.COLUMN_QUANTITY,
                                Utility.convertFarsiNumbersToDecimal(mChosenProductListMap.get(i).get("quantity").toString()));

                        itemsValuesArray[i] = itemsValues;
                    }

                    this.getContentResolver().bulkInsert(
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
                        paymentValues.put(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID, whichDetailSaleId);
                        paymentValues.put(KasebContract.DetailSalePayments.COLUMN_AMOUNT,
                                Utility.convertFarsiNumbersToDecimal(String.valueOf(
                                        Long.valueOf(mPaymentListMap.get(i).get("amount").toString()))));
                        paymentValues.put(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, mPaymentListMap.get(i).get("id").toString());
                        paymentValues.put(KasebContract.DetailSalePayments.COLUMN_IS_PASS, Boolean.parseBoolean(mPaymentListMap.get(i).get("isPass")));
                        paymentValues.put(KasebContract.DetailSalePayments.COLUMN_MODIFIED_DATE, Utility.preInsertDate(mContext));

                        paymentValuesArray[i] = paymentValues;
                    }

                    this.getContentResolver().bulkInsert(
                            KasebContract.DetailSalePayments.CONTENT_URI,
                            paymentValuesArray
                    );
                    //endregion Insert DetailSalePayments

                    //region Insert DetailSaleTaxes
                    count = mTaxListMap.size();
                    taxValuesArray = new ContentValues[count];

                    for (int i = 0; i < count; i++) {
                        taxValues = new ContentValues();

                        taxValues.put(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, whichDetailSaleId);
                        taxValues.put(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT,
                                Utility.convertFarsiNumbersToDecimal(String.valueOf(
                                        Long.valueOf(mTaxListMap.get(i).get("amount").toString()))));
                        taxValues.put(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID, mTaxListMap.get(i).get("id").toString());

                        taxValuesArray[i] = taxValues;
                    }

                    this.getContentResolver().bulkInsert(
                            KasebContract.DetailSaleTaxes.CONTENT_URI,
                            taxValuesArray
                    );
                    //endregion Insert DetailSaleTaxes

                    //just a message to show everything are under control
                    Toast.makeText(this,
                            getApplicationContext().getResources().getString(R.string.msg_update_succeed), Toast.LENGTH_LONG).show();

                    mDb.setTransactionSuccessful();
                    mDb.endTransaction();

                    mSaved = true;

                    addCustomerLayout.setEnabled(false);

                    saveItem.setVisible(false);
                    editItem.setVisible(true);

                    saleCode.setEnabled(false);
                    saleDate.setEnabled(false);


                    Utility.setStatusEnableForAllChildrenOfLinearLayout(mProductLinearLayout, false);
                    Utility.setStatusEnableForAllChildrenOfLinearLayout(mPaymentLinearLayout, false);
                    Utility.setStatusEnableForAllChildrenOfLinearLayout(mTaxLinearLayout, false);

                    imageButtonProducts.setEnabled(false);
                    imageButtonPayments.setEnabled(false);
                    imageButtonTaxes.setEnabled(false);

                    //region Print
                    ArrayList<Long> mSummaryOfInvoice = new ArrayList<Long>();
                    mSummaryOfInvoice.add(sTotalAmount);
                    mSummaryOfInvoice.add(sTotalTax);
                    mSummaryOfInvoice.add(sTotalDiscount);
                    mSummaryOfInvoice.add(sPaidAmount);
                    mSummaryOfInvoice.add(sBalanceAmount);

                    try {
                        Utility.printInvoice(mContext, saleDate.getText().toString(), saleCode.getText().toString(),
                                nameCustomer.getText().toString(),
                                mSummaryOfInvoice, customerId, String.valueOf(whichDetailSaleId),
                                mChosenProductListMap, mTaxListMap, mPaymentListMap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(DetailSaleView.this, R.string.problem_in_pdf_reader, Toast.LENGTH_LONG).show();
                    }
                    //endregion Print

                    getHelperText();
                    finish();
                }
                break;
            case R.id.menu_detail_sale_view_edit:
                mSaved = false;

                addCustomerLayout.setEnabled(true);

                saveItem.setVisible(true);
                editItem.setVisible(false);

                saleCode.setEnabled(true);
                saleDate.setEnabled(true);

                Utility.setStatusEnableForAllChildrenOfLinearLayout(mProductLinearLayout, true);
                Utility.setStatusEnableForAllChildrenOfLinearLayout(mPaymentLinearLayout, true);
                Utility.setStatusEnableForAllChildrenOfLinearLayout(mTaxLinearLayout, true);

                imageButtonProducts.setEnabled(true);
                imageButtonPayments.setEnabled(true);
                imageButtonTaxes.setEnabled(true);

                //region handle asterisk for necessary fields

                //region sale code
                Utility.setAsteriskToTextInputLayout(saleCodeTextInputLayout, getResources().getString(R.string.detail_sales_info_sale_code), true);
                //endregion sale code

                //region sale date
                Utility.setAsteriskToTextInputLayout(saleDateTextInputLayout, getResources().getString(R.string.hint_date), true);
                //endregion sale date

                //endregion handle asterisk for necessary fields

                return true;
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

        builder = new AlertDialog.Builder(DetailSaleView.this)
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
                DetailSaleView.this,
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
        builder = new AlertDialog.Builder(DetailSaleView.this)
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
                DetailSaleView.this,
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

                            differneceOfBuy_Sale = Utility.checkNumberOfProductsForDetailSale(DetailSaleView.this,
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
        builder = new AlertDialog.Builder(DetailSaleView.this)
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
                    Float amount = Utility.createFloatNumberWithString(DetailSaleView.this, paymentAmount.getText().toString());

                    if (amount > sFinalAmount) {
                        paymentAmountTextInputLayout.setError(getResources().getString(R.string.more_than_balance_amount));
                        paymentAmount.setSelectAllOnFocus(true);
                        paymentAmount.selectAll();
                        paymentAmount.requestFocus();
                        return;
                    } else if (!Utility.checkForValidityForEditTextDate(DetailSaleView.this, paymentDueDate)) {
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
        builder = new AlertDialog.Builder(DetailSaleView.this)
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
                    Float amount = Utility.createFloatNumberWithString(DetailSaleView.this, taxAmount.getText().toString());

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
                    Float percent = Utility.createFloatNumberWithString(DetailSaleView.this, taxPercent.getText().toString());
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

            if (type.equals(getResources().getString(R.string.tax_types_discount)))
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

    public void setPaymentMap(ArrayList<Map<String, String>> list) {
        mPaymentListMap = list;
    }

    private void getHelperText() {

        saleCodeTextInputLayout.setError(null);
        saleDateTextInputLayout.setError(null);
    }

    // this method check the validation and correct entries. its check fill first and then check the validation rules.
    private boolean checkValidityWithChangeColorOfHelperText() {

        if (!Utility.checkForValidityForEditTextNullOrEmptyAndItterative(
                mContext, saleCode, saleCodeTextInputLayout, KasebContract.Sales.CONTENT_URI,
                KasebContract.Sales.COLUMN_SALE_CODE + " = ? and " + KasebContract.Sales._ID + " != ? ",
                KasebContract.Sales._ID, new String[]{saleCode.getText().toString()})) {
            saleCodeTextInputLayout.setError(String.format("%s %s",
                    getResources().getString(R.string.example_sale_code),
                    getResources().getString(R.string.non_repetitive)));
            return false;
        }

        if (!Utility.checkForValidityForEditTextDate(DetailSaleView.this, saleDate)) {
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