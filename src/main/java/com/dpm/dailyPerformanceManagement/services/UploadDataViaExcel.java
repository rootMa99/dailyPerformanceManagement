package com.dpm.dailyPerformanceManagement.services;

import com.dpm.dailyPerformanceManagement.models.RequestModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

@Service
public class UploadDataViaExcel {

    public static boolean isValidFormat(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static List<RequestModel> getDataFromExcel(InputStream is){
        System.out.println("extraction started");
        List<RequestModel> rrm=new ArrayList<>();
        boolean done = false;
        try{
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("DATA");
            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                if (done) {
                    break;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                RequestModel rm=new RequestModel();
                while (cellIterator.hasNext() && !done) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    switch (cellIndex) {
                        case 0 -> {
                            if (cell.getCellType() == BLANK) {
                                done = true;
                            }
                            rm.setDate(cell.getDateCellValue());
                        }
                        case 1 -> {
                            if (cell.getCellType() == CellType.NUMERIC) {
                                rm.setReal(cell.getNumericCellValue());
                            }
                        }
                        case 2 -> {
                            if (cell.getCellType() == CellType.NUMERIC) {
                                rm.setTarget(cell.getNumericCellValue());
                            }
                        }
                        case 3 -> {
                            if (cell.getCellType() == CellType.STRING) {
                                rm.setAlias(cell.getStringCellValue());
                            }
                        }
                        case 4 -> {
                            if (cell.getCellType() == CellType.STRING) {
                                rm.setType(cell.getStringCellValue());
                            }
                        }
                        default -> {

                        }
                    }
                    cellIndex++;
                    if (cellIndex == 5) {
                        rowIndex++;
                        break;
                    }
                }
                rowIndex++;
                rrm.add(rm);
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return rrm;
    }
}
