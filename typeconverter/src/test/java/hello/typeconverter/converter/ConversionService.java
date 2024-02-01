package hello.typeconverter.converter;

import hello.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.*;

public class ConversionService {

    @Test
    void conversionService(){
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());

        Integer result = conversionService.convert("10", Integer.class);
        System.out.println("result = " + result);
        assertThat(result).isEqualTo(10);
        assertThat("10").isEqualTo(conversionService.convert(10,String.class));
        IpPort ip = conversionService.convert("127.0.0.1:8080", IpPort.class);
        assertThat(ip).isEqualTo(new IpPort("127.0.0.1",8080));

        String convert = conversionService.convert(new IpPort("127.0.0.1", 8080), String.class);
        assertThat(convert).isEqualTo("127.0.0.1:8080");
    }
}
