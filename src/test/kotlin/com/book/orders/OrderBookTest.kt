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
  fun `given side 'B' and level 2 it provides correct 2nd best bid price`() {
    //given
    val orderBook = OrderBook()
    orderBook.add(Order(1, 30.0, 'B', 15))
    orderBook.add(Order(2, 10.0, 'B', 10))
    orderBook.add(Order(3, 40.0, 'B', 20))
    orderBook.add(Order(4, 5.0, 'B', 50))
    orderBook.add(Order(5, 25.0, 'B', 10))

    //when
    val price = orderBook.price('B', 2)

    //then
    assertThat(price).isEqualTo(30);
  }

  @Test
  fun `given side 'O' and level 2 it provides correct 2nd best offer price`() {
    //given
    val orderBook = OrderBook()
    orderBook.add(Order(1, 30.0, 'O', 15))
    orderBook.add(Order(2, 10.0, 'O', 10))
    orderBook.add(Order(3, 40.0, 'O', 20))
    orderBook.add(Order(4, 5.0, 'O', 50))
    orderBook.add(Order(5, 25.0, 'O', 10))

    //when
    val price = orderBook.price('O', 2)

    //then
    assertThat(price).isEqualTo(10);
  }

  @Test
  fun `given side 'B' and no bids provides null price`() {
    //given
    val orderBook = OrderBook()
    orderBook.add(Order(1, 30.0, 'O', 15))
    orderBook.add(Order(2, 10.0, 'O', 10))
    orderBook.add(Order(3, 40.0, 'O', 20))
    orderBook.add(Order(4, 5.0, 'O', 50))
    orderBook.add(Order(5, 25.0, 'O', 10))

    //when
    val price = orderBook.price('B', 2)

    //then
    assertThat(price).isNull()
  }
}