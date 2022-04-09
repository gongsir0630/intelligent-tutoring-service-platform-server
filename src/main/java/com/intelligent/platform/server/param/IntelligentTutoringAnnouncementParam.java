package com.intelligent.platform.server.param;

import com.intelligent.platform.server.model.IntelligentTutoringAnnouncement;
import lombok.Data;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-09
 */
@Data
public class IntelligentTutoringAnnouncementParam {
    private Long id;
    private String title;
    private String content;
    private String date;

    public IntelligentTutoringAnnouncement buildAnnouncement(String username) {
        IntelligentTutoringAnnouncement announcement = IntelligentTutoringAnnouncement.builder()
                .id(id)
                .title(title)
                .content(content)
                .date(date)
                .updateTime(System.currentTimeMillis())
                .updateUserName(username)
                .build();
        if (id == null || id == 0L) {
            announcement.setCreateTime(System.currentTimeMillis());
            announcement.setCreateUserName(username);
        }
        return announcement;
    }
}
