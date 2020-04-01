import com.github.wolpl.events.Event
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.Test

class EventTest {

    @Test
    fun `awaitFire should return when event is fired`() {
        runBlocking {
            val event = Event<Int>()

            val awaiter = async {
                withTimeout(2000) {
                    event.awaitFire()
                }
            }
            delay(500)
            event(1)
            awaiter.await()
        }
    }

    @Test
    fun `awaitFire should return when event is fired with multiple awaiters`() {
        runBlocking {
            val event = Event<Int>()
            val awaiters = (1 until 1000).map {
                async {
                    withTimeout(2000) {
                        event.awaitFire()
                    }
                }
            }
            delay(500)
            event(1)
            awaiters.forEach {
                it.await()
            }
        }
    }
}