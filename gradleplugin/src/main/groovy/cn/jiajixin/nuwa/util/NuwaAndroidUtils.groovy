import groovy.xml.Namespace
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project

/**
 *
 */
class NuwaAndroidUtils {

    public static final String PATHC_NAME = "patch.jar";

    /**
     * 解析manifest配置文件，获取Application的类名
     * @param manifestFile
     * @return
     */
    public static String getApplication(File manifestFile) {
        def manifest = new XmlParser().parse(manifestFile);
        def androidTag = new Namespace("http://schemas.android.com/apk/res/android", 'android');
        def applicationName = manifest.application[0].attribute(androidTag.name);
        if (applicationName != null) {
            return applicationName.replace(".", "/") + ".class"
        }
        return null;
    }

    /**
     * 打包生成dex文件
     * @param project
     * @param classDir
     */
    public static void dex(Project project, File classDir) {
        if (classDir.listFiles().size() != 0) {//有文件
            def sdkDir;
            Properties properties = new Properties();
            //获取local.properties文件
            File localProps = project.rootProject.file('local.properties');
            if (localProps.exists()) {
                properties.load(localProps.newDataInputStream());
                sdkDir = properties.getProperty("sdk.dir");
            } else {
                sdkDir = System.getenv("ANDROID_HOME");
            }

            if (sdkDir) {
                def cmdExt = Os.isFamily(Os.FAMILY_WINDOWS) ? '.bat' : '';
                def stdout = new ByteArrayOutputStream();
                project.exec {//调用sdk的build-tools工具编译输出dex文件
                    commandLine "${sdkDir}/build-tools/${project.android.buildToolsVersion}/dx${cmdExt}",
                            '--dex',
                            "--output=${new File(classDir.getParent(), PATCH_NAME).absolutePath}",
                            "${classDir.absolutePath}"
                    standardOutput = stdout
                }
                def error = stdout.toString().trim();
                if (error) {
                    println "dex error:" + error
                }
            }
        } else {//
            throw new InvalidUserDataException('$ANDROID_HOME is not defined')
        }
    }

    /**
     * 混淆文件
     * @param proguardTask
     * @param mappingFile
     */
    public static applyMapping(DefaultTask proguardTask, File mappingFile) {
        if (proguardTask) {
            if (mappingFile.exists()) {
                proguardTask.applymapping(mappingFile);
            } else {
                println "$mappingFile does not exist"
            }
        }
    }
}