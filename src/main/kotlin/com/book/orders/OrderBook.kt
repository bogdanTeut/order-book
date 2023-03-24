package com.book.orders

import java.time.LocalDateTime
import kotlin.collections.Map.Entry

data class Order(val id: Long, val price: Double, val side: Char, val size: Long)

data class TimedOrder(val order: Order, val time: LocalDateTime)

data class PriceLevel(val price: Double, val orders: List<TimedOrder>)

private val TimedOrder.price
  get() = order.price

private val TimedOrder.size
  get() = order.size

class OrderBook {
  private val ordersMap = mutableMapOf<Long, TimedOrder>()

  fun add(order: Order) {
    ordersMap[order.id] = TimedOrder(order, LocalDateTime.now())
  }

  fun remove(orderId: Long) {
    ordersMap.remove(orderId)
  }

  fun update(orderId: Long, size: Long) {
    ordersMap.computeIfPresent(orderId) { _, timedOrder ->
      TimedOrder(timedOrder.order.copy(size = size), timedOrder.time)
    }
  }

  private val mapperToPriceLevel: (Entry<Double, List<TimedOrder>>) -> PriceLevel = { (price, orders) -> PriceLevel(price, orders) }

  private fun sortedByPriceLevels(side: Char): List<PriceLevel> {
    val (bids, offers) = ordersMap.values
      .partition { it.order.side == 'B' }

    val bidsByPriceMap = bids.groupBy { it.price }
    val offersByPriceMap = offers.groupBy { it.price }

    return if (side == 'B') bidsByPriceMap.entries.sortedByDescending { it.key }.map(mapperToPriceLevel)
    else offersByPriceMap.entries.sortedBy { it.key }.map(mapperToPriceLevel)
  }

  fun price(side: Char, level: Int): Double? {
    require(level > 0) { "Level must be positive value." }

    val priceLevels = sortedByPriceLevels(side)

    return priceLevels.getOrNull(level-1)?.price
  }

  fun totalSize(side: Char, level: Int): Long? {
    require(level > 0) { "Level must be positive value." }

    val priceLevels = sortedByPriceLevels(side)

    return priceLevels.getOrNull(level-1)?.orders?.sumOf { it.size }
  }

  fun orders(side: Char): List<Order> {
    val priceLevels = sortedByPriceLevels(side)

    return priceLevels.flatMap { (_, timedOrders)
      -> timedOrders.sortedBy { it.time }.map { it.order }
    }
  }

  internal fun fetchAll(): List<Order> = ordersMap.values.map { it.order }.toList()
}
