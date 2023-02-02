import AppDeps.appModules
import Constants.JDL_FILE
import Constants.WEBAPP
import Constants.WEBAPP_SRC
import Constants.sep
import Constants.BLANK
import Constants.DELIM
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.add
import org.gradle.kotlin.dsl.exclude
import java.io.File
import java.util.*
import kotlin.text.Charsets.UTF_8


object BuildTools {

    /*=================================================================================*/
    @JvmStatic
    val Project.webAppSrc
        get() = StringTokenizer(properties[WEBAPP_SRC].toString(), DELIM)
            .toList()
            .map { it.toString() }

    /*=================================================================================*/
    @JvmStatic
    fun Copy.move(
        path: String,
        from: String,
        into: String
    ) {
        from(when {
            project.layout
                .projectDirectory
                .dir(from)
                .asFileTree
                .first { it.name == path }
                .isDirectory -> project.layout
                .projectDirectory
                .dir(from)
                .dir(path)
            else -> project.layout
                .projectDirectory
                .dir(from)
                .file(path)
        })
        into(project.layout.projectDirectory.dir(into))
    }

    /*=================================================================================*/
    @JvmStatic
    fun Project.dependency(entry: Map.Entry<String, String?>) = entry.run {
        key + when (value) {
            null -> BLANK
            BLANK -> BLANK
            else -> ":${properties[value]}"
        }
    }

    /*=================================================================================*/
    @JvmStatic
    fun Project.androidDependencies() {
        appModules.forEach { module ->
            module.value.forEach {
                when (it.key) {
                    "androidx.test.espresso:espresso-core" ->
                        dependencies.add(module.key, dependency(it)) {
                            exclude("com.android.support", "support-annotations")
                        }
                    else -> dependencies.add(module.key, dependency(it))
                }
            }
        }
    }

    /*=================================================================================*/
    @JvmStatic
    fun File.displayJdl() {
        listFiles()
            ?.first { it.name == WEBAPP }
            ?.listFiles()
            ?.first { it.name == JDL_FILE }
            ?.readText(UTF_8)
            .run { println("$WEBAPP$sep$JDL_FILE:\n$this") }
    }
}
/*=================================================================================*/