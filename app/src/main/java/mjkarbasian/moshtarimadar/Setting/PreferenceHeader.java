package mjkarbasian.moshtarimadar.Setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import mjkarbasian.moshtarimadar.Adapters.HeaderAdapter;
import mjkarbasian.moshtarimadar.Customers.Customers;
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.Others.DrawerActivity;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by family on 11/3/2016.
 */
public class PreferenceHeader extends Fragment {

    //region declare values
    private static final int RESULT_PICK_CONTACT = 1;
    private static final int PICK_FROM_GALLERY = 2;
    private static final String LOG_TAG = PreferenceHeader.class.getSimpleName();

    ListView mListView;
    HeaderAdapter headerAdaper;
    ArrayList<Integer> headerIcons = new ArrayList<>();
    ArrayList<String> headerTitle = new ArrayList<>();
    ArrayList<String> headerSummary = new ArrayList<>();

    AlertDialog.Builder builder;
    AlertDialog dialogView;

    ImageView mCustomerAvatar;
    EditText firstName;
    EditText lastName;
    EditText birthDay;
    EditText phoneMobile;
    EditText customerDescription;
    EditText email;
    EditText phoneWork;
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
    TextInputLayout phoneFaxTextInputLayout;
    TextInputLayout phoneOtherTextInputLayout;
    TextInputLayout addressCountryTextInputLayout;
    TextInputLayout addressCityTextInputLayout;
    TextInputLayout addressStreetTextInputLayout;
    TextInputLayout addressPostalCodeTextInputLayout;

    SharedPreferences kasebSharedPreferences;
    SharedPreferences.Editor editor;
    //endregion declare values

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        headerIcons = initHeaderIcon();
        headerTitle = initHeaderTitle();
        headerSummary = initHeaderSummary();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //region handle sharepreference
        kasebSharedPreferences = getContext().getSharedPreferences(getResources().getString(R.string.kasebPreference), getContext().MODE_PRIVATE);
        editor = kasebSharedPreferences.edit();
        //endregion handle sharepreference

