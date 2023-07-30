package com.github.imdtf.mnt.vo;

import lombok.Data;

/**
 * 0 *
 * 1 * @Author: DTF
 * 2 * @email: imdtf@qq.com
 * 3 * @Date: 2023/7/29 13:04
 * 4
 */
@Data
public class PageVO<T> {


    public static <T> PageVO<T> of () {
        return new PageVO<>();
    }
}
