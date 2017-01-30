package mjkarbasian.moshtarimadar.Tool;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import mjkarbasian.moshtarimadar.Data.KasebContract;

/**
 * Created by Unique on 10/4/2016.
 */
public class TestUtilities extends AndroidTestCase {

    public static long sample_number = 100;
    public static String sample_text = "sample100";

    //region 1 State table createValues
    public static ContentValues createStateValues() {
        ContentValues StateValues = new ContentValues();
        StateValues.put(KasebContract.State.COLUMN_STATE_POINTER, sample_number++);
        return StateValues;
    }
    //endregion

    //region 2 Sales table createValues
    public static ContentValues createSalesValues(long CustomersRowId) {
        ContentValues SalesValues = new ContentValues();
        SalesValues.put(KasebContract.Sales.COLUMN_CUSTOMER_ID, CustomersRowId);
        SalesValues.put(KasebContract.Sales.COLUMN_SALE_CODE, sample_text + (sample_number++));
        SalesValues.put(KasebContract.Sales.COLUMN_IS_DELETED, 0);

        return SalesValues;
    }
    //endregion

    //region 3 Products table createValues
    public static ContentValues createProductsValues() {
        ContentValues ProductsValues = new ContentValues();
        ProductsValues.put(KasebContract.Products.COLUMN_PRODUCT_NAME, sample_text + (sample_number++));
        ProductsValues.put(KasebContract.Products.COLUMN_PRODUCT_CODE, sample_text + (sample_number++));
        ProductsValues.put(KasebContract.Products.COLUMN_DESCRIPTION, sample_text + (sample_number++));
        ProductsValues.put(KasebContract.Products.COLUMN_UNIT, sample_text + (sample_number++));

        return ProductsValues;
    }
    //endregion

    //region 4 Costs table createValues
    public static ContentValues createCostsValues(long CostTypesRowId) {
        ContentValues CostsValues = new ContentValues();
        CostsValues.put(KasebContract.Costs.COLUMN_COST_TYPE_ID, CostTypesRowId);
        CostsValues.put(KasebContract.Costs.COLUMN_COST_NAME, sample_text + (sample_number++));
        CostsValues.put(KasebContract.Costs.COLUMN_COST_CODE, sample_text + (sample_number++));
        CostsValues.put(KasebContract.Costs.COLUMN_AMOUNT, sample_number++);
        CostsValues.put(KasebContract.Costs.COLUMN_DATE, sample_text + (sample_number++));
        CostsValues.put(KasebContract.Costs.COLUMN_DESCRIPTION, sample_text + (sample_number++));

        return CostsValues;
    }
    //endregion

    //region 5 PaymentMethods table createValues
    public static ContentValues createPaymentMethodsValues() {
        ContentValues PaymentMethodsValues = new ContentValues();
        PaymentMethodsValues.put(KasebContract.PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER, sample_number++);
        return PaymentMethodsValues;
    }
    //endregion

    //region 6 TaxTypes table createValues
    public static ContentValues createTaxTypesValues() {
        ContentValues TaxTypesValues = new ContentValues();
        TaxTypesValues.put(KasebContract.TaxTypes.COLUMN_TAX_TYPE_POINTER, sample_number++);
        return TaxTypesValues;
    }
    //endregion

    //region 7 ProductHistory table createValues
    public static ContentValues createProductHistoryValues(long ProductsRowId) {
        ContentValues ProductHistoryValues = new ContentValues();
        ProductHistoryValues.put(KasebContract.ProductHistory.COLUMN_PRODUCT_ID, ProductsRowId);
        ProductHistoryValues.put(KasebContract.ProductHistory.COLUMN_COST, sample_number++);
        ProductHistoryValues.put(KasebContract.ProductHistory.COLUMN_DATE, sample_text + (sample_number++));
        ProductHistoryValues.put(KasebContract.ProductHistory.COLUMN_QUANTITY, sample_number++);
        ProductHistoryValues.put(KasebContract.ProductHistory.COLUMN_SALE_PRICE, sample_number++);

        return ProductHistoryValues;
    }
    //endregion

    //region 8 DetailSaleTaxes table createValues
    public static ContentValues createDetailSaleTaxesValues(long DetailSaleRowId, long TaxtypesRowId) {
        ContentValues DetailSaleTaxesValues = new ContentValues();
        DetailSaleTaxesValues.put(KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, DetailSaleRowId);
        DetailSaleTaxesValues.put(KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID, TaxtypesRowId);
        DetailSaleTaxesValues.put(KasebContract.DetailSaleTaxes.COLUMN_AMOUNT, sample_number++);
        return DetailSaleTaxesValues;
    }
    //endregion

    //region 9 DetailSaleProducts table createValues
    public static ContentValues createDetailSaleProductsValues(long ProductsRowId, long DetailSaleRowId) {
        ContentValues DetailSaleProductsValues = new ContentValues();
        DetailSaleProductsValues.put(KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID, ProductsRowId);
        DetailSaleProductsValues.put(KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID, DetailSaleRowId);
        DetailSaleProductsValues.put(KasebContract.DetailSaleProducts.COLUMN_QUANTITY, sample_number++);
        DetailSaleProductsValues.put(KasebContract.DetailSaleProducts.COLUMN_AMOUNT, sample_number++);

        return DetailSaleProductsValues;
    }
    //endregion

