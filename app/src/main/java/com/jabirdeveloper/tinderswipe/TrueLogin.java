package com.jabirdeveloper.tinderswipe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class TrueLogin extends AppCompatActivity {
    EditText username,password;
    Button sigin;
    DBHelper DB;
    ProgressBar progressBar;
    private boolean isConnected(TrueLogin trueLogin)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) trueLogin.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiCon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobiCon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifiCon != null && wifiCon.isConnected()) || (mobiCon != null && mobiCon.isConnected()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private void showCustomDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(TrueLogin.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });
        builder.show();
    }
    private void showCustomDialog(String s)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(TrueLogin.this);
        builder.setMessage("Fill up all details.")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_login);
        username = findViewById(R.id.username_l);
        password = findViewById(R.id.password_l);
        DB = new DBHelper(this);
        sigin = findViewById(R.id.loginbtn_l);
        LoadingDialog loadingDialog = new LoadingDialog(TrueLogin.this);
        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected(TrueLogin.this))
                {
                    showCustomDialog();
                }
                else
                {
                    String user = username.getText().toString();
                    String pass = password.getText().toString();

                    if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass))

                        Toast.makeText(TrueLogin.this, "All fields are required to be filled", Toast.LENGTH_SHORT).show();
                    else {
                        Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                        if ((checkuserpass == true)) {
                            Toast.makeText(TrueLogin.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            loadingDialog.startLoadingDialog();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadingDialog.dismissDialog();
                                }
                            }, 5000);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(TrueLogin.this, "New User\n Sign up ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }
}