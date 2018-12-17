package day15

class Event<T> {

    private val handlers = mutableListOf<(T) -> Unit>()

    operator fun plusAssign(handler: (T) -> Unit) {
        handlers.add(handler)
    }

    operator fun invoke(value: T) {
        for(handler in handlers) {
            handler(value)
        }
    }

}