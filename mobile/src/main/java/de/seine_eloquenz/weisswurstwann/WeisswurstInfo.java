package de.seine_eloquenz.weisswurstwann;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Class checking whether weisswurst will be available in the KIT canteen
 */
public final class WeisswurstInfo {

    private static final int CANTEEN_ID = 31; //KIT Adenauerring
    private static final long ONE_DAY_MS = 24 * 3600 * 1000;
    private static final String GET_MEALS = "/canteens/" + CANTEEN_ID + "/days/" + "%s" + "/meals";
    private static final String OPEN_MENSA_URL = "http://openmensa.org/api/v2";
    private static final String SEARCH_REGEX = ".* [Ww]ei(ss|ß)w[uü]rst(e?) .*";

    private final Gson gson;
    private final SimpleDateFormat sm;

    /**
     * Creates a new {@link WeisswurstInfo}
     */
    public WeisswurstInfo() {
        this.gson = new Gson();
        sm = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
    }

    public String getNotificationText() {
        return "Morgen gibt's Weißwürste in der KIT-Cafeteria!";
    }

    /**
     * Checks whether weisswurst will be available the next weekday
     * @return true if weisswurst available, false if not
     */
    public boolean checkWeisswurstStatusTomorrow() throws IOException {
        return checkWeisswurstStatus(this.getDateToCheck());
    }

    /**
     * Checks the next week for days where weisswurst is available
     * @return WWStatus array for each day monday-friday
     */
    public WWWeek checkWeisswurstStatusWeek() throws IOException {
        WWWeek ret = new WWWeek();
        String[] dates = getNextWeekDates();
        for (int i = 0; i < ret.length() && i < dates.length; i++) {
            if (!dates[i].equals("i")) {
                ret.update(i, checkWeisswurstStatus(dates[i]) ? WWStatus.AVAILABLE : WWStatus.NOT_AVAILABLE);
            }
        }
        return ret;
    }

    /**
     * Checks whether weisswurst will be available the next weekday
     * @param date formatted date to check for
     * @return true if weisswurst available, false if not
     */
    public boolean checkWeisswurstStatus(String date) throws IOException {
        JsonArray meals = this.getMeals(date);
        for (JsonElement meal : meals) {
            String name = meal.getAsJsonObject().get("name").getAsString();
            if (name.matches(SEARCH_REGEX)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the {@link JsonArray} of the meals on the next weekday
     * @return jsonarry with the meals
     */
    private JsonArray getMeals(String date) throws IOException {
        String uri = OPEN_MENSA_URL + String.format(GET_MEALS, date);
        URL url;
        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new JsonArray();
        }
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        JsonArray resp = gson.fromJson(new InputStreamReader(connection.getInputStream()), JsonArray.class);
        connection.disconnect();
        return resp;
    }

    /**
     * Gets a yyyy-MM-dd formatted date string of the next week day
     * @return date string
     */
    private String getDateToCheck() {
        Date date = new Date(System.currentTimeMillis() + ONE_DAY_MS);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            date.setTime(System.currentTimeMillis() + 3 * ONE_DAY_MS);
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            date.setTime(System.currentTimeMillis() + 2 * ONE_DAY_MS);
        }
        return sm.format(date);
    }

    /**
     * Gets a yyyy-MM-dd formatted date string of the next week day
     * @return date string
     */
    private String[] getNextWeekDates() {
        Date date = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            date.setTime(System.currentTimeMillis() + 2 * ONE_DAY_MS);
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            date.setTime(System.currentTimeMillis() + ONE_DAY_MS);
        }
        cal.setTime(date);
        String[] dates = {"i", "i", "i", "i", "i"};
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                dates[0] = sm.format(date);
                dates[1] = sm.format(new Date(date.getTime() + ONE_DAY_MS));
                dates[2] = sm.format(new Date(date.getTime() + 2 * ONE_DAY_MS));
                dates[3] = sm.format(new Date(date.getTime() + 3 * ONE_DAY_MS));
                dates[4] = sm.format(new Date(date.getTime() + 4 * ONE_DAY_MS));
                break;
            case Calendar.TUESDAY:
                dates[1] = sm.format(date);
                dates[2] = sm.format(new Date(date.getTime() + ONE_DAY_MS));
                dates[3] = sm.format(new Date(date.getTime() + 2 * ONE_DAY_MS));
                dates[4] = sm.format(new Date(date.getTime() + 3 * ONE_DAY_MS));
                break;
            case Calendar.WEDNESDAY:
                dates[2] = sm.format(date);
                dates[3] = sm.format(new Date(date.getTime() + ONE_DAY_MS));
                dates[4] = sm.format(new Date(date.getTime() + 2 * ONE_DAY_MS));
                break;
            case Calendar.THURSDAY:
                dates[3] = sm.format(date);
                dates[4] = sm.format(new Date(date.getTime() + ONE_DAY_MS));
                break;
            case Calendar.FRIDAY:
                dates[4] = sm.format(date);
                break;
            default:
        }
        return dates;
    }
}
