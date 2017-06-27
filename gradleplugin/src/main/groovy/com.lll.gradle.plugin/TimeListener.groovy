package com.lll.gradle.plugin

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState
import org.gradle.util.Clock

/**
 * 自定义一个Listener,
 *   用来监控gradle 的每个task执行时间
 */
class TimeListener implements TaskExecutionListener, BuildListener {

    private Clock clock;//时间工具类
    private times = [];//定义一个数组


    @Override
    void buildStarted(Gradle gradle) {

    }

    @Override
    void settingsEvaluated(Settings settings) {

    }

    @Override
    void projectsLoaded(Gradle gradle) {

    }

    @Override
    void projectsEvaluated(Gradle gradle) {

    }

    @Override
    void buildFinished(BuildResult buildResult) {
        println "task execute time:";
        for(time in times){

        }
    }

    @Override
    void beforeExecute(Task task) {
        clock = new Clock();//创建对象
    }

    @Override
    void afterExecute(Task task, TaskState taskState) {
        //
        def ms = clock.timeInMs;//获取时间
        times.add([ms, task.path]);
        task.project.logger.debug("${task.path} spend ${ms} ms");
    }
}