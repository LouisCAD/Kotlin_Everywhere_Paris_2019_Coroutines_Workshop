import kotlinx.coroutines.delay
import util.withEnterAndExitLog

suspend fun main() {
    withEnterAndExitLog("todo") {
        delay(1000)
        TODO()
    }
}
