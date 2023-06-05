package com.btl2.bookstore.book;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.btl2.bookstore.user.UserDAO;

@Controller
public class CustomerController {
	@GetMapping("/home/{username}")
	public String getHome(Model model, @PathVariable String username) {
		UserDAO userDAO = new UserDAO();
		int iduser = userDAO.getIDUser(username);
		BookOrderDAO bookOrderDAO = new BookOrderDAO();
		List<BookOrder> listOrder = bookOrderDAO.GetAllItemCart(iduser);
		int totalQuantity = 0;
		for (Iterator iterator = listOrder.iterator(); iterator.hasNext();) {
			BookOrder bookOrder = (BookOrder) iterator.next();
			totalQuantity += bookOrder.getQuantity();
		}
		
		BookDAO bookDAO = new BookDAO();
		List<Book> results = bookDAO.selectAllBook();
		
		model.addAttribute("totalQuantity", totalQuantity);
		model.addAttribute("username", username);
		model.addAttribute("books", results);
		BookOrder bookorder = new BookOrder();
		model.addAttribute("bookorder", bookorder);
		return "home-page";
	}
	@GetMapping("/{username}/{category}")
	public String getCategory(Model model,@PathVariable String username ,@PathVariable String category) {
		BookDAO bookDAO = new BookDAO();
		List<Book> results = bookDAO.selectBookByCategory(category);
		
		UserDAO userDAO = new UserDAO();
		int iduser = userDAO.getIDUser(username);
		BookOrderDAO bookOrderDAO = new BookOrderDAO();
		List<BookOrder> listOrder = bookOrderDAO.GetAllItemCart(iduser);
		int totalQuantity = 0;
		for (Iterator iterator = listOrder.iterator(); iterator.hasNext();) {
			BookOrder bookOrder = (BookOrder) iterator.next();
			totalQuantity += bookOrder.getQuantity();
		}
		
		model.addAttribute("totalQuantity", totalQuantity);
		model.addAttribute("username", username);
		model.addAttribute("books", results);
		BookOrder bookorder = new BookOrder();
		model.addAttribute("bookorder", bookorder);
		return "home-page";
	}
	@PostMapping("/{username}/search")
	public String getBookByName(Model model,@PathVariable String username,@RequestParam("searchname") String name, @RequestParam("category") String category) {
		BookDAO bookDAO = new BookDAO();
		List<Book> results = bookDAO.searchBook(name, category);
		UserDAO userDAO = new UserDAO();
		int iduser = userDAO.getIDUser(username);
		BookOrderDAO bookOrderDAO = new BookOrderDAO();
		List<BookOrder> listOrder = bookOrderDAO.GetAllItemCart(iduser);
		int totalQuantity = 0;
		for (Iterator iterator = listOrder.iterator(); iterator.hasNext();) {
			BookOrder bookOrder = (BookOrder) iterator.next();
			totalQuantity += bookOrder.getQuantity();
		}
		
		model.addAttribute("totalQuantity", totalQuantity);
		model.addAttribute("username", username);
		model.addAttribute("books", results);
		BookOrder bookorder = new BookOrder();
		model.addAttribute("bookorder", bookorder);
		return "home-page";
	}
	@GetMapping("/detail/{user}/{idbook}")
	public String getDetailBook(Model model, @PathVariable String user, @PathVariable String idbook) {
			BookDAO bookDAO = new BookDAO();
			Book book = bookDAO.getBook(Integer.parseInt(idbook));
			RatingDAO ratingDAO = new RatingDAO();
			List<Rating> list = new ArrayList<Rating>();
			list = ratingDAO.getAllRating(Integer.parseInt(idbook));
			if (list.isEmpty()) {
				model.addAttribute("ratings", null);
				model.addAttribute("averrating", null);
			}
			else {
				float avarating = 0;
				for (Iterator iterator1 = list.iterator(); iterator1.hasNext();) {
					Rating rating = (Rating) iterator1.next();
					avarating += rating.getStars();
				}
				avarating = (Math.round((avarating/list.size())*100)/100);
				model.addAttribute("ratings", list);
				model.addAttribute("averrating", avarating);
			}
			UserDAO userDAO = new UserDAO();
			int iduser = userDAO.getIDUser(user);
			BookOrderDAO bookOrderDAO = new BookOrderDAO();
			List<BookOrder> listOrder = bookOrderDAO.GetAllItemCart(iduser);
			int totalQuantity = 0;
			for (Iterator iterator = listOrder.iterator(); iterator.hasNext();) {
				BookOrder bookOrder = (BookOrder) iterator.next();
				totalQuantity += bookOrder.getQuantity();
			}
			Rating rate = new Rating();
			model.addAttribute("rating", rate);
			model.addAttribute("totalQuantity", totalQuantity);
			model.addAttribute("book", book);
			model.addAttribute("username", user);
			model.addAttribute("bookorder", new BookOrder());
			return "product-detail";
	}
	@PostMapping("/review/{username}/{idbook}")
	public String reviewBook(Model model, Rating rating, @PathVariable String username,@PathVariable String idbook) {
		RatingDAO ratingDAO = new RatingDAO();
		rating.setIdbook(Integer.parseInt(idbook));
		rating.setUsername(username);
		ratingDAO.insertRating(rating);
		model.addAttribute("username", username);
		return "redirect:/detail/" + username + "/" + idbook;
	}
	
