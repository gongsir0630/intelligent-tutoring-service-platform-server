package com.intelligent.platform.server.vo;

import com.intelligent.platform.server.model.IntelligentTutoringMessage;
import lombok.ToString;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-05-01
 */
@ToString(callSuper = true)
public class IntelligentTutoringMessageVO extends IntelligentTutoringMessage {

    /**
     * 发信人名字
     */
    private String from;

    /**
     * 收信人名字
     */
    private String to;

    /**
     * 时间字符串
     */
    private String createTimeStr;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
