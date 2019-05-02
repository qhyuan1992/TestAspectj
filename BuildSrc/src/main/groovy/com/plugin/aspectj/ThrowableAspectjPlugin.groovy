package com.plugin.aspectj

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

/**
 * 使用插件：将app module中的依赖移出来
 * 1.将AspectjTest移到子module中
 * 2.插件中添加依赖子module和aspectjrt
 */
public class ThrowableAspectjPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        println "ThrowableAspectjPlugin " + project.getName()
        def hasApp = project.plugins.withType(AppPlugin)
        def hasLib = project.plugins.withType(LibraryPlugin)
        if (!hasApp && !hasLib) {
            throw new IllegalStateException("'android' or 'android-library' plugin required.")
        }

        final def variants
        if (hasApp) {
            variants = project.android.applicationVariants
        } else {
            variants = project.android.libraryVariants
        }

        project.dependencies {
            implementation 'org.aspectj:aspectjrt:1.8.6'
            implementation project.project(':runntime')
        }

        variants.all { variant ->
            JavaCompile javaCompile = variant.javaCompile
            javaCompile.doLast {
                String[] args = [
                        "-showWeaveInfo",
                        "-1.8",
                        "-inpath", javaCompile.destinationDir.toString(),
                        "-aspectpath", javaCompile.classpath.asPath,
                        "-d", javaCompile.destinationDir.toString(),
                        "-classpath", javaCompile.classpath.asPath,
                        "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)
                ]
                println "ajc args: " + Arrays.toString(args)

                MessageHandler handler = new MessageHandler(true)
                new Main().run(args, handler);
                for (IMessage message : handler.getMessages(null, true)) {
                    switch (message.getKind()) {
                        case IMessage.ABORT:
                        case IMessage.ERROR:
                        case IMessage.FAIL:
                            println  "error ajc " + message.message
                            break
                        case IMessage.WARNING:
                            println "warn ajc " + message.message
                            break
                        case IMessage.INFO:
                            println "info ajc " + message.message
                            break
                        case IMessage.DEBUG:
                            println "debug ajc " + message.message
                            break
                    }
                }
            }
        }
    }
}
