import groovyjarjarasm.asm.ClassReader
import groovyjarjarasm.asm.ClassVisitor
import groovyjarjarasm.asm.ClassWriter
import groovyjarjarasm.asm.MethodVisitor
import org.apache.tools.ant.taskdefs.Input
import org.gradle.internal.impldep.org.apache.commons.codec.digest.DigestUtils
import org.gradle.internal.impldep.org.apache.commons.io.IOUtils

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

/**
 *
 */
class NuwaProcessor {

    /**
     * 压缩jar文件
     * @param hashFile
     * @param jarFile
     * @param patchDir
     * @param map
     * @param includePackage
     * @param excludeClass
     */
    public
    static processJar(File hashFile, File jarFile, File patchDir, Map map, HashSet<String> includePackage, HashSet<String> excludeClass) {
        if (jarFile) {
            def optJar = new File(jarFile.getParent(), jarFile.name + ".opt");
            def file = new JarFile(jarFile);
            Enumeration enumeration = file.entries();
            JarOutputStream jarOutputStream = new JarOutputStream(new FileInputStream(optJar));
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = enumeration.nextElement();
                String entryName = jarEntry.getName();
                ZipEntry zipEntry = new ZipEntry(entryName);
                InputStream inputStream = file.getInputStream(jarEntry);
                jarOutputStream.putNextEntry(zipEntry);
                if (shouldProcessClassInJar(entryName, includePackage, excludeClass)) {
                    def bytes = referHackWhenInit(inputStream);
                    jarOutputStream.write(bytes);

                    def hash = DigestUtils.shaHex(bytes);
                    hashFile.append(NuwaMapUtils.format(entryName, hash));
                    if (NuwaMapUtils.notSame(map, entryName, hash)) {
                        NuwaFileUtils.copyBytesToFile(bytes, NuwaFileUtils.touchFile(patchDir, entryName))
                    }
                } else {
                    jarOutputStream.write(IOUtils.toByteArray(inputStream));
                }
                jarOutputStream.closeEntry();
            }
            jarOutputStream.close();
            file.close();
            if(jarFile.exists()){
                jarFile.delete();
            }
            optJar.renameTo(jarFile);
        }
    }

    public static byte[] processClass(File file) {
        File optClass = new File(file.getParent(), file.name + ".opt");
        FileInputStream inputStream = new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream(optClass)

        def bytes = referHackWhenInit(inputStream);
        outputStream.write(bytes)
        inputStream.close()
        outputStream.close()
        if (file.exists()) {
            file.delete()
        }
        optClass.renameTo(file)
        return bytes
    }

    private
    static boolean shouldProcessClassInJar(String entryName, HashSet<String> includePackage, HashSet<String> excludeClass) {
        return entryName.endsWith(".class") && !entryName.startsWith("cn/jiajixin/nuwa/") &&
                NuwaSetUtils.isIncluded(entryName, includePackage) && !NuwaSetUtils.isExcluded(entryName, excludeClass) &&
                !entryName.contains("android/support/")
    }

    public static boolean shouldProcessPreDexJar(String path) {
        return path.endsWith("classes.jar") && !path.contains("com.android.support") && !path.contains("/android/m2repository");
    }

    /**
     * 插装字节码
     * @param inputStream
     * @return
     */
    private static byte[] referHackWhenInit(InputStream inputStream) {
        ClassReader cr = new ClassReader(inputStream);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM4, cw) {
            @Override
            MethodVisitor visitMethod(int access, String name, String desc,
                                      String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions)
                mv = new MethodVisitor() {
                    @Override
                    void visitInsn(int opcode) {
                        if ("<init>".equals(name) && opcode == Opcodes.RETURN) {
                            super.visitLdcInsn(Type.getType("Lcn/jiajixin/nuwa/Hack;"));
                        }
                        super.visitInsn(i)
                    }
                }
                return mv;
            }
        };
        cr.accept(cv, 0);
        return cw.toByteArray();
    }


}