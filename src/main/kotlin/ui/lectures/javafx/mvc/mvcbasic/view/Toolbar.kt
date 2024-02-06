package ui.lectures.javafx.mvc.mvcbasic.view

import javafx.geometry.Orientation
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import ui.lectures.javafx.mvc.mvcbasic.model.Model

class Toolbar (private val model: Model) : ToolBar(), View {

    private val addImageButton = Button("Add Image", ImageView("add.png")).apply {
        addEventHandler(MouseEvent.MOUSE_CLICKED) {
            model.addNewImage()
        }
    }

    private val deleteImageButton = Button("Delete Image", ImageView("delete.png")).apply {
        addEventHandler(MouseEvent.MOUSE_CLICKED) {
            model.deleteImage()
        }
    }

    private val rotateRightButton = Button("Rotate Right", ImageView("rotate-right.png")).apply {
        addEventHandler(MouseEvent.MOUSE_CLICKED) {
            model.rotateImageRight()
        }
    }

    private val rotateLeftButton = Button("Rotate Left", ImageView("rotate-left.png")).apply {
        addEventHandler(MouseEvent.MOUSE_CLICKED) {
            model.rotateImageLeft()
        }
    }

    private val zoomInButton = Button("Zoom In", ImageView("zoom-in.png")).apply {
        addEventHandler(MouseEvent.MOUSE_CLICKED) {
            model.zoomIn()
        }
    }

    private val zoomOutButton = Button("Zoom Out", ImageView("zoom-out.png")).apply {
        addEventHandler(MouseEvent.MOUSE_CLICKED) {
            model.zoomOut()
        }
    }

    private val resetButton = Button("Reset", ImageView("reset.png")).apply {
        addEventHandler(MouseEvent.MOUSE_CLICKED) {
            model.resetImage()
        }
    }

    private val tileButton = ToggleButton("Tile", ImageView("tile.png")).apply{
        addEventHandler(MouseEvent.MOUSE_CLICKED) {
            model.tileImages()
        }
    }

    private val cascadeButton = ToggleButton("Cascade", ImageView("cascade.png")).apply{
        addEventHandler(MouseEvent.MOUSE_CLICKED) {
            model.cascadeImages()
        }
    }

    private val modeGroup = HBox()
    private val toggleGroup = ToggleGroup()
    private val hLine = Separator()

    init {
        model.addView(this) // subscribe to the Model

        cascadeButton.toggleGroup = toggleGroup
        tileButton.toggleGroup = toggleGroup
        modeGroup.children.addAll(cascadeButton, tileButton)
        modeGroup.spacing = 5.0

        hLine.orientation = Orientation.VERTICAL

        items.addAll(addImageButton, deleteImageButton, rotateRightButton,
            rotateLeftButton, zoomInButton, zoomOutButton, resetButton, hLine, modeGroup)

        update() // call to set initial text
    }

    override fun update() {

        if (model.isTileMode()) {
            tileButton.isSelected = true
            disableTransformations()

            deleteImageButton.isDisable = !model.isImageSelected()

        } else {
            cascadeButton.isSelected = true
            println(model.isImageSelected())
            if (model.isImageSelected()) {
                enableTransformations()
                deleteImageButton.isDisable = false
            } else {
                disableTransformations()
                deleteImageButton.isDisable = true
            }
        }
    }
    private fun disableTransformations() {
        rotateRightButton.isDisable = true
        rotateLeftButton.isDisable = true
        zoomInButton.isDisable = true
        zoomOutButton.isDisable = true
        resetButton.isDisable = true
    }
    private fun enableTransformations() {
        rotateRightButton.isDisable = false
        rotateLeftButton.isDisable = false
        zoomInButton.isDisable = false
        zoomOutButton.isDisable = false
        resetButton.isDisable = false
    }
}
