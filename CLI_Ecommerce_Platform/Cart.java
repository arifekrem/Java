/*
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
*/

//Name:	Ekrem Yilmaz
//Student Number:	501108034
//used by ecommerce system to change the linkedlist shoppingcart

import java.util.*; //imports java util library

public class Cart{
    private LinkedList <CartItem> shoppingCart; //used to keep track of whats in the cart
    private ListIterator <CartItem> iter; //used to check values in cart

    public Cart(){//constructor that initializes variables 
        shoppingCart = new LinkedList<CartItem>();
        iter = shoppingCart.listIterator();
    }

    //adds to the cart linkedlist
    public void addToCart(Product product, String productOptions){
        iter.add(new CartItem(product, productOptions));
    }

    //prints the products out of the print function
    public void printCart(){
        for(CartItem cartItem: shoppingCart){
           cartItem.getProduct().print();
        }
    }

    //removes the product from the cart
    public void removeCartItem(Product product){
        for (Iterator<CartItem> iter2 = shoppingCart.iterator() ; iter2.hasNext() ;){ //uses another iterator and makes sure a value exists
            CartItem it = iter2.next();//gets value
            if (it.getProduct().equals(product)){ 
                shoppingCart.remove();//removes element from shoppingCart
            }
        }
    }

    //used for retrieving the cart items
    public ArrayList<CartItem> getCartList(){
        ArrayList<CartItem> lists = new ArrayList<CartItem>();	
        for (CartItem cartItems: shoppingCart){
            lists.add(cartItems); //adds items to list
        }
        return lists;//returns list values
    }

    //deletes all cart values
    public void removeCartItems(){
        shoppingCart.clear();
    }
}