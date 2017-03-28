package com.byteshaft.doctor.accountfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.byteshaft.doctor.MainActivity;
import com.byteshaft.doctor.R;
import com.byteshaft.doctor.utils.AppGlobals;
import com.byteshaft.doctor.utils.Helpers;
import com.byteshaft.requests.FormData;
import com.byteshaft.requests.HttpRequest;
import com.github.lzyzsd.circleprogress.DonutProgress;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


public class UserBasicInfoStepTwo extends Fragment implements AdapterView.OnItemSelectedListener,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener, HttpRequest.OnReadyStateChangeListener,
        HttpRequest.OnFileUploadProgressListener {
    private View mBaseView;
    private Spinner mStateSpinner;
    private Spinner mCitySpinner;
    private Spinner mInsuranceCarrierSpinner;
//    private Spinner mAffiliatedClinicsSpinner;
    private TextView mStateSpinnerTextView;
    private EditText mPhoneOneEditText;
    private EditText mPhoneTwoEditText;
    private EditText mEmergencyContactEditText;
    private CheckBox mNotificationCheckBox;
    private CheckBox mNewsCheckBox;
    private CheckBox mTermsConditionCheckBox;
    private String mPhoneOneEditTextString;
    private String mPhoneTwoEditTextString;
    private String mEmergencyContactString;
    private String mStatesSpinnerValueString;
    private String mCitiesSpinnerValueString;
    private String mAffiliatedClinicsSpinnerValueString;
    private String mInsuranceCarrierSpinnerValueString;
    private String mNotificationCheckBoxString = "true";
    private String mNewsCheckBoxString = "true";
    private String mTermsConditionCheckBoxString;
    private Button mSaveButton;
    private HttpRequest mRequest;
    private DonutProgress donutProgress;
    private AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.fragment_user_basic_info_step_two, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(getResources().getString(R.string.sign_up));
        setHasOptionsMenu(true);
        mStateSpinner = (Spinner) mBaseView.findViewById(R.id.states_spinner);
        mCitySpinner = (Spinner) mBaseView.findViewById(R.id.cities_spinner);
        mInsuranceCarrierSpinner = (Spinner) mBaseView.findViewById(R.id.insurance_spinner);
//        mAffiliatedClinicsSpinner = (Spinner) mBaseView.findViewById(R.id.clinic_spinner);
        mStateSpinnerTextView = (TextView) mBaseView.findViewById(R.id.states_spinner_text_view);

        mPhoneOneEditText = (EditText) mBaseView.findViewById(R.id.phone_one_edit_text);
        mPhoneTwoEditText = (EditText) mBaseView.findViewById(R.id.phone_two_edit_text);
        mEmergencyContactEditText = (EditText) mBaseView.findViewById(R.id.emergency_contact);

        mPhoneOneEditText.setText(AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_PHONE_NUMBER_PRIMARY));
        mPhoneTwoEditText.setText(AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_PHONE_NUMBER_SECONDARY));
        mEmergencyContactEditText.setText(AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_EMERGENCY_CONTACT));

        mNotificationCheckBox = (CheckBox) mBaseView.findViewById(R.id.notifications_check_box);
        mNewsCheckBox = (CheckBox) mBaseView.findViewById(R.id.news_check_box);
        mTermsConditionCheckBox = (CheckBox) mBaseView.findViewById(R.id.terms_check_box);

        mSaveButton = (Button) mBaseView.findViewById(R.id.save_button);

        mPhoneOneEditText.setTypeface(AppGlobals.typefaceNormal);
        mPhoneTwoEditText.setTypeface(AppGlobals.typefaceNormal);
        mEmergencyContactEditText.setTypeface(AppGlobals.typefaceNormal);
        mNotificationCheckBox.setTypeface(AppGlobals.typefaceNormal);
        mNewsCheckBox.setTypeface(AppGlobals.typefaceNormal);
        mTermsConditionCheckBox.setTypeface(AppGlobals.typefaceNormal);

        List<String> StateList = new ArrayList<>();
        StateList.add("State1");
        StateList.add("State2");
        StateList.add("State3");
        StateList.add("State4");
        StateList.add("State5");
        ArrayAdapter<String> StateListAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, StateList);
        StateListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStateSpinner.setAdapter(StateListAdapter);

        List<String> citiesList = new ArrayList<String>();
        citiesList.add("city1");
        citiesList.add("city2");
        citiesList.add("city3");
        citiesList.add("city4");
        citiesList.add("city5");
        ArrayAdapter<String> CitiesListAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, citiesList);
        CitiesListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCitySpinner.setAdapter(CitiesListAdapter);

        List<String> InsuranceCarrierList = new ArrayList<>();
        InsuranceCarrierList.add("Insurance Carrier-1");
        InsuranceCarrierList.add("Insurance Carrier-2");
        InsuranceCarrierList.add("Insurance Carrier-3");
        InsuranceCarrierList.add("Insurance Carrier-4");
        ArrayAdapter<String> InsuranceCarrierListAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, InsuranceCarrierList);
        InsuranceCarrierListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mInsuranceCarrierSpinner.setAdapter(InsuranceCarrierListAdapter);


