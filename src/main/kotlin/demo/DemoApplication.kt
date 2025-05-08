package demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QmDemoApplication

fun main(args: Array<String>) {
    runApplication<QmDemoApplication>(*args)
}
