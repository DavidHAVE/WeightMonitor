package com.weightmonitorid.android.weightmonitor;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.weightmonitorid.android.weightmonitor.helper.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView mName1TextView, mName2TextView, mName3TextView, mName4TextView, mName5TextView, mPercentage1TextView, mPercentage2TextView,
            mPercentage3TextView, mPercentage4TextView, mPercentage5TextView, mWeight1TextView, mWeight2TextView, mWeight3TextView,
            mHeight4TextView, mHeight5TextView;
    private ImageView mStatus1ImageView, mStatus2ImageView, mStatus3ImageView, mStatus4ImageView, mStatus5ImageView;

    private ProgressDialog loadingDialog;

    private int notificationId;

    private String usernameaio, aiokey;
    private String goodsName1, goodsName2, goodsName3, goodsName4, goodsName5,
            weight1Value, weight2Value, weight3Value, weight4Value, weight5Value;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName1TextView = (TextView) findViewById(R.id.name1_text_view);
        mName2TextView = (TextView) findViewById(R.id.name2_text_view);
        mName3TextView = (TextView) findViewById(R.id.name3_text_view);
        mName4TextView = (TextView) findViewById(R.id.name4_text_view);
        mName5TextView = (TextView) findViewById(R.id.name5_text_view);
        mPercentage1TextView = (TextView) findViewById(R.id.percentage1_text_view);
        mPercentage2TextView = (TextView) findViewById(R.id.percentage2_text_view);
        mPercentage3TextView = (TextView) findViewById(R.id.percentage3_text_view);
        mPercentage4TextView = (TextView) findViewById(R.id.percentage4_text_view);
        mPercentage5TextView = (TextView) findViewById(R.id.percentage5_text_view);
        mWeight1TextView = (TextView) findViewById(R.id.weight1_text_view);
        mWeight2TextView = (TextView) findViewById(R.id.weight2_text_view);
        mWeight3TextView = (TextView) findViewById(R.id.weight3_text_view);
        mHeight4TextView = (TextView) findViewById(R.id.height4_text_view);
        mHeight5TextView = (TextView) findViewById(R.id.height5_text_view);
        mStatus1ImageView = (ImageView) findViewById(R.id.status1_image_view);
        mStatus2ImageView = (ImageView) findViewById(R.id.status2_image_view);
        mStatus3ImageView = (ImageView) findViewById(R.id.status3_image_view);
        mStatus4ImageView = (ImageView) findViewById(R.id.status4_image_view);
        mStatus5ImageView = (ImageView) findViewById(R.id.status5_image_view);

        getSharedPreferences();

        mName1TextView.setOnLongClickListener(this);
        mName2TextView.setOnLongClickListener(this);
        mName3TextView.setOnLongClickListener(this);
        mName4TextView.setOnLongClickListener(this);
        mName5TextView.setOnLongClickListener(this);
        mStatus1ImageView.setOnClickListener(this);
        mStatus2ImageView.setOnClickListener(this);
        mStatus3ImageView.setOnClickListener(this);
        mStatus4ImageView.setOnClickListener(this);
        mStatus5ImageView.setOnClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        if (v == mName1TextView) {
            Intent intent = new Intent(MainActivity.this, EditNameActivity.class);
            intent.putExtra(Constants.GOODS, "1");
            startActivity(intent);
        } else if (v == mName2TextView) {
            Intent intent = new Intent(MainActivity.this, EditNameActivity.class);
            intent.putExtra(Constants.GOODS, "2");
            startActivity(intent);
        } else if (v == mName3TextView) {
            Intent intent = new Intent(MainActivity.this, EditNameActivity.class);
            intent.putExtra(Constants.GOODS, "3");
            startActivity(intent);
        } else if (v == mName4TextView) {
            Intent intent = new Intent(MainActivity.this, EditNameActivity.class);
            intent.putExtra(Constants.GOODS, "4");
            startActivity(intent);
        } else if (v == mName5TextView) {
            Intent intent = new Intent(MainActivity.this, EditNameActivity.class);
            intent.putExtra(Constants.GOODS, "5");
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == mStatus1ImageView) {
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
            intent.putExtra(Constants.STATISTICS, "1");
            startActivity(intent);
        } else if (v == mStatus2ImageView) {
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
            intent.putExtra(Constants.STATISTICS, "2");
            startActivity(intent);
        } else if (v == mStatus3ImageView) {
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
            intent.putExtra(Constants.STATISTICS, "3");
            startActivity(intent);
        } else if (v == mStatus4ImageView) {
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
            intent.putExtra(Constants.STATISTICS, "4");
            startActivity(intent);
        } else if (v == mStatus5ImageView) {
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
            intent.putExtra(Constants.STATISTICS, "5");
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setCanceledOnTouchOutside(true);
        loadingDialog.setCancelable(true);
        loadingDialog.setMessage("Please Wait...");
        loadingDialog.show();

        requestQueue = Volley.newRequestQueue(this);

        getLastData1();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh_action_menu) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setCanceledOnTouchOutside(true);
            loadingDialog.setCancelable(false);
            loadingDialog.setMessage("Please Wait...");
            loadingDialog.show();

            requestQueue = Volley.newRequestQueue(this);

            getLastData1();
        } else if (id == R.id.settings_action_menu) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        usernameaio = sharedPreferences.getString("usernameaio", "1");
        aiokey = sharedPreferences.getString("aiokey", "1");
        Log.e("MainActivity", "getSharedPref : AIOUsername ="+usernameaio+", AIOKey ="+aiokey);

        goodsName1 = sharedPreferences.getString(Constants.GOODS_KEY_1, "Barang 1");
        goodsName2 = sharedPreferences.getString(Constants.GOODS_KEY_2, "Barang 2");
        goodsName3 = sharedPreferences.getString(Constants.GOODS_KEY_3, "Barang 3");
        goodsName4 = sharedPreferences.getString(Constants.GOODS_KEY_4, "Barang 4");
        goodsName5 = sharedPreferences.getString(Constants.GOODS_KEY_5, "Barang 5");
        mName1TextView.setText(goodsName1);
        mName2TextView.setText(goodsName2);
        mName3TextView.setText(goodsName3);
        mName4TextView.setText(goodsName4);
        mName5TextView.setText(goodsName5);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void getLastData1() {
        getLastData1FromServer().setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(getLastData1FromServer());
    }
    private JsonObjectRequest getLastData1FromServer() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.READ_LAST_DATA_URL + usernameaio + "/feeds/goods1/data/last/", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            weight1Value = response.getString("value");
                            changeStatus(0, weight1Value);
                            Log.e("MainActivity", "value1 ="+weight1Value);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        getLastData2();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getLastData2();
                        Log.e("MainActivity", "Cannot Load Data 1");
