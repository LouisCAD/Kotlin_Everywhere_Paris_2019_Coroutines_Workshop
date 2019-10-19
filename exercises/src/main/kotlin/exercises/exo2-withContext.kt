package exercises

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

suspend fun playWithWithContext() {
    withEnterAndExitLog(tag = "out of withContext") {
        TODO("Use withContext inside and log result of answerToTheUltimateQuestionBlocking() inside")
    }
}

/**
 * **Warning:** this function is blocking (but not for too long).
 *
 * Use Dispatchers.Default to execute it because it needs CPU computations.
 */
private fun answerToTheUltimateQuestionBlocking(): Int {
    var result = Int.MAX_VALUE
    while (result > 42) result--
    return result
}

/**
 * Convert to a suspending function ASAP to keep your code clean.
 */
private suspend fun answerToTheUltimateQuestion(): Int = withContext(TODO("replace me")) {
    answerToTheUltimateQuestionBlocking()
}

private suspend fun pretendWeDoInputOutputLikeWritingAFile() = withContext(Dispatchers.IO) {
    val currentDir = File(".")
    val exampleFile = currentDir.resolve("example.txt")
    Thread.sleep(300) // Could write a file instead, using file.writeText(), give it a try?
}
