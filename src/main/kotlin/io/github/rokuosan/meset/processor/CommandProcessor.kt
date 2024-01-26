package io.github.rokuosan.meset.processor

import io.github.rokuosan.meset.schema.Task

class CommandProcessor(
    override val task: Task.Command,
    override val parents: List<Processor>,
    override val children: List<Processor>
): Processor {
    override fun run() {
        println("Command")
    }
}