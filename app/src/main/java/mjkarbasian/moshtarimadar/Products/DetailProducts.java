package mjkarbasian.moshtarimadar.Products;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

import mjkarbasian.moshtarimadar.Adapters.DetailProductAdapter;
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;


public class DetailProducts extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    //region Declare Values & Views
    final static int FRAGMENT_PRODUCT_HISTORY_LOADER = 5;
    private final String LOG_TAG = DetailProducts.class.getSimpleName();
    String[] mProjection;
    Cursor mCursor;
    ListView mListView;
    DetailProductAdapter mAdapter = null;
    Long productId;
    FloatingActionButton fab;
    TextView productStock;
    Long stockProduct = 0l;

    ContentValues productHistoryValues = new ContentValues();
    ContentValues productValues = new ContentValues();

    EditText productName;
    EditText productCode;
    EditText productUnit;
    EditText productDescription;
    EditText buyPrice;
    EditText quantity;
    EditText salePrice;
    EditText buyDate;
    EditText discountAmount;
    EditText discountPercent;

    MenuItem saveItem;
    MenuItem editItem;

    AlertDialog.Builder builder;
    AlertDialog dialogView;

    TextInputLayout productNameTextInputLayout;
    TextInputLayout productCodeTextInputLayout;
    TextInputLayout productUnitTextInputLayout;
    TextInputLayout productDescriptionTextInputLayout;
    TextInputLayout salePriceTextInputLayout;
    TextInputLayout quantityTextInputLayout;
    TextInputLayout buyPriceTextInputLayout;
    TextInputLayout buyDateTextInputLayout;
    TextInputLayout discountAmountTextInputLayout;
    TextInputLayout discountPercentTextInputLayout;

    private Uri insertUri;
    //endregion Declare Values & Views

    public DetailProducts() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        productId = Long.parseLong(this.getArguments().getString("productId"));

        mProjection = new String[]{
                KasebContract.ProductHistory._ID,
                KasebContract.ProductHistory.COLUMN_COST,
                KasebContract.ProductHistory.COLUMN_DATE,
                KasebContract.ProductHistory.COLUMN_QUANTITY,
                KasebContract.ProductHistory.COLUMN_SALE_PRICE};

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAdapter = new DetailProductAdapter(
                getActivity(),
                null,
                0);

        View rootView = inflater.inflate(R.layout.fragment_detail_products, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listview_detail_product);
        mListView.setAdapter(mAdapter);
        TextView emptyText = (TextView) rootView.findViewById(R.id.empty_text_view);
        emptyText.setText(getActivity().getResources().getString(R.string.empty_list_text));
        emptyText.setVisibility(View.VISIBLE);
        mListView.setEmptyView(emptyText);

        mListView.post(new Runnable() {
            public void run() {
                mListView.setSelection(mListView.getCount() - 1);
            }
        });

        Utility.setHeightOfListView(mListView);

        //region Fab Add ProductHistory
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab_detail_product);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //region create alert dialog
                builder = new AlertDialog.Builder(getActivity())
                        .setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_add_product_history, null))
                        .setNegativeButton(R.string.discard_button, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialogView.dismiss();
                            }
                        }).setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .setTitle(R.string.title_add_product_history);

                dialogView = builder.create();
                dialogView.show();

                dialogView.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean wantToCloseDialog = false;

                        //region edit product
                        try {
                            if (checkValidityWithChangeColorOfHelperTextForProductHistoryInsert()) {
                                productHistoryValues.put(KasebContract.ProductHistory.COLUMN_COST,
                                        Utility.convertFarsiNumbersToDecimal(buyPrice.getText().toString()));
                                productHistoryValues.put(KasebContract.ProductHistory.COLUMN_QUANTITY,
                                        Utility.convertFarsiNumbersToDecimal(quantity.getText().toString()));
                                productHistoryValues.put(KasebContract.ProductHistory.COLUMN_SALE_PRICE,
                                        Utility.convertFarsiNumbersToDecimal(salePrice.getText().toString()));
                                productHistoryValues.put(KasebContract.ProductHistory.COLUMN_DATE, buyDate.getText().toString());
                                productHistoryValues.put(KasebContract.ProductHistory.COLUMN_PRODUCT_ID, productId);

                                insertUri = getActivity().getContentResolver().insert(
                                        KasebContract.ProductHistory.CONTENT_URI,
                                        productHistoryValues
                                );
                                Toast.makeText(getActivity(), getResources().getString(R.string.msg_insert_succeed), Toast.LENGTH_LONG).show();

                                wantToCloseDialog = true;

                                Utility.setHeightOfListView(mListView);
                                getHelperText();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //endregion edit product

                        if (wantToCloseDialog)
                            dialogView.dismiss();
                    }
                });
                //endregion create alert dialog

                //region Declare Views in dialog
                buyPrice = (EditText) dialogView.findViewById(R.id.add_product_history_buy_price);
                quantity = (EditText) dialogView.findViewById(R.id.add_product_history_quantity);
                salePrice = (EditText) dialogView.findViewById(R.id.add_product_history_sale_price);
                buyDate = (EditText) dialogView.findViewById(R.id.add_product_history_buy_date);
                discountAmount = (EditText) dialogView.findViewById(R.id.add_product_history_discount_amount);
                discountPercent = (EditText) dialogView.findViewById(R.id.add_product_history_discount_percent);

                salePriceTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_add_product_history_sale_price);
                quantityTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_add_product_history_quantity);
                buyPriceTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_add_product_history_buy_price);
                buyDateTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_add_product_history_buy_date);
                discountAmountTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_add_product_history_discount_amount);
                discountPercentTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_add_product_history_discount_percent);

                buyDate.setText(Utility.preInsertDate(getActivity()));

                //region handle asterisk for necessary fields

                //region sale price
                Utility.setAsteriskToTextInputLayout(salePriceTextInputLayout, getResources().getString(R.string.hint_sale_price), false);
                //endregion sale price

                //region quantity
                Utility.setAsteriskToTextInputLayout(quantityTextInputLayout, getResources().getString(R.string.hint_quantity), false);
                //endregion quantity

                //region buy price
                Utility.setAsteriskToTextInputLayout(buyPriceTextInputLayout, getResources().getString(R.string.hint_buy_price), false);
                //endregion buy price

                //region buy date
                Utility.setAsteriskToTextInputLayout(buyDateTextInputLayout, getResources().getString(R.string.hint_date), true);
                //endregion buy date

                //endregion handle asterisk for necessary fields

                //region discountAmount addTextChangedListener
                discountAmount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            Float mDiscountAmount = Float.valueOf(discountAmount.getText().toString());
                            Float mSalePrice = Float.valueOf(salePrice.getText().toString());

                            if (mDiscountAmount > mSalePrice) {
                                discountAmount.setText(salePrice.getText().toString());
                                discountAmountTextInputLayout.setError(getResources().getString(R.string.discount_amount_not_more_than_sale_price));
                                discountAmount.setSelectAllOnFocus(true);
                                discountAmount.selectAll();
                                discountAmount.requestFocus();
                            }
                        } catch (Exception e) {
                            if (salePrice.getText().toString().equals(null) || salePrice.getText().toString().equals("")) {
                                salePriceTextInputLayout.setError(getResources().getString(R.string.example_price));
                                salePrice.requestFocus();
                            } else
                                discountAmountTextInputLayout.setError(getResources().getString(R.string.example_price));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        try {
                            Float mDiscountAmount = Float.valueOf(discountAmount.getText().toString());
                            Float mSalePrice = Float.valueOf(salePrice.getText().toString());

                            buyPrice.setText(String.format("%.2f", Float.valueOf(mSalePrice - mDiscountAmount)));
                        } catch (Exception e) {
                            if (salePrice.getText().toString().equals(null) || salePrice.getText().toString().equals("")) {
                                salePriceTextInputLayout.setError(getResources().getString(R.string.example_price));
                                salePrice.requestFocus();
                            } else
                                discountAmountTextInputLayout.setError(getResources().getString(R.string.example_price));
                        }
                    }
                });
                //endregion discountAmount addTextChangedListener

                //region discountPercent addTextChangedListener
                discountPercent.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            Float mDiscountPercent = Float.valueOf(discountPercent.getText().toString());
                            Float mSalePrice = Float.valueOf(salePrice.getText().toString());

                            if (mDiscountPercent > 100) {
                                discountAmount.setText(String.format("%.2f", Float.valueOf(mDiscountPercent * mSalePrice / 100)));

                                discountPercent.setText(Utility.doubleFormatter(100));
                                discountPercentTextInputLayout.setError(getResources().getString(R.string.not_more_hundred));
                                discountPercent.setSelectAllOnFocus(true);
                                discountPercent.selectAll();
                            }
                        } catch (Exception e) {
                            if (salePrice.getText().toString().equals(null) || salePrice.getText().toString().equals("")) {
                                salePriceTextInputLayout.setError(getResources().getString(R.string.example_price));
                                salePrice.requestFocus();
                            } else
                                discountPercentTextInputLayout.setError(getResources().getString(R.string.example_quantity));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        try {
                            Float mDiscountPercent = Float.valueOf(discountPercent.getText().toString());
                            Float mSalePrice = Float.valueOf(salePrice.getText().toString());

                            buyPrice.setText(String.format("%.2f",
                                    Float.valueOf((float) ((Float.valueOf(100 - mDiscountPercent) / 100.0) * mSalePrice))));
                            discountAmount.setText(String.format("%.2f", Float.valueOf(mDiscountPercent * mSalePrice / 100)));
                        } catch (Exception e) {
                            if (salePrice.getText().toString().equals(null) || salePrice.getText().toString().equals("")) {
                                salePriceTextInputLayout.setError(getResources().getString(R.string.example_price));
                                salePrice.requestFocus();
                            } else
                                discountPercentTextInputLayout.setError(getResources().getString(R.string.example_quantity));
                        }
                    }
                });
                //endregion discountPercent addTextChangedListener

                //endregion Declare Views in dialog
            }
        });
        //endregion Fab Add ProductHistory

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_detail_products, menu);
        saveItem = (MenuItem) menu.findItem(R.id.action_detail_product_save);
        editItem = (MenuItem) menu.findItem(R.id.action_detail_product_edit);
        saveItem.setVisible(isHidden());
        MenuItem sort = menu.findItem(R.id.sort_button);
        sort.setVisible(false);
    }

    @Override
    public void onResume() {
        super.onResume();

        //set fab animation and show
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_up);
        fab.startAnimation(hyperspaceJumpAnimation);
        fab.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.action_detail_product_edit:
                saveItem.setVisible(isVisible());
                editItem.setVisible(isHidden());

                productName.setEnabled(true);
                productCode.setEnabled(true);
                productUnit.setEnabled(true);
                productDescription.setEnabled(true);

                Utility.setHeightOfListView(mListView);

                //region product name
                Utility.setAsteriskToTextInputLayout(productNameTextInputLayout, getResources().getString(R.string.hint_product_name), true);
                //endregion product name

                productName.requestFocus();

                //region product code
                Utility.setAsteriskToTextInputLayout(productCodeTextInputLayout, getResources().getString(R.string.hint_product_code), true);
                //endregion product code

                //endregion handle asterisk for necessary fields

                break;
            case R.id.action_detail_product_save:
                if (checkValidityWithChangeColorOfHelperTextForProductInsert()) {
                    editItem.setVisible(isVisible());
                    saveItem.setVisible(isHidden());

                    productValues.put(KasebContract.Products.COLUMN_PRODUCT_NAME, productName.getText().toString());
                    productValues.put(KasebContract.Products.COLUMN_PRODUCT_CODE, productCode.getText().toString());
                    productValues.put(KasebContract.Products.COLUMN_UNIT, productUnit.getText().toString());
                    productValues.put(KasebContract.Products.COLUMN_DESCRIPTION, productDescription.getText().toString());

                    getActivity().getContentResolver().update(
                            KasebContract.Products.CONTENT_URI,
                            productValues,
                            KasebContract.Products._ID + " = ? ",
                            new String[]{String.valueOf(productId)}
                    );

                    //just a message to show everything are under control
                    Toast.makeText(getContext(),
                            getContext().getResources().getString(R.string.msg_update_succeed), Toast.LENGTH_LONG).show();

                    productName.setEnabled(false);
                    productCode.setEnabled(false);
                    productUnit.setEnabled(false);
                    productDescription.setEnabled(false);

                    getHelperText();
                }
                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        Log.d(LOG_TAG, "onStart");

        productHistoryValues = new ContentValues();
        productValues = new ContentValues();

        productName = (EditText) getActivity().findViewById(R.id.detail_product_name);
        productCode = (EditText) getActivity().findViewById(R.id.detail_product_code);
        productUnit = (EditText) getActivity().findViewById(R.id.detail_product_unit);
        productDescription = (EditText) getActivity().findViewById(R.id.detail_product_description);
        productStock = (TextView) getActivity().findViewById(R.id.detail_product_stock);

        productNameTextInputLayout = (TextInputLayout) getActivity().findViewById(R.id.text_input_layout_detail_product_name);
        productCodeTextInputLayout = (TextInputLayout) getActivity().findViewById(R.id.text_input_layout_detail_product_code);
        productUnitTextInputLayout = (TextInputLayout) getActivity().findViewById(R.id.text_input_layout_detail_product_unit);
        productDescriptionTextInputLayout = (TextInputLayout) getActivity().findViewById(R.id.text_input_layout_detail_product_description);

        //in this line does not matter post 0l to second parameter of function because not use in this case
        stockProduct = Utility.checkNumberOfProductsForDetailSale(getActivity(),
                0l, "SaleInsert", Long.parseLong(String.valueOf(productId)));

        productStock.setText(String.valueOf(stockProduct));

        mCursor = getContext().getContentResolver().query(
                KasebContract.Products.buildProductsUri(productId),
                new String[]{
                        KasebContract.Products.COLUMN_PRODUCT_NAME,
                        KasebContract.Products.COLUMN_PRODUCT_CODE,
                        KasebContract.Products.COLUMN_DESCRIPTION,
                        KasebContract.Products.COLUMN_UNIT},
                null,
                null,
                null);

        if (mCursor != null)
            if (mCursor.moveToFirst()) {
                productName.setText(mCursor.getString(mCursor.getColumnIndex(KasebContract.Products.COLUMN_PRODUCT_NAME)));
                productCode.setText(mCursor.getString(mCursor.getColumnIndex(KasebContract.Products.COLUMN_PRODUCT_CODE)));
                productUnit.setText(mCursor.getString(mCursor.getColumnIndex(KasebContract.Products.COLUMN_UNIT)));
                productDescription.setText(mCursor.getString(mCursor.getColumnIndex(KasebContract.Products.COLUMN_DESCRIPTION)));
            }

        productName.setEnabled(false);
        productCode.setEnabled(false);
        productUnit.setEnabled(false);
        productDescription.setEnabled(false);
        fab.hide();

        super.onStart();
        updateList();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(FRAGMENT_PRODUCT_HISTORY_LOADER, null, this);
    }

    private void updateList() {
        getLoaderManager().restartLoader(FRAGMENT_PRODUCT_HISTORY_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader");

        return new CursorLoader(
                getActivity(),
                KasebContract.ProductHistory.aProductHistory(productId),
                mProjection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(LOG_TAG, "onLoadFinished");
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(LOG_TAG, "onLoadReset");
        mAdapter.swapCursor(null);
    }

    private void getHelperText() {

        productNameTextInputLayout.setError(null);
        productCodeTextInputLayout.setError(null);
        try {
            salePriceTextInputLayout.setError(null);
            quantityTextInputLayout.setError(null);
            buyPriceTextInputLayout.setError(null);
            buyDateTextInputLayout.setError(null);
            discountAmountTextInputLayout.setError(null);
            discountPercentTextInputLayout.setError(null);
        } catch (Exception e) {
        }
    }

    // this method check the validation and correct entries. its check fill first and then check the validation rules.
    private boolean checkValidityWithChangeColorOfHelperTextForProductInsert() {

        if (!Utility.checkForValidityForEditTextNullOrEmptyAndItterative(
                getActivity(), productName, productNameTextInputLayout, KasebContract.Products.CONTENT_URI,
                KasebContract.Products.COLUMN_PRODUCT_NAME + " = ? and " + KasebContract.Products._ID + " != ? ",
                KasebContract.Products._ID, new String[]{productName.getText().toString(), String.valueOf(productId)})) {
            productNameTextInputLayout.setError(String.format("%s %s",
                    getResources().getString(R.string.example_product_name),
                    getResources().getString(R.string.non_repetitive)));
            return false;
        }

        if (!Utility.checkForValidityForEditTextNullOrEmpty(getActivity(), productCode)) {
            productCodeTextInputLayout.setError(getResources().getString(R.string.example_product_code));
            productCode.setSelectAllOnFocus(true);
            productCode.selectAll();
            productCode.requestFocus();
            return false;
        } else
            productCodeTextInputLayout.setError(null);
        return true;
    }

    // this method check the validation and correct entries. its check fill first and then check the validation rules.
    private boolean checkValidityWithChangeColorOfHelperTextForProductHistoryInsert() throws ParseException {

        if (!Utility.checkForValidityForEditTextNullOrEmpty(getActivity(), salePrice)) {
            salePriceTextInputLayout.setError(getResources().getString(R.string.example_price));
            salePrice.setSelectAllOnFocus(true);
            salePrice.selectAll();
            salePrice.requestFocus();
            return false;
        } else
            salePriceTextInputLayout.setError(null);

        if (!Utility.checkForValidityForEditTextNullOrEmpty(getActivity(), quantity)) {
            quantityTextInputLayout.setError(getResources().getString(R.string.example_quantity));
            quantity.setSelectAllOnFocus(true);
            quantity.selectAll();
            quantity.requestFocus();
            return false;
        } else
            quantityTextInputLayout.setError(null);

        if (!Utility.checkForValidityForEditTextNullOrEmpty(getActivity(), buyPrice)) {
            buyPriceTextInputLayout.setError(String.format("%s %s",
                    getResources().getString(R.string.example_price),
                    getResources().getString(R.string.amount_not_less_than_sale_price)));
            buyPrice.setSelectAllOnFocus(true);
            buyPrice.selectAll();
            buyPrice.requestFocus();
            return false;
        } else {
            Float mBuyPrice = Utility.createFloatNumberWithString(getActivity(), buyPrice.getText().toString());
            Float mSalePrice = Utility.createFloatNumberWithString(getActivity(), salePrice.getText().toString());

            if (mBuyPrice > mSalePrice) {
                buyPriceTextInputLayout.setError(String.format("%s %s",
                        getResources().getString(R.string.example_price),
                        getResources().getString(R.string.amount_not_less_than_sale_price)));
                buyPrice.setSelectAllOnFocus(true);
                buyPrice.selectAll();
                buyPrice.requestFocus();
                return false;
            } else
                buyPriceTextInputLayout.setError(null);
        }

        if (!Utility.checkForValidityForEditTextDate(getActivity(), buyDate)) {
            buyDateTextInputLayout.setError(getResources().getString(R.string.example_date));
            buyDate.setSelectAllOnFocus(true);
            buyDate.selectAll();
            buyDate.requestFocus();
            return false;
        } else
            buyDateTextInputLayout.setError(null);

        return true;
    }
}