//                        Toast.makeText(MainActivity.this, "Cannot Load Data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("X-AIO-Key", aiokey);
                headers.put("feed_key", "goods1");
                return headers;
            }
        };
        return jsonObjectRequest;
    }


    private void getLastData2() {
        getLastData2FromServer().setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(getLastData2FromServer());
    }
    private JsonObjectRequest getLastData2FromServer() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.READ_LAST_DATA_URL + usernameaio + "/feeds/goods2/data/last/", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            weight2Value = response.getString("value");
                            changeStatus(1, weight2Value);
                            Log.e("MainActivity", "value2 ="+weight2Value);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        getLastData3();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getLastData3();
                        Log.e("MainActivity", "Cannot Load Data 2");
//                        Toast.makeText(MainActivity.this, "Cannot Load Data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("X-AIO-Key", aiokey);
                headers.put("feed_key", "goods2");
                return headers;
            }
        };
        return jsonObjectRequest;
    }

    private void getLastData3() {
        getLastData3FromServer().setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(getLastData3FromServer());
    }

    private JsonObjectRequest getLastData3FromServer() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.READ_LAST_DATA_URL + usernameaio + "/feeds/goods3/data/last/", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            weight3Value = response.getString("value");
                            changeStatus(2, weight3Value);
                            Log.e("MainActivity", "value3 ="+weight3Value);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        getLastData4();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getLastData4();
                        Log.e("MainActivity", "Cannot Load Data 3");
