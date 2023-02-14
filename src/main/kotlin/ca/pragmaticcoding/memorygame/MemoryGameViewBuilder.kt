package ca.pragmaticcoding.memorygame

import javafx.beans.binding.Bindings
import javafx.beans.property.BooleanProperty
import javafx.beans.property.IntegerProperty
import javafx.beans.property.ReadOnlyIntegerProperty
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.Rectangle2D
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.util.Builder

class MemoryGameViewBuilder(private val model: MemoryGameModel,
                            private val clickHandler: (Int) -> Unit,
                            private val buttonHandler: Runnable) : Builder<Region> {

   private val cardImages = this::class.java.getResource("card-deck.png")?.toString()?.let { Image(it) }
   private val gridWidth = 13
   private val cardWidth = 147.7
   private val cardHeight = 230.5
   override fun build(): Region = BorderPane().apply {
      center = FlowPane().apply {
         model.cards.forEachIndexed { index, card ->
            children += createCardImageView(card, model.faceUp[index], index)
         }
         vgap = 10.0
         hgap = 10.0
         alignment = Pos.CENTER
      }
      bottom = createBottom()
      minWidth = 840.0
      minHeight = 580.0
      padding = Insets(20.0)
      this::class.java.getResource("styles.css")?.toString()?.let { stylesheets += it }
      styleClass += "main-pane"
   }

   private fun createBottom() = HBox(120.0).apply {
      children += GridPane().apply {
         add(Label("Guesses:"), 0, 0)
         add(Label("Matches:"), 0, 1)
         add(labelOf(model.guesses), 1, 0)
         add(labelOf(model.matches), 1, 1)
      }
      children += Button("Play Again").apply {
         onAction = EventHandler { buttonHandler.run() }
      }
      alignment = Pos.CENTER

   }

   private fun labelOf(boundValue: ReadOnlyIntegerProperty) = Label().apply {
      textProperty().bind(boundValue.asString())
   }

   private fun createCardImageView(card: IntegerProperty, faceUp: BooleanProperty, index: Int) =
      ImageView(cardImages).apply {
         viewportProperty().bind(Bindings.createObjectBinding({ determineViewPort(card.value, faceUp.value) },
                                                              card,
                                                              faceUp))
         setOnMouseClicked { clickHandler(index) }
      }

   private fun determineViewPort(card: Int, faceUp: Boolean): Rectangle2D =
      if (faceUp) getSprite(card) else getSprite(55)

   private fun getSprite(card: Int): Rectangle2D {
      val row = if ((card % gridWidth) == 0) (card / gridWidth) - 1 else card / gridWidth
      val col = if (((card % gridWidth) - 1) < 0) 9 else ((card % gridWidth) - 1)
      return Rectangle2D(col * cardWidth, row * cardHeight, cardWidth, cardHeight)
   }
}
