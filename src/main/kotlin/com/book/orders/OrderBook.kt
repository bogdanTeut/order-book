package com.book.orders

data class Order(val id: Long, val price: Double, val side: Char, val size: Long)

class OrderBook {
  private val ordersMap = mutableMapOf<Long, Order>()

  fun add(order: Order) {
    ordersMap[order.id] = order
  }

  fun fetchAll(): List<Order> = ordersMap.values.toList()
  fun remove(orderId: Long) {
    ordersMap.remove(orderId)
  }

  fun update(orderId: Long, size: Long) {
    ordersMap.computeIfPresent(orderId) { _, order -> order.copy(size = size) }
  }

  fun price(side: Char, level: Int): Double {
    val (bids, offers) = ordersMap.values
      .partition { it.side == 'B' }

    val bidsByPriceMap = bids.groupBy { it.price }
    val offersByPriceMap = offers.groupBy { it.price }

   return if (side == 'B') bidsByPriceMap.keys.sortedDescending()[level-1]
   else offersByPriceMap.keys.sorted()[level-1]
  }
}