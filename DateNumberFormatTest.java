import java.text.*;
import java.util.Locale;
import java.util.Date;

public class DateNumberFormatTest {

    public static void main(String[] args) {
		NumberFormat nf1 = NumberFormat.getInstance(Locale.FRENCH);
		NumberFormat nf2 = NumberFormat.getInstance(Locale.CHINA);
		NumberFormat nf3 = NumberFormat.getInstance(Locale.GERMANY);
		NumberFormat nf4 = NumberFormat.getInstance(Locale.ITALIAN);
		NumberFormat nf5 = NumberFormat.getInstance(Locale.ENGLISH);
		System.out.println("Locale.FRENCH:"  + nf1.format(1234567));
		System.out.println("Locale.CHINA:"   + nf2.format(1234567));
		System.out.println("Locale.GERMANY:" + nf3.format(1234567));
		System.out.println("Locale.ITALIAN:" + nf4.format(1234567));
		System.out.println("Locale.ENGLISH:" + nf5.format(1234567));
		
		SimpleDateFormat df0 = new SimpleDateFormat("dd/MM/yyyy");
		
		DateFormat df1 = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRENCH);
		DateFormat df2 = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CHINA);
		DateFormat df3 = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMANY);
		DateFormat df4 = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALIAN);
		DateFormat df5 = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH);

		try {
			Date d = df0.parse("17/07/1989");

			System.out.println("Locale.FRENCH:"  + df1.format(d));
			System.out.println("Locale.CHINA:"   + df2.format(d));
			System.out.println("Locale.GERMANY:" + df3.format(d));
			System.out.println("Locale.ITALIAN:" + df4.format(d));
			System.out.println("Locale.ENGLISH:" + df5.format(d));
			} catch (ParseException e) {
				e.printStackTrace();
			}

    }

}