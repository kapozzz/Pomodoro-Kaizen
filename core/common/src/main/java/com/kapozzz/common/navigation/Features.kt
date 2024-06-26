package com.kapozzz.common.navigation

object Features {
    object Timer {

        const val NESTED_ROUTE = "timer_nested_route"

        const val SCREEN_ROUTE = "timer_screen_route?id={id}"

        private const val SCREEN_ROUTE_WITHOUT_ID = "timer_screen_route"

        fun createTimerRouteWithId(id: String?): String {
            return SCREEN_ROUTE_WITHOUT_ID + if (id != null) "?id=$id" else ""
        }

    }
    object Tasks {

        const val NESTED_ROUTE = "tasks_feature_nested_route"

        const val TASKS_ROUTE = "tasks_route"

        const val CREATE_TASK_ROUTE = "create_task?id={id}"

        private const val CREATE_TASK_ROUTE_WITHOUT_ID = "create_task"

        fun createTaskRouteWithId(id: String?): String {
            return CREATE_TASK_ROUTE_WITHOUT_ID + if (id != null) "?id=$id" else ""
        }

    }
}

