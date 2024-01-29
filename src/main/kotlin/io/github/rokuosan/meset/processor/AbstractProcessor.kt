package io.github.rokuosan.meset.processor

import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

abstract class AbstractProcessor: Processor {
    override fun run() = runBlocking {
        children.map { c ->
            launch {
                c.run()
            }
        }.joinAll()
    }
}