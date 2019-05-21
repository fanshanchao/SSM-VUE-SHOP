package fsc.site.util;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author fanshanchao
 * @date 2019/3/26 13:07
 */
public class SendEmail {
    /**
     *
     * @param subject 邮件的主题
     * @param code 验证码内容
     * @param emailAddress 目的地
     * @param javaMailSender  发送邮件的核心类
     * @param freeMarkerConfiguration freemarker配置管理类
     * @return
     */
    public String sendCodeMailFreeMarker(String subject, int code, String emailAddress, JavaMailSender javaMailSender,
                                     freemarker.template.Configuration freeMarkerConfiguration){
        //创建一个邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;//发送邮件的一个插件
        //加载邮件的配置信息
        Properties properties = new Properties();
        try {
            //InputStream inputStream = new BufferedInputStream(new FileInputStream(new File("classpath:javamail.properties")));
            //从配置文件中拿到发送人的地址
            properties.load(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("/javamail.properties"),"UTF-8"));//加载格式化后的流
            String from = properties.get("mail.smtp.username")+"";
            //使用邮件帮助类
            mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            //设置发送人邮箱
            mimeMessageHelper.setFrom(from);
            //设置收件人邮箱
            mimeMessageHelper.setTo(emailAddress);
            //设置邮件的标题
            mimeMessageHelper.setSubject(subject);
            //解析模板文件
            mimeMessageHelper.setText(getCodeText(freeMarkerConfiguration,code), true);
            //通过文件路径获取文件名 这里用到的是一个自己写的工具 不发生附件，所以用不到这个方法
            //String filename = StringUtils.getFileName(location);
            //发送邮件
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println("处理异常");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "发送成功";

    }
    /**
     * 读取freemarker模板的方法
     */
    private String getCodeText(freemarker.template.Configuration freeMarkerConfiguration,int code) {
        String txt = "";
        try {
            Template template = freeMarkerConfiguration.getTemplate("codeEmail.ftl");
            // 通过map传递动态数据
            Map<String, Integer> map = new HashMap<>();
            map.put("code",code);
            // 解析模板文件
            txt = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            System.out.println("getText()->>>>>>>>>");// 输出的是HTML格式的文档
            System.out.println(txt);
        } catch (IOException e) {
            // TODO 异常执行块！
            e.printStackTrace();
        } catch (TemplateException e) {
            // TODO 异常执行块！
            e.printStackTrace();
        }
        return txt;
    }
}
