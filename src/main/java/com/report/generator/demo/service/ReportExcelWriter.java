/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package com.report.generator.demo.service;

import com.report.generator.demo.repository.entity.MessageData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;

/**
 * @author Afanasev E.V.
 * @version 1.0 5/21/2021
 */
public class ReportExcelWriter {

    private final XSSFWorkbook wb;
    private final XSSFSheet sheet;

    public ReportExcelWriter() {
        this.wb = new XSSFWorkbook();
        this.sheet = wb.createSheet();
        createTitle();
    }

    public void createRow(int index, MessageData data) {
        XSSFRow row = sheet.createRow(index);
        setCellValue(row.createCell(0), data.getMessageDate());
        setCellValue(row.createCell(1), data.getName());
        setCellValue(row.createCell(2), data.getRating());
        setCellValue(row.createCell(3), data.getText());
    }

    public void writeWorkbook() throws IOException {
        FileOutputStream fileOut = new FileOutputStream(Instant.now().getEpochSecond() + ".xlsx");
        wb.write(fileOut);
        fileOut.close();
    }

    private void createTitle() {
        XSSFRow rowTitle = sheet.createRow(0);
        setCellValue(rowTitle.createCell(0), "Date");
        setCellValue(rowTitle.createCell(1), "Name");
        setCellValue(rowTitle.createCell(2), "Rating");
        setCellValue(rowTitle.createCell(3), "Text");
    }

    private void setCellValue(XSSFCell cell, String value) {
        cell.setCellValue(value);
    }

    private void setCellValue(XSSFCell cell, long value) {
        cell.setCellValue(value);
    }

    private void setCellValue(XSSFCell cell, Instant value) {
        cell.setCellValue(value.toString());
    }
}
