package fsc.site.conf;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * @author fanshanchao
 * @date 2019/3/26 13:14
 * 这个类用来配置java mail发送邮件的配置信息
 */
@Configuration
@PropertySource(value = {"classpath:javamail.properties"})//加载数据库配置文件
public class EmailConfig {
    //加载spring 的环境对象 用来获取配置文件中的信息
    @Autowired
    private Environment env;

    /**
     * 配置邮件发送器
     * @return
     */
    @Bean
    public JavaMailSender mailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        //配置发送器的各各配置
        javaMailSender.setHost(env.getProperty("mail.smtp.host"));
        javaMailSender.setPassword(env.getProperty("mail.smtp.password"));
        javaMailSender.setPort(Integer.parseInt(env.getProperty("mail.smtp.port")));
        javaMailSender.setUsername(env.getProperty("mail.smtp.username"));
        javaMailSender.setDefaultEncoding(env.getProperty("mail.smtp.defaultEncoding"));
        Properties emailProperties = new Properties();
        //设置邮箱一些基本配置
        emailProperties.setProperty("mail.smtp.auth",env.getProperty("mail.smtp.auth"));
        emailProperties.setProperty("mail.smtp.timeout",env.getProperty("mail.smtp.timeout"));
        emailProperties.setProperty("mail.smtp.socketFactory.class",env.getProperty("mail.smtp.socketFactory.class"));
        emailProperties.setProperty("mail.smtp.socketFactory.port",env.getProperty("mail.smtp.socketFactory.port"));
        javaMailSender.setJavaMailProperties(emailProperties);
        return javaMailSender;
    }

    /**
     * 不知道知道为什么velocity引擎用不了 那就用FreeMarker吧 注意这里因为包冲突用的是全类名
     * @return
     */
    @Bean("emailConfiguration")
    public freemarker.template.Configuration freeMarkerConfigurationFactoryBean() throws IOException, TemplateException {
        FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean = new FreeMarkerConfigurationFactoryBean();
        //设置模板所在位置
        freeMarkerConfigurationFactoryBean.setTemplateLoaderPath("classpath:/freemarker/");
        //设置模板的默认编码
        freeMarkerConfigurationFactoryBean.setDefaultEncoding(env.getProperty("mail.smtp.defaultEncoding"));
        return freeMarkerConfigurationFactoryBean.createConfiguration();
    }

}
