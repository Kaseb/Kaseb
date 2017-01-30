package mjkarbasian.moshtarimadar.Data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import mjkarbasian.moshtarimadar.Helpers.Utility;

/**
 * Created by family on 9/30/2016.
 */


public class KasebProvider extends ContentProvider {

    //region defining uri integers
    public static final int CUSTOMERS = 100;
    public static final int CUSTOMERS_RECORD = 101;
    public static final int SALES = 102;
    public static final int SALES_RECORD = 103;
    public static final int DETAIL_SALE = 104;
    public static final int DETAIL_SALE_RECORD = 105;
    public static final int PRODUCTS = 106;
    public static final int PRODUCTS_RECORD = 107;
    public static final int PRODUCT_HISTORY = 110;
    public static final int PRODUCT_HISTORY_RECORD = 111;
    public static final int COSTS = 112;
    public static final int COSTS_RECORD = 113;
    public static final int COST_TYPES = 114;
    public static final int COST_TYPES_RECORD = 115;
    public static final int PAYMENT_METHODS = 116;
    public static final int PAYMENT_METHODS_RECORD = 117;
    public static final int TAX_TYPES = 118;
    public static final int TAX_TYPES_RECORD = 119;
    public static final int DETAIL_SALE_PRODUCTS = 120;
    public static final int DETAIL_SALE_PRODUCTS_RECORD = 121;
    public static final int DETAIL_SALE_PAYMENTS = 122;
    public static final int DETAIL_SALE_PAYMENTS_RECORD = 123;
    public static final int DETAIL_SALE_TAXES = 124;
    public static final int DETAIL_SALE_TAXES_RECORD = 125;
    public static final int STATE = 126;
    public static final int STATE_RECORD = 127;
    public static final int CUSTOMER_BY_STATE = 301;
    public static final int SALES_BY_CUSTOMER = 302;
    public static final int DETAIL_SALE_BY_SALE_ID = 303;
    public static final int DETAIL_SALE_BY_IS_BALANCED = 304;
    public static final int DETAIL_SALE_PAYMENT_BY_DETAIL_SALE_ID = 305;
    public static final int DETAIL_SALE_PAYMENT_BY_PAYMENT_METHOD = 306;
    public static final int DETAIL_SALE_PRODUCT_BY_DETAIL_SALE_ID = 307;
    public static final int DETAIL_SALE_TAXES_BY_DETAIL_SALE_ID = 308;
    public static final int DETAIL_SALE_TAXES_BY_TAX_TYPE = 309;
    public static final int PRODUCT_HISTORY_BY_PRODUCT_ID = 310;
    public static final int COSTS_BY_TYPE = 311;
    public static final int DETAIL_SALE_PRODUCT_BY_PRODUCT_ID = 312;
    public static final int SALE_DETAIL_SALE_JOIN = 400;
    final static String LOG_TAG = KasebProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final SQLiteQueryBuilder sSalesByCustomerQueryBuilder;
    //endregion

    private static final SQLiteQueryBuilder sDetailSaleBySaleQueryBuilder;
    public static KasebDbHelper mOpenHelper;

    static {
        sSalesByCustomerQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN Customer ON customer._id = sales.customer_id
        sSalesByCustomerQueryBuilder.setTables(
                KasebContract.Customers.TABLE_NAME + " INNER JOIN " +
                        KasebContract.Sales.TABLE_NAME +
                        " ON " + KasebContract.Customers.TABLE_NAME +
                        "." + KasebContract.Customers._ID +
                        " = " + KasebContract.Sales.TABLE_NAME +
                        "." + KasebContract.Sales.COLUMN_CUSTOMER_ID);
    }

    static {
        sDetailSaleBySaleQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN Customer ON sale._id = Detailsales.Sale_id
        sDetailSaleBySaleQueryBuilder.setTables(
                KasebContract.Sales.TABLE_NAME + " INNER JOIN " +
                        KasebContract.DetailSale.TABLE_NAME +
                        " ON " + KasebContract.Sales.TABLE_NAME +
                        "." + KasebContract.Sales._ID +
                        " = " + KasebContract.DetailSale.TABLE_NAME +
                        "." + KasebContract.DetailSale.COLUMN_SALE_ID);
    }

    private static String recordSelectionMaker(String tableName) {
        return tableName + "._ID" + " =?";
    }

    private static String dataSetSelectionMaker(String tableName, String ColumnName) {
        return ColumnName + " =?";
        //return tableName + "." + ColumnName + " =?";
    }

