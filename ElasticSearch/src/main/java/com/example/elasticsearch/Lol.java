package com.example.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 英雄联盟游戏人物
 * @author haopeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lol implements Serializable {
    private Long id;
    /**
     * 英雄游戏名字
     */
    private String name;
    /**
     * 英雄名字
     */
    private String realName;
    /**
     * 英雄描述信息
     */
    private String desc;
}
