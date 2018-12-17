package day15

import day06.Coordinates
import java.lang.IllegalArgumentException

class EntityFactory(val world: World) {

    inline fun <reified K: Entity> createEntity(id: Int, position: Coordinates, ap: Int, hp: Int): K {
        val entity = when (K::class) {
            Elf::class -> Elf(id, world, position, ap, hp) as K
            Goblin::class -> Goblin(id, world, position, ap, hp) as K
            else -> throw IllegalArgumentException("Unable to create Entity of type ${K::class.simpleName}")
        }
        world.entities.add(entity)

        entity.onDeath += { world.entities.remove(it) } // Remove entity when it dies

        return entity
    }

}