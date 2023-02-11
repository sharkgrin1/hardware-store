package store;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.jooq.conf.RenderNameCase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Locale;

@SpringBootApplication(scanBasePackageClasses = StoreApp.class, exclude = {UserDetailsServiceAutoConfiguration.class})
public class StoreApp {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        System.setProperty("org.jboss.logging.provider", "slf4j");
        SpringApplication.run(StoreApp.class, args);
    }

    @Bean
    @ConditionalOnMissingBean(ParameterNamesModule.class)
    public ParameterNamesModule parameterNamesModule() {
        return new ParameterNamesModule(JsonCreator.Mode.DEFAULT);
    }

    @Bean
    public DefaultConfigurationCustomizer defaultConfigurationCustomizer() {
        return configuration -> configuration.settings().setRenderNameCase(RenderNameCase.LOWER);
    }
}