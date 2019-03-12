package com.weightmonitorid.android.weightmonitor;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.weightmonitorid.android.weightmonitor.helper.Constants;
import com.weightmonitorid.android.weightmonitor.helper.DatabaseHelper;
import com.weightmonitorid.android.weightmonitor.model.Weight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsActivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {

    DatabaseHelper db;
    private LineChart mLineChart;
    private ProgressDialog loadingDialog;
    private String statstics;

    private List<Weight> dataThisMonthList, dataNextMonthList, weights;

    private int flag = 0;
    private float highestWeight;
    private String typeGoods;
    private String usernameaio, aiokey;
    private String dateStr;
    private String goodsName1, goodsName2, goodsName3, goodsName4, goodsName5,
            weight1Value, weight2Value, weight3Value, weight4Value, weight5Value;
    private RequestQueue requestQueue;
    private String createdAt;
    private long totalDataThisMonth, totalDataNextMonth, weightValue;
    private long highestValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        if (getIntent() != null) {
            statstics = getIntent().getExtras().getString(Constants.STATISTICS);
            if (statstics.equals("1")) {
                typeGoods = "goods1";
            } else if (statstics.equals("2")) {
                typeGoods = "goods2";
            } else if (statstics.equals("3")) {
                typeGoods = "goods3";
            } else if (statstics.equals("4")) {
                typeGoods = "goods4";
            } else if (statstics.equals("5")) {
                typeGoods = "goods5";
            }
        }


        mLineChart = (LineChart) findViewById(R.id.line_chart);
        mLineChart.setOnChartGestureListener(this);
        mLineChart.setOnChartValueSelectedListener(this);
        mLineChart.setDrawGridBackground(false);

        // add data
//        setData();

        // get the legend (only possible after setting data)
//        Legend l = mLineChart.getLegend();
//
//        // modify the legend ...
//        // l.setPosition(LegendPosition.LEFT_OF_CHART);
//        l.setForm(Legend.LegendForm.LINE);
//
//        // no description text
//        mLineChart.setDescription("Demo Line Chart");
//        mLineChart.setNoDataTextDescription("You need to provide data for the chart.");
//
//        // enable touch gestures
//        mLineChart.setTouchEnabled(true);
//
//        // enable scaling and dragging
//        mLineChart.setDragEnabled(true);
//        mLineChart.setScaleEnabled(true);
//        // mChart.setScaleXEnabled(true);
//        // mChart.setScaleYEnabled(true);
//
////        LimitLine upper_limit = new LimitLine(130f, "Upper Limit");
////        upper_limit.setLineWidth(4f);
////        upper_limit.enableDashedLine(10f, 10f, 0f);
////        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
////        upper_limit.setTextSize(10f);
////
////        LimitLine lower_limit = new LimitLine(-30f, "Lower Limit");
////        lower_limit.setLineWidth(4f);
////        lower_limit.enableDashedLine(10f, 10f, 0f);
////        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
////        lower_limit.setTextSize(10f);
//
//        YAxis leftAxis = mLineChart.getAxisLeft();
//        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
////        leftAxis.addLimitLine(upper_limit);
////        leftAxis.addLimitLine(lower_limit);
////        if(statstics.equals("1") || statstics.equals("2") || statstics.equals("3")) {
////            leftAxis.setAxisMaxValue(3000f);
////            leftAxis.setAxisMinValue(0f);
////        }else if (statstics.equals("4") || statstics.equals("5")){
////            leftAxis.setAxisMaxValue(100f);
////            leftAxis.setAxisMinValue(0f);
////        }
//        //leftAxis.setYOffset(20f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
//        leftAxis.setDrawZeroLine(false);
//
//        // limit lines are drawn behind data (and not on top)
//        leftAxis.setDrawLimitLinesBehindData(true);
//
//        mLineChart.getAxisRight().setEnabled(false);
//
//        //mChart.getViewPortHandler().setMaximumScaleY(2f);
//        //mChart.getViewPortHandler().setMaximumScaleX(2f);
//
//        mLineChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
//
//        //  dont forget to refresh the drawing
//        mLineChart.invalidate();


        db = new DatabaseHelper(getApplicationContext());

        getSharedPreferences();

        dataThisMonthList = new ArrayList<>();
        dataNextMonthList = new ArrayList<>();
        weights = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setCanceledOnTouchOutside(true);
        loadingDialog.setCancelable(true);
        loadingDialog.setMessage("Please Wait...");
        loadingDialog.show();

        requestQueue = Volley.newRequestQueue(this);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);
        Log.e("StatisticActivity", "formatDate =" + formattedDate);


        String filename = "abc.def";
        String start = filename.substring(0, filename.indexOf(".")); // returns "abc"
        Log.e("StatisticActivity", "start =" + start);

        getAllData();

