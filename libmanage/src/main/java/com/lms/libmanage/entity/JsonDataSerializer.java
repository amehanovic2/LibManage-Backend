package com.lms.libmanage.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDataSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        gen.writeString(simpleDateFormat.format(date));
    }
}
