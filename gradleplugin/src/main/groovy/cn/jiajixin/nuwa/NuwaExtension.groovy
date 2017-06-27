import org.gradle.api.Project

/**
 *
 */
class NuwaExtension {
    HashSet<String> includePackage = []
    HashSet<String> excludeClass = []
    boolean debugOn = true

    NuwaExtension(Project project) {
    }
}