package ca.pragmaticcoding.memorygame

import javafx.scene.layout.Region
import javafx.util.Builder

class MemoryGameController {

   private val viewBuilder: Builder<Region>
   private val interactor: MemoryGameInteractor
   private val model = MemoryGameModel()

   init {
      interactor = MemoryGameInteractor(model)
      viewBuilder = MemoryGameViewBuilder(model, { index -> interactor.flipCard(index) }) { interactor.startGame() }
   }

   fun getView(): Region = viewBuilder.build()
}