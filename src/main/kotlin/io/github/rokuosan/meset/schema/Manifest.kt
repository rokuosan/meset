package io.github.rokuosan.meset.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Manifest (
    @SerialName("env")
    val env: List<Env>? = null,

    @SerialName("config")
    val config: Config? = null,

    @SerialName("tasks")
    val tasks: List<Task>? = null,
)

@Serializable
sealed class Env {
    @Serializable
    @SerialName("path")
    data class Path(
        val name: String? = null,
        val at: String? = null,
        val location: String? = null,
    ): Env()
}


@Serializable
data class Config(
    val workers: Int
)

@Serializable
sealed class Task{

    @Serializable
    @SerialName("command")
    data class Command(
        val name: String? = null,

        @SerialName("depends_on")
        val dependsOn: List<String>? = null,

        @SerialName("prefix")
        val prefix: String? = null,
        val commands: List<String>,
    ) : Task()

    @Serializable
    @SerialName("file")
    data class File(
        val name: String? = null,

        @SerialName("depends_on")
        val dependsOn: List<String>? = null,

        @SerialName("prefix")
        val prefix: String? = null,
        val filename: String,
        val text: String,
    ) : Task()
}
