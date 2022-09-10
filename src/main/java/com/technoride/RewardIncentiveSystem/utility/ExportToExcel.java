package com.technoride.RewardIncentiveSystem.utility;

import com.technoride.RewardIncentiveSystem.model.PdfHeadTitle;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.poi.ss.usermodel.*;

import java.nio.file.*;
import java.nio.file.attribute.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@Component
public class ExportToExcel {

    @Value("${excelPath}")
    private String excelPath;

    @Value("${pdfConfig}")
    private String pdfConfigPath;
    @Value("${configFilePath}")
    private String configFilePath;
    int excelStartRow;
    String rightAllignColumnIndex;
    String integerColumnIndex,rightColString;
    String pdfFileFormat = "";
    String[] headings;
    public void doExcel(String templateName, String fileFormat, List<List<String>> excelValues, String pdfReportName, String heading, String reportFileName, HttpServletResponse response) throws IOException, DocumentException {
        getConfigFileProperties(templateName);
        ArrayList<String> files = new ArrayList<>();
        String filePath = copy(templateName, reportFileName);
        String s[] = pdfReportName.split("&");
        System.out.println(filePath);
        XSSFWorkbook wb = null;
        System.out.println(filePath);
        FileInputStream fileIn = new FileInputStream(filePath);
        wb = new XSSFWorkbook(fileIn);
        Sheet sheet = wb.getSheetAt(0);
        headings = heading.split("&");
        List<String> rightAllignIndexList = new ArrayList<>();
        List<Integer> rightAllignIndexList1 = new ArrayList<>();
        List<String> rightColStringIndexList = new ArrayList<>();
        List<Integer> rightColStringIndexList1 = new ArrayList<>();
        int rightIndex = 3;
        String rtIndexFlag = "false";
        if(!rightColString.equals("")) {
            if (rightColString.contains(",")) {
                rightColStringIndexList = Arrays.asList(rightColString.split(","));
            } else {
                rightColStringIndexList.add(rightColString);
            }
        }
        if(rightColStringIndexList.size()>0){
            for (String s1:rightColStringIndexList) {
                rightColStringIndexList1.add(Integer.valueOf(s1));
            }
        }
        if(rightAllignColumnIndex.contains(","))
        {
            //     rightAllignIndex = rightAllignColumnIndex.split(",");
            rightAllignIndexList = Arrays.asList(rightAllignColumnIndex.split(","));
            rtIndexFlag = "true";
        }
        else
        {
            rightIndex = Integer.parseInt(rightAllignColumnIndex);
            rtIndexFlag = "false";
        }
        if(rightAllignIndexList.size()>0){
            for (String s1:rightAllignIndexList) {
                rightAllignIndexList1.add(Integer.valueOf(s1));
            }
        }
        else{
            rightAllignIndexList1.add(Integer.valueOf(rightIndex));
        }
        for(int i = 0, k=1;i<headings.length;i++)
        {
            Row row1 = sheet.createRow(k);
            Cell cell1 = row1.createCell(0);
            cell1.setCellValue(headings[i]);
            k++;
        }
        int m = excelStartRow+1;

            Row row1 = sheet.createRow(excelStartRow);
            List<String> rowList1 = excelValues.get(0);
            for(int j=0;j<rowList1.size();j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(rowList1.get(j));
            }


        for(int i =1,k=excelStartRow+1;i<excelValues.size();i++,k++) {
            Row row = sheet.createRow(k);
            List<String> rowList = excelValues.get(i);
            for(int j=0;j<rowList.size();j++) {
                Cell cell = row.createCell(j);

                  if(rightAllignIndexList1.contains(j)) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        System.out.println("value of j" + rowList.get(j));
                        if(rowList.get(j).equals(""))
                        {
                            cell.setCellValue(rowList.get(j));
                        }
                        else {
                            if(rightColStringIndexList1.contains(j))
                            {
                                cell.setCellValue(rowList.get(j));}
                            else{
                            cell.setCellValue(Double.parseDouble(rowList.get(j)));}
                        }

                    }

                else {
                      System.out.println("value of j**********" + rowList.get(j));
                       cell.setCellValue(rowList.get(j));
                     }
            }
            m++;
        }
        Row row2 = sheet.createRow(m+1);
        Cell cell1 = row2.createCell(0);
        cell1.setCellValue(s[1]);
        FileOutputStream fileOut = new FileOutputStream(filePath);
        wb.write(fileOut);
        String pdffilePath = excelPath+reportFileName+".pdf";
        int contentSz = excelValues.size();
        createPdf(pdffilePath,filePath, pdfReportName,contentSz);

