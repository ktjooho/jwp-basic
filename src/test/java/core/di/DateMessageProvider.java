package core.di;

import java.util.Calendar;

/**
 * Created by nokdu on 2017-03-30.
 */
public class DateMessageProvider {

    private Calendar now;

    public DateMessageProvider(Calendar now) {
        this.now = now;
    }

    public String getDateMessage() {

        int hour = now.get(Calendar.HOUR_OF_DAY);

        if(hour < 12) {
            return "오전";
        }

        return "오후";
    }
}
