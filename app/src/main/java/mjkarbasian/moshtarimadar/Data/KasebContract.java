package mjkarbasian.moshtarimadar.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by family on 9/21/2016.
 */
public class KasebContract {
    public static final String CONTENT_AUTHORITY = "mjkarbasian.moshtarimadar";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CUSTOMERS = "customers";
    public static final String PATH_SALES = "sales";
    public static final String PATH_DETAIL_SALE = "detail_sale";
    public static final String PATH_PRODUCTS = "products";
    public static final String PATH_PRODUCT_HISTORY = "product_history";
    public static final String PATH_COSTS = "costs";
    public static final String PATH_COST_TYPES = "cost_types";
    public static final String PATH_PAYMENT_METHODS = "payment_methods";
    public static final String PATH_TAX_TYPES = "tax_types";
    public static final String PATH_DETAIL_SALE_PRODUCTS = "detail_sale_products";
    public static final String PATH_DETAIL_SALE_PAYMENTS = "detail_sale_payments";
    public static final String PATH_DETAIL_SALE_TAXES = "detail_sale_taxes";
    public static final String PATH_STATE = "state";

    //region List and types of uri`s:
    // 1)content://AUTHORITY/customers---Dir
    // 2)content://AUTHORITY/customers/id---Item
    // 3)content://AUTHORITY/sales---Dir
    // 3)content://AUTHORITY/sales/id---item
    // 4)content://AUTHORITY/detail_sale/id---item
    // 5)content://AUTHORITY/products---Dir
    // 6)content://AUTHORITY/products/id---item
    // 7)content://AUTHORITY/products_history----Dir
    // 7)content://AUTHORITY/products_history/id---item
    // 8)content://AUTHORITY/costs---Dir
    // 9)content://AUTHORITY/costs/id---item
    // 10)content://AUTHORITY/cost_types---Dir
    // 11)content://AUTHORITY/cost_types/id----item
    // 12)content://AUTHORITY/payment_methods---Dir
    // 13)content://AUTHORITY/payment_methods/id---Item
    // 14)content://AUTHORITY/tax_types---Dir
    // 15)content://AUTHORITY/tax_types/id---Item
    // 16)content://AUTHORITY/detail_sale_products---Dir
    // 17)content://AUTHORITY/detail_sale_products/id---Item
    // 18)content://AUTHORITY/detail_sale_payments---Dir
    // 19)content://AUTHORITY/detail_sale_payments/id---Item
    // 20)content://AUTHORITY/detail_sale_tax---Dir
    // 21)content://AUTHORITY/detail_sale_tax/id---Item
    // 22)content://AUTHORITY/state---Dir
    // 23)content://AUTHORITY/state/id---item
    //new uris
    //24)contents://AUTHORITY/Customers/state_id---Dir
    //25)content://AUTHORITY/Sales/customer_Id---Dir
    //26)content://AUTHORITY/DetailSale/sale_id =---Dir which is one item
    //27)content://AUTHORITY/DetailSale/is_Balanced/isBalanced---Dir
    //28)content://AUTHORITY/DetailSalePayments/detail_sale_id---Dir
    //28')content://AUTHORITY/DetailSalePayments/payment_methods/payment_method_Id---Dir
    //29)content://AUTHORITY/DetailSaleProducts/detail_sale_id---Dir
    //30)content://authority/detail_sale_taxes/detailSaleId---Dir
    //31)content://authority/detail_sale_taxes/tax_type/taxTypeId---Dir
    //32)content://AUTHORITY/product_history/productId---Dir
    //33)content://AUTHORITY/costs/costTypeID---Dir
    //endregion

    public static final class Customers implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CUSTOMERS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CUSTOMERS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CUSTOMERS;

        //Defining columns
        public static final String TABLE_NAME = "customers";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_BIRTHDAY = "birthday";
        public static final String COLUMN_STATE_ID = "state_id";
        public static final String COLUMN_PHONE_MOBILE = "phone_mobile";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IS_DELETED = "is_deleted";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE_WORK = "phone_work";
        public static final String COLUMN_PHONE_HOME = "phone_home";
        public static final String COLUMN_PHONE_OTHER = "phone_other";
        public static final String COLUMN_PHONE_FAX = "phone_fax";
        public static final String COLUMN_ADDRESS_COUNTRY = "address_country";
        public static final String COLUMN_ADDRESS_CITY = "address_city";
        public static final String COLUMN_ADDRESS_STREET = "address_street";
        public static final String COLUMN_ADDRESS_POSTAL_CODE = "address_postal_code";
        public static final String COLUMN_CUSTOMER_PICTURE = "customer_picture";

