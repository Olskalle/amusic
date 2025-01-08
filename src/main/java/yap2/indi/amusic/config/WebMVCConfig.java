package yap2.indi.amusic.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@Configuration
@EnableWebMvc
@ComponentScan({ "yap2.indi.amusic" })
public class WebMVCConfig implements WebMvcConfigurer, ApplicationContextAware {

    private ApplicationContext applicationContext;

    // Java setter
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setViewNames(new String[] { "*.html" });
        return resolver;
    }

    private ISpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addDialect(new LayoutDialect()); // Подключаем поддержку layout
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    private ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("classpath:/templates/");  // Путь к шаблонам в папке src/main/resources/templates
        resolver.setSuffix(".html");
        resolver.setCacheable(false);  // Для разработки лучше отключить кэш
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
    }
}
