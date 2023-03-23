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

  @Test
  fun `remove order from order book successfully`() {
    //given
    val orderBook = OrderBook()
    orderBook.add(Order(1, 30.0, 'B', 10))

    //when
    orderBook.remove(Order(1, 30.0, 'B', 10))

    //then
    assertThat(orderBook.fetchAll()).isEmpty()
  }
}