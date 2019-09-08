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

    private static WWWeek wwStatus = new WWWeek();

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
        updateStatusIcon(mon, wwStatus.monday());
        updateStatusIcon(tue, wwStatus.tuesday());
        updateStatusIcon(wed, wwStatus.wednesday());
        updateStatusIcon(thu, wwStatus.thursday());
        updateStatusIcon(fri, wwStatus.friday());
        if (wwStatus.errorOccured()) {
            Toast.makeText(this, this.getString(R.string.connectionErrorMessage), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStatusIcon(ImageView view, WWStatus status) {
        switch (status) {
            case NO_INFO:
                view.setBackgroundColor(Color.GRAY);
                break;
            case AVAILABLE:
                view.setBackgroundColor(Color.GREEN);
                break;
            case NOT_AVAILABLE:
                view.setBackgroundColor(Color.RED);
                break;
            default:
                //Well always get one of the above
        }
    }

    private static class CheckWeisswurstTask extends AsyncTask<String, Integer, WWWeek> {

        @Override
        protected WWWeek doInBackground(final String... strings) {
            WeisswurstInfo weisswurstInfo = new WeisswurstInfo();
            try {
                return weisswurstInfo.checkWeisswurstStatusWeek();
            } catch (IOException e) {
                return WWWeek.error();
            }
        }

        @Override
        protected void onPostExecute(WWWeek result) {
            wwStatus = result;
        }
    }
}