        //Defining Uri Maker functions : content://AUTHORITY/customers/id
        public static Uri buildCustomerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //query customers by their group : contents:////AUTHORITY/customers/state_id
        public static Uri stateCustomer(long stateId) {
            return CONTENT_URI.buildUpon().appendPath("state_id").appendPath(String.valueOf(stateId)).build();
        }
    }

    public static final class State implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STATE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATE;

        //Defining columns
        public static final String TABLE_NAME = "state";
        public static final String COLUMN_STATE_POINTER = "state_pointer";
        public static final String COLUMN_STATE_COLOR= "state_color";


        //Defining Uri Maker functions : content://AUTHORITY/state/id
        public static Uri buildStateUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class Sales implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SALES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SALES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SALES;

        //Defining columns
        public static final String TABLE_NAME = "sales";
        public static final String COLUMN_CUSTOMER_ID = "customer_id";
        public static final String COLUMN_SALE_CODE = "sale_code";
        public static final String COLUMN_IS_DELETED = "is_deleted";


        //Defining Uri Maker functions : content://AUTHORITY/Sales/id
        public static Uri buildSalesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //defining uri for sale of a customer : content://AUTHORITY/Sales/customer_Id
        public static Uri customerSales(long customerId) {
            return CONTENT_URI.buildUpon().appendPath("customer_id").appendPath(String.valueOf(customerId)).build();
        }

        //definig a join uri for sale and detailSale content://AUTHORITY/Sales/DetailSale
        public static Uri saleDetailSaleJoin(){
            return CONTENT_URI.buildUpon().appendPath(PATH_DETAIL_SALE).build();
        }
    }

    public static final class DetailSale implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DETAIL_SALE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DETAIL_SALE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DETAIL_SALE;

        //Defining columns
        public static final String TABLE_NAME = "detail_sale";
        public static final String COLUMN_SALE_ID = "sale_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_ITEMS_NUMBER = "items_number";
        public static final String COLUMN_SUB_TOTAL = "sub_total";
        public static final String COLUMN_TOTAL_TAX = "total_tax";
        public static final String COLUMN_TOTAL_DISCOUNT = "total_discount";
        public static final String COLUMN_TOTAL_DUE = "total_due";
        public static final String COLUMN_TOTAL_PAID = "total_paid";
        public static final String COLUMN_IS_BALANCED = "is_balanced";

        //Defining Uri Maker functions : content://AUTHORITY/DetailSale/id
        public static Uri buildDetailSaleUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        // Defining Uri for DetailSaleOf a Sale cursor dir??? : content://AUTHORITY/DetailSale/sale_id
        public static Uri saleDetailSale(long saleId) {
            return CONTENT_URI.buildUpon().appendPath("sale_id").appendPath(String.valueOf(saleId)).build();
        }

        //Defineing Uri for DetailSales Which are balance or not,cursor dir Content://AUTHORITY/DetailSale/is_Balanced/isBalanced
        public static Uri isBalanceDetailSale(Boolean isBalance) {
            return CONTENT_URI.buildUpon().appendPath("is_balanced").appendPath(String.valueOf((isBalance ? 1 : 0))).build();
        }
    }

    public static final class DetailSalePayments implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DETAIL_SALE_PAYMENTS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DETAIL_SALE_PAYMENTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DETAIL_SALE_PAYMENTS;

        //Defining columns
        public static final String TABLE_NAME = "detail_sale_payments";
        public static final String COLUMN_DETAIL_SALE_ID = "detail_sale_id";
        public static final String COLUMN_PAYMENT_METHOD_ID = "payment_method_id";
        public static final String COLUMN_DUE_DATE = "due_date";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_MODIFIED_DATE = "modify_date";
        //is pass must change in version 2 biz rule must change.
        public static final String COLUMN_IS_PASS = "is_pass";

        //Defining Uri Maker functions : content://AUTHORITY/DetailSalePayments/id
        public static Uri buildDetailSalePaymentsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //Defining Uri for products of a detailsale cursor_dir: content://AUTHORITY/DetailSalePayments/detail_sale_id
        public static Uri paymentOfDetailSale(long detailSaleId) {
            return CONTENT_URI.buildUpon().appendPath("detail_sale_id").appendPath(String.valueOf(detailSaleId)).build();
        }

        //Define uri for payment by type : content://AUTHORITY/DetailSalePayments/payment_methods/payment_method_id
        public static Uri paymentsByMethod(long paymentMethodId) {
            return CONTENT_URI.buildUpon().appendPath("payment_method_id").appendPath(String.valueOf(paymentMethodId)).build();
        }
    }

    public static final class DetailSaleProducts implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DETAIL_SALE_PRODUCTS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DETAIL_SALE_PRODUCTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DETAIL_SALE_PRODUCTS;

        //Defining columns
        public static final String TABLE_NAME = "detail_sale_products";
        public static final String COLUMN_DETAIL_SALE_ID = "detail_sale_id";
        public static final String COLUMN_PRODUCT_ID = "product_id";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_AMOUNT = "amount";

        //Defining Uri Maker functions cursor_dir : content://AUTHORITY/DetailSaleProducts/id
        public static Uri buildDetailSaleProducts(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //Defining Uri for products of a detailsale cursor_dir: content://AUTHORITY/DetailSaleProducts/detail_sale_id
        public static Uri uriDetailSaleProductsWithDetailSaleId(long detailSaleId) {
            return CONTENT_URI.buildUpon().appendPath("detail_sale_id").appendPath(String.valueOf(detailSaleId)).build();
        }

        //Defining Uri for products of a detailsale cursor_dir: content://AUTHORITY/DetailSaleProducts/product_id
        public static Uri uriDetailSaleProductsWithProductId(long productId) {
            return CONTENT_URI.buildUpon().appendPath("product_id").appendPath(String.valueOf(productId)).build();
        }
    }

    public static final class DetailSaleTaxes implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DETAIL_SALE_TAXES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DETAIL_SALE_TAXES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DETAIL_SALE_TAXES;

        //Defining columns
        public static final String TABLE_NAME = "detail_sale_taxes";
        public static final String COLUMN_DETAIL_SALE_ID = "detail_sale_id";
        public static final String COLUMN_TAX_TYPE_ID = "tax_type_id";
        public static final String COLUMN_AMOUNT = "amount";

        //Defining Uri Maker functions : content://AUTHORITY/DetailSaleTaxes/id
        public static Uri buildDetailSaleTaxesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //Uri for taxes in detail factor: content://authority/detail_sale_taxes/detail_sale_id
        public static Uri taxOfDetailSale(long detailSaleId) {
            return CONTENT_URI.buildUpon().appendPath("detail_sale_id").appendPath(String.valueOf(detailSaleId)).build();
        }

        //Uri for taxes by type : content://authority/detail_sale_taxes/tax_types/tax_type=type
        public static Uri taxOfDetailSaleByType(long taxTypeId) {
            return CONTENT_URI.buildUpon().appendPath("tax_type_id").appendPath(String.valueOf(taxTypeId)).build();
        }
    }

    public static final class PaymentMethods implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PAYMENT_METHODS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PAYMENT_METHODS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PAYMENT_METHODS;

        //Defining columns
        public static final String TABLE_NAME = "payment_methods";
        public static final String COLUMN_PAYMENT_METHOD_POINTER = "payment_method_pointer";

        //Defining Uri Maker functions : content://AUTHORITY/PaymentMethods/id
        public static Uri buildPaymentMethodsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class TaxTypes implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TAX_TYPES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TAX_TYPES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TAX_TYPES;

        //Defining columns
        public static final String TABLE_NAME = "tax_types";
        public static final String COLUMN_TAX_TYPE_POINTER = "tax_type_pointer";

        //Defining Uri Maker functions : content://AUTHORITY/tax_types/id
        public static Uri buildTaxTypesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class Products implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUCTS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        //Defining columns
        public static final String TABLE_NAME = "products";
        public static final String COLUMN_PRODUCT_NAME = "product_name";
        public static final String COLUMN_PRODUCT_CODE = "product_code";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRODUCT_PICTURE = "product_picture" ;

        //Defining Uri Maker functions : content://AUTHORITY/products/id
        public static Uri buildProductsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ProductHistory implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUCT_HISTORY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT_HISTORY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT_HISTORY;

        //Defining columns
        public static final String TABLE_NAME = "product_history";
        public static final String COLUMN_PRODUCT_ID = "product_id";
        public static final String COLUMN_COST = "cost";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_SALE_PRICE = "sale_price";
        public static final String COLUMN_DATE = "date";

        //Defining Uri Maker functions : content://AUTHORITY/product_history/id
        public static Uri buildProductHistoryUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //Definig uri for  query of specific product histories content://AUTHORITY/product_history/product_id
        public static Uri aProductHistory(long productId) {
            return CONTENT_URI.buildUpon().appendPath("product_id").appendPath(String.valueOf(productId)).build();
        }
    }

    public static final class Costs implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_COSTS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COSTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COSTS;

        //Defining columns
        public static final String TABLE_NAME = "costs";
        public static final String COLUMN_COST_NAME = "name";
        public static final String COLUMN_COST_CODE = "code";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_COST_TYPE_ID = "cost_type_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_DESCRIPTION = "description";


        //Defining Uri Maker functions : content://AUTHORITY/DetailSale/id
        public static Uri buildCostsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //define Uri to query cost by cost types content://AUTHORITY/DetailSale/cost_type_id
        public static Uri costsByType(long costTypeID) {
            return CONTENT_URI.buildUpon().appendPath("cost_type_id").appendPath(String.valueOf(costTypeID)).build();
        }
    }

    public static final class CostTypes implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_COST_TYPES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COST_TYPES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COST_TYPES;

        //Defining columns
        public static final String TABLE_NAME = "cost_types";
        public static final String COLUMN_COST_TYPE_POINTER = "cost_type_pointer";

        //Defining Uri Maker functions : content://AUTHORITY/cost_types/id
        public static Uri buildCostTypesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}