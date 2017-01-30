package mjkarbasian.moshtarimadar.Products;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;

import mjkarbasian.moshtarimadar.Costs.CostInsert;
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;
import mjkarbasian.moshtarimadar.Sales.Sales;


/**
 * Created by Unique on 10/25/2016.
 */
public class ProductInsert extends Fragment {

    //region declare values
    private static final String LOG_TAG = CostInsert.class.getSimpleName();
    View rootView;
    Boolean firstCome = false;

    AlertDialog.Builder builderTour;
    AlertDialog dialogViewTour;

    SharedPreferences kasebSharedPreferences;
    SharedPreferences.Editor editor;

    ContentValues productValues = new ContentValues();
    ContentValues productHistoryValues = new ContentValues();

    EditText productName;
    EditText productCode;
    EditText unit;
    EditText productDescription;
    EditText buyPrice;
    EditText quantity;
    EditText salePrice;
    EditText buyDate;
    EditText discountAmount;
    EditText discountPercent;

    TextInputLayout productNameTextInputLayout;
    TextInputLayout salePriceTextInputLayout;
    TextInputLayout quantityTextInputLayout;
    TextInputLayout buyPriceTextInputLayout;
    TextInputLayout buyDateTextInputLayout;
    TextInputLayout productCodeTextInputLayout;
    TextInputLayout productUnitTextInputLayout;
    TextInputLayout productDescriptionTextInputLayout;
    TextInputLayout discountAmountTextInputLayout;
    TextInputLayout discountPercentTextInputLayout;

    private Uri insertUri;
    //endregion declare values