        if(fileFormat.equals("excel"))
        {
            downloadFile(filePath, fileFormat, response);
        }
        downloadFile(pdffilePath, fileFormat, response);
    //    createZip(files, response);
}
    private  void createPdf(String pdfPath,String excelPath, String pdfReportName, int contentSize) throws IOException, DocumentException {

        System.out.println("Inside createpdf");
        System.out.println("Pdf path"+pdfPath);
        float left = 30;
        float right = 30;
        float top = 20;
        float bottom = 100;

    //    float left = 30;
    //    float right = 30;
    //    float top = 80;
     //   float bottom = 0;
        Document document = new Document(PageSize.A4, left, right, top, bottom);
        document.setPageSize(PageSize.A4.rotate());
       // Document document = new Document();
        //  document.left(100);
        PdfWriter pdfWriter = PdfWriter.getInstance(document,new FileOutputStream(pdfPath));
      //  Rectangle rectangle = new Rectangle(20, 20, 500, 820);
        Rectangle rectangle = new Rectangle(20, 10, 540, 890);
      //  Rectangle rectangle = new Rectangle(40, 40, 540, 790);
        pdfWriter.setBoxSize("rectangle", rectangle);
        HeaderAndFooterPdfPageEventHelper headerAndFooter = new HeaderAndFooterPdfPageEventHelper();
        pdfWriter.setPageEvent(headerAndFooter);

        document.open();
        readAndWrite(document,excelPath,pdfReportName, contentSize);
        document.close();
    }

    private  void readAndWrite(Document document, String excelPath, String pdfReportName, int contentSize) throws IOException, DocumentException {
        System.out.println("Inside read write");
        System.out.println("Excel path"+excelPath);
       // String pdfReportName = "monthlyReward";
        org.apache.poi.ss.usermodel.Workbook workbook = new XSSFWorkbook(new FileInputStream(excelPath));
        Paragraph p;
        Sheet sheet = workbook.getSheetAt(0);
        String s[] = pdfReportName.split("&");
      //  getPdfFileProperties(s[0]);
     //   String rightAllignIndex[] = new String[5];
        List<String> rightAllignIndexList = new ArrayList<>();
        List<Integer> rightAllignIndexList1 = new ArrayList<>();
        String rightIndex = "3";
        String rtIndexFlag = "false";
        if(rightAllignColumnIndex.contains(","))
        {
       //     rightAllignIndex = rightAllignColumnIndex.split(",");
            rightAllignIndexList = Arrays.asList(rightAllignColumnIndex.split(","));
            rtIndexFlag = "true";
        }
        else
        {
            rightIndex = rightAllignColumnIndex;
            rtIndexFlag = "false";
        }
        if(rightAllignIndexList.size()>0){
            for (String s1:rightAllignIndexList) {
                rightAllignIndexList1.add(Integer.valueOf(s1));
            }
        }

        List<String> integerIndexList = new ArrayList<>();
        List<Integer> integerIndexList1 = new ArrayList<>();
        String integerIndex = "3";
        if(!integerColumnIndex.equals("")) {
            if (integerColumnIndex.contains(",")) {
                //     rightAllignIndex = rightAllignColumnIndex.split(",");
                integerIndexList = Arrays.asList(integerColumnIndex.split(","));
            } else {
                integerIndex = integerColumnIndex;
                integerIndexList.add(integerColumnIndex);
            }
        }
        if(integerIndexList.size()>0){
            for (String s1:integerIndexList) {
                integerIndexList1.add(Integer.valueOf(s1));
            }
        }
        String pdfFileForm[] = pdfFileFormat.split(":");
        int colNo = Integer.parseInt(pdfFileForm[0]);
        String widths[] = pdfFileForm[1].split(",");
        PdfPTable table = new PdfPTable(colNo);
        float w[] = new float[colNo];
        for(int m =0;m<colNo;m++)
        {
            w[m]=Float.parseFloat(widths[m]);
        }
            table.setTotalWidth(w);
            table.setWidthPercentage(100);
            table.setLockedWidth(false);
            table.setHeaderRows(1);
            PdfPCell pdfCell;

        PdfPTable tableH = new PdfPTable(1);
        tableH.setTotalWidth(new float[]{ 300});
        tableH.setWidthPercentage(100);
        tableH.setLockedWidth(false);

            for(Row row1 : sheet) {
                if (row1.getRowNum() == 0) {
                    System.out.println("row 0" + row1.getCell(0).getStringCellValue());
                    p = new Paragraph(new Phrase(
                            row1.getCell(0).getStringCellValue(),
                            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20.0f)));
                    p.setAlignment(Paragraph.ALIGN_CENTER);
                    pdfCell = new PdfPCell();
                    removeCellBorder(pdfCell);
                  //  pdfCell.setColspan(colNo);
                    pdfCell.addElement(p);
                    // pdfCell.setPaddingTop(20);

                    tableH.addCell(pdfCell);
                }
                for (int i = 1; i <= headings.length; i++) {
                    if (row1.getRowNum() == i) {
                        System.out.println("row 1" + row1.getCell(0).getStringCellValue());
                        p = new Paragraph(new Phrase(
                                row1.getCell(0).getStringCellValue(),
                                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11.0f)));
                        p.setAlignment(Paragraph.ALIGN_LEFT);
                        pdfCell = new PdfPCell();
                        removeCellBorder(pdfCell);
                     //   pdfCell.setColspan(colNo);
                        pdfCell.addElement(p);
                        tableH.addCell(pdfCell);
                    }

                }
            }
        document.add(tableH);

            for(Row row : sheet){

                int tableHeaderIndex = headings.length+1;
                if(row.getRowNum() == tableHeaderIndex){
                    for(org.apache.poi.ss.usermodel.Cell cell : row){
                        for(int k = 0;k<colNo;k++)
                        {
                            if(rtIndexFlag.equals("true")) {
                                if (rightAllignIndexList1.contains(cell.getColumnIndex())) {
                                    p = new Paragraph(new Phrase(
                                            cell.getStringCellValue(),
                                            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10.0f)));
                                    p.setAlignment(Paragraph.ALIGN_RIGHT);
                                    pdfCell = new PdfPCell();
                                    //  pdfCell.setFixedHeight(40);
                                    pdfCell.setFixedHeight(40);
                                    pdfCell.setNoWrap(false);

                                    //  p.setLeading(5);
                                    pdfCell.addElement(p);
                                    pdfCell.setPaddingTop(7);
                                    table.addCell(pdfCell);
                                    break;
                                }
                                }
                            else {
                                if (cell.getColumnIndex() == Integer.parseInt(rightIndex)) {
                                    p = new Paragraph(new Phrase(
                                            cell.getStringCellValue(),
                                            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10.0f)));
                                    p.setAlignment(Paragraph.ALIGN_RIGHT);
                                    pdfCell = new PdfPCell();
                                    pdfCell.setFixedHeight(40);
                                    //  pdfCell.setFixedHeight(40);
                                    pdfCell.setNoWrap(false);
                                    //  p.setLeading(5);
                                    pdfCell.addElement(p);
                                    pdfCell.setPaddingTop(7);
                                    table.addCell(pdfCell);
                                    break;
                                }
                            }
                            if(cell.getColumnIndex() == k){
                                p = new Paragraph(new Phrase(
                                        cell.getStringCellValue(),
                                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10.0f)));
                                pdfCell = new PdfPCell();
                              //  pdfCell.setFixedHeight(10);
                               // pdfCell.setFixedHeight(40);
                                pdfCell.setNoWrap(false);
                              //  p.setLeading(5);
                                pdfCell.addElement(p);
                                pdfCell.setPaddingTop(7);
                                table.addCell(pdfCell);

                            }
                        }
                    }
                }
                int tableBodyStartIndex = tableHeaderIndex+1;
                int contentSize1 = contentSize+tableBodyStartIndex;
                System.out.println("Body index: "+tableBodyStartIndex);
                 if(row.getRowNum() >= tableBodyStartIndex && row.getRowNum()<=contentSize1){

                    for(org.apache.poi.ss.usermodel.Cell cell : row){
                        for(int k = 0;k<colNo;k++)
                        {

                            if(rtIndexFlag.equals("true")) {
                                if (rightAllignIndexList1.contains(cell.getColumnIndex())) {
                                    if(cell.getCellType()==0) {
                                        System.out.println("Cell value "+k+" "+cell.getNumericCellValue());
                                        if(integerIndexList1.contains(cell.getColumnIndex())) {
                                            p = new Paragraph(new Phrase(
                                                    String.valueOf((int) cell.getNumericCellValue()),
                                                    FontFactory.getFont(FontFactory.HELVETICA, 10.0f)));
                                        }
                                        else{
                                            p = new Paragraph(new Phrase(
                                                    String.valueOf(cell.getNumericCellValue()),
                                                    FontFactory.getFont(FontFactory.HELVETICA, 10.0f)));
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Cell value string "+k+" "+cell.getStringCellValue());
                                        p = new Paragraph(new Phrase(
                                                cell.getStringCellValue(),
                                                FontFactory.getFont(FontFactory.HELVETICA, 10.0f)));
                                    }
                                    p.setAlignment(Paragraph.ALIGN_RIGHT);
                                    pdfCell = new PdfPCell();
                                    pdfCell.setFixedHeight(40);
                                    //  pdfCell.setFixedHeight(40);
                                    pdfCell.setNoWrap(false);
                                    //  p.setLeading(5);
                                    pdfCell.addElement(p);
                                    pdfCell.setPaddingTop(7);
                                    table.addCell(pdfCell);
                                    break;
                                }
                            }
                            else {
                                if (cell.getColumnIndex() == Integer.parseInt(rightIndex)) {
                                    p = new Paragraph(new Phrase(
                                            String.valueOf(cell.getNumericCellValue()),
                                            FontFactory.getFont(FontFactory.HELVETICA, 10.0f)));
                                    p.setAlignment(Paragraph.ALIGN_RIGHT);
                                    pdfCell = new PdfPCell();
                                    pdfCell.setFixedHeight(40);
                                    //  pdfCell.setFixedHeight(40);
                                    pdfCell.setNoWrap(false);
                                    //  p.setLeading(5);
                                    pdfCell.addElement(p);
                                    pdfCell.setPaddingTop(7);
                                    table.addCell(pdfCell);
                                    break;
                                }
                            }
                        /*   if(cell.getColumnIndex() == 3 || cell.getColumnIndex() == 2){
                                p = new Paragraph(new Phrase(
                                        cell.getStringCellValue(),
                                        FontFactory.getFont(FontFactory.HELVETICA, 10.0f)));
                                p.setAlignment(Paragraph.ALIGN_RIGHT);
                                pdfCell = new PdfPCell();
                                //  pdfCell.setFixedHeight(40);
                                pdfCell.setNoWrap(false);
                                //  p.setLeading(5);
                                pdfCell.addElement(p);
                                pdfCell.setPaddingTop(7);
                                table.addCell(pdfCell);
                                break;
                            }*/
                            if(cell.getColumnIndex() == k){
                                System.out.println("Cell value***** "+k+" "+cell.getStringCellValue());
                                p = new Paragraph(new Phrase(
                                        cell.getStringCellValue(),
                                        FontFactory.getFont(FontFactory.HELVETICA, 10.0f)));
                                pdfCell = new PdfPCell();
                              //  pdfCell.setFixedHeight(40);
                                pdfCell.setNoWrap(false);
                              //  p.setLeading(5);
                                pdfCell.addElement(p);
                                pdfCell.setPaddingTop(7);
                                table.addCell(pdfCell);
                            }

                        }
                    }
                }
              //  document.newPage();
            }
            document.add(table);
        PdfPTable table1 = new PdfPTable(1);
        table1.setTotalWidth(new float[]{ 400});
        p = new Paragraph(s[1],
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10.0f));
        p.setAlignment(Paragraph.ALIGN_LEFT);
        pdfCell = new PdfPCell();
        pdfCell.setNoWrap(false);
        removeCellBorder(pdfCell);
        pdfCell.addElement(p);
        pdfCell.setPaddingTop(7);
        table1.addCell(pdfCell);
        document.add(table1);
       // document.newPage();
    }
    private PdfPCell removeCellBorder(PdfPCell cell){
        cell.setPaddingLeft(20);
        cell.setUseVariableBorders(true);
        cell.setBorderColorTop(BaseColor.WHITE);
        cell.setBorderColorBottom(BaseColor.WHITE);
        cell.setBorderColorRight(BaseColor.WHITE);
        cell.setBorderColorLeft(BaseColor.WHITE);
        return  cell;
    }
    public static void createZip(ArrayList<String> f, HttpServletResponse resp) {
        resp.setContentType("application/zip");
        resp.setHeader("Content-disposition", "attachment; filename=Data.zip");
        try {

            byte[] buffer = new byte[1024];
            // ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
            ZipOutputStream out = new ZipOutputStream(resp.getOutputStream());
            out.setLevel(Deflater.DEFAULT_COMPRESSION);
            for (int i = 0; i < f.size(); i++) {
                System.out.println(f.get(i));
                FileInputStream in = new FileInputStream(new File(f.get(i)));
                // out.putNextEntry(new ZipEntry(new File(f.get(i)).getPath()));
                out.putNextEntry(new ZipEntry(new File(f.get(i)).toString()));
                int len;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                    //  System.out.println("zip file"+len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    private void getPdfFileProperties(String reportName) {
        Properties properties = new Properties();
        File Configfile = new File(pdfConfigPath+"PdfFormat.properties");
        try {
            FileReader reader = new FileReader(Configfile);
            properties.load(reader);
            pdfFileFormat = properties.getProperty(reportName);
        }
        catch (Exception e){

        }
    }
    private void getConfigFileProperties(String templateFileName) {
        Properties properties = new Properties();
       // System.out.println("Config file path: "+configFilePath+templateFileName+".Properties");
        File Configfile = new File(configFilePath+templateFileName+".Properties");
        try {
            FileInputStream reader = new FileInputStream(Configfile);
            properties.load(reader);
            excelStartRow = Integer.parseInt(properties.getProperty("excelStartRow"));
            rightAllignColumnIndex = properties.getProperty("rightAllignColumnIndex");
            pdfFileFormat = properties.getProperty("pdfTableColumnWidth");
            integerColumnIndex = properties.getProperty("integerColumnIndex");
            rightColString = properties.getProperty("rightColString");
        }
        catch (Exception e){

        }
    }
    public void downloadFile(String pdffilePath, String fileFormat, HttpServletResponse resp) throws IOException {
        File file = new File(pdffilePath);
        ServletOutputStream stream = null;
        BufferedInputStream buf = null;
        try {
            stream = resp.getOutputStream();
            // set response headers
            if(fileFormat.equals("excel"))
            {
                resp.setContentType("applicaion/msexcel");
            }
            else {
                resp.setContentType("application/pdf");
            }
            resp.setDateHeader("Expires", 0);
            resp.addHeader("Content-Disposition", "attachment; filename=" + file.getName());
            resp.setContentLength((int) file.length());
            buf = new BufferedInputStream(new FileInputStream(file));
            int readBytes = 0;
            while ((readBytes = buf.read()) != -1)
                stream.write(readBytes);
        } finally {
            if (stream != null)
                stream.flush();
            stream.close();
            if (buf != null)
                buf.close();
        }

    }
    String copy(String templateName, String reportName)
    {
        java.nio.file.FileSystem system = FileSystems.getDefault();
        Path original = system.getPath(excelPath+"/template/"+templateName+".xlsx");
        Path target = system.getPath(excelPath+"/"+reportName+".xlsx");

        try {
            Files.deleteIfExists(target);
            Files.copy(original, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        return target.toString();
    }
}