//        // add data
//        setData();
    }

    private void getSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        usernameaio = sharedPreferences.getString("usernameaio", "1");
        aiokey = sharedPreferences.getString("aiokey", "1");
    }

    private void getAllData() {
        getAllDataFromServer().setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(getAllDataFromServer());
    }

    private JsonArrayRequest getAllDataFromServer() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constants.READ_LAST_DATA_URL + usernameaio + "/feeds/" + typeGoods + "/data",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseData(response);
                        Log.e("StatisticActivity", "response = " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MainActivity", "error");
                        loadingDialog.dismiss();
                        Toast.makeText(StatisticsActivity.this, "Cannot Load Data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("X-AIO-Key", aiokey);
                return headers;
            }
        };
        return jsonArrayRequest;
    }

    private void parseData(JSONArray array) {
//        for (int i = 0; i < array.length(); i++) {
//            JSONObject json = null;
//            try {
//                json = array.getJSONObject(i);
//                name = json.getString("name");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            if (name.equals("switch1") || name.equals("switch2")) {
//                nameList.add(name);
//            }
//        }
//        if(nameList.size() == 0) {
//            addFeed1ToServer();
//        }else if (!nameList.get(0).equals("switch1")){
//            addFeed1ToServer();
//        }else if (!nameList.get(0).equals("switch2")){
//            addFeed2ToServer();
//        }
//
//        for (int i = 0; i < nameList.size(); i++) {
//            Log.e("MainActivity", "name :"+nameList.get(i));
//            if (nameList.size() > 0 && nameList.get(i).equals("switch1")) {
//                getLastData(i);
//            } else if (nameList.size() > 0 && nameList.get(i).equals("switch2")) {
//                getLastData(i);
//            }
//        }

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        String dateNow = dateFormat.format(date);
        Log.e("StatisticActivity", "dateNow =" + dateNow);

        Date dateAsObj = null;
        try {
            dateAsObj = dateFormat.parse(dateNow);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateAsObj);
        cal.add(Calendar.MONTH, 1);
        Date dateAsObjAfterAMonth = cal.getTime();
        String nextMonthDate = dateFormat.format(dateAsObjAfterAMonth);
//        String nextMonthDate = dateAsObj.toString();
//        nextMonthDate = dateFormat.format(nextMonthDate);

        Log.e("StatisticActivity", "nextMonthDate =" + nextMonthDate);


        String filename = "abc.def";
        String start = filename.substring(0, filename.indexOf(".")); // returns "abc"
        Log.e("StatisticActivity", "start =" + start);

        Weight weight1, weight2;
        for (int i = 0; i < array.length(); i++) {
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                createdAt = json.getString("created_at");
//                createdAt = createdAt.substring(0, filename.indexOf("T"));


//                DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//                Date date2 = null;
//                try {
//                    date2 = dateFormat.parse(createdAt);//You will get date object relative to server/client timezone wherever it is parsed
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                DateFormat formatter = new SimpleDateFormat("MM"); //If you need time just put specific format for time like 'HH:mm:ss'
//
//                dateStr = formatter.format(date2);



                ////
//                String date33 = "2018-01-09T11:11:02.0+03:00";
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                SimpleDateFormat output = new SimpleDateFormat("MM");
                Date d = null;
                try {
                    d = dateformat.parse(createdAt);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dateStr = output.format(d);

//                Log.d("Date format", "output date :" + formattedDate);
                //////

                Log.e("StatisticsActivity", "dateStr = " + dateStr +", dateNow = "+dateNow);

                String weightValueString = json.getString("value");
                weightValue = Long.parseLong(weightValueString);
                Log.e("StatisticsActivity", "createdAt = " + createdAt+", value = "+weightValue);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (dateStr.equals(dateNow)) {
                dataThisMonthList.clear();
                totalDataThisMonth += weightValue;
                weight1 = new Weight(dateStr, totalDataThisMonth);
                dataThisMonthList.add(weight1);
            } else if (dateStr.equals(nextMonthDate)) {
                dataNextMonthList.clear();
                totalDataNextMonth += weightValue;
                weight2 = new Weight(dateStr, totalDataNextMonth);
                dataNextMonthList.add(weight2);
            }

            if (totalDataThisMonth > totalDataNextMonth) {
                highestValue = totalDataThisMonth;
            }else if (totalDataThisMonth < totalDataNextMonth ){
                highestValue = totalDataNextMonth;
            }

            flag += 1;
            Log.e("StatisticsActivity", "flag = " + flag +", array.length() = "+array.length());


        }

//        Food food = new Food("Masakan 1", "Intro 1", "Ingredient 1", "Make 1", R.drawable.satu, 1);
//        foods.add(food);

        if (typeGoods.equals("goods1")) {
            int dataCount = db.getWeight1Count();
            if (dataCount < 1) {
                if (dataThisMonthList.size() != 0) {
                    long stat_id = db.insertWeight1(dataThisMonthList);
                    Log.e("StatisticActivity", "insert1, stat_id : " + stat_id);
                    loadingDialog.dismiss();
                    db.closeDB();
                }

                if (dataNextMonthList.size() != 0) {
                    long stat_id = db.insertWeight1(dataNextMonthList);
                    Log.e("StatisticActivity", "insert2, stat_id : " + stat_id);
                    loadingDialog.dismiss();
                    db.closeDB();
                }
            } else {
                if (dataThisMonthList.size() != 0) {
//                    int id = dataThisMonthList.get(0).getId();
                    int id = 1;
                    String datee = dataThisMonthList.get(0).getDate();
                    Long weightt = dataThisMonthList.get(0).getWeight();
                    Weight weight = new Weight(id, datee, weightt);
                    long stat_id = db.updateWeight1(weight);
                    Log.e("StatisticActivity", "ID : " + id+", datee ="+datee+", weightt ="+weightt);
                    Log.e("StatisticActivity", "update1, stat_id : " + stat_id);
                    db.closeDB();
                }
                if (dataNextMonthList.size() != 0) {
//                    int id = dataThisMonthList.get(0).getId();
                    int id = 1;
                    String datee = dataNextMonthList.get(0).getDate();
                    Long weightt = dataNextMonthList.get(0).getWeight();
                    Weight weight = new Weight(id, datee, weightt);
                    long stat_id = db.updateWeight1(weight);
                    Log.e("StatisticActivity", "update2, stat_id : " + stat_id);
                    db.closeDB();
                }
            }

            Log.e("MainActivity", "highestValue ="+highestValue);
            weights = db.getAllWeight1();

        } else if (typeGoods.equals("goods2")) {
            int dataCount = db.getWeight2Count();
            if (dataCount < 1) {
                if (dataThisMonthList.size() != 0) {
                    long stat_id = db.insertWeight2(dataThisMonthList);
                    Log.e("StatisticActivity", "stat_id : " + stat_id);
                    loadingDialog.dismiss();
                    db.closeDB();
                }

                if (dataNextMonthList.size() != 0) {
                    long stat_id = db.insertWeight2(dataNextMonthList);
                    Log.e("StatisticActivity", "fstat_id : " + stat_id);
                    loadingDialog.dismiss();
                    db.closeDB();
                }
            } else {
                if (dataThisMonthList.size() != 0) {
                    int id = 1;
                    String datee = dataThisMonthList.get(0).getDate();
                    Long weightt = dataThisMonthList.get(0).getWeight();
                    Weight weight = new Weight(id, datee, weightt);
                    long stat_id = db.updateWeight2(weight);
                    db.closeDB();
                }
                if (dataNextMonthList.size() != 0) {
                    int id = 1;
                    String datee = dataNextMonthList.get(0).getDate();
                    Long weightt = dataNextMonthList.get(0).getWeight();
                    Weight weight = new Weight(id, datee, weightt);
                    long stat_id = db.updateWeight2(weight);
                    db.closeDB();
                }
            }

//            Weight weight = db.getHighestWeight2();
//            highestWeight = weight.getWeight();
//            Log.e("StatisticActivity", "highestWeight2 : " + highestWeight);
//            db.closeDB();

            weights = db.getAllWeight2();

        } else if (typeGoods.equals("goods3")) {
            int dataCount = db.getWeight3Count();
            if (dataCount < 1) {
                if (dataThisMonthList.size() != 0) {
                    long stat_id = db.insertWeight3(dataThisMonthList);
                    Log.e("StatisticActivity", "stat_id : " + stat_id);
                    loadingDialog.dismiss();
                    db.closeDB();
                }

                if (dataNextMonthList.size() != 0) {
                    long stat_id = db.insertWeight3(dataNextMonthList);
                    Log.e("StatisticActivity", "stat_id : " + stat_id);
                    loadingDialog.dismiss();
                    db.closeDB();
                }
            } else {
                if (dataThisMonthList.size() != 0) {
                    int id = 1;
                    String datee = dataThisMonthList.get(0).getDate();
                    Long weightt = dataThisMonthList.get(0).getWeight();
                    Weight weight = new Weight(id, datee, weightt);
                    long stat_id = db.updateWeight3(weight);
                    db.closeDB();
                }
                if (dataNextMonthList.size() != 0) {
                    int id = 1;
                    String datee = dataNextMonthList.get(0).getDate();
                    Long weightt = dataNextMonthList.get(0).getWeight();
                    Weight weight = new Weight(id, datee, weightt);
                    long stat_id = db.updateWeight3(weight);
                    db.closeDB();
                }
            }

//            Weight weight = db.getHighestWeight3();
//            highestWeight = weight.getWeight();
//            Log.e("StatisticActivity", "highestWeight3 : " + highestWeight);
//            db.closeDB();

            weights = db.getAllWeight3();

        } else if (typeGoods.equals("goods4")) {
            int dataCount = db.getWeight4Count();
            if (dataCount < 1) {
                if (dataThisMonthList.size() != 0) {
                    long stat_id = db.insertWeight4(dataThisMonthList);
                    Log.e("StatisticActivity", "stat_id : " + stat_id);
                    loadingDialog.dismiss();
                    db.closeDB();
                }

                if (dataNextMonthList.size() != 0) {
                    long stat_id = db.insertWeight4(dataNextMonthList);
                    Log.e("StatisticActivity", "stat_id : " + stat_id);
                    loadingDialog.dismiss();
                    db.closeDB();
                }
            } else {
                if (dataThisMonthList.size() != 0) {
                    int id = 1;
                    String datee = dataThisMonthList.get(0).getDate();
                    Long weightt = dataThisMonthList.get(0).getWeight();
                    Weight weight = new Weight(id, datee, weightt);
                    long stat_id = db.updateWeight4(weight);
                    db.closeDB();
                }
                if (dataNextMonthList.size() != 0) {
                    int id = 1;
                    String datee = dataNextMonthList.get(0).getDate();
                    Long weightt = dataNextMonthList.get(0).getWeight();
                    Weight weight = new Weight(id, datee, weightt);
                    long stat_id = db.updateWeight4(weight);
                    db.closeDB();
                }
            }

//            Weight weight = db.getHighestWeight4();
//            highestWeight = weight.getWeight();
//            Log.e("StatisticActivity", "highestWeight4 : " + highestWeight);
//            db.closeDB();

            weights = db.getAllWeight4();

        } else if (typeGoods.equals("goods5")) {
            int dataCount = db.getWeight5Count();
            if (dataCount < 1) {
                if (dataThisMonthList.size() != 0) {
                    long stat_id = db.insertWeight5(dataThisMonthList);
                    Log.e("StatisticActivity", "stat_id : " + stat_id);
                    loadingDialog.dismiss();
                    db.closeDB();
                }

                if (dataNextMonthList.size() != 0) {
                    long stat_id = db.insertWeight5(dataNextMonthList);
                    Log.e("StatisticActivity", "stat_id : " + stat_id);
                    loadingDialog.dismiss();
                    db.closeDB();
                }
            } else {
                if (dataThisMonthList.size() != 0) {
                    int id = 1;
                    String datee = dataThisMonthList.get(0).getDate();
                    Long weightt = dataThisMonthList.get(0).getWeight();
                    Weight weight = new Weight(id, datee, weightt);
                    long stat_id = db.updateWeight5(weight);
                    db.closeDB();
                }
                if (dataNextMonthList.size() != 0) {
                    int id = 1;
                    String datee = dataNextMonthList.get(0).getDate();
                    Long weightt = dataNextMonthList.get(0).getWeight();
                    Weight weight = new Weight(id, datee, weightt);
                    long stat_id = db.updateWeight5(weight);
                    db.closeDB();
                }
            }

//            Weight weight = db.getHighestWeight5();
//            highestWeight = weight.getWeight();
//            Log.e("StatisticActivity", "highestWeight5 : " + highestWeight);
//            db.closeDB();

            weights = db.getAllWeight5();

        }


//        for (int i = 0; i < nameList.size(); i++) {
//            Log.e("MainActivity", "name :"+nameList.get(i));
//            if (nameList.size() > 0 && nameList.get(i).equals("switch1")) {
//                getLastData(i);
//            } else if (nameList.size() > 0 && nameList.get(i).equals("switch2")) {
//                getLastData(i);
//            }
//        }
        // add data

//        Weight weight = db.getHighestWeight1();
//        highestWeight  = weight.getWeight();
//        Log.e("StatisticActivity", "highestWeight1 : " + highestWeight);
//        db.closeDB();

        // get the legend (only possible after setting data)
        Legend l = mLineChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // no description text
        mLineChart.setDescription("Total Data Perbulan");
        mLineChart.setNoDataTextDescription("Data for the chart is empty.");

        // enable touch gestures
        mLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

//        LimitLine upper_limit = new LimitLine(130f, "Upper Limit");
//        upper_limit.setLineWidth(4f);
//        upper_limit.enableDashedLine(10f, 10f, 0f);
//        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        upper_limit.setTextSize(10f);
//
//        LimitLine lower_limit = new LimitLine(-30f, "Lower Limit");
//        lower_limit.setLineWidth(4f);
//        lower_limit.enableDashedLine(10f, 10f, 0f);
//        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        lower_limit.setTextSize(10f);

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(upper_limit);
//        leftAxis.addLimitLine(lower_limit);
        if (statstics.equals("1") || statstics.equals("2") || statstics.equals("3")) {
            leftAxis.setAxisMaxValue(highestValue + 200);
            leftAxis.setAxisMinValue(0f);
        } else if (statstics.equals("4") || statstics.equals("5")) {
            leftAxis.setAxisMaxValue(highestValue + 20);
            leftAxis.setAxisMinValue(0f);
        }
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mLineChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        mLineChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        mLineChart.invalidate();

        if (flag == array.length()) {
            Log.e("StatisticsActivity", "masuk");

            setData();
        }
    }


    private ArrayList<String> setXAxisValues() {
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < weights.size(); i++) {
            xVals.add("Bulan "+String.valueOf(i + 1));
            Log.e("StatisticsActivity", "11");
        }

        return xVals;
    }

