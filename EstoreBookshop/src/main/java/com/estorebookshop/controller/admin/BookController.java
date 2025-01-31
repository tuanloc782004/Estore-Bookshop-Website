package com.estorebookshop.controller.admin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.misc.Array2DHashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estorebookshop.config.service.StorageService;
import com.estorebookshop.model.Book;
import com.estorebookshop.model.BookCategory;
import com.estorebookshop.model.BookImage;
import com.estorebookshop.model.Category;
import com.estorebookshop.model.Language;
import com.estorebookshop.model.Publisher;
import com.estorebookshop.service.BookCategoryService;
import com.estorebookshop.service.BookImageService;
import com.estorebookshop.service.BookService;
import com.estorebookshop.service.CategoryService;
import com.estorebookshop.service.LanguageService;
import com.estorebookshop.service.PublisherService;

@Controller
@RequestMapping("/admin/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private PublisherService publisherService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private BookCategoryService bookCategoryService;

	@Autowired
	private BookImageService bookImageService;

	@RequestMapping("")
	public String book(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		model.addAttribute("current", "book");
		try {
			Page<Book> list = this.bookService.findAll(pageNo);

			if (keyword != null) {
				list = this.bookService.findByKeyword(keyword, pageNo);
				model.addAttribute("keyword", keyword);
			}

			model.addAttribute("totalPage", list.getTotalPages());
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("books", list);
			return "admin/book/book";
		} catch (Exception e) {
			model.addAttribute("error", "An error occurred while fetching books: " + e.getMessage());
			return "admin/book/book";
		}
	}

	@GetMapping("/add")
	public String add(Model model) {
		try {
			Book book = new Book();
			book.setBookCategories(new HashSet<BookCategory>());
			model.addAttribute(book);

			List<Category> categories = this.categoryService.findAll();
			model.addAttribute("categories", categories);

			Set<Long> categoryIds = book.getBookCategories().stream()
					.map(bookCategory -> bookCategory.getCategory().getId()).collect(Collectors.toSet());
			model.addAttribute("categoryIds", categoryIds);

			List<Language> languages = this.languageService.findAll();
			model.addAttribute("languages", languages);

			List<Publisher> publishers = this.publisherService.findAll();
			model.addAttribute("publishers", publishers);

			model.addAttribute("current", "book");

			return "admin/book/book-form";
		} catch (Exception e) {
			model.addAttribute("error", "An error occurred while preparing the add book form: " + e.getMessage());
			return "redirect:/admin/book";
		}
	}

	@PostMapping("/add")
	public String create(@ModelAttribute("book") Book book, @RequestParam List<Long> categoryIds,
			@RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes) {
		try {
			book.setRating(BigDecimal.valueOf(0.00));
			this.bookService.save(book);

			List<Category> selectedCategories = this.categoryService.findAllById(categoryIds);

			for (Category category : selectedCategories) {
				BookCategory bookCategory = new BookCategory();
				bookCategory.setBook(book);
				bookCategory.setCategory(category);
				this.bookCategoryService.save(bookCategory);
			}

			Set<BookImage> newImageEntities = new Array2DHashSet<>();
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					String imageUrl = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + "_"
							+ file.getOriginalFilename();
					this.storageService.store(file, imageUrl);

					BookImage bookImage = new BookImage();
					bookImage.setImageUrl("/upload-dir/" + imageUrl);
					bookImage.setBook(book);
					this.bookImageService.save(bookImage);
					newImageEntities.add(bookImage);
				}
			}

			book.setBookImages(newImageEntities);
			redirectAttributes.addFlashAttribute("message", "Book added successfully!");
			return "redirect:/admin/book";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Failed to add book: " + e.getMessage());
			return "redirect:/admin/book/add";
		}
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		try {
			Book book = this.bookService.findById(id);
			if (book == null) {
				model.addAttribute("error", "Book not found!");
				return "redirect:/admin/book";
			}

			model.addAttribute("book", book);

			List<Category> categories = this.categoryService.findAll();
			model.addAttribute("categories", categories);

			Set<Long> categoryIds = book.getBookCategories().stream()
					.map(bookCategory -> bookCategory.getCategory().getId()).collect(Collectors.toSet());
			model.addAttribute("categoryIds", categoryIds);

			List<Language> languages = this.languageService.findAll();
			model.addAttribute("languages", languages);

			List<Publisher> publishers = this.publisherService.findAll();
			model.addAttribute("publishers", publishers);

			model.addAttribute("current", "book");

			return "admin/book/book-form";
		} catch (Exception e) {
			model.addAttribute("error", "An error occurred while fetching book details: " + e.getMessage());
			return "redirect:/admin/book";
		}
	}

	@PostMapping("/edit")
	public String update(@ModelAttribute("book") Book book, @RequestParam List<Long> categoryIds,
			@RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes) {
		try {
			List<Category> selectedCategories = this.categoryService.findAllById(categoryIds);
			this.bookCategoryService.deleteByBook(book);

			for (Category category : selectedCategories) {
				BookCategory bookCategory = new BookCategory();
				bookCategory.setBook(book);
				bookCategory.setCategory(category);
				this.bookCategoryService.save(bookCategory);
			}

			Set<BookImage> newImageEntities = new Array2DHashSet<>();
			if (files != null) {
				for (MultipartFile file : files) {
					if (!file.isEmpty()) {
						String imageUrl = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
								+ "_" + file.getOriginalFilename();
						this.storageService.store(file, imageUrl);

						BookImage bookImage = new BookImage();
						bookImage.setImageUrl("/upload-dir/" + imageUrl);
						bookImage.setBook(book);
						this.bookImageService.save(bookImage);
						newImageEntities.add(bookImage);
					}
				}
			}

			book.setBookImages(newImageEntities);
			book.setRating(this.bookService.findById(book.getId()).getRating());
			this.bookService.save(book);

			redirectAttributes.addFlashAttribute("message", "Book updated successfully!");
			return "redirect:/admin/book";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Failed to update book: " + e.getMessage());
			return "redirect:/admin/book/edit/" + book.getId();
		}
	}

	@GetMapping("/delete/{id}")
	@Transactional
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			this.bookCategoryService.deleteByBookId(id);
			this.bookImageService.deleteByBookId(id);
			this.bookService.deleteById(id);
			redirectAttributes.addFlashAttribute("message", "Book deleted successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Failed to delete book: " + e.getMessage());
		}
		return "redirect:/admin/book";
	}

	@GetMapping("/set-enabled/{id}")
	public String setEnabled(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			Book book = this.bookService.findById(id);
			if (book == null) {
				redirectAttributes.addFlashAttribute("error", "Book not found!");
				return "redirect:/admin/book";
			}

			book.setEnabled(!book.isEnabled());
			this.bookService.save(book);

			redirectAttributes.addFlashAttribute("message", "Book status updated successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Failed to update book status: " + e.getMessage());
		}
		return "redirect:/admin/book";
	}
}
