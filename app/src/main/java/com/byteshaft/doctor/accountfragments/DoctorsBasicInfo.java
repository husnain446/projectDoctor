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

public class DoctorsBasicInfo extends Fragment implements AdapterView.OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener, View.OnClickListener, HttpRequest.OnReadyStateChangeListener, HttpRequest.OnFileUploadProgressListener {

    private View mBaseView;
    private Button mSaveButton;

    private Spinner mStateSpinner;
    private Spinner mCitySpinner;
    private Spinner mSpecialitySpinner;
    private Spinner mAffiliatedClinicsSpinner;
    private Spinner mSubscriptionSpinner;

    private EditText mPhoneOneEditText;
    private EditText mPhoneTwoEditText;
    private EditText mConsultationTimeEditText;
    private EditText mCollegeIdEditText;

    private CheckBox mNotificationCheckBox;
    private CheckBox mNewsCheckBox;
    private CheckBox mTermsConditionCheckBox;
    private String mPhoneOneEditTextString;
    private String mPhoneTwoEditTextString;
    private String mConsultationTimeEditTextString;
    private String mCollegeIdEditTextString;
    private String mStatesSpinnerValueString;
    private String mCitiesSpinnerValueString;
    private String mSpecialitySpinnerValueString;
    private String mAffiliatedClinicsSpinnerValueString;
    private String mSubscriptionSpinnerValueString;
    private String mNotificationCheckBoxString = "true";
    private String mNewsCheckBoxString = "true";
    private String mTermsConditionCheckBoxString;

    private HttpRequest mRequest;
    private DonutProgress donutProgress;
    private AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.fragment_doctor_basic_info, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(getResources().getString(R.string.sign_up));
        setHasOptionsMenu(true);
        mSaveButton = (Button) mBaseView.findViewById(R.id.save_button);
        mStateSpinner = (Spinner) mBaseView.findViewById(R.id.states_spinner);
        mCitySpinner = (Spinner) mBaseView.findViewById(R.id.cities_spinner);
        mSpecialitySpinner = (Spinner) mBaseView.findViewById(R.id.speciality_spinner);
        mAffiliatedClinicsSpinner = (Spinner) mBaseView.findViewById(R.id.clinics_spinner);
        mSubscriptionSpinner = (Spinner) mBaseView.findViewById(R.id.subscriptions_spinner);

        mPhoneOneEditText = (EditText) mBaseView.findViewById(R.id.phone_one_edit_text);
        mPhoneTwoEditText = (EditText) mBaseView.findViewById(R.id.phone_two_edit_text);
        mConsultationTimeEditText = (EditText) mBaseView.findViewById(R.id.consultation_time_edit_text);
        mCollegeIdEditText = (EditText) mBaseView.findViewById(R.id.college_id_edit_text);

        mNotificationCheckBox = (CheckBox) mBaseView.findViewById(R.id.notifications_check_box);
        mNewsCheckBox = (CheckBox) mBaseView.findViewById(R.id.news_check_box);
        mTermsConditionCheckBox = (CheckBox) mBaseView.findViewById(R.id.terms_check_box);

        mSaveButton.setTypeface(AppGlobals.typefaceNormal);
        mPhoneOneEditText.setTypeface(AppGlobals.typefaceNormal);
        mPhoneTwoEditText.setTypeface(AppGlobals.typefaceNormal);
        mConsultationTimeEditText.setTypeface(AppGlobals.typefaceNormal);
        mCollegeIdEditText.setTypeface(AppGlobals.typefaceNormal);

