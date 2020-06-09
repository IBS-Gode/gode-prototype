package org.ibs.cdx.gode.store;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Date;

@Component
public class OffsetDateTimeToDateConverter implements Converter<OffsetDateTime, Date> {


    @Override
    public Date convert(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null : Date.from(offsetDateTime.toInstant());
    }
}
