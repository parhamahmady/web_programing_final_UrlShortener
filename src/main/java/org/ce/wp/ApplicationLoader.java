package org.ce.wp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Parham Ahmadi
 * @since 20.01.23
 */
@SpringBootApplication(scanBasePackages = "org.ce.wp")
@EnableJpaRepositories(basePackages = "org.ce.wp.repository")
public class ApplicationLoader {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApplicationLoader.class)
                .run(args);
    }
}