        mPhoneOneEditText.setText(AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_PHONE_NUMBER_PRIMARY));
        mPhoneTwoEditText.setText(AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_PHONE_NUMBER_SECONDARY));
        mConsultationTimeEditText.setText(AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_CONSULTATION_TIME));
        mCollegeIdEditText  .setText(AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_COLLEGE_ID));


        List<String> StateList = new ArrayList<>();
        StateList.add("state1");
        StateList.add("state2");
        StateList.add("state3");
        StateList.add("state4");
        StateList.add("state5");
        ArrayAdapter<String> StateListAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, StateList);
        StateListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStateSpinner.setAdapter(StateListAdapter);

        List<String> citiesList = new ArrayList<>();
        citiesList.add("city1");
        citiesList.add("city2");
        citiesList.add("city3");
        citiesList.add("city4");
        citiesList.add("city5");
        ArrayAdapter<String> CitiesListAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, citiesList);
        CitiesListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCitySpinner.setAdapter(CitiesListAdapter);

        List<String> specialityList = new ArrayList<>();
        specialityList.add("ent");
        specialityList.add("dermatologist");
        specialityList.add("surgeon");
        specialityList.add("physiotherapist");
        specialityList.add("dentist");
        ArrayAdapter<String> SpecialityListAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, specialityList);
        SpecialityListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpecialitySpinner.setAdapter(SpecialityListAdapter);

        List<String> clinicList = new ArrayList<>();
        clinicList.add("dray clinic");
        clinicList.add("cantt clinic");
        clinicList.add("city hospital");
        clinicList.add("medicare");
        clinicList.add("patient care");
        ArrayAdapter<String> clinicListAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, clinicList);
        clinicListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAffiliatedClinicsSpinner.setAdapter(clinicListAdapter);

        List<String> subscriptionList = new ArrayList<>();
        subscriptionList.add("basic plan");
        subscriptionList.add("monthly plan");
        subscriptionList.add("Premium plan");
        subscriptionList.add("professional plan");
        ArrayAdapter<String> subscriptionListAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, subscriptionList);
        subscriptionListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSubscriptionSpinner.setAdapter(subscriptionListAdapter);


        mStateSpinner.setOnItemSelectedListener(this);
        mCitySpinner.setOnItemSelectedListener(this);
        mSpecialitySpinner.setOnItemSelectedListener(this);
        mAffiliatedClinicsSpinner.setOnItemSelectedListener(this);
        mSubscriptionSpinner.setOnItemSelectedListener(this);

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
                getFragmentManager().popBackStack();
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
            case R.id.speciality_spinner:
                mSpecialitySpinnerValueString = adapterView.getItemAtPosition(i).toString();
                System.out.println(mSpecialitySpinnerValueString);
                break;
            case R.id.clinics_spinner:
                mAffiliatedClinicsSpinnerValueString = adapterView.getItemAtPosition(i).toString();
                System.out.println(mAffiliatedClinicsSpinnerValueString);
                break;
            case R.id.subscriptions_spinner:
                mSubscriptionSpinnerValueString = adapterView.getItemAtPosition(i).toString();
                System.out.println(mSubscriptionSpinnerValueString);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.notifications_check_box:
                if (mNotificationCheckBox.isChecked()) {
                    mNotificationCheckBoxString = "true";
                    System.out.println(mNotificationCheckBoxString);
                }
                break;
            case R.id.news_check_box:
                if (mNewsCheckBox.isChecked()) {
                    mNewsCheckBoxString = "true";
                    System.out.println(mNewsCheckBoxString);
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

    @Override
    public void onClick(View view) {
        mPhoneTwoEditTextString = mPhoneTwoEditText.getText().toString();
        if (validateEditText()) {
            sendingDataToServer();
        }

    }

    private boolean validateEditText() {
        boolean valid = true;
        mPhoneOneEditTextString = mPhoneOneEditText.getText().toString();
        mCollegeIdEditTextString = mCollegeIdEditText.getText().toString();
        mConsultationTimeEditTextString = mConsultationTimeEditText.getText().toString();

        if (mPhoneOneEditTextString.trim().isEmpty()) {
            mPhoneOneEditText.setError("please enter your phone number");
            valid = false;
        } else {
            mPhoneOneEditText.setError(null);
        }
        if (mCollegeIdEditTextString.trim().isEmpty()) {
            mCollegeIdEditText.setError("please provide your collegeID");
            valid = false;
        } else {
            mCollegeIdEditText.setError(null);
        }
        if (mConsultationTimeEditTextString.trim().isEmpty()) {
            mConsultationTimeEditText.setError("please enter your consultation time");
            valid = false;
        } else {
            mConsultationTimeEditText.setError(null);
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
        Log.i("TAG", "key image url " + AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_IMAGE_URL));
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
        data.append(FormData.TYPE_CONTENT_TEXT, "speciality", mSpecialitySpinnerValueString);
        data.append(FormData.TYPE_CONTENT_TEXT, "affiliate_clinic", mAffiliatedClinicsSpinnerValueString);
        data.append(FormData.TYPE_CONTENT_TEXT, "subscription_type", mSubscriptionSpinnerValueString);
        data.append(FormData.TYPE_CONTENT_TEXT, "phone_number_primary", mPhoneOneEditTextString);
        data.append(FormData.TYPE_CONTENT_TEXT, "phone_number_secondary", mPhoneTwoEditTextString);
        data.append(FormData.TYPE_CONTENT_TEXT, "consultation_time", mConsultationTimeEditTextString);
        data.append(FormData.TYPE_CONTENT_TEXT, "college_id", mCollegeIdEditTextString);
        data.append(FormData.TYPE_CONTENT_TEXT, "show_notification", mNotificationCheckBoxString);
        data.append(FormData.TYPE_CONTENT_TEXT, "show_news", mNewsCheckBoxString);

        mRequest = new HttpRequest(getActivity().getApplicationContext());
        mRequest.setOnReadyStateChangeListener(this);
        mRequest.setOnFileUploadProgressListener(this);
        mRequest.open("POST", String.format("%suser/profile", AppGlobals.BASE_URL));
        mRequest.setRequestHeader("Authorization", "Token " +
                AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_TOKEN));
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
                        try {
                            JSONObject jsonObject = new JSONObject(request.getResponseText());

                            String userId = jsonObject.getString(AppGlobals.KEY_USER_ID);
                            String firstName = jsonObject.getString(AppGlobals.KEY_FIRST_NAME);
                            String lastName = jsonObject.getString(AppGlobals.KEY_LAST_NAME);

                            String gender = jsonObject.getString(AppGlobals.KEY_GENDER);
                            String dateOfBirth = jsonObject.getString(AppGlobals.KEY_DATE_OF_BIRTH);
                            String phoneNumberPrimary = jsonObject.getString(AppGlobals.KEY_PHONE_NUMBER_PRIMARY);
                            String phoneNumberSecondary = jsonObject.getString(AppGlobals.KEY_PHONE_NUMBER_SECONDARY);

                            String affiliateClinic = jsonObject.getString(AppGlobals.KEY_AFFILIATE_CLINIC);
                            String subscriptionType = jsonObject.getString(AppGlobals.KEY_SUBSCRIPTION_TYPE);
                            String address = jsonObject.getString(AppGlobals.KEY_ADDRESS);
                            String location = jsonObject.getString(AppGlobals.KEY_LOCATION);

                            String chatStatus = jsonObject.getString(AppGlobals.KEY_CHAT_STATUS);
                            String state = jsonObject.getString(AppGlobals.KEY_STATE);
                            String city = jsonObject.getString(AppGlobals.KEY_CITY);
                            String docId = jsonObject.getString(AppGlobals.KEY_DOC_ID);
                            String collegeId = jsonObject.getString(AppGlobals.KEY_COLLEGE_ID);
                            String showNews = jsonObject.getString(AppGlobals.KEY_SHOW_NEWS);

                            String showNotification = jsonObject.getString(AppGlobals.KEY_SHOW_NOTIFICATION);
                            String consultationTime = jsonObject.getString(AppGlobals.KEY_CONSULTATION_TIME);
                            String reviewStars = jsonObject.getString(AppGlobals.KEY_REVIEW_STARS);
                            String imageUrl = jsonObject.getString(AppGlobals.KEY_IMAGE_URL);


                            //saving values
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_USER_ID, userId);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_FIRST_NAME, firstName);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_LAST_NAME, lastName);

                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_GENDER, gender);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_DATE_OF_BIRTH, dateOfBirth);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_PHONE_NUMBER_PRIMARY, phoneNumberPrimary);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_PHONE_NUMBER_SECONDARY, phoneNumberSecondary);

                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_AFFILIATE_CLINIC, affiliateClinic);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_SUBSCRIPTION_TYPE, subscriptionType);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_ADDRESS, address);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_LOCATION, location);

                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_CHAT_STATUS, chatStatus);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_STATE, state);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_CITY, city);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_DOC_ID, docId);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_SHOW_NEWS, showNews);

                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_SHOW_NOTIFICATION, showNotification);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_CONSULTATION_TIME, consultationTime);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_REVIEW_STARS, reviewStars);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.KEY_COLLEGE_ID, collegeId);
                            AppGlobals.saveDataToSharedPreferences(AppGlobals.SERVER_PHOTO_URL, imageUrl);
                            Log.i("Emergency Contact", " " + AppGlobals.getStringFromSharedPreferences(AppGlobals.KEY_EMERGENCY_CONTACT));
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
        double progress = (loaded/(double)total) * 100;
        Log.i("current progress", "" +(int) progress);
        donutProgress.setProgress((int) progress);

    }
}
