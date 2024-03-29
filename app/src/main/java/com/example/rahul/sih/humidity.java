package com.example.rahul.sih;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class humidity extends AppCompatActivity {

    AnyChartView anyChartView;
    String url;
    Boolean first = true;
    Pie pie;
    ProgressBar progressBar;
    TextView currentTemperature, currentPpm;
    WebSocket ws;
    private OkHttpClient client;
    PieChart pieChart;


    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, okhttp3.Response response) {
            /*webSocket.send("Hello, it's SSaurel !");
            webSocket.send("What's up ?");
            webSocket.send(ByteString.decodeHex("deadbeef"));
            //webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");*/
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output(text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output("Receiving bytes : " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
            output("Error : " + t.getMessage());
        }
    }

    private void start() {
        String url = "ws://sensorapiturings.herokuapp.com/echo?connectionType=client";
        String local = "ws://172.16.166.209:5000/echo?connectionType=client";
        String echo = "ws://echo.websocket.org";
        okhttp3.Request request = new okhttp3.Request.Builder().url(local).build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    private void output(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String value = get_temperature(txt);
                if(value == null)
                    return;
                showHumidity(Integer.valueOf(value), Integer.valueOf(value), Integer.valueOf(value));
            }
        });
    }

    String get_temperature(String text)
    {
        // get JSONObject from JSON file
        JSONObject obj = null;
        try {
            obj = new JSONObject(text);
            JSONObject data = obj.getJSONObject("data");
            String value = data.getString("currentHumidity");
            return value;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        currentTemperature = findViewById(R.id.currentTemperature);
        currentPpm = findViewById(R.id.currentPpm);

        pieChart = findViewById(R.id.piechart);
        showHumidity(75, 0, 0);

        client = new OkHttpClient();
        start();
    }


    void showHumidity(int humidity, int temperature, int ppm)
    {
        if(!first)
        {
            pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(false);
            pieChart.setExtraOffsets(5, 10, 5, 5);

            pieChart.setDragDecelerationFrictionCoef(0.95f);

            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleColor(Color.WHITE);
            pieChart.setTransparentCircleRadius(61f);

            ArrayList<PieEntry> yValues = new ArrayList<>();

            yValues.add(new PieEntry(humidity, "Humidity"));
            yValues.add(new PieEntry(100 - humidity, ""));


            PieDataSet dataSet = new PieDataSet(yValues, "Humidity");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);

            final int[] JOYFUL_COLORS = {
                    Color.rgb(248, 247, 244), Color.rgb(254, 149, 7)
            };

            ArrayList<Integer> colours = new ArrayList<>();
            colours.add(Color.rgb(248, 247, 244));
            colours.add(Color.rgb(248, 247, 244));
            pieChart.setHoleColor(Color.rgb(48, 48, 48));
            dataSet.setColors(colours);

            PieData data = new PieData((dataSet));
            data.setValueTextSize(10f);
            data.setValueTextColor(Color.YELLOW);

            pieChart.setData(data);

            first = false;

            ws.close(1000, "");
        }

        else
        {
            ArrayList<PieEntry> yValues = new ArrayList<>();

            yValues.add(new PieEntry(humidity, "Humidity"));
            yValues.add(new PieEntry(100 - humidity, ""));

            PieDataSet dataSet = new PieDataSet(yValues, "Humidity");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            ArrayList<Integer> colours = new ArrayList<>();
            colours.add(Color.rgb(77, 120, 145));
            colours.add(Color.rgb(48, 48, 48));
            colours.add(Color.rgb(48, 48, 48));
            colours.add(Color.rgb(48, 48, 48));
            colours.add(Color.rgb(48, 48, 48));
            pieChart.setHoleColor(Color.rgb(48, 48, 48));


            dataSet.setColors(colours);

            PieData data = new PieData((dataSet));
            data.setValueTextSize(10f);
            data.setValueTextColor(Color.YELLOW);

            pieChart.setData(data);

            pieChart.notifyDataSetChanged(); // let the chart know it's data changed
            pieChart.invalidate();
        }

    }


    public void openHumidity(View view) {
        Intent intent = new Intent(getApplicationContext(), humidity.class);
        startActivity(intent);
    }


    public void openTemperature(View view) {
        Intent intent = new Intent(getApplicationContext(), thermometer.class);
        startActivity(intent);
    }


    public void openPressure(View view) {
        Intent intent = new Intent(getApplicationContext(), pressure.class);
        startActivity(intent);
    }

    public void openVibrations(View view) {
        Intent intent = new Intent(getApplicationContext(), vibrations.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        if(ws != null)
            ws.close(1000, "");
        super.onPause();
    }

    @Override
    protected void onStop() {
        if(ws != null)
            ws.close(1000, null);
        super.onStop();
    }
}
