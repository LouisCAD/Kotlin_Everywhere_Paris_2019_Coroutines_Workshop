package exercises

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.random.Random
import kotlin.time.minutes
import kotlin.time.seconds

suspend fun playWithCoroutineScopes() {
    withEnterAndExitLog("coroutineScope") {
        repeat(times = 4) { // Repeat is optional, drop it if you want.
            coroutineScope {
                TODO("launch several coroutines here using the functions below, and yours")
            }
        }
    }
}

/**
 * Since this function takes a long time to execute, you'll want to cancel it.
 * [launch] returns a [Job] on which you can call [Job.cancel] to do so.
 *
 * All children with also be cancelled. Try making a little hierarchy, and cancel it all.
 */
private suspend fun cookSoftBoiledEggs() {
    val eggsWereStolen = getRandomBoolean()
    if (eggsWereStolen) {
        withContext(NonCancellable) {
            // Writing the lawsuit is super important.
            val currentDir = File(".")
            waitForInkFlowDownThePen()
            currentDir.writeText("Lawsuit: Eggs have been stolen!")
            throw IllegalStateException("Eggs were stolen! Can't cook them!")
        }
    }
    waitDuration(4.minutes) // Let's pretend waiting is enough
}

private suspend fun buyIceCreamCan(
    brandName: String,
    flavor: String,
    quantity: Int
) {
    require(quantity in 1..10_000)
    waitDuration(2.seconds)
    log("Yeah, yeah, we bought $quantity $brandName $flavor ice cream cans")
}


private suspend fun getRandomBoolean(): Boolean {
    delay(timeMillis = 10) // Wait for randomness gods to be ready from across the sky.
    return Random.nextBoolean()
}

/**
 * As often, this function is cancellable. This is because it's made of cancellable functions.
 */
private suspend fun waitForInkFlowDownThePen() {
    // Shake the pen in the future to improve effectivity.
    delay(timeMillis = 1) // Magic number ! 🌈
}
