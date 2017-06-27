import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils

/**
 *
 */
class NuwaFileUtils {

    /**
     *
     * @param dir
     * @param path
     * @return
     */
    public static File touchFile(File dir, String path) {
        def file = new File("${dir}/${path}");
        def parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return file;
    }

    /**
     *
     * @param bytes
     * @param file
     */
    public static copyBytesToFile(byte[] bytes, File file) {
        if (!file.exists()) {
            file.createNewFile();
        }
        FileUtils.writeByteArrayToFile(file, bytes)
    }

    public static File getFileFromProperty(Project project, String property) {
        def file;
        if (project.hasProperty(property)) {
            file = new File(project.getProperties()[property]);
            if (!file.exists()) {
                throw new InvalidUserDataException("${project.getProperties()[property]} does not exist")
            }
            if (!file.isDirectory()) {
                throw new InvalidUserDataException("${project.getProperties()[property]} is not directory")
            }
        }
        return file;
    }

    public static File getVariantFile(File dir, def variant, String fileName) {
        return new File("${dir}/${variant.dirName}/${fileName}")
    }

}