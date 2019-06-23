package cn.maidaotech.edu.sign.api;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("cn.maidaotech.edu.sign.api")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public static String[] getScanPackages() {
        return Application.class.getAnnotation(ComponentScan.class).value();
    }

    public static Reflections getAppReflection() {
        return new Reflections(new ConfigurationBuilder().forPackages(getScanPackages()));
    }
}
