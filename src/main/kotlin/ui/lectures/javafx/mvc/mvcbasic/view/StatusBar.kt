package ui.lectures.javafx.mvc.mvcbasic.view

import javafx.geometry.Insets
import javafx.scene.control.Label
import ui.lectures.javafx.mvc.mvcbasic.model.Model

class StatusBar(private val model: Model) : Label(), View {
    init {
        model.addView(this) // subscribe to the Model
        this.padding = Insets(1.0, 1.0, 1.0, 5.0)
        update() // call to set initial text
    }

    override fun update() {
        text = model.getStatusText() // set text of label if notified by the Model
    }
}