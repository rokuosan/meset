package io.github.rokuosan.meset.processor

import io.github.rokuosan.meset.schema.Task

object ProcessorManager {
    private val processors: MutableList<Processor> = mutableListOf()

    /**
     * Subscribe all tasks to processor manager
     *
     * @param tasks List of tasks
     */
    fun subscribe(tasks: List<Task>, parents: List<Processor> = emptyList()) {
        if (tasks.isEmpty()) return

        val targets = mutableListOf<Task>()
        val children = mutableListOf<Task>()
        tasks.forEach {
            if (it.dependsOn.isNullOrEmpty()) {
                targets.add(it)
            } else {
                children.add(it)
            }
        }
        for (target in targets) {
            val c = mutableListOf<Task>()
            for (child in children) {
                if (child.dependsOn?.contains(target.name) == true) {
                    c.add(child)
                }
            }
            for (child in c){
                children.remove(child)
                children.add(
                    when (child) {
                        is Task.Command -> child.copy(dependsOn = child.dependsOn?.filter { it != target.name })
                        is Task.File -> child.copy(dependsOn = child.dependsOn?.filter { it != target.name })
                    }
                )
            }
            processors.add(target.toProcessor(parents = parents, children = c.map { it.toProcessor() }))
        }

        subscribe(children, targets.map { it.toProcessor() })
    }

    fun find(name: String): Processor? = processors.find { it.task.name == name }

    fun runAll() {
        processors
            .filter { it.parents.isEmpty() }
            .forEach {
            it.run()
        }
    }
}

fun Task.toProcessor(parents: List<Processor> = emptyList(), children: List<Processor> = emptyList()): Processor {
    return when (this) {
        is Task.Command -> CommandProcessor(this, parents, children)
        is Task.File -> FileProcessor(this, parents, children)
    }
}