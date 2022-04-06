package com.intelligent.platform.server.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 何双宝 <2936741978@qq.com>
 * Created on 2022-04-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntelligentTutoringGradeVO {
    private String value;
    private String label;
}
