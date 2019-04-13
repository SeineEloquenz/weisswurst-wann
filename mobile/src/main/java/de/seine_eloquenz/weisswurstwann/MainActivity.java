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

    private static boolean wwStatus;

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
        if (wwStatus) {
            text.setText(R.string.wwavailable);
        } else {
            text.setText(R.string.wwnotavailable);
        }
    }

    private static class CheckWeisswurstTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(final String... strings) {
            WeisswurstInfo weisswurstInfo = new WeisswurstInfo();
            return weisswurstInfo.checkWeisswurstStatus();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            wwStatus = result;
        }
    }
}
