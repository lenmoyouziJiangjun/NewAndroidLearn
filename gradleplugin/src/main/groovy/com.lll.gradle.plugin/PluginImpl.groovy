package com.lll.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * groovy 学习
 *   定义一个groovy 类
 */
public class PluginImpl implements Plugin<Project> {

    void apply(Project project){
        //添加一个编译时间监控的listener
        project.gradle.addListener(new TimeListener())
    }

}