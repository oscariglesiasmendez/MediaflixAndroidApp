package com.mouredev.aristidevslogin.ui.principal.screens.drawermenu_screens.orders

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mouredev.aristidevslogin.data.OrdersRepositoryImpl
import com.mouredev.aristidevslogin.data.model.Client
import com.mouredev.aristidevslogin.data.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.mouredev.aristidevslogin.data.Result

import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class OrdersViewModel(
    private val ordersRepository: OrdersRepositoryImpl
) : ViewModel() {

    private val _orders = MutableStateFlow<Result<List<Order>>?>(null)
    val orders: StateFlow<Result<List<Order>>?> = _orders.asStateFlow()

    fun getOrdersByClient(client: Client) {
        viewModelScope.launch {
            ordersRepository.getAllOrdersByClient(client.clientId).collect { result ->
                _orders.value = result
            }
        }
    }
}