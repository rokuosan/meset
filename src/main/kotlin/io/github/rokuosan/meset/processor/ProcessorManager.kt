package io.github.rokuosan.meset.processor

import io.github.rokuosan.meset.schema.Task
import kotlinx.coroutines.*

object ProcessorManager {
    private val processors: MutableSet<Processor> = mutableSetOf()

    private enum class State {
        READY,
        RUNNING,
        DONE
    }

    private var managerState = State.READY
    private val states = mutableMapOf<Processor, State>()

    fun subscribe(tasks: List<Task>) {
        val ps = tasks.map { it.toProcessor() }

        ps.forEach { p ->
            val children = ps.filter { it.task.dependsOn?.contains(p.task.name) == true }
            p.children.addAll(children)
            children.forEach { c ->
                c.parents += p
            }
            states[p] = State.READY
        }

        processors.addAll(ps)
    }

    fun find(name: String): Processor? = processors.find { it.task.name == name }

    fun runAll() = runBlocking {
        if (managerState == State.RUNNING) this.cancel()

        processors.filter { it.parents.isEmpty() }
            .map {
                launch {
                    states[it] = State.RUNNING
                    it.run()
                    states[it] = State.DONE
                }
            }.joinAll()

        managerState = State.RUNNING
    }
}

fun Task.toProcessor(
    parents: MutableSet<Processor> = mutableSetOf(),
    children: MutableSet<Processor> = mutableSetOf()
): Processor {
    return when (this) {
        is Task.Command -> CommandProcessor(this, parents, children)
        is Task.File -> FileProcessor(this, parents, children)
    }
}

fun Processor.toS(): String {
    return "Processor(task=${this.task.name}, parents=${this.parents.joinToString(", ", transform = { it.task.name })}, children=${this.children.joinToString(", ", transform = { it.task.name })})"
}