//                        Toast.makeText(MainActivity.this, "Cannot Load Data", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("X-AIO-Key", aiokey);
                headers.put("feed_key", "goods3");
                return headers;
            }
        };
        return jsonObjectRequest;
    }

    private void getLastData4() {
        getLastData1FromServer().setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(getLastData4FromServer());
    }
    private JsonObjectRequest getLastData4FromServer() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.READ_LAST_DATA_URL + usernameaio + "/feeds/goods4/data/last/", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            weight4Value = response.getString("value");
                            changeStatus(3, weight4Value);
                            Log.e("MainActivity", "value4 ="+weight4Value);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        getLastData5();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getLastData5();
                        Log.e("MainActivity", "Cannot Load Data 4");
//                        Toast.makeText(MainActivity.this, "Cannot Load Data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("X-AIO-Key", aiokey);
                headers.put("feed_key", "goods4");
                return headers;
            }
        };
        return jsonObjectRequest;
    }

    private void getLastData5() {
        getLastData5FromServer().setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(getLastData5FromServer());
    }
    private JsonObjectRequest getLastData5FromServer() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.READ_LAST_DATA_URL + usernameaio + "/feeds/goods5/data/last/", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            weight5Value = response.getString("value");
                            changeStatus(4, weight5Value);
                            Log.e("MainActivity", "value5 ="+weight5Value);
                            loadingDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismiss();
                        Log.e("MainActivity", "Cannot Load Data 5");
