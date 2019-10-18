package util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.time.ExperimentalTime
import kotlin.time.MonoClock

@UseExperimental(ExperimentalContracts::class, ExperimentalTime::class)
inline fun <R> withEnterAndExitLog(name: String, block: () -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    println("$name: called")
    val startClockMark = MonoClock.markNow()
    try {
        val returnValue = block()
        println("$name: returned in ${startClockMark.elapsedNow().toLongMilliseconds()}ms")
        return returnValue
    } catch (t: Throwable) {
        println("$name: threw after ${startClockMark.elapsedNow().toLongMilliseconds()}ms")
        throw t
    }
}
