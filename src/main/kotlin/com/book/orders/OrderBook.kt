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

  fun update(orderId: Long, size: Long) {
    val existingOrder = orders.find { it.id == orderId }
    val newOrder = existingOrder?.copy(size = size)

    newOrder?.let {
      remove(orderId)
      add(newOrder)
    }
  }
}