//    private ArrayList<String> setXAxisValues(){
//        ArrayList<String> xVals = new ArrayList<String>();
//        xVals.add("1");
//        xVals.add("2");
//        xVals.add("3");
//        xVals.add("4");
//        xVals.add("5");
//
//        return xVals;
//    }

//    private ArrayList<Entry> setYAxisValues(){
//
//        ArrayList<Entry> yVals = new ArrayList<Entry>();
//        for (int i = 0; i < weights.size(); i++) {
//
//            yVals.add(new Entry(weights.get(i).getWeight(), i));
//        }
//
//        return yVals;
//    }


    private ArrayList<Entry> setYAxisValues() {
//        Log.e("StatisticsActivity", "weights size :" + weights.size() + "weights value :" + weights.get(0).getWeight());
        ;
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < weights.size(); i++) {
            yVals.add(new Entry((float) weights.get(i).getWeight(), i));
            Log.e("StatisticsActivity", "22, value ="+(float) weights.get(i).getWeight());

//        yVals.add(new Entry(1000, 0));
//            yVals.add(new Entry(1f, 1));
//            yVals.add(new Entry(2f, 2));
//            yVals.add(new Entry(0.5f, 3));
//            yVals.add(new Entry(2.8f, 4));
        }

        return yVals;
    }
//    private ArrayList<Entry> setYAxisValues(){
//        ArrayList<Entry> yVals = new ArrayList<Entry>();
//        yVals.add(new Entry(1.5f, 0));
//        yVals.add(new Entry(1f, 1));
//        yVals.add(new Entry(2f, 2));
//        yVals.add(new Entry(0.5f, 3));
//        yVals.add(new Entry(2.8f, 4));
//
//        return yVals;
//    }

    private void setData() {
        Log.e("StatisticsActivity", "setData");
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "DataSet 1");

        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mLineChart.setData(data);

        loadingDialog.dismiss();
    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mLineChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


}

