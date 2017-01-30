package mjkarbasian.moshtarimadar.Costs;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import mjkarbasian.moshtarimadar.Adapters.TypesSettingAdapter;
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by family on 10/19/2016.
 */
public class CostInsert extends Fragment {

    //region declare values
    private static final String LOG_TAG = CostInsert.class.getSimpleName();
    ContentValues costValues = new ContentValues();
    View rootView;
    Spinner costType;

    EditText costName;
    EditText costCode;
    EditText costAmount;
    EditText costDate;
    EditText costDescription;

    TextInputLayout costNameTextInputLayout;
    TextInputLayout costAmountTextInputLayout;
    TextInputLayout costDateTextInputLayout;
    TextInputLayout costCodeTextInputLayout;

    private Uri insertUri;
    //endregion declare values

    public CostInsert() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cost_insert, container, false);
        costType = (Spinner) rootView.findViewById(R.id.input_cost_type_spinner);
        costName = (EditText) rootView.findViewById(R.id.input_cost_name);
        costCode = (EditText) rootView.findViewById(R.id.input_cost_code);
        costCode.setText(Utility.preInsertCostCode(getActivity()));
        costAmount = (EditText) rootView.findViewById(R.id.input_cost_amount);
        costDate = (EditText) rootView.findViewById(R.id.input_cost_date);
        costDate.setText(Utility.preInsertDate(getActivity()));
        costDescription = (EditText) rootView.findViewById(R.id.input_cost_description);

        costNameTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_cost_name);
        costCodeTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_cost_code);
        costAmountTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_cost_amount);
        costDateTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_cost_date);

        Cursor cursor = getContext().getContentResolver().query(KasebContract.CostTypes.CONTENT_URI
                , null, null, null, null);

        int[] toViews = {
                android.R.id.text1
        };
        String[] fromColumns = {
                KasebContract.CostTypes.COLUMN_COST_TYPE_POINTER
        };

        TypesSettingAdapter cursorAdapter = new TypesSettingAdapter(getActivity(), cursor, 0, KasebContract.CostTypes.COLUMN_COST_TYPE_POINTER);
        costType.setAdapter(cursorAdapter);

        //region handle asterisk for necessary fields

        //region cost name
        Utility.setAsteriskToTextInputLayout(costNameTextInputLayout, getResources().getString(R.string.hint_cost_name), false);
        //endregion cost name

        costName.requestFocus();

        //region cost code
        Utility.setAsteriskToTextInputLayout(costCodeTextInputLayout, getResources().getString(R.string.hint_cost_code), true);
        //endregion cost code

        //region cost amount
        Utility.setAsteriskToTextInputLayout(costAmountTextInputLayout, getResources().getString(R.string.hint_cost_amount), false);
        //endregion cost amount

        //region cost date
        Utility.setAsteriskToTextInputLayout(costDateTextInputLayout, getResources().getString(R.string.hint_date), true);
        //endregion cost date

        //endregion handle asterisk for necessary fields
        return rootView;
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
                if (checkValidityWithChangeColorOfHelperText()) {
                    costValues.put(KasebContract.Costs.COLUMN_COST_NAME, costName.getText().toString());
                    costValues.put(KasebContract.Costs.COLUMN_COST_CODE, costCode.getText().toString());
                    costValues.put(KasebContract.Costs.COLUMN_AMOUNT,
                            Utility.convertFarsiNumbersToDecimal(costAmount.getText().toString()));
                    costValues.put(KasebContract.Costs.COLUMN_DATE, costDate.getText().toString());
                    costValues.put(KasebContract.Costs.COLUMN_DESCRIPTION, costDescription.getText().toString());
                    costValues.put(KasebContract.Costs.COLUMN_COST_TYPE_ID, costType.getSelectedItemPosition() + 1);
                    insertUri = getActivity().getContentResolver().insert(
                            KasebContract.Costs.CONTENT_URI,
                            costValues
                    );

                    //just a message to show everything are under control
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_insert_succeed),
                            Toast.LENGTH_LONG).show();

                    getHelperText();
                    backToLastPage();
                }
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    // this method back to the activity view. this must be a utility method.
    private void backToLastPage() {
        Utility.clearForm((ViewGroup) rootView);
        costValues = new ContentValues();
        getFragmentManager().popBackStackImmediate();
    }

    private void getHelperText() {

        costNameTextInputLayout.setError(null);
        costCodeTextInputLayout.setError(null);
        costAmountTextInputLayout.setError(null);
        costDateTextInputLayout.setError(null);
    }

    // this method check the validation and correct entries. its check fill first and then check the validation rules.
    private boolean checkValidityWithChangeColorOfHelperText() {
        if (!Utility.checkForValidityForEditTextNullOrEmpty(getActivity(), costName)) {
            costNameTextInputLayout.setError(getResources().getString(R.string.example_cost_name));
            costName.setSelectAllOnFocus(true);
            costName.selectAll();
            costName.requestFocus();
            return false;
        } else
            costNameTextInputLayout.setError(null);

        if (!Utility.checkForValidityForEditTextNullOrEmpty(getActivity(), costCode)) {
            costCodeTextInputLayout.setError(getResources().getString(R.string.example_cost_code));
            costCode.setSelectAllOnFocus(true);
            costCode.selectAll();
            costCode.requestFocus();
            return false;
        } else
            costCodeTextInputLayout.setError(null);

        if (!Utility.checkForValidityForEditTextNullOrEmpty(getActivity(), costAmount)) {
            costAmountTextInputLayout.setError(getResources().getString(R.string.example_price));
            costAmount.setSelectAllOnFocus(true);
            costAmount.selectAll();
            costAmount.requestFocus();
            return false;
        } else
            costAmountTextInputLayout.setError(null);

        if (!Utility.checkForValidityForEditTextDate(getActivity(), costDate)) {
            costDateTextInputLayout.setError(getResources().getString(R.string.example_date));
            costDate.setSelectAllOnFocus(true);
            costDate.selectAll();
            costDate.requestFocus();
            return false;
        } else
            costDateTextInputLayout.setError(null);

        return true;
    }
}

