package io.github.rokuosan.meset.processor

import io.github.rokuosan.meset.schema.Task

class CommandProcessor(
    override val task: Task.Command,
    override val parents: MutableSet<Processor>,
    override val children: MutableSet<Processor>
): Processor, AbstractProcessor() {
    override fun run() {

        println("Hello from ${task.name}")

        super.run()
    }
}