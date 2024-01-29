package io.github.rokuosan.meset.processor

import io.github.rokuosan.meset.schema.Task

interface Processor {
    val parents: MutableSet<Processor>
    val children: MutableSet<Processor>
    val task: Task

    fun run()
}