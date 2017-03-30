package core.di;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

/**
 * Created by nokdu on 2017-03-30.
 */
public class DateMessageProviderTest {

    @Test
    public void 오전() {
        DateMessageProvider dateMessageProvider = new DateMessageProvider(createCurrentDate(11));
        Assert.assertEquals("오전",dateMessageProvider.getDateMessage());
    }
    @Test
    public void 오후() {
        DateMessageProvider dateMessageProvider = new DateMessageProvider(createCurrentDate(15));
        Assert.assertEquals("오후",dateMessageProvider.getDateMessage());
    }
    private Calendar createCurrentDate(int hoursOfDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hoursOfDay);
        return calendar;
    }
}