	@PostMapping("/addtocart/{username}/{idbook}")
	public String addToCart(Model model, BookOrder bookorder,@PathVariable String username, @PathVariable String idbook) {
		UserDAO userDAO = new UserDAO();
		int iduser = userDAO.getIDUser(username);
		bookorder.setIdbookorder(0);
		bookorder.setIduser(iduser);
		bookorder.setIdbook(Integer.parseInt(idbook));
		bookorder.setCheckout(0);
		BookOrderDAO bookOrderDAO = new BookOrderDAO();
		int quantity = bookorder.getQuantity();
		if (quantity == 0) {
			bookorder.setQuantity(1);
		}
		bookOrderDAO.AddToCart(bookorder);
		return "redirect:/home/" + username;
	}
	@GetMapping("/viewcart/{username}")
	public String viewCart(Model model, @PathVariable String username) {
		UserDAO userDAO = new UserDAO();
		BookOrderDAO bookOrderDAO = new BookOrderDAO();
		int iduser = userDAO.getIDUser(username);
		List<BookOrder> listOrder = bookOrderDAO.GetAllItemCart(iduser);
		float total = 0;
		int totalQuantity = 0;
		for (Iterator iterator = listOrder.iterator(); iterator.hasNext();) {
			BookOrder bookOrder = (BookOrder) iterator.next();
			total += bookOrder.getQuantity()*bookOrder.getBook().getPrice();
			totalQuantity += bookOrder.getQuantity();
		}
//		BookOrder bookOrder = new BookOrder();
//		model.addAttribute("bookorder", bookOrder);
		model.addAttribute("checkout", new CheckOut());
		model.addAttribute("username", username);
		model.addAttribute("totalAmount", total);
		model.addAttribute("cartItems", listOrder);
		model.addAttribute("totalQuantity", totalQuantity);
		return "cart";
	}
	
	@GetMapping("/remove/{username}/{idbookorder}")
	public String removeItem(Model model, @PathVariable String username, @PathVariable String idbookorder) {
		UserDAO userDAO = new UserDAO();
		BookOrderDAO bookOrderDAO = new BookOrderDAO();
		int iduser = userDAO.getIDUser(username);
		bookOrderDAO.removeItem(iduser, Integer.parseInt(idbookorder));
		return "redirect:/viewcart/" + username;
	}
	
	@PostMapping("/checkout/{username}/{finalprice}")
	public String insertCheckOut(@PathVariable String username,@PathVariable float finalprice, CheckOut checkOut) {
		CheckOutDAO checkOutDAO = new CheckOutDAO();
		UserDAO userDAO = new UserDAO();
		int iduser = userDAO.getIDUser(username);
		checkOut.setIduser(iduser);
		checkOut.setFinalprice(finalprice);
		checkOutDAO.insertCheckOut(checkOut);
		return "redirect:/home/" + username;
	}
	
	@GetMapping("/order/{username}/{totalQuantity}")
	public String getAllCheckOut(Model model, @PathVariable String username, @PathVariable int totalQuantity) {
		CheckOutDAO checkOutDAO = new CheckOutDAO();
		UserDAO userDAO = new UserDAO();
		int iduser = userDAO.getIDUser(username);
		List<CheckOut> checkoutlist = checkOutDAO.getCheckOuts(iduser);
		model.addAttribute("checkoutlist", checkoutlist);
		model.addAttribute("username", username);
		model.addAttribute("totalQuantity", totalQuantity);
		return "orders";
	}
	
	@GetMapping("/cancel/{user}/{idcheckout}/{totalQuantity}")
	public String cancelCheckOut(@PathVariable String user,@PathVariable int idcheckout, @PathVariable int totalQuantity) {
		CheckOutDAO checkOutDAO = new CheckOutDAO();
		UserDAO userDAO = new UserDAO();
		int iduser = userDAO.getIDUser(user);
		checkOutDAO.deleteCheckOut(iduser, idcheckout);
		return "redirect:/order/" + user +"/"+ String.valueOf(totalQuantity);
	}
}
