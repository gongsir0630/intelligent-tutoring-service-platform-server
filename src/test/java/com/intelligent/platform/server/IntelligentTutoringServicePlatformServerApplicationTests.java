package com.intelligent.platform.server;

import com.intelligent.platform.server.utils.IntelligentTutoringMailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IntelligentTutoringServicePlatformServerApplicationTests {

    @Test
    void contextLoads() {
        String contentHtml = String.format("<p>尊敬的<font color='red'>%s</font>老师, 您有一条新的课程预约信息, 请尽快登录平台进行处理</p>", "何双宝");
        IntelligentTutoringMailUtils.MailContent content = IntelligentTutoringMailUtils.MailContent.builder()
                .to("2936741978@qq.com")
                .subject("课程预约通知")
                .content(contentHtml)
                .isHtml(true)
                .build();
        IntelligentTutoringMailUtils.send(content);
    }

}
