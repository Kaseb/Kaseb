package mjkarbasian.moshtarimadar.Helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.meness.roozh.Roozh;
import io.github.meness.roozh.RoozhLocale;
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.R;

import static mjkarbasian.moshtarimadar.Helpers.Samples.cost_1;
import static mjkarbasian.moshtarimadar.Helpers.Samples.customer_1;
import static mjkarbasian.moshtarimadar.Helpers.Samples.detail_sale_1;
import static mjkarbasian.moshtarimadar.Helpers.Samples.detail_sale_2;
import static mjkarbasian.moshtarimadar.Helpers.Samples.ds1PaymentItems;
import static mjkarbasian.moshtarimadar.Helpers.Samples.ds1ProductItems;
import static mjkarbasian.moshtarimadar.Helpers.Samples.ds1TaxItems;
import static mjkarbasian.moshtarimadar.Helpers.Samples.ds2PaymentItems;
import static mjkarbasian.moshtarimadar.Helpers.Samples.ds2ProductItems;
import static mjkarbasian.moshtarimadar.Helpers.Samples.ds2TaxItems;
import static mjkarbasian.moshtarimadar.Helpers.Samples.product_1;
import static mjkarbasian.moshtarimadar.Helpers.Samples.product_history;
import static mjkarbasian.moshtarimadar.Helpers.Samples.sale_1;
import static mjkarbasian.moshtarimadar.Helpers.Samples.sale_2;
import static mjkarbasian.moshtarimadar.Helpers.Samples.salesCode;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setCost_1;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setCustomer_1;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setDS1PaymentItems;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setDS1ProductItems;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setDS1TaxItems;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setDS2PaymentItems;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setDS2ProductItems;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setDS2TaxItems;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setDetailSale_1;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setDetailSale_2;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setProduct_1;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setProduct_history;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setSale;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setSaleDueDate;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setSaleFinalAmount;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setSaleOffTaxList;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setSalePaymentList;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setSaleProductList;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setSaleSummary;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setSale_1;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setSale_2;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setSalesAmount;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setSalesCode;
import static mjkarbasian.moshtarimadar.Helpers.Samples.setSalesCustomer;

/**
 * Created by family on 6/24/2016.
 */
public class Utility {

