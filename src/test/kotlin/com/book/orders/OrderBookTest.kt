package com.book.orders

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class OrderBookTest {

  @Test
  fun `add order to order book successfully`() {
    //given
    val orderBook = OrderBook()

    //when
    orderBook.add(Order(1, 30.0, 'B', 10))

    //then
    assertThat(orderBook.fetchAll()).contains(Order(1, 30.0, 'B', 10))
  }
}

data class Order(val id: Long, val price: Double, val side: Char, val size: Long)

class OrderBook {
  fun add(order: Order) {
    TODO("Not yet implemented")
  }

  fun fetchAll(): List<Order>? {
    TODO("Not yet implemented")
  }
}