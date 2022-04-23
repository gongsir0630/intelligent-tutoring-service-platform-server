package com.intelligent.platform.server.utils;

import cn.hutool.extra.mail.MailUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-23
 */
public class IntelligentTutoringMailUtils {

    public static void send(MailContent content) {
        MailUtil.send(content.getTo(), content.getSubject(), content.getContent(), content.isHtml);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MailContent {
        private String to;
        private String subject;
        private String content;
        private boolean isHtml;
    }
}
