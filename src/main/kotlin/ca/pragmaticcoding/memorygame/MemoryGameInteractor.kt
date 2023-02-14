package ca.pragmaticcoding.memorygame

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty

class MemoryGameInteractor(val model: MemoryGameModel) {

   private val numCards = 5
   private var firstClick: Int? = null
   private var secondClick: Int? = null

   init {
      for (idx in 1..numCards * 2) {
         model.cards += SimpleIntegerProperty(0)
         model.faceUp += SimpleBooleanProperty(false)
      }
      startGame()
   }

   fun startGame() {
      println("Starting Game")
      val cards = (1..52).shuffled().slice(0..4)
      (cards + cards).shuffled().forEachIndexed { index, gameCard ->
         model.cards[index].value = gameCard
         model.faceUp[index].value = false
      }
      println("The Cards: ${model.cards}")
      model.guesses.value = 0
      model.matches.value = 0
   }

   fun flipCard(index: Int) {
      if (model.faceUp[index].value == false) {
         if (firstClick == null) {
            firstClick = index
         } else if (secondClick == null) {
            secondClick = index
            if (model.cards[firstClick!!].value == model.cards[secondClick!!].value) {
               model.matches.value++
            }
         } else {
            if (model.cards[firstClick!!].value != model.cards[secondClick!!].value) {
               model.faceUp[firstClick!!].value = false
               model.faceUp[secondClick!!].value = false
            }
            firstClick = index
            secondClick = null
         }
         model.faceUp[index].value = true
         model.guesses.value++
      }
   }
}