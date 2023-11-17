package me.taste2plate.app.customer.presentation.screens.my_plan

import me.taste2plate.app.customer.domain.model.custom.Plan
import me.taste2plate.app.customer.domain.model.user.MyPlanModel

data class MyPlanState(
    val isLoading:Boolean = false,
    val isError: Boolean = false,
    val message: String? = null,
    val myPlan: MyPlanModel? = null,
)
