package me.theo.coursepaper3app.converterSize;

import me.theo.coursepaper3app.models.Size;
import org.springframework.core.convert.converter.Converter;

public class SizeConverter implements Converter<String, Size> {

    @Override
    public Size convert(String source) {
        return Size.convertSize(Integer.parseInt(source));
    }
}
