package softuni.productshop3.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.productshop3.util.FileIOUtil;
import softuni.productshop3.util.FileIOUtilImpl;
import softuni.productshop3.util.ValidationUtil;
import softuni.productshop3.util.ValidationUtilImpl;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public FileIOUtil fileIOUtil(){
        return new FileIOUtilImpl();
    }
    @Bean
    public Gson gson(){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }
    @Bean
    public ValidationUtil validationUtil(){
        return new ValidationUtilImpl();
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
