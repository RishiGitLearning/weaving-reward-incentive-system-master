package com.technoride.RewardIncentiveSystem.utility;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.technoride.RewardIncentiveSystem.model.PdfHeadTitle;

public class HeaderAndFooterPdfPageEventHelper extends PdfPageEventHelper {
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
     //   super.onStartPage(writer, document);
        System.out.println("onStartPage() method > Writing header in file");
       // String h = PdfHeadTitle.getInstance().getMsg();
    /*    Rectangle rect = writer.getBoxSize("rectangle");
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase("SHRI DINESH MILLS LTD, BARODA.", FontFactory.getFont(FontFactory.HELVETICA,
                       16.0f)), (rect.getRight() / 2)+20, rect.getTop(), 0);

       ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase("PAID FOR           : REWARDS           Date      : Jan", FontFactory.getFont(FontFactory.HELVETICA,
                        10.0f)), (rect.getRight() / 2)+10, rect.getTop()-15, 0);
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase("DEPARTMENT            : FELT WEAVING           Month  : Jan", FontFactory.getFont(FontFactory.HELVETICA,
                        10.0f)), (rect.getRight() / 2)+10, rect.getTop()-30, 0);*/

    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
    //    super.onEndPage(writer, document);
        System.out.println("onEndPage() method > Writing footer in file");
        Rectangle rect = writer.getBoxSize("rectangle");
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase(String.format("Page %d", writer.getPageNumber()), FontFactory.getFont(FontFactory.HELVETICA, 12.0f)),
               (rect.getRight() / 2)+150, rect.getBottom(), 0);
    }
}
