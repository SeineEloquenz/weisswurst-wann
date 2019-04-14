package de.seine_eloquenz.weisswurstwann;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The Main Activity of weisswurst wann
 */
public class MainActivity extends Activity {

    private static String wwStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        Button button = (Button) findViewById(R.id.wwbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateInfo();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateInfo();
    }

    private void updateInfo() {
        CheckWeisswurstTask task = new CheckWeisswurstTask();
        TextView text = findViewById(R.id.wwtext);
        task.execute();
        text.setText(wwStatus);
    }

    private static class CheckWeisswurstTask extends AsyncTask<String, Integer, int[]> {

        @Override
        protected int[] doInBackground(final String... strings) {
            WeisswurstInfo weisswurstInfo = new WeisswurstInfo();
            return weisswurstInfo.checkWeisswurstStatusWeek();
        }

        @Override
        protected void onPostExecute(int[] result) {
            String res = "";
            for (int r : result) {
                if (r < 0) {
                    res = res + "-";
                } else if (r == 0) {
                    res = res + "n";
                } else {
                    res = res + "y";
                }
            }
            wwStatus = res;
        }
    }
}
