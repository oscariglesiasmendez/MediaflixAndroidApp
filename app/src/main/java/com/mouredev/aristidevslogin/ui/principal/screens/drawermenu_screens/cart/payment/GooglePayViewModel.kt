package com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.cart.payment

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.tasks.Task
import com.google.android.gms.wallet.AutoResolveHelper


class GooglePayViewModel(application: Application) : AndroidViewModel(application) {

    private val paymentsClient: PaymentsClient = PaymentsUtil.createPaymentsClient(application)

    fun getLoadPaymentDataTask(priceCents: Long): Task<PaymentData> {
        val paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents)
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())
        return paymentsClient.loadPaymentData(request)
    }

    fun loadPaymentData(activity: Activity, priceCents: Long) {
        val paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents)
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

        val task = paymentsClient.loadPaymentData(request)
        AutoResolveHelper.resolveTask(task, activity, LOAD_PAYMENT_DATA_REQUEST_CODE)
    }

    fun processPaymentResult(paymentData: PaymentData?) {

    }

    companion object {
        const val LOAD_PAYMENT_DATA_REQUEST_CODE = 991
    }
}
