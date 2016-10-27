package mjkarbasian.moshtarimadar;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import mjkarbasian.moshtarimadar.Data.KasebContract;

/**
 * Created by family on 10/19/2016.
 */
public class CostInsert extends Fragment {
    private static final String LOG_TAG = CostInsert.class.getSimpleName() ;
    EditText costName;
    EditText costCode;
    EditText costAmount;
    Spinner costType;
    EditText costDate;
    EditText costDescription;
    ContentValues costValues = new ContentValues();
    private Uri insertUri;

    public CostInsert() {
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_cost_insert, container, false);

         costType = (Spinner) rootView.findViewById(R.id.input_cost_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cost_types, android.R.layout.simple_spinner_item);
        costName = (EditText)rootView.findViewById(R.id.input_cost_name);
        costCode = (EditText)rootView.findViewById(R.id.input_cost_code);
        costAmount = (EditText)rootView.findViewById(R.id.input_cost_amount);
        costDate = (EditText)rootView.findViewById(R.id.input_cost_date);
        costDescription = (EditText)rootView.findViewById(R.id.input_cost_description);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        costType.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.sort_button);
        menu.removeItem(R.id.search_button);
        inflater.inflate(R.menu.fragments_for_insert, menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_inputs:{
                costValues.put(KasebContract.Costs.COLUMN_COST_NAME,costName.getText().toString());
                costValues.put(KasebContract.Costs.COLUMN_COST_CODE,costCode.getText().toString());
                costValues.put(KasebContract.Costs.COLUMN_AMOUNT,costAmount.getText().toString());
                costValues.put(KasebContract.Costs.COLUMN_DATE,costDate.getText().toString());
                costValues.put(KasebContract.Costs.COLUMN_DESCRIPTION,costDescription.getText().toString());
                costValues.put(KasebContract.Costs.COLUMN_COST_TYPE_ID,costType.getSelectedItemPosition());
               insertUri =getActivity().getContentResolver().insert(
                       KasebContract.Costs.CONTENT_URI,
                        costValues
                );
                //region disabling edit
                costName.setEnabled(false);
                costCode.setEnabled(false);
                costAmount.setEnabled(false);
                costDescription.setEnabled(false);
                costType.setEnabled(false);
                costDate.setEnabled(false);
                //just a message to show everything are under control
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_insert_succeed),
                        Toast.LENGTH_LONG).show();
                
                checkForValidity();
                backToLastPage();

                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    // this method check the validation and correct entries. its check fill first and then check the validation rules.
    private void checkForValidity() {
    }

    // this method back to the activity view. this must be a utility method.
    private void backToLastPage() {
        getFragmentManager().popBackStackImmediate();
    }
}
