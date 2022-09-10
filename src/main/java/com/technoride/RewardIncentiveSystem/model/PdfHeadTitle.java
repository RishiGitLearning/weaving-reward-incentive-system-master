package com.technoride.RewardIncentiveSystem.model;

public class PdfHeadTitle {
    public String msg;
    private static PdfHeadTitle pdfHeadTitle = null;

    private PdfHeadTitle() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static PdfHeadTitle getInstance()
    {
        if (pdfHeadTitle == null)
            pdfHeadTitle = new PdfHeadTitle();

        return pdfHeadTitle;
    }
}
