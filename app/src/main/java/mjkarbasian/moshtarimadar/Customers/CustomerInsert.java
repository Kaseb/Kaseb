package mjkarbasian.moshtarimadar.Customers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;

import mjkarbasian.moshtarimadar.Adapters.TypesSettingAdapter;
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Helpers.GalleryUtil;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.Products.Products;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by Unique on 10/21/2016.
 */
public class CustomerInsert extends Fragment {

    //region declare values
    private static final int GALLERY_ACTIVITY_CODE = 200;
    private static final int RESULT_CROP = 400;

    Spinner stateType;
    View rootView;
    View tourView;
    ContentValues customerValues = new ContentValues();
    ImageView mCustomerAvatar;
    Bitmap photo;

    SharedPreferences kasebSharedPreferences;
    SharedPreferences.Editor editor;

    AlertDialog.Builder builderTour;
    AlertDialog dialogViewTour;

    EditText firstName;
    EditText lastName;
    EditText birthDay;
    EditText phoneMobile;
    EditText customerDescription;
    EditText email;
    EditText phoneWork;
    EditText phoneHome;
    EditText phoneOther;
    EditText phoneFax;
    EditText addressCountry;
    EditText addressCity;
    EditText addressStreet;
    EditText addressPostalCode;

    TextInputLayout firstNameTextInputLayout;
    TextInputLayout lastNameTextInputLayout;
    TextInputLayout phoneMobileTextInputLayout;
    TextInputLayout birthDayTextInputLayout;
    TextInputLayout customerDescriptionTextInputLayout;
    TextInputLayout emailTextInputLayout;
    TextInputLayout phoneWorkTextInputLayout;
    TextInputLayout phoneHomeTextInputLayout;
    TextInputLayout phoneFaxTextInputLayout;
    TextInputLayout phoneOtherTextInputLayout;
    TextInputLayout addressCountryTextInputLayout;
    TextInputLayout addressCityTextInputLayout;
    TextInputLayout addressStreetTextInputLayout;
    TextInputLayout addressPostalCodeTextInputLayout;
    private Uri insertUri;
    //endregion declare values

