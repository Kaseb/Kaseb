package mjkarbasian.moshtarimadar.Data;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import mjkarbasian.moshtarimadar.Tool.TestUtilities;

import static mjkarbasian.moshtarimadar.Data.KasebContract.CONTENT_AUTHORITY;
import static mjkarbasian.moshtarimadar.Data.KasebContract.CostTypes;
import static mjkarbasian.moshtarimadar.Data.KasebContract.Costs;
import static mjkarbasian.moshtarimadar.Data.KasebContract.Customers;
import static mjkarbasian.moshtarimadar.Data.KasebContract.DetailSale;
import static mjkarbasian.moshtarimadar.Data.KasebContract.DetailSalePayments;
import static mjkarbasian.moshtarimadar.Data.KasebContract.DetailSaleProducts;
import static mjkarbasian.moshtarimadar.Data.KasebContract.DetailSaleTaxes;
import static mjkarbasian.moshtarimadar.Data.KasebContract.PaymentMethods;
import static mjkarbasian.moshtarimadar.Data.KasebContract.ProductHistory;
import static mjkarbasian.moshtarimadar.Data.KasebContract.Products;
import static mjkarbasian.moshtarimadar.Data.KasebContract.Sales;
import static mjkarbasian.moshtarimadar.Data.KasebContract.State;
import static mjkarbasian.moshtarimadar.Data.KasebContract.TaxTypes;

/**
 * Created by Unique on 10/13/2016.
 */
public class TestKasebProvider extends AndroidTestCase {

    final static String LOG_TAG = TestKasebProvider.class.getSimpleName();
    static private final int BULK_INSERT_RECORDS_TO_INSERT = 10;
    public static long sample_number = 100;
    KasebProvider ojectKasebProvider = new KasebProvider();

    // Since we want each test to start with a clean slate, run deleteAllRecords
    // in setUp (called by the test runner before each test).
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    private void deleteAllRecords() {
        deleteAllRecordsFromProvider();
    }

