package help;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import sun.util.resources.LocaleData;

public class TestParse {

	@Test
	public void testDateParse(){
		
		String d = "September 17, 1985";
		
		String month = d.substring( 0, d.indexOf( " " ) );
		String day = d.substring( d.indexOf( " " )+1, d.indexOf( "," ) );
		String year = d.substring( d.lastIndexOf( " " ) + 1 );
		
		Calendar c = Calendar.getInstance();
		c.set( Calendar.MONTH, Month.valueOf( month.toUpperCase() ).getValue() - 1 );
		c.set( Calendar.DAY_OF_MONTH, Integer.parseInt( day ) );
		c.set( Calendar.YEAR, Integer.parseInt( year ) );
		c.set( Calendar.HOUR_OF_DAY, 0 );
		c.set( Calendar.SECOND, 0 );
		c.set( Calendar.MINUTE, 0 );
		c.set( Calendar.MILLISECOND, 0 );
		
		System.out.println( new Date( c.getTimeInMillis() ) );
		
		int x = 0;
	}
	
	@Test
	public void testMod(){
		
		System.out.println( 11 % 12 );
		System.out.println( 10 % 2 );
	}
}