    public ProductInsert() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product_insert, container, false);

        productName = (EditText) rootView.findViewById(R.id.input_product_name);
        productCode = (EditText) rootView.findViewById(R.id.input_product_code);
        unit = (EditText) rootView.findViewById(R.id.input_product_unit);
        productDescription = (EditText) rootView.findViewById(R.id.input_product_description);
        buyPrice = (EditText) rootView.findViewById(R.id.input_buy_price);
        quantity = (EditText) rootView.findViewById(R.id.input_quantity);
        salePrice = (EditText) rootView.findViewById(R.id.input_sale_price);
        buyDate = (EditText) rootView.findViewById(R.id.dialog_add_payment_for_sale_input_due_date);
        discountAmount = (EditText) rootView.findViewById(R.id.input_discount_amount);
        discountPercent = (EditText) rootView.findViewById(R.id.input_discount_percent);

        productNameTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_product_name);
        salePriceTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_sale_price);
        quantityTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_quantity);
        buyPriceTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_buy_price);
        buyDateTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_buy_date);
        productCodeTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_product_code);
        productUnitTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_product_unit);
        productDescriptionTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_product_description);
        discountAmountTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_discount_amount);
        discountPercentTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_discount_percent);

        //region handle sharepreference
        kasebSharedPreferences = getActivity().getSharedPreferences(getString(R.string.kasebPreference), getActivity().MODE_PRIVATE);
        editor = kasebSharedPreferences.edit();
        //endregion handle sharepreference

        //region create alertdialog tour
        builderTour = new AlertDialog.Builder(getActivity())
                .setNegativeButton(R.string.back_tour, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).setPositiveButton(R.string.next_tour, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).setNeutralButton(R.string.cancel_tour, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).setTitle(R.string.title_product_insert);

        dialogViewTour = builderTour.create();
        //endregion create alertdialog tour

        //region handle asterisk for necessary fields

        //region product name
        Utility.setAsteriskToTextInputLayout(productNameTextInputLayout, getResources().getString(R.string.hint_product_name), false);
        //endregion product name

        productName.requestFocus();

        //region product code
        Utility.setAsteriskToTextInputLayout(productCodeTextInputLayout, getResources().getString(R.string.hint_product_code), true);
        //endregion product code

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

        try {
            if (kasebSharedPreferences.getBoolean("getStarted", false)) {

                dialogViewTour.setMessage(getResources().getString(R.string.tour_text_product_insert));
                dialogViewTour.show();

                dialogViewTour.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialogViewTour.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), Sales.class);
                        getActivity().finish();
                        startActivity(intent);
                        Utility.setActivityTransition(getActivity());

                        dialogViewTour.dismiss();
                    }
                });

                dialogViewTour.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //region back tour
                        backToLastPage();

                        dialogViewTour.dismiss();
                        //endregion back tour
                    }
                });

                dialogViewTour.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //region end tour
                        editor.putBoolean("getStarted", false);
                        editor.apply();

                        dialogViewTour.dismiss();
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

        //region discountAmount addTextChangedListener
        discountAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (firstCome) {
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
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (firstCome) {
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
                if (firstCome) {
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
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (firstCome) {
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
            }
        });
        //endregion discountPercent addTextChangedListener

        return rootView;
    }

    @Override
    public void onResume() {
        Utility.clearForm((ViewGroup) rootView);
        getHelperText();
        productName.requestFocus();
        productCode.setText(Utility.preInsertProductCode(getActivity()));
        buyDate.setText(Utility.preInsertDate(getActivity()));

        firstCome = true;
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.sort_button);
        menu.removeItem(R.id.search_button);
        inflater.inflate(R.menu.fragments_for_insert, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_inputs: {
                try {
                    if (checkValidityWithChangeColorOfHelperText()) {

                        productValues.put(KasebContract.Products.COLUMN_PRODUCT_NAME, productName.getText().toString());
                        productValues.put(KasebContract.Products.COLUMN_PRODUCT_CODE, productCode.getText().toString());
                        productValues.put(KasebContract.Products.COLUMN_UNIT, unit.getText().toString());
                        productValues.put(KasebContract.Products.COLUMN_DESCRIPTION, productDescription.getText().toString());
                        insertUri = getActivity().getContentResolver().insert(
                                KasebContract.Products.CONTENT_URI,
                                productValues
                        );

                        productHistoryValues.put(KasebContract.ProductHistory.COLUMN_COST,
                                Utility.convertFarsiNumbersToDecimal(buyPrice.getText().toString()));
                        productHistoryValues.put(KasebContract.ProductHistory.COLUMN_QUANTITY,
                                Utility.convertFarsiNumbersToDecimal(quantity.getText().toString()));
                        productHistoryValues.put(KasebContract.ProductHistory.COLUMN_SALE_PRICE,
                                Utility.convertFarsiNumbersToDecimal(salePrice.getText().toString()));
                        productHistoryValues.put(KasebContract.ProductHistory.COLUMN_DATE, buyDate.getText().toString());
                        productHistoryValues.put(KasebContract.ProductHistory.COLUMN_PRODUCT_ID, insertUri.getLastPathSegment());

                        insertUri = getActivity().getContentResolver().insert(
                                KasebContract.ProductHistory.CONTENT_URI,
                                productHistoryValues
                        );

                        //region disabling edit
                        productName.setEnabled(false);
                        productCode.setEnabled(false);
                        unit.setEnabled(false);
                        productDescription.setEnabled(false);
                        buyPrice.setEnabled(false);
                        quantity.setEnabled(false);
                        salePrice.setEnabled(false);
                        buyDate.setEnabled(false);

                        //just a message to show everything are under control
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_insert_succeed),
                                Toast.LENGTH_LONG).show();

                        getHelperText();
                        backToLastPage();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // this method back to the activity view. this must be a utility method.
    private void backToLastPage() {
        Utility.clearForm((ViewGroup) rootView);
        productValues = new ContentValues();
        productHistoryValues = new ContentValues();
        getFragmentManager().popBackStackImmediate();
    }

    private void getHelperText() {

        productNameTextInputLayout.setError(null);
        productCodeTextInputLayout.setError(null);
        salePriceTextInputLayout.setError(null);
        quantityTextInputLayout.setError(null);
        buyPriceTextInputLayout.setError(null);
        buyDateTextInputLayout.setError(null);
        discountAmountTextInputLayout.setError(null);
        discountPercentTextInputLayout.setError(null);
    }

    // this method check the validation and correct entries. its check fill first and then check the validation rules.
    private boolean checkValidityWithChangeColorOfHelperText() throws ParseException {

        if (!Utility.checkForValidityForEditTextNullOrEmptyAndItterative(
                getActivity(), productName, productNameTextInputLayout, KasebContract.Products.CONTENT_URI,
                KasebContract.Products.COLUMN_PRODUCT_NAME + " = ? ",
                KasebContract.Products._ID, new String[]{productName.getText().toString()})) {
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