//        List<String> clinicList = new ArrayList<>();
//        clinicList.add("Doctor dray clinic");
//        clinicList.add("Cantt clinic");
//        clinicList.add("City hospital");
//        clinicList.add("Medicare");
//        clinicList.add("Patient care");
//        ArrayAdapter<String> clinicListAdapter = new ArrayAdapter<>(getActivity(),
//                android.R.layout.simple_list_item_1, clinicList);
//        clinicListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mAffiliatedClinicsSpinner.setAdapter(clinicListAdapter);

        mStateSpinner.setOnItemSelectedListener(this);
        mCitySpinner.setOnItemSelectedListener(this);
//        mAffiliatedClinicsSpinner.setOnItemSelectedListener(this);
        mInsuranceCarrierSpinner.setOnItemSelectedListener(this);

        mNotificationCheckBox.setOnCheckedChangeListener(this);
        mNewsCheckBox.setOnCheckedChangeListener(this);
        mTermsConditionCheckBox.setOnCheckedChangeListener(this);

        mSaveButton.setOnClickListener(this);

        return mBaseView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.states_spinner:
                mStatesSpinnerValueString = adapterView.getItemAtPosition(i).toString();
                System.out.println(mStatesSpinnerValueString);
                break;
            case R.id.cities_spinner:
                mCitiesSpinnerValueString = adapterView.getItemAtPosition(i).toString();
                System.out.println(mCitiesSpinnerValueString);
                break;
            case R.id.insurance_spinner:
                mInsuranceCarrierSpinnerValueString = adapterView.getItemAtPosition(i).toString();
                System.out.println(mInsuranceCarrierSpinnerValueString);
                break;
