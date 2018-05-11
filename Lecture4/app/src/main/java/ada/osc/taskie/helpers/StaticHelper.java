package ada.osc.taskie.helpers;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class StaticHelper {

    public static boolean tryParseInt(String valueToCheck) {
        try {
            if(valueToCheck.isEmpty()) {
                return false;
            }
            Integer.parseInt(valueToCheck);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkRegex(String valueToCheck){
        if(valueToCheck.matches("[a-zA-Z]+") && !valueToCheck.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

    //started with wrong converting because CalendarView.getDate() not listening to changes
    //on calendar, instead date change listener is implemented
    public static boolean isValidDate(String value) {
        try {
            long chosenUnixTimestamp = Long.parseLong(value);
            long currentUnixTimestamp = System.currentTimeMillis();
            if(currentUnixTimestamp > chosenUnixTimestamp) {
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    //also not used because upper explanation
    public static String unixTimeStampToSimpleDate(long timestampValue){
        Date date = new java.util.Date(timestampValue * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
        return sdf.format(date);
    }

    public static boolean isValidDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);
        currentMonth += 1;

        if((day > currentDay) && (month >= currentMonth) && (year >= currentYear)) {
            return true;
        }
        else {
            return false;
        }
    }


    public static void displayToast(String message, Context context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
