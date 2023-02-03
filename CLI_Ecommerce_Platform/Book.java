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

/* A book is a product that has additional information - e.g. title, author

 	 A book also comes in different formats ("Paperback", "Hardcover", "EBook")
 	 
 	 The format is specified as a specific "stock type" in get/set/reduce stockCount methods.

*/
public class Book extends Product 
{
  private String author;
  private String title;
  private String year; 
  
  // Stock related information NOTE: inherited stockCount variable is used for EBooks
  int paperbackStock;
  int hardcoverStock;
  
  public Book(String name, String id, double price, int paperbackStock, int hardcoverStock, String title, String author, String year)
  {
  	 // Make use of the constructor in the super class Product. Initialize additional Book instance variables. 
  	 // Set category to BOOKS 
     super(name, id, price, 100000, Product.Category.BOOKS);
     this.title = title;
     this.author = author;
     this.year = year;
    //  this.year =  year;
     this.paperbackStock = paperbackStock;
     this.hardcoverStock = hardcoverStock;
  }
    
  // Check if a valid format  
  public boolean validOptions(String productOptions)
  {
  	// check productOptions for "Paperback" or "Hardcover" or "EBook"
  	// if it is one of these, return true, else return false

    if (productOptions.equals("Paperback")) {
      return true;
    }

    if (productOptions.equals("Hardcover")){
      return true;
    }

    if (productOptions.equals("EBook")){
      return true;
    }

  	return false;
  }
  
  // Override getStockCount() in super class.
  public int getStockCount(String productOptions)
	{
  	// Use the productOptions to check for (and return) the number of stock for "Paperback" etc
  	// Use the variables paperbackStock and hardcoverStock at the top. 
  	// For "EBook", use the inherited stockCount variable.

    if (productOptions.equals("Paperback")){
      return this.paperbackStock;
    }

    if (productOptions.equals("Hardcover")){
      return this.hardcoverStock;
    }

    if (productOptions.equals("EBook")){
      return super.getStockCount(productOptions);
    }

  	return 1;
	}
  
  public void setStockCount(int stockCount, String productOptions)
	{
    // Use the productOptions to check for (and set) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.
    if (productOptions.equalsIgnoreCase("Paperback")){
      stockCount = paperbackStock;
    }
    if (productOptions.equalsIgnoreCase("Hardcover")){
      stockCount = hardcoverStock;
    }
    if (productOptions.equalsIgnoreCase("EBook")){
      super.setStockCount(stockCount, productOptions);
    }
	}
  
  /*
   * When a book is ordered, reduce the stock count for the specific stock type
   */
  public void reduceStockCount(String productOptions)
	{
  	// Use the productOptions to check for (and reduce) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.
    if (productOptions.equalsIgnoreCase("Paperback")){
      paperbackStock--;
    }
    if (productOptions.equalsIgnoreCase("Hardcover")){
      hardcoverStock--;
    }
    if (productOptions.equalsIgnoreCase("EBook")){
      super.reduceStockCount(productOptions);
    }
	}
  /*
   * Print product information in super class and append Book specific information title and author
   */
  public void print()
  {
  	// Replace the line below.
  	// Make use of the super class print() method and append the title and author info. See the video
  	super.print();
    System.out.print(" Book Title: :"+this.title+" Author: "+this.author+" Published: "+this.year);
  }
  
  //these functions below are used for the bonus part of the project
  //gets year
  public  String getYear(){
    return year;
  }
  //gets author
  public String getAuthorName(){
    return author;
  }
  //gets year and compares it
  public int compareTo(Book other){
    return this.year.compareTo(other.year);
  }
}