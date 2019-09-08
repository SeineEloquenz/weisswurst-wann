package de.seine_eloquenz.weisswurstwann;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

/**
 * The Main Activity of weisswurst wann
 */
public class MainActivity extends Activity {

    private static String wwStatus = "-----";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        Button button = findViewById(R.id.wwbutton);
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
        ImageView mon = findViewById(R.id.wwmon);
        ImageView tue = findViewById(R.id.wwtue);
        ImageView wed = findViewById(R.id.wwwed);
        ImageView thu = findViewById(R.id.wwthu);
        ImageView fri = findViewById(R.id.wwfri);
        task.execute();
        updateStatusIcon(mon, wwStatus.charAt(0));
        updateStatusIcon(tue, wwStatus.charAt(1));
        updateStatusIcon(wed, wwStatus.charAt(2));
        updateStatusIcon(thu, wwStatus.charAt(3));
        updateStatusIcon(fri, wwStatus.charAt(4));
        if (wwStatus.contains("-")) {
            Toast.makeText(this, this.getString(R.string.connectionErrorMessage), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStatusIcon(ImageView view, char status) {
        switch (status) {
            case '-':
                view.setBackgroundColor(Color.GRAY);
                break;
            case 'y':
                view.setBackgroundColor(Color.GREEN);
                break;
            case 'n':
                view.setBackgroundColor(Color.RED);
                break;
            default:
                //Well always get one of the above
        }
    }

    private static class CheckWeisswurstTask extends AsyncTask<String, Integer, WWStatus[]> {

        @Override
        protected WWStatus[] doInBackground(final String... strings) {
            WeisswurstInfo weisswurstInfo = new WeisswurstInfo();
            try {
                return weisswurstInfo.checkWeisswurstStatusWeek();
            } catch (IOException e) {
                return WWStatus.error();
            }
        }

        @Override
        protected void onPostExecute(WWStatus[] result) {
            StringBuilder res = new StringBuilder();
            for (WWStatus r : result) {
                if (r.equals(WWStatus.ERROR)) {
                    res.append("-");
                } else if (r.equals(WWStatus.NOT_AVAILABLE)) {
                    res.append("n");
                } else {
                    res.append("y");
                }
            }
            wwStatus = res.toString();
        }
    }
}
