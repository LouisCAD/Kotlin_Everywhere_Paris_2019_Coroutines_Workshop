package util

import kotlinx.coroutines.CancellationException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.time.ExperimentalTime
import kotlin.time.MonoClock

/**
 * Logs when the [block] enters and exits (be it by throwing or returning).
 * Also logs the execution time in milliseconds.
 */
@UseExperimental(ExperimentalContracts::class, ExperimentalTime::class)
inline fun <R> withEnterAndExitLog(tag: String, block: () -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    println("$tag: called")
    val startClockMark = MonoClock.markNow()
    try {
        val returnValue = block()
        println("$tag: returned in ${startClockMark.elapsedNow().toLongMilliseconds()}ms")
        return returnValue
    } catch (t: Throwable) {
        val millis = startClockMark.elapsedNow().toLongMilliseconds()
        if (t is CancellationException) {
            println("$tag: was cancelled after ${millis}ms")
        } else {
            println("$tag: threw after ${millis}ms")
        }
        throw t
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun log(message: Any?) = println(message)
