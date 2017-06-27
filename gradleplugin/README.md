#Gradle插件编写
  1、参考网站
    >参考网站1：http://blog.csdn.net/sbsujjbcy/article/details/50782830
    >参考网站2：groovy语法基础：https://segmentfault.com/a/1190000004276167
    >参考网站3：w3c搜集groovy语法：http://www.w3cschool.cn/groovy/groovy_overview.html
    >参考网站4：groovy官网：http://www.groovy-lang.org/

#groovy基础语法：
 1:兼容Java语法，参数方面多了一个def定义方式
 2:字符串拼接




#Gradle常用方法介绍：
#####1、参考网站：
     1、gradle最全介绍：http://www.cnblogs.com/wxishang1991/p/5532006.html
     2、Gradle官网介绍：https://docs.gradle.org/current/dsl/
#####2、Gradle的三大对象介绍：
     1、Gradle常用的三大对象：Gradle、Project、Setting；
     2、Gradle对象：
     3、Project对象：每一个build.gradle会转换成一个Project对象。
     4、Setting对象：每一个settings.gradle都会转换成一个Settings对象。
     //注意，对于其他gradle文件，除非定义了class，否则会转换成一个实现了Script接口的对象。
#####3、Project介绍：
      带编译的工程就是Project;每一个Project在其根目录下面都要有一个编译脚本文件build.gradle.
      Project


#Android Gradle介绍
####1、Android gradle 参考文档
    1、官网介绍：http://tools.android.com/tech-docs/new-build-system/user-guide
    2、Android plugin介绍：http://google.github.io/android-gradle-dsl/current/

#Android gradle 插件开发：
#####1、环境搭建：
     >首先，新建一个Android项目。
     >之后，新建一个Android Module项目，类型选择Android Library。
     >将新建的Module中除了build.gradle文件外的其余文件全都删除，然后删除build.gradle文件中的所有内容。
     >在新建的module中新建文件夹src，接着在src文件目录下新建main文件夹，在main目录下新建groovy目录，这时候groovy文件夹会被Android识别为groovy源码目录。
     >除了在main目录下新建groovy目录外，你还要在main目录下新建resources目录，同理resources目录会被自动识别为资源文件夹。在groovy目录下新建项目包名，就像Java包名那样。resources目录下新建文件夹META-INF，META-INF文件夹下新建gradle-plugins文件夹。这样，就完成了gradle 插件的项目的整体搭建，之后就是小细节了。目前，项目的结构是这样的。
     >在然后在resources/META-INF/gradle-plugins目录下新建一个properties文件，注意该文件的命名就是你只有使用插件的名字，这里命名为plugin.test.properties，在里面输入
     ```
       implementation-class=cn.edu.zafu.gradle.PluginImpl
     ```
     >打开Module下的build.gradle文件，输入
      ```apply plugin: 'groovy'
         apply plugin: 'maven'

      dependencies {
          compile gradleApi()
          compile localGroovy()
      }

      repositories {
          mavenCentral()
      }
      ```

#####2、业务逻辑开发：

#####3、发布gradle 插件到本地库：
#####4、发布gradle 插件到jcenter：

#####5、使用gradle 插件：

      1、 在需要用到的项目的build.gradle 文件中输入：
      ```buildscript {
           repositories {  //指定我们的gradle插件所在的maven库位置
               maven {
                   url uri('../repo')
               }
           }
           dependencies { //dependencies我们的gradle插件
               classpath 'cn.edu.zafu.gradle.plugin:time:1.0.0'
           }
       }
       apply plugin: 'plugin.test'  //声明我们的插件，插件名称就是我们定义的properties文件名称。
       ```

#maven库介绍：

