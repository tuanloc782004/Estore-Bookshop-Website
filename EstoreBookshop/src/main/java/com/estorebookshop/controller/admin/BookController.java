package com.estorebookshop.controller.admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.misc.Array2DHashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
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

		Page<Book> list = this.bookService.findAll(pageNo);

		if (keyword != null) {
			list = this.bookService.findByKeyword(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}

		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("books", list);
		return "admin/book/book";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		Book book = this.bookService.findById(id);
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

		return "admin/book/book-form";
	}

	@PostMapping("/edit")
	public String update(@ModelAttribute("book") Book book, @RequestParam List<Long> categoryIds,
			@RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes) {

		// Cập nhật danh mục sách
		List<Category> selectedCategories = this.categoryService.findAllById(categoryIds);
		this.bookCategoryService.deleteByBook(book); // Xóa các mối quan hệ cũ

		for (Category category : selectedCategories) {
			BookCategory bookCategory = new BookCategory();
			bookCategory.setBook(book);
			bookCategory.setCategory(category);
			this.bookCategoryService.save(bookCategory); // Lưu các mối quan hệ mới
		}

		// Thêm ảnh mới
		Set<BookImage> newImageEntities = new Array2DHashSet<BookImage>();
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				// Đặt tên cho file ảnh mới
				String imageUrl = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + "_"
						+ file.getOriginalFilename();
				this.storageService.store(file, imageUrl); // Lưu ảnh mới với tên đã tạo

				BookImage bookImage = new BookImage();
				bookImage.setImageUrl("/upload-dir/" + imageUrl); // Lưu đường dẫn ảnh
				bookImage.setBook(book); // Liên kết ảnh với sách
				this.bookImageService.save(bookImage);
				newImageEntities.add(bookImage);
			}
		}

		// Cập nhật ảnh mới vào sách
		book.setBookImages(newImageEntities);
		
		book.setRating(this.bookService.findById(book.getId()).getRating());

		// Lưu sách với các thay đổi
		this.bookService.save(book);
		redirectAttributes.addFlashAttribute("message", "Book updated successfully!");
		return "redirect:/admin/book"; // Điều hướng về danh sách sách sau khi cập nhật
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		this.bookService.deleteById(id);
		return "redirect:/admin/book";
	}
}
