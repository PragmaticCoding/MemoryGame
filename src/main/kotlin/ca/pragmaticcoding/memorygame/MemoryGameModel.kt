package ca.pragmaticcoding.memorygame

import javafx.beans.property.BooleanProperty
import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList

class MemoryGameModel {
   val cards: ObservableList<IntegerProperty> = FXCollections.observableArrayList()
   val faceUp: ObservableList<BooleanProperty> = FXCollections.observableArrayList()
   val guesses: IntegerProperty = SimpleIntegerProperty(0)
   val matches: IntegerProperty = SimpleIntegerProperty(0)
}