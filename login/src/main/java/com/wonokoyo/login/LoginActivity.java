package com.wonokoyo.login;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wonokoyo.login.serveraccess.RetrofitInstance;
import com.wonokoyo.login.util.BiometricCallback;
import com.wonokoyo.login.util.BiometricManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements BiometricCallback {

    BiometricManager mBiometricManager;

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private ImageView ivFP;

    TelephonyManager manager;

    private String idUser, namaUser, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 200);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imei = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                loginUser(etUsername.getText().toString(), etPassword.getText().toString(), imei);
            }
        });

        ivFP = findViewById(R.id.ivFP);
        ivFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBiometricManager = new BiometricManager.BiometricBuilder(LoginActivity.this)
                        .setTitle(getString(R.string.biometric_title))
                        .setSubtitle(getString(R.string.biometric_subtitle))
                        .setDescription(getString(R.string.biometric_description))
                        .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
                        .build();

                //start authentication
                mBiometricManager.authenticate(LoginActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public void onSdkVersionNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_failure), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationCancelled() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_cancelled), Toast.LENGTH_LONG).show();
        mBiometricManager.cancelAuthentication();
    }

    @Override
    public void onAuthenticationSuccessful() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_success), Toast.LENGTH_SHORT).show();
        String imei = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        loginUser("", "", imei);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_LONG).show();
    }

    public void loginUser(String username, String password, String deviceId) {
        Call<ResponseBody> call = RetrofitInstance.getLoginService().login(username, password, deviceId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body.string());
                        if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                            idUser = jsonObject.getString("id_user");
                            namaUser = jsonObject.getString("nama_user");
                            message = jsonObject.getString("message");

                            resultLogin(true);
                        } else {
                            idUser = "";
                            namaUser = "";
                            message = jsonObject.getString("message");

                            resultLogin(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                idUser = "";
                namaUser = "";
                message = "";
            }
        });
    }

    public void resultLogin(Boolean isLogin) {
        if (isLogin) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

            Intent returnIntent = new Intent();
            returnIntent.putExtra("id_user", idUser);
            returnIntent.putExtra("nama_user", namaUser);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