    private static final String LOG_TAG = Utility.class.getSimpleName();
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern mPattern = Pattern.compile(EMAIL_PATTERN);
    private static int mSmallSize = 14;
    private static int mMiddleSize = 16;
    private static int mBigSize = 20;
    private static BaseColor subTitleColorTables = new BaseColor(140, 221, 8);
    private static BaseColor mainTitleColorTables = new BaseColor(255, 255, 255);
    private static Context _mContext;
    private static Document _mDocument;

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 0, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static void setAsteriskToTextInputLayout(final TextInputLayout mTextInputLayout, final String mHint, Boolean mFillable) {
        if (!mFillable || mTextInputLayout.getEditText().length() == 0) {
            mTextInputLayout.setHint(String.format("  %s", mHint));
            mTextInputLayout.getEditText().setHint(Utility.setAsteriskToView(""));
        } else {
            mTextInputLayout.setHint(String.format("* %s", mHint));
            mTextInputLayout.getEditText().setHint("");
        }

        mTextInputLayout.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mTextInputLayout.setHint(String.format("* %s", mHint));
                    mTextInputLayout.getEditText().setHint("");
                } else if (mTextInputLayout.getEditText().getText().length() == 0) {
                    mTextInputLayout.setHint(String.format("  %s", mHint));
                    mTextInputLayout.getEditText().setHint(Utility.setAsteriskToView(""));
                }
            }
        });
    }

    public static SpannableStringBuilder setAsteriskToView(String mMessage) {

        SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder();

        mSpannableStringBuilder.append(mMessage);
        int start = mSpannableStringBuilder.length();
        mSpannableStringBuilder.append("*");
        int end = mSpannableStringBuilder.length();

        mSpannableStringBuilder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return mSpannableStringBuilder;
    }

    public static boolean validateEmail(String email) {
        Matcher mMatcher = mPattern.matcher(email);
        return mMatcher.matches();
    }

    public static void printInvoice(Context mContext, String mSaleDate, String mSaleCode, String mNameCustomer,
                                    ArrayList<Long> mSummaryOfInvoice,
                                    Long mCustomerId, String mDetailSaleId,
                                    ArrayList<Map<String, String>> mChosenProductListMap,
                                    ArrayList<Map<String, String>> mTaxListMap,
                                    ArrayList<Map<String, String>> mPaymentListMap) throws IOException, DocumentException {
        _mContext = mContext;

        //region handle sharepreference
        String kasebPREFERENCES = "kasebProfile";
        SharedPreferences kasebSharedPreferences = mContext.getSharedPreferences(kasebPREFERENCES, mContext.MODE_PRIVATE);
        //endregion handle sharepreference

        HashMap<String, String> mTextFactor = new HashMap<String, String>();
        mTextFactor.put("firstTitle", _mContext.getString(R.string.title_of_print_invoice));
        mTextFactor.put("secondTitle", _mContext.getString(R.string.title_of_invoice_items));
        mTextFactor.put("thirdTitle", _mContext.getString(R.string.title_of_taxes));
        mTextFactor.put("sixthTitle", _mContext.getString(R.string.title_of_discounts));
        mTextFactor.put("fourthTitle", _mContext.getString(R.string.title_of_payments));
        mTextFactor.put("fifthTitle", _mContext.getString(R.string.title_of_invoice_summary));
        mTextFactor.put("sellerInfo", " ... ");
        mTextFactor.put("sellerTell", " ... ");
        mTextFactor.put("sellerAddress", " ... ");

        if (kasebSharedPreferences.getString("firstName", null) != null) {
            mTextFactor.put("sellerInfo",
                    kasebSharedPreferences.getString("firstName", null) + "  " +
                            kasebSharedPreferences.getString("lastName", null));

            mTextFactor.put("sellerTell",
                    kasebSharedPreferences.getString("phoneMobile", null) +
                            (kasebSharedPreferences.getString("phoneWork", null) != null
                                    && kasebSharedPreferences.getString("phoneWork", null) != "" ? " - " +
                                    kasebSharedPreferences.getString("phoneWork", null) : ""));

            mTextFactor.put("sellerAddress",
                    (kasebSharedPreferences.getString("addressCountry", null) != null
                            && kasebSharedPreferences.getString("addressCountry", null) != "" ?
                            kasebSharedPreferences.getString("addressCountry", null) : " ... ")
                            + (kasebSharedPreferences.getString("addressCity", null) != null
                            && kasebSharedPreferences.getString("addressCity", null) != "" ? " - " +
                            kasebSharedPreferences.getString("addressCity", null) : " ... ")
                            + (kasebSharedPreferences.getString("addressStreet", null) != null
                            && kasebSharedPreferences.getString("addressStreet", null) != "" ? " - " +
                            kasebSharedPreferences.getString("addressStreet", null) : " ... "));
        }

        mTextFactor.put("saleCode", mSaleCode);
        mTextFactor.put("saleDate", mSaleDate);
        mTextFactor.put("customerInfo", mNameCustomer);

        mTextFactor.put("totalAmount",
                Utility.formatPurchase(
                        _mContext,
                        Utility.DecimalSeperation(_mContext,
                                Double.parseDouble(String.valueOf(mSummaryOfInvoice.get(0))))));

        mTextFactor.put("totalTax", Utility.formatPurchase(
                _mContext,
                Utility.DecimalSeperation(_mContext,
                        Double.parseDouble(String.valueOf(mSummaryOfInvoice.get(1))))));

        mTextFactor.put("totalDiscount", Utility.formatPurchase(
                _mContext,
                Utility.DecimalSeperation(_mContext,
                        Double.parseDouble(String.valueOf(mSummaryOfInvoice.get(2))))));

        mTextFactor.put("totalPaied", Utility.formatPurchase(
                _mContext,
                Utility.DecimalSeperation(_mContext,
                        Double.parseDouble(String.valueOf(mSummaryOfInvoice.get(3))))));

        mTextFactor.put("totalUnpaied", Utility.formatPurchase(
                _mContext,
                Utility.DecimalSeperation(_mContext,
                        Double.parseDouble(String.valueOf(mSummaryOfInvoice.get(4))))));

        try {
            File myFile = Utility.createPdf(
                    "KasebSales",
                    String.valueOf(mCustomerId) + "_00000_" + mDetailSaleId,
                    PageSize.A4,
                    mTextFactor,
                    mChosenProductListMap,
                    mTaxListMap,
                    mPaymentListMap
            );

            Utility.viewPdf(_mContext, myFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkForValidityForEditTextNullOrEmpty(Context mContext, EditText mEditText) {
        _mContext = mContext;
        if (mEditText.getText().toString().equals("") || mEditText.getText().toString().equals(null))
            return false;

        return true;
    }

    public static boolean checkForValidityForEditTextDate(Context mContext, EditText mEditText) {
        _mContext = mContext;
        String[] dateList = mEditText.getText().toString().split("/");

        if (mEditText.getText().toString().equals("") || mEditText.getText().toString().equals(null)) {
            return false;
        } else if (dateList.length != 3) {
            return false;
        } else if (dateList[0].length() != 4) {
            return false;
        } else if (dateList[1].length() == 1 && dateList[1].equals("0")) {
            return false;
        } else if (dateList[1].length() != 1 && dateList[1].length() != 2) {
            return false;
        } else if (dateList[2].length() == 1 && dateList[2].equals("0")) {
            return false;
        } else if (dateList[2].length() != 1 && dateList[2].length() != 2) {
            return false;
        }

        return true;
    }

    public static void setErrorForTextView(TextView mTextView) {
        mTextView.setError("");
        mTextView.requestFocus();
    }

    public static boolean checkForValidityForEditTextNullOrEmptyAndItterative(
            Context mContext, EditText mEditText, TextInputLayout mTextInputLayout, Uri mUri, String mWhereStatment,
            String mProjectionColumnName, String[] mSelection) {
        if (!checkForValidityForEditTextNullOrEmpty(mContext, mEditText)) {
            mEditText.setSelectAllOnFocus(true);
            mEditText.selectAll();
            mEditText.requestFocus();
            return false;
        } else {
            Cursor mCursor = mContext.getContentResolver().query(
                    mUri,
                    new String[]{mProjectionColumnName},
                    mWhereStatment,
                    mSelection,
                    null);

            if (mCursor != null) {
                if (mCursor.moveToFirst())
                    if (mCursor.getCount() > 0) {
                        mEditText.setSelectAllOnFocus(true);
                        mEditText.selectAll();
                        mEditText.requestFocus();
                        return false;
                    }
            }

            mTextInputLayout.setError(null);
            return true;
        }
    }

    public static void setHeightOfListView(ListView mListView) {
        ListAdapter adapter = mListView.getAdapter();

        if (adapter == null) {
            return;
        }

        ViewGroup mViewGroup = mListView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, mViewGroup);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = mListView.getLayoutParams();
        int dpValue = totalHeight + (mListView.getDividerHeight() * (adapter.getCount() - 1));
        par.height = dipConverter(dpValue, mListView.getContext());
        mListView.setLayoutParams(par);
        mListView.requestLayout();
    }

    public static int indexOfRowsInMap(ArrayList<Map<String, String>> list, String key, String value) {
        Map<String, String> mProductsRowMapSample;
        for (int i = 0; i < list.size(); i++) {
            mProductsRowMapSample = list.get(i);
            if (mProductsRowMapSample.get("id").equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public static Map<String, String> setValueWithIndexInKeyOfMapRow(Map<String, String> map, String key, String value) {
        map.put(key, value);
        return map;
    }

    public static String makePlaceholders(int len) {
        StringBuilder sb = new StringBuilder(len * 2 - 1);
        sb.append("?");
        for (int i = 1; i < len; i++)
            sb.append(",?");
        return sb.toString();
    }

    public static String getTheLastPathUri(Uri uri) {
        String[] segments = uri.getPath().split("/");
        String pathStr = segments[segments.length - 1];
        return pathStr;
    }

    public static String formatPurchase(Context context, String purcahseAmount) {

        return context.getString(R.string.format_purchase_amount, purcahseAmount);
    }

    public static String DecimalSeperation(Context context, double number) {
        DecimalFormat format = new DecimalFormat();
        format.setDecimalSeparatorAlwaysShown(true);
        String decNumber = (String) format.format(number);
        return decNumber;
    }

    public static int dipConverter(int dip, Context context) {

        int dpValue = dip; // margin in dips
        float d = context.getResources().getDisplayMetrics().density;
        int marginDip = (int) (dpValue * d); // margin in pixels

        return marginDip;
    }

    public static String getLocale(Context context) {
        Locale current = context.getResources().getConfiguration().locale;
        return current.getCountry();
    }

    public static String doubleFormatter(double myNumber) {
        DecimalFormat f = (DecimalFormat) DecimalFormat.getInstance();
        f.setDecimalSeparatorAlwaysShown(false);
        f.setGroupingUsed(false);
        String myString = f.format(myNumber);
        return myString;
    }

    public static String localePersianDate(String date) {
        try {
            String[] datePart = date.split("/");
            return doubleFormatter(Double.parseDouble(datePart[0])) +
                    "/" + doubleFormatter(Double.parseDouble(datePart[1])) + "/" +
                    doubleFormatter(Double.parseDouble(datePart[2]));

        } catch (Exception e) {

        }
        return "ERROR";
    }

    public static String formatDate(Calendar calendar) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(calendar.getTime());
        return formattedDate;
    }

    public static String changeDate(String date, int option, int amount) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date day = df.parse(date);
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(day);
        String changedDate;
        switch (option) {
            case Calendar.DATE:
                currentDate.roll(Calendar.DAY_OF_MONTH, amount);
                break;
            case Calendar.MONTH:
                currentDate.roll(Calendar.MONTH, amount);
                break;
            case Calendar.YEAR:
                currentDate.roll(Calendar.YEAR, amount);
                break;
            default:

        }

        changedDate = Utility.formatDate(currentDate);
        return changedDate;

    }

    public static void initializer(Context context) {
        Cursor cursor = context.getContentResolver().query(KasebContract.CostTypes.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) costTypesInit(context);
        cursor = context.getContentResolver().query(KasebContract.TaxTypes.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) taxTypesInit(context);
        cursor = context.getContentResolver().query(KasebContract.PaymentMethods.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) paymentsMethodinit(context);
        cursor = context.getContentResolver().query(KasebContract.State.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() == 0) stateInits(context);
        //region initialize sample data
//        cursor = context.getContentResolver().query(KasebContract.Customers.CONTENT_URI, null, null, null, null);
//        if (cursor.getCount() == 0) customerInits(context);
//        cursor = context.getContentResolver().query(KasebContract.Products.CONTENT_URI, null, null, null, null);
//        if (cursor.getCount() == 0) productInits(context);
//        cursor = context.getContentResolver().query(KasebContract.Costs.CONTENT_URI, null, null, null, null);
//        if (cursor.getCount() == 0) costsInits(context);
//        cursor = context.getContentResolver().query(KasebContract.Sales.CONTENT_URI, null, null, null, null);
//        if (cursor.getCount() == 0) salesInits(context);
//        cursor = context.getContentResolver().query(KasebContract.DetailSale.CONTENT_URI, null, null, null, null);
//        if (cursor.getCount() == 0) detailSaleInits(context);
//        cursor = context.getContentResolver().query(KasebContract.DetailSaleProducts.CONTENT_URI, null, null, null, null);
//        if (cursor.getCount() == 0) dSaleProductInits(context);
//        cursor = context.getContentResolver().query(KasebContract.DetailSalePayments.CONTENT_URI, null, null, null, null);
//        if (cursor.getCount() == 0) dSalePaymentInits(context);
//        cursor = context.getContentResolver().query(KasebContract.DetailSaleTaxes.CONTENT_URI, null, null, null, null);
//        if (cursor.getCount() == 0) dSaleTaxInits(context);
//        cursor = context.getContentResolver().query(KasebContract.ProductHistory.CONTENT_URI, null, null, null, null);
//        if (cursor.getCount() == 0) productHistoryInits(context);
//endregio
        cursor.close();

        if (Samples.productCode.size() == 0) {
            Samples.setProductCode();
            Samples.setProductName(context);
            Samples.setProductDate();
            Samples.setProductPrice();
            Samples.setProductPicture(context);
            Samples.setProducts();
            Samples.setProductsPriceList();
        }

        if (salesCode.size() == 0) {
            setSalesCode();
            setSaleDueDate();
            setSalesCustomer(context);
            setSalesAmount();
            setSaleProductList();
            try {
                setSalePaymentList(context);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setSaleOffTaxList(context);
            setSaleFinalAmount(context);
            setSale();
            setSaleSummary();
        }
    }

    private static void productHistoryInits(Context context) {
        setProduct_history(context);
        ContentValues productHistory = new ContentValues();
        productHistory.put(KasebContract.ProductHistory.COLUMN_PRODUCT_ID, product_history.getInt(KasebContract.ProductHistory.COLUMN_PRODUCT_ID));
        productHistory.put(KasebContract.ProductHistory.COLUMN_QUANTITY, product_history.getInt(KasebContract.ProductHistory.COLUMN_QUANTITY));
        productHistory.put(KasebContract.ProductHistory.COLUMN_COST, product_history.getFloat(KasebContract.ProductHistory.COLUMN_COST));
        productHistory.put(KasebContract.ProductHistory.COLUMN_SALE_PRICE, product_history.getFloat(KasebContract.ProductHistory.COLUMN_SALE_PRICE));
        productHistory.put(KasebContract.ProductHistory.COLUMN_DATE, product_history.getString(KasebContract.ProductHistory.COLUMN_DATE));

        Uri productHUri = context.getContentResolver().insert(KasebContract.ProductHistory.CONTENT_URI, productHistory);
    }

    private static void detailSaleInits(Context context) {
        setDetailSale_1(context);
        setDetailSale_2(context);
        ContentValues detailSale1 = new ContentValues();
        ContentValues detailSale2 = new ContentValues();

        detailSale1.put(KasebContract.DetailSale.COLUMN_SALE_ID, detail_sale_1.getInt(KasebContract.DetailSale.COLUMN_SALE_ID));
        detailSale1.put(KasebContract.DetailSale.COLUMN_DATE, detail_sale_1.getString(KasebContract.DetailSale.COLUMN_DATE));
        detailSale1.put(KasebContract.DetailSale.COLUMN_ITEMS_NUMBER, detail_sale_1.getInt(KasebContract.DetailSale.COLUMN_ITEMS_NUMBER));
        detailSale1.put(KasebContract.DetailSale.COLUMN_SUB_TOTAL, detail_sale_1.getFloat(KasebContract.DetailSale.COLUMN_SUB_TOTAL));
        detailSale1.put(KasebContract.DetailSale.COLUMN_TOTAL_TAX, detail_sale_1.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_TAX));
        detailSale1.put(KasebContract.DetailSale.COLUMN_TOTAL_DISCOUNT, detail_sale_1.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_DISCOUNT));
        detailSale1.put(KasebContract.DetailSale.COLUMN_TOTAL_DUE, detail_sale_1.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_DUE));
        detailSale1.put(KasebContract.DetailSale.COLUMN_TOTAL_PAID, detail_sale_1.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_PAID));
        detailSale1.put(KasebContract.DetailSale.COLUMN_IS_BALANCED, detail_sale_1.getInt(KasebContract.DetailSale.COLUMN_IS_BALANCED));

        detailSale2.put(KasebContract.DetailSale.COLUMN_SALE_ID, detail_sale_2.getInt(KasebContract.DetailSale.COLUMN_SALE_ID));
        detailSale2.put(KasebContract.DetailSale.COLUMN_DATE, detail_sale_2.getString(KasebContract.DetailSale.COLUMN_DATE));
        detailSale2.put(KasebContract.DetailSale.COLUMN_ITEMS_NUMBER, detail_sale_2.getInt(KasebContract.DetailSale.COLUMN_ITEMS_NUMBER));
        detailSale2.put(KasebContract.DetailSale.COLUMN_SUB_TOTAL, detail_sale_2.getFloat(KasebContract.DetailSale.COLUMN_SUB_TOTAL));
        detailSale2.put(KasebContract.DetailSale.COLUMN_TOTAL_TAX, detail_sale_2.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_TAX));
        detailSale2.put(KasebContract.DetailSale.COLUMN_TOTAL_DISCOUNT, detail_sale_2.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_DISCOUNT));
        detailSale2.put(KasebContract.DetailSale.COLUMN_TOTAL_DUE, detail_sale_2.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_DUE));
        detailSale2.put(KasebContract.DetailSale.COLUMN_TOTAL_PAID, detail_sale_2.getFloat(KasebContract.DetailSale.COLUMN_TOTAL_PAID));

        Uri ds1Uri = context.getContentResolver().insert(KasebContract.DetailSale.CONTENT_URI, detailSale1);
        Log.d(LOG_TAG, ds1Uri.toString());
        Uri ds2Uri = context.getContentResolver().insert(KasebContract.DetailSale.CONTENT_URI, detailSale2);
        Log.d(LOG_TAG, ds2Uri.toString());
    }

    private static void dSaleTaxInits(Context context) {
        setDS1TaxItems(context);
        setDS2TaxItems(context);
        ContentValues dsTax1 = new ContentValues();
        ContentValues dsTax2 = new ContentValues();

        dsTax1.put(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, ds1TaxItems.getInt(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID));
        dsTax1.put(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID, ds1TaxItems.getInt(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID));
        dsTax1.put(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT, ds1TaxItems.getFloat(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT));

        dsTax2.put(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, ds2TaxItems.getInt(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID));
        dsTax2.put(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID, ds2TaxItems.getInt(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID));
        dsTax2.put(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT, ds2TaxItems.getFloat(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT));

        Uri ds1TaxUri = context.getContentResolver().insert(KasebContract.DetailSaleTaxes.CONTENT_URI, dsTax1);
        Uri ds2TaxUri = context.getContentResolver().insert(KasebContract.DetailSaleTaxes.CONTENT_URI, dsTax2);
    }

    private static void dSalePaymentInits(Context context) {
        setDS1PaymentItems(context);
        setDS2PaymentItems(context);
        ContentValues dsPayment1 = new ContentValues();
        ContentValues dsPayment2 = new ContentValues();

        dsPayment1.put(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID, ds1PaymentItems.getInt(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID));
        dsPayment1.put(KasebContract.DetailSalePayments.COLUMN_MODIFIED_DATE, ds1PaymentItems.getString(KasebContract.DetailSalePayments.COLUMN_MODIFIED_DATE));
        dsPayment1.put(KasebContract.DetailSalePayments.COLUMN_DUE_DATE, ds1PaymentItems.getString(KasebContract.DetailSalePayments.COLUMN_DUE_DATE));
        dsPayment1.put(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, ds1PaymentItems.getInt(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID));
        dsPayment1.put(KasebContract.DetailSalePayments.COLUMN_AMOUNT, ds1PaymentItems.getFloat(KasebContract.DetailSalePayments.COLUMN_AMOUNT));

        dsPayment2.put(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID, ds2PaymentItems.getInt(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID));
        dsPayment2.put(KasebContract.DetailSalePayments.COLUMN_MODIFIED_DATE, ds2PaymentItems.getString(KasebContract.DetailSalePayments.COLUMN_MODIFIED_DATE));
        dsPayment2.put(KasebContract.DetailSalePayments.COLUMN_DUE_DATE, ds2PaymentItems.getString(KasebContract.DetailSalePayments.COLUMN_DUE_DATE));
        dsPayment2.put(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, ds2PaymentItems.getInt(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID));
        dsPayment2.put(KasebContract.DetailSalePayments.COLUMN_AMOUNT, ds2PaymentItems.getFloat(KasebContract.DetailSalePayments.COLUMN_AMOUNT));

        Uri ds1PaymentUri = context.getContentResolver().insert(KasebContract.DetailSalePayments.CONTENT_URI, dsPayment1);
        Uri ds2PaymentUri = context.getContentResolver().insert(KasebContract.DetailSalePayments.CONTENT_URI, dsPayment2);
    }

    private static void dSaleProductInits(Context context) {
        setDS1ProductItems(context);
        setDS2ProductItems(context);
        ContentValues dsProduct1 = new ContentValues();
        ContentValues dsProduct2 = new ContentValues();

        dsProduct1.put(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID, ds1ProductItems.getInt(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID));
        dsProduct1.put(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID, ds1ProductItems.getInt(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID));
        dsProduct1.put(KasebContract.DetailSaleProducts.COLUMN_QUANTITY, ds1ProductItems.getInt(KasebContract.DetailSaleProducts.COLUMN_QUANTITY));
        dsProduct1.put(KasebContract.DetailSaleProducts.COLUMN_AMOUNT, ds1ProductItems.getFloat(KasebContract.DetailSaleProducts.COLUMN_AMOUNT));

        dsProduct2.put(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID, ds2ProductItems.getInt(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID));
        dsProduct2.put(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID, ds2ProductItems.getInt(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID));
        dsProduct2.put(KasebContract.DetailSaleProducts.COLUMN_QUANTITY, ds2ProductItems.getInt(KasebContract.DetailSaleProducts.COLUMN_QUANTITY));
        dsProduct2.put(KasebContract.DetailSaleProducts.COLUMN_AMOUNT, ds2ProductItems.getFloat(KasebContract.DetailSaleProducts.COLUMN_AMOUNT));

        Uri dsProductUri = context.getContentResolver().insert(KasebContract.DetailSaleProducts.CONTENT_URI, dsProduct1);
        Uri dsProductUri2 = context.getContentResolver().insert(KasebContract.DetailSaleProducts.CONTENT_URI, dsProduct2);
    }

    private static void salesInits(Context context) {
        setSale_1(context);
        ContentValues sale1 = new ContentValues();
        sale1.put(KasebContract.Sales.COLUMN_SALE_CODE, sale_1.getString(KasebContract.Sales.COLUMN_SALE_CODE));
        sale1.put(KasebContract.Sales.COLUMN_CUSTOMER_ID, sale_1.getInt(KasebContract.Sales.COLUMN_CUSTOMER_ID));
        Uri saleUri = context.getContentResolver().insert(KasebContract.Sales.CONTENT_URI, sale1);

        setSale_2(context);
        ContentValues sale2 = new ContentValues();
        sale2.put(KasebContract.Sales.COLUMN_SALE_CODE, sale_2.getString(KasebContract.Sales.COLUMN_SALE_CODE));
        sale2.put(KasebContract.Sales.COLUMN_CUSTOMER_ID, sale_2.getInt(KasebContract.Sales.COLUMN_CUSTOMER_ID));
        Uri saleUri2 = context.getContentResolver().insert(KasebContract.Sales.CONTENT_URI, sale2);
    }

    private static void costsInits(Context context) {
        setCost_1(context);
        ContentValues cost = new ContentValues();
        cost.put(KasebContract.Costs.COLUMN_COST_NAME, cost_1.getString(KasebContract.Costs.COLUMN_COST_NAME));
        cost.put(KasebContract.Costs.COLUMN_COST_CODE, cost_1.getString(KasebContract.Costs.COLUMN_COST_CODE));
        cost.put(KasebContract.Costs.COLUMN_COST_TYPE_ID, cost_1.getInt(KasebContract.Costs.COLUMN_COST_TYPE_ID));
        cost.put(KasebContract.Costs.COLUMN_AMOUNT, cost_1.getFloat(KasebContract.Costs.COLUMN_AMOUNT));
        cost.put(KasebContract.Costs.COLUMN_DATE, cost_1.getString(KasebContract.Costs.COLUMN_DATE));
        cost.put(KasebContract.Costs.COLUMN_DESCRIPTION, cost_1.getString(KasebContract.Costs.COLUMN_DESCRIPTION));
        Uri costUri = context.getContentResolver().insert(KasebContract.Costs.CONTENT_URI, cost);
    }

    private static void productInits(Context context) {
        setProduct_1(context);
        ContentValues product = new ContentValues();
        product.put(KasebContract.Products.COLUMN_PRODUCT_NAME, product_1.getString(KasebContract.Products.COLUMN_PRODUCT_NAME));
        product.put(KasebContract.Products.COLUMN_PRODUCT_CODE, product_1.getString(KasebContract.Products.COLUMN_PRODUCT_CODE));
        product.put(KasebContract.Products.COLUMN_UNIT, product_1.getString(KasebContract.Products.COLUMN_UNIT));
        product.put(KasebContract.Products.COLUMN_DESCRIPTION, product_1.getString(KasebContract.Products.COLUMN_DESCRIPTION));
        Uri productUri = context.getContentResolver().insert(KasebContract.Products.CONTENT_URI, product);

    }

    private static void customerInits(Context context) {
        setCustomer_1(context);
        ContentValues customer = new ContentValues();
        customer.put(KasebContract.Customers.COLUMN_FIRST_NAME, customer_1.getString(KasebContract.Customers.COLUMN_FIRST_NAME));
        customer.put(KasebContract.Customers.COLUMN_LAST_NAME, customer_1.getString(KasebContract.Customers.COLUMN_LAST_NAME));
        customer.put(KasebContract.Customers.COLUMN_PHONE_MOBILE, customer_1.getString(KasebContract.Customers.COLUMN_PHONE_MOBILE));
        customer.put(KasebContract.Customers.COLUMN_EMAIL, customer_1.getString(KasebContract.Customers.COLUMN_EMAIL));
        customer.put(KasebContract.Customers.COLUMN_PHONE_WORK, customer_1.getString(KasebContract.Customers.COLUMN_PHONE_WORK));
        customer.put(KasebContract.Customers.COLUMN_PHONE_HOME, customer_1.getString(KasebContract.Customers.COLUMN_PHONE_HOME));
        customer.put(KasebContract.Customers.COLUMN_PHONE_FAX, customer_1.getString(KasebContract.Customers.COLUMN_PHONE_FAX));
        customer.put(KasebContract.Customers.COLUMN_PHONE_OTHER, customer_1.getString(KasebContract.Customers.COLUMN_PHONE_OTHER));
        customer.put(KasebContract.Customers.COLUMN_ADDRESS_COUNTRY, customer_1.getString(KasebContract.Customers.COLUMN_ADDRESS_COUNTRY));
        customer.put(KasebContract.Customers.COLUMN_ADDRESS_CITY, customer_1.getString(KasebContract.Customers.COLUMN_ADDRESS_CITY));
        customer.put(KasebContract.Customers.COLUMN_ADDRESS_STREET, customer_1.getString(KasebContract.Customers.COLUMN_ADDRESS_STREET));
        customer.put(KasebContract.Customers.COLUMN_ADDRESS_POSTAL_CODE, customer_1.getString(KasebContract.Customers.COLUMN_ADDRESS_POSTAL_CODE));
        customer.put(KasebContract.Customers.COLUMN_DESCRIPTION, customer_1.getString(KasebContract.Customers.COLUMN_DESCRIPTION));
        customer.put(KasebContract.Customers.COLUMN_STATE_ID, customer_1.getInt(KasebContract.Customers.COLUMN_STATE_ID));
        Uri customerUri = context.getContentResolver().insert(KasebContract.Customers.CONTENT_URI, customer);
    }

    private static void stateInits(Context context) {
        ContentValues[] contentValues;
        contentValues = new ContentValues[4];
        String[] ids = new String[]{
                context.getResources().getString(R.string.states_gold),
                context.getResources().getString(R.string.states_silver),
                context.getResources().getString(R.string.states_bronze, R.string.states_instart),
                context.getResources().getString(R.string.states_instart)};
        int[] colors = new int[]{Color.rgb(255, 215, 0), Color.rgb(192, 192, 192), Color.rgb(218, 165, 32), Color.rgb(176, 224, 230)};
        for (int i = 0; i < ids.length; i++) {
            ContentValues states = new ContentValues();
            states.put(KasebContract.State.COLUMN_STATE_POINTER, ids[i]);
            states.put(KasebContract.State.COLUMN_STATE_COLOR, colors[i]);
            contentValues[i] = states;
        }

        int insertedUri = context.getContentResolver().bulkInsert(
                KasebContract.State.CONTENT_URI,
                contentValues
        );
        Log.d(LOG_TAG, "Data successfully initialized to: " + Integer.toString(insertedUri));
    }

    private static void paymentsMethodinit(Context context) {
        ContentValues[] contentValues;
        contentValues = new ContentValues[3];
        String[] ids = new String[]{context.getResources().getString(R.string.payment_method_cash)
                , context.getResources().getString(R.string.payment_method_cheque)
                , context.getResources().getString(R.string.payment_method_pos)};
        for (int i = 0; i < ids.length; i++) {
            ContentValues paymentMethods = new ContentValues();
            paymentMethods.put(KasebContract.PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER, ids[i]);
            contentValues[i] = paymentMethods;
        }

        int insertedUri = context.getContentResolver().bulkInsert(
                KasebContract.PaymentMethods.CONTENT_URI,
                contentValues
        );
        Log.d(LOG_TAG, "Data successfully initialized to: " + Integer.toString(insertedUri));

    }

    private static void taxTypesInit(Context context) {
        ContentValues[] contentValues;
        contentValues = new ContentValues[2];
        String[] ids = new String[]{context.getResources().getString(R.string.tax_types_vas)
                , context.getResources().getString(R.string.tax_types_discount)};
        for (int i = 0; i < ids.length; i++) {
            ContentValues taxTypes = new ContentValues();
            taxTypes.put(KasebContract.TaxTypes.COLUMN_TAX_TYPE_POINTER, ids[i]);
            contentValues[i] = taxTypes;
        }

        int insertedUri = context.getContentResolver().bulkInsert(
                KasebContract.TaxTypes.CONTENT_URI,
                contentValues
        );
        Log.d(LOG_TAG, "Data successfully initialized to: " + Integer.toString(insertedUri));

    }

    private static void costTypesInit(Context context) {
        ContentValues[] contentValues;
        contentValues = new ContentValues[4];
        String[] ids = new String[]{context.getResources().getString(R.string.cost_types_bill),
                context.getResources().getString(R.string.cost_types_salary),
                context.getResources().getString(R.string.cost_types_tax),
                context.getResources().getString(R.string.cost_types_others)};
        for (int i = 0; i < ids.length; i++) {
            ContentValues costTypes = new ContentValues();
            costTypes.put(KasebContract.CostTypes.COLUMN_COST_TYPE_POINTER, ids[i]);
            contentValues[i] = costTypes;
        }

        int insertedUri = context.getContentResolver().bulkInsert(
                KasebContract.CostTypes.CONTENT_URI,
                contentValues
        );
        Log.d(LOG_TAG, "Data successfully initialized to: " + Integer.toString(insertedUri));
    }

    public static void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }

    public static String preInsertDate(Context context) {

        Roozh persianCalendar = Roozh.getInstance(RoozhLocale.PERSIAN);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        Roozh pCalendar = persianCalendar.gregorianToPersian(date.getTime());
        int pYear = pCalendar.getYear();
        int pMonth = pCalendar.getMonth();
        int pDay = pCalendar.getDayOfMonth();
        String pDate = Integer.toString(pYear) + "/" + Integer.toString(pMonth) + "/" + Integer.toString(pDay);

        if (!getLocale(context).equals("IR")) {
            return formatDate(calendar);
        } else {
            return pDate;
        }
    }

    public static String preInsertSaleCode(Context context) {
        Cursor cursor = context.getContentResolver().query(KasebContract.Sales.CONTENT_URI, new String[]{KasebContract.Sales._ID},
                null, null, KasebContract.Sales._ID + " DESC");
        String prefix = context.getResources().getString(R.string.sale_code_prefix);
        String newCode = null;
        int codeNumber;

        if (cursor.moveToFirst()) {
            codeNumber = cursor.getInt(0) + 1;
            newCode = prefix + String.valueOf(Utility.doubleFormatter(codeNumber));
        } else {
            codeNumber = 1;
            newCode = prefix + " " + String.valueOf(Utility.doubleFormatter(codeNumber));
        }
        cursor.close();
        return newCode;
    }

    public static String preInsertCostCode(Context context) {
        Cursor cursor = context.getContentResolver().query(KasebContract.Costs.CONTENT_URI, new String[]{KasebContract.Costs._ID},
                null, null, KasebContract.Costs._ID + " DESC");
        String prefix = context.getResources().getString(R.string.cost_code_prefix);
        String newCode = null;
        int codeNumber;
        if (cursor.moveToFirst()) {
            codeNumber = cursor.getInt(0) + 1;
            newCode = prefix + " " + String.valueOf(Utility.doubleFormatter(codeNumber));
        } else {
            codeNumber = 1;
            newCode = prefix + " " + String.valueOf(Utility.doubleFormatter(codeNumber));
        }
        cursor.close();
        return newCode;
    }

    public static String preInsertProductCode(Context context) {
        Cursor cursor = context.getContentResolver().query(KasebContract.Products.CONTENT_URI, new String[]{KasebContract.Products._ID},
                null, null, KasebContract.Products._ID + " DESC");
        String prefix = context.getResources().getString(R.string.product_code_prefix);
        String newCode = null;
        int codeNumber;
        if (cursor.moveToFirst()) {
            codeNumber = cursor.getInt(0) + 1;
            newCode = prefix + String.valueOf(Utility.doubleFormatter(codeNumber));
        } else {
            codeNumber = 1;
            newCode = prefix + String.valueOf(Utility.doubleFormatter(codeNumber));
        }
        cursor.close();
        return newCode;
    }

    public static Long checkNumberOfProductsForDetailSale(Context mContext, Long mDetailSaleId,
                                                          String mWhichActivity, Long mProductId) {
        return howManyBuyed(mContext, mProductId) -
                howManySold(mContext, mDetailSaleId, mWhichActivity, mProductId);
    }

    public static String cretaeWhereStatementArgsFor_checkNumberOfProductsForDetailSale(String whichActivity, int mCount) {

        if (whichActivity.equals("SaleInsert"))
            return KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID + " IN (" +
                    Utility.makePlaceholders((mCount > 0 ? mCount : 1)) + ") and " +
                    KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID + " = ? ";
        else if (whichActivity.equals("SaleView"))
            return KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID + " IN (" +
                    Utility.makePlaceholders((mCount > 0 ? mCount : 1)) + ") and " +
                    KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID + " != ? and " +
                    KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID + " = ? ";

        return "";
    }

    private static Long howManySold(Context mContext, Long mDetailSaleId, String mWhichActivity, Long mProductId) {

        Cursor mCursor = mContext.getContentResolver().query(
                KasebContract.Sales.CONTENT_URI,
                new String[]{KasebContract.Sales._ID},
                KasebContract.Sales.COLUMN_IS_DELETED + " = ? ",
                new String[]{"0"},
                null);

        String[] mSelection = new String[(mCursor.getCount() > 0 ? mCursor.getCount() : 1)];

        if (mCursor != null)
            if (mCursor.moveToFirst())
                for (int i = 0; i < mCursor.getCount(); i++) {
                    mSelection[i] = mCursor.getString(mCursor.getColumnIndex(KasebContract.Sales._ID));
                    mCursor.moveToNext();
                }

        if (mSelection[0] == null)
            mSelection[0] = "-1";

        String mWhereStatement = KasebContract.DetailSale.COLUMN_SALE_ID + " IN (" +
                Utility.makePlaceholders((mCursor.getCount() > 0 ? mCursor.getCount() : 1)) + ")";

        mCursor = mContext.getContentResolver().query(
                KasebContract.DetailSale.CONTENT_URI,
                new String[]{KasebContract.DetailSale._ID},
                mWhereStatement,
                mSelection,
                null);

        if (mWhichActivity == "SaleInsert")
            mSelection = new String[(mCursor.getCount() > 0 ? mCursor.getCount() + 1 : 1)];
        else if (mWhichActivity == "SaleView")
            mSelection = new String[(mCursor.getCount() > 0 ? mCursor.getCount() + 2 : 1)];

        if (mCursor != null)
            if (mCursor.moveToFirst())
                for (int i = 0; i < mCursor.getCount(); i++) {
                    mSelection[i] = mCursor.getString(mCursor.getColumnIndex(KasebContract.DetailSale._ID));
                    mCursor.moveToNext();
                }

        if (mSelection[0] == null)
            mSelection[0] = "-1";
        else if (mWhichActivity == "SaleInsert")
            mSelection[mSelection.length - 1] = String.valueOf(mProductId);
        else if (mWhichActivity == "SaleView") {
            mSelection[mSelection.length - 2] = String.valueOf(mDetailSaleId);
            mSelection[mSelection.length - 1] = String.valueOf(mProductId);
        }

        mCursor = mContext.getContentResolver().query(
                KasebContract.DetailSaleProducts.CONTENT_URI,
                new String[]{"SUM (" + KasebContract.DetailSaleProducts.COLUMN_QUANTITY + " ) AS all_sold_quantity_product"},
                cretaeWhereStatementArgsFor_checkNumberOfProductsForDetailSale(mWhichActivity, mCursor.getCount()),
                mWhichActivity == "SaleInsert" ? mSelection :
                        new String[]{String.valueOf(mDetailSaleId), String.valueOf(mProductId)},
                null);

        if (mCursor != null)
            if (mCursor.moveToFirst())
                return mCursor.getLong(mCursor.getColumnIndex("all_sold_quantity_product"));

        mCursor.close();
        return 0l;
    }

    private static Long howManyBuyed(Context mContext, Long mProductId) {
        Cursor mCursor = mContext.getContentResolver().query(
                KasebContract.ProductHistory.aProductHistory(mProductId),
                new String[]{"SUM (" + KasebContract.ProductHistory.COLUMN_QUANTITY + " ) AS all_bought_quantity_product"},
                null,
                null,
                null);

        if (mCursor != null)
            if (mCursor.moveToFirst())
                return mCursor.getLong(mCursor.getColumnIndex("all_bought_quantity_product"));

        mCursor.close();
        return 0l;
    }

    public static File createPdf(String mDirectoryName, String mFileName,
                                 Rectangle mPageSize, HashMap<String, String> mTextHashMap,
                                 ArrayList<Map<String, String>> mChosenProductListMap,
                                 ArrayList<Map<String, String>> mTaxListMap,
                                 ArrayList<Map<String, String>> mPaymentListMap)
            throws IOException, DocumentException, ParseException {

        File pdfFolder = new File(Environment.getExternalStorageDirectory(), "/" + mDirectoryName);
        if (!pdfFolder.exists())
            pdfFolder.mkdir();

        File myFile = new File(pdfFolder + "/" + mFileName + ".pdf");

        if (myFile.exists())
            myFile.delete();

        _mDocument = new Document(mPageSize);
        OutputStream output = new FileOutputStream(myFile);
        PdfWriter.getInstance(_mDocument, output);

        _mDocument.open();

        addMetaData();
        addContent(mTextHashMap, mChosenProductListMap, mTaxListMap, mPaymentListMap);

        _mDocument.close();

        return myFile;
    }

    private static void addMetaData() {
        _mDocument.addTitle(_mContext.getString(R.string.sales_pdf_meta_data_title));
        _mDocument.addSubject(_mContext.getString(R.string.sales_pdf_meta_data_subject));
        _mDocument.addKeywords(_mContext.getString(R.string.sales_pdf_meta_data_kew_words));
        _mDocument.addAuthor(_mContext.getString(R.string.sales_pdf_meta_data_author));
        _mDocument.addCreator(_mContext.getString(R.string.sales_pdf_meta_data_creator));
    }

    private static void addContent(HashMap<String, String> mTextHashMapTitles_Summary,
                                   ArrayList<Map<String, String>> mChosenProductListMap,
                                   ArrayList<Map<String, String>> mTaxListMap,
                                   ArrayList<Map<String, String>> mPaymentListMap)
            throws DocumentException, ParseException, IOException {

        //region Create Paragraph with Some Lines
        Paragraph mParagraph = new Paragraph();
        mParagraph.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(mParagraph, 1);
        //endregion Create Paragraph with Some Lines

        PdfPTable table;

        //region add InfoForSalesInvoice paragraph
        _mDocument.add(mParagraph);
        table = new PdfPTable(1);
        PdfPCell mCell = new PdfPCell(new Phrase(mTextHashMapTitles_Summary.get("firstTitle"), createFontWithSize(mBigSize)));
        mCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        mCell.setBorder(Rectangle.NO_BORDER);
        setRtlToCell(mCell);
        table.addCell(mCell);
        _mDocument.add(table);
        _mDocument.add(mParagraph);

        createTableInfoForSalesInvoice(mTextHashMapTitles_Summary);
        //endregion add InfoForSalesInvoice paragraph

        //region add InfoTableForInvoiceItems paragraph
        _mDocument.add(mParagraph);
        table = new PdfPTable(1);
        mCell = new PdfPCell(new Phrase(mTextHashMapTitles_Summary.get("secondTitle"), createFontWithSize(mBigSize)));
        mCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        mCell.setBorder(Rectangle.NO_BORDER);
        setRtlToCell(mCell);
        table.addCell(mCell);
        _mDocument.add(table);
        _mDocument.add(mParagraph);

        createInfoTableForInvoiceItems(mChosenProductListMap);
        //endregion add InfoTableForInvoiceItems paragraph

        //region add TaxesForSalesInvoice paragraph
        if (numberOfTypes(mTaxListMap, _mContext.getString(R.string.vas_title)) != 0) {
            _mDocument.add(mParagraph);
            table = new PdfPTable(1);
            mCell = new PdfPCell(new Phrase(mTextHashMapTitles_Summary.get("thirdTitle"), createFontWithSize(mBigSize)));
            mCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            mCell.setBorder(Rectangle.NO_BORDER);
            setRtlToCell(mCell);
            table.addCell(mCell);
            _mDocument.add(table);
            _mDocument.add(mParagraph);

            createTableTaxesForSalesInvoice(mTaxListMap);
        }
        //endregion add TaxesForSalesInvoice paragraph

        //region add Taxes_DiscountsForSalesInvoice paragraph
        if (numberOfTypes(mTaxListMap, _mContext.getString(R.string.discount_title)) != 0) {
            _mDocument.add(mParagraph);
            table = new PdfPTable(1);
            mCell = new PdfPCell(new Phrase(mTextHashMapTitles_Summary.get("sixthTitle"), createFontWithSize(mBigSize)));
            mCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            mCell.setBorder(Rectangle.NO_BORDER);
            setRtlToCell(mCell);
            table.addCell(mCell);
            _mDocument.add(table);
            _mDocument.add(mParagraph);

            createTableDiscountsForSalesInvoice(mTaxListMap);
        }
        //endregion add Taxes_DiscountsForSalesInvoice paragraph

        //region add PaymentsForSalesInvoice paragraph
        if (mPaymentListMap.size() != 0) {
            _mDocument.add(mParagraph);
            table = new PdfPTable(1);
            mCell = new PdfPCell(new Phrase(mTextHashMapTitles_Summary.get("fourthTitle"), createFontWithSize(mBigSize)));
            mCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            mCell.setBorder(Rectangle.NO_BORDER);
            setRtlToCell(mCell);
            table.addCell(mCell);
            _mDocument.add(table);
            _mDocument.add(mParagraph);

            createTablePaymentsForSalesInvoice(mPaymentListMap);
        }
        //endregion add PaymentsForSalesInvoice paragraph

        //region add InfoForSalesInvoice paragraph
        _mDocument.add(mParagraph);
        table = new PdfPTable(1);
        mCell = new PdfPCell(new Phrase(mTextHashMapTitles_Summary.get("fifthTitle"), createFontWithSize(mBigSize)));
        mCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        mCell.setBorder(Rectangle.NO_BORDER);
        setRtlToCell(mCell);
        table.addCell(mCell);
        _mDocument.add(table);
        _mDocument.add(mParagraph);

        createTableSummaryForSalesInvoice(mTextHashMapTitles_Summary);
        //endregion add InfoForSalesInvoice paragraph

        //region add Register paragraph
        _mDocument.add(mParagraph);
        _mDocument.add(mParagraph);
        createTableEndInfoForSalesInvoice();
        _mDocument.add(mParagraph);
        _mDocument.add(mParagraph);
        table = new PdfPTable(1);
        mCell = new PdfPCell(new Phrase(_mContext.getString(R.string.register_message_copyright), createFontWithSize(mBigSize)));
        mCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        mCell.setBorder(Rectangle.NO_BORDER);
        setRtlToCell(mCell);
        table.addCell(mCell);
        _mDocument.add(table);
        //endregion add Register paragraph
    }

    private static void createTableInfoForSalesInvoice(HashMap<String, String> mTextHashMapTitles)
            throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{105, 105});

        PdfPCell mCell1 = new PdfPCell();
        Phrase mElement;
        mElement = new Phrase(_mContext.getString(R.string.seller_info), createFontWithSize(mMiddleSize));
        mCell1.addElement(mElement);
        mElement = new Phrase(mTextHashMapTitles.get("sellerInfo"),
                (checkStringIfHasAsciiCharacter(mTextHashMapTitles.get("sellerInfo")) ?
                        createEnglishFontWithSize(mSmallSize) : createFontWithSize(mSmallSize)));
        mCell1.addElement(mElement);
        mElement = new Phrase(_mContext.getString(R.string.seller_tell), createFontWithSize(mMiddleSize));
        mCell1.addElement(mElement);
        mElement = new Phrase(mTextHashMapTitles.get("sellerTell"),
                (checkStringIfHasAsciiCharacter(mTextHashMapTitles.get("sellerTell")) ?
                        createEnglishFontWithSize(mSmallSize) : createFontWithSize(mSmallSize)));
        mCell1.addElement(mElement);
        mElement = new Phrase(_mContext.getString(R.string.seller_address), createFontWithSize(mMiddleSize));
        mCell1.addElement(mElement);
        mElement = new Phrase(mTextHashMapTitles.get("sellerAddress"),
                (checkStringIfHasAsciiCharacter(mTextHashMapTitles.get("sellerAddress")) ?
                        createEnglishFontWithSize(mSmallSize) : createFontWithSize(mSmallSize)));
        mCell1.addElement(mElement);

        setBackGroundColor_P_BW_HA_VA(mCell1, mainTitleColorTables, 10, 3, Element.ALIGN_LEFT, Element.ALIGN_CENTER);

        PdfPCell mCell2 = new PdfPCell();
        mElement = new Phrase(_mContext.getString(R.string.customer_info), createFontWithSize(mMiddleSize));
        mCell2.addElement(mElement);
        mElement = new Phrase(mTextHashMapTitles.get("customerInfo"),
                (checkStringIfHasAsciiCharacter(mTextHashMapTitles.get("customerInfo")) ?
                        createEnglishFontWithSize(mSmallSize) : createFontWithSize(mSmallSize)));
        mCell2.addElement(mElement);
        mElement = new Phrase(_mContext.getString(R.string.invoice_date), createFontWithSize(mMiddleSize));
        mCell2.addElement(mElement);
        mElement = new Phrase(mTextHashMapTitles.get("saleDate"), createFontWithSize(mSmallSize));
        mCell2.addElement(mElement);
        mElement = new Phrase(_mContext.getString(R.string.invoice_sale_code), createFontWithSize(mMiddleSize));
        mCell2.addElement(mElement);
        mElement = new Phrase(mTextHashMapTitles.get("saleCode"),
                (checkStringIfHasAsciiCharacter(mTextHashMapTitles.get("saleCode")) ?
                        createEnglishFontWithSize(mSmallSize) : createFontWithSize(mSmallSize)));
        mCell2.addElement(mElement);

        setBackGroundColor_P_BW_HA_VA(mCell2, mainTitleColorTables, 10, 3, Element.ALIGN_LEFT, Element.ALIGN_CENTER);

        //region Add Columns
        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            table.addCell(mCell2);
            table.addCell(mCell1);
        } else {
            table.addCell(mCell1);
            table.addCell(mCell2);
        }
        //endregion Add Columns

        _mDocument.add(table);
    }

    private static void createInfoTableForInvoiceItems(ArrayList<Map<String, String>> mChosenProductListMap)
            throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(5);
        table.setWidths(new int[]{25, 50, 40, 45, 45});

        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            table.setWidths(new int[]{45, 45, 40, 50, 25});

        //region Add Titles For Items In Invoice
        PdfPCell mCell1 = new PdfPCell(new Phrase(_mContext.getString(R.string.row_title), createFontWithSize(mSmallSize)));
        setBackGroundColor_P_BW_HA_VA(mCell1, subTitleColorTables, 10, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

        PdfPCell mCell2 = new PdfPCell(new Phrase(_mContext.getString(R.string.good_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell2, subTitleColorTables, 10, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

        PdfPCell mCell3 = new PdfPCell(new Phrase(_mContext.getString(R.string.price_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell3, subTitleColorTables, 10, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

        PdfPCell mCell4 = new PdfPCell(new Phrase(_mContext.getString(R.string.quantity_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell4, subTitleColorTables, 10, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

        PdfPCell mCell5 = new PdfPCell(new Phrase(_mContext.getString(R.string.total_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell5, subTitleColorTables, 10, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

        //region Add Columns
        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            table.addCell(mCell5);
            table.addCell(mCell4);
            table.addCell(mCell3);
            table.addCell(mCell2);
            table.addCell(mCell1);
        } else {
            table.addCell(mCell1);
            table.addCell(mCell2);
            table.addCell(mCell3);
            table.addCell(mCell4);
            table.addCell(mCell5);
        }
        //endregion Add Columns

        table.setHeaderRows(1);
        //endregion Add Titles For Items In Invoice

        //region Add Data For Items In Invoice
        for (int i = 0; i < mChosenProductListMap.size(); i++) {

            mCell1 = new PdfPCell(new Phrase(String.valueOf(i + 1), createFontWithSize(mSmallSize)));
            setBackGroundColor_P_BW_HA_VA(mCell1, mainTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

            String MnameOfProduct = getProductNameWithProductId(
                    Long.valueOf(mChosenProductListMap.get(i).get("id").toString()));

            mCell2 = new PdfPCell(new Phrase(MnameOfProduct,
                    (checkStringIfHasAsciiCharacter(MnameOfProduct) ? createEnglishFontWithSize(mSmallSize) :
                            createFontWithSize(mSmallSize))));
            setBackGroundColor_P_BW_HA_VA(mCell2, mainTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

            mCell3 = new PdfPCell(new Phrase(
                    Utility.formatPurchase(
                            _mContext,
                            Utility.DecimalSeperation(_mContext,
                                    Double.parseDouble(mChosenProductListMap.get(i).get("price").toString()))),
                    createFontWithSize(mSmallSize)));
            setBackGroundColor_P_BW_HA_VA(mCell3, mainTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

            mCell4 = new PdfPCell(new Phrase(mChosenProductListMap.get(i).get("quantity").toString(), createFontWithSize(mSmallSize)));
            setBackGroundColor_P_BW_HA_VA(mCell4, mainTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

            mCell5 = new PdfPCell(new Phrase(
                    Utility.formatPurchase(
                            _mContext,
                            Utility.DecimalSeperation(_mContext,
                                    Double.parseDouble(
                                            String.valueOf(
                                                    Long.valueOf(mChosenProductListMap.get(i).get("price").toString()) *
                                                            Long.valueOf(mChosenProductListMap.get(i).get("quantity").toString()
                                                            ))))), createFontWithSize(mSmallSize)));
            setBackGroundColor_P_BW_HA_VA(mCell5, mainTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

            //region Add Columns
            if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                table.addCell(mCell5);
                table.addCell(mCell4);
                table.addCell(mCell3);
                table.addCell(mCell2);
                table.addCell(mCell1);
            } else {
                table.addCell(mCell1);
                table.addCell(mCell2);
                table.addCell(mCell3);
                table.addCell(mCell4);
                table.addCell(mCell5);
            }
            //endregion Add Columns
        }
        //endregion Add Data For Items In Invoice

        _mDocument.add(table);
    }

    private static void createTableTaxesForSalesInvoice(ArrayList<Map<String, String>> mTaxListMap)
            throws DocumentException, ParseException, IOException {
        PdfPTable table = new PdfPTable(3);
        table.setWidths(new int[]{25, 125, 60});

        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            table.setWidths(new int[]{60, 125, 25});

        //region Add Titles For Taxes In Invoice
        PdfPCell mCell1 = new PdfPCell(new Phrase(_mContext.getString(R.string.row_title), createFontWithSize(mSmallSize)));
        setBackGroundColor_P_BW_HA_VA(mCell1, subTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

        PdfPCell mCell2 = new PdfPCell(new Phrase(_mContext.getString(R.string.amount_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell2, subTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

        PdfPCell mCell3 = new PdfPCell(new Phrase(_mContext.getString(R.string.percent_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell3, subTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

        //region Add Columns
        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            table.addCell(mCell3);
            table.addCell(mCell2);
            table.addCell(mCell1);
        } else {
            table.addCell(mCell1);
            table.addCell(mCell2);
            table.addCell(mCell3);
        }
        //endregion Add Columns

        table.setHeaderRows(1);
        //endregion Add Titles For Taxes In Invoice

        //region Add Data For Taxes In Invoice
        int j = 0;
        for (int i = 0; i < mTaxListMap.size(); i++) {
            if (mTaxListMap.get(i).get("type").toString().equals(_mContext.getString(R.string.vas_title))) {
                mCell1 = new PdfPCell(new Phrase(String.valueOf(j++ + 1), createFontWithSize(mSmallSize)));
                setBackGroundColor_P_BW_HA_VA(mCell1, mainTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

                mCell2 = new PdfPCell(new Phrase(
                        Utility.formatPurchase(
                                _mContext,
                                Utility.DecimalSeperation(_mContext,
                                        Double.parseDouble(convertFarsiNumbersToDecimal(mTaxListMap.get(i).get("amount").toString()))
                                )), createFontWithSize(mSmallSize)));
                setBackGroundColor_P_BW_HA_VA(mCell2, mainTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

                mCell3 = new PdfPCell(new Phrase(
                        createFloatNumberWithString(_mContext, convertFarsiNumbersToDecimal(mTaxListMap.get(i).get("percent").toString()))
                                + " %", createFontWithSize(mSmallSize)));

                setBackGroundColor_P_BW_HA_VA(mCell3, mainTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

                //region Add Columns
                if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                    table.addCell(mCell3);
                    table.addCell(mCell2);
                    table.addCell(mCell1);
                } else {
                    table.addCell(mCell1);
                    table.addCell(mCell2);
                    table.addCell(mCell3);
                }
                //endregion Add Columns
            }
        }
        //endregion Add Data For Taxes In Invoice

        _mDocument.add(table);
    }

    private static void createTableDiscountsForSalesInvoice(ArrayList<Map<String, String>> mTaxListMap)
            throws DocumentException, ParseException, IOException {
        PdfPTable table = new PdfPTable(3);
        table.setWidths(new int[]{25, 125, 60});

        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            table.setWidths(new int[]{60, 125, 25});

        //region Add Titles For Discounts In Invoice
        PdfPCell mCell1 = new PdfPCell(new Phrase(_mContext.getString(R.string.row_title), createFontWithSize(mSmallSize)));
        setBackGroundColor_P_BW_HA_VA(mCell1, subTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

        PdfPCell mCell2 = new PdfPCell(new Phrase(_mContext.getString(R.string.amount_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell2, subTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

        PdfPCell mCell3 = new PdfPCell(new Phrase(_mContext.getString(R.string.percent_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell3, subTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

        //region Add Columns
        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            table.addCell(mCell3);
            table.addCell(mCell2);
            table.addCell(mCell1);
        } else {
            table.addCell(mCell1);
            table.addCell(mCell2);
            table.addCell(mCell3);
        }
        //endregion Add Columns

        table.setHeaderRows(1);
        //endregion Add Titles For Taxes In Invoice

        //region Add Data For Discounts In Invoice
        int j = 0;
        for (int i = 0; i < mTaxListMap.size(); i++) {

            if (mTaxListMap.get(i).get("type").equals(_mContext.getString(R.string.discount_title))) {
                mCell1 = new PdfPCell(new Phrase(String.valueOf(j++ + 1), createFontWithSize(mSmallSize)));
                setBackGroundColor_P_BW_HA_VA(mCell1, mainTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

                mCell2 = new PdfPCell(new Phrase(
                        Utility.formatPurchase(
                                _mContext,
                                Utility.DecimalSeperation(_mContext,
                                        Double.parseDouble(convertFarsiNumbersToDecimal(mTaxListMap.get(i).get("amount")))
                                )), createFontWithSize(mSmallSize)));
                setBackGroundColor_P_BW_HA_VA(mCell2, mainTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

                mCell3 = new PdfPCell(new Phrase(
                        createFloatNumberWithString(_mContext, convertFarsiNumbersToDecimal(mTaxListMap.get(i).get("percent")))
                                + " %", createFontWithSize(mSmallSize)));

                mCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                mCell3.setVerticalAlignment(Element.ALIGN_CENTER);
                mCell3.setBorderWidth(3);
                mCell3.setPadding(5);

                //region Add Columns
                if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                    table.addCell(mCell3);
                    table.addCell(mCell2);
                    table.addCell(mCell1);
                } else {
                    table.addCell(mCell1);
                    table.addCell(mCell2);
                    table.addCell(mCell3);
                }
                //endregion Add Columns
            }
        }
        //endregion Add Data For Discounts In Invoice

        _mDocument.add(table);
    }

    private static void createTablePaymentsForSalesInvoice(ArrayList<Map<String, String>> mPaymentListMap)
            throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(3);
        table.setWidths(new int[]{25, 125, 60});

        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            table.setWidths(new int[]{60, 125, 25});

        //region Add Titles In Payments In Invoice
        PdfPCell mCell1 = new PdfPCell(new Phrase(_mContext.getString(R.string.row_title), createFontWithSize(mSmallSize)));
        setBackGroundColor_P_BW_HA_VA(mCell1, subTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

        PdfPCell mCell2 = new PdfPCell(new Phrase(_mContext.getString(R.string.type_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell2, subTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

        PdfPCell mCell3 = new PdfPCell(new Phrase(_mContext.getString(R.string.amount_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell3, subTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

        //region Add Columns
        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            table.addCell(mCell3);
            table.addCell(mCell2);
            table.addCell(mCell1);
        } else {
            table.addCell(mCell1);
            table.addCell(mCell2);
            table.addCell(mCell3);
        }
        //endregion Add Columns

        table.setHeaderRows(1);
        //endregion Add Titles In Payments In Invoice

        //region Add Data In Payments In Invoice
        for (int i = 0; i < mPaymentListMap.size(); i++) {
            mCell1 = new PdfPCell(new Phrase(String.valueOf(i + 1), createFontWithSize(mSmallSize)));
            setBackGroundColor_P_BW_HA_VA(mCell1, mainTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

            String mType = mPaymentListMap.get(i).get("type").toString();
            if (mType.equals(_mContext.getString(R.string.cheque_title)))
                mType += (mPaymentListMap.get(i).get("isPass").toString().equals("true") ?
                        _mContext.getString(R.string.passed_cheque_title) : _mContext.getString(R.string.not_passed_cheque_title));

            mCell2 = new PdfPCell(new Phrase(mType, (checkStringIfHasAsciiCharacter(mType) ? createEnglishFontWithSize(mSmallSize) :
                    createFontWithSize(mSmallSize))));
            setBackGroundColor_P_BW_HA_VA(mCell2, mainTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

            mCell3 = new PdfPCell(new Phrase(
                    Utility.formatPurchase(
                            _mContext,
                            Utility.DecimalSeperation(_mContext,
                                    Double.parseDouble(mPaymentListMap.get(i).get("amount").toString())
                            )), createFontWithSize(mSmallSize)));
            setBackGroundColor_P_BW_HA_VA(mCell3, mainTitleColorTables, 5, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

            //region Add Columns
            if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                table.addCell(mCell3);
                table.addCell(mCell2);
                table.addCell(mCell1);
            } else {
                table.addCell(mCell1);
                table.addCell(mCell2);
                table.addCell(mCell3);
            }
            //endregion Add Columns
        }
        //endregion Add Data In Payments In Invoice

        _mDocument.add(table);
    }

    private static void createTableSummaryForSalesInvoice(HashMap<String, String> mSummaryHashMap)
            throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{70, 140});

        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            table.setWidths(new int[]{140, 70});

        //region Add Total Amount To Invoice
        PdfPCell mCell1 = new PdfPCell(new Phrase(_mContext.getString(R.string.total_amount_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell1, subTitleColorTables, 10, 3, Element.ALIGN_RIGHT, Element.ALIGN_CENTER);
        setPaddingTopAndButtom(mCell1);

        PdfPCell mCell2 = new PdfPCell(new Phrase(mSummaryHashMap.get("totalAmount"), createFontWithSize(mSmallSize)));
        setBackGroundColor_P_BW_HA_VA(mCell2, mainTitleColorTables, 5, 3, Element.ALIGN_LEFT, Element.ALIGN_CENTER);

        //region Add Columns
        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            table.addCell(mCell2);
            table.addCell(mCell1);
        } else {
            table.addCell(mCell1);
            table.addCell(mCell2);
        }
        //endregion Add Columns
        //endregion Add Total Amount To Invoice

        //region Add Total Tax To Invoice
        mCell1 = new PdfPCell(new Phrase(_mContext.getString(R.string.total_tax_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell1, subTitleColorTables, 10, 3, Element.ALIGN_RIGHT, Element.ALIGN_CENTER);
        setPaddingTopAndButtom(mCell1);

        mCell2 = new PdfPCell(new Phrase(mSummaryHashMap.get("totalTax"), createFontWithSize(mSmallSize)));
        setBackGroundColor_P_BW_HA_VA(mCell2, mainTitleColorTables, 5, 3, Element.ALIGN_LEFT, Element.ALIGN_CENTER);

        //region Add Columns
        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            table.addCell(mCell2);
            table.addCell(mCell1);
        } else {
            table.addCell(mCell1);
            table.addCell(mCell2);
        }
        //endregion Add Columns
        //endregion Add Total Tax To Invoice

        //region Add Total Discount To Invoice
        mCell1 = new PdfPCell(new Phrase(_mContext.getString(R.string.total_discount_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell1, subTitleColorTables, 10, 3, Element.ALIGN_RIGHT, Element.ALIGN_CENTER);
        setPaddingTopAndButtom(mCell1);

        mCell2 = new PdfPCell(new Phrase(mSummaryHashMap.get("totalDiscount"), createFontWithSize(mSmallSize)));
        setBackGroundColor_P_BW_HA_VA(mCell2, mainTitleColorTables, 5, 3, Element.ALIGN_LEFT, Element.ALIGN_CENTER);

        //region Add Columns
        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            table.addCell(mCell2);
            table.addCell(mCell1);
        } else {
            table.addCell(mCell1);
            table.addCell(mCell2);
        }
        //endregion Add Columns
        //endregion Add Total Discount To Invoice

        //region Add Total Paied To Invoice
        mCell1 = new PdfPCell(new Phrase(_mContext.getString(R.string.total_paied_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell1, subTitleColorTables, 10, 3, Element.ALIGN_RIGHT, Element.ALIGN_CENTER);
        setPaddingTopAndButtom(mCell1);

        mCell2 = new PdfPCell(new Phrase(mSummaryHashMap.get("totalPaied"), createFontWithSize(mSmallSize)));
        setBackGroundColor_P_BW_HA_VA(mCell2, mainTitleColorTables, 5, 3, Element.ALIGN_LEFT, Element.ALIGN_CENTER);

        //region Add Columns
        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            table.addCell(mCell2);
            table.addCell(mCell1);
        } else {
            table.addCell(mCell1);
            table.addCell(mCell2);
        }
        //endregion Add Columns
        //endregion Add Total Paied To Invoice

        //region Add Total UnPaied To Invoice
        mCell1 = new PdfPCell(new Phrase(_mContext.getString(R.string.total_unpaied_title), createFontWithSize(mMiddleSize)));
        setBackGroundColor_P_BW_HA_VA(mCell1, subTitleColorTables, 10, 3, Element.ALIGN_RIGHT, Element.ALIGN_CENTER);
        setPaddingTopAndButtom(mCell1);

        mCell2 = new PdfPCell(new Phrase(mSummaryHashMap.get("totalUnpaied"), createFontWithSize(mSmallSize)));
        setBackGroundColor_P_BW_HA_VA(mCell2, mainTitleColorTables, 5, 3, Element.ALIGN_LEFT, Element.ALIGN_CENTER);

        //region Add Columns
        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            table.addCell(mCell2);
            table.addCell(mCell1);
        } else {
            table.addCell(mCell1);
            table.addCell(mCell2);
        }
        //endregion Add Columns
        //endregion Add Total UnPaied To Invoice

        _mDocument.add(table);
    }

    private static void createTableEndInfoForSalesInvoice()
            throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{105, 105});

        PdfPCell mCell = new PdfPCell(new Phrase());
        setBackGroundColor_P_BW_HA_VA(mCell, subTitleColorTables, 0, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);
        table.addCell(mCell);

        mCell = new PdfPCell(new Phrase());
        setBackGroundColor_P_BW_HA_VA(mCell, subTitleColorTables, 0, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);
        table.addCell(mCell);

        table.setHeaderRows(1);

        mCell = new PdfPCell(new Phrase(_mContext.getString(R.string.stamp_and_signature), createFontWithSize(mBigSize)));
        setBackGroundColor_P_BW_HA_VA(mCell, subTitleColorTables, 20, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);
        setPaddingTopAndButtom(mCell);
        mCell.setColspan(2);
        table.addCell(mCell);

        mCell = new PdfPCell(new Phrase(_mContext.getString(R.string.seller_title), createFontWithSize(mBigSize)));
        setBackGroundColor_P_BW_HA_VA(mCell, subTitleColorTables, 20, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);
        setPaddingTopAndButtom(mCell);
        table.addCell(mCell);

        mCell = new PdfPCell(new Phrase(_mContext.getString(R.string.buyer_title), createFontWithSize(mBigSize)));
        setBackGroundColor_P_BW_HA_VA(mCell, subTitleColorTables, 20, 3, Element.ALIGN_CENTER, Element.ALIGN_CENTER);
        setPaddingTopAndButtom(mCell);
        table.addCell(mCell);

        mCell = new PdfPCell(new Phrase("\n\n\n\n\n\n\n"));
        mCell.setBorderWidth(3);
        table.addCell(mCell);

        mCell = new PdfPCell(new Phrase("\n\n\n\n\n\n\n"));
        mCell.setBorderWidth(3);
        table.addCell(mCell);

        _mDocument.add(table);
    }

    private static void setBackGroundColor_P_BW_HA_VA(
            PdfPCell mPdfPCell, BaseColor mBaseColor, int mPadding, int mBorderWidth,
            int mHorizontalAlignment, int mVerticalAlignment) {
        mPdfPCell.setHorizontalAlignment(mHorizontalAlignment);
        mPdfPCell.setVerticalAlignment(mVerticalAlignment);
        mPdfPCell.setBorderWidth(mBorderWidth);
        mPdfPCell.setPadding(mPadding);
        mPdfPCell.setBackgroundColor(mBaseColor);
        mPdfPCell.setPaddingBottom(10);

        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            mPdfPCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
    }

    private static void setRtlToCell(PdfPCell mPdfPCell) {
        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            mPdfPCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
    }

    public static float createFloatNumberWithString(Context mContext, String mNumber) throws ParseException {
        if (mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            return NumberFormat.getInstance(Locale.forLanguageTag("es")).parse(mNumber).floatValue();
        else
            return Float.valueOf(mNumber);
    }

    private static Font createFontWithSize(int mSize) throws IOException, DocumentException {
        if (_mContext.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            BaseFont mUnicodeFontName = BaseFont.createFont("assets/fonts/bmitra.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            return new Font(mUnicodeFontName, mSize, Font.BOLD);
        } else
            return new Font(Font.FontFamily.TIMES_ROMAN, mSize, Font.BOLD);
    }

    private static Font createEnglishFontWithSize(int mSize) throws IOException, DocumentException {
        return new Font(Font.FontFamily.TIMES_ROMAN, mSize, Font.BOLD);
    }

    private static String getProductNameWithProductId(Long mProductId) {
        Cursor mCursor = _mContext.getContentResolver().query(
                KasebContract.Products.buildProductsUri(mProductId),
                new String[]{KasebContract.Products.COLUMN_PRODUCT_NAME},
                null,
                null,
                null
        );

        if (mCursor != null)
            if (mCursor.moveToFirst())
                return mCursor.getString(mCursor.getColumnIndex(KasebContract.Products.COLUMN_PRODUCT_NAME));

        mCursor.close();

        return null;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public static void viewPdf(Context mContext, File mFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(mFile), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        mContext.startActivity(intent);
    }

    private static int numberOfTypes(ArrayList<Map<String, String>> mHashMapArrayList, String mType) {
        int j = 0;
        for (int i = 0; i < mHashMapArrayList.size(); i++)
            if (mHashMapArrayList.get(i).get("type").equals(mType))
                j++;
        return j;
    }

    private static void setPaddingTopAndButtom(PdfPCell mPdfPCell) {
        mPdfPCell.setPaddingBottom(15);
        mPdfPCell.setPaddingTop(2);
    }

    private static boolean checkStringIfHasAsciiCharacter(String mValue) {
        for (int i = 0; i < mValue.length(); i++) {
            int mAscii = mValue.charAt(i);
            if ((mAscii > 64 && mAscii < 91) || (mAscii > 96 && mAscii < 123))
                return true;
        }

        return false;
    }

    public static String convertFarsiNumbersToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    public static void setActivityTransition(Activity activity) {
        Configuration config = activity.getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    public static void stylePie(PieChart statePie, String noDataTxt) {
        statePie.setNoDataText(noDataTxt);
        statePie.setCenterTextSize(16f);
        statePie.invalidate(); // refresh
        statePie.notifyDataSetChanged();
        statePie.setDescription(null);
    }

    public static void styleLegend(Legend pieLegend) {
        pieLegend.setTextSize(14f);
        if (pieLegend != null)
            pieLegend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
    }

    public static void activityOnBackExit(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.close_activity_dialog_title)
                .setMessage(R.string.close_activity_dialog_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finishAffinity();
                    }

                })
                .setNegativeButton(android.R.string.no, null)
                .show();

    }

    public static void createTypeFaceWithSize(TextView mTextView) throws IOException, DocumentException {
        Typeface mTypeface;
        if (mTextView.getContext().getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            mTypeface = Typeface.createFromAsset(
                    mTextView.getContext().getApplicationContext().getAssets(), "fonts/bmitra.ttf");
        } else
            mTypeface = Typeface.create("sans-serif-light", Typeface.BOLD);

        mTextView.setTypeface(mTypeface);
    }

    public static void setStatusEnableForAllChildrenOfLinearLayout(LinearLayout mLinearLayout, Boolean mEnabled) {
        for (int i = 0; i < mLinearLayout.getChildCount(); i++)
            mLinearLayout.getChildAt(i).setEnabled(mEnabled);
    }
}
