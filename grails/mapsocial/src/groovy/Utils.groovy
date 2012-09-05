import java.util.Date
import java.text.SimpleDateFormat

class Utils {

    public static String getDateAsISO8601String(Date date) {
		SimpleDateFormat ISO8601FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		String result = ISO8601FORMAT.format(date);
		result = result.substring(0, result.length()-2) + ":" + result.substring(result.length()-2);
		return result;
  	}

}
