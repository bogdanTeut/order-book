package com.book.orders

import kotlin.collections.Map.Entry

data class Order(val id: Long, val price: Double, val side: Char, val size: Long)

data class PriceLevel(val price: Double, val orders: List<Order>)

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

  private val mapperToPriceLevel: (Entry<Double, List<Order>>) -> PriceLevel = { (price, orders) -> PriceLevel(price, orders) }

  private fun sortedByPriceLevels(side: Char): List<PriceLevel> {
    val (bids, offers) = ordersMap.values
      .partition { it.side == 'B' }

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

    val levels = sortedByPriceLevels(side)

    return levels.getOrNull(level-1)?.orders?.sumOf { it.size }
  }
}