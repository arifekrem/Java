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

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface
{
	public static void main(String[] args)
	{
		// Create the system
		ECommerceSystem amazon = new ECommerceSystem();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");
		
		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();
			try{
			
				if (action == null || action.equals("")) 
				{
					System.out.print("\n>");
					continue;
				}
				else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;

				else if (action.equalsIgnoreCase("PRODS"))	// List all products for sale
				{
					amazon.printAllProducts(); 
				}
				else if (action.equalsIgnoreCase("BOOKS"))	// List all books for sale
				{
					amazon.printAllBooks(); 
				}
				else if (action.equalsIgnoreCase("CUSTS")) 	// List all registered customers
				{
					amazon.printCustomers();	
				}
				else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
				{
					amazon.printAllOrders();	
				}
				else if (action.equalsIgnoreCase("SHIPPED"))	// List all orders that have been shipped
				{
					amazon.printAllShippedOrders();	
				}
				else if (action.equalsIgnoreCase("NEWCUST"))	// Create a new registered customer
				{
					String name = "";
					String address = "";
					
					System.out.print("Name: "); //reads input and makes sure it exists
					if (scanner.hasNextLine())
						name = scanner.nextLine();
					
					System.out.print("\nAddress: "); //reads input and makes sure it exists
					if (scanner.hasNextLine())
						address = scanner.nextLine();
					
					boolean success = amazon.createCustomer(name, address); //calls function in ECommerceSystem
					if (!success)
					{
						System.out.println(amazon.getErrorMessage()); //error checking
					}
				}
				else if (action.equalsIgnoreCase("SHIP"))	// ship an order to a customer
				{
						String orderNumber = "";
			
						System.out.print("Order Number: ");
						// Get order number from scanner
						if (scanner.hasNextLine())//reads input and makes sure it exists
							orderNumber = scanner.nextLine();
						// Ship order to customer (see ECommerceSystem for the correct method to use
						ProductOrder success = amazon.shipOrder(orderNumber);//calls function in ECommerceSystem
						if (success == null){
							System.out.println(amazon.getErrorMessage());//error checking
						}
						else{
							success.print();
						}
				}
				else if (action.equalsIgnoreCase("CUSTORDERS")) // List all the current orders and shipped orders for this customer id
				{
					String customerId = "";

					System.out.print("Customer Id: ");
					// Get customer Id from scanner
					if (scanner.hasNextLine()){
						customerId = scanner.nextLine();
					}
					// Print all current orders and all shipped orders for this customer
					boolean cuOrders = amazon.printOrderHistory(customerId);//calls function in ECommerceSystem
					if (cuOrders == false){
						System.out.println(amazon.getErrorMessage());//error checking
					}
				}
				else if (action.equalsIgnoreCase("ORDER")) // order a product for a certain customer
				{
					String productId = "";
					String customerId = "";
	
					System.out.print("Product Id: ");
				// Get product Id from scanner
				if (scanner.hasNextLine()){
					productId = scanner.nextLine();
				}
					
					System.out.print("\nCustomer Id: ");
				// Get customer Id from scanner
					if (scanner.hasNextLine()){
						customerId = scanner.nextLine();
					}
					// Order the product. Check for valid orderNumber string return and for error message set in ECommerceSystem
					String success = amazon.orderProduct(productId, customerId, "");
					if (success == null){
						System.out.println(amazon.getErrorMessage());//error checking
					}
					// Print Order Number string returned from method in ECommerceSystem
					else
						System.out.println("Order #"+success);
					
				}
				else if (action.equalsIgnoreCase("ORDERBOOK")) // order a book for a customer, provide a format (Paperback, Hardcover or EBook)
				{
					String productId = "";
					String customerId = "";
					String options = "";

					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine()){
						productId = scanner.nextLine();
					}

					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine()){
						customerId = scanner.nextLine();
					}
					
					System.out.print("\nFormat [Paperback Hardcover EBook]: ");
					// get book forma and store in options string
					if (scanner.hasNextLine()){
						options = scanner.nextLine();
					}
					
					
					// Order product. Check for error mesage set in ECommerceSystem
					String order = amazon.orderProduct(productId, customerId, options);//calls function in ECommerceSystem
					if (order == null){
						System.out.println(amazon.getErrorMessage());//error checking
					}
					else
					// Print order number string if order number is not null
						System.out.println("Order #" + order);
				}
				else if (action.equalsIgnoreCase("ORDERSHOES")) // order shoes for a customer, provide size and color 
				{
					String productId = "";
					String customerId = "";
					String options = "";
					
					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine()){
						productId = scanner.nextLine();
					}

					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine()){
						customerId = scanner.nextLine();
					}
					
					System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
					// get shoe size and store in options	
					if (scanner.hasNextLine()){
						options = scanner.nextLine();
					}
					
					System.out.print("\nColor: \"Black\" \"Brown\": ");
					// get shoe color and append to options
					if (scanner.hasNextLine()){
						options = options + " " + scanner.nextLine();
					}
					//order shoes
					String valid = amazon.orderProduct(productId, customerId, options);//calls function in ECommerceSystem
					if (valid == null){
						System.out.println(amazon.getErrorMessage());//error checking
					}
					else{
						System.out.println("Order #"+valid);
					}
				}
				
				
				else if (action.equalsIgnoreCase("CANCEL")) // Cancel an existing order
				{
					String orderNumber = "";

					System.out.print("Order Number: ");
					// get order number from scanner
					if (scanner.hasNextLine()){
						orderNumber = scanner.nextLine();
					}
					// cancel order. Check for error
					boolean cancelOrder2 = amazon.cancelOrder(orderNumber);//calls function in ECommerceSystem
					if (cancelOrder2 == false){
						System.out.println(amazon.getErrorMessage());//error checking
					}
				}
				else if (action.equalsIgnoreCase("BOOKSBYAUTHOR")){
					String author = "";

					System.out.print("Author: ");
					if (scanner.hasNextLine()){
						author = scanner.nextLine();
					}
					// 
					boolean authorbooks = amazon.sortBooksByAuthor(author);//calls function in ECommerceSystem
						if (authorbooks == false){
							System.out.println(amazon.getErrorMessage());//error checking
						}

				}
				else if (action.equalsIgnoreCase("PRINTBYPRICE")) // sort products by price
				{
					amazon.printByPrice();//calls function in ECommerceSystem
				}
				else if (action.equalsIgnoreCase("PRINTBYNAME")) // sort products by name (alphabetic)
				{
					amazon.printByName();//calls function in ECommerceSystem
				}
				else if (action.equalsIgnoreCase("SORTCUSTS")) // sort products by name (alphabetic)
				{
					amazon.sortCustomersByName();//calls function in ECommerceSystem
				}
				else if (action.equalsIgnoreCase("ADDTOCART")){

					//variables used to store
					String productID = "";
					String customerID = "";
					String productOptions = "";
					
					System.out.print("Product ID: ");//collects product id
					if (scanner.hasNextLine()){
						productID = scanner.nextLine();
					}

					System.out.print("Customer ID: ");//collects custID
					if (scanner.hasNextLine()){
						customerID = scanner.nextLine();
					}

					//if its a book
					if (productID.equals("706") || productID.equals("707") || productID.equals("708") || productID.equals("710") || productID.equals("702")){
						System.out.print("\nFormat [Paperback Hardcover EBook]: ");
						if (scanner.hasNextLine()){
							productOptions = scanner.nextLine();
						}
					}
					
					amazon.addToCart(productID, customerID, productOptions);
					//a. ADDTOCART: String productid, String customerID, String productOptions
				}
				//removes cart item
				else if (action.equalsIgnoreCase("REMCARTITEM")){
					String productID = "";
					String customerID = "";

					System.out.print("Product ID: ");//collects product id
					if (scanner.hasNextLine()){
						productID = scanner.nextLine();
					}

					System.out.print("Customer ID: ");//collects custID
					if (scanner.hasNextLine()){
						customerID = scanner.nextLine();
					} 
					amazon.removeCartItem(productID,customerID);
				}
				//prints cart items of a customer
				else if (action.equalsIgnoreCase("PRINTCART")){
					String customerID = "";

					System.out.print("Customer ID: ");
					if (scanner.hasNextLine()){
						customerID = scanner.nextLine();
					} 
					amazon.printCart(customerID);
				}

				//moves all products from customers cart to orders
				else if (action.equalsIgnoreCase("ORDERITEMS")){
					String customerID = "";

					System.out.print("Customer ID: ");
					if (scanner.hasNextLine()){
						customerID = scanner.nextLine();
					} 
					amazon.orderItems(customerID);
				}

				//prints most frequently bought item to least
				else if (action.equalsIgnoreCase("STATS")){
					amazon.orderstats();
				}

				//used to collect rating of a specific prodcut
				else if (action.equalsIgnoreCase("RATE")){
					String rating = "";
					String productID = "";

					System.out.print("Product ID: ");//collects prodid and rating 
					if (scanner.hasNextLine()){
						productID = scanner.nextLine();
					} 
					System.out.print("Rating: ");
					if (scanner.hasNextLine()){
						rating = scanner.nextLine();
					} 

					amazon.rateProduct(productID, rating);
					
				}
				//prints rating of a specific product
				else if (action.equalsIgnoreCase("PRINTRATING")){
					String productID = "";

					System.out.print("Product ID: ");
					if (scanner.hasNextLine()){
						productID = scanner.nextLine();
					} 
					amazon.printRating(productID);
				}

				//used to check all products above a specific rating
				else if (action.equalsIgnoreCase("RATINGABOVE")){
					String rating = "";
					String category = "";

					System.out.print("Rating: ");
					if (scanner.hasNextLine()){
						rating = scanner.nextLine();
					} 

					System.out.println("Product Category: ");
					if (scanner.hasNextLine()){
						category = scanner.nextLine();
					}
					amazon.printAllAboveThreshold(rating, category);
				}
				//catches all the exceptions used
			}catch (UnknownCustomerException a){
				System.out.println(a.getMessage());
			}catch (UnknownProductException a){
				System.out.println(a.getMessage());
			}catch (InvalidProductOptionException a){
				System.out.println(a.getMessage());
			}catch (OutOfStockException a){
				System.out.println(a.getMessage());
			}catch (InvalidCustomerAddressException a){
				System.out.println(a.getMessage());
			}catch (InvalidCustomerNameException a){
				System.out.println(a.getMessage());
			}catch (InvalidOrderNumberException a){
				System.out.println(a.getMessage());
			}catch (InvalidCategoryException a){
				System.out.println(a.getMessage());
			}catch (InvalidRateException a){
				System.out.println(a.getMessage());
			}
			System.out.print("\n>");
		}
	}
}
