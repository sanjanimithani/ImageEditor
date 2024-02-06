package ui.lectures.javafx.mvc.mvcbasic.view

import javafx.event.EventHandler
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.stage.FileChooser
import javafx.stage.Stage
import ui.lectures.javafx.mvc.mvcbasic.model.Model
import java.io.File
import java.io.FileInputStream
import kotlin.math.floor


class Canvas (private val stage: Stage, private val model: Model) : ScrollPane(), View {
    private var offsetX = 0.0
    private var offsetY = 0.0
    private val pane = Pane()

    init {
        model.addView(this)
        this.hbarPolicy = ScrollBarPolicy.AS_NEEDED
        this.vbarPolicy = ScrollBarPolicy.AS_NEEDED


        update()

        pane.onMouseClicked = EventHandler {
            if (it.target == pane) {
                model.setImageSelected(ImageView())
            }
            it.consume()
        }

        this.onMouseClicked = EventHandler {
            if (it.target != pane) {
                model.setImageSelected(ImageView())
                it.consume()
            }
        }
        this.content = pane
    }

    override fun update() {

        pane.children.clear()


        if (model.isTileMode()) {
            tileImages()

            if (model.getAddNewImage()) {
                addNewImage()
            }

        } else {
            val imageList = model.getImages()
            for (imageView in imageList) {
                val image = imageView.key
                image.makeDraggable()
                pane.children.add(image)
            }

            if (model.getAddNewImage()) {
                addNewImage()
            }

            model.getSelectedImage().toFront()

            if (model.getRotateRight()) {
                model.getSelectedImage().rotate += 10
            }

            if (model.getRotateLeft()) {
                model.getSelectedImage().rotate -= 10
            }

            if (model.getZoomIn()) {
                zoomIn(model.getSelectedImage())
            }

            if (model.getZoomOut()) {
               zoomOut(model.getSelectedImage())
            }

            if (model.getReset()) {
                val image = model.getSelectedImage()
                resetImages(image)
            }
        }
    }

    fun ImageView.clone(): ImageView {
        val copy = ImageView(this.image)
        copy.fitWidth = this.fitWidth
        copy.fitHeight = this.fitHeight
        copy.rotate = this.rotate
        return copy
    }

    private fun tileImages() {
        val sorted = model.getImages().keys.sortedBy { it.fitHeight }.reversed()
        val map = mutableMapOf<ImageView, ImageView>()

        val paneWidth = this.width
        println(paneWidth)
        val numBoxes = floor(paneWidth / (model.getFixedWidth() + 10.0)).toInt()
        val hbox = HBox()

        for (i in (1..numBoxes)) {
            val vbox = VBox()
            var index = 0
            for (image in sorted) {
                if ((index % numBoxes) + 1 == i) {
                    val clone = image.clone()
                    resetImages(clone)
                    clone.removeDraggable()
                    vbox.children.add(clone)
                    map[clone] = image
                }
                index += 1
            }
            vbox.spacing = 10.0
            //pane.children.add(vbox)
            hbox.children.add(vbox)
        }
        hbox.spacing = 10.0
        pane.children.add(hbox)
        model.setTiledImagesMap(map)
    }

    private fun addNewImage() {

        val fileDialog = FileChooser()
        val fileDialogResult = fileDialog.showOpenDialog(stage)
        fileDialogResult ?: return

        println(fileDialogResult)
        val location = fileDialogResult.toString()
        println(location)

        val curr = File(location)
        val ext = curr.extension

        println(ext)

        if ((ext == "png") or (ext == "jpg") or (ext == "jpeg") or (ext == "bmp")) {
            val image = Image(FileInputStream(curr.absolutePath))
            val imageViewer = ImageView(image)

            val originalWidth = image.width
            val originalHeight = image.height

            imageViewer.fitWidth = model.getFixedWidth()
            val scaleFactor = model.getFixedWidth() / originalWidth
            imageViewer.fitHeight = originalHeight * scaleFactor

            val width = model.getFixedWidth()
            val height = originalHeight * scaleFactor

            val maxX = this.width - width
            var maxY = this.height - height

            if (maxY <= 0.0) {
                maxY = this.height
            }

            val posX = kotlin.random.Random.nextDouble(0.0, maxX)
            val posY = kotlin.random.Random.nextDouble(0.0, maxY)

            imageViewer.layoutX = posX
            imageViewer.layoutY = posY

            println("X: " + imageViewer.x + " Y: " + imageViewer.y)

            model.addImageToMap(imageViewer, curr.name)
        }
    }

    private fun zoomIn(image: ImageView) {
        val originalX = image.scaleX
        val originalY = image.scaleY

        if ((originalX <= 7.0) and (originalY <= 7.0)) {
            image.scaleX = originalX * 1.25
            image.scaleY = originalY * 1.25
        }
    }

    private fun zoomOut(image: ImageView) {
        val originalX = image.scaleX
        val originalY = image.scaleY

        if ((originalX >= 0.3) and (originalY >= 0.3)) {
            image.scaleX = originalX * 0.8
            image.scaleY = originalY * 0.8
        }
    }

    private fun resetImages(image: ImageView) {
        image.scaleX = 1.0
        image.scaleY = 1.0
        image.fitWidth = model.getFixedWidth()
        val scaleFactor = model.getFixedWidth() / image.image.width
        image.fitHeight = image.image.height * scaleFactor
        image.rotate = 0.0
    }

    private fun ImageView.removeDraggable() {
        this.onMousePressed = EventHandler {
            model.setImageSelected(this)
            it.consume()
        }
        this.onMouseDragged = null
    }

    private fun ImageView.makeDraggable() {
        this.onMousePressed = EventHandler {
            offsetX = this.x - it.sceneX
            offsetY = this.y - it.sceneY
            model.setImageSelected(this)
            it.consume()
        }

        this.onMouseDragged = EventHandler {
            this.x = it.sceneX + offsetX
            this.y = it.sceneY + offsetY
            it.consume()
        }

    }

}