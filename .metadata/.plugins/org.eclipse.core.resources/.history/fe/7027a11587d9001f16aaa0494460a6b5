package com.estorebookshop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estorebookshop.config.model.ContactMessage;
import com.estorebookshop.config.service.ExcelService;

@Controller
@RequestMapping("/contact")
public class ContactController {
	
	@Autowired
    private ExcelService excelService;
	
	private List<ContactMessage> messages = new ArrayList<>();

	@RequestMapping("")
	public String contact(Model model) {
		
        return "contact";  
    
	}
	
	@PostMapping("")
    public String processContactForm(@RequestParam String name, 
                                     @RequestParam String email, 
                                     @RequestParam String subject, 
                                     @RequestParam String message, 
                                     Model model) throws IOException {
      
		// Lưu thông tin form vào danh sách message
        ContactMessage contactMessage = new ContactMessage(name, email, subject, message);
        messages.add(contactMessage);
        
        // Sau khi gửi xong, lưu các message vào file Excel
        excelService.writeMessagesToExcel(messages);

        // Thêm thông báo cho người dùng khi gửi thành công
        model.addAttribute("message", "Your message has been sent successfully!");
        
        return "contact";  
    }

}
