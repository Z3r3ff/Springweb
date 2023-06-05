package com.btl2.bookstore.book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.btl2.bookstore.user.UserDAO;

@Controller
public class AdminController {
	
	//admin
	@GetMapping("/books")
	public String getBooks(Model model) {
		BookDAO bookDAO = new BookDAO();
		List<Book> results = bookDAO.selectAllBook();
		model.addAttribute("books", results);
		return "books";
	}
	
	@GetMapping("/book/{idbook}")
	public String getBook(Model model, @PathVariable String idbook) {
		model.addAttribute("idbook",idbook);
		BookDAO bookDAO = new BookDAO();
		Book book = bookDAO.getBook(Integer.parseInt(idbook));
		model.addAttribute("book", book);
		return "Book-detail";
	}
	
	@PostMapping("/save/{idbook}")
	public String addBook(@Valid Book book,BindingResult result ,@PathVariable String idbook,@RequestParam("image") MultipartFile file) {
		if(result.hasErrors()) {
			return "Book-detail";
		}
		BookDAO bookDAO = new BookDAO();
		bookDAO.insertBook(book, file);
		return "redirect:/books";
	}
	
	@PutMapping("/save/{idbook}")
	public String updateBook(@Valid Book book,BindingResult result,@PathVariable String idbook,@RequestParam("image") MultipartFile file) {
		if(result.hasErrors()) {
			return "Book-detail";
		}
		BookDAO bookDAO = new BookDAO();
		bookDAO.updateBook(book, file);
		return "redirect:/books";
	}
	
	@GetMapping("/book/delete/{idbook}")
	public String deleteBook(Book book, @PathVariable String idbook) {
		BookDAO bookDAO = new BookDAO();
		bookDAO.deleteBook(Integer.parseInt(idbook));
		return "redirect:/books";
	}
	
	@GetMapping("/delete/confirm/{idbook}")
	public String confirmDele(Book book,@PathVariable String idbook ,Model model) {
		model.addAttribute("idbook", idbook);
		return "confirmdele";
	}
}

