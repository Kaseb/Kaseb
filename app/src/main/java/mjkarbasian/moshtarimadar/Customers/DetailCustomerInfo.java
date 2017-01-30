package mjkarbasian.moshtarimadar.Customers;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by family on 7/27/2016.
 */
public class DetailCustomerInfo extends Fragment {
    Integer customerosition;
    String[] mProjectionCursor;
    Uri uriCursor;

    EditText customerFirstName;
    EditText customerLastName;
    EditText customerBirthDay;
    EditText customerPhoneMobile;
    EditText customerDescription;
    EditText customerEmail;
    EditText customerPhoneWork;
    EditText customerPhoneHome;
    EditText customerPhoneOther;
    EditText customerPhoneFax;
    EditText customerAddressCountry;
    EditText customerAddressCity;
    EditText customerAddressStreet;
    EditText customerAddressPostalCode;

    String sCustomerFirstName;
    String sCustomerLastName;
    String sCustomerBirthDay;
    String sCustomerPhoneMobile;
    String sCustomerDescription;
    String sCustomerEmail;
    String sCustomerPhoneWork;
    String sCustomerPhoneHome;
    String sCustomerPhoneOther;
    String sCustomerPhoneFax;
    String sCustomerAddressCountry;
    String sCustomerAddressCity;
    String sCustomerAddressStreet;
    String sCustomerAddressPostalCode;

    public DetailCustomerInfo() {
        super();
    }

    public DetailCustomerInfo(Uri uri) {
        super();
        uriCursor = uri;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_info, container, false);

        customerFirstName = (EditText) view.findViewById(R.id.customer_first_name);
        customerLastName = (EditText) view.findViewById(R.id.customer_last_name);
        customerBirthDay = (EditText) view.findViewById(R.id.customer_birth_day);
        customerPhoneMobile = (EditText) view.findViewById(R.id.customer_phone_mobile);
        customerDescription = (EditText) view.findViewById(R.id.customer_description);
        customerEmail = (EditText) view.findViewById(R.id.customer_email);
        customerPhoneWork = (EditText) view.findViewById(R.id.customer_phone_work);
        customerPhoneHome = (EditText) view.findViewById(R.id.customer_phone_home);
        customerPhoneOther = (EditText) view.findViewById(R.id.customer_phone_other);
        customerPhoneFax = (EditText) view.findViewById(R.id.customer_phone_fax);
        customerAddressCountry = (EditText) view.findViewById(R.id.customer_address_country);
        customerAddressCity = (EditText) view.findViewById(R.id.customer_address_city);
        customerAddressStreet = (EditText) view.findViewById(R.id.customer_address_street);
        customerAddressPostalCode = (EditText) view.findViewById(R.id.customer_address_postal_code);

        mProjectionCursor = new String[]{
                KasebContract.Customers._ID,
                KasebContract.Customers.COLUMN_FIRST_NAME,
                KasebContract.Customers.COLUMN_LAST_NAME,
                KasebContract.Customers.COLUMN_BIRTHDAY,
                KasebContract.Customers.COLUMN_PHONE_MOBILE,
                KasebContract.Customers.COLUMN_PHONE_HOME,
                KasebContract.Customers.COLUMN_PHONE_FAX,
                KasebContract.Customers.COLUMN_PHONE_WORK,
                KasebContract.Customers.COLUMN_PHONE_OTHER,
                KasebContract.Customers.COLUMN_DESCRIPTION,
                KasebContract.Customers.COLUMN_EMAIL,
                KasebContract.Customers.COLUMN_ADDRESS_COUNTRY,
                KasebContract.Customers.COLUMN_ADDRESS_CITY,
                KasebContract.Customers.COLUMN_ADDRESS_STREET,
                KasebContract.Customers.COLUMN_ADDRESS_POSTAL_CODE};

        Cursor customerCursor = getContext().getContentResolver().query(
                uriCursor,
                mProjectionCursor,
                null,
                null,
                null
        );

