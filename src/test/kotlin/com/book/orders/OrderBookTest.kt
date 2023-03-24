package com.book.orders

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Assertions
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

  @Test
  fun `given side 'B' and level that exceeds price levels provides null price`() {
    //given
    val orderBook = OrderBook()
    orderBook.add(Order(1, 30.0, 'B', 15))
    orderBook.add(Order(2, 10.0, 'B', 10))
    orderBook.add(Order(3, 40.0, 'B', 20))
    orderBook.add(Order(4, 5.0, 'B', 50))
    orderBook.add(Order(5, 25.0, 'B', 10))

    //when
    val price = orderBook.price('B', 8)

    //then
    assertThat(price).isNull()
  }

  @Test
  fun `given side and negative level it throws illegal argument exception`() {
    //given
    val orderBook = OrderBook()

    //when
    val exception = Assertions.assertThrows(IllegalArgumentException::class.java) { orderBook.price('B', -1) }

    //then
    assertThat(exception).hasMessageThat().isEqualTo("Level must be positive value.")
  }

  @Test
  fun `given bids plus offers and level 2 it provides correct 2nd best prices for both bids and offers`() {
    //given
    val orderBook = OrderBook()
    orderBook.add(Order(1, 30.0, 'B', 15))
    orderBook.add(Order(2, 10.0, 'B', 30))
    orderBook.add(Order(3, 25.0, 'O', 10))
    orderBook.add(Order(4, 40.0, 'B', 20))
    orderBook.add(Order(5, 5.0, 'B', 50))
    orderBook.add(Order(6, 12.0, 'O', 50))
    orderBook.add(Order(7, 25.0, 'B', 25))
    orderBook.add(Order(8, 20.0, 'O', 10))

    //when then
    assertThat(orderBook.price('B', 2)).isEqualTo(30)
    assertThat(orderBook.price('O', 1)).isEqualTo(12)
  }

  @Test
  fun `given side 'B' and level 2 it provides correct total size`() {
    //given
    val orderBook = OrderBook()
    orderBook.add(Order(1, 30.0, 'B', 15))
    orderBook.add(Order(2, 10.0, 'B', 30))
    orderBook.add(Order(3, 25.0, 'O', 10))
    orderBook.add(Order(4, 40.0, 'B', 20))
    orderBook.add(Order(5, 30.0, 'B', 50))
    orderBook.add(Order(6, 12.0, 'O', 50))
    orderBook.add(Order(7, 25.0, 'B', 25))
    orderBook.add(Order(8, 20.0, 'O', 10))

    //when
    val price = orderBook.totalSize('B', 2)

    //then
    assertThat(price).isEqualTo(65);
  }

  @Test
  fun `given side 'B' provides all orders sorted correctly in level and time order`() {
    //given
    val orderBook = OrderBook()
    orderBook.add(Order(1, 30.0, 'B', 15))
    orderBook.add(Order(2, 10.0, 'B', 30))
    orderBook.add(Order(3, 25.0, 'O', 10))
    orderBook.add(Order(4, 40.0, 'B', 20))
    orderBook.add(Order(5, 30.0, 'B', 50))
    orderBook.add(Order(6, 12.0, 'O', 50))
    orderBook.add(Order(7, 10.0, 'B', 25))
    orderBook.add(Order(8, 20.0, 'O', 10))

    //when
    val orders = orderBook.orders('B')

    //then
    assertThat(orders)
      .containsExactlyElementsIn(listOf(
        Order(4, 40.0, 'B', 20),
        Order(1, 30.0, 'B', 15),
        Order(5, 30.0, 'B', 50),
        Order(2, 10.0, 'B', 30),
        Order(7, 10.0, 'B', 25)
      )).inOrder();
  }
}