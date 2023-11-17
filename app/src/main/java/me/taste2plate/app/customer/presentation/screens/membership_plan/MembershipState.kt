package me.taste2plate.app.customer.presentation.screens.membership_plan

import me.taste2plate.app.customer.domain.model.custom.Plan

data class MembershipState(
    val isLoading:Boolean = false,
    val isError: Boolean = false,
    val message: String? = null,
    val plans: List<Plan> = emptyList(),
)
