package mjkarbasian.moshtarimadar;

import android.test.AndroidTestCase;

import java.util.Calendar;
import java.util.Date;

import io.github.meness.roozh.Roozh;
import io.github.meness.roozh.RoozhLocale;

/**
 * Created by family on 11/18/2016.
 */
public class TestCalendar extends AndroidTestCase {

    Roozh persianCalendar = Roozh.getInstance(RoozhLocale.PERSIAN);
    Calendar calendar = Calendar.getInstance();
    public void testToday(){

        Date date = calendar.getTime();
        int[] today = getPersianCalendar(date);
        int year = 1395;
        int month = 8;
        int day = 28;
        assertEquals("date test Faile",year,today[0]);
        assertEquals("date test Faile",month,today[1]);
        assertEquals("date test Faile",day,today[2]);
    }
    public void testOneYearLater(){
        calendar.roll(Calendar.YEAR,4);
        Date date = calendar.getTime();
        date=calendar.getTime();
        int[] oneYearLater = getPersianCalendar(date);
        int year = 1399;
        int month = 8;
        int day = 28;
        assertEquals("date test Faile",year,oneYearLater[0]);
        assertEquals("date test Faile",month,oneYearLater[1]);
        assertEquals("date test Faile",day,oneYearLater[2]);

    }
    public int[] getPersianCalendar(Date mDate) {

        Roozh pCalendar = persianCalendar.gregorianToPersian(mDate.getTime());
        int checkYear = pCalendar.getYear();
        int checkMonth = pCalendar.getMonth();
        int checkDate = pCalendar.getDayOfMonth();
        int[] pRDate = {checkYear,checkMonth,checkDate};
        return pRDate;
    }
}
