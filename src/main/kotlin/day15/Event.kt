package day15

class Event<T> {

    private val handlers = mutableListOf<(T) -> Entity>()

    operator fun plusAssign(handler: (T) -> Entity) {
        handlers.add(handler)
    }

    operator fun invoke(value: T) {
        for(handler in handlers) {
            handler(value)
        }
    }

}