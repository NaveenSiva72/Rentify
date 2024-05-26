package com.example.rentify.configuration;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.ZonedDateTime;

public class ZonedDateTimeCodec implements Codec<ZonedDateTime> {

    @Override
    public ZonedDateTime decode(BsonReader reader, DecoderContext decoderContext) {
        // Deserialize ZonedDateTime from BSON
        return ZonedDateTime.parse(reader.readString());
    }

    @Override
    public void encode(BsonWriter writer, ZonedDateTime value, EncoderContext encoderContext) {
        // Serialize ZonedDateTime to BSON
        writer.writeString(value.toString());
    }

    @Override
    public Class<ZonedDateTime> getEncoderClass() {
        return ZonedDateTime.class;
    }
}
