package org.ce.wp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
public class EnvironmentConfiguration implements EnvironmentPostProcessor {

    private static final String CONFIG_FILE_PATH = "config/application.properties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Resource classPathConfig = new ClassPathResource(CONFIG_FILE_PATH);
        if (classPathConfig.exists()) {
            Properties classPathProperties = new Properties();
            try {
                classPathProperties.load(classPathConfig.getInputStream());
                environment.getPropertySources().addFirst(new PropertiesPropertySource("classPathProperties", classPathProperties));
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }

        Resource fileSystemConfig = new FileSystemResource(CONFIG_FILE_PATH);
        if (fileSystemConfig.exists()) {
            Properties fileSystemProperties = new Properties();
            try {
                fileSystemProperties.load(fileSystemConfig.getInputStream());
                environment.getPropertySources().addFirst(new PropertiesPropertySource("fileSystemProperties", fileSystemProperties));
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
