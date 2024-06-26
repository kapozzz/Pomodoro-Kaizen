package com.kapozzz.domain.models

data class Step(
    val title: String,
    val description: String,
    var isCompleted: Boolean
) {
    companion object {
        fun getDefault(): Step {
            return Step(
                title = "empty_step",
                description = "",
                isCompleted = false
            )
        }
        fun getCompletedEmptyStep(): Step {
            return Step(
                title = "",
                description = "",
                isCompleted = true
            )
        }
    }
}
