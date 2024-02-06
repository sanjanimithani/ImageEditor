package ui.lectures.javafx.mvc.mvcbasic.view

/**
 * Interface for all views that want to subscribe to notifications from the [Model][ui.lectures.javafx.mvc.mvcbasic.model.Model] about changes of its state.
 */
interface View {

    /**
     * Notifies the View that the Model has been changed.
     */
    fun update()
}