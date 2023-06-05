package com.btl2.bookstore.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.btl2.bookstore.book.Book;
import com.btl2.bookstore.book.BookDAO;

@Controller
public class UserController {
	@GetMapping("/")
	public String init(User user) {
		return "login";
	}
	@GetMapping("/disregister")
	public String displayRegis(User user) {
		return "register";
	}
	@PostMapping("register")
	public String register(@Valid User user, BindingResult result,Model model) {
		if(result.hasErrors()) {
			return "register";
		}
		UserDAO userDAO = new UserDAO();
		String Error = "username exist!!!";
		if (userDAO.checkExistUser(user)) {
			model.addAttribute("error", Error);
			return "register";
		}
		userDAO.addUser(user);
		return "login";
	}
	@PostMapping("/login")
	public String checkLogin(User user,Model model) {
		String Error = "username and password must not be blank";
		if (user.getUsername() == "" || user.getPassword() == "") {
			model.addAttribute("error", Error);
			return "login";
		}
		UserDAO userDAO = new UserDAO();
		if (userDAO.checkUserandRole(user).equals("admin")) { 
			return "redirect:/books";
		}
		else if(userDAO.checkUserandRole(user).equals("customer")) {
			return "redirect:/home/"+user.getUsername();
		}
		Error = "username or password invalid";
		model.addAttribute("error", Error);
		return "login";
	}
}

