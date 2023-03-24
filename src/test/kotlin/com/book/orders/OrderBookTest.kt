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
    orderBook.remove(1)

    //then
    assertThat(orderBook.fetchAll()).isEmpty()
  }

  @Test
  fun `update order size using order id`() {
    //given
    val orderBook = OrderBook()
    orderBook.add(Order(1, 30.0, 'B', 10))

    //when
    orderBook.update(1, 20)

    //then
    assertThat(orderBook.fetchAll()).contains(Order(1, 30.0, 'B', 20))
  }

  @Test
  fun `given side and level it provides correct price`() {
    //given
    val orderBook = OrderBook()
    orderBook.add(Order(1, 30.0, 'B', 15))
    orderBook.add(Order(1, 10.0, 'B', 10))
    orderBook.add(Order(1, 40.0, 'B', 20))

    //when
    val price = orderBook.price('B', 2)

    assertThat(price).isEqualTo(10);
  }
}