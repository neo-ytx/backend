package edu.fudan.backend.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.sl.extractor.SlideShowExtractor;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;

@Component
@Slf4j
public class POIUtils {
    public String document2String(MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            String type = filename.substring(filename.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            switch (type) {
                case ".doc":
                    return this.readWord(inputStream);
                case ".docx":
                    return this.readWordX(inputStream);
                case ".pdf":
                    return this.readPdf(inputStream);
                case ".csv":
                case ".xls":
                case ".xlsx":
                    return this.readExcel(inputStream);
                case ".ppt":
                case ".pptx":
                    return this.readPowerpoint(inputStream);
                default:
                    return this.readText(inputStream);

            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("POI read IO error");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("POI other error");
        }
        return "";
    }

    private String readWord(InputStream inputStream) throws IOException {
        HWPFDocument document = new HWPFDocument(inputStream);
        StringBuilder result = document.getText();
        inputStream.close();
        document.close();
        return result.toString();
    }

    private String readWordX(InputStream inputStream) throws Exception {
        XWPFDocument document = new XWPFDocument(inputStream);
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);
        String result = extractor.getText();
        inputStream.close();
        extractor.close();
        document.close();
        return result;
    }

    private String readExcel(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            workbook = new HSSFWorkbook(inputStream);
        }
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet == null) continue;
            for (int j = 0; j < sheet.getLastRowNum(); j++) {
                Row row = sheet.getRow(j);
                if (row == null) continue;
                for (int k = 0; k < row.getLastCellNum(); k++) {
                    String cell = convertCellValueToString(row.getCell(k));
                    stringBuilder.append(cell).append(" ");
                }
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    private static String convertCellValueToString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
                Double doubleValue = cell.getNumericCellValue();
                DecimalFormat df = new DecimalFormat("0");
                return df.format(doubleValue);
            case STRING:    //字符串
                return cell.getStringCellValue();
            case BOOLEAN:   //布尔
                boolean booleanValue = cell.getBooleanCellValue();
                return Boolean.toString(booleanValue);
            case FORMULA:   // 公式
                return cell.getCellFormula();
            case BLANK:     // 空值
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return "";
    }

    private String readPowerpoint(InputStream inputStream) throws IOException {
        SlideShow slideShow = null;
        try {
            slideShow = new XMLSlideShow(inputStream);
        } catch (Exception e) {
            slideShow = new HSLFSlideShow(inputStream);
        }
        SlideShowExtractor slideShowExtractor = new SlideShowExtractor(slideShow);
        return slideShowExtractor.getText();
    }

    private String readText(InputStream inputStream) throws Exception {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString();
    }

    private String readPdf(InputStream inputStream) throws IOException {
        PDDocument document = PDDocument.load(inputStream);
        StringBuilder stringBuilder = new StringBuilder();
        if (!document.isEncrypted()) {
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            PDFTextStripper textStripper = new PDFTextStripper();
            String pdfFileInText = textStripper.getText(document);
            String[] lines = pdfFileInText.split("\\r?\\n");
            for (String line : lines) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String ags[]) throws Exception {
        File file = new File("c:/Users/ytxlo/Desktop/test1.docx");
        InputStream inputStream = new FileInputStream(file);
        new POIUtils().readWordX(inputStream);
    }
}