//                        Toast.makeText(MainActivity.this, "Cannot Load Data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("X-AIO-Key", aiokey);
                headers.put("feed_key", "goods4");
                return headers;
            }
        };
        return jsonObjectRequest;
    }

    private void changeStatus(int goodsType, String value) {
        //Berat
        if (goodsType == 0) {
            float goodsValue = Float.parseFloat(value) / 1000; //gram to kg
            String goodsValueString = String.valueOf(goodsValue);

//            float calculation1 = 3 / goodsValue;
            float percentageValue = (float) Math.round(100 * goodsValue);
            String percentageValueString = String.valueOf(percentageValue);
            if (goodsValue <= 0){
                mWeight1TextView.setText(goodsValueString);
                mPercentage1TextView.setText(percentageValueString);
                mStatus1ImageView.setImageResource(R.drawable.status5);
                notificationId = notificationId + 1;
                sendMyNotification("Peringatan Berat", "Berat "+goodsName1+" 0 %.");
            } else if (goodsValue <= 0.25){
                mWeight1TextView.setText(goodsValueString);
                mPercentage1TextView.setText(percentageValueString);
                mStatus1ImageView.setImageResource(R.drawable.status4);
                notificationId = notificationId + 1;
                sendMyNotification("Peringatan Berat", "Berat "+goodsName1+" dibawah 25 %.");
            } else if (goodsValue <= 0.50) {
                mWeight1TextView.setText(goodsValueString);
                mPercentage1TextView.setText(percentageValueString);
                mStatus1ImageView.setImageResource(R.drawable.status3);
            } else if (goodsValue <= 0.75) {
                mWeight1TextView.setText(goodsValueString);
                mPercentage1TextView.setText(percentageValueString);
                mStatus1ImageView.setImageResource(R.drawable.status2);
            } else if (goodsValue <= 1.00 || goodsValue > 1.00) {
                mWeight1TextView.setText(goodsValueString);
                mPercentage1TextView.setText(percentageValueString);
                mStatus1ImageView.setImageResource(R.drawable.status1);
            }
        } else if (goodsType == 1) {
            float goodsValue = Float.parseFloat(value) / 1000; //gram to kg
            String goodsValueString = String.valueOf(goodsValue);

//            float calculation1 = 3 / goodsValue;
            float percentageValue = (float) Math.round(100 * goodsValue);
            String percentageValueString = String.valueOf(percentageValue);
            if (goodsValue <= 0){
                mWeight2TextView.setText(goodsValueString);
                mPercentage2TextView.setText(percentageValueString);
                mStatus2ImageView.setImageResource(R.drawable.status5);
                notificationId = notificationId + 1;
                sendMyNotification("Peringatan Berat", "Berat "+goodsName2+" 0 %.");
            } else if (goodsValue <= 0.25){
                mWeight2TextView.setText(goodsValueString);
                mPercentage2TextView.setText(percentageValueString);
                mStatus2ImageView.setImageResource(R.drawable.status4);
                notificationId = notificationId + 1;
                sendMyNotification("Peringatan Berat", "Berat "+goodsName2+" dibawah 25 %.");
            } else if (goodsValue <= 0.50) {
                mWeight2TextView.setText(goodsValueString);
                mPercentage2TextView.setText(percentageValueString);
                mStatus2ImageView.setImageResource(R.drawable.status3);
            } else if (goodsValue <= 0.75) {
                mWeight2TextView.setText(goodsValueString);
                mPercentage2TextView.setText(percentageValueString);
                mStatus2ImageView.setImageResource(R.drawable.status2);
            } else if (goodsValue <= 1.00 || goodsValue > 1.00) {
                mWeight2TextView.setText(goodsValueString);
                mPercentage2TextView.setText(percentageValueString);
                mStatus2ImageView.setImageResource(R.drawable.status1);
            }
        } else if (goodsType == 2) {
            float goodsValue = Float.parseFloat(value) / 1000; //gram to kg
            String goodsValueString = String.valueOf(goodsValue);

//            float calculation1 = 3 / goodsValue;
            float percentageValue = (float) Math.round(100 * goodsValue);
            String percentageValueString = String.valueOf(percentageValue);
            if (goodsValue <= 0){
                mWeight3TextView.setText(goodsValueString);
                mPercentage3TextView.setText(percentageValueString);
                mStatus3ImageView.setImageResource(R.drawable.status5);
                notificationId = notificationId + 1;
                sendMyNotification("Peringatan Berat", "Berat "+goodsName3+" 0 %.");
            } else if (goodsValue <= 0.25){
                mWeight3TextView.setText(goodsValueString);
                mPercentage3TextView.setText(percentageValueString);
                mStatus3ImageView.setImageResource(R.drawable.status4);
                notificationId = notificationId + 1;
                sendMyNotification("Peringatan Berat", "Berat "+goodsName3+" dibawah 25 %.");
            } else if (goodsValue <= 0.50) {
                mWeight3TextView.setText(goodsValueString);
                mPercentage3TextView.setText(percentageValueString);
                mStatus3ImageView.setImageResource(R.drawable.status3);
            } else if (goodsValue <= 0.75) {
                mWeight3TextView.setText(goodsValueString);
                mPercentage3TextView.setText(percentageValueString);
                mStatus3ImageView.setImageResource(R.drawable.status2);
            } else if (goodsValue <= 1.00 || goodsValue > 1.00) {
                mWeight3TextView.setText(value);
                mPercentage3TextView.setText(percentageValueString);
                mStatus3ImageView.setImageResource(R.drawable.status1);
            }


            //Tinggi
        } else if (goodsType == 3) {
            float goodsValue = Float.parseFloat(value) / 100; //cm to m

            float percentageValue = (float) Math.round(1000 * goodsValue / 2);
            String percentageValueString = String.valueOf(percentageValue);
            if (goodsValue <= 0){
                mHeight4TextView.setText(String.valueOf(goodsValue));
                mPercentage4TextView.setText(percentageValueString);
                mStatus4ImageView.setImageResource(R.drawable.status5);
                notificationId = notificationId + 1;
                sendMyNotification("Peringatan Tinggi", "Tinggi "+goodsName4+" berada di 0 %.");
            } else if (goodsValue <= 0.05){
                mHeight4TextView.setText(String.valueOf(goodsValue));
                mPercentage4TextView.setText(percentageValueString);
                mStatus4ImageView.setImageResource(R.drawable.status4);
                notificationId = notificationId + 1;
                sendMyNotification("Peringatan Tinggi", "Tinggi "+goodsName4+" dibawah 25 %.");
            } else if (goodsValue <= 0.10) {
                mHeight4TextView.setText(String.valueOf(goodsValue));
                mPercentage4TextView.setText(percentageValueString);
                mStatus4ImageView.setImageResource(R.drawable.status3);
            } else if (goodsValue <= 0.15) {
                mHeight4TextView.setText(String.valueOf(goodsValue));
                mPercentage4TextView.setText(percentageValueString);
                mStatus4ImageView.setImageResource(R.drawable.status2);
            } else if (goodsValue <= 0.20 || goodsValue > 1.00) {
                mHeight4TextView.setText(String.valueOf(goodsValue));
                mPercentage4TextView.setText(percentageValueString);
                mStatus4ImageView.setImageResource(R.drawable.status1);
            }
        } else if (goodsType == 4) {
            float goodsValue = Float.parseFloat(value) / 100; //cm to m

            float percentageValue = (float) Math.round(1000 * goodsValue / 2);
            String percentageValueString = String.valueOf(percentageValue);
            if (goodsValue <= 0){
                mHeight5TextView.setText(String.valueOf(goodsValue));
                mPercentage5TextView.setText(percentageValueString);
                mStatus5ImageView.setImageResource(R.drawable.status5);
                notificationId = notificationId + 1;
                sendMyNotification("Peringatan Tinggi", "Tinggi "+goodsName5+" berada di 0 %.");
            } else if (goodsValue <= 0.05){
                mHeight5TextView.setText(String.valueOf(goodsValue));
                mPercentage5TextView.setText(percentageValueString);
                mStatus5ImageView.setImageResource(R.drawable.status4);
                notificationId = notificationId + 1;
                sendMyNotification("Peringatan Tinggi", "Tinggi "+goodsName5+" dibawah 25 %.");
            } else if (goodsValue <= 0.10) {
                mHeight5TextView.setText(String.valueOf(goodsValue));
                mPercentage5TextView.setText(percentageValueString);
                mStatus5ImageView.setImageResource(R.drawable.status3);
            } else if (goodsValue <= 0.15) {
                mHeight5TextView.setText(String.valueOf(goodsValue));
                mPercentage5TextView.setText(percentageValueString);
                mStatus5ImageView.setImageResource(R.drawable.status2);
            } else if (goodsValue <= 0.20 || goodsValue > 1.00) {
                mHeight5TextView.setText(String.valueOf(goodsValue));
                mPercentage5TextView.setText(percentageValueString);
                mStatus5ImageView.setImageResource(R.drawable.status1);
            }
        }
        loadingDialog.dismiss();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        usernameaio = sharedPreferences.getString("usernameaio", "1");
        aiokey = sharedPreferences.getString("aiokey", "1");
        Log.e("MainActivity", "OnsharedPref : AIOUsername ="+usernameaio+", AIOKey ="+aiokey);
        goodsName1 = sharedPreferences.getString(Constants.GOODS_KEY_1, "Barang 1");
        goodsName2 = sharedPreferences.getString(Constants.GOODS_KEY_2, "Barang 2");
        goodsName3 = sharedPreferences.getString(Constants.GOODS_KEY_3, "Barang 3");
        goodsName4 = sharedPreferences.getString(Constants.GOODS_KEY_4, "Barang 4");
        goodsName5 = sharedPreferences.getString(Constants.GOODS_KEY_5, "Barang 5");
        mName1TextView.setText(goodsName1);
        mName2TextView.setText(goodsName2);
        mName3TextView.setText(goodsName3);
        mName4TextView.setText(goodsName4);
        mName5TextView.setText(goodsName5);
    }

    private void sendMyNotification(String title, String message) {
        Log.e("MainActivity", "notificationId ="+notificationId);
        String notificationIdString = String.valueOf(notificationId);

        //On click of notification it redirect to this Activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            notificationIdString = "id_notifikasi";
            // The user-visible name of the channel.
            CharSequence name = "Notifikasi";
            // The user-visible description of the channel.
            String description = "Notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(notificationIdString, name, importance);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(mChannel);

            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 123, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),"id_product")
                    .setSmallIcon(R.drawable.scales_96) //your app icon
                    .setBadgeIconType(R.drawable.scales_96) //your app icon
                    .setChannelId(notificationIdString)
                    .setContentTitle(title)
                    .setAutoCancel(true).setContentIntent(pendingIntent)
                    .setNumber(1)
                    .setColor(255)
                    .setContentText(message)
                    .setWhen(System.currentTimeMillis());
            notificationManager.notify(notificationId, notificationBuilder.build());

        }else{

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.scales_96)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(soundUri)
                    .setContentIntent(pendingIntent2);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId, notificationBuilder.build());
        }

    }
    public void cancelNotification() {
        String ns = NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(ns);
        notificationManager.cancel(1);
    }

}