    //region 10 CostTypes table createValues
    public static ContentValues createCostTypesValues() {
        ContentValues CostTypesValues = new ContentValues();
        CostTypesValues.put(KasebContract.CostTypes.COLUMN_COST_TYPE_POINTER, sample_text + (sample_number++));
        return CostTypesValues;
    }
    //endregion

    //region 11 DetailSale table createValues
    public static ContentValues createDetailSaleValues(long SalesRowId) {
        ContentValues DetailSaleValues = new ContentValues();
        DetailSaleValues.put(KasebContract.DetailSale.COLUMN_SALE_ID, SalesRowId);
        DetailSaleValues.put(KasebContract.DetailSale.COLUMN_DATE, sample_text + (sample_number++));
        DetailSaleValues.put(KasebContract.DetailSale.COLUMN_ITEMS_NUMBER, sample_number++);
        DetailSaleValues.put(KasebContract.DetailSale.COLUMN_SUB_TOTAL, sample_number++);
        DetailSaleValues.put(KasebContract.DetailSale.COLUMN_TOTAL_TAX, sample_number++);
        DetailSaleValues.put(KasebContract.DetailSale.COLUMN_TOTAL_DISCOUNT, sample_number++);
        DetailSaleValues.put(KasebContract.DetailSale.COLUMN_TOTAL_DUE, sample_number++);
        DetailSaleValues.put(KasebContract.DetailSale.COLUMN_TOTAL_PAID, sample_number++);
        DetailSaleValues.put(KasebContract.DetailSale.COLUMN_IS_BALANCED, 0);
        return DetailSaleValues;
    }
    //endregion

    //region 12 DetailSalePayments table createValues
    public static ContentValues createDetailSalePaymentsValues(long DetailSaleRowId, long PaymentMethodsRowId) {
        ContentValues DetailSalePaymentsValues = new ContentValues();
        DetailSalePaymentsValues.put(KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID, DetailSaleRowId);
        DetailSalePaymentsValues.put(KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, PaymentMethodsRowId);
        DetailSalePaymentsValues.put(KasebContract.DetailSalePayments.COLUMN_DUE_DATE, sample_text + (sample_number++));
        DetailSalePaymentsValues.put(KasebContract.DetailSalePayments.COLUMN_AMOUNT, sample_number++);

        return DetailSalePaymentsValues;
    }
    //endregion

    //region 13 Customers table createValues
    public static ContentValues createCustomersValues(long StateRowId) {
        ContentValues CustomersValues = new ContentValues();

        CustomersValues.put(KasebContract.Customers.COLUMN_FIRST_NAME, sample_text + (sample_number++));
        CustomersValues.put(KasebContract.Customers.COLUMN_LAST_NAME, sample_text + (sample_number++));
        CustomersValues.put(KasebContract.Customers.COLUMN_BIRTHDAY, sample_text + (sample_number++));
        CustomersValues.put(KasebContract.Customers.COLUMN_STATE_ID, StateRowId);
        CustomersValues.put(KasebContract.Customers.COLUMN_PHONE_MOBILE, (new Date().getTime()));
        CustomersValues.put(KasebContract.Customers.COLUMN_DESCRIPTION, sample_text + (sample_number++));
        CustomersValues.put(KasebContract.Customers.COLUMN_IS_DELETED, 0);
        CustomersValues.put(KasebContract.Customers.COLUMN_EMAIL, sample_text + (sample_number++));
        CustomersValues.put(KasebContract.Customers.COLUMN_PHONE_WORK, sample_text + (sample_number++));
        CustomersValues.put(KasebContract.Customers.COLUMN_PHONE_OTHER, sample_text + (sample_number++));
        CustomersValues.put(KasebContract.Customers.COLUMN_PHONE_FAX, sample_text + (sample_number++));
        CustomersValues.put(KasebContract.Customers.COLUMN_ADDRESS_COUNTRY, sample_text + (sample_number++));
        CustomersValues.put(KasebContract.Customers.COLUMN_ADDRESS_CITY, sample_text + (sample_number++));
        CustomersValues.put(KasebContract.Customers.COLUMN_ADDRESS_STREET, sample_text + (sample_number++));
        CustomersValues.put(KasebContract.Customers.COLUMN_ADDRESS_POSTAL_CODE, sample_text + (sample_number++));

        return CustomersValues;
    }
    //endregion


    public static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues, String tablename) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. in -" + tablename + "table- " + error, idx == -1);

            String expectedValue = entry.getValue().toString();
            if (valueCursor.getType(idx) != Cursor.FIELD_TYPE_BLOB) {
                assertEquals("Value '" + entry.getValue().toString() +
                        "' did not match the expected value '" +
                        expectedValue + "'. " + error + " in -" + tablename + " table- ", expectedValue, valueCursor.getString(idx));
            }
        }
    }

    public static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues, String tablename) {
        assertTrue("Empty cursor returned. " + error + " in -" + tablename + " table- ", valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues, tablename);
        valueCursor.close();
    }

    public static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }

    public static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
//            new PollingCheck(5000) {
//                @Override
//                protected boolean check() {
//                    return mContentChanged;
//                }
//            }.run();
//            mHT.quit();
        }
    }
}