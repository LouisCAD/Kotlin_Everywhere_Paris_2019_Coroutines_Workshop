import exercises.log
import exercises.withEnterAndExitLog

fun main() {
    withEnterAndExitLog("main") {
        log("Workshop has just started, and it'll be a lot of fun!")
    }
    withEnterAndExitLog("todo") {
        TODO()
    }
}
