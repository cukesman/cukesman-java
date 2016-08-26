package cukesman.reporter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ISO8601DateSerializer implements JsonSerializer<Date> {

    public static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private final DateFormat dateFormat;

    public ISO8601DateSerializer() {
        dateFormat = isoDateFormat();
    }

    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null ) {
            return null;
        } else {
            return new JsonPrimitive(dateFormat.format(src));
        }
    }

    public static DateFormat isoDateFormat() {
        SimpleDateFormat isoDateFormat = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        isoDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return isoDateFormat;
    }
}
