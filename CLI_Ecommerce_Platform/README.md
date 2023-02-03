------------------------------------------------------------
-------------A-------------EEEEEEEEEE-----YYY-------YYY-----
------------AAA------------EEE-------------YYY-----YYY------
-----------AAAAA-----------EEE--------------YYY---YYY-------
----------AAA-AAA----------EEE---------------YYY-YYY--------
---------AAA---AAA---------EEEEEEEEEE---------YYYYY---------
--------AAA-----AAA--------EEE-----------------YYY----------
-------AAAAAAAAAAAAA-------EEE-----------------YYY----------
------AAA---------AAA------EEE-----------------YYY----------
-----AAA-----------AAA-----EEEEEEEEEE----------YYY----------
------------------------------------------------------------

This code is a simplified version of an E-commerce platform such as Amazon using a CLI interface.

## Getting Started

First, run ECommerceUserInterface.java by:

```bash
java ECommerceUserInterface.java
```

Commands include:

ADDTOCART
 - takes custID/prodID/book handling
 - adds products to cart
REMCARTITEM
 - takes CustID and ProdID
 - removes element from cart
PRINTCART
 - custID and prints cart items of a user
ORDERITEMS
 - takes custID and sends cart items to order
STATS
 - prints frequency of most ordered item to least
PRINTBYNAME
 - sorts products by name and can be checked using prods
PRINTPRICE
 - sorts products by price

RATE
 - allows the user to rate a product from 1-5 stars 
RATINGABOVE
 - find all products that have a specific star count or higher
PRINTRATING
 - print the rating of a product

SORTBYCUSTS
 - sorts customers
BOOKSBYAUTHOR
 - finds all books by that author
PRODS
 - lists all products
BOOKS
 - lists all books
CUSTS
 - lists all customers
NEWCUST
 - adds customer
ORDER
 - adds order for a customer
ORDERBOOK
 - orders book for a customer
CANCEL
 - cancels an order for a customer
CUSTORDERS
 - lists all the orders for a specific customer 
SHIP
 - ships the order to the customers
ORDERS
 - lists all the orders
SHIPPED
 - lists all the shipped products