package ui.lectures.javafx.mvc.mvcbasic

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import ui.lectures.javafx.mvc.mvcbasic.model.Model
import ui.lectures.javafx.mvc.mvcbasic.view.StatusBar
import ui.lectures.javafx.mvc.mvcbasic.view.Toolbar
import ui.lectures.javafx.mvc.mvcbasic.view.Canvas

class HelloBasicMVC : Application() {
    override fun start(stage: Stage) {

        val myModel = Model()
        val toolbar = Toolbar(myModel)
        val status = StatusBar(myModel)
        val canvas = Canvas(stage, myModel)

        val root = BorderPane().apply {
            center = canvas
            bottom = status
            top = toolbar
        }

        stage.apply {
            title = "Lightbox (c) 2023 Sanjani Mithani"
            scene = Scene(root).apply {
                maxHeight = 1600.0
                maxWidth = 1200.0
                minWidth = 700.0 // Will always be larger than 2 images beside each other
                minHeight = 500.0

            }
        }.show()

    }
}