//            case R.id.clinic_spinner:
//                mAffiliatedClinicsSpinnerValueString = adapterView.getItemAtPosition(i).toString();
//                System.out.println(mAffiliatedClinicsSpinnerValueString);
//                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        mPhoneTwoEditTextString = mPhoneTwoEditText.getText().toString();
        if (validateEditText()) {
            sendingDataToServer();
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.notifications_check_box:
                if (mNotificationCheckBox.isChecked()) {
                    mNotificationCheckBoxString = "true";
                    System.out.println(mNotificationCheckBoxString);
                } else {
                    mNotificationCheckBoxString = "false";
                }
                break;
            case R.id.news_check_box:
                if (mNewsCheckBox.isChecked()) {
                    mNewsCheckBoxString = "true";
                    System.out.println(mNewsCheckBoxString);
                } else {
                    mNewsCheckBoxString = "false";
                }
                break;
            case R.id.terms_check_box:
                if (mTermsConditionCheckBox.isChecked()) {
                    mSaveButton.setEnabled(true);
                    mTermsConditionCheckBoxString = mTermsConditionCheckBox.getText().toString();
                    System.out.println(mTermsConditionCheckBoxString);
                } else {
                    mSaveButton.setEnabled(false);
                }
                break;
        }

    }

    private boolean validateEditText() {
        boolean valid = true;
        mPhoneOneEditTextString = mPhoneOneEditText.getText().toString();
        mEmergencyContactString = mEmergencyContactEditText.getText().toString();

        if (mPhoneOneEditTextString.trim().isEmpty()) {
            mPhoneOneEditText.setError("please enter your phone number");
            valid = false;
        } else {
            mPhoneOneEditText.setError(null);
        }
        if (mEmergencyContactString.trim().isEmpty()) {
            mEmergencyContactEditText.setError("please provide your Emergency Contact");
            valid = false;
        } else {
            mEmergencyContactEditText.setError(null);
        }

        return valid;
    }

    private void sendingDataToServer() {
        FormData data = new FormData();
        data.append(FormData.TYPE_CONTENT_TEXT, "identity_document", AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_DOC_ID));
        data.append(FormData.TYPE_CONTENT_TEXT, "first_name", AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_FIRST_NAME));
        data.append(FormData.TYPE_CONTENT_TEXT, "last_name", AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_LAST_NAME));
        data.append(FormData.TYPE_CONTENT_TEXT, "dob", AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_DATE_OF_BIRTH));
        data.append(FormData.TYPE_CONTENT_TEXT, "gender", AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_GENDER));
        data.append(FormData.TYPE_CONTENT_TEXT, "location", AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_LOCATION));
        data.append(FormData.TYPE_CONTENT_TEXT, "address", AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_ADDRESS));
        if (!AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_IMAGE_URL).trim().isEmpty()) {
            data.append(FormData.TYPE_CONTENT_FILE, "photo", AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_IMAGE_URL));
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle(getResources().getString(R.string.updating));
            alertDialogBuilder.setCancelable(false);
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.progress_alert_dialog, null);
            alertDialogBuilder.setView(dialogView);
            donutProgress = (DonutProgress) dialogView.findViewById(R.id.upload_progress);

            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            Helpers.showProgressDialog(getActivity(), "Updating your Profile...");
        }
        data.append(FormData.TYPE_CONTENT_TEXT, "state", mStatesSpinnerValueString);
        data.append(FormData.TYPE_CONTENT_TEXT, "city", mCitiesSpinnerValueString);
        data.append(FormData.TYPE_CONTENT_TEXT, "insurance_carrier", mInsuranceCarrierSpinnerValueString);
