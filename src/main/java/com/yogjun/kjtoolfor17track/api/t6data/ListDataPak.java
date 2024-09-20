package com.yogjun.kjtoolfor17track.api.t6data;

import lombok.Data;

import java.util.List;

/**
 * {@link ListDataPak}
 *
 * @author <a href="mailto:280536928@qq.com">yogjun</a>
 * @version ${project.version} - 2024/9/20
 */
@Data
public class ListDataPak {
    private Integer code;
    private Integer count;
    private List<ListData> data;
}
