package com.estorebookshop.config.service.impl;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.estorebookshop.config.model.ContactMessage;
import com.estorebookshop.config.service.ExcelService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public void writeMessagesToExcel(List<ContactMessage> messages) throws IOException {
        // Tạo workbook Excel mới
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Messages");

        // Tạo tiêu đề cho các cột
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Email");
        headerRow.createCell(2).setCellValue("Subject");
        headerRow.createCell(3).setCellValue("Message");

        // Thêm dữ liệu vào các dòng tiếp theo
        int rowNum = 1;
        for (ContactMessage message : messages) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(message.getName());
            row.createCell(1).setCellValue(message.getEmail());
            row.createCell(2).setCellValue(message.getSubject());
            row.createCell(3).setCellValue(message.getMessage());
        }

        // Lưu file Excel vào ổ đĩa
        try (FileOutputStream fileOut = new FileOutputStream("messages.xlsx")) {
            workbook.write(fileOut);
        }

        workbook.close();
    }
}

