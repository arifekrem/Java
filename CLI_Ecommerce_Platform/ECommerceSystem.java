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

import java.io.*;
import java.net.Inet4Address;
import java.util.*;

/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem
{
    private TreeMap<String,Product> products = new TreeMap<String, Product>();
    private Map<String, Integer> orderstats = new HashMap<String, Integer>();
    
    private ArrayList<Customer> customers = new ArrayList<Customer>();	
    
    private ArrayList<ProductOrder> orders   = new ArrayList<ProductOrder>();
    private ArrayList<ProductOrder> shippedOrders   = new ArrayList<ProductOrder>();
    
    // These variables are used to generate order numbers, customer id's, product id's 
    private int orderNumber = 500;
    private int customerId = 900;
    private int productId = 700;
    
    // General variable used to store an error message when something is invalid (e.g. customer id does not exist)  
    String errMsg = null;
    
    // Random number generator
    Random random = new Random();
    
    public ECommerceSystem(){//constructor 
      try{
        String file = "products.txt"; //the text file
        products = creatingProducts(file); //activates the function that reads the .txt file and reads it 
        //customers
        //add the customer objects to the customers arraylist since I changed the originak constructor to encompass try/catch blocks
        customers.add(new Customer(generateCustomerId(),"Inigo Montoya", "1 SwordMaker Lane, Florin"));
       	customers.add(new Customer(generateCustomerId(),"Prince Humperdinck", "The Castle, Florin"));
      	customers.add(new Customer(generateCustomerId(),"Andy Dufresne", "Shawshank Prison, Maine"));
      	customers.add(new Customer(generateCustomerId(),"Ferris Bueller", "4160 Country Club Drive, Long Beach"));
      }
      catch(FileNotFoundException a){ // catches exceptions
        System.out.println("File not found");
        System.exit(1); //closes scanner 
      }
    }
    

    //Does the file I/O and reads the txt file
    //uses a treemap
    private TreeMap<String,Product> creatingProducts(String file) throws FileNotFoundException{
      //variables used to store the info from the file and to help read from the file
      int index = -1;//used to index the info properly 
      String name = "";//name of product
      String id = "";//ID of product
      double price = 0;//price of product
      int stock = 0;//quantity

      //productoptions for books
      int paperbackStock = 0;
      int hardcoverStock = 0;

      //other info only for books
      String title = "";
      String author = "";
      String year = "0";
      Product.Category category = Product.Category.BOOKS;//de

      //sets up a scanner 
      File realFile = new File (file);
      Scanner scanner = new Scanner(realFile); 

      //starts reading the .txt file
      while (scanner.hasNext()){//checks to make sure that the next line exists
        String elemIndex = scanner.nextLine(); //holds the line
        index++; //used to help identify the category of the product

        //checks the category type of the product and assigns it
        if (index==0){
          if (elemIndex.equals("BOOK")){
            category = Product.Category.BOOKS;
          }
          if (elemIndex.equals("CLOTHING")){
            category = Product.Category.CLOTHING;
          }
          if (elemIndex.equals("FURNITURE")){
            category = Product.Category.FURNITURE;
          }
          if (elemIndex.equals("COMPUTERS")){
            category = Product.Category.COMPUTERS;
          }
          if (elemIndex.equals("GENERAL")){
            category = Product.Category.GENERAL;
          }
        }

        //collects the name of the product
        if (index == 1){
          name = elemIndex;
        }

        //collects the price and converts it to the double 
        if (index == 2){
          price = Double.parseDouble(elemIndex);
        }

        //Checks if the name of the product 
        if (name.equals("Book") && index == 3){
          //splits array and the takes the paper/hardcover info
          String[] array = elemIndex.split(" ");
          paperbackStock = Integer.parseInt(array[0]); 
          hardcoverStock = Integer.parseInt(array[1]);
        }

        //if it isnt a book, set the stock 
        if (!name.equals("Book") && index == 3){
          stock = Integer.parseInt(elemIndex);
        }
        //if its a book, create an arraylist and split the string by the : to get the author,title,,year, and create id
        if (name.equals("Book") && index == 4){
          ArrayList<String> infoBook = new ArrayList<String>(Arrays.asList(elemIndex.split(":")));
          title = infoBook.get(0);
          author = infoBook.get(1);
          year = infoBook.get(2);
          id = generateProductId();
          products.put(id, new Book(name,id,price,paperbackStock,hardcoverStock,title,author,year));
          orderstats.put(name+" "+id,0); //creates a key and sets the value to 0 for all the values so that it count the frequency
          index = -1; 
        }
        //if its not a book, create the productID and and then add to products and orderstats
        if (index == 4 && !name.equalsIgnoreCase("BOOK")){
          id = generateProductId();
          products.put(id, new Product(name, id, price, stock, category));
          orderstats.put(name + " " + id, 0);
          index = -1;
        }
      } 
      scanner.close(); // close the scanner to prevent bugs
      return products; //returns the product as a treemap
    }
    // check if given category is valid, and returns as Product.Category, else throws exception
    public Product.Category validCategory(String category){
      Product.Category c = null;
      if (category.equalsIgnoreCase("GENERAL")){
        c = Product.Category.GENERAL;
      }
      else if (category.equalsIgnoreCase("COMPUTERS")){
        c = Product.Category.COMPUTERS;
      }
      else if (category.equalsIgnoreCase("FURNITURE")){
        c = Product.Category.FURNITURE;
      }
      else if (category.equalsIgnoreCase("CLOTHING")){
        c = Product.Category.CLOTHING;
      }
      else if (category.equalsIgnoreCase("BOOKS")){
        c = Product.Category.BOOKS;
      }
      else {
        throw new InvalidCategoryException("Invalid Category Input: " + category);
      }

      if (c == null){
        throw new InvalidCategoryException("Invalid Category Input: " + category);
      }
      return c;
    }

    //used to rate a product by taking productID and the rating the customer gave
    // method adds a rating input to a product
  public void rateProduct(String productId, String rating){
    realProd(productId); // check if valid
    validRate(rating); // check if valid
    
    for(Product p : products.values()){ // iterate and get product
      if(p.getId().equals(productId)){
          p.rateProduct(rating); // add rating
      }
    }
  }

  //used to make sure the rating info is valid
  public int validRate(String rate){
    if (rate == null){
      throw new InvalidRateException("Invalid Rating");
    }
    else if (Integer.parseInt(rate) < 1 || Integer.parseInt(rate) > 5){ //checks to make sure the rating is valid
      throw new InvalidCategoryException("Enter Rating from 1-5");
    }
    return Integer.parseInt(rate);
  }
    

    // method prints rating of product
  public void printRating(String productId){ 
    realProd(productId);
    for(Product p : products.values()){ // find product
      if(p.getId().equals(productId)){
          p.printRating();
      }
    }
  }

    //prints the rating above the threshold the user wanted
    public void printAllAboveThreshold(String rating, String category){ //takes the rating it needs to look for to print
      validRate(rating); // valid rate
      validCategory(category); //valid category
      
      for(Product p : products.values()){ //goes through all the products
        if(p.aboveRating(validRate(rating))){ //checks to see if its above the rating
          if (p.getCategory().equals(validCategory(category))){
           p.print(); // print the name of the products
          }
        }
      }
    }

    //prints the frequency of each product and sorts them from greatest to least
    //for this function, the keys and values will change together
    public void orderstats() {
      ArrayList<String> keys = new ArrayList<String>(); //holds the key values of orderstats
      ArrayList<Integer> values = new ArrayList<Integer>(); //holds the values of orderstats
  
      for (String k : orderstats.keySet()){//goes through the keyset and values and adds it to keys
        keys.add(k);
        values.add(orderstats.get(k));
      }
  
      int helper = keys.size(); //helps the for loop go through all the elements 
  
      for (int i = 0; i < helper; i++) { //checks all keys and values
        //assumes the first element is the max
        String maxKey = keys.get(0);
        Integer maxFreq = values.get(0);

        //goes through the keys to and temporaily gets the values to compare with the max 
        for (int j = 0; j < keys.size(); j++) {
          Integer currentFreq = values.get(j);
          String currentKey = keys.get(j);
  
          if (maxFreq < currentFreq) {//if current greater than max, change max
            maxFreq = currentFreq;
            maxKey = currentKey;
          }
        }

        //print the max
        System.out.println(maxKey + " : " + maxFreq);

        //remove max and repeat
        values.remove(maxFreq);
        keys.remove(maxKey);
      }
    }
  
    //a. ADDTOCART: String productid, String customerID, String productOptions
    public void addToCart(String productid, String customerID, String productOptions){
      realProd(productid);
      realCust(customerID);
      //goes through customers, compares the custID and then ProdID and then adds product and productoptions to cart
      for(Customer c : customers){ 
        if(c.getId().equals(customerID)){
            for(Product p : products.values()){
              if(p.getId().equals(productid)){
                c.getCart().addToCart(p, productOptions);
              }
            }
        }
      }
    }

    //prints the cart of the user by going through the customer arraylist and has exception handling
    public void printCart(String customerID){ 
      realCust(customerID);
      for(Customer c : customers){
        if(c.getId().equals(customerID)){
          c.getCart().printCart();
        }
      }
    }

    //removes an item from the cartby going through the productid and customerid and uses exception handling
    public void removeCartItem(String productID, String customerID){
      realCust(customerID);
      realProd(productID);
      for(Customer c : customers){
        if(c.getId().equals(customerID)){
          for(Product p: products.values()){
            if(p.getId().equals(productID)){
              c.getCart().removeCartItem(p);
              return;
            }
          }
        }
      }
    }

    //exception handling for customers, checks to make sure the customer id exists
    public Customer realCust(String customerID){
      int index = customers.indexOf(new Customer(customerID));
      if (index == -1){
        throw new UnknownCustomerException("Customer "+customerID+" Not Found");
      }
      Customer realCust = customers.get(index);
      return realCust;
    }

    //exception handling for products, checks to make sure the product id exists
    public Product realProd(String productId){
      boolean flag = products.keySet().contains(productId);
      if (!flag || productId.equals("")){
        throw new UnknownProductException("Product " + productId + " Not Found");
      }
      Product validProd = products.get(productId);
      return validProd;
    }

    //transfers the items in cart to the orders arraylist
    public void orderItems(String customerID){
      Customer realCust = realCust(customerID);//exception checking
      ArrayList<CartItem> list = realCust.getCart().getCartList(); 

      for (CartItem CI: list){
        String ID = CI.getProduct().getId();
        String productOpt = CI.getProductOptions();
        orderProduct(ID, customerID, productOpt);//call orderproduct to send over the cart items
      }
      realCust.getCart().removeCartItems(); //clears cart
    }

    
    private String generateOrderNumber()
    {
    	return "" + orderNumber++;
    }

    private String generateCustomerId()
    {
    	return "" + customerId++;
    }
    
    private String generateProductId()
    {
    	return "" + productId++;
    }
    
    public String getErrorMessage()
    {
    	return errMsg;
    }
    
    public void printAllProducts()
    {
    	for (Product p : products.values())
    		p.print();
    }
    
    // Print all products that are books. See getCategory() method in class Product
    public void printAllBooks()
    {
      for (Product books : products.values()){
        if (books.getCategory() == (Product.Category.BOOKS)){ //makes sure that the product is a book by checking its category
          books.print();
        }
      }
    }
    
    // Print all current orders
    public void printAllOrders()
    {
      for (ProductOrder order : orders){
        order.print();
      }
    	
    }
    // Print all shipped orders
    public void printAllShippedOrders()
    {
      for (ProductOrder order : shippedOrders){
        order.print();
      }

    }
    
    // Print all customers
    public void printCustomers()
    {
      for (Customer ppl : customers){
        ppl.print();
      }
    	
    }
    /*
     * Given a customer id, print all the current orders and shipped orders for them (if any)
     */
    public boolean printOrderHistory(String customerId)
    {
      // Make sure customer exists - check using customerId
        // If customer does not exist, set errMsg String and return false
        // see video for an appropriate error message string
        // ... code here

      // loops through arraylist of customer by customer, and check if its Id matches with the parameter customerId
      // if it is found, then the customer exist and the loop breaks
      // else, it sets an error message and returns false
      Customer existCust = null;
      for (Customer cu : customers){
        if (cu.getId().equals(customerId)){
          existCust = cu;
          break;
        }
      }
        if (existCust == null){
          throw new UnknownCustomerException("Customer "+customerId+" Not Found");
        }
        // Print current orders of this customer 
        System.out.println("Current Orders of Customer "+customerId);


        // enter code here
      
      for (ProductOrder pOr : orders){
        if (pOr.getCustomer().equals(existCust)){
          pOr.print();
        }
      }
        // Print shipped orders of this customer 
        System.out.println("\nShipped Orders of Customer "+customerId);
        //enter code here
        for (ProductOrder pShip : shippedOrders){
        if (pShip.getCustomer().equals(existCust)){
          pShip.print();
        }
      }
        return true;
    }
    
    public String orderProduct(String productId, String customerId, String productOptions)
    {
    	// First check to see if customer object with customerId exists in array list customers
    	// if it does not, set errMsg and return null (see video for appropriate error message string)
    	// else get the Customer object
      Customer realCust = null;
    	for (Customer cu : customers){
        if (cu.getId().equals(customerId)){
          realCust = cu;
          break;
          }
        }
        if (realCust == null){
          throw new UnknownCustomerException("Customer "+customerId+" Not Found");
      }
      
    	// Check to see if product object with productId exists in array list of products
    	// if it does not, set errMsg and return null (see video for appropriate error message string)
    	// else get the Product object 

      Product realProd = null;
    	for (Product pr : products.values()){
        if (pr.getId().equals(productId)){
          realProd = pr;
          break;
        }
      }

      if (realProd == null){
          throw new  UnknownProductException("Product "+productId+" Not Found ");
        }
    	// Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
    	// See class Product and class Book for the method validOptions()
    	// If options are not valid, set errMsg string and return null;

      if (realProd != null){
        if (!realProd.validOptions(productOptions)){ //checks to see if options are valid by calling the method in the other class
          throw new InvalidProductOptionException("Product Book ProductId "+productId+" Invalid Options: "+productOptions);
        }
      
    	// Check if the product has stock available (i.e. not 0)
    	// See class Product and class Book for the method getStockCount()
    	// If no stock available, set errMsg string and return null
        if (realProd.getStockCount(productOptions) == 0){//checks to see if stock exists by calling the method in the other class
          throw new OutOfStockException("Product "+productId+" Out of Stock");
        }

      // Create a ProductOrder, (make use of generateOrderNumber() method above)
    	// reduce stock count of product by 1 (see class Product and class Book)
    	// Add to orders list and return order number string

    	  ProductOrder newPrOr = new ProductOrder(generateOrderNumber(), realProd, realCust, productOptions);
        realProd.reduceStockCount(productOptions); //calls other class and cuts stock 
        orders.add(newPrOr); //adds the object to orders
        for (Product p: products.values()){ 
          if (p.getId().equals(productId)){
            orderstats.put(p.getName()+" "+productId, orderstats.get(p.getName()+" "+productId)+1);
          }
        }
        return newPrOr.getOrderNumber(); //sends the order number to be printed
      }
      else { //error checking
        throw new UnknownProductException("Product " + productId + " Not Found ");
      }
    }
    
    /*
     * Create a new Customer object and add it to the list of customers
     */
    
    public boolean createCustomer(String name, String address)
    {
    	// Check name parameter to make sure it is not null or ""
    	// If it is not a valid name, set errMsg (see video) and return false
    	// Repeat this check for address parameter
      if (name.equals("") || name == null){
        throw new InvalidCustomerNameException("Invalid Customer Name");
      }
      if (address.equals("") || address == null){
        throw new InvalidCustomerAddressException("Invalid Customer Address");
      }
    	
    	// Create a Customer object and add to array list
      Customer person1 = new Customer(generateCustomerId(), name, address); //creates new person object
      customers.add(person1);
    	return true;
    }
    
    public ProductOrder shipOrder(String orderNumber)
    {
      // Check if order number exists first. If it doesn't, set errMsg to a message (see video) 
        // and return false
        // Retrieve the order from the orders array list, remove it, then add it to the shippedOrders array list
        // return a reference to the order

        ProductOrder transferOrder = null;
        boolean flag = false;

        for (ProductOrder or : orders){
          if (or.getOrderNumber().equals(orderNumber)){ //whenever order number matches moves the order to shipped
            transferOrder = or;
            orders.remove(or);
            shippedOrders.add(or); 
            flag = true; //used as error checking
            return transferOrder;
          }
        }
        if (flag == false){
          throw new InvalidOrderNumberException("Order "+orderNumber+" Not Found");
        }
        return null;
    }
    
    /*
     * Cancel a specific order based on order number
     */
    public boolean cancelOrder(String orderNumber)
    {
      // Check if order number exists first. If it doesn't, set errMsg to a message (see video) 
    	// and return false
    	for (ProductOrder po1: orders){
        if (po1.getOrderNumber().equals(orderNumber)){ //whenever ordernumber matches
          orders.remove(po1);//remove the object
          return true;
        }
      }
      throw new InvalidOrderNumberException("Order Number "+orderNumber+" Not Found");
    }

    // Sort products by increasing price
    public void printByPrice()
    {
      ArrayList<Product> productList = new ArrayList<Product>(products.values());
      // lambda expression
        productList.sort(Comparator.comparing(Product::getPrice));
      for (Product p : productList){
        p.print();
      }
    }
    
    
    // // Sort products alphabetically by product name
    public void printByName()
    {
      ArrayList<Product> productL = new ArrayList<Product>(products.values());//uses collections and compare names and then sorts
        Comparator <Product> alphabetProd = new Comparator<Product>() {
        public int compare(Product p1, Product p2){
          return p1.getName().compareTo(p2.getName());
        }
      };
      Collections.sort(productL, alphabetProd);
      for (Product p : productL){
        p.print();
      }
    }
    
        
    // Sort products alphabetically by product name
    public void sortCustomersByName()
    {
      Comparator <Customer> alphaname = new Comparator<Customer>(){//creates comparator
        public int compare(Customer cust1, Customer cust2){
          return cust1.getName().compareTo(cust2.getName());//returns the compared names
        }
      };
      Collections.sort(customers, alphaname); //sorts names
  	  
    }


    // Sort books by increasing of year
    public boolean sortBooksByAuthor(String author){
      ArrayList<Book> listbooks = new ArrayList<Book>(); //creates arraylist
      boolean flag = false; //error checking variable

      for (Product pr : products.values()){
        if (pr.getCategory().equals(Product.Category.BOOKS)){ //if product category is books
          Book collect = (Book) pr; //casts product object as a book object
          if (collect.getAuthorName().equalsIgnoreCase(author)){ //checks author name
            listbooks.add((Book) pr); //adds the same author names
            flag = true; //error checking variable
          }
        }
      }

    if (!flag){ //error checking
      throw new UnknownAuthorException("Author: "+author+"Not Found");
    }
    Comparator <Book> booksort = new Comparator<Book>() { //new comparator
      public int compare(Book b1, Book b2){
        return b1.getAuthorName().compareTo(b2.getAuthorName());//compares author name
      }
    };
    Collections.sort(listbooks, booksort);//sorts info

    for (int i=0;i<listbooks.size();i++){
      listbooks.get(i).print();//prints it
    }
    return true;
    }
  }

