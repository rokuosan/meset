package io.github.rokuosan.meset.context

import com.charleskorn.kaml.PolymorphismStyle
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import io.github.rokuosan.meset.Resource
import io.github.rokuosan.meset.schema.Manifest

data class Environment(
    val home: String,
    val yamlLoader: Yaml,
    val resourceLoader: Resource = Resource(),
    val manifest: Manifest? = null,
) {
    companion object {
        fun getDefault(): Environment{
            // Get home
            val home = System.getProperty("user.home")?.plus("/.meset")
                ?: throw RuntimeException("could not get user home")

            // Yaml
            val yaml = Yaml(
                configuration = YamlConfiguration(
                    polymorphismStyle = PolymorphismStyle.Property,
                    polymorphismPropertyName = "type"
                )
            )

            return Environment(
                home = home,
                yamlLoader = yaml,
            )
        }
    }
}