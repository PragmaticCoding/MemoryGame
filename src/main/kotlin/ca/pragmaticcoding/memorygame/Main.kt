package ca.pragmaticcoding.memorygame

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class Main : Application() {
   override fun start(stage: Stage) {
      stage.title = "Memory Game"
      stage.scene = Scene(MemoryGameController().getView()).apply {
         Main::class.java.getResource("styles.css")?.toString()?.let {
            stylesheets += it
            println("Found a stylesheet $it")
         }
      }
      stage.show()
   }
}

fun main() {
   Application.launch(Main::class.java)
}