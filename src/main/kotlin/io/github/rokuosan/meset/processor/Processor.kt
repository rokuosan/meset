package io.github.rokuosan.meset.processor

import io.github.rokuosan.meset.schema.Task

interface Processor {
    val parents: List<Processor>
    val children: List<Processor>
    val task: Task

    fun run()
}