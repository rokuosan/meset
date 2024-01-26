package io.github.rokuosan.meset.processor

import io.github.rokuosan.meset.schema.Task

class FileProcessor(
    override val task: Task.File,
    override val parents: List<Processor>,
    override val children: List<Processor>
): Processor {
    override fun run() {
        println("File")
    }
}