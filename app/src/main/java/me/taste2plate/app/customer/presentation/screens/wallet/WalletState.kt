package me.taste2plate.app.customer.presentation.screens.wallet

import me.taste2plate.app.customer.domain.model.custom.Plan
import me.taste2plate.app.customer.domain.model.user.MyPlanModel
import me.taste2plate.app.customer.domain.model.user.WalletTransactionModel

data class WalletState(
    val isLoading:Boolean = false,
    val isError: Boolean = false,
    val message: String? = null,
    val myPlan: MyPlanModel? = null,
    val transactions: List<WalletTransactionModel.Result> = emptyList(),
)