    private void deleteAllRecordsFromProvider() {
        //region 4 Delete records & Check Deleted records -Costs table-
        mContext.getContentResolver().delete(
                KasebContract.Costs.CONTENT_URI,
                null,
                null
        );

        Cursor costsCursor = mContext.getContentResolver().query(
                KasebContract.Costs.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from -Costs table- during delete", 0, costsCursor.getCount());
        costsCursor.close();
        //endregion

        //region 10 Delete records & Check Deleted records -DetailSalePayments table-
        mContext.getContentResolver().delete(
                KasebContract.DetailSalePayments.CONTENT_URI,
                null,
                null
        );

        Cursor detailSalePaymentsCursor = mContext.getContentResolver().query(
                KasebContract.DetailSalePayments.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from -DetailSalePayments table- during delete", 0, detailSalePaymentsCursor.getCount());
        detailSalePaymentsCursor.close();
        //endregion

        //region 12 Delete records & Check Deleted records -DetailSaleTaxes table-
        mContext.getContentResolver().delete(
                KasebContract.DetailSaleTaxes.CONTENT_URI,
                null,
                null
        );

        Cursor detailSaleTaxesCursor = mContext.getContentResolver().query(
                KasebContract.DetailSaleTaxes.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from -DetailSaleTaxes table- during delete", 0, detailSaleTaxesCursor.getCount());
        detailSaleTaxesCursor.close();
        //endregion

        //region 11 Delete records & Check Deleted records -ProductHistory table-
        mContext.getContentResolver().delete(
                KasebContract.ProductHistory.CONTENT_URI,
                null,
                null
        );

        Cursor productHistoryCursor = mContext.getContentResolver().query(
                KasebContract.ProductHistory.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from -ProductHistory table- during delete", 0, productHistoryCursor.getCount());
        productHistoryCursor.close();
        //endregion

        //region 13 Delete records & Check Deleted records -DetailSaleProducts table-
        mContext.getContentResolver().delete(
                KasebContract.DetailSaleProducts.CONTENT_URI,
                null,
                null
        );

        Cursor detailSaleProductsCursor = mContext.getContentResolver().query(
                KasebContract.DetailSaleProducts.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from -DetailSaleProducts table- during delete", 0, detailSaleProductsCursor.getCount());
        detailSaleProductsCursor.close();
        //endregion

        //region 9 Delete records & Check Deleted records -DetailSale table-
        mContext.getContentResolver().delete(
                KasebContract.DetailSale.CONTENT_URI,
                null,
                null
        );

        Cursor detailSaleCursor = mContext.getContentResolver().query(
                KasebContract.DetailSale.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from -DetailSale table- during delete", 0, detailSaleCursor.getCount());
        detailSaleCursor.close();
        //endregion

        //region 8 Delete records & Check Deleted records -Sales table-
        mContext.getContentResolver().delete(
                KasebContract.Sales.CONTENT_URI,
                null,
                null
        );

        Cursor salesCursor = mContext.getContentResolver().query(
                KasebContract.Sales.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from -Sales table- during delete", 0, salesCursor.getCount());
        salesCursor.close();
        //endregion

        //region 3 Delete records & Check Deleted records -Customers table-
        mContext.getContentResolver().delete(
                KasebContract.Customers.CONTENT_URI,
                null,
                null
        );

        Cursor customersCursor = mContext.getContentResolver().query(
                KasebContract.Customers.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from -Customers table- during delete", 0, customersCursor.getCount());
        customersCursor.close();
        //endregion

        //region 7 Delete records & Check Deleted records -Products table-
        mContext.getContentResolver().delete(
                KasebContract.Products.CONTENT_URI,
                null,
                null
        );

        Cursor productsCursor = mContext.getContentResolver().query(
                KasebContract.Products.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from -Products table- during delete", 0, productsCursor.getCount());
        productsCursor.close();
        //endregion

        //region 1 Delete records & Check Deleted records -State table-
        mContext.getContentResolver().delete(
                KasebContract.State.CONTENT_URI,
                null,
                null
        );

        Cursor stateCursor = mContext.getContentResolver().query(
                KasebContract.State.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from -State table- during delete", 0, stateCursor.getCount());
        stateCursor.close();
        //endregion

        //region 2 Delete records & Check Deleted records -CostTypes table-
        mContext.getContentResolver().delete(
                KasebContract.CostTypes.CONTENT_URI,
                null,
                null
        );

        Cursor costTypesCursor = mContext.getContentResolver().query(
                KasebContract.CostTypes.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from -CostTypes table- during delete", 0, costTypesCursor.getCount());
        costTypesCursor.close();
        //endregion

        //region 5 Delete records & Check Deleted records -PaymentMethods table-
        mContext.getContentResolver().delete(
                KasebContract.PaymentMethods.CONTENT_URI,
                null,
                null
        );

        Cursor paymentMethodsCursor = mContext.getContentResolver().query(
                KasebContract.PaymentMethods.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from -PaymentMethods table- during delete", 0, paymentMethodsCursor.getCount());
        paymentMethodsCursor.close();
        //endregion

        //region 6 Delete records & Check Deleted records -TaxTypes table-
        mContext.getContentResolver().delete(
                KasebContract.TaxTypes.CONTENT_URI,
                null,
                null
        );

        Cursor taxTypesCursor = mContext.getContentResolver().query(
                KasebContract.TaxTypes.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from -TaxTypes table- during delete", 0, taxTypesCursor.getCount());
        taxTypesCursor.close();
        //endregion
    }

    public void testGetType() {

        //region 1 State
        Long State_id = sample_number++;
        Uri g = KasebContract.State.buildStateUri(State_id);
        String stateType = ojectKasebProvider.getType(KasebContract.State.buildStateUri(State_id));
        assertEquals("Error: StateUri type was not correct", KasebContract.State.CONTENT_ITEM_TYPE, stateType);
        //endregion 1

        //region 2 CostTypes
        Long CostTypes_id = sample_number++;
        Uri d = KasebContract.CostTypes.buildCostTypesUri(CostTypes_id);
        String costTypesType = ojectKasebProvider.getType(KasebContract.CostTypes.buildCostTypesUri(CostTypes_id));
        assertEquals("Error: CostTypesUri type was not correct", KasebContract.CostTypes.CONTENT_ITEM_TYPE, costTypesType);
        //endregion 3

        //region 3 Customers
        Long customers_id = sample_number++;
        String customersType = ojectKasebProvider.getType(KasebContract.Customers.buildCustomerUri(customers_id));
        assertEquals("Error: CustomersUri type was not correct", KasebContract.Customers.CONTENT_ITEM_TYPE, customersType);
        //endregion 3

        //region 4 Costs
        Long costs_id = sample_number++;
        String CostsType = ojectKasebProvider.getType(KasebContract.Costs.buildCostsUri(costs_id));
        assertEquals("Error: CostsUri type was not correct", KasebContract.Costs.CONTENT_ITEM_TYPE, CostsType);
        //endregion 4

        //region 5 PaymentMethods
        Long paymentMethods_id = sample_number++;
        String PaymentMethodsType = ojectKasebProvider.getType(KasebContract.PaymentMethods.buildPaymentMethodsUri(paymentMethods_id));
        assertEquals("Error: PaymentMethodsUri type was not correct", KasebContract.PaymentMethods.CONTENT_ITEM_TYPE, PaymentMethodsType);
        //endregion 5

        //region 6 TaxTypes
        Long TaxTypes_id = sample_number++;
        String taxTypesType = ojectKasebProvider.getType(KasebContract.TaxTypes.buildTaxTypesUri(TaxTypes_id));
        assertEquals("Error: TaxTypesUri type was not correct", KasebContract.TaxTypes.CONTENT_ITEM_TYPE, taxTypesType);
        //endregion 6

        //region 7 Products
        Long Products_id = sample_number++;
        String productsType = ojectKasebProvider.getType(KasebContract.Products.buildProductsUri(Products_id));
        assertEquals("Error: ProductsUri type was not correct", KasebContract.Products.CONTENT_ITEM_TYPE, productsType);
        //endregion 7

        //region 8 Sales
        Long Sales_id = sample_number++;
        String salesType = ojectKasebProvider.getType(KasebContract.Sales.buildSalesUri(Sales_id));
        assertEquals("Error: SalesUri type was not correct", KasebContract.Sales.CONTENT_ITEM_TYPE, salesType);
        //endregion 8

        //region 9 DetailSale
        Long DetailSale_id = sample_number++;
        String detailSaleType = ojectKasebProvider.getType(KasebContract.DetailSale.buildDetailSaleUri(DetailSale_id));
        assertEquals("Error: DetailSaleUri type was not correct", KasebContract.DetailSale.CONTENT_ITEM_TYPE, detailSaleType);
        //endregion 9

        //region 10 DetailSalePayments
        Long DetailSalePayments_id = sample_number++;
        String detailSalePaymentsType = ojectKasebProvider.getType(KasebContract.DetailSalePayments.buildDetailSalePaymentsUri(DetailSalePayments_id));
        assertEquals("Error: DetailSalePaymentsUri type was not correct", KasebContract.DetailSalePayments.CONTENT_ITEM_TYPE, detailSalePaymentsType);
        //endregion 10

        //region 11 ProductHistory
        Long ProductHistory_id = sample_number++;
        String productHistoryType = ojectKasebProvider.getType(KasebContract.ProductHistory.buildProductHistoryUri(ProductHistory_id));
        assertEquals("Error: ProductHistoryUri type was not correct", KasebContract.ProductHistory.CONTENT_ITEM_TYPE, productHistoryType);
        //endregion 11

        //region 12 DetailSaleTaxes
        Long DetailSaleTaxes_id = sample_number++;
        String detailSaleTaxesType = ojectKasebProvider.getType(KasebContract.DetailSaleTaxes.buildDetailSaleTaxesUri(DetailSaleTaxes_id));
        assertEquals("Error: DetailSaleTaxesUri type was not correct", KasebContract.DetailSaleTaxes.CONTENT_ITEM_TYPE, detailSaleTaxesType);
        //endregion 12

        //region 13 DetailSaleProducts
        Long DetailSaleProducts_id = sample_number++;
        String detailSaleProductsType = ojectKasebProvider.getType(KasebContract.DetailSaleProducts.buildDetailSaleProducts(DetailSaleProducts_id));
        assertEquals("Error: DetailSaleProductsUri type was not correct", KasebContract.DetailSaleProducts.CONTENT_ITEM_TYPE, detailSaleProductsType);
        //endregion 13
    }

    public void testBasicQuery() {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues;

        TestUtilities.TestContentObserver observer = TestUtilities.getTestContentObserver();
        ContentValues[] bulkInsertContentValues;

        //region 1 State table
        testValues = TestUtilities.createStateValues();
        long stateRowId = db.insert(KasebContract.State.TABLE_NAME, null, testValues);
        assertTrue("Unable to insert -State table- row", stateRowId != -1);

        Cursor stateCursor = mContext.getContentResolver().query(
                KasebContract.State.buildStateUri(stateRowId),
                null,
                null,
                null,
                null
        );

        assertNotNull("stateCursor is Null", stateCursor);

        Log.i(LOG_TAG, "this is my log " + stateCursor.toString());
        TestUtilities.validateCursor("stateCursor does not match expected value",
                stateCursor, testValues, KasebContract.State.TABLE_NAME);

        stateCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 2 CostTypes table
        testValues = TestUtilities.createCostTypesValues();
        long costTypesRowId = db.insert(KasebContract.CostTypes.TABLE_NAME, null, testValues);
        assertTrue("Unable to insert -CostTypes table- row", costTypesRowId != -1);

        Cursor costTypesCursor = mContext.getContentResolver().query(
                KasebContract.CostTypes.buildCostTypesUri(costTypesRowId),
                null,
                null,
                null,
                null
        );

        assertNotNull("costTypesCursor is Null", costTypesCursor);

        Log.i(LOG_TAG, "this is my log " + costTypesCursor.toString());
        TestUtilities.validateCursor("costTypesCursor does not match expected value",
                costTypesCursor, testValues, KasebContract.CostTypes.TABLE_NAME);

        costTypesCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 3 Customers table
        testValues = TestUtilities.createCustomersValues(stateRowId);
        long customersRowId = db.insert(KasebContract.Customers.TABLE_NAME, null, testValues);
        assertTrue("Unable to insert -Customers table- row", customersRowId != -1);

        Cursor customersCursor = mContext.getContentResolver().query(
                KasebContract.Customers.buildCustomerUri(customersRowId),
                null,
                null,
                null,
                null
        );

        assertNotNull("customersCursor is Null", customersCursor);

        Log.i(LOG_TAG, "this is my log " + customersCursor.toString());
        TestUtilities.validateCursor("customersCursor does not match expected value",
                customersCursor, testValues, KasebContract.Customers.TABLE_NAME);

        customersCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 4 Costs table
        testValues = TestUtilities.createCostsValues(costTypesRowId);
        long costsRowId = db.insert(KasebContract.Costs.TABLE_NAME, null, testValues);
        assertTrue("Unable to insert -Costs table- row", costsRowId != -1);

        Cursor costsCursor = mContext.getContentResolver().query(
                KasebContract.Costs.buildCostsUri(costsRowId),
                null,
                null,
                null,
                null
        );

        assertNotNull("costsCursor is Null", costsCursor);

        Log.i(LOG_TAG, "this is my log " + costsCursor.toString());
        TestUtilities.validateCursor("costsCursor does not match expected value",
                costsCursor, testValues, KasebContract.Costs.TABLE_NAME);

        costsCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 5 PaymentMethods table
        testValues = TestUtilities.createPaymentMethodsValues();
        long paymentMethodsRowId = db.insert(KasebContract.PaymentMethods.TABLE_NAME, null, testValues);
        assertTrue("Unable to insert -PaymentMethods table- row", paymentMethodsRowId != -1);

        Cursor paymentMethodsCursor = mContext.getContentResolver().query(
                KasebContract.PaymentMethods.buildPaymentMethodsUri(paymentMethodsRowId),
                null,
                null,
                null,
                null
        );

        assertNotNull("paymentMethodsCursor is Null", paymentMethodsCursor);

        Log.i(LOG_TAG, "this is my log " + paymentMethodsCursor.toString());
        TestUtilities.validateCursor("paymentMethodsCursor does not match expected value",
                paymentMethodsCursor, testValues, KasebContract.PaymentMethods.TABLE_NAME);

        paymentMethodsCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 6 TaxTypes table
        testValues = TestUtilities.createTaxTypesValues();
        long taxTypesRowId = db.insert(KasebContract.TaxTypes.TABLE_NAME, null, testValues);
        assertTrue("Unable to insert -TaxTypes table- row", taxTypesRowId != -1);

        Cursor taxTypesCursor = mContext.getContentResolver().query(
                KasebContract.TaxTypes.buildTaxTypesUri(taxTypesRowId),
                null,
                null,
                null,
                null
        );

        assertNotNull("TaxTypesCursor is Null", taxTypesCursor);

        Log.i(LOG_TAG, "this is my log " + taxTypesCursor.toString());
        TestUtilities.validateCursor("TaxTypesCursor does not match expected value",
                taxTypesCursor, testValues, KasebContract.TaxTypes.TABLE_NAME);

        taxTypesCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 7 Products table
        testValues = TestUtilities.createProductsValues();
        long productsRowId = db.insert(KasebContract.Products.TABLE_NAME, null, testValues);
        assertTrue("Unable to insert -Products table- row", productsRowId != -1);

        Cursor productsCursor = mContext.getContentResolver().query(
                KasebContract.Products.buildProductsUri(productsRowId),
                null,
                null,
                null,
                null
        );

        assertNotNull("productsCursor is Null", productsCursor);

        Log.i(LOG_TAG, "this is my log " + productsCursor.toString());
        TestUtilities.validateCursor("productsCursor does not match expected value",
                productsCursor, testValues, KasebContract.Products.TABLE_NAME);

        productsCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 8 Sales table
        testValues = TestUtilities.createSalesValues(customersRowId);
        long salesRowId = db.insert(KasebContract.Sales.TABLE_NAME, null, testValues);
        assertTrue("Unable to insert -Sales table- row", salesRowId != -1);

        Cursor salesCursor = mContext.getContentResolver().query(
                KasebContract.Sales.buildSalesUri(salesRowId),
                null,
                null,
                null,
                null
        );

        assertNotNull("salesCursor is Null", salesCursor);

        Log.i(LOG_TAG, "this is my log " + salesCursor.toString());
        TestUtilities.validateCursor("salesCursor does not match expected value",
                salesCursor, testValues, KasebContract.Sales.TABLE_NAME);

        salesCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 9 DetailSale table
        testValues = TestUtilities.createDetailSaleValues(salesRowId);
        long detailSaleRowId = db.insert(KasebContract.DetailSale.TABLE_NAME, null, testValues);
        assertTrue("Unable to insert -DetailSale table- row", detailSaleRowId != -1);

        Cursor detailSaleCursor = mContext.getContentResolver().query(
                KasebContract.DetailSale.buildDetailSaleUri(detailSaleRowId),
                null,
                null,
                null,
                null
        );

        assertNotNull("detailSaleCursor is Null", detailSaleCursor);

        Log.i(LOG_TAG, "this is my log " + detailSaleCursor.toString());
        TestUtilities.validateCursor("detailSaleCursor does not match expected value",
                detailSaleCursor, testValues, KasebContract.DetailSale.TABLE_NAME);

        detailSaleCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 10 DetailSalePayments table
        testValues = TestUtilities.createDetailSalePaymentsValues(detailSaleRowId, paymentMethodsRowId);
        long detailSalePaymentsRowId = db.insert(KasebContract.DetailSalePayments.TABLE_NAME, null, testValues);
        assertTrue("Unable to insert -DetailSalePayments table- row", detailSalePaymentsRowId != -1);

        Cursor detailSalePaymentsCursor = mContext.getContentResolver().query(
                KasebContract.DetailSalePayments.buildDetailSalePaymentsUri(detailSalePaymentsRowId),
                null,
                null,
                null,
                null
        );

        assertNotNull("detailSalePaymentsCursor is Null", detailSalePaymentsCursor);

        Log.i(LOG_TAG, "this is my log " + detailSalePaymentsCursor.toString());
        TestUtilities.validateCursor("detailSalePaymentsCursor does not match expected value",
                detailSalePaymentsCursor, testValues, KasebContract.DetailSalePayments.TABLE_NAME);

        detailSalePaymentsCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 11 ProductHistory table
        testValues = TestUtilities.createProductHistoryValues(productsRowId);
        long productHistoryRowId = db.insert(KasebContract.ProductHistory.TABLE_NAME, null, testValues);
        assertTrue("Unable to insert -ProductHistory table- row", productHistoryRowId != -1);

        Cursor productHistoryCursor = mContext.getContentResolver().query(
                KasebContract.ProductHistory.buildProductHistoryUri(productHistoryRowId),
                null,
                null,
                null,
                null
        );

        assertNotNull("productHistoryCursor is Null", productHistoryCursor);

        Log.i(LOG_TAG, "this is my log " + productHistoryCursor.toString());
        TestUtilities.validateCursor("productHistoryCursor does not match expected value",
                productHistoryCursor, testValues, KasebContract.ProductHistory.TABLE_NAME);

        productHistoryCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 12 DetailSaleTaxes table
        testValues = TestUtilities.createDetailSaleTaxesValues(detailSaleRowId, taxTypesRowId);
        long detailSaleTaxesRowId = db.insert(KasebContract.DetailSaleTaxes.TABLE_NAME, null, testValues);
        assertTrue("Unable to insert -DetailSaleTaxes table- row", detailSaleTaxesRowId != -1);

        Cursor detailSaleTaxesCursor = mContext.getContentResolver().query(
                KasebContract.DetailSaleTaxes.buildDetailSaleTaxesUri(detailSaleTaxesRowId),
                null,
                null,
                null,
                null
        );

        assertNotNull("detailSaleTaxesCursor is Null", detailSaleTaxesCursor);

        Log.i(LOG_TAG, "this is my log " + detailSaleTaxesCursor.toString());
        TestUtilities.validateCursor("detailSaleTaxesCursor does not match expected value",
                detailSaleTaxesCursor, testValues, KasebContract.DetailSaleTaxes.TABLE_NAME);

        detailSaleTaxesCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 13 DetailSaleProducts table
        testValues = TestUtilities.createDetailSaleProductsValues(productsRowId, detailSaleRowId);
        long detailSaleProductsRowId = db.insert(KasebContract.DetailSaleProducts.TABLE_NAME, null, testValues);
        assertTrue("Unable to insert -DetailSaleProducts table- row", detailSaleProductsRowId != -1);

        Cursor detailSaleProductsCursor = mContext.getContentResolver().query(
                KasebContract.DetailSaleProducts.buildDetailSaleProducts(detailSaleProductsRowId),
                null,
                null,
                null,
                null
        );

        assertNotNull("detailSaleProductsCursor is Null", detailSaleProductsCursor);

        Log.i(LOG_TAG, "this is my log " + detailSaleProductsCursor.toString());
        TestUtilities.validateCursor("detailSaleProductsCursor does not match expected value",
                detailSaleProductsCursor, testValues, KasebContract.DetailSaleProducts.TABLE_NAME);

        detailSaleProductsCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 14 Other Query : CUSTOMER_BY_STATE
        deleteAllRecordsFromProvider();
        testValues = TestUtilities.createStateValues();
        long newStateRowId = db.insert(KasebContract.State.TABLE_NAME, null, testValues);

        bulkInsertContentValues = createBulkInsertCustomersByStateIdValues(newStateRowId);
        mContext.getContentResolver().registerContentObserver(Customers.CONTENT_URI, true, observer);
        int customersID = mContext.getContentResolver().bulkInsert(Customers.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion with StateId failed -Customers/State table-", BULK_INSERT_RECORDS_TO_INSERT, customersID);

        // A cursor is your primary interface to the query results.
        Cursor customersWithIdCursor = mContext.getContentResolver().query(
                Customers.stateCustomer(newStateRowId),
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        Log.i(LOG_TAG, "this is my log " + customersWithIdCursor.toString());
        assertNotNull("customersWithIdCursor is Null", customersWithIdCursor);

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Query failed - Customers/State table - ", BULK_INSERT_RECORDS_TO_INSERT,
                customersWithIdCursor.getCount());

        customersWithIdCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 15 Other Query : SALES_BY_CUSTOMER
        deleteAllRecordsFromProvider();
        testValues = TestUtilities.createStateValues();
        long newStateRowId1 = db.insert(KasebContract.State.TABLE_NAME, null, testValues);

        testValues = TestUtilities.createCustomersValues(newStateRowId1);
        long newCustomerRowId = db.insert(KasebContract.Customers.TABLE_NAME, null, testValues);

        bulkInsertContentValues = createBulkInsertSalesByCustomerIdValues(newCustomerRowId);
        mContext.getContentResolver().registerContentObserver(Sales.CONTENT_URI, true, observer);
        int salesId = mContext.getContentResolver().bulkInsert(Sales.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion with CustomerId failed -Sales/Customer table-", BULK_INSERT_RECORDS_TO_INSERT, salesId);

        // A cursor is your primary interface to the query results.
        Cursor saleswithIdCursor = mContext.getContentResolver().query(
                Sales.customerSales(newCustomerRowId),
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        Log.i(LOG_TAG, "this is my log " + saleswithIdCursor.toString());
        assertNotNull("saleswithIdCursor is Null", saleswithIdCursor);

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Query failed - Sales/Customer table - ", BULK_INSERT_RECORDS_TO_INSERT,
                saleswithIdCursor.getCount());

        saleswithIdCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 16 Other Query : DETAIL_SALE_BY_IS_BALANCED
        deleteAllRecordsFromProvider();

        bulkInsertContentValues = createBulkInsertDetailSaleWithIsBalancedValues();
        mContext.getContentResolver().registerContentObserver(DetailSale.CONTENT_URI, true, observer);
        int detailSaleId = mContext.getContentResolver().bulkInsert(DetailSale.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion with IsBalanced failed -DetailSale:IsBalanced table-", BULK_INSERT_RECORDS_TO_INSERT,
                detailSaleId);

        // A cursor is your primary interface to the query results.
        Cursor detailSalewithIsBalancedCursor = mContext.getContentResolver().query(
                DetailSale.isBalanceDetailSale(true),
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        Log.i(LOG_TAG, "this is my log " + detailSalewithIsBalancedCursor.toString());
        assertNotNull("detailSalewithIsBalancedCursor is Null", detailSalewithIsBalancedCursor);

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Query failed - DetailSale:IsBalanced table - ", BULK_INSERT_RECORDS_TO_INSERT,
                detailSalewithIsBalancedCursor.getCount());

        detailSalewithIsBalancedCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 17 Other Query : PRODUCT_HISTORY_BY_PRODUCT_ID
        deleteAllRecordsFromProvider();

        testValues = TestUtilities.createProductsValues();
        long newProductsRowId = db.insert(KasebContract.Products.TABLE_NAME, null, testValues);

        bulkInsertContentValues = createBulkInsertProductHistoryByProductsIdValues(newProductsRowId);
        mContext.getContentResolver().registerContentObserver(ProductHistory.CONTENT_URI, true, observer);
        int productHistoryId = mContext.getContentResolver().bulkInsert(ProductHistory.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion with productHistoryId failed -ProductHistory/Products table-", BULK_INSERT_RECORDS_TO_INSERT, productHistoryId);

        // A cursor is your primary interface to the query results.
        Cursor productHistorywithIdCursor = mContext.getContentResolver().query(
                ProductHistory.aProductHistory(newProductsRowId),
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        Log.i(LOG_TAG, "this is my log " + productHistorywithIdCursor.toString());
        assertNotNull("productHistorywithIdCursor is Null", productHistorywithIdCursor);

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Query failed - ProductHistory/Products table - ", BULK_INSERT_RECORDS_TO_INSERT,
                productHistorywithIdCursor.getCount());

        productHistorywithIdCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 18 Other Query : DETAIL_SALE_PRODUCT_BY_DETAIL_SALE_ID
        deleteAllRecordsFromProvider();

        testValues = TestUtilities.createStateValues();
        long newStateRowId2 = db.insert(KasebContract.State.TABLE_NAME, null, testValues);

        testValues = TestUtilities.createCustomersValues(newStateRowId2);
        long newCustomersRowId = db.insert(KasebContract.Customers.TABLE_NAME, null, testValues);

        testValues = TestUtilities.createSalesValues(newCustomersRowId);
        long newSalesRowId = db.insert(KasebContract.Sales.TABLE_NAME, null, testValues);

        testValues = TestUtilities.createDetailSaleValues(newSalesRowId);
        long newDetailSaleRowId = db.insert(KasebContract.DetailSale.TABLE_NAME, null, testValues);

        bulkInsertContentValues = createBulkInsertDetailSaleProductsByDetailSaleIdValues(newDetailSaleRowId);
        mContext.getContentResolver().registerContentObserver(DetailSaleProducts.CONTENT_URI, true, observer);
        int detailSaleProductId = mContext.getContentResolver().bulkInsert(DetailSaleProducts.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion with detailSaleProductId failed -DetailSaleProducts/DetailSale table-",
                BULK_INSERT_RECORDS_TO_INSERT, detailSaleProductId);

        // A cursor is your primary interface to the query results.
        Cursor detailSaleProductswithDetailSaleIdCursor = mContext.getContentResolver().query(
                DetailSaleProducts.uriDetailSaleProductsWithDetailSaleId(newDetailSaleRowId),
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        Log.i(LOG_TAG, "this is my log " + detailSaleProductswithDetailSaleIdCursor.toString());
        assertNotNull("detailSaleProductswithDetailSaleIdCursor is Null", detailSaleProductswithDetailSaleIdCursor);

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Query failed - DetailSaleProducts/DetailSale - ", BULK_INSERT_RECORDS_TO_INSERT,
                detailSaleProductswithDetailSaleIdCursor.getCount());

        detailSaleProductswithDetailSaleIdCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 19 Other Query : DETAIL_SALE_PAYMENT_BY_DETAIL_SALE_ID
        deleteAllRecordsFromProvider();

        testValues = TestUtilities.createStateValues();
        long newStateRowId3 = db.insert(KasebContract.State.TABLE_NAME, null, testValues);

        testValues = TestUtilities.createCustomersValues(newStateRowId3);
        long newCustomersRowId1 = db.insert(KasebContract.Customers.TABLE_NAME, null, testValues);

        testValues = TestUtilities.createSalesValues(newCustomersRowId1);
        long newSalesRowId1 = db.insert(KasebContract.Sales.TABLE_NAME, null, testValues);

        testValues = TestUtilities.createDetailSaleValues(newSalesRowId1);
        long newDetailSaleRowId1 = db.insert(KasebContract.DetailSale.TABLE_NAME, null, testValues);

        bulkInsertContentValues = createBulkInsertDetailSalePaymentsByDetailSaleIdValues(newDetailSaleRowId1);
        mContext.getContentResolver().registerContentObserver(DetailSalePayments.CONTENT_URI, true, observer);
        int detailSalePaymentId = mContext.getContentResolver().bulkInsert(DetailSalePayments.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion with detailSaleProductId failed -DetailSalePayments/DetailSale table-",
                BULK_INSERT_RECORDS_TO_INSERT, detailSalePaymentId);

        // A cursor is your primary interface to the query results.
        Cursor detailSalePaymentswithDetailSaleIdCursor = mContext.getContentResolver().query(
                DetailSalePayments.paymentOfDetailSale(newDetailSaleRowId1),
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        Log.i(LOG_TAG, "this is my log " + detailSalePaymentswithDetailSaleIdCursor.toString());
        assertNotNull("detailSalePaymentswithDetailSaleIdCursor is Null", detailSalePaymentswithDetailSaleIdCursor);

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Query failed - DetailSalePayments/DetailSale - ", BULK_INSERT_RECORDS_TO_INSERT,
                detailSalePaymentswithDetailSaleIdCursor.getCount());

        detailSalePaymentswithDetailSaleIdCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 20 Other Query : DETAIL_SALE_PAYMENT_BY_PAYMENT_METHOD
        deleteAllRecordsFromProvider();

        testValues = TestUtilities.createPaymentMethodsValues();
        long newPaymentMethodRowId = db.insert(KasebContract.PaymentMethods.TABLE_NAME, null, testValues);

        bulkInsertContentValues = createBulkInsertDetailSalePaymentsByPaymentMethodValues(newPaymentMethodRowId);
        mContext.getContentResolver().registerContentObserver(DetailSalePayments.CONTENT_URI, true, observer);
        int detailSalePaymentId1 = mContext.getContentResolver().bulkInsert(DetailSalePayments.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion with detailSaleProductId failed -DetailSalePayments/DetailSale table-",
                BULK_INSERT_RECORDS_TO_INSERT, detailSalePaymentId1);

        // A cursor is your primary interface to the query results.
        Cursor detailSalePaymentswithPaymentMethodIdCursor = mContext.getContentResolver().query(
                DetailSalePayments.paymentsByMethod(newPaymentMethodRowId),
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        Log.i(LOG_TAG, "this is my log " + detailSalePaymentswithPaymentMethodIdCursor.toString());
        assertNotNull("detailSalePaymentswithPaymentMethodIdCursor is Null", detailSalePaymentswithPaymentMethodIdCursor);

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Query failed - DetailSalePayments/DetailSale - ", BULK_INSERT_RECORDS_TO_INSERT,
                detailSalePaymentswithPaymentMethodIdCursor.getCount());

        detailSalePaymentswithPaymentMethodIdCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 21 Other Query : DETAIL_SALE_TAXES_BY_DETAIL_SALE_ID
        deleteAllRecordsFromProvider();

        testValues = TestUtilities.createStateValues();
        long newStateRowId4 = db.insert(KasebContract.State.TABLE_NAME, null, testValues);

        testValues = TestUtilities.createCustomersValues(newStateRowId4);
        long newCustomersRowId2 = db.insert(KasebContract.Customers.TABLE_NAME, null, testValues);

        testValues = TestUtilities.createSalesValues(newCustomersRowId2);
        long newSalesRowId2 = db.insert(KasebContract.Sales.TABLE_NAME, null, testValues);

        testValues = TestUtilities.createDetailSaleValues(newSalesRowId2);
        long newDetailSaleRowId2 = db.insert(KasebContract.DetailSale.TABLE_NAME, null, testValues);

        bulkInsertContentValues = createBulkInsertDetailSaleTaxesByDetailSaleIdValues(newDetailSaleRowId2);
        mContext.getContentResolver().registerContentObserver(DetailSaleTaxes.CONTENT_URI, true, observer);
        int detailSaleTaxesId = mContext.getContentResolver().bulkInsert(DetailSaleTaxes.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion with detailSaleProductId failed -DetailSaleTaxes/DetailSale table-",
                BULK_INSERT_RECORDS_TO_INSERT, detailSaleTaxesId);

        // A cursor is your primary interface to the query results.
        Cursor detailSaleTaxeswithDetailSaleIdCursor = mContext.getContentResolver().query(
                DetailSaleTaxes.taxOfDetailSale(newDetailSaleRowId2),
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        Log.i(LOG_TAG, "this is my log " + detailSaleTaxeswithDetailSaleIdCursor.toString());
        assertNotNull("detailSaleTaxeswithDetailSaleIdCursor is Null", detailSaleTaxeswithDetailSaleIdCursor);

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Query failed - DetailSaleTaxes/DetailSale - ", BULK_INSERT_RECORDS_TO_INSERT,
                detailSaleTaxeswithDetailSaleIdCursor.getCount());

        detailSaleTaxeswithDetailSaleIdCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 22 Other Query : DETAIL_SALE_TAXES_BY_TAX_TYPE
        deleteAllRecordsFromProvider();

        testValues = TestUtilities.createTaxTypesValues();
        long newTaxTypesRowId = db.insert(KasebContract.TaxTypes.TABLE_NAME, null, testValues);

        bulkInsertContentValues = createBulkInsertDetailSaleTaxesByTaxTypesValues(newTaxTypesRowId);
        mContext.getContentResolver().registerContentObserver(DetailSaleTaxes.CONTENT_URI, true, observer);
        int detailSaleTaxesId1 = mContext.getContentResolver().bulkInsert(DetailSaleTaxes.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion with detailSaleProductId failed -DetailSaleTaxes/TaxTypes table-",
                BULK_INSERT_RECORDS_TO_INSERT, detailSaleTaxesId1);

        // A cursor is your primary interface to the query results.
        Cursor detailSaleTaxeswithTaxTypesIdCursor = mContext.getContentResolver().query(
                DetailSaleTaxes.taxOfDetailSaleByType(newTaxTypesRowId),
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        Log.i(LOG_TAG, "this is my log " + detailSaleTaxeswithTaxTypesIdCursor.toString());
        assertNotNull("detailSaleTaxeswithTaxTypesIdCursor is Null", detailSaleTaxeswithTaxTypesIdCursor);

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Query failed - DetailSaleTaxes/TaxTypes - ", BULK_INSERT_RECORDS_TO_INSERT,
                detailSaleTaxeswithTaxTypesIdCursor.getCount());

        detailSaleTaxeswithTaxTypesIdCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        //region 23 Other Query : COSTS_BY_TYPE
        deleteAllRecordsFromProvider();

        testValues = TestUtilities.createCostTypesValues();
        long newCostTypesRowId = db.insert(KasebContract.CostTypes.TABLE_NAME, null, testValues);

        bulkInsertContentValues = createBulkInsertCostsByTypesValues(newCostTypesRowId);
        mContext.getContentResolver().registerContentObserver(Costs.CONTENT_URI, true, observer);
        int costId = mContext.getContentResolver().bulkInsert(Costs.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion with detailSaleProductId failed -Costs/CostTypes table-",
                BULK_INSERT_RECORDS_TO_INSERT, costId);

        // A cursor is your primary interface to the query results.
        Cursor costsWithCostTypesIdCursor = mContext.getContentResolver().query(
                Costs.costsByType(newCostTypesRowId),
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        Log.i(LOG_TAG, "this is my log " + costsWithCostTypesIdCursor.toString());
        assertNotNull("costsWithCostTypesIdCursor is Null", costsWithCostTypesIdCursor);

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Query failed - Costs/CostTypes - ", BULK_INSERT_RECORDS_TO_INSERT,
                costsWithCostTypesIdCursor.getCount());

        costsWithCostTypesIdCursor.close();
        deleteAllRecordsFromProvider();
        //endregion

        db.close();
    }

    public void testUpdate() {

        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();

        //region 1 State table
        ContentValues stateValues = TestUtilities.createStateValues();
        Uri stateUri = mContext.getContentResolver().insert(
                State.CONTENT_URI,
                stateValues
        );
        long stateRowId = ContentUris.parseId(stateUri);

        // Verify we got a row back.
        assertTrue("Unable to insert -State table- row", stateRowId != -1);

        ContentValues stateUpdateValues = new ContentValues();
        stateUpdateValues.put(State._ID, stateRowId);
        stateUpdateValues.put(State.COLUMN_STATE_POINTER, 100);

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor stateCursor = mContext.getContentResolver().query(
                State.CONTENT_URI,
                null, null, null, null);

        stateCursor.moveToFirst();

        mContext.getContentResolver().registerContentObserver(State.CONTENT_URI, true, tco);
        stateCursor.registerContentObserver(tco);

        int stateUpdateCount = mContext.getContentResolver().update(State.CONTENT_URI, stateUpdateValues, State._ID + "=" +
                Long.toString(stateRowId), null);
        assertEquals("Update more than one row in -State table-", stateUpdateCount, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        stateCursor.unregisterContentObserver(tco);
        stateCursor = mContext.getContentResolver().query(
                State.buildStateUri(stateRowId), null, null, null, null);
        stateCursor.moveToFirst();

        TestUtilities.validateCursor("testUpdate.  Error validating -State table- update.", stateCursor,
                stateUpdateValues, State.TABLE_NAME);
        stateCursor.close();
        //endregion

        //region 2 CostTypes table
        ContentValues costTypesValues = TestUtilities.createCostTypesValues();
        Uri costTypesUri = mContext.getContentResolver().insert(
                CostTypes.CONTENT_URI,
                costTypesValues
        );
        long costTypesRowId = ContentUris.parseId(costTypesUri);

        // Verify we got a row back.
        assertTrue("Unable to insert -CostTypes table- row", costTypesRowId != -1);

        ContentValues costTypesUpdateValues = new ContentValues();
        costTypesUpdateValues.put(CostTypes._ID, costTypesRowId);
        costTypesUpdateValues.put(CostTypes.COLUMN_COST_TYPE_POINTER, 100);

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor costTypesCursor = mContext.getContentResolver().query(
                CostTypes.CONTENT_URI,
                null, null, null, null);

        costTypesCursor.moveToFirst();

        mContext.getContentResolver().registerContentObserver(CostTypes.CONTENT_URI, true, tco);
        costTypesCursor.registerContentObserver(tco);

        int costTypesUpdateCount = mContext.getContentResolver().update(CostTypes.CONTENT_URI, costTypesUpdateValues,
                CostTypes._ID + "=" +
                        Long.toString(costTypesRowId), null);
        assertEquals("Update more than one row in -CostTypes table-", costTypesUpdateCount, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        costTypesCursor.unregisterContentObserver(tco);
        costTypesCursor = mContext.getContentResolver().query(
                CostTypes.buildCostTypesUri(costTypesRowId), null, null, null, null);
        costTypesCursor.moveToFirst();

        TestUtilities.validateCursor("testUpdate.  Error validating -CostTypes table- update.", costTypesCursor,
                costTypesUpdateValues, State.TABLE_NAME);
        costTypesCursor.close();
        //endregion

        //region 3 Customers table
        ContentValues customersValues = TestUtilities.createCustomersValues(stateRowId);
        Uri customersUri = mContext.getContentResolver().insert(
                Customers.CONTENT_URI,
                customersValues
        );
        long customersRowId = ContentUris.parseId(customersUri);

        // Verify we got a row back.
        assertTrue("Unable to insert -Customers table- row", customersRowId != -1);

        ContentValues customersUpdateValues = new ContentValues();
        customersUpdateValues.put(Customers._ID, customersRowId);
        customersUpdateValues.put(Customers.COLUMN_FIRST_NAME, "sample");
        customersUpdateValues.put(Customers.COLUMN_LAST_NAME, "sample");
        customersUpdateValues.put(Customers.COLUMN_PHONE_MOBILE, "sample");
        customersUpdateValues.put(Customers.COLUMN_PHONE_WORK, "sample");
        customersUpdateValues.put(Customers.COLUMN_PHONE_FAX, "sample");
        customersUpdateValues.put(Customers.COLUMN_PHONE_OTHER, "sample");
        customersUpdateValues.put(Customers.COLUMN_EMAIL, "sample");
        customersUpdateValues.put(Customers.COLUMN_ADDRESS_COUNTRY, "sample");
        customersUpdateValues.put(Customers.COLUMN_ADDRESS_CITY, "sample");
        customersUpdateValues.put(Customers.COLUMN_ADDRESS_STREET, "sample");
        customersUpdateValues.put(Customers.COLUMN_ADDRESS_POSTAL_CODE, "sample");
        customersUpdateValues.put(Customers.COLUMN_DESCRIPTION, "sample");
        customersUpdateValues.put(Customers.COLUMN_IS_DELETED, 0);
        customersUpdateValues.put(Customers.COLUMN_STATE_ID, stateRowId);

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor customersCursor = mContext.getContentResolver().query(
                Customers.CONTENT_URI,
                null, null, null, null);

        customersCursor.moveToFirst();

        mContext.getContentResolver().registerContentObserver(Customers.CONTENT_URI, true, tco);
        customersCursor.registerContentObserver(tco);

        int customersUpdateCount = mContext.getContentResolver().update(Customers.CONTENT_URI, customersUpdateValues,
                Customers._ID + "=" +
                        Long.toString(customersRowId), null);
        assertEquals("Update more than one row in -Customers table-", customersUpdateCount, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        customersCursor.unregisterContentObserver(tco);
        customersCursor = mContext.getContentResolver().query(
                Customers.buildCustomerUri(customersRowId), null, null, null, null);
        customersCursor.moveToFirst();

        TestUtilities.validateCursor("testUpdate.  Error validating -Customers table- update.", customersCursor,
                customersUpdateValues, Customers.TABLE_NAME);
        customersCursor.close();
        //endregion

        //region 4 Costs table
        ContentValues costsValues = TestUtilities.createCostsValues(costTypesRowId);
        Uri costsUri = mContext.getContentResolver().insert(
                Costs.CONTENT_URI,
                costsValues
        );
        long costsRowId = ContentUris.parseId(costsUri);

        // Verify we got a row back.
        assertTrue("Unable to insert -Costs table- row", costsRowId != -1);

        ContentValues costsUpdateValues = new ContentValues();
        costsUpdateValues.put(Costs._ID, costsRowId);
        costsUpdateValues.put(Costs.COLUMN_COST_TYPE_ID, 100);
        costsUpdateValues.put(Costs.COLUMN_COST_NAME, "sample");
        costsUpdateValues.put(Costs.COLUMN_COST_CODE, "sample");
        costsUpdateValues.put(Costs.COLUMN_AMOUNT, 100);
        costsUpdateValues.put(Costs.COLUMN_DATE, "sample");
        costsUpdateValues.put(Costs.COLUMN_DESCRIPTION, "sample");
        costsUpdateValues.put(Costs.COLUMN_COST_TYPE_ID, costTypesRowId);

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor costsCursor = mContext.getContentResolver().query(
                Costs.CONTENT_URI,
                null, null, null, null);

        costsCursor.moveToFirst();

        mContext.getContentResolver().registerContentObserver(Costs.CONTENT_URI, true, tco);
        costsCursor.registerContentObserver(tco);

        int costsUpdateCount = mContext.getContentResolver().update(Costs.CONTENT_URI, costsUpdateValues, Costs._ID + "=" +
                Long.toString(costsRowId), null);
        assertEquals("Update more than one row in -State table-", stateUpdateCount, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        costsCursor.unregisterContentObserver(tco);
        costsCursor = mContext.getContentResolver().query(
                Costs.buildCostsUri(costsRowId), null, null, null, null);
        costsCursor.moveToFirst();

        TestUtilities.validateCursor("testUpdate.  Error validating -Costs table- update.", costsCursor,
                costsUpdateValues, Costs.TABLE_NAME);
        costsCursor.close();
        //endregion

        //region 5 PaymentMethods table
        ContentValues paymentMethodsValues = TestUtilities.createPaymentMethodsValues();
        Uri paymentMethodsUri = mContext.getContentResolver().insert(
                PaymentMethods.CONTENT_URI,
                paymentMethodsValues
        );
        long paymentMethodsRowId = ContentUris.parseId(paymentMethodsUri);

        // Verify we got a row back.
        assertTrue("Unable to insert -PaymentMethods table- row", paymentMethodsRowId != -1);

        ContentValues paymentMethodsUpdateValues = new ContentValues();
        paymentMethodsUpdateValues.put(PaymentMethods._ID, paymentMethodsRowId);
        paymentMethodsUpdateValues.put(PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER, 100);

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor paymentMethodsCursor = mContext.getContentResolver().query(
                PaymentMethods.CONTENT_URI,
                null, null, null, null);

        paymentMethodsCursor.moveToFirst();

        mContext.getContentResolver().registerContentObserver(PaymentMethods.CONTENT_URI, true, tco);
        paymentMethodsCursor.registerContentObserver(tco);

        int paymentMethodsUpdateCount = mContext.getContentResolver().update(PaymentMethods.CONTENT_URI,
                paymentMethodsUpdateValues, PaymentMethods._ID + "=" +
                        Long.toString(paymentMethodsRowId), null);
        assertEquals("Update more than one row in -PaymentMethods table-", paymentMethodsUpdateCount, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        paymentMethodsCursor.unregisterContentObserver(tco);
        paymentMethodsCursor = mContext.getContentResolver().query(
                PaymentMethods.buildPaymentMethodsUri(paymentMethodsRowId), null, null, null, null);
        paymentMethodsCursor.moveToFirst();

        TestUtilities.validateCursor("testUpdate.  Error validating -PaymentMethods table- update.", paymentMethodsCursor,
                paymentMethodsUpdateValues, PaymentMethods.TABLE_NAME);
        paymentMethodsCursor.close();
        //endregion

        //region 6 TaxTypes table
        ContentValues taxTypesValues = TestUtilities.createTaxTypesValues();
        Uri taxTypesUri = mContext.getContentResolver().insert(
                TaxTypes.CONTENT_URI,
                taxTypesValues
        );
        long taxTypesRowId = ContentUris.parseId(taxTypesUri);

        // Verify we got a row back.
        assertTrue("Unable to insert -TaxTypes table- row", taxTypesRowId != -1);

        ContentValues taxTypesUpdateValues = new ContentValues();
        taxTypesUpdateValues.put(TaxTypes._ID, taxTypesRowId);
        taxTypesUpdateValues.put(TaxTypes.COLUMN_TAX_TYPE_POINTER, 100);

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor taxTypesCursor = mContext.getContentResolver().query(
                TaxTypes.CONTENT_URI,
                null, null, null, null);

        taxTypesCursor.moveToFirst();

        mContext.getContentResolver().registerContentObserver(TaxTypes.CONTENT_URI, true, tco);
        taxTypesCursor.registerContentObserver(tco);

        int taxTypesUpdateCount = mContext.getContentResolver().update(TaxTypes.CONTENT_URI, taxTypesUpdateValues, TaxTypes._ID + "=" +
                Long.toString(taxTypesRowId), null);
        assertEquals("Update more than one row in -TaxTypes table-", taxTypesUpdateCount, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        taxTypesCursor.unregisterContentObserver(tco);
        taxTypesCursor = mContext.getContentResolver().query(
                TaxTypes.buildTaxTypesUri(taxTypesRowId), null, null, null, null);
        taxTypesCursor.moveToFirst();

        TestUtilities.validateCursor("testUpdate.  Error validating -TaxTypes table- update.", taxTypesCursor,
                taxTypesUpdateValues, TaxTypes.TABLE_NAME);
        taxTypesCursor.close();
        //endregion

        //region 7 Products table
        ContentValues productsValues = TestUtilities.createProductsValues();
        Uri productsUri = mContext.getContentResolver().insert(
                Products.CONTENT_URI,
                productsValues
        );
        long productsRowId = ContentUris.parseId(productsUri);

        // Verify we got a row back.
        assertTrue("Unable to insert -Products table- row", productsRowId != -1);

        ContentValues productsUpdateValues = new ContentValues();
        productsUpdateValues.put(Products._ID, productsRowId);
        productsUpdateValues.put(Products.COLUMN_PRODUCT_NAME, "sample");
        productsUpdateValues.put(Products.COLUMN_PRODUCT_CODE, "sample");
        productsUpdateValues.put(Products.COLUMN_DESCRIPTION, "sample");
        productsUpdateValues.put(Products.COLUMN_UNIT, "sample");

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor ProductsCursor = mContext.getContentResolver().query(
                Products.CONTENT_URI,
                null, null, null, null);

        ProductsCursor.moveToFirst();

        mContext.getContentResolver().registerContentObserver(Products.CONTENT_URI, true, tco);
        ProductsCursor.registerContentObserver(tco);

        int ProductsUpdateCount = mContext.getContentResolver().update(Products.CONTENT_URI,
                productsUpdateValues, Products._ID + "=" +
                        Long.toString(productsRowId), null);
        assertEquals("Update more than one row in -Products table-", ProductsUpdateCount, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        ProductsCursor.unregisterContentObserver(tco);
        ProductsCursor = mContext.getContentResolver().query(
                Products.buildProductsUri(productsRowId), null, null, null, null);
        ProductsCursor.moveToFirst();

        TestUtilities.validateCursor("testUpdate.  Error validating -Products table- update.", ProductsCursor,
                productsUpdateValues, Products.TABLE_NAME);
        ProductsCursor.close();
        //endregion

        //region 8 Sales table
        ContentValues salesValues = TestUtilities.createSalesValues(customersRowId);
        Uri salesUri = mContext.getContentResolver().insert(
                Sales.CONTENT_URI,
                salesValues
        );
        long salesRowId = ContentUris.parseId(salesUri);

        // Verify we got a row back.
        assertTrue("Unable to insert -Sales table- row", salesRowId != -1);

        ContentValues salesUpdateValues = new ContentValues();
        salesUpdateValues.put(Sales._ID, salesRowId);
        salesUpdateValues.put(Sales.COLUMN_IS_DELETED, 0);
        salesUpdateValues.put(Sales.COLUMN_SALE_CODE, "sample");
        salesUpdateValues.put(Sales.COLUMN_CUSTOMER_ID, 100);
        salesUpdateValues.put(Sales.COLUMN_CUSTOMER_ID, customersRowId);

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor salesCursor = mContext.getContentResolver().query(
                Sales.CONTENT_URI,
                null, null, null, null);

        salesCursor.moveToFirst();

        mContext.getContentResolver().registerContentObserver(Sales.CONTENT_URI, true, tco);
        salesCursor.registerContentObserver(tco);

        int SalesUpdateCount = mContext.getContentResolver().update(Sales.CONTENT_URI, salesUpdateValues, Sales._ID + "=" +
                Long.toString(salesRowId), null);
        assertEquals("Update more than one row in -Sales table-", SalesUpdateCount, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        salesCursor.unregisterContentObserver(tco);
        salesCursor = mContext.getContentResolver().query(
                Sales.buildSalesUri(salesRowId), null, null, null, null);
        salesCursor.moveToFirst();

        TestUtilities.validateCursor("testUpdate.  Error validating -Sales table- update.", salesCursor,
                salesUpdateValues, Sales.TABLE_NAME);
        salesCursor.close();
        //endregion

        //region 9 DetailSale table
        ContentValues detailSaleValues = TestUtilities.createDetailSaleValues(salesRowId);
        Uri detailSaleUri = mContext.getContentResolver().insert(
                DetailSale.CONTENT_URI,
                detailSaleValues
        );
        long detailSaleRowId = ContentUris.parseId(detailSaleUri);

        // Verify we got a row back.
        assertTrue("Unable to insert -DetailSale table- row", detailSaleRowId != -1);

        ContentValues DetailSaleUpdateValues = new ContentValues();
        DetailSaleUpdateValues.put(DetailSale._ID, detailSaleRowId);
        DetailSaleUpdateValues.put(DetailSale.COLUMN_DATE, "sample");
        DetailSaleUpdateValues.put(DetailSale.COLUMN_ITEMS_NUMBER, 100);
        DetailSaleUpdateValues.put(DetailSale.COLUMN_SUB_TOTAL, 100);
        DetailSaleUpdateValues.put(DetailSale.COLUMN_TOTAL_DISCOUNT, 100);
        DetailSaleUpdateValues.put(DetailSale.COLUMN_TOTAL_TAX, 100);
        DetailSaleUpdateValues.put(DetailSale.COLUMN_TOTAL_DUE, 100);
        DetailSaleUpdateValues.put(DetailSale.COLUMN_TOTAL_PAID, 100);
        DetailSaleUpdateValues.put(DetailSale.COLUMN_IS_BALANCED, 0);
        DetailSaleUpdateValues.put(DetailSale.COLUMN_SALE_ID, salesRowId);

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor detailSaleCursor = mContext.getContentResolver().query(
                DetailSale.CONTENT_URI,
                null, null, null, null);

        detailSaleCursor.moveToFirst();

        mContext.getContentResolver().registerContentObserver(DetailSale.CONTENT_URI, true, tco);
        detailSaleCursor.registerContentObserver(tco);

        int DetailSaleUpdateCount = mContext.getContentResolver().update(DetailSale.CONTENT_URI, DetailSaleUpdateValues,
                DetailSale._ID + "=" +
                        Long.toString(detailSaleRowId), null);
        assertEquals("Update more than one row in -DetailSale table-", DetailSaleUpdateCount, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        detailSaleCursor.unregisterContentObserver(tco);
        detailSaleCursor = mContext.getContentResolver().query(
                DetailSale.buildDetailSaleUri(detailSaleRowId), null, null, null, null);
        detailSaleCursor.moveToFirst();

        TestUtilities.validateCursor("testUpdate.  Error validating -DetailSale table- update.", detailSaleCursor,
                DetailSaleUpdateValues, DetailSale.TABLE_NAME);
        detailSaleCursor.close();
        //endregion

        //region 10 DetailSalePayments table
        ContentValues detailSalePaymentsValues = TestUtilities.createDetailSalePaymentsValues(detailSaleRowId, paymentMethodsRowId);
        Uri detailSalePaymentsUri = mContext.getContentResolver().insert(
                DetailSalePayments.CONTENT_URI,
                detailSalePaymentsValues
        );
        long detailSalePaymentsRowId = ContentUris.parseId(detailSalePaymentsUri);

        // Verify we got a row back.
        assertTrue("Unable to insert -DetailSalePayments table- row", detailSalePaymentsRowId != -1);

        ContentValues DetailSalePaymentsUpdateValues = new ContentValues();
        DetailSalePaymentsUpdateValues.put(DetailSalePayments._ID, detailSalePaymentsRowId);
        DetailSalePaymentsUpdateValues.put(DetailSalePayments.COLUMN_DUE_DATE, "sample");
        DetailSalePaymentsUpdateValues.put(DetailSalePayments.COLUMN_AMOUNT, 100);
        DetailSalePaymentsUpdateValues.put(DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, 100);
        DetailSalePaymentsUpdateValues.put(DetailSalePayments.COLUMN_DETAIL_SALE_ID, detailSaleRowId);
        DetailSalePaymentsUpdateValues.put(DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, paymentMethodsRowId);

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor detailSalePaymentsCursor = mContext.getContentResolver().query(
                DetailSalePayments.CONTENT_URI,
                null, null, null, null);

        detailSalePaymentsCursor.moveToFirst();

        mContext.getContentResolver().registerContentObserver(DetailSalePayments.CONTENT_URI, true, tco);
        detailSalePaymentsCursor.registerContentObserver(tco);

        int detailSalePaymentsUpdateCount = mContext.getContentResolver().update(DetailSalePayments.CONTENT_URI,
                DetailSalePaymentsUpdateValues,
                DetailSalePayments._ID + "=" +
                        Long.toString(detailSalePaymentsRowId), null);
        assertEquals("Update more than one row in -DetailSalePayments table-", detailSalePaymentsUpdateCount, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        detailSalePaymentsCursor.unregisterContentObserver(tco);
        detailSalePaymentsCursor = mContext.getContentResolver().query(
                DetailSalePayments.buildDetailSalePaymentsUri(detailSalePaymentsRowId), null, null, null, null);
        detailSalePaymentsCursor.moveToFirst();

        TestUtilities.validateCursor("testUpdate.  Error validating -DetailSalePayments table- update.", detailSalePaymentsCursor,
                DetailSalePaymentsUpdateValues, DetailSalePayments.TABLE_NAME);
        detailSalePaymentsCursor.close();
        //endregion

        //region 11 ProductHistory table
        ContentValues productHistoryValues = TestUtilities.createProductHistoryValues(productsRowId);
        Uri productHistoryUri = mContext.getContentResolver().insert(
                ProductHistory.CONTENT_URI,
                productHistoryValues
        );
        long productHistoryRowId = ContentUris.parseId(productHistoryUri);

        // Verify we got a row back.
        assertTrue("Unable to insert -ProductHistory table- row", productHistoryRowId != -1);

        ContentValues productHistoryUpdateValues = new ContentValues();
        productHistoryUpdateValues.put(ProductHistory._ID, productHistoryRowId);
        productHistoryUpdateValues.put(ProductHistory.COLUMN_COST, 100);
        productHistoryUpdateValues.put(ProductHistory.COLUMN_DATE, "sample");
        productHistoryUpdateValues.put(ProductHistory.COLUMN_QUANTITY, 100);
        productHistoryUpdateValues.put(ProductHistory.COLUMN_SALE_PRICE, 100);
        productHistoryUpdateValues.put(ProductHistory.COLUMN_PRODUCT_ID, productsRowId);

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor ProductHistoryCursor = mContext.getContentResolver().query(
                ProductHistory.CONTENT_URI,
                null, null, null, null);

        ProductHistoryCursor.moveToFirst();

        mContext.getContentResolver().registerContentObserver(ProductHistory.CONTENT_URI, true, tco);
        ProductHistoryCursor.registerContentObserver(tco);

        int productHistoryUpdateCount = mContext.getContentResolver().update(ProductHistory.CONTENT_URI, productHistoryUpdateValues,
                ProductHistory._ID + "=" +
                        Long.toString(productHistoryRowId), null);
        assertEquals("Update more than one row in -ProductHistory table-", productHistoryUpdateCount, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        ProductHistoryCursor.unregisterContentObserver(tco);
        ProductHistoryCursor = mContext.getContentResolver().query(
                ProductHistory.buildProductHistoryUri(productHistoryRowId), null, null, null, null);
        ProductHistoryCursor.moveToFirst();

        TestUtilities.validateCursor("testUpdate.  Error validating -ProductHistory table- update.", ProductHistoryCursor,
                productHistoryUpdateValues, ProductHistory.TABLE_NAME);
        ProductHistoryCursor.close();
        //endregion

        //region 12 DetailSaleTaxes table
        ContentValues detailSaleTaxesValues = TestUtilities.createDetailSaleTaxesValues(detailSaleRowId, taxTypesRowId);
        Uri detailSaleTaxesUri = mContext.getContentResolver().insert(
                DetailSaleTaxes.CONTENT_URI,
                detailSaleTaxesValues
        );
        long detailSaleTaxesRowId = ContentUris.parseId(detailSaleTaxesUri);

        // Verify we got a row back.
        assertTrue("Unable to insert -DetailSaleTaxes table- row", detailSaleTaxesRowId != -1);

        ContentValues detailSaleTaxesUpdateValues = new ContentValues();
        detailSaleTaxesUpdateValues.put(DetailSaleTaxes._ID, detailSaleTaxesRowId);
        detailSaleTaxesUpdateValues.put(DetailSaleTaxes.COLUMN_AMOUNT, 100);
        detailSaleTaxesUpdateValues.put(DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, detailSaleRowId);
        detailSaleTaxesUpdateValues.put(DetailSaleTaxes.COLUMN_TAX_TYPE_ID, taxTypesRowId);

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor DetailSaleTaxesCursor = mContext.getContentResolver().query(
                DetailSaleTaxes.CONTENT_URI,
                null, null, null, null);

        DetailSaleTaxesCursor.moveToFirst();

        mContext.getContentResolver().registerContentObserver(DetailSaleTaxes.CONTENT_URI, true, tco);
        DetailSaleTaxesCursor.registerContentObserver(tco);

        int DetailSaleTaxesUpdateCount = mContext.getContentResolver().update(DetailSaleTaxes.CONTENT_URI,
                detailSaleTaxesUpdateValues,
                DetailSaleTaxes._ID + "=" +
                        Long.toString(detailSaleTaxesRowId), null);
        assertEquals("Update more than one row in -DetailSaleTaxes table-", DetailSaleTaxesUpdateCount, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        DetailSaleTaxesCursor.unregisterContentObserver(tco);
        DetailSaleTaxesCursor = mContext.getContentResolver().query(
                DetailSaleTaxes.buildDetailSaleTaxesUri(detailSaleTaxesRowId), null, null, null, null);
        DetailSaleTaxesCursor.moveToFirst();

        TestUtilities.validateCursor("testUpdate.  Error validating -DetailSaleTaxes table- update.", DetailSaleTaxesCursor,
                detailSaleTaxesUpdateValues, DetailSaleTaxes.TABLE_NAME);
        DetailSaleTaxesCursor.close();
        //endregion

        //region 13 DetailSaleProducts table
        ContentValues detailSaleProductsValues = TestUtilities.createDetailSaleProductsValues(productsRowId, detailSaleRowId);
        Uri detailSaleProductsUri = mContext.getContentResolver().insert(
                DetailSaleProducts.CONTENT_URI,
                detailSaleProductsValues
        );
        long detailSaleProductsRowId = ContentUris.parseId(detailSaleProductsUri);

        // Verify we got a row back.
        assertTrue("Unable to insert -DetailSaleProducts table- row", detailSaleProductsRowId != -1);

        ContentValues detailSaleProductsUpdateValues = new ContentValues();
        detailSaleProductsUpdateValues.put(DetailSaleProducts._ID, detailSaleProductsRowId);
        detailSaleProductsUpdateValues.put(DetailSaleProducts.COLUMN_QUANTITY, 100);
        detailSaleProductsUpdateValues.put(DetailSaleProducts.COLUMN_AMOUNT, 100);
        detailSaleProductsUpdateValues.put(DetailSaleProducts.COLUMN_DETAIL_SALE_ID, detailSaleRowId);
        detailSaleProductsUpdateValues.put(DetailSaleProducts.COLUMN_PRODUCT_ID, productsRowId);

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor detailSaleProductsCursor = mContext.getContentResolver().query(
                DetailSaleProducts.CONTENT_URI,
                null, null, null, null);

        detailSaleProductsCursor.moveToFirst();

        mContext.getContentResolver().registerContentObserver(DetailSaleProducts.CONTENT_URI, true, tco);
        detailSaleProductsCursor.registerContentObserver(tco);

        int detailSaleProductsUpdateCount = mContext.getContentResolver().update(DetailSaleProducts.CONTENT_URI,
                detailSaleProductsUpdateValues,
                DetailSaleProducts._ID + "=" +
                        Long.toString(detailSaleProductsRowId), null);
        assertEquals("Update more than one row in -DetailSaleProducts table-", detailSaleProductsUpdateCount, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        detailSaleProductsCursor.unregisterContentObserver(tco);
        detailSaleProductsCursor = mContext.getContentResolver().query(
                DetailSaleProducts.buildDetailSaleProducts(detailSaleProductsRowId), null, null, null, null);
        detailSaleProductsCursor.moveToFirst();

        TestUtilities.validateCursor("testUpdate.  Error validating -DetailSaleProducts table- update.", detailSaleProductsCursor,
                detailSaleProductsUpdateValues, DetailSaleProducts.TABLE_NAME);
        detailSaleProductsCursor.close();
        //endregion
    }

    //region Create ContentValues
    //region 1 State table
    private ContentValues[] createBulkInsertStateValues() {

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues stateValues = new ContentValues();
            stateValues.put(State.COLUMN_STATE_POINTER, i * 10);
            returnContentValues[i] = stateValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 2 CostTypes table
    private ContentValues[] createBulkInsertCostTypesValues() {

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues costTypesValues = new ContentValues();
            costTypesValues.put(CostTypes.COLUMN_COST_TYPE_POINTER, i * 10);
            returnContentValues[i] = costTypesValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 3 Customers table
    private ContentValues[] createBulkInsertCustomersValues() {

        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValuesState = TestUtilities.createStateValues();
        long stateRowId = db.insert(KasebContract.State.TABLE_NAME, null, testValuesState);

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues customersValues = new ContentValues();
            customersValues.put(Customers.COLUMN_FIRST_NAME, "sample");
            customersValues.put(Customers.COLUMN_LAST_NAME, "sample");
            customersValues.put(Customers.COLUMN_PHONE_MOBILE, "sample" + i);
            customersValues.put(Customers.COLUMN_PHONE_WORK, "sample");
            customersValues.put(Customers.COLUMN_PHONE_FAX, "sample");
            customersValues.put(Customers.COLUMN_PHONE_OTHER, "sample");
            customersValues.put(Customers.COLUMN_EMAIL, "sample");
            customersValues.put(Customers.COLUMN_ADDRESS_COUNTRY, "sample");
            customersValues.put(Customers.COLUMN_ADDRESS_CITY, "sample");
            customersValues.put(Customers.COLUMN_ADDRESS_STREET, "sample");
            customersValues.put(Customers.COLUMN_ADDRESS_POSTAL_CODE, "sample");
            customersValues.put(Customers.COLUMN_DESCRIPTION, "sample");
            customersValues.put(Customers.COLUMN_IS_DELETED, 0);
            customersValues.put(Customers.COLUMN_STATE_ID, stateRowId);
            returnContentValues[i] = customersValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 4 Costs table
    private ContentValues[] createBulkInsertCostsValues() {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValuesCostTypes = TestUtilities.createCostTypesValues();
        long costTypesRowId = db.insert(KasebContract.CostTypes.TABLE_NAME, null, testValuesCostTypes);

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues costsValues = new ContentValues();
            costsValues.put(Costs.COLUMN_COST_TYPE_ID, costTypesRowId);
            costsValues.put(Costs.COLUMN_COST_NAME, "sample");
            costsValues.put(Costs.COLUMN_COST_CODE, "sample" + i);
            costsValues.put(Costs.COLUMN_AMOUNT, i * 10);
            costsValues.put(Costs.COLUMN_DATE, "sample");
            costsValues.put(Costs.COLUMN_DESCRIPTION, "sample");

            returnContentValues[i] = costsValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 5 PaymentMethods table
    private ContentValues[] createBulkInsertPaymentMethodsValues() {

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues paymentMethodsValues = new ContentValues();
            paymentMethodsValues.put(PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER, i * 10);
            returnContentValues[i] = paymentMethodsValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 6 TaxTypes table
    private ContentValues[] createBulkInsertTaxTypesValues() {

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues taxTypesValues = new ContentValues();
            taxTypesValues.put(TaxTypes.COLUMN_TAX_TYPE_POINTER, i * 10);
            returnContentValues[i] = taxTypesValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 7 Products table
    private ContentValues[] createBulkInsertProductsValues() {

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues productsValues = new ContentValues();
            productsValues.put(Products.COLUMN_PRODUCT_NAME, "sample" + i);
            productsValues.put(Products.COLUMN_PRODUCT_CODE, "sample" + i);
            productsValues.put(Products.COLUMN_DESCRIPTION, "sample");
            productsValues.put(Products.COLUMN_UNIT, "sample");
            returnContentValues[i] = productsValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 8 Sales table
    private ContentValues[] createBulkInsertSalesValues() {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValuesState = TestUtilities.createStateValues();
        long stateRowId = db.insert(KasebContract.State.TABLE_NAME, null, testValuesState);

        ContentValues testValuesCustomers = TestUtilities.createCustomersValues(stateRowId);
        long customersRowId = db.insert(KasebContract.Customers.TABLE_NAME, null, testValuesCustomers);

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues salesValues = new ContentValues();
            salesValues.put(Sales.COLUMN_IS_DELETED, 0);
            salesValues.put(Sales.COLUMN_SALE_CODE, "sample" + i);
            salesValues.put(Sales.COLUMN_CUSTOMER_ID, 100);
            salesValues.put(Sales.COLUMN_CUSTOMER_ID, customersRowId);
            returnContentValues[i] = salesValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 9 DetailSale table
    private ContentValues[] createBulkInsertDetailSaleValues() {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValuesState = TestUtilities.createStateValues();
        long stateRowId = db.insert(KasebContract.State.TABLE_NAME, null, testValuesState);

        ContentValues testValuesCustomers = TestUtilities.createCustomersValues(stateRowId);
        long customersRowId = db.insert(KasebContract.Customers.TABLE_NAME, null, testValuesCustomers);

        long[] salesRowIdArray = new long[BULK_INSERT_RECORDS_TO_INSERT];

        ContentValues testValuesSales;

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            testValuesSales = TestUtilities.createSalesValues(customersRowId);
            salesRowIdArray[i] = db.insert(KasebContract.Sales.TABLE_NAME, null, testValuesSales);
        }

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues detailSaleValues = new ContentValues();
            detailSaleValues.put(DetailSale.COLUMN_DATE, "sample");
            detailSaleValues.put(DetailSale.COLUMN_ITEMS_NUMBER, 100);
            detailSaleValues.put(DetailSale.COLUMN_SUB_TOTAL, 100);
            detailSaleValues.put(DetailSale.COLUMN_TOTAL_DISCOUNT, 100);
            detailSaleValues.put(DetailSale.COLUMN_TOTAL_TAX, 100);
            detailSaleValues.put(DetailSale.COLUMN_TOTAL_DUE, 100);
            detailSaleValues.put(DetailSale.COLUMN_TOTAL_PAID, 100);
            detailSaleValues.put(DetailSale.COLUMN_IS_BALANCED, 0);
            detailSaleValues.put(DetailSale.COLUMN_SALE_ID, salesRowIdArray[i]);
            returnContentValues[i] = detailSaleValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 10 DetailSalePayments table
    private ContentValues[] createBulkInsertDetailSalePaymentsValues() {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValuesState = TestUtilities.createStateValues();
        long stateRowId = db.insert(KasebContract.State.TABLE_NAME, null, testValuesState);

        ContentValues testValuesCustomers = TestUtilities.createCustomersValues(stateRowId);
        long customersRowId = db.insert(KasebContract.Customers.TABLE_NAME, null, testValuesCustomers);

        ContentValues testValuesSales = TestUtilities.createSalesValues(customersRowId);
        long salesRowId = db.insert(KasebContract.Sales.TABLE_NAME, null, testValuesSales);

        ContentValues testValuesDetailSale = TestUtilities.createDetailSaleValues(salesRowId);
        long detailSaleRowId = db.insert(KasebContract.DetailSale.TABLE_NAME, null, testValuesDetailSale);

        ContentValues testValuesPaymentMethods = TestUtilities.createPaymentMethodsValues();
        long paymentMethodsRowId = db.insert(KasebContract.PaymentMethods.TABLE_NAME, null, testValuesPaymentMethods);

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues detailSalePaymentsValues = new ContentValues();
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_DUE_DATE, "sample");
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_AMOUNT, 100);
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, 100);
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_DETAIL_SALE_ID, detailSaleRowId);
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, paymentMethodsRowId);
            returnContentValues[i] = detailSalePaymentsValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 11 ProductHistory table
    private ContentValues[] createBulkInsertProductHistoryValues() {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValuesProducts = TestUtilities.createProductsValues();
        long productsRowId = db.insert(KasebContract.Products.TABLE_NAME, null, testValuesProducts);

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues productHistoryValues = new ContentValues();
            productHistoryValues.put(ProductHistory.COLUMN_COST, 100);
            productHistoryValues.put(ProductHistory.COLUMN_DATE, "sample");
            productHistoryValues.put(ProductHistory.COLUMN_QUANTITY, 100);
            productHistoryValues.put(ProductHistory.COLUMN_SALE_PRICE, 100);
            productHistoryValues.put(ProductHistory.COLUMN_PRODUCT_ID, productsRowId);

            returnContentValues[i] = productHistoryValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 12 DetailSaleTaxes table
    private ContentValues[] createBulkInsertDetailSaleTaxesValues() {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValuesState = TestUtilities.createStateValues();
        long stateRowId = db.insert(KasebContract.State.TABLE_NAME, null, testValuesState);

        ContentValues testValuesCustomers = TestUtilities.createCustomersValues(stateRowId);
        long customersRowId = db.insert(KasebContract.Customers.TABLE_NAME, null, testValuesCustomers);

        ContentValues testValuesSales = TestUtilities.createSalesValues(customersRowId);
        long salesRowId = db.insert(KasebContract.Sales.TABLE_NAME, null, testValuesSales);

        ContentValues testValuesDetailSale = TestUtilities.createDetailSaleValues(salesRowId);
        long detailSaleRowId = db.insert(KasebContract.DetailSale.TABLE_NAME, null, testValuesDetailSale);

        ContentValues testValuesTaxTypes = TestUtilities.createTaxTypesValues();
        long taxTypesRowId = db.insert(KasebContract.TaxTypes.TABLE_NAME, null, testValuesTaxTypes);

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues detailSaleTaxesValues = new ContentValues();
            detailSaleTaxesValues.put(DetailSaleTaxes.COLUMN_AMOUNT, 100);
            detailSaleTaxesValues.put(DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, detailSaleRowId);
            detailSaleTaxesValues.put(DetailSaleTaxes.COLUMN_TAX_TYPE_ID, taxTypesRowId);
            returnContentValues[i] = detailSaleTaxesValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 13 DetailSaleProducts table
    private ContentValues[] createBulkInsertDetailSaleProductsValues() {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValuesState = TestUtilities.createStateValues();
        long stateRowId = db.insert(KasebContract.State.TABLE_NAME, null, testValuesState);

        ContentValues testValuesCustomers = TestUtilities.createCustomersValues(stateRowId);
        long customersRowId = db.insert(KasebContract.Customers.TABLE_NAME, null, testValuesCustomers);

        ContentValues testValuesSales = TestUtilities.createSalesValues(customersRowId);
        long salesRowId = db.insert(KasebContract.Sales.TABLE_NAME, null, testValuesSales);

        ContentValues testValuesDetailSale = TestUtilities.createDetailSaleValues(salesRowId);
        long detailSaleRowId = db.insert(KasebContract.DetailSale.TABLE_NAME, null, testValuesDetailSale);

        ContentValues testValuesProducts = TestUtilities.createProductsValues();
        long productsRowId = db.insert(KasebContract.Products.TABLE_NAME, null, testValuesProducts);

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues detailSaleProductsValues = new ContentValues();
            detailSaleProductsValues.put(DetailSaleProducts.COLUMN_QUANTITY, 100);
            detailSaleProductsValues.put(DetailSaleProducts.COLUMN_AMOUNT, 100);
            detailSaleProductsValues.put(DetailSaleProducts.COLUMN_DETAIL_SALE_ID, detailSaleRowId);
            detailSaleProductsValues.put(DetailSaleProducts.COLUMN_PRODUCT_ID, productsRowId);
            returnContentValues[i] = detailSaleProductsValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 14 Customers table With StateId
    private ContentValues[] createBulkInsertCustomersByStateIdValues(long stateRowId) {

        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues customersValues = new ContentValues();
            customersValues.put(Customers.COLUMN_FIRST_NAME, "sample" + i);
            customersValues.put(Customers.COLUMN_LAST_NAME, "sample");
            customersValues.put(Customers.COLUMN_PHONE_MOBILE, "sample" + i);
            customersValues.put(Customers.COLUMN_PHONE_WORK, "sample");
            customersValues.put(Customers.COLUMN_PHONE_FAX, "sample");
            customersValues.put(Customers.COLUMN_PHONE_OTHER, "sample");
            customersValues.put(Customers.COLUMN_EMAIL, "sample");
            customersValues.put(Customers.COLUMN_ADDRESS_COUNTRY, "sample");
            customersValues.put(Customers.COLUMN_ADDRESS_CITY, "sample");
            customersValues.put(Customers.COLUMN_ADDRESS_STREET, "sample");
            customersValues.put(Customers.COLUMN_ADDRESS_POSTAL_CODE, "sample");
            customersValues.put(Customers.COLUMN_DESCRIPTION, "sample");
            customersValues.put(Customers.COLUMN_IS_DELETED, 0);
            customersValues.put(Customers.COLUMN_STATE_ID, stateRowId);
            returnContentValues[i] = customersValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 15 Other Query : SALES_BY_CUSTOMER
    private ContentValues[] createBulkInsertSalesByCustomerIdValues(long customerId) {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues salesValues = new ContentValues();
            salesValues.put(Sales.COLUMN_IS_DELETED, 0);
            salesValues.put(Sales.COLUMN_SALE_CODE, "sample" + i);
            salesValues.put(Sales.COLUMN_CUSTOMER_ID, 100);
            salesValues.put(Sales.COLUMN_CUSTOMER_ID, customerId);
            returnContentValues[i] = salesValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 16 Other Query : DETAIL_SALE_BY_IS_BALANCED
    private ContentValues[] createBulkInsertDetailSaleWithIsBalancedValues() {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValuesState = TestUtilities.createStateValues();
        long stateRowId = db.insert(KasebContract.State.TABLE_NAME, null, testValuesState);

        ContentValues testValuesCustomers = TestUtilities.createCustomersValues(stateRowId);
        long customersRowId = db.insert(KasebContract.Customers.TABLE_NAME, null, testValuesCustomers);

        long[] salesRowIdArray = new long[BULK_INSERT_RECORDS_TO_INSERT];

        ContentValues testValuesSales;

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            testValuesSales = TestUtilities.createSalesValues(customersRowId);
            salesRowIdArray[i] = db.insert(KasebContract.Sales.TABLE_NAME, null, testValuesSales);
        }

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues detailSaleValues = new ContentValues();
            detailSaleValues.put(DetailSale.COLUMN_DATE, "sample");
            detailSaleValues.put(DetailSale.COLUMN_ITEMS_NUMBER, 100);
            detailSaleValues.put(DetailSale.COLUMN_SUB_TOTAL, 100);
            detailSaleValues.put(DetailSale.COLUMN_TOTAL_DISCOUNT, 100);
            detailSaleValues.put(DetailSale.COLUMN_TOTAL_TAX, 100);
            detailSaleValues.put(DetailSale.COLUMN_TOTAL_DUE, 100);
            detailSaleValues.put(DetailSale.COLUMN_TOTAL_PAID, 100);
            detailSaleValues.put(DetailSale.COLUMN_IS_BALANCED, 1);
            detailSaleValues.put(DetailSale.COLUMN_SALE_ID, salesRowIdArray[i]);
            returnContentValues[i] = detailSaleValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 17 Other Query : PRODUCT_HISTORY_BY_PRODUCT_ID
    private ContentValues[] createBulkInsertProductHistoryByProductsIdValues(long prouctsId) {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues productHistoryValues = new ContentValues();
            productHistoryValues.put(ProductHistory.COLUMN_COST, 100);
            productHistoryValues.put(ProductHistory.COLUMN_DATE, "sample");
            productHistoryValues.put(ProductHistory.COLUMN_QUANTITY, 100);
            productHistoryValues.put(ProductHistory.COLUMN_SALE_PRICE, 100);
            productHistoryValues.put(ProductHistory.COLUMN_PRODUCT_ID, prouctsId);

            returnContentValues[i] = productHistoryValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 18 Other Query : DETAIL_SALE_PRODUCT_BY_DETAIL_SALE_ID
    private ContentValues[] createBulkInsertDetailSaleProductsByDetailSaleIdValues(long detailSaleId) {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValuesProducts = TestUtilities.createProductsValues();
        long productsRowId = db.insert(KasebContract.Products.TABLE_NAME, null, testValuesProducts);

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues detailSaleProductsValues = new ContentValues();
            detailSaleProductsValues.put(DetailSaleProducts.COLUMN_QUANTITY, 100);
            detailSaleProductsValues.put(DetailSaleProducts.COLUMN_AMOUNT, 100);
            detailSaleProductsValues.put(DetailSaleProducts.COLUMN_DETAIL_SALE_ID, detailSaleId);
            detailSaleProductsValues.put(DetailSaleProducts.COLUMN_PRODUCT_ID, productsRowId);
            returnContentValues[i] = detailSaleProductsValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 19 Other Query : DETAIL_SALE_PAYMENT_BY_DETAIL_SALE_ID
    private ContentValues[] createBulkInsertDetailSalePaymentsByDetailSaleIdValues(long detailSaleId) {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValuesPaymentMethods = TestUtilities.createPaymentMethodsValues();
        long paymentMethodsRowId = db.insert(KasebContract.PaymentMethods.TABLE_NAME, null, testValuesPaymentMethods);

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues detailSalePaymentsValues = new ContentValues();
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_DUE_DATE, "sample");
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_AMOUNT, 100);
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, 100);
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_DETAIL_SALE_ID, detailSaleId);
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, paymentMethodsRowId);
            returnContentValues[i] = detailSalePaymentsValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 20 Other Query : DETAIL_SALE_PAYMENT_BY_PAYMENT_METHOD
    private ContentValues[] createBulkInsertDetailSalePaymentsByPaymentMethodValues(long paymentMethodId) {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValuesState = TestUtilities.createStateValues();
        long stateRowId = db.insert(KasebContract.State.TABLE_NAME, null, testValuesState);

        ContentValues testValuesCustomers = TestUtilities.createCustomersValues(stateRowId);
        long customersRowId = db.insert(KasebContract.Customers.TABLE_NAME, null, testValuesCustomers);

        ContentValues testValuesDetailSale = TestUtilities.createDetailSaleValues(customersRowId);
        long detailSaleRowId = db.insert(KasebContract.DetailSale.TABLE_NAME, null, testValuesDetailSale);

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues detailSalePaymentsValues = new ContentValues();
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_DUE_DATE, "sample");
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_AMOUNT, 100);
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, 100);
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_DETAIL_SALE_ID, detailSaleRowId);
            detailSalePaymentsValues.put(DetailSalePayments.COLUMN_PAYMENT_METHOD_ID, paymentMethodId);
            returnContentValues[i] = detailSalePaymentsValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 21 Other Query : DETAIL_SALE_TAXES_BY_DETAIL_SALE_ID
    private ContentValues[] createBulkInsertDetailSaleTaxesByDetailSaleIdValues(long detailSaleId) {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValuesTaxTypes = TestUtilities.createTaxTypesValues();
        long taxTypesRowId = db.insert(KasebContract.TaxTypes.TABLE_NAME, null, testValuesTaxTypes);

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues detailSaleTaxesValues = new ContentValues();
            detailSaleTaxesValues.put(DetailSaleTaxes.COLUMN_AMOUNT, 100);
            detailSaleTaxesValues.put(DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, detailSaleId);
            detailSaleTaxesValues.put(DetailSaleTaxes.COLUMN_TAX_TYPE_ID, taxTypesRowId);
            returnContentValues[i] = detailSaleTaxesValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 22 Other Query : DETAIL_SALE_TAXES_BY_TAX_TYPE
    private ContentValues[] createBulkInsertDetailSaleTaxesByTaxTypesValues(long taxTypesId) {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValuesState = TestUtilities.createStateValues();
        long stateRowId = db.insert(KasebContract.State.TABLE_NAME, null, testValuesState);

        ContentValues testValuesCustomers = TestUtilities.createCustomersValues(stateRowId);
        long customersRowId = db.insert(KasebContract.Customers.TABLE_NAME, null, testValuesCustomers);

        ContentValues testValuesDetailSale = TestUtilities.createDetailSaleValues(customersRowId);
        long detailSaleRowId = db.insert(KasebContract.DetailSale.TABLE_NAME, null, testValuesDetailSale);

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues detailSaleTaxesValues = new ContentValues();
            detailSaleTaxesValues.put(DetailSaleTaxes.COLUMN_AMOUNT, 100);
            detailSaleTaxesValues.put(DetailSaleTaxes.COLUMN_DETAIL_SALE_ID, detailSaleRowId);
            detailSaleTaxesValues.put(DetailSaleTaxes.COLUMN_TAX_TYPE_ID, taxTypesId);
            returnContentValues[i] = detailSaleTaxesValues;
        }
        return returnContentValues;
    }
    //endregion

    //region 23 Other Query : COSTS_BY_TYPE
    private ContentValues[] createBulkInsertCostsByTypesValues(long costTypesId) {
        KasebDbHelper dbHelper = new KasebDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues costsValues = new ContentValues();
            costsValues.put(Costs.COLUMN_COST_TYPE_ID, costTypesId);
            costsValues.put(Costs.COLUMN_COST_NAME, "sample");
            costsValues.put(Costs.COLUMN_COST_CODE, "sample" + i);
            costsValues.put(Costs.COLUMN_AMOUNT, i * 10);
            costsValues.put(Costs.COLUMN_DATE, "sample");
            costsValues.put(Costs.COLUMN_DESCRIPTION, "sample");

            returnContentValues[i] = costsValues;
        }
        return returnContentValues;
    }
    //endregion
    //endregion

    public void testBulkInsert() {
        TestUtilities.TestContentObserver observer = TestUtilities.getTestContentObserver();
        ContentValues[] bulkInsertContentValues;

        //region 1 State table
        bulkInsertContentValues = createBulkInsertStateValues();
        mContext.getContentResolver().registerContentObserver(State.CONTENT_URI, true, observer);
        int stateID = mContext.getContentResolver().bulkInsert(State.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion failed -State table-", BULK_INSERT_RECORDS_TO_INSERT, stateID);

        // A cursor is your primary interface to the query results.
        Cursor stateCursor = mContext.getContentResolver().query(
                State.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Bulk Insertion failed - State table - ", BULK_INSERT_RECORDS_TO_INSERT,
                stateCursor.getCount());

//        // and let's make sure they match the ones we created
//        stateCursor.moveToFirst();
//        for (int i = 9; i > -1; i--, stateCursor.moveToNext()) {
//            TestUtilities.validateCurrentRecord("testBulkInsert. Error validating State " + i,
//                    stateCursor, bulkInsertContentValues[i],State.TABLE_NAME);
//        }
        stateCursor.close();
        deleteAllRecords();
        //endregion

        //region 2 CostTypes table
        bulkInsertContentValues = createBulkInsertCostTypesValues();
        mContext.getContentResolver().registerContentObserver(CostTypes.CONTENT_URI, true, observer);
        int costTypesID = mContext.getContentResolver().bulkInsert(CostTypes.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion failed -CostTypes table-", BULK_INSERT_RECORDS_TO_INSERT, costTypesID);

        // A cursor is your primary interface to the query results.
        Cursor costTypesCursor = mContext.getContentResolver().query(
                CostTypes.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Bulk Insertion failed - CostTypes table - ", BULK_INSERT_RECORDS_TO_INSERT,
                costTypesCursor.getCount());

        costTypesCursor.close();
        deleteAllRecords();
        //endregion

        //region 3 Customers table
        bulkInsertContentValues = createBulkInsertCustomersValues();
        mContext.getContentResolver().registerContentObserver(Customers.CONTENT_URI, true, observer);
        int customersID = mContext.getContentResolver().bulkInsert(Customers.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion failed -Customers table-", BULK_INSERT_RECORDS_TO_INSERT, customersID);

        // A cursor is your primary interface to the query results.
        Cursor customersCursor = mContext.getContentResolver().query(
                Customers.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Bulk Insertion failed - Customers table - ", BULK_INSERT_RECORDS_TO_INSERT,
                customersCursor.getCount());

        customersCursor.close();
        deleteAllRecords();
        //endregion

        //region 4 Costs table
        bulkInsertContentValues = createBulkInsertCostsValues();
        mContext.getContentResolver().registerContentObserver(Costs.CONTENT_URI, true, observer);
        int costsID = mContext.getContentResolver().bulkInsert(Costs.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion failed -Costs table-", BULK_INSERT_RECORDS_TO_INSERT, costsID);

        // A cursor is your primary interface to the query results.
        Cursor costsCursor = mContext.getContentResolver().query(
                Costs.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Bulk Insertion failed - Costs table - ", BULK_INSERT_RECORDS_TO_INSERT,
                costsCursor.getCount());

        costsCursor.close();
        deleteAllRecords();
        //endregion

        //region 5 PaymentMethods table
        bulkInsertContentValues = createBulkInsertPaymentMethodsValues();
        mContext.getContentResolver().registerContentObserver(PaymentMethods.CONTENT_URI, true, observer);
        int paymentMethodsID = mContext.getContentResolver().bulkInsert(PaymentMethods.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion failed -PaymentMethods table-", BULK_INSERT_RECORDS_TO_INSERT, paymentMethodsID);

        // A cursor is your primary interface to the query results.
        Cursor paymentMethodsCursor = mContext.getContentResolver().query(
                PaymentMethods.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Bulk Insertion failed - PaymentMethods table - ", BULK_INSERT_RECORDS_TO_INSERT,
                paymentMethodsCursor.getCount());

        paymentMethodsCursor.close();
        deleteAllRecords();
        //endregion

        //region 6 TaxTypes table
        bulkInsertContentValues = createBulkInsertTaxTypesValues();
        mContext.getContentResolver().registerContentObserver(TaxTypes.CONTENT_URI, true, observer);
        int taxTypesID = mContext.getContentResolver().bulkInsert(TaxTypes.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion failed -TaxTypes table-", BULK_INSERT_RECORDS_TO_INSERT, taxTypesID);

        // A cursor is your primary interface to the query results.
        Cursor taxTypesCursor = mContext.getContentResolver().query(
                TaxTypes.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Bulk Insertion failed - TaxTypes table - ", BULK_INSERT_RECORDS_TO_INSERT,
                taxTypesCursor.getCount());

        taxTypesCursor.close();
        deleteAllRecords();
        //endregion

        //region 7 Products table
        bulkInsertContentValues = createBulkInsertProductsValues();
        mContext.getContentResolver().registerContentObserver(Products.CONTENT_URI, true, observer);
        int productsID = mContext.getContentResolver().bulkInsert(Products.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion failed -Products table-", BULK_INSERT_RECORDS_TO_INSERT, productsID);

        // A cursor is your primary interface to the query results.
        Cursor productsCursor = mContext.getContentResolver().query(
                Products.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Bulk Insertion failed - Products table - ", BULK_INSERT_RECORDS_TO_INSERT,
                productsCursor.getCount());

        productsCursor.close();
        deleteAllRecords();
//        //endregion

        //region 8 Sales table
        bulkInsertContentValues = createBulkInsertSalesValues();
        mContext.getContentResolver().registerContentObserver(Sales.CONTENT_URI, true, observer);
        int salesID = mContext.getContentResolver().bulkInsert(Sales.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion failed -Sales table-", BULK_INSERT_RECORDS_TO_INSERT, salesID);

        // A cursor is your primary interface to the query results.
        Cursor salesCursor = mContext.getContentResolver().query(
                Sales.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Bulk Insertion failed - Sales table - ", BULK_INSERT_RECORDS_TO_INSERT,
                salesCursor.getCount());

        salesCursor.close();
        deleteAllRecords();
        //endregion

        //region 9 DetailSale table
        bulkInsertContentValues = createBulkInsertDetailSaleValues();
        mContext.getContentResolver().registerContentObserver(DetailSale.CONTENT_URI, true, observer);
        int detailSaleID = mContext.getContentResolver().bulkInsert(DetailSale.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion failed -DetailSale table-", BULK_INSERT_RECORDS_TO_INSERT, detailSaleID);

        // A cursor is your primary interface to the query results.
        Cursor detailSaleCursor = mContext.getContentResolver().query(
                DetailSale.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Bulk Insertion failed - DetailSale table - ", BULK_INSERT_RECORDS_TO_INSERT,
                detailSaleCursor.getCount());

        detailSaleCursor.close();
        deleteAllRecords();
        //endregion

        //region 10 DetailSalePayments table
        bulkInsertContentValues = createBulkInsertDetailSalePaymentsValues();
        mContext.getContentResolver().registerContentObserver(DetailSalePayments.CONTENT_URI, true, observer);
        int detailSalePaymentsID = mContext.getContentResolver().bulkInsert(DetailSalePayments.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion failed -DetailSalePayments table-", BULK_INSERT_RECORDS_TO_INSERT, detailSalePaymentsID);

        // A cursor is your primary interface to the query results.
        Cursor detailSalePaymentsCursor = mContext.getContentResolver().query(
                DetailSalePayments.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Bulk Insertion failed - DetailSalePayments table - ", BULK_INSERT_RECORDS_TO_INSERT,
                detailSalePaymentsCursor.getCount());

        detailSalePaymentsCursor.close();
        deleteAllRecords();
        //endregion

        //region 11 ProductHistory table
        bulkInsertContentValues = createBulkInsertProductHistoryValues();
        mContext.getContentResolver().registerContentObserver(ProductHistory.CONTENT_URI, true, observer);
        int productHistoryID = mContext.getContentResolver().bulkInsert(ProductHistory.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion failed -ProductHistory table-", BULK_INSERT_RECORDS_TO_INSERT, productHistoryID);

        // A cursor is your primary interface to the query results.
        Cursor productHistoryCursor = mContext.getContentResolver().query(
                ProductHistory.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Bulk Insertion failed - ProductHistory table - ", BULK_INSERT_RECORDS_TO_INSERT,
                productHistoryCursor.getCount());

        productHistoryCursor.close();
        deleteAllRecords();
        //endregion

        //region 12 DetailSaleTaxes table
        bulkInsertContentValues = createBulkInsertDetailSaleTaxesValues();
        mContext.getContentResolver().registerContentObserver(DetailSaleTaxes.CONTENT_URI, true, observer);
        int detailSaleTaxesID = mContext.getContentResolver().bulkInsert(DetailSaleTaxes.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion failed -DetailSaleTaxes table-", BULK_INSERT_RECORDS_TO_INSERT, detailSaleTaxesID);

        // A cursor is your primary interface to the query results.
        Cursor detailSaleTaxesCursor = mContext.getContentResolver().query(
                DetailSaleTaxes.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Bulk Insertion failed - DetailSaleTaxes table - ", BULK_INSERT_RECORDS_TO_INSERT,
                detailSaleTaxesCursor.getCount());

        detailSaleTaxesCursor.close();
        deleteAllRecords();
        //endregion

        //region 13 DetailSaleProducts table
        bulkInsertContentValues = createBulkInsertDetailSaleProductsValues();
        mContext.getContentResolver().registerContentObserver(DetailSaleProducts.CONTENT_URI, true, observer);
        int detailSaleProductsID = mContext.getContentResolver().bulkInsert(DetailSaleProducts.CONTENT_URI, bulkInsertContentValues);

        observer.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(observer);

        assertEquals("Bulk Insertion failed -DetailSaleProducts table-", BULK_INSERT_RECORDS_TO_INSERT, detailSaleProductsID);

        // A cursor is your primary interface to the query results.
        Cursor detailSaleProductsCursor = mContext.getContentResolver().query(
                DetailSaleProducts.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // columns for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        // we should have as many records in the database as we've inserted
        assertEquals("Number of Inserted Rows is not " + BULK_INSERT_RECORDS_TO_INSERT
                        + " --> \nresult : Bulk Insertion failed - DetailSaleProducts table - ", BULK_INSERT_RECORDS_TO_INSERT,
                detailSaleProductsCursor.getCount());

        detailSaleProductsCursor.close();
        deleteAllRecords();
        //endregion
    }

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // WeatherProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                KasebProvider.class.getName());

        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: WeatherProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + CONTENT_AUTHORITY,
                    providerInfo.authority, CONTENT_AUTHORITY);

        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: WeatherProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }
}