package com.book.orders

data class Order(val id: Long, val price: Double, val side: Char, val size: Long)

class OrderBook {
  private val orders = mutableListOf<Order>()

  fun add(order: Order) {
    orders += order
  }

  fun fetchAll(): List<Order> = orders
  fun remove(orderId: Long) {
    orders.removeAll { it.id == orderId }
  }
}