    public CustomerInsert() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_customer_insert, container, false);

        mCustomerAvatar = (ImageView) rootView.findViewById(R.id.fragment_customer_insert_picture);

        mCustomerAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic_selector_on_customer_insert(mCustomerAvatar);
            }
        });

        //region handle sharepreference
        kasebSharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.kasebPreference), getActivity().MODE_PRIVATE);
        editor = kasebSharedPreferences.edit();
        //endregion handle sharepreference

        //region create alertdialog tour
        builderTour = new AlertDialog.Builder(getActivity())
                .setNegativeButton(R.string.back_tour, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .setPositiveButton(R.string.next_tour, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .setNeutralButton(R.string.cancel_tour, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .setTitle(R.string.title_customer_insert);

        dialogViewTour = builderTour.create();
        //endregion create alertdialog tour

        stateType = (Spinner) rootView.findViewById(R.id.input_state_type_spinner);
        firstName = (EditText) rootView.findViewById(R.id.input_first_name);
        lastName = (EditText) rootView.findViewById(R.id.input_last_name);
        birthDay = (EditText) rootView.findViewById(R.id.input_birth_day);
        phoneMobile = (EditText) rootView.findViewById(R.id.input_phone_mobile);
        customerDescription = (EditText) rootView.findViewById(R.id.input_description);
        email = (EditText) rootView.findViewById(R.id.input_email);
        phoneWork = (EditText) rootView.findViewById(R.id.input_phone_work);
        phoneHome = (EditText) rootView.findViewById(R.id.input_phone_home);
        phoneOther = (EditText) rootView.findViewById(R.id.input_phone_other);
        phoneFax = (EditText) rootView.findViewById(R.id.input_phone_fax);
        addressCountry = (EditText) rootView.findViewById(R.id.input_address_country);
        addressCity = (EditText) rootView.findViewById(R.id.input_address_city);
        addressStreet = (EditText) rootView.findViewById(R.id.input_address_street);
        addressPostalCode = (EditText) rootView.findViewById(R.id.input_address_postal_code);

        firstNameTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_first_name);
        lastNameTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_last_name);
        phoneMobileTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_phone_mobile);
        birthDayTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_birth_day);
        customerDescriptionTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_description);
        emailTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_email);
        phoneWorkTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_phone_work);
        phoneHomeTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_phone_home);
        phoneOtherTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_phone_other);
        phoneFaxTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_phone_fax);
        addressCountryTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_address_country);
        addressCityTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_address_city);
        addressStreetTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_address_street);
        addressPostalCodeTextInputLayout = (TextInputLayout) rootView.findViewById(R.id.text_input_layout_input_address_postal_code);

        //region handle asterisk for necessary fields

        //region first name
        Utility.setAsteriskToTextInputLayout(firstNameTextInputLayout, getResources().getString(R.string.hint_first_name), false);
        //endregion first name

        firstName.requestFocus();

        //region last name
        Utility.setAsteriskToTextInputLayout(lastNameTextInputLayout, getResources().getString(R.string.hint_last_name), false);
        //endregion last name

        //region mobile number
        Utility.setAsteriskToTextInputLayout(phoneMobileTextInputLayout, getResources().getString(R.string.hint_mobile_number), false);
        //endregion mobile number

        //endregion handle asterisk for necessary fields

        Cursor cursor = getContext().getContentResolver().query(KasebContract.State.CONTENT_URI
                , null, null, null, KasebContract.State._ID + " DESC");

        int[] toViews = {
                android.R.id.text1
        };
        String[] fromColumns = {
                KasebContract.State.COLUMN_STATE_POINTER
        };

        TypesSettingAdapter cursorAdapter = new TypesSettingAdapter(
                getActivity(), cursor, 0, KasebContract.State.COLUMN_STATE_POINTER);
        stateType.setAdapter(cursorAdapter);

        try {
            if (kasebSharedPreferences.getBoolean("getStarted", false)) {

                dialogViewTour.setMessage(getResources().getString(R.string.tour_text_customer_insert));
                dialogViewTour.show();

                dialogViewTour.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialogViewTour.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), Products.class);
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

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.sort_button);
        menu.removeItem(R.id.search_button);
        tourView = (View) menu.findItem(R.id.save_inputs);
        inflater.inflate(R.menu.fragments_for_insert, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_inputs: {

                if (checkValidityWithChangeColorOfHelperText()) {
                    customerValues.put(KasebContract.Customers.COLUMN_FIRST_NAME, firstName.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_LAST_NAME, lastName.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_BIRTHDAY, birthDay.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_PHONE_MOBILE, phoneMobile.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_PHONE_WORK, phoneWork.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_PHONE_HOME, phoneHome.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_PHONE_FAX, phoneFax.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_PHONE_OTHER, phoneOther.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_DESCRIPTION, customerDescription.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_EMAIL, email.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_ADDRESS_COUNTRY, addressCountry.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_ADDRESS_CITY, addressCity.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_ADDRESS_STREET, addressStreet.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_ADDRESS_POSTAL_CODE, addressPostalCode.getText().toString());

                    customerValues.put(KasebContract.Customers.COLUMN_STATE_ID, stateType.getCount() - stateType.getSelectedItemPosition());
                    insertUri = getActivity().getContentResolver().insert(
                            KasebContract.Customers.CONTENT_URI,
                            customerValues
                    );

                    //region disabling edit
                    firstName.setEnabled(false);
                    lastName.setEnabled(false);
                    birthDay.setEnabled(false);
                    phoneMobile.setEnabled(false);
                    customerDescription.setEnabled(false);
                    email.setEnabled(false);
                    phoneWork.setEnabled(false);
                    phoneHome.setEnabled(false);
                    phoneOther.setEnabled(false);
                    phoneFax.setEnabled(false);
                    addressCountry.setEnabled(false);
                    addressCity.setEnabled(false);
                    addressStreet.setEnabled(false);
                    addressPostalCode.setEnabled(false);
                    stateType.setEnabled(false);

                    //just a message to show everything are under control
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_insert_succeed),
                            Toast.LENGTH_LONG).show();

                    backToLastPage();
                }

                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void backToLastPage() {
        Utility.clearForm((ViewGroup) rootView);
        getHelperText();
        customerValues = new ContentValues();
        getFragmentManager().popBackStackImmediate();
    }

    public void pic_selector_on_customer_insert(View view) {
        mCustomerAvatar = (ImageView) view;
        Intent gallery_Intent = new Intent(getContext(), GalleryUtil.class);
        startActivityForResult(gallery_Intent, GALLERY_ACTIVITY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String picturePath = data.getStringExtra("picturePath");
                //perform Crop on the Image Selected from Gallery
                performCrop(picturePath);
            }
        }
        if (requestCode == RESULT_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getExtras() != null) {
                    photo = data.getExtras().getParcelable("data");
                    mCustomerAvatar.setImageBitmap(photo);

                    firstName.setSelectAllOnFocus(true);
                    firstName.selectAll();
                    firstName.requestFocus();

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                    byte[] imagegBytes = byteArrayOutputStream.toByteArray();
                    customerValues.put(KasebContract.Customers.COLUMN_CUSTOMER_PICTURE, imagegBytes);
                } else if (data.getData() != null) {
                    Uri picUri = data.getData();
                    BufferedInputStream bufferInputStream = null;
                    try {
                        URLConnection connection = new URL(picUri.toString()).openConnection();
                        connection.connect();
                        bufferInputStream = new BufferedInputStream(connection.getInputStream(), 8192);
                        photo = BitmapFactory.decodeStream(bufferInputStream);

                        mCustomerAvatar.setImageBitmap(photo);

                        firstName.setSelectAllOnFocus(true);
                        firstName.selectAll();
                        firstName.requestFocus();

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                        byte[] imagegBytes = byteArrayOutputStream.toByteArray();
                        customerValues.put(KasebContract.Customers.COLUMN_CUSTOMER_PICTURE, imagegBytes);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(getActivity(), R.string.problem_in_crop_image, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            Toast toast = Toast.makeText(getActivity(), R.string.does_not_support_crop_action, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void getHelperText() {

        firstNameTextInputLayout.setError(null);
        lastNameTextInputLayout.setError(null);
        phoneMobileTextInputLayout.setError(null);
        birthDayTextInputLayout.setError(null);
        emailTextInputLayout.setError(null);
    }

    // this method check the validation and correct entries. its check fill first and then check the validation rules.
    private boolean checkValidityWithChangeColorOfHelperText() {

        if (!Utility.checkForValidityForEditTextNullOrEmpty(getActivity(), firstName)) {
            firstNameTextInputLayout.setError(getResources().getString(R.string.example_first_name));
            firstName.setSelectAllOnFocus(true);
            firstName.selectAll();
            firstName.requestFocus();
            return false;
        } else
            firstNameTextInputLayout.setError(null);

        if (!Utility.checkForValidityForEditTextNullOrEmpty(getActivity(), lastName)) {
            lastNameTextInputLayout.setError(getResources().getString(R.string.example_last_name));
            lastName.setSelectAllOnFocus(true);
            lastName.selectAll();
            lastName.requestFocus();
            return false;
        } else
            lastNameTextInputLayout.setError(null);

        if (!Utility.checkForValidityForEditTextNullOrEmptyAndItterative(
                getActivity(), phoneMobile, phoneMobileTextInputLayout, KasebContract.Customers.CONTENT_URI,
                KasebContract.Customers.COLUMN_PHONE_MOBILE + " = ? ",
                KasebContract.Customers._ID, new String[]{phoneMobile.getText().toString()})) {
            phoneMobileTextInputLayout.setError(String.format("%s %s",
                    getResources().getString(R.string.example_mobile_number),
                    getResources().getString(R.string.non_repetitive)));
            return false;
        }

        if (!birthDay.getText().toString().equals("") && !birthDay.getText().toString().equals(null) &&
                !Utility.checkForValidityForEditTextDate(getActivity(), birthDay)) {
            birthDayTextInputLayout.setError(getResources().getString(R.string.example_date));
            birthDay.setSelectAllOnFocus(true);
            birthDay.selectAll();
            birthDay.requestFocus();
            return false;
        } else
            birthDayTextInputLayout.setError(null);

        if (!email.getText().toString().equals("") && !email.getText().toString().equals(null) &&
                !Utility.validateEmail(email.getText().toString())) {

            emailTextInputLayout.setError(getResources().getString(R.string.example_email));
            email.setSelectAllOnFocus(true);
            email.selectAll();
            email.requestFocus();
            return false;
        } else
            emailTextInputLayout.setError(null);

        return true;
    }
}