package za.co.barclays.strmap;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class generate_fingerprint extends AppCompatActivity {

    boolean isRecorded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_fingerprint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isRecorded = false;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_generate_fingerprint, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    String line;

    public void incrementProgressBar(View v) {

        Button save = findViewById(R.id.SaveButton);
        ProgressBar prog = findViewById(R.id.scanCount);
        int progress = prog.getProgress();
        prog.setProgress(progress+10);
        if (prog.getProgress()==100) {

            isRecorded = true;
            save.setEnabled(true);

        }

        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int size = 0;
        List<ScanResult> results;
        wifi.startScan();

        results = wifi.getScanResults();


        for (int k = 0; k < results.size(); k++)
        {
            line = line + "\n"+(results.get(k).SSID + "|" + results.get(k).BSSID + "|" + results.get(k).level);
        }

        line = line + "\n***";


    }


    public void saveCurrentPrint(View v) throws IOException {

        EditText roomID = findViewById(R.id.numberInput);
        EditText display = findViewById(R.id.textDisplay);
        String localops = line;

        display.setText(localops);

    }


}
