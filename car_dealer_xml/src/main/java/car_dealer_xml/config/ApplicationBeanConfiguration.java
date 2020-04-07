package car_dealer_xml.config;

import car_dealer_xml.utils.ValidationUtil;
import car_dealer_xml.utils.ValidationUtilImpl;
import car_dealer_xml.utils.XMLParser;
import car_dealer_xml.utils.XMLParserImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public XMLParser xmlParser() {
        return new XMLParserImpl();
    }

    @Bean
    public Random random(){
        return new Random();
    }


}
