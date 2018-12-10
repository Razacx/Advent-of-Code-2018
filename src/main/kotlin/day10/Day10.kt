package day10

import java.awt.*
import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

data class Vector2(val x: Int, val y: Int) {

    operator fun plus(other: Vector2): Vector2 {
        return Vector2(x + other.x, y + other.y)
    }

    operator fun times(scalar: Int): Vector2 {
        return Vector2(x * scalar, y * scalar)
    }

    operator fun times(scalar: Double): Vector2 {
        return Vector2((x * scalar).toInt(), (y * scalar).toInt())
    }

}

data class Particle(var position: Vector2, val velocity: Vector2)

fun parseParticle(line: String): Particle {
    val result = """position=<(.*)> velocity=<(.*)>""".toRegex().matchEntire(line)!!
    val positionTokens = result.groups[1]!!.value.trim().split(" ").filter { it.trim() != "" }.map { it.replace(",", "") }
    val velocityTokens = result.groups[2]!!.value.trim().split(" ").filter { it.trim() != "" }.map { it.replace(",", "") }

    return Particle(
            Vector2(positionTokens[0].toInt(), positionTokens[1].toInt()),
            Vector2(velocityTokens[0].toInt(), velocityTokens[1].toInt())
    )
}

class World(val particles: List<Particle>) {

    var steps = 0

    fun simulate(steps: Int) {
        for (particle in particles) {
            particle.position += particle.velocity * steps
        }
        this.steps += steps
    }

}

fun main(vararg args: String) {

    val particles = BufferedReader(FileReader("input/day10/particles.txt"))
            .lines()
            .map { parseParticle(it) }
            .collect(Collectors.toList())

    val world = World(particles)

    Window(world)

}

class Window(private val world: World) : JFrame("Simulation") {

    private val beginSteps = 10_100
    private val renderComponent: RenderComponent = RenderComponent(world)
    private val stepsLabel: JLabel

    init {
        renderComponent.preferredSize = Dimension(1000, 1000)
        add(renderComponent, BorderLayout.CENTER)

        stepsLabel = JLabel("Steps: $beginSteps")

        val bottomPanel = JPanel(FlowLayout())

        val jButton10 = JButton("Advance 10")
        val jButton1 = JButton("Advance 1")
        jButton10.addActionListener { advanceSimulation(10) }
        jButton1.addActionListener { advanceSimulation(1) }

        bottomPanel.add(jButton1)
        bottomPanel.add(jButton10)
        bottomPanel.add(stepsLabel)

        add(bottomPanel, BorderLayout.SOUTH)

        setSize(1000, 1000)
        defaultCloseOperation = EXIT_ON_CLOSE
        isVisible = true

        advanceSimulation(beginSteps)
    }

    fun advanceSimulation(steps: Int) {
        world.simulate(steps); renderComponent.repaint()
        stepsLabel.text = "Steps: " + world.steps
    }

}

class RenderComponent(private val world: World) : JPanel() {

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        val g2d = g as Graphics2D

        g2d.color = Color.black
        g2d.fillRect(0, 0, width, height)

        g2d.color = Color.white

        world.particles.forEach {

            val screenPosition = it.position
            g2d.fillRect(screenPosition.x + 500, screenPosition.y + 500, 1, 1)

        }
    }

}