    private static UriMatcher buildUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = KasebContract.CONTENT_AUTHORITY;
        uriMatcher.addURI(authority, KasebContract.PATH_CUSTOMERS, CUSTOMERS);
        uriMatcher.addURI(authority, KasebContract.PATH_CUSTOMERS + "/#", CUSTOMERS_RECORD);
        uriMatcher.addURI(authority, KasebContract.PATH_SALES, SALES);
        uriMatcher.addURI(authority, KasebContract.PATH_SALES + "/#", SALES_RECORD);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE, DETAIL_SALE);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE + "/#", DETAIL_SALE_RECORD);
        uriMatcher.addURI(authority, KasebContract.PATH_PRODUCTS, PRODUCTS);
        uriMatcher.addURI(authority, KasebContract.PATH_PRODUCTS + "/#", PRODUCTS_RECORD);
        uriMatcher.addURI(authority, KasebContract.PATH_PRODUCT_HISTORY, PRODUCT_HISTORY);
        uriMatcher.addURI(authority, KasebContract.PATH_PRODUCT_HISTORY + "/#", PRODUCT_HISTORY_RECORD);
        uriMatcher.addURI(authority, KasebContract.PATH_COSTS, COSTS);
        uriMatcher.addURI(authority, KasebContract.PATH_COSTS + "/#", COSTS_RECORD);
        uriMatcher.addURI(authority, KasebContract.PATH_COST_TYPES, COST_TYPES);
        uriMatcher.addURI(authority, KasebContract.PATH_COST_TYPES + "/#", COST_TYPES_RECORD);
        uriMatcher.addURI(authority, KasebContract.PATH_PAYMENT_METHODS, PAYMENT_METHODS);
        uriMatcher.addURI(authority, KasebContract.PATH_PAYMENT_METHODS + "/#", PAYMENT_METHODS_RECORD);
        uriMatcher.addURI(authority, KasebContract.PATH_TAX_TYPES, TAX_TYPES);
        uriMatcher.addURI(authority, KasebContract.PATH_TAX_TYPES + "/#", TAX_TYPES_RECORD);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE_PRODUCTS, DETAIL_SALE_PRODUCTS);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE_PRODUCTS + "/#", DETAIL_SALE_PRODUCTS_RECORD);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE_PAYMENTS, DETAIL_SALE_PAYMENTS);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE_PAYMENTS + "/#", DETAIL_SALE_PAYMENTS_RECORD);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE_TAXES, DETAIL_SALE_TAXES);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE_TAXES + "/#", DETAIL_SALE_TAXES_RECORD);
        uriMatcher.addURI(authority, KasebContract.PATH_STATE, STATE);
        uriMatcher.addURI(authority, KasebContract.PATH_STATE + "/#", STATE_RECORD);
        //new uris
        uriMatcher.addURI(authority, KasebContract.PATH_CUSTOMERS + "/state_id/*", CUSTOMER_BY_STATE);
        uriMatcher.addURI(authority, KasebContract.PATH_SALES + "/customer_id/*", SALES_BY_CUSTOMER);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE + "/sale_id/*", DETAIL_SALE_BY_SALE_ID);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE + "/is_balanced/*", DETAIL_SALE_BY_IS_BALANCED);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE_PAYMENTS + "/detail_sale_id/*", DETAIL_SALE_PAYMENT_BY_DETAIL_SALE_ID);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE_PAYMENTS + "/payment_method_id/*", DETAIL_SALE_PAYMENT_BY_PAYMENT_METHOD);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE_PRODUCTS + "/detail_sale_id/*", DETAIL_SALE_PRODUCT_BY_DETAIL_SALE_ID);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE_TAXES + "/detail_sale_id/*", DETAIL_SALE_TAXES_BY_DETAIL_SALE_ID);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE_TAXES + "/tax_type_id/*", DETAIL_SALE_TAXES_BY_TAX_TYPE);
        uriMatcher.addURI(authority, KasebContract.PATH_PRODUCT_HISTORY + "/product_id/*", PRODUCT_HISTORY_BY_PRODUCT_ID);
        uriMatcher.addURI(authority, KasebContract.PATH_DETAIL_SALE_PRODUCTS + "/product_id/*", DETAIL_SALE_PRODUCT_BY_PRODUCT_ID);
        uriMatcher.addURI(authority, KasebContract.PATH_COSTS + "/cost_type_id/*", COSTS_BY_TYPE);
        uriMatcher.addURI(authority,KasebContract.PATH_SALES +"/"+KasebContract.PATH_DETAIL_SALE,SALE_DETAIL_SALE_JOIN);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new KasebDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            //region 1 CUSTOMERS
            case CUSTOMERS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.Customers.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 2 CUSTOMERS_RECORD
            case CUSTOMERS_RECORD: {
                long id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.Customers.TABLE_NAME,
                        projection,
                        recordSelectionMaker(KasebContract.Customers.TABLE_NAME),
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 3 SALES
            case SALES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.Sales.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 4 SALES_RECORD
            case SALES_RECORD: {
                long id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.Sales.TABLE_NAME,
                        projection,
                        recordSelectionMaker(KasebContract.Sales.TABLE_NAME),
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 5 DETAIL_SALE
            case DETAIL_SALE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSale.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 6 DETAIL_SALE_RECORD
            case DETAIL_SALE_RECORD: {
                long id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSale.TABLE_NAME,
                        projection,
                        recordSelectionMaker(KasebContract.DetailSale.TABLE_NAME),
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 7 PRODUCTS
            case PRODUCTS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.Products.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 8 PRODUCTS_RECORD
            case PRODUCTS_RECORD: {
                long id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.Products.TABLE_NAME,
                        projection,
                        recordSelectionMaker(KasebContract.Products.TABLE_NAME),
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 9 PRODUCT_HISTORY
            case PRODUCT_HISTORY: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.ProductHistory.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 10 PRODUCT_HISTORY_RECORD
            case PRODUCT_HISTORY_RECORD: {
                long id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.ProductHistory.TABLE_NAME,
                        projection,
                        recordSelectionMaker(KasebContract.ProductHistory.TABLE_NAME),
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 11 COSTS
            case COSTS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.Costs.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 12 COSTS_RECORD
            case COSTS_RECORD: {
                long id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.Costs.TABLE_NAME,
                        projection,
                        recordSelectionMaker(KasebContract.Costs.TABLE_NAME),
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 13 COST_TYPES
            case COST_TYPES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.CostTypes.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 14 COST_TYPES_RECORD
            case COST_TYPES_RECORD: {
                long id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.CostTypes.TABLE_NAME,
                        projection,
                        recordSelectionMaker(KasebContract.CostTypes.TABLE_NAME),
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 15 PAYMENT_METHODS
            case PAYMENT_METHODS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.PaymentMethods.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 16 PAYMENT_METHODS_RECORD
            case PAYMENT_METHODS_RECORD: {
                long id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.PaymentMethods.TABLE_NAME,
                        projection,
                        recordSelectionMaker(KasebContract.PaymentMethods.TABLE_NAME),
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 17 TAX_TYPES
            case TAX_TYPES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.TaxTypes.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 18 TAX_TYPES_RECORD
            case TAX_TYPES_RECORD: {
                long id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.TaxTypes.TABLE_NAME,
                        projection,
                        recordSelectionMaker(KasebContract.TaxTypes.TABLE_NAME),
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 19 DETAIL_SALE_PRODUCTS
            case DETAIL_SALE_PRODUCTS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSaleProducts.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 20 DETAIL_SALE_PRODUCTS_RECORD
            case DETAIL_SALE_PRODUCTS_RECORD: {
                long id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSaleProducts.TABLE_NAME,
                        projection,
                        recordSelectionMaker(KasebContract.DetailSaleProducts.TABLE_NAME),
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 21 DETAIL_SALE_PAYMENTS
            case DETAIL_SALE_PAYMENTS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSalePayments.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 22 DETAIL_SALE_PAYMENTS_RECORD
            case DETAIL_SALE_PAYMENTS_RECORD: {
                long id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSalePayments.TABLE_NAME,
                        projection,
                        recordSelectionMaker(KasebContract.DetailSalePayments.TABLE_NAME),
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 23 DETAIL_SALE_TAXES
            case DETAIL_SALE_TAXES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSaleTaxes.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 24 DETAIL_SALE_TAXES_RECORD
            case DETAIL_SALE_TAXES_RECORD: {
                long id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSaleTaxes.TABLE_NAME,
                        projection,
                        recordSelectionMaker(KasebContract.DetailSaleTaxes.TABLE_NAME),
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 25 STATE
            case STATE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.State.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 26 STATE_RECORD
            case STATE_RECORD: {
                long id = ContentUris.parseId(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.State.TABLE_NAME,
                        projection,
                        recordSelectionMaker(KasebContract.State.TABLE_NAME),
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 27 CUSTOMER_BY_STATE
            case CUSTOMER_BY_STATE: {
                String stateId = Utility.getTheLastPathUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.Customers.TABLE_NAME,
                        projection,
                        dataSetSelectionMaker(KasebContract.Customers.TABLE_NAME, KasebContract.Customers.COLUMN_STATE_ID),
                        new String[]{stateId},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 28 SALES_BY_CUSTOMER
            case SALES_BY_CUSTOMER: {
                String customerId = Utility.getTheLastPathUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.Sales.TABLE_NAME,
                        projection,
                        dataSetSelectionMaker(KasebContract.Sales.TABLE_NAME, KasebContract.Sales.COLUMN_CUSTOMER_ID),
                        new String[]{customerId},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 29 DETAIL_SALE_BY_IS_BALANCED
            case DETAIL_SALE_BY_IS_BALANCED: {
                String isBalancedId = Utility.getTheLastPathUri(uri);
                retCursor = sDetailSaleBySaleQueryBuilder.query(
                        mOpenHelper.getReadableDatabase(),
                        projection,
                        KasebContract.DetailSale.COLUMN_IS_BALANCED + " = ?",
                        new String[]{isBalancedId},
                        KasebContract.Sales.COLUMN_CUSTOMER_ID,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 30 PRODUCT_HISTORY_BY_PRODUCT_ID
            case PRODUCT_HISTORY_BY_PRODUCT_ID: {
                String productId = Utility.getTheLastPathUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.ProductHistory.TABLE_NAME,
                        projection,
                        dataSetSelectionMaker(KasebContract.ProductHistory.TABLE_NAME, KasebContract.ProductHistory.COLUMN_PRODUCT_ID),
                        new String[]{productId},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 31 DETAIL_SALE_PRODUCT_BY_DETAIL_SALE_ID
            case DETAIL_SALE_PRODUCT_BY_DETAIL_SALE_ID: {
                String detailSaleId = Utility.getTheLastPathUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSaleProducts.TABLE_NAME,
                        projection,
                        dataSetSelectionMaker(KasebContract.DetailSaleProducts.TABLE_NAME,
                                KasebContract.DetailSaleProducts.COLUMN_DETAIL_SALE_ID),
                        new String[]{detailSaleId},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 32 DETAIL_SALE_PAYMENT_BY_DETAIL_SALE_ID
            case DETAIL_SALE_PAYMENT_BY_DETAIL_SALE_ID: {
                String detailSaleId = Utility.getTheLastPathUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSalePayments.TABLE_NAME,
                        projection,
                        dataSetSelectionMaker(KasebContract.DetailSalePayments.TABLE_NAME, KasebContract.DetailSalePayments.COLUMN_DETAIL_SALE_ID),
                        new String[]{detailSaleId},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 33 DETAIL_SALE_PAYMENT_BY_PAYMENT_METHOD
            case DETAIL_SALE_PAYMENT_BY_PAYMENT_METHOD: {
                String paymentMethodId = Utility.getTheLastPathUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSalePayments.TABLE_NAME,
                        projection,
                        dataSetSelectionMaker(KasebContract.DetailSalePayments.TABLE_NAME, KasebContract.DetailSalePayments.COLUMN_PAYMENT_METHOD_ID),
                        new String[]{paymentMethodId},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 34 DETAIL_SALE_TAXES_BY_DETAIL_SALE_ID
            case DETAIL_SALE_TAXES_BY_DETAIL_SALE_ID: {
                String detailSaleId = Utility.getTheLastPathUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSaleTaxes.TABLE_NAME,
                        projection,
                        dataSetSelectionMaker(KasebContract.DetailSaleTaxes.TABLE_NAME, KasebContract.DetailSaleTaxes.COLUMN_DETAIL_SALE_ID),
                        new String[]{detailSaleId},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 35 DETAIL_SALE_TAXES_BY_TAX_TYPE
            case DETAIL_SALE_TAXES_BY_TAX_TYPE: {
                String taxTypeId = Utility.getTheLastPathUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSaleTaxes.TABLE_NAME,
                        projection,
                        dataSetSelectionMaker(KasebContract.DetailSaleTaxes.TABLE_NAME, KasebContract.DetailSaleTaxes.COLUMN_TAX_TYPE_ID),
                        new String[]{taxTypeId},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 36 COSTS_BY_TYPE
            case COSTS_BY_TYPE: {
                String costTypeId = Utility.getTheLastPathUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.Costs.TABLE_NAME,
                        projection,
                        dataSetSelectionMaker(KasebContract.Costs.TABLE_NAME, KasebContract.Costs.COLUMN_COST_TYPE_ID),
                        new String[]{costTypeId},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 37 DETAIL_SALE_BY_SALE_ID
            case DETAIL_SALE_BY_SALE_ID: {
                String saleId = Utility.getTheLastPathUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSale.TABLE_NAME,
                        projection,
                        dataSetSelectionMaker(KasebContract.DetailSale.TABLE_NAME,
                                KasebContract.DetailSale.COLUMN_SALE_ID),
                        new String[]{saleId},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region 38 DETAIL_SALE_PRODUCT_BY_PRODUCT_ID
            case DETAIL_SALE_PRODUCT_BY_PRODUCT_ID: {
                String productId = Utility.getTheLastPathUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KasebContract.DetailSaleProducts.TABLE_NAME,
                        projection,
                        dataSetSelectionMaker(KasebContract.DetailSaleProducts.TABLE_NAME,
                                KasebContract.DetailSaleProducts.COLUMN_PRODUCT_ID),
                        new String[]{productId},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            //endregion

            //region SALE_DETAIL_SALE_JOIN
            case SALE_DETAIL_SALE_JOIN:{
                retCursor = sDetailSaleBySaleQueryBuilder.query(
                        mOpenHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }
            //endregion

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {

            case CUSTOMERS:
                return KasebContract.Customers.CONTENT_TYPE;
            case CUSTOMERS_RECORD:
                return KasebContract.Customers.CONTENT_ITEM_TYPE;
            case SALES:
                return KasebContract.Sales.CONTENT_TYPE;
            case SALES_RECORD:
                return KasebContract.Sales.CONTENT_ITEM_TYPE;
            case DETAIL_SALE:
                return KasebContract.DetailSale.CONTENT_TYPE;
            case DETAIL_SALE_RECORD:
                return KasebContract.DetailSale.CONTENT_ITEM_TYPE;
            case PRODUCTS:
                return KasebContract.Products.CONTENT_TYPE;
            case PRODUCTS_RECORD:
                return KasebContract.Products.CONTENT_ITEM_TYPE;
            case PRODUCT_HISTORY:
                return KasebContract.ProductHistory.CONTENT_TYPE;
            case PRODUCT_HISTORY_RECORD:
                return KasebContract.ProductHistory.CONTENT_ITEM_TYPE;
            case COSTS:
                return KasebContract.Costs.CONTENT_TYPE;
            case COSTS_RECORD:
                return KasebContract.Costs.CONTENT_ITEM_TYPE;
            case COST_TYPES:
                return KasebContract.CostTypes.CONTENT_TYPE;
            case COST_TYPES_RECORD:
                return KasebContract.CostTypes.CONTENT_ITEM_TYPE;
            case PAYMENT_METHODS:
                return KasebContract.PaymentMethods.CONTENT_TYPE;
            case PAYMENT_METHODS_RECORD:
                return KasebContract.PaymentMethods.CONTENT_ITEM_TYPE;
            case TAX_TYPES:
                return KasebContract.TaxTypes.CONTENT_TYPE;
            case TAX_TYPES_RECORD:
                return KasebContract.TaxTypes.CONTENT_ITEM_TYPE;
            case DETAIL_SALE_PRODUCTS:
                return KasebContract.DetailSaleProducts.CONTENT_TYPE;
            case DETAIL_SALE_PRODUCTS_RECORD:
                return KasebContract.DetailSaleProducts.CONTENT_ITEM_TYPE;
            case DETAIL_SALE_PAYMENTS:
                return KasebContract.DetailSalePayments.CONTENT_TYPE;
            case DETAIL_SALE_PAYMENTS_RECORD:
                return KasebContract.DetailSalePayments.CONTENT_ITEM_TYPE;
            case DETAIL_SALE_TAXES:
                return KasebContract.DetailSaleTaxes.CONTENT_TYPE;
            case DETAIL_SALE_TAXES_RECORD:
                return KasebContract.DetailSaleTaxes.CONTENT_ITEM_TYPE;
            case STATE:
                return KasebContract.State.CONTENT_TYPE;
            case STATE_RECORD:
                return KasebContract.State.CONTENT_ITEM_TYPE;
            //new uris
            case CUSTOMER_BY_STATE:
                return KasebContract.Customers.CONTENT_TYPE;
            case SALES_BY_CUSTOMER:
                return KasebContract.Sales.CONTENT_TYPE;
            case DETAIL_SALE_BY_SALE_ID:
                return KasebContract.DetailSale.CONTENT_ITEM_TYPE;
            case DETAIL_SALE_BY_IS_BALANCED:
                return KasebContract.DetailSale.CONTENT_TYPE;
            case PRODUCT_HISTORY_BY_PRODUCT_ID:
                return KasebContract.ProductHistory.CONTENT_TYPE;
            case COSTS_BY_TYPE:
                return KasebContract.Costs.CONTENT_TYPE;
            case DETAIL_SALE_PRODUCT_BY_DETAIL_SALE_ID:
                return KasebContract.DetailSaleProducts.CONTENT_TYPE;
            case DETAIL_SALE_PAYMENT_BY_DETAIL_SALE_ID:
                return KasebContract.DetailSalePayments.CONTENT_TYPE;
            case DETAIL_SALE_PAYMENT_BY_PAYMENT_METHOD:
                return KasebContract.DetailSalePayments.CONTENT_TYPE;
            case DETAIL_SALE_TAXES_BY_DETAIL_SALE_ID:
                return KasebContract.DetailSaleTaxes.CONTENT_TYPE;
            case DETAIL_SALE_TAXES_BY_TAX_TYPE:
                return KasebContract.DetailSaleTaxes.CONTENT_TYPE;
            case DETAIL_SALE_PRODUCT_BY_PRODUCT_ID:
                return KasebContract.DetailSaleProducts.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case CUSTOMERS: {
                long _id = db.insert(KasebContract.Customers.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KasebContract.Customers.buildCustomerUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case SALES: {
                long _id = db.insert(KasebContract.Sales.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KasebContract.Sales.buildSalesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case DETAIL_SALE: {
                long _id = db.insert(KasebContract.DetailSale.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KasebContract.DetailSale.buildDetailSaleUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case PRODUCTS: {
                long _id = db.insert(KasebContract.Products.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KasebContract.Products.buildProductsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case PRODUCT_HISTORY: {
                long _id = db.insert(KasebContract.ProductHistory.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KasebContract.ProductHistory.buildProductHistoryUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case COSTS: {
                long _id = db.insert(KasebContract.Costs.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KasebContract.Costs.buildCostsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case COST_TYPES: {
                long _id = db.insert(KasebContract.CostTypes.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KasebContract.CostTypes.buildCostTypesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case PAYMENT_METHODS: {
                long _id = db.insert(KasebContract.PaymentMethods.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KasebContract.PaymentMethods.buildPaymentMethodsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TAX_TYPES: {
                long _id = db.insert(KasebContract.TaxTypes.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KasebContract.TaxTypes.buildTaxTypesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case DETAIL_SALE_PRODUCTS: {
                long _id = db.insert(KasebContract.DetailSaleProducts.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KasebContract.DetailSaleProducts.buildDetailSaleProducts(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case DETAIL_SALE_PAYMENTS: {
                long _id = db.insert(KasebContract.DetailSalePayments.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KasebContract.DetailSalePayments.buildDetailSalePaymentsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case DETAIL_SALE_TAXES: {
                long _id = db.insert(KasebContract.DetailSaleTaxes.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KasebContract.DetailSaleTaxes.buildDetailSaleTaxesUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case STATE: {
                long _id = db.insert(KasebContract.State.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KasebContract.State.buildStateUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case CUSTOMERS:
                rowsDeleted = db.delete(
                        KasebContract.Customers.TABLE_NAME, selection, selectionArgs);
                break;
            case SALES:
                rowsDeleted = db.delete(
                        KasebContract.Sales.TABLE_NAME, selection, selectionArgs);
                break;
            case DETAIL_SALE:
                rowsDeleted = db.delete(
                        KasebContract.DetailSale.TABLE_NAME, selection, selectionArgs);
                break;
            case PRODUCTS:
                rowsDeleted = db.delete(
                        KasebContract.Products.TABLE_NAME, selection, selectionArgs);
                break;
            case PRODUCT_HISTORY:
                rowsDeleted = db.delete(
                        KasebContract.ProductHistory.TABLE_NAME, selection, selectionArgs);
                break;
            case COSTS:
                rowsDeleted = db.delete(
                        KasebContract.Costs.TABLE_NAME, selection, selectionArgs);
                break;
            case COST_TYPES:
                rowsDeleted = db.delete(
                        KasebContract.CostTypes.TABLE_NAME, selection, selectionArgs);
                break;
            case PAYMENT_METHODS:
                rowsDeleted = db.delete(
                        KasebContract.PaymentMethods.TABLE_NAME, selection, selectionArgs);
                break;
            case TAX_TYPES:
                rowsDeleted = db.delete(
                        KasebContract.TaxTypes.TABLE_NAME, selection, selectionArgs);
                break;
            case DETAIL_SALE_PRODUCTS:
                rowsDeleted = db.delete(
                        KasebContract.DetailSaleProducts.TABLE_NAME, selection, selectionArgs);
                break;
            case DETAIL_SALE_PAYMENTS:
                rowsDeleted = db.delete(
                        KasebContract.DetailSalePayments.TABLE_NAME, selection, selectionArgs);
                break;
            case DETAIL_SALE_TAXES:
                rowsDeleted = db.delete(
                        KasebContract.DetailSaleTaxes.TABLE_NAME, selection, selectionArgs);
                break;
            case STATE:
                rowsDeleted = db.delete(
                        KasebContract.State.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;
        switch (match) {
            case CUSTOMERS:
                rowsUpdated = db.update(KasebContract.Customers.TABLE_NAME, values, selection, selectionArgs);
                break;
            case SALES:
                rowsUpdated = db.update(KasebContract.Sales.TABLE_NAME, values, selection, selectionArgs);
                break;
            case DETAIL_SALE:
                rowsUpdated = db.update(KasebContract.DetailSale.TABLE_NAME, values, selection, selectionArgs);
                break;
            case PRODUCTS:
                rowsUpdated = db.update(KasebContract.Products.TABLE_NAME, values, selection, selectionArgs);
                break;
            case PRODUCT_HISTORY:
                rowsUpdated = db.update(KasebContract.ProductHistory.TABLE_NAME, values, selection, selectionArgs);
                break;
            case COSTS:
                rowsUpdated = db.update(KasebContract.Costs.TABLE_NAME, values, selection, selectionArgs);
                break;
            case COST_TYPES:
                rowsUpdated = db.update(KasebContract.CostTypes.TABLE_NAME, values, selection, selectionArgs);
                break;
            case PAYMENT_METHODS:
                rowsUpdated = db.update(KasebContract.PaymentMethods.TABLE_NAME, values, selection, selectionArgs);
                break;
            case TAX_TYPES:
                rowsUpdated = db.update(KasebContract.TaxTypes.TABLE_NAME, values, selection, selectionArgs);
                break;
            case DETAIL_SALE_PRODUCTS:
                rowsUpdated = db.update(KasebContract.DetailSaleProducts.TABLE_NAME, values, selection, selectionArgs);
                break;
            case DETAIL_SALE_PAYMENTS:
                rowsUpdated = db.update(KasebContract.DetailSalePayments.TABLE_NAME, values, selection, selectionArgs);
                break;
            case DETAIL_SALE_TAXES:
                rowsUpdated = db.update(KasebContract.DetailSaleTaxes.TABLE_NAME, values, selection, selectionArgs);
                break;
            case STATE:
                rowsUpdated = db.update(KasebContract.State.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {
            case CUSTOMERS: {
                db.beginTransaction();
                long _id = 0;
                try {
                    for (ContentValues value : values) {
//                        long isUnique = DatabaseUtils.queryNumEntries(db, KasebContract.Customers.TABLE_NAME,
//                                KasebContract.Customers.COLUMN_PHONE_MOBILE + " = " + "'"
//                                        + value.getAsString(KasebContract.Customers.COLUMN_PHONE_MOBILE) + "'");
//                        if(isUnique==0)
                        _id = db.insert(KasebContract.Customers.TABLE_NAME, null, value);
                        if (_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            case SALES: {
                db.beginTransaction();
                long _id = 0;
                try {
                    for (ContentValues value : values) {
//                        long isUnique = DatabaseUtils.queryNumEntries(db, KasebContract.Sales.TABLE_NAME,
//                                KasebContract.Sales.COLUMN_SALE_CODE + " = " + "'"
//                                        + value.getAsString(KasebContract.Sales.COLUMN_SALE_CODE) + "'");
//                        if(isUnique==0)
                        _id = db.insert(KasebContract.Sales.TABLE_NAME, null, value);
                        if (_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            case DETAIL_SALE: {
                db.beginTransaction();
                long _id = 0;
                try {
                    for (ContentValues value : values) {
//                        long isUnique = DatabaseUtils.queryNumEntries(db, KasebContract.DetailSale.TABLE_NAME,
//                                KasebContract.DetailSale.COLUMN_SALE_ID + " = " + "'"
//                                        + value.getAsString(KasebContract.DetailSale.COLUMN_SALE_ID) + "'");
//                        if(isUnique==0)
                        _id = db.insert(KasebContract.DetailSale.TABLE_NAME, null, value);
                        if (_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            case PRODUCTS: {
                db.beginTransaction();
                long _id = 0;
                try {
                    for (ContentValues value : values) {
//                        long isUnique = DatabaseUtils.queryNumEntries(db, KasebContract.Products.TABLE_NAME,
//                                KasebContract.Products.COLUMN_PRODUCT_CODE + " = " + "'"
//                                        + value.getAsString(KasebContract.Products.COLUMN_PRODUCT_CODE) + "'");
//                        if(isUnique==0)
                        _id = db.insert(KasebContract.Products.TABLE_NAME, null, value);
                        if (_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            case PRODUCT_HISTORY: {
                db.beginTransaction();
                long _id = 0;
                try {
                    for (ContentValues value : values) {
                        _id = db.insert(KasebContract.ProductHistory.TABLE_NAME, null, value);
                        if (_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            case COSTS: {
                db.beginTransaction();
                long _id = 0;
                try {
                    for (ContentValues value : values) {
//                        long isUnique = DatabaseUtils.queryNumEntries(db, KasebContract.Costs.TABLE_NAME,
//                                KasebContract.Costs.COLUMN_COST_CODE + " = " + "'"
//                                        + value.getAsString(KasebContract.Costs.COLUMN_COST_CODE) + "'");
//                        if(isUnique==0)
                        _id = db.insert(KasebContract.Costs.TABLE_NAME, null, value);
                        if (_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            case COST_TYPES: {
                db.beginTransaction();
                long _id = 0;
                try {
                    for (ContentValues value : values) {
//                        long isUnique = DatabaseUtils.queryNumEntries(db, KasebContract.CostTypes.TABLE_NAME,
//                                KasebContract.CostTypes.COLUMN_COST_TYPE_POINTER + " = " + "'"
//                                        + value.getAsString(KasebContract.CostTypes.COLUMN_COST_TYPE_POINTER) + "'");
//                        if(isUnique==0)
                        _id = db.insert(KasebContract.CostTypes.TABLE_NAME, null, value);
                        if (_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            case PAYMENT_METHODS: {
                db.beginTransaction();
                long _id = 0;
                try {
                    for (ContentValues value : values) {
//                        long isUnique = DatabaseUtils.queryNumEntries(db, KasebContract.PaymentMethods.TABLE_NAME,
//                                KasebContract.PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER + " = " + "'"
//                                        + value.getAsString(KasebContract.PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER) + "'");
//                        if(isUnique==0)
                        _id = db.insert(KasebContract.PaymentMethods.TABLE_NAME, null, value);
                        if (_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            case TAX_TYPES: {
                db.beginTransaction();
                long _id = 0;
                try {
                    for (ContentValues value : values) {
//                        long isUnique = DatabaseUtils.queryNumEntries(db, KasebContract.TaxTypes.TABLE_NAME,
//                                KasebContract.TaxTypes.COLUMN_TAX_TYPE_POINTER + " = " + "'"
//                                        + value.getAsString(KasebContract.TaxTypes.COLUMN_TAX_TYPE_POINTER) + "'");
//                        if(isUnique==0)
                        _id = db.insert(KasebContract.TaxTypes.TABLE_NAME, null, value);
                        if (_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            case DETAIL_SALE_PRODUCTS: {
                db.beginTransaction();
                long _id = 0;
                try {
                    for (ContentValues value : values) {
                        _id = db.insert(KasebContract.DetailSaleProducts.TABLE_NAME, null, value);
                        if (_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            case DETAIL_SALE_PAYMENTS: {
                db.beginTransaction();
                long _id = 0;
                try {
                    for (ContentValues value : values) {
                        _id = db.insert(KasebContract.DetailSalePayments.TABLE_NAME, null, value);
                        if (_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            case DETAIL_SALE_TAXES: {
                db.beginTransaction();
                long _id = 0;
                try {
                    for (ContentValues value : values) {
                        _id = db.insert(KasebContract.DetailSaleTaxes.TABLE_NAME, null, value);
                        if (_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            case STATE: {
                db.beginTransaction();
                long _id = 0;
                try {
                    for (ContentValues value : values) {
//                        long isUnique = DatabaseUtils.queryNumEntries(db, KasebContract.State.TABLE_NAME,
//                                KasebContract.State.COLUMN_STATE_POINTER + " = " + "'"
//                                        + value.getAsString(KasebContract.State.COLUMN_STATE_POINTER) + "'");
//                        if(isUnique==0)
                        _id = db.insert(KasebContract.State.TABLE_NAME, null, value);
                        if (_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            default:
                return super.bulkInsert(uri, values);
        }
        return returnCount;
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)

    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