        if (customerCursor != null) {
            if (customerCursor.moveToFirst()) {
                sCustomerFirstName = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_FIRST_NAME));
                sCustomerLastName = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_LAST_NAME));
                sCustomerBirthDay = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_BIRTHDAY));
                sCustomerPhoneMobile = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_PHONE_MOBILE));
                sCustomerDescription = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_DESCRIPTION));
                sCustomerEmail = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_EMAIL));
                sCustomerPhoneWork = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_PHONE_WORK));
                sCustomerPhoneHome = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_PHONE_HOME));
                sCustomerPhoneOther = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_PHONE_OTHER));
                sCustomerPhoneFax = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_PHONE_FAX));
                sCustomerAddressCountry = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_ADDRESS_COUNTRY));
                sCustomerAddressCity = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_ADDRESS_CITY));
                sCustomerAddressStreet = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_ADDRESS_STREET));
                sCustomerAddressPostalCode = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_ADDRESS_POSTAL_CODE));
            }
        }

        customerBirthDay.setText(sCustomerBirthDay);
        customerPhoneMobile.setText(sCustomerPhoneMobile);
        customerDescription.setText(sCustomerDescription);
        customerEmail.setText(sCustomerEmail);
        customerPhoneWork.setText(sCustomerPhoneWork);
        customerPhoneHome.setText(sCustomerPhoneHome);
        customerPhoneOther.setText(sCustomerPhoneOther);
        customerPhoneFax.setText(sCustomerPhoneFax);
        customerAddressCountry.setText(sCustomerAddressCountry);
        customerAddressCity.setText(sCustomerAddressCity);
        customerAddressStreet.setText(sCustomerAddressStreet);
        customerAddressPostalCode.setText(sCustomerAddressPostalCode);

        customerBirthDay.setEnabled(false);
        customerPhoneMobile.setEnabled(false);
        customerDescription.setEnabled(false);
        customerEmail.setEnabled(false);
        customerPhoneWork.setEnabled(false);
        customerPhoneHome.setEnabled(false);
        customerPhoneOther.setEnabled(false);
        customerPhoneFax.setEnabled(false);
        customerAddressCountry.setEnabled(false);
        customerAddressCity.setEnabled(false);
        customerAddressStreet.setEnabled(false);
        customerAddressPostalCode.setEnabled(false);

        ImageView callImagePhoneMobile = (ImageView) view.findViewById(R.id.customer_call_btn_phone_mobile);
        ImageView smsImagePhoneMobile = (ImageView) view.findViewById(R.id.customer_sms_btn_phone_mobile);
        ImageView callImagePhoneHome = (ImageView) view.findViewById(R.id.customer_call_btn_phone_home);
        ImageView smsImagePhoneHome = (ImageView) view.findViewById(R.id.customer_sms_btn_phone_home);
        ImageView callImagePhoneWork = (ImageView) view.findViewById(R.id.customer_call_btn_phone_work);
        ImageView smsImagePhoneWork = (ImageView) view.findViewById(R.id.customer_sms_btn_phone_work);
        ImageView callImagePhoneFax = (ImageView) view.findViewById(R.id.customer_call_btn_phone_fax);
        ImageView smsImagePhoneFax = (ImageView) view.findViewById(R.id.customer_sms_btn_phone_fax);
        ImageView callImagePhoneOther = (ImageView) view.findViewById(R.id.customer_call_btn_phone_other);
        ImageView smsImagePhoneOther = (ImageView) view.findViewById(R.id.customer_sms_btn_phone_other);
        ImageView emailImage = (ImageView) view.findViewById(R.id.customer_email_btn);
        ImageView shareImage = (ImageView) view.findViewById(R.id.customer_share_btn);

        callImagePhoneMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", sCustomerPhoneMobile, null));
                getActivity().startActivity(intent);
            }
        });

        smsImagePhoneMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", sCustomerPhoneMobile, null));
                getActivity().startActivity(intent);
            }
        });

        callImagePhoneHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", sCustomerPhoneHome, null));
                getActivity().startActivity(intent);
            }
        });

        smsImagePhoneHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", sCustomerPhoneHome, null));
                getActivity().startActivity(intent);
            }
        });

        callImagePhoneWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", sCustomerPhoneWork, null));
                getActivity().startActivity(intent);
            }
        });

        smsImagePhoneWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", sCustomerPhoneWork, null));
                getActivity().startActivity(intent);
            }
        });

        callImagePhoneFax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", sCustomerPhoneFax, null));
                getActivity().startActivity(intent);
            }
        });

        smsImagePhoneFax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", sCustomerPhoneFax, null));
                getActivity().startActivity(intent);
            }
        });

        callImagePhoneOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", sCustomerPhoneOther, null));
                getActivity().startActivity(intent);
            }
        });

        smsImagePhoneOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", sCustomerPhoneOther, null));
                getActivity().startActivity(intent);
            }
        });

        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, sCustomerPhoneMobile);
                getActivity().startActivity(Intent.createChooser(intent, "Share with"));
            }
        });

        emailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_TEXT, sCustomerEmail);
                getActivity().startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        return view;
    }
}
