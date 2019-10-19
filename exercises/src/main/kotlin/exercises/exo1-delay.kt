package exercises

import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

suspend fun playWithDelay() {
    withEnterAndExitLog("playWithDelay") {
        TODO()
    }
}

/**
 * You can also try this one we built, using experimental kotlin.time
 */
@ExperimentalTime
suspend fun waitDuration(duration: Duration = 0.seconds) = delay(duration.toLongMilliseconds())