//the code below is used to catch the exceptions used in ecommerce system
class UnknownCustomerException extends RuntimeException{
  public UnknownCustomerException(){
  }
  public UnknownCustomerException(String msg){
    super(msg);//prints error msg
  }
}

class UnknownProductException extends RuntimeException{
  public UnknownProductException(){
  }
  public UnknownProductException(String msg){
    super(msg);
  }
}
class InvalidCategoryException extends RuntimeException{
  public InvalidCategoryException(){
  }
  public InvalidCategoryException(String msg){
    super(msg);
  }
}
class UnknownAuthorException extends RuntimeException{
  public UnknownAuthorException(){
  }
  public UnknownAuthorException(String msg){
    super(msg);
  }
}

class InvalidProductOptionException extends RuntimeException{
  public InvalidProductOptionException(){
  }
  public InvalidProductOptionException(String msg){
    super(msg);
  }
}

class OutOfStockException extends RuntimeException{
  public OutOfStockException(){
  }
  public OutOfStockException(String msg){
    super(msg);
  }
}

class InvalidCustomerNameException extends RuntimeException{
  public InvalidCustomerNameException(){
  }
  public InvalidCustomerNameException(String msg){
    super(msg);
  }
}

class InvalidCustomerAddressException extends RuntimeException{
  public InvalidCustomerAddressException(){
  }
  public InvalidCustomerAddressException(String msg){
    super(msg);
  }
}

class InvalidOrderNumberException extends RuntimeException{
  public InvalidOrderNumberException(){
  }
  public InvalidOrderNumberException(String msg){
    super(msg);
  }
}
class InvalidRateException extends RuntimeException{
  public InvalidRateException(){
  }
  public InvalidRateException(String msg){
    super(msg);
  }
}

