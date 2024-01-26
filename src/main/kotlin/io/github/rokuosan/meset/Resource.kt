package io.github.rokuosan.meset


/**
 * Resource class for reading files from resources folder
 */
class Resource {
    fun getString(path: String): String? {
        return this.javaClass
            .classLoader
            .getResourceAsStream(path)
            ?.bufferedReader()
            ?.use {
                return it.readText()
            }
    }
}