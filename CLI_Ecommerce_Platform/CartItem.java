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

//this is the file that consists of get functions 
public class CartItem{
    private Product product;
    private String ProductOptions = "";

    public CartItem(Product product, String ProductOpt){
        this.ProductOptions = ProductOpt;
        this.product = product;
    }
    //return the product the cart item holds
    public Product getProduct(){
        return product;
    }

    //returns productoptions
    public String getProductOptions(){
        return ProductOptions;
    }
    //returns productoptions
    public String toString() {
        return this.product.toString();
    }
   
}