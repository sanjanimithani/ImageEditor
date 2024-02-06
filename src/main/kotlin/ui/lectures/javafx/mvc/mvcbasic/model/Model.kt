package ui.lectures.javafx.mvc.mvcbasic.model

import javafx.scene.image.ImageView
import ui.lectures.javafx.mvc.mvcbasic.view.View

class Model {

    private val views = mutableListOf<View>()
    private val imagesMap = mutableMapOf<ImageView, String>()
    private var tiledImagesMap = mutableMapOf<ImageView, ImageView>()
    private var selectedImage = ImageView()
    private val fixedWidth = 300.0
    private var statusText: String? = ""
    private var rotateRight = false
    private var rotateLeft = false
    private var zoomIn = false
    private var zoomOut = false
    private var reset = false
    private var tileMode = false
    private var addImage = false

    fun addView(view: View) {
        views.add(view)
    }
    fun getStatusText() : String? {
        return if (statusText == "") {
            "${imagesMap.size} image(s) uploaded"
        } else {
            statusText
        }

    }

    fun getNumberOfImages(): Int {
        return imagesMap.size
    }

    fun isImageSelected(): Boolean {
        return statusText != ""

    }

    fun isTileMode() : Boolean {
        return tileMode
    }

    fun deleteImage() {
        imagesMap.remove(selectedImage)
        selectedImage = ImageView()
        statusText = ""
        views.forEach { it.update() }
    }

    fun rotateImageRight() {
        rotateRight = true
        views.forEach { it.update() }
        rotateRight = false
    }

    fun getRotateRight(): Boolean {
        return rotateRight
    }

    fun rotateImageLeft() {
        rotateLeft = true
        views.forEach { it.update() }
        rotateLeft = false
    }

    fun getRotateLeft(): Boolean {
        return rotateLeft
    }

    fun zoomIn() {
        zoomIn = true
        views.forEach { it.update() }
        zoomIn = false
    }

    fun getZoomIn(): Boolean {
        return zoomIn
    }

    fun zoomOut() {
        zoomOut = true
        views.forEach { it.update() }
        zoomOut = false
    }

    fun getZoomOut(): Boolean {
        return zoomOut
    }

    fun resetImage() {
        reset = true
        views.forEach { it.update() }
        reset = false
    }

    fun getReset(): Boolean {
        return reset
    }

    fun getImages(): MutableMap<ImageView, String> {
        return imagesMap
    }

    fun getSelectedImage() : ImageView {
        return selectedImage
    }
    fun setImageSelected(image : ImageView) {
        if (tileMode) {
            println("in here")
            selectedImage = tiledImagesMap[image]!!
            val name = imagesMap[tiledImagesMap[image]]
            statusText = name ?: ""

        } else {
            selectedImage = image
            val name = imagesMap[image]
            statusText = name ?: ""
        }
        views.forEach { it.update() }
    }


    fun getFixedWidth(): Double {
        return fixedWidth
    }

    fun cascadeImages() {
        tileMode = false
        selectedImage = ImageView()
        statusText = ""
        views.forEach { it.update() }
    }

    fun tileImages() {
        tileMode = true
        selectedImage = ImageView()
        statusText = ""
        views.forEach { it.update() }
    }

    fun setTiledImagesMap(map: MutableMap<ImageView, ImageView>) {
        tiledImagesMap.clear()
        tiledImagesMap = map
    }

    fun addNewImage() {
        addImage = true
        views.forEach { it.update() }
    }

    fun getAddNewImage() : Boolean {
        return addImage
    }

    fun addImageToMap(image: ImageView, name : String) {
        imagesMap[image] = name
        addImage = false
        views.forEach { it.update() }
    }

}