//        data.append(FormData.TYPE_CONTENT_TEXT, "affiliate_clinic", mAffiliatedClinicsSpinnerValueString);
        data.append(FormData.TYPE_CONTENT_TEXT, "phone_number_primary", mPhoneOneEditTextString);
        data.append(FormData.TYPE_CONTENT_TEXT, "phone_number_secondary", mPhoneTwoEditTextString);
        data.append(FormData.TYPE_CONTENT_TEXT, "emergency_contact", mEmergencyContactString);
        data.append(FormData.TYPE_CONTENT_TEXT, "show_notification", mNotificationCheckBoxString);
        data.append(FormData.TYPE_CONTENT_TEXT, "show_news", mNewsCheckBoxString);

        mRequest = new HttpRequest(getActivity().getApplicationContext());
        mRequest.setOnReadyStateChangeListener(this);
        mRequest.setOnFileUploadProgressListener(this);
        mRequest.open("POST", String.format("%suser/profile", AppGlobals.BASE_URL));
        mRequest.setRequestHeader("Authorization", "Token " +
                AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_TOKEN));
        mRequest.setTimeout(200000);
        mRequest.send(data);
    }

    @Override
    public void onReadyStateChange(HttpRequest request, int readyState) {
        switch (readyState) {
            case HttpRequest.STATE_DONE:
                if (alertDialog != null) {
                    donutProgress.setProgress(100);
                    alertDialog.dismiss();
                } else {
                    Helpers.dismissProgressDialog();
                }
                switch (request.getStatus()) {
                    case HttpRequest.ERROR_NETWORK_UNREACHABLE:
                        AppGlobals.alertDialog(getActivity(), "Profile update Failed!", "please check your internet connection");
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        AppGlobals.alertDialog(getActivity(), "Profile update Failed!", "provide a valid EmailAddress");
                        break;
                    case HttpURLConnection.HTTP_UNAUTHORIZED:
                        AppGlobals.alertDialog(getActivity(), "Profile update Failed!", "Please enter correct password");
                        break;
                    case HttpURLConnection.HTTP_FORBIDDEN:
                        AppGlobals.alertDialog(getActivity(), "Inactive Account", "Please activate your account");
                        AccountManagerActivity.getInstance().loadFragment(new AccountActivationCode());
                        break;
                    case HttpURLConnection.HTTP_CREATED:
                        System.out.println(request.getResponseText() + "working ");
                        Toast.makeText(getActivity(), "Profile Created Successfully", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(request.getResponseText());
                            System.out.println(jsonObject + "working ");

                            String userId = jsonObject.getString(AppGlobals.KEY_USER_ID);
                            String firstName = jsonObject.getString(AppGlobals.KEY_FIRST_NAME);
                            String lastName = jsonObject.getString(AppGlobals.KEY_LAST_NAME);
                            String imageUrl = jsonObject.getString(AppGlobals.KEY_IMAGE_URL);

                            String gender = jsonObject.getString(AppGlobals.KEY_GENDER);
                            String dateOfBirth = jsonObject.getString(AppGlobals.KEY_DATE_OF_BIRTH);
                            String phoneNumberPrimary = jsonObject.getString(AppGlobals.KEY_PHONE_NUMBER_PRIMARY);
                            String phoneNumberSecondary = jsonObject.getString(AppGlobals.KEY_PHONE_NUMBER_SECONDARY);

//                            String affiliateClinic = jsonObject.getString(AppGlobals.KEY_AFFILIATE_CLINIC);
                            String insuranceCarrier = jsonObject.getString(AppGlobals.KEY_INSURANCE_CARRIER);
                            String address = jsonObject.getString(AppGlobals.KEY_ADDRESS);
                            String location = jsonObject.getString(AppGlobals.KEY_LOCATION);

                            String chatStatus = jsonObject.getString(AppGlobals.KEY_CHAT_STATUS);
                            String state = jsonObject.getString(AppGlobals.KEY_STATE);
                            String city = jsonObject.getString(AppGlobals.KEY_CITY);
                            String docId = jsonObject.getString(AppGlobals.KEY_DOC_ID);
                            String showNews = jsonObject.getString(AppGlobals.KEY_SHOW_NEWS);

                            String showNotification = jsonObject.getString(AppGlobals.KEY_SHOW_NOTIFICATION);
                            String emergencyContact = jsonObject.getString(AppGlobals.KEY_EMERGENCY_CONTACT);

                            //saving values
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_USER_ID, userId);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_FIRST_NAME, firstName);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_LAST_NAME, lastName);

                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_GENDER, gender);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_DATE_OF_BIRTH, dateOfBirth);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_PHONE_NUMBER_PRIMARY, phoneNumberPrimary);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_PHONE_NUMBER_SECONDARY, phoneNumberSecondary);

//                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_AFFILIATE_CLINIC, affiliateClinic);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_INSURANCE_CARRIER, insuranceCarrier);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_ADDRESS, address);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_LOCATION, location);

                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_CHAT_STATUS, chatStatus);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_STATE, state);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_CITY, city);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_DOC_ID, docId);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_SHOW_NEWS, showNews);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_SHOW_NOTIFICATION, showNotification);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_EMERGENCY_CONTACT, emergencyContact);
                            Log.i("Emergency Contact", " " + AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_EMERGENCY_CONTACT));
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.SERVER_PHOTO_URL, imageUrl);
                            AppGlobals.gotInfo(true);
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                }
        }

    }

    @Override
    public void onFileUploadProgress(HttpRequest request, File file, long loaded, long total) {
        double progress = (loaded / (double) total) * 100;
        Log.i("current progress", "" + (int) progress);
        donutProgress.setProgress((int) progress);


    }
}