        View rootView = inflater.inflate(R.layout.fragment_setting_types, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list_view_setting_types);
        headerAdaper = new HeaderAdapter(getActivity(), headerIcons, headerTitle, headerSummary);
        mListView.setAdapter(headerAdaper);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //create an instance of typeSetting Fragment and its passing value bundle
                Bundle columnName = new Bundle();
                TypeSettingFragment typeFragment = new TypeSettingFragment();
                //filtering typeSetting from Headers By an if
                if (position < 4) {
                    switch (position) {
                        case 0: {
                            // this is cost types setting so we call typeSettingFragment by passing it Cost type column Name
                            columnName.putString("columnName", KasebContract.CostTypes.COLUMN_COST_TYPE_POINTER);
                            break;
                        }
                        case 1: {
                            columnName.putString("columnName", KasebContract.TaxTypes.COLUMN_TAX_TYPE_POINTER);
                            break;
                        }
                        case 2: {
                            columnName.putString("columnName", KasebContract.PaymentMethods.COLUMN_PAYMENT_METHOD_POINTER);
                            break;
                        }
                        case 3: {
                            columnName.putString("columnName", KasebContract.State.COLUMN_STATE_POINTER);
                            break;
                        }

                        default:
                    }
                    typeFragment.setArguments(columnName);
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container, typeFragment, "typeFragments");
                    fragmentTransaction.addToBackStack(null);
                    int callBackStack = fragmentTransaction.commit();
                } else switch (position) {
                    case 4: {
                        new AlertDialog.Builder(getActivity())
                                .setTitle(getActivity().getResources().getString(R.string.pref_header_backup))
                                .setMessage(getActivity().getResources().getString(R.string.dialog_select_backup_restore))
                                .setPositiveButton(getActivity().getResources().getString(R.string.dialog_select_backup), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            doBackup();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                                .setNegativeButton(getActivity().getResources().getString(R.string.dialog_select_restore), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        doRestore();
                                    }
                                })
                                .setNeutralButton(getActivity().getResources().getString(R.string.dialog_select_cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();

                        break;
                    }
                    case 5: {
                        Intent contactPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        contactPickerIntent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
                        break;
                    }
                    case 6: {

                        //region create alert dialog
                        builder = new android.app.AlertDialog.Builder(getActivity())
                                .setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_edit_profile_of_kaseb, null))
                                .setNegativeButton(R.string.discard_button, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                    }
                                }).setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                    }
                                })
                                .setTitle(R.string.title_set_profile);

                        dialogView = builder.create();
                        dialogView.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        dialogView.show();

                        try {
                            String mTest = kasebSharedPreferences.getString("numberOfEntranceToProfile", null);
                            editor.putString("numberOfEntranceToProfile",
                                    String.valueOf(Double.valueOf(kasebSharedPreferences.getString("numberOfEntranceToProfile", null)) + 1));
                            editor.apply();
                        } catch (Exception e) {
                            editor.putString("numberOfEntranceToProfile", "1");
                            editor.apply();
                        }

                        dialogView.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Boolean wantToCloseDialog = false;

                                if (checkValidityWithChangeColorOfHelperText()) {

                                    //region save info of kaseb profile
                                    editor.putString("firstName", firstName.getText().toString());
                                    editor.putString("lastName", lastName.getText().toString());
                                    editor.putString("birthDay", birthDay.getText().toString());
                                    editor.putString("phoneMobile", phoneMobile.getText().toString());
                                    editor.putString("phoneWork", phoneWork.getText().toString());
                                    editor.putString("phoneFax", phoneFax.getText().toString());
                                    editor.putString("phoneOther", phoneOther.getText().toString());
                                    editor.putString("customerDescription", customerDescription.getText().toString());
                                    editor.putString("email", email.getText().toString());
                                    editor.putString("addressCountry", addressCountry.getText().toString());
                                    editor.putString("addressCity", addressCity.getText().toString());
                                    editor.putString("addressStreet", addressStreet.getText().toString());
                                    editor.putString("addressPostalCode", addressPostalCode.getText().toString());

                                    if (mCustomerAvatar.getDrawable() != null)
                                        editor.putString("customerAvatar",
                                                Utility.encodeTobase64(((BitmapDrawable) mCustomerAvatar.getDrawable()).getBitmap()));

                                    editor.apply();

                                    if (kasebSharedPreferences.getString("numberOfEntranceToProfile", null).equals("1")) {
                                        //region start tour
                                        editor.putBoolean("getStarted", true);
                                        editor.apply();

                                        Intent intent = new Intent(getActivity(), Customers.class);
                                        startActivity(intent);
                                        Utility.setActivityTransition(getActivity());
                                        //endregion start tour
                                    } else
                                        Toast.makeText(getActivity(), R.string.msg_insert_succeed, Toast.LENGTH_SHORT).show();
                                    //endregion save info of kaseb profile

                                    try {
                                        ((DrawerActivity) getActivity()).setInfoOfKaseb();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (DocumentException e) {
                                        e.printStackTrace();
                                    }

                                    getHelperText();
                                    wantToCloseDialog = true;

                                    View view = dialogView.getCurrentFocus();
                                    if (view != null) {
                                        InputMethodManager imm = (InputMethodManager)
                                                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                    }
                                }

                                if (wantToCloseDialog)
                                    dialogView.dismiss();
                            }
                        });

                        dialogView.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (kasebSharedPreferences.getString("firstName", null) != null)
                                    dialogView.dismiss();
                                else
                                    Toast.makeText(getActivity(), R.string.complete_profile_kaseb, Toast.LENGTH_LONG).show();
                            }
                        });
                        //endregion create alert dialog

                        //region define views
                        firstName = (EditText) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_input_first_name);
                        lastName = (EditText) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_input_last_name);
                        birthDay = (EditText) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_input_birth_day);
                        phoneMobile = (EditText) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_input_phone_mobile);
                        customerDescription = (EditText) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_input_description);
                        email = (EditText) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_input_email);
                        phoneWork = (EditText) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_input_phone_work);
                        phoneOther = (EditText) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_input_phone_other);
                        phoneFax = (EditText) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_input_phone_fax);
                        addressCountry = (EditText) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_input_address_country);
                        addressCity = (EditText) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_input_address_city);
                        addressStreet = (EditText) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_input_address_street);
                        addressPostalCode = (EditText) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_input_address_postal_code);

                        mCustomerAvatar = (ImageView) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_customer_picture);
                        mCustomerAvatar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent.putExtra("crop", "true");
                                intent.putExtra("aspectX", 0);
                                intent.putExtra("aspectY", 0);
                                try {
                                    intent.putExtra("return-data", true);
                                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
                                } catch (ActivityNotFoundException e) {
                                }
                            }
                        });

                        firstNameTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_text_input_layout_input_first_name);
                        lastNameTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_text_input_layout_input_last_name);
                        phoneMobileTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_text_input_layout_input_phone_mobile);
                        birthDayTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_text_input_layout_input_birth_day);
                        customerDescriptionTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_text_input_layout_input_description);
                        emailTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_text_input_layout_input_email);
                        phoneWorkTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_text_input_layout_input_phone_work);
                        phoneOtherTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_text_input_layout_input_phone_other);
                        phoneFaxTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_text_input_layout_input_phone_fax);
                        addressCountryTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_text_input_layout_input_address_country);
                        addressCityTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_text_input_layout_input_address_city);
                        addressStreetTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_text_input_layout_input_address_street);
                        addressPostalCodeTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.dialog_edit_profile_kaseb_text_input_layout_input_address_postal_code);

                        if (kasebSharedPreferences.getString("firstName", null) == null)
                            dialogView.setCancelable(false);

                        //region show info of kaseb profile
                        firstName.setText(kasebSharedPreferences.getString("firstName", null));
                        lastName.setText(kasebSharedPreferences.getString("lastName", null));
                        birthDay.setText(kasebSharedPreferences.getString("birthDay", null));
                        phoneMobile.setText(kasebSharedPreferences.getString("phoneMobile", null));
                        phoneWork.setText(kasebSharedPreferences.getString("phoneWork", null));
                        phoneFax.setText(kasebSharedPreferences.getString("phoneFax", null));
                        phoneOther.setText(kasebSharedPreferences.getString("phoneOther", null));
                        customerDescription.setText(kasebSharedPreferences.getString("customerDescription", null));
                        email.setText(kasebSharedPreferences.getString("email", null));
                        addressCountry.setText(kasebSharedPreferences.getString("addressCountry", null));
                        addressCity.setText(kasebSharedPreferences.getString("addressCity", null));
                        addressStreet.setText(kasebSharedPreferences.getString("addressStreet", null));
                        addressPostalCode.setText(kasebSharedPreferences.getString("addressPostalCode", null));

                        if (kasebSharedPreferences.getString("customerAvatar", null) != null)
                            mCustomerAvatar.setImageBitmap(
                                    Utility.decodeBase64(kasebSharedPreferences.getString("customerAvatar", null)));
                        //endregion show info of kaseb profile

                        //endregion define views

                        break;
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        //region check exist profile kaseb in preference header
        if (kasebSharedPreferences.getString("firstName", null) == null &&
                kasebSharedPreferences.getString("customerAvatar", null) == null)
            mListView.performItemClick(
                    mListView.getAdapter().getView(6, null, null),
                    6, mListView.getAdapter().getItemId(6));
        //endregion check exist profile kaseb in preference header
    }

    private void doRestore() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            if (sd.canWrite()) {
                final String currentDBPath = getActivity().getDatabasePath("kaseb.db").getPath();
                String backupDBPath = Environment.getExternalStorageDirectory() + "/kaseb_copy.db";
                File backupDB = new File(currentDBPath);
                File currentDB = new File(backupDBPath);
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getActivity(), R.string.import_successful,
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.import_contact_failed, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void doBackup() throws IOException {
        final String inFileName = getActivity().getDatabasePath("kaseb.db").getPath();
        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);

        String outFileName = Environment.getExternalStorageDirectory() + "/kaseb_copy.db";

        // Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);

        // Transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        // Close the streams
        output.flush();
        output.close();
        fis.close();
        Toast.makeText(getActivity(), R.string.backup_successful, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        String[] displayName = new String[2];
        String phoneMobile = null;
        String contactEmail = null;
        String contactId = null;
        switch (reqCode) {
            case (RESULT_PICK_CONTACT): {
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor contactCursor = getActivity().getContentResolver().query(contactData, null, null, null, null);
                    if (contactCursor.moveToFirst()) {
                        contactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
                    }
                    displayName = getNames(contactId);
                    if (displayName[0] == null || displayName[1] == null) {
                        dataError(getActivity().getResources().getString(R.string.dialog_input_import_contact_first_last_name));
                        break;
                    }
                    if (contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)).equals("1"))
                        phoneMobile = getMobileNumber(contactId);
                    else {
                        dataError(getActivity().getResources().getString(R.string.dialog_input_import_contact_phone_mobile));
                        break;
                    }
                    contactEmail = getEmail(contactId);

                    if (displayName[0] != null && displayName[1] != null && phoneMobile != null) {
                        ContentValues contactValue = new ContentValues();
                        contactValue.put(KasebContract.Customers.COLUMN_FIRST_NAME, displayName[0]);
                        contactValue.put(KasebContract.Customers.COLUMN_LAST_NAME, displayName[1]);
                        contactValue.put(KasebContract.Customers.COLUMN_PHONE_MOBILE, phoneMobile);
                        contactValue.put(KasebContract.Customers.COLUMN_EMAIL, contactEmail);
                        Uri insertUri = getActivity().getContentResolver().insert(KasebContract.Customers.CONTENT_URI, contactValue);
                        Log.v(LOG_TAG, "Contact successfully is imported in: " + insertUri.toString());
                    } else {
                        dataError(getActivity().getResources().getString(R.string.dialog_input_import_contact_general));
                    }
                    break;
                }
            }
            case (PICK_FROM_GALLERY): {

                try {
                    if (data.getData() != null) {
                        //this is work in android 5.1 api 22
                        mCustomerAvatar.setImageURI(data.getData());

                        editor.putString("customerAvatar",
                                Utility.encodeTobase64(((BitmapDrawable) mCustomerAvatar.getDrawable()).getBitmap()));
                        editor.apply();
                    } else if (data.getExtras().getParcelable("data") != null) {
                        //this is work in android 4.2 api 17
                        Bitmap photo;
                        photo = data.getExtras().getParcelable("data");
                        mCustomerAvatar.setImageBitmap(photo);

                        editor.putString("customerAvatar",
                                Utility.encodeTobase64(((BitmapDrawable) mCustomerAvatar.getDrawable()).getBitmap()));
                        editor.apply();
                    }
                } catch (Exception e) {
                }

                break;
            }
        }
    }

    private void dataError(String name) {
        new AlertDialog.Builder(getActivity())
                .setTitle(getActivity().getResources().getString(R.string.dialog_title_import_customer_fail))
                .setMessage(getActivity().getResources().getString(R.string.dialog_title_import_customer_fail) + name + " " +
                        getActivity().getResources().getString(R.string.dialog_message_import_customer_fail_par2))
                .setPositiveButton(getActivity().getResources().getString(R.string.dialog_positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private String getEmail(String contactId) {
        Cursor cr = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{contactId}, null);
        ;
        String contactEmail = null;
        if (cr.moveToFirst())
            contactEmail = cr.getString(cr.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.DATA));
        cr.close();
        return contactEmail;
    }

    private String getMobileNumber(String contactId) {
        Cursor cr = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                + " = " + contactId, null, null);
        String contactMobilePhone = null;
        if (cr.moveToFirst())
            contactMobilePhone = cr.getString(cr.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
        cr.close();
        return contactMobilePhone;
    }

    private String[] getNames(String contactId) {
        String firstName = null;
        String family = null;
        String whereName = ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?";
        String[] whereNameParams = new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, contactId};
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.Data.CONTENT_URI
                , null, whereName, whereNameParams,
                ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
        while (cursor.moveToNext()) {
            firstName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
            family = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
        }
        cursor.close();
        return new String[]{firstName, family};
    }

    private ArrayList<String> initHeaderSummary() {
        return null;
    }

    private ArrayList<String> initHeaderTitle() {
        ArrayList headerTitle = new ArrayList<>();
        headerTitle.add(getActivity().getResources().getString(R.string.pref_header_cost));
        headerTitle.add(getActivity().getResources().getString(R.string.pref_header_tax));
        headerTitle.add(getActivity().getResources().getString(R.string.pref_header_payment));
        headerTitle.add(getActivity().getResources().getString(R.string.pref_header_customer));
        headerTitle.add(getActivity().getResources().getString(R.string.pref_header_backup));
        headerTitle.add(getActivity().getResources().getString(R.string.pref_header_import_contacts));
        headerTitle.add(getActivity().getResources().getString(R.string.pref_header_edit_profile));
        return headerTitle;
    }

    private ArrayList<Integer> initHeaderIcon() {
        ArrayList<Integer> headerIcons = new ArrayList<>();
        headerIcons.add(R.drawable.coin);
        headerIcons.add(R.drawable.taxtype);
        headerIcons.add(R.drawable.paymentmethod);
        headerIcons.add(R.drawable.statetype);
        headerIcons.add(R.drawable.backuprestore);
        headerIcons.add(R.drawable.importcontact);
        headerIcons.add(R.drawable.kaseb_profile);
        return headerIcons;
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

        if (!Utility.checkForValidityForEditTextNullOrEmpty(getActivity(), phoneMobile)) {
            phoneMobileTextInputLayout.setError(String.format("%s %s",
                    getResources().getString(R.string.example_mobile_number),
                    getResources().getString(R.string.non_repetitive)));
            phoneMobile.setSelectAllOnFocus(true);
            phoneMobile.selectAll();
            phoneMobile.requestFocus();
            return false;
        } else
            phoneMobileTextInputLayout.setError(null);

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
