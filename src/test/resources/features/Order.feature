@OrderManagement
@API
@Order
@Create
@Smoke
@Regression
@Login
@CreateOrder
Feature: Order Management API Tests

  @Login
  Scenario: Create order with valid data
    Given I login as a user with username "test@fd.com" and password "Test@1234"
    And Store the authentication token for future requests
    And Store the userId from response for future requests
    When I add a item to the cart with below mentioned details
      | userId     | _id                      | productName     | productCategory | productSubCategory | productPrice | productDescription | productImage                                                                    | productRating | productTotalOrders | productStatus | productAddedBy  | __v | productFor |
      | {{userId}} | 67a8dde5c0d3e6622a297cc8 | ZARA COAT 3     | fashion         | shirts             | 31500        | Adidas Originals   | https://rahulshettyacademy.com/api/ecom/uploads/productImage_1650649434146.jpeg | 0             | 0                  | true          | admin@gmail.com | 0   | family     |
    When I add a item to the cart with below mentioned details
      | userId     | _id                      | productName     | productCategory | productSubCategory | productPrice | productDescription | productImage                                                                   | productRating | productTotalOrders | productStatus | productAddedBy  | __v | productFor |
      | {{userId}} | 67a8df1ac0d3e6622a297ccb | ADIDAS ORIGINAL | fashion         | shirts             | 31500        | Addias Originals   | https://rahulshettyacademy.com/api/ecom/uploads/productImage_1650649488046.jpg | 0             | 0                  | true          | admin@gmail.com | 0   | women      |
    When I create an order with below mentioned details
      | country | productOrderedId         |
      | India   | 67a8dde5c0d3e6622a297cc8 |
      | India   | 67a8df1ac0d3e6622a297ccb |
    Then Get product details from order
#    Then Validate the order details
#      | productName | productCategory | productSubCategory | productPrice | productDescription | productImage                                                                    | productRating | productTotalOrders | productStatus | productAddedBy |
#      | ZARA COAT 3 | fashion         | shirts             | 31500        | Adidas Originals   | https://rahulshettyacademy.com/api/ecom/uploads/productImage_1650649434146.jpeg | 0             | 0                  | true          |                |
#    Then Get order
