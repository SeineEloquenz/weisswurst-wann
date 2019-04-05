package de.seine_eloquenz.weisswurstwann;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
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
    private static final String SEARCH_REGEX = "[Ww]ei(ss|ß)w[uü]rst(e?)";

    private final Gson gson;
    private final Client client;

    /**
     * Creates a new {@link WeisswurstInfo}
     */
    public WeisswurstInfo() {
        this.gson = new Gson();
        this.client = ClientBuilder.newClient();
    }

    /**
     * Checks whether weisswurst will be available the next weekday
     * @return true if weisswurst available, false if not
     */
    public boolean checkWeisswurstStatus() {
        JsonArray meals = this.getMeals();
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
    private JsonArray getMeals() {
        String uri = OPEN_MENSA_URL + String.format(GET_MEALS, this.getDateToCheck());
        WebTarget target = client.target(uri);
        try {
            String resp = target.request().get(String.class);
            return gson.fromJson(resp, JsonArray.class);
        } catch (javax.ws.rs.NotFoundException e) {
            return new JsonArray();
        }
    }

    /**
     * Gets a yyyy-MM-dd formatted date string of the next week day
     * @return date string
     */
    private String getDateToCheck() {
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
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
}
