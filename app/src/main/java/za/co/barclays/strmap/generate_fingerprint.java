package za.co.barclays.strmap;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class generate_fingerprint extends AppCompatActivity {

    boolean isRecorded;
    String line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_fingerprint);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isRecorded = false;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Scan Cleared", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                    isRecorded = false;
                    line = "";
                    ProgressBar prog = findViewById(R.id.scanCount);
                    prog.setProgress(0);
                    Button save = findViewById(R.id.SaveButton);
                    save.setEnabled(false);
                    EditText display = findViewById(R.id.textDisplay);
                    display.setText("");
                    Button scan = findViewById(R.id.button);
                    scan.setEnabled(true);

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

        isRecorded = false;
        line = "";
        ProgressBar prog = findViewById(R.id.scanCount);
        prog.setProgress(0);
        Button save = findViewById(R.id.SaveButton);
        save.setEnabled(false);
        EditText display = findViewById(R.id.textDisplay);
        display.setText("");

        return super.onOptionsItemSelected(item);
    }



    public void incrementProgressBar(View v) {

        Button save = findViewById(R.id.SaveButton);
        ProgressBar prog = findViewById(R.id.scanCount);
        Button scan = findViewById(R.id.button);
        int progress = prog.getProgress();
        prog.setProgress(progress+10);
        if (prog.getProgress()==100) {

            isRecorded = true;
            save.setEnabled(true);
            scan.setEnabled(false);

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


    }

    private void writeToFile(String data,String filename,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void saveCurrentPrint(View v) throws IOException {

        EditText roomID = findViewById(R.id.numberInput);
        EditText display = findViewById(R.id.textDisplay);
        String localops = line;

        writeToFile(localops,roomID.getText().toString()+".txt",getApplicationContext());

        display.setText(localops);

    }


}
