package com.kapozzz.domain.models

data class Step(
    val title: String,
    val description: String,
    val isCompleted: Boolean
) {
    companion object {
        fun getDefault(): Step {
            return Step(
                title = "",
                description = "",
                isCompleted = false
            )
        }
    }
}
