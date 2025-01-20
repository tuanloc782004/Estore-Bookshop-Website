package com.estorebookshop.config.service;

import java.io.IOException;
import java.util.List;

import com.estorebookshop.config.model.ContactMessage;

public interface ExcelService {
	
	void writeMessagesToExcel(List<ContactMessage> messages) throws IOException;

}
