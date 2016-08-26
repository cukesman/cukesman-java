package cukesman.reporter.gson;

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

    private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private final DateFormat dateFormat;

    public ISO8601DateSerializer() {
        dateFormat = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public JsonElement serialize(final Date date, final Type typeOfSrc, final JsonSerializationContext context) {
        if (date == null ) {
            return null;
        } else {
            return new JsonPrimitive(dateFormat.format(date));
        }
    }

}
