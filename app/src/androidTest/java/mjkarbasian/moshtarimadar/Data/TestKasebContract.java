package mjkarbasian.moshtarimadar.Data;

import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;
import java.util.List;

/**
 * Created by Unique on 10/7/2016.
 */
public class TestKasebContract extends AndroidTestCase {

    public static long sample_number = 100;
    private static final String LOG_TAG = TestKasebContract.class.getSimpleName();

    public void testBuildKasebUri() {

        //region 1 State
        long State_id = sample_number++;
        Uri stateUri = KasebContract.State.buildStateUri(State_id);
        Log.i(LOG_TAG, "created uri is: " + stateUri.toString());
        assertNotNull("Error: Null Uri returned. You must fill-in buildStateID in KasebContract.",
                stateUri);

        assertEquals("Error: \"mjkarbasian.moshtarimadar\"  not properly appended to the Uri",
                "mjkarbasian.moshtarimadar", stateUri.getAuthority());

        List<String> stateUriPaths = stateUri.getPathSegments();
        assertEquals("Error: State table  not properly appended to the Uri",
                KasebContract.PATH_STATE, stateUriPaths.get(0));

        assertEquals("Error: State _ID not properly appended to the end of the Uri",
                State_id, Long.parseLong(stateUriPaths.get(1)));
        //endregion

        //region 2 CostTypes
        long CostTypes_id = sample_number++;
        Uri costTypesUri = KasebContract.CostTypes.buildCostTypesUri(CostTypes_id);
        Log.i(LOG_TAG, "created uri is: " + costTypesUri.toString());
        assertNotNull("Error: Null Uri returned.  You must fill-in buildCostTypesID in KasebContract.",
                costTypesUri);

        assertEquals("Error: \"mjkarbasian.moshtarimadar\"  not properly appended to the Uri",
                "mjkarbasian.moshtarimadar", costTypesUri.getAuthority());

        List<String> costTypesUriPaths = costTypesUri.getPathSegments();
        assertEquals("Error: Kaseb  not properly appended to the end of the Uri",
                KasebContract.PATH_COST_TYPES, costTypesUriPaths.get(0));

        assertEquals("Error: CostTypes _ID not properly appended to the end of the Uri",
                CostTypes_id, Long.parseLong(costTypesUriPaths.get(1).toString()));
        //endregion

        //region 3 Customers
        long Customers_id = sample_number++;
        Uri customersUri = KasebContract.Customers.buildCustomerUri(Customers_id);
        Log.i(LOG_TAG, "created uri is: " + customersUri.toString());
        assertNotNull("Error: Null Uri returned.  You must fill-in buildCustomersID in KasebContract.",
                customersUri);

        assertEquals("Error: \"mjkarbasian.moshtarimadar\"  not properly appended to the Uri",
                "mjkarbasian.moshtarimadar", customersUri.getAuthority());

        List<String> customersUriPaths = customersUri.getPathSegments();
        assertEquals("Error: Kaseb  not properly appended to the end of the Uri",
                KasebContract.PATH_CUSTOMERS, customersUriPaths.get(0));

        assertEquals("Error: Customers _ID not properly appended to the end of the Uri",
                Customers_id, Long.parseLong(customersUriPaths.get(1).toString()));
        //endregion

        //region 4 Costs
        long Costs_id = sample_number++;
        Uri costUri = KasebContract.Costs.buildCostsUri(Costs_id);
        Log.i(LOG_TAG, "created uri is: " + costUri.toString());
        assertNotNull("Error: Null Uri returned.  You must fill-in buildCostsID in KasebContract.",
                costUri);

        assertEquals("Error: \"mjkarbasian.moshtarimadar\"  not properly appended to the Uri",
                "mjkarbasian.moshtarimadar", costUri.getAuthority());

        List<String> costUriPaths = costUri.getPathSegments();
        assertEquals("Error: Kaseb  not properly appended to the end of the Uri",
                KasebContract.PATH_COSTS, costUriPaths.get(0));

        assertEquals("Error: Costs _ID not properly appended to the end of the Uri",
                Costs_id, Long.parseLong(costUriPaths.get(1).toString()));
        //endregion

        //region 5 PaymentMethods
        long PaymentMethods_id = sample_number++;
        Uri paymentMethodsUri = KasebContract.PaymentMethods.buildPaymentMethodsUri(PaymentMethods_id);
        Log.i(LOG_TAG, "created uri is: " + paymentMethodsUri.toString());
        assertNotNull("Error: Null Uri returned.  You must fill-in buildPaymentMethodsID in KasebContract.",
                paymentMethodsUri);

        assertEquals("Error: \"mjkarbasian.moshtarimadar\"  not properly appended to the Uri",
                "mjkarbasian.moshtarimadar", paymentMethodsUri.getAuthority());

        List<String> paymentMethodsUriPaths = paymentMethodsUri.getPathSegments();
        assertEquals("Error: Kaseb  not properly appended to the end of the Uri",
                KasebContract.PATH_PAYMENT_METHODS, paymentMethodsUriPaths.get(0));

        assertEquals("Error: PaymentMethods _ID not properly appended to the end of the Uri",
                PaymentMethods_id, Long.parseLong(paymentMethodsUriPaths.get(1).toString()));
        //endregion

        //region 6 TaxTypes
        long TaxTypes_id = sample_number++;
        Uri taxTypesUri = KasebContract.TaxTypes.buildTaxTypesUri(TaxTypes_id);
        Log.i(LOG_TAG, "created uri is: " + taxTypesUri.toString());
        assertNotNull("Error: Null Uri returned.  You must fill-in buildTaxTypesID in KasebContract.",
                taxTypesUri);

        assertEquals("Error: \"mjkarbasian.moshtarimadar\"  not properly appended to the Uri",
                "mjkarbasian.moshtarimadar", taxTypesUri.getAuthority());

        List<String> taxTypesUriPaths = taxTypesUri.getPathSegments();
        assertEquals("Error: Kaseb  not properly appended to the end of the Uri",
                KasebContract.PATH_TAX_TYPES, taxTypesUriPaths.get(0));

        assertEquals("Error: TaxTypes _ID not properly appended to the end of the Uri",
                TaxTypes_id, Long.parseLong(taxTypesUriPaths.get(1).toString()));
        //endregion

        //region 7 Products
        long Products_id = sample_number++;
        Uri productsUri = KasebContract.Products.buildProductsUri(Products_id);
        Log.i(LOG_TAG, "created uri is: " + productsUri.toString());
        assertNotNull("Error: Null Uri returned.  You must fill-in buildProductsID in KasebContract.",
                productsUri);

        assertEquals("Error: \"mjkarbasian.moshtarimadar\"  not properly appended to the Uri",
                "mjkarbasian.moshtarimadar", productsUri.getAuthority());

        List<String> productsUriPaths = productsUri.getPathSegments();
        assertEquals("Error: Kaseb  not properly appended to the end of the Uri",
                KasebContract.PATH_PRODUCTS, productsUriPaths.get(0));

        assertEquals("Error: Products _ID not properly appended to the end of the Uri",
                Products_id, Long.parseLong(productsUriPaths.get(1).toString()));
        //endregion

        //region 8 Sales
        long Sales_id = sample_number++;
        Uri salesUri = KasebContract.Sales.buildSalesUri(Sales_id);
        Log.i(LOG_TAG, "created uri is: " + salesUri.toString());
        assertNotNull("Error: Null Uri returned.  You must fill-in buildSalesID in KasebContract.",
                salesUri);

        assertEquals("Error: \"mjkarbasian.moshtarimadar\"  not properly appended to the Uri",
                "mjkarbasian.moshtarimadar", salesUri.getAuthority());

        List<String> salesUriPaths = salesUri.getPathSegments();
        assertEquals("Error: Kaseb  not properly appended to the end of the Uri",
                KasebContract.PATH_SALES, salesUriPaths.get(0));

        assertEquals("Error: Sales _ID not properly appended to the end of the Uri",
                Sales_id, Long.parseLong(salesUriPaths.get(1).toString()));
        //endregion

        //region 9 DetailSale
        long DetailSale_id = sample_number++;
        Uri detailSaleUri = KasebContract.DetailSale.buildDetailSaleUri(DetailSale_id);
        Log.i(LOG_TAG, "created uri is: " + detailSaleUri.toString());
        assertNotNull("Error: Null Uri returned.  You must fill-in buildDetailSaleID in KasebContract.",
                detailSaleUri);

        assertEquals("Error: \"mjkarbasian.moshtarimadar\"  not properly appended to the Uri",
                "mjkarbasian.moshtarimadar", detailSaleUri.getAuthority());

        List<String> detailSaleUriPaths = detailSaleUri.getPathSegments();
        assertEquals("Error: Kaseb  not properly appended to the end of the Uri",
                KasebContract.PATH_DETAIL_SALE, detailSaleUriPaths.get(0));

        assertEquals("Error: DetailSale _ID not properly appended to the end of the Uri",
                DetailSale_id, Long.parseLong(detailSaleUriPaths.get(1).toString()));
        //endregion

        //region 10 DetailSalePayments
        long DetailSalePayments_id = sample_number++;
        Uri detailSalePaymentsUri = KasebContract.DetailSalePayments.buildDetailSalePaymentsUri(DetailSalePayments_id);
        Log.i(LOG_TAG, "created uri is: " + detailSalePaymentsUri.toString());
        assertNotNull("Error: Null Uri returned.  You must fill-in buildDetailSalePaymentsID in KasebContract.",
                detailSalePaymentsUri);

        assertEquals("Error: \"mjkarbasian.moshtarimadar\"  not properly appended to the Uri",
                "mjkarbasian.moshtarimadar", detailSalePaymentsUri.getAuthority());

        List<String> detailSalePaymentsUriPaths = detailSalePaymentsUri.getPathSegments();
        assertEquals("Error: Kaseb  not properly appended to the end of the Uri",
                KasebContract.PATH_DETAIL_SALE_PAYMENTS, detailSalePaymentsUriPaths.get(0));

        assertEquals("Error: DetailSalePayments _ID not properly appended to the end of the Uri",
                DetailSalePayments_id, Long.parseLong(detailSalePaymentsUriPaths.get(1).toString()));
        //endregion

        //region 11 ProductHistory
        long ProductHistory_id = sample_number++;
        Uri productHistoryUri = KasebContract.ProductHistory.buildProductHistoryUri(ProductHistory_id);
        Log.i(LOG_TAG, "created uri is: " + productHistoryUri.toString());
        assertNotNull("Error: Null Uri returned.  You must fill-in buildProductHistoryID in KasebContract.",
                productHistoryUri);

        assertEquals("Error: \"mjkarbasian.moshtarimadar\"  not properly appended to the Uri",
                "mjkarbasian.moshtarimadar", productHistoryUri.getAuthority());

        List<String> productHistoryUriPaths = productHistoryUri.getPathSegments();
        assertEquals("Error: Kaseb  not properly appended to the end of the Uri",
                KasebContract.PATH_PRODUCT_HISTORY, productHistoryUriPaths.get(0));

        assertEquals("Error: ProductHistory _ID not properly appended to the end of the Uri",
                ProductHistory_id, Long.parseLong(productHistoryUriPaths.get(1).toString()));
        //endregion

        //region 12 DetailSaleTaxes
        long DetailSaleTaxes_id = sample_number++;
        Uri detailSaleTaxesUri = KasebContract.DetailSaleTaxes.buildDetailSaleTaxesUri(DetailSaleTaxes_id);
        Log.i(LOG_TAG, "created uri is: " + detailSaleTaxesUri.toString());
        assertNotNull("Error: Null Uri returned.  You must fill-in buildDetailSaleTaxesID in KasebContract.",
                detailSaleTaxesUri);

        assertEquals("Error: \"mjkarbasian.moshtarimadar\"  not properly appended to the Uri",
                "mjkarbasian.moshtarimadar", detailSaleTaxesUri.getAuthority());

        List<String> detailSaleTaxesUriPaths = detailSaleTaxesUri.getPathSegments();
        assertEquals("Error: Kaseb  not properly appended to the end of the Uri",
                KasebContract.PATH_DETAIL_SALE_TAXES, detailSaleTaxesUriPaths.get(0));

        assertEquals("Error: DetailSaleTaxes _ID not properly appended to the end of the Uri",
                DetailSaleTaxes_id, Long.parseLong(detailSaleTaxesUriPaths.get(1).toString()));
        //endregion

        //region 13 DetailSaleProducts
        long DetailSaleProducts_id = sample_number++;
        Uri detailSaleProductsUri = KasebContract.DetailSaleProducts.buildDetailSaleProducts(DetailSaleProducts_id);
        Log.i(LOG_TAG, "created uri is: " + detailSaleProductsUri.toString());
        assertNotNull("Error: Null Uri returned.  You must fill-in buildDetailSaleProductsID in KasebContract.",
                detailSaleProductsUri);

        assertEquals("Error: \"mjkarbasian.moshtarimadar\"  not properly appended to the Uri",
                "mjkarbasian.moshtarimadar", detailSaleProductsUri.getAuthority());

        List<String> detailSaleProductsUriPaths = detailSaleProductsUri.getPathSegments();
        assertEquals("Error: Kaseb  not properly appended to the end of the Uri",
                KasebContract.PATH_DETAIL_SALE_PRODUCTS, detailSaleProductsUriPaths.get(0));

        assertEquals("Error: DetailSaleProducts _ID not properly appended to the end of the Uri",
                DetailSaleProducts_id, Long.parseLong(detailSaleProductsUriPaths.get(1).toString()));
        //endregion
    }
}