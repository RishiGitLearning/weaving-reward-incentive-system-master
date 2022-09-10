package com.technoride.RewardIncentiveSystem.controller;

import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;
import com.technoride.RewardIncentiveSystem.entity.MonthlyAttendance;
import com.technoride.RewardIncentiveSystem.entity.PtaxMonth;
import com.technoride.RewardIncentiveSystem.repository.FeltProdLoomWeaverRepository;
import com.technoride.RewardIncentiveSystem.repository.MonthlyAttendanceRepository;
import com.technoride.RewardIncentiveSystem.repository.PtaxMonthRepository;
import com.technoride.RewardIncentiveSystem.utility.ExportToExcel;
import com.technoride.RewardIncentiveSystem.utility.TranslateNumberToWords;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class FinalPaymentController {

    @Autowired
    private PtaxMonthRepository ptaxMonthRepository;

    @Autowired
    private MonthlyAttendanceRepository monthlyAttendanceRepository;

    @Autowired
    private FeltProdLoomWeaverRepository feltProdLoomWeaverRepository;

    @Autowired
    private ExportToExcel exportToExcel;

    @Autowired
    private TranslateNumberToWords translateNumberToWords;

    List<String> months = Arrays.asList("January", "February", "March", "April", "May","June","July","August","September","October","November","December");

    String ctryCd = "IN";
    String lang = "en";

    @GetMapping("/finalPayment")
    public String getFinalPayment()
    {
        return "finalPayment";
    }
    @GetMapping("/incentiveSheet")
    public String getIncentiveSheet(Model model)
    {
        List<String> yearList = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for(int y = year; y>=1960; y--)
        {
            yearList.add(String.valueOf(y));
        }
        model.addAttribute("yearList", yearList);
        model.addAttribute("monthList",months);
        return "finalPaymentIncentive";
    }

    List<PtaxMonth> ptaxMonthIncentiveList = new ArrayList<>();
    List<PtaxMonth> ptaxMonthIncentiveRList = new ArrayList<>();
    List<PtaxMonth> ptaxMonthIncentiveVList = new ArrayList<>();
   @RequestMapping(value="/index/processFinalPaymentIncentive", method = RequestMethod.GET, produces = "application/json")
   public @ResponseBody String processFinalPaymentIncentive(@RequestParam(value = "yearSel") String yearSel,
                                               @RequestParam(value = "monthSel") String monthSel)
    {
        ptaxMonthIncentiveList.clear();
        ptaxMonthIncentiveRList.clear();
        ptaxMonthIncentiveVList.clear();
        String message = "";
        int index = months.indexOf(monthSel);
        System.out.println("Index" + index);
        int month = index;
        int pickMonth = month + 1;
        int pickYear = Integer.parseInt(yearSel);
        //  List<String> empList = ptaxMonthRepository.findEmpByMonthYear(pickMonth,pickYear);
      //  ptaxMonthIncentiveList = ptaxMonthRepository.getPtaxByIncentiveFlag(pickMonth,pickYear,true);
        List<String> empNoList = monthlyAttendanceRepository.findEmpListForInc(pickMonth, pickYear, "");
        int i = 1;
        for (String empId:empNoList) {
            List<PtaxMonth> ptaxMonthList = ptaxMonthRepository.findEmpGross(empId,pickMonth,pickYear);
         if(ptaxMonthList.size()>0)
         {
             PtaxMonth pm1 = ptaxMonthList.get(0);
             if(pm1.getPtWvgIncGross()==0 && pm1.getPtWvrRewardGross()==0)
             {

             }
             else {
                 pm1.setId(i);
                 pm1.setPtEmpName(monthlyAttendanceRepository.findEmpName(pm1.getPtEmpCode(), "").get(0));
                 double netAMount = Precision.round(pm1.getPtWvgIncGross() - pm1.getPtWvgIncPtax(), 0);
                 pm1.setNetAmount((int) netAMount);
                 ptaxMonthIncentiveList.add(pm1);
                 i++;
             }
         }
        }
        List<String> empNoListR = monthlyAttendanceRepository.findEmpListForIncByPayCat(pickMonth, pickYear, "","R");

        int j = 1;
        for (String empId:empNoListR) {
            MonthlyAttendance m = monthlyAttendanceRepository.findEmpDataByPayCatg(pickMonth,pickYear,empId,"R");
            double cr = m.getPresentDays()+m.getRokdiDays();
            if(cr>0) {
                List<PtaxMonth> ptaxMonthList = ptaxMonthRepository.findEmpGross(empId, pickMonth, pickYear);
                if (ptaxMonthList.size() > 0) {
                    PtaxMonth pm1 = ptaxMonthList.get(0);
                    if (pm1.getPtWvgIncGross() == 0 && pm1.getPtWvrRewardGross() == 0) {

                    } else {
                        pm1.setId(j);
                        pm1.setPtEmpName(monthlyAttendanceRepository.findEmpName(pm1.getPtEmpCode(), "").get(0));
                        double netAMount = Precision.round(pm1.getPtWvgIncGross() - pm1.getPtWvgIncPtax(), 0);
                        pm1.setNetAmount((int) netAMount);
                        ptaxMonthIncentiveRList.add(pm1);
                        j++;
                    }
                }
            }
        }


       List<String> empNoListV = monthlyAttendanceRepository.findEmpListForIncByPayCat(pickMonth, pickYear, "","V");
        int k = 1;
        for (String empId:empNoListV) {
            List<PtaxMonth> ptaxMonthList = ptaxMonthRepository.findEmpGross(empId,pickMonth,pickYear);
            if(ptaxMonthList.size()>0)
            {
                PtaxMonth pm1 = ptaxMonthList.get(0);
                if(pm1.getPtWvgIncGross()==0 && pm1.getPtWvrRewardGross()==0)
                {

                }
                else {
                    pm1.setId(k);
                    pm1.setPtEmpName(monthlyAttendanceRepository.findEmpName(pm1.getPtEmpCode(), "").get(0));
                    double netAMount = Precision.round(pm1.getPtWvgIncGross() - pm1.getPtWvgIncPtax(), 0);
                    pm1.setNetAmount((int) netAMount);
                    ptaxMonthIncentiveVList.add(pm1);
                    k++;
                }
            }
        }
        System.out.println("R"+empNoListR);
        System.out.println("V"+empNoListV);
        /*for (PtaxMonth pm:ptaxMonthIncentiveList) {
            pm.setId(i);
            pm.setPtEmpName(monthlyAttendanceRepository.findEmpName(pm.getPtEmpCode()).get(0));
            i++;
        }*/
      //  model.addAttribute("finalPaymentIncentiveList", ptaxMonthIncentiveList);
        List<String> yearList = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for(int y = year; y>=1960; y--)
        {
            yearList.add(String.valueOf(y));
        }
        yearList.remove(yearSel);
        yearList.add(0,yearSel);
      //  model.addAttribute("yearList", yearList);
        List<String> months1 = new ArrayList<>();
        months1.add("January");
        months1.add("February");
        months1.add("March");
        months1.add("April");
        months1.add("May");
        months1.add("June");
        months1.add("July");
        months1.add("August");
        months1.add("September");
        months1.add("October");
        months1.add("November");
        months1.add("December");
        months1.remove(monthSel);
        months1.add(0,monthSel);
       // model.addAttribute("monthList",months1);
        String empInc = new Gson().toJson(ptaxMonthIncentiveList);
        String messageAlert = new Gson().toJson(message);
        String bothJson = "[" + empInc + "," + messageAlert + "]";
        return bothJson;
    }
    List<PtaxMonth> ptaxMonthList1 = new ArrayList<>();
    @PostMapping("/exportFinalIncentivePay")
    public String exportIncentivePtaxData(@RequestParam(value = "frmSubmit") String format, HttpServletResponse response, Model model) throws IOException, DocumentException {
        System.out.println("WeaverLoomList"+ptaxMonthIncentiveList);
        String heading = "";
        LocalDate cDate = LocalDate.now();
        String currentDate = cDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String templateName = "FinalIncentive";
        String fileFormat = "pdf";
        if(format.contains("Pdf")||format.contains("Pdf2"))
        {
            fileFormat = "pdf";
        }
        else if(format.contains("Excel")||format.contains("Excel2"))
        {
            fileFormat = "excel";
        }
        if(format.contains("Voucher"))
        {
            ptaxMonthList1 = ptaxMonthIncentiveVList;
        }
        else {
            ptaxMonthList1 = ptaxMonthIncentiveRList;
        }
        double totalIncentive = 0;
        if(ptaxMonthList1.size()>0) {
            int m = ptaxMonthList1.get(0).getPtMonth()-1;
            String month = months.get(m);
            int year = ptaxMonthList1.get(0).getPtYear();
            List<String> colHeaderList = Stream.of("Sr.no.","Employee No", "Employee Name", "Wages", "Ptax Deducted", "Incentive Amount", "Reward Amount","Ptax to be Deducted", "Net Amount in Rs.", "Signature").collect(Collectors.toList());
            List<List<String>> excelValues = new ArrayList<>();
            excelValues.add(colHeaderList);
            double totalIncPtax = 0;
            double salaryPtax = 0;
            double wvgIncGross = 0;
            double wvgRewGross = 0;
            DecimalFormat df = new DecimalFormat("0.00");
            for (PtaxMonth w : ptaxMonthList1) {
                List<String> valueList = new ArrayList<>();
                valueList.add(String.valueOf(w.getId()));
                valueList.add(w.getPtEmpCode());
                valueList.add(w.getPtEmpName());
            //    Double sal = new Double(df.format(w.getPtSalaryGross()));
                String sal = df.format(w.getPtSalaryGross());
                System.out.println("Value of sal"+sal);
                System.out.println("Value of sal*****"+w.getPtSalaryGross());
                valueList.add(sal);
                valueList.add(String.valueOf(w.getPtSalaryPtax()));
                valueList.add(String.valueOf(w.getPtWvgIncGross()));
                valueList.add(String.valueOf(w.getPtWvrRewardGross()));
                valueList.add(String.valueOf(w.getPtWvgIncPtax()));
                double netAMount = Precision.round(w.getPtWvgIncGross()-w.getPtWvgIncPtax(),0);
                valueList.add(String.valueOf((int)netAMount));
                salaryPtax = salaryPtax+w.getPtSalaryPtax();
                wvgIncGross = wvgIncGross+w.getPtWvgIncGross();
                wvgRewGross = wvgRewGross+w.getPtWvrRewardGross();
                totalIncentive = totalIncentive+netAMount;
                totalIncPtax = totalIncPtax+w.getPtWvgIncPtax();
                valueList.add("");
                excelValues.add(valueList);
            }
            List<String> valueList = new ArrayList<>();
            valueList.add("");
            valueList.add("");
            valueList.add("TOTAL");
            valueList.add("");
            valueList.add(String.valueOf(salaryPtax));
            valueList.add(String.valueOf(wvgIncGross));
            valueList.add(String.valueOf(wvgRewGross));
            valueList.add(String.valueOf(totalIncPtax));
            valueList.add(String.valueOf((int)totalIncentive));
            valueList.add("");
            excelValues.add(valueList);
            String result = translateNumberToWords.translate(ctryCd, lang, String.valueOf(totalIncentive));
            System.out.println("Input: " + totalIncentive + " result: " + result);
            heading = "PAID FOR              : INCENTIVE                                                                                                                                                       Date : "+currentDate+"&"+"DEPARTMENT     : FELT WEAVING                                                                                                                                                Month : "+month+"-"+year;
            String pdfReportName = "incentiveFinalPayment&TOTAL INCENTIVE: Rupees "+result+" only";
            exportToExcel.doExcel(templateName, fileFormat, excelValues,pdfReportName, heading, "Final Incentive_"+month+"_"+year, response);
        }
        else{
            List<String> yearList = new ArrayList<>();
            int year = LocalDate.now().getYear();
            for(int y = year; y>=1960; y--)
            {
                yearList.add(String.valueOf(y));
            }
            model.addAttribute("yearList", yearList);
            model.addAttribute("monthList",months);
        }
        return "finalPaymentIncentive";
    }

    @GetMapping("/rewardSheet")
    public String getRewardSheet(Model model)
    {
        List<String> yearList = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for(int y = year; y>=1960; y--)
        {
            yearList.add(String.valueOf(y));
        }
        model.addAttribute("yearList", yearList);
        model.addAttribute("monthList",months);
        return "finalPaymentReward";
    }
    List<PtaxMonth> ptaxMonthRewardList = new ArrayList<>();

    @RequestMapping(value="/index/processFinalPaymentReward", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processFinalPaymentReward(@RequestParam(value = "yearSel") String yearSel,
                                            @RequestParam(value = "monthSel") String monthSel)
    {
        ptaxMonthRewardList.clear();
        String message = "";
        int index = months.indexOf(monthSel);
        System.out.println("Index" + index);
        int month = index;
        int pickMonth = month + 1;
        int pickYear = Integer.parseInt(yearSel);
        //  List<String> empList = ptaxMonthRepository.findEmpByMonthYear(pickMonth,pickYear);
        List<String> empNoList = feltProdLoomWeaverRepository.findEmpNoByMonth(pickMonth, pickYear, "");
        int i = 1;
        System.out.println("EMPLIST"+empNoList);
        System.out.println("Emp size: "+empNoList.size());
        double totalNetAmount = 0;
        for (String empId:empNoList) {
            System.out.println("EmpId: "+empId);
            List<PtaxMonth> ptaxMonthList = ptaxMonthRepository.findEmpGross(empId,pickMonth,pickYear);
            System.out.println("Ptax month list: "+ptaxMonthList);
            System.out.println("Ptax month list size: "+ptaxMonthList.size());
            if(ptaxMonthList.size()>0)
            {
                PtaxMonth pm1 = ptaxMonthList.get(0);
                pm1.setId(i);
                try {
                    pm1.setPtEmpName(monthlyAttendanceRepository.findEmpName(pm1.getPtEmpCode(), "").get(0));
                }
                catch(Exception e){
                    e.printStackTrace();
                    System.out.println("Data not found in mothly attendance table for "+empId);
                }

                double netAmount = Precision.round(pm1.getPtWvrRewardGross()-pm1.getPtWvrRewardPtax(),0);
                if(netAmount>0) {
                    pm1.setNetAmount((int) netAmount);
                    ptaxMonthRewardList.add(pm1);
                    i++;
                }
                totalNetAmount = totalNetAmount+netAmount;
            }
        }
       // PtaxMonth ptaxMonth = new PtaxMonth();
       // ptaxMonth.setPtEmpName("Total");
      //  ptaxMonth.setNetAmount((int)totalNetAmount);
     //   ptaxMonthRewardList.add(ptaxMonth);
      /*  ptaxMonthRewardList = ptaxMonthRepository.getPtax(pickMonth,pickYear);

        for (PtaxMonth pm:ptaxMonthRewardList) {
            pm.setId(i);
            pm.setPtEmpName(monthlyAttendanceRepository.findEmpName(pm.getPtEmpCode()).get(0));
            i++;
        }*/
       // model.addAttribute("finalPaymentRewardList", ptaxMonthRewardList);
        List<String> yearList = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for(int y = year; y>=1960; y--)
        {
            yearList.add(String.valueOf(y));
        }
        yearList.remove(yearSel);
        yearList.add(0,yearSel);
      //  model.addAttribute("yearList", yearList);
        List<String> months1 = new ArrayList<>();
        months1.add("January");
        months1.add("February");
        months1.add("March");
        months1.add("April");
        months1.add("May");
        months1.add("June");
        months1.add("July");
        months1.add("August");
        months1.add("September");
        months1.add("October");
        months1.add("November");
        months1.add("December");
        months1.remove(monthSel);
        months1.add(0,monthSel);
     //   model.addAttribute("monthList",months1);
        String empRew = new Gson().toJson(ptaxMonthRewardList);
        String messageAlert = new Gson().toJson(message);
        String bothJson = "[" + empRew + "," + messageAlert + "]";
        return bothJson;
    }
    @PostMapping("/exportFinalRewardPay")
    public String exportRewardPtaxData(@RequestParam(value = "frmSubmit") String format, HttpServletResponse response, Model model) throws IOException, DocumentException {
        System.out.println("WeaverLoomList"+ptaxMonthRewardList);
        String heading = "";
        LocalDate cDate = LocalDate.now();
        String currentDate = cDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String templateName = "FinalReward";
        String fileFormat = "pdf";
        if(format.contains("Pdf"))
        {
            fileFormat = "pdf";
        }
        else if(format.contains("Excel"))
        {
            fileFormat = "excel";
        }
        double totalReward = 0;
        if(ptaxMonthRewardList.size()>0) {
            int m = ptaxMonthRewardList.get(0).getPtMonth()-1;
            String month = months.get(m);
            int year = ptaxMonthRewardList.get(0).getPtYear();
            List<String> colHeaderList = Stream.of("Sr.No.","Employee No", "Employee Name", "Wages", "Ptax Deducted", "Reward", "Ptax to be Deducted","Net Amount in Rs.", "Signature").collect(Collectors.toList());
            List<List<String>> excelValues = new ArrayList<>();
            double totalSalPtax = 0;
            double totalRewGross = 0;
            double totalRewPtax = 0;
            DecimalFormat df = new DecimalFormat("0.00");
            excelValues.add(colHeaderList);
            for (PtaxMonth w : ptaxMonthRewardList) {
                List<String> valueList = new ArrayList<>();
                valueList.add(String.valueOf(w.getId()));
                valueList.add(w.getPtEmpCode());
                valueList.add(w.getPtEmpName());
                String sal = df.format(w.getPtSalaryGross());
                valueList.add(sal);
                valueList.add(String.valueOf(w.getPtSalaryPtax()));
                valueList.add(String.valueOf(w.getPtWvrRewardGross()));
                valueList.add(String.valueOf(w.getPtWvrRewardPtax()));
                double netAmount = Precision.round(w.getPtWvrRewardGross()-w.getPtWvrRewardPtax(),0);
               if(netAmount>0) {
                   valueList.add(String.valueOf((int) netAmount));
                   valueList.add("");
                   excelValues.add(valueList);
               }
                totalReward = totalReward+netAmount;
               totalSalPtax = totalSalPtax+w.getPtSalaryPtax();
               totalRewGross = totalRewGross+w.getPtWvrRewardGross();
               totalRewPtax = totalRewPtax+w.getPtWvrRewardPtax();
            }
            List<String> valueList = new ArrayList<>();
            valueList.add("");
            valueList.add("");
            valueList.add("TOTAL");
            valueList.add("");
            valueList.add(String.valueOf(totalSalPtax));
            valueList.add(String.valueOf(totalRewGross));
            valueList.add(String.valueOf(totalRewPtax));
            valueList.add(String.valueOf(totalReward));
            valueList.add("");
            excelValues.add(valueList);
            String result = translateNumberToWords.translate(ctryCd, lang, String.valueOf(totalReward));
            heading = "PAID FOR              : REWARD                                                                                                                                                  Date  : "+currentDate+"&"+"DEPARTMENT     : FELT WEAVING                                                                                                                                         Month : "+month+"-"+year;
            String pdfReportName = "rewardFinalPayment&TOTAL REWARDS: Rupees "+result+" only";
            exportToExcel.doExcel(templateName, fileFormat, excelValues, pdfReportName, heading, "Final Reward_"+month+"_"+year, response);
        }
        else{
            List<String> yearList = new ArrayList<>();
            int year = LocalDate.now().getYear();
            for(int y = year; y>=1960; y--)
            {
                yearList.add(String.valueOf(y));
            }
            model.addAttribute("yearList", yearList);
            model.addAttribute("monthList",months);
        }
        return "finalPaymentReward";
    }
    @GetMapping("/incentiveFinalPayment")
    public String getIncentiveFinalPayment(Model model)
    {
        List<String> yearList = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for(int y = year; y>=1960; y--)
        {
            yearList.add(String.valueOf(y));
        }
        model.addAttribute("yearList", yearList);
        model.addAttribute("monthList",months);
        return "finalPaymentIncentiveSummary";
    }

    @GetMapping("/rewardFinalPayment")
    public String getRewardFinalPayment(Model model)
    {
        List<String> yearList = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for(int y = year; y>=1960; y--)
        {
            yearList.add(String.valueOf(y));
        }
        model.addAttribute("yearList", yearList);
        model.addAttribute("monthList",months);
        return "finalPaymentRewardSummary";
    }
    List<PtaxMonth> ptaxMonthList = new ArrayList<>();
    @PostMapping("/exportFinalIncentiveSummaryPay")
    public String exportFinalIncentiveSummaryPay(@RequestParam(value = "frmSubmit") String format, HttpServletResponse response, Model model) throws IOException, DocumentException {
        System.out.println("WeaverLoomList"+ptaxMonthIncentiveList);
        String heading = "";
        LocalDate cDate = LocalDate.now();
        String currentDate = cDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String templateName = "FinalIncentiveSummary";
        String fileFormat = "pdf";
        if(format.contains("Pdf") || format.contains("Pdf2"))
        {
            fileFormat = "pdf";
        }
        else if(format.contains("Excel") || format.contains("Excel2"))
        {
            fileFormat = "excel";
        }

        if(format.contains("Voucher"))
        {
            ptaxMonthList = ptaxMonthIncentiveVList;
        }
        else {
            ptaxMonthList = ptaxMonthIncentiveRList;
        }
        double totalIncentive = 0;
        if(ptaxMonthList.size()>0) {
            int m = ptaxMonthList.get(0).getPtMonth()-1;
            String month = months.get(m);
            int year = ptaxMonthList.get(0).getPtYear();
            List<String> colHeaderList = Stream.of("Sr.no.","Employee No", "Employee Name", "Net Amount in Rs.", "Signature").collect(Collectors.toList());
            List<List<String>> excelValues = new ArrayList<>();
            excelValues.add(colHeaderList);
            for (PtaxMonth w : ptaxMonthList) {
                List<String> valueList = new ArrayList<>();
                valueList.add(String.valueOf(w.getId()));
                valueList.add(w.getPtEmpCode());
                valueList.add(w.getPtEmpName());
                double netAmount= Precision.round(w.getPtWvgIncGross()-w.getPtWvgIncPtax(),0);
                valueList.add(String.valueOf((int)netAmount));
                valueList.add("");
                excelValues.add(valueList);
                totalIncentive = totalIncentive+netAmount;
           }
            List<String> valueList = new ArrayList<>();
            valueList.add("");
            valueList.add("");
            valueList.add("TOTAL");
            valueList.add(String.valueOf(totalIncentive));
            valueList.add("");
            excelValues.add(valueList);
            String result = translateNumberToWords.translate(ctryCd, lang, String.valueOf(totalIncentive));
            heading = "PAID FOR              : INCENTIVE                                                              ADVANCED                                                              Date   :"+currentDate+"&"+"DEPARTMENT     : FELT WEAVING                                                                                                                                         Month : "+month+"-"+year;
            String pdfReportName = "incentiveSummaryFinalPayment&TOTAL INCENTIVE: Rupees "+result+" only";
            exportToExcel.doExcel(templateName, fileFormat, excelValues, pdfReportName, heading, "Final Incentive_"+month+"_"+year, response);
        }
        else{
            List<String> yearList = new ArrayList<>();
            int year = LocalDate.now().getYear();
            for(int y = year; y>=1960; y--)
            {
                yearList.add(String.valueOf(y));
            }
            model.addAttribute("yearList", yearList);
            model.addAttribute("monthList",months);
        }
        return "finalPaymentIncentiveSummary";
    }
    @PostMapping("/exportFinalRewardSummaryPay")
    public String exportFinalRewardSummaryPay(@RequestParam(value = "frmSubmit") String format, HttpServletResponse response, Model model) throws IOException, DocumentException {
        System.out.println("WeaverLoomList"+ptaxMonthRewardList);
        String heading = "";
        LocalDate cDate = LocalDate.now();
        String currentDate = cDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String templateName = "FinalRewardSummary";
        String fileFormat = "pdf";
        if(format.contains("Pdf"))
        {
            fileFormat = "pdf";
        }
        else if(format.contains("Excel"))
        {
            fileFormat = "excel";
        }
        double totalReward = 0;
        if(ptaxMonthRewardList.size()>0) {
            int m = ptaxMonthRewardList.get(0).getPtMonth()-1;
            String month = months.get(m);
            int year = ptaxMonthRewardList.get(0).getPtYear();
            List<String> colHeaderList = Stream.of("Sr.No.","Employee No", "Employee Name", "Net Amount in Rs.","Signature").collect(Collectors.toList());
            List<List<String>> excelValues = new ArrayList<>();
            excelValues.add(colHeaderList);
            for (PtaxMonth w : ptaxMonthRewardList) {
                List<String> valueList = new ArrayList<>();
                valueList.add(String.valueOf(w.getId()));
                valueList.add(w.getPtEmpCode());
                valueList.add(w.getPtEmpName());
                double netAmount = Precision.round(w.getPtWvrRewardGross()-w.getPtWvrRewardPtax(),0);
                if(netAmount>0) {
                    valueList.add(String.valueOf((int) netAmount));
                    valueList.add("");
                    excelValues.add(valueList);
                }
                totalReward = totalReward+netAmount;
            }
            List<String> valueList = new ArrayList<>();
            valueList.add("");
            valueList.add("");
            valueList.add("TOTAL");
            valueList.add(String.valueOf(totalReward));
            valueList.add("");
            excelValues.add(valueList);
            String result = translateNumberToWords.translate(ctryCd, lang, String.valueOf(totalReward));
            heading = "PAID FOR              : REWARDS                                                              ADVANCED                                                                Date   :"+currentDate+"&"+"DEPARTMENT     : FELT WEAVING                                                                                                                                           Month : "+month+"-"+year;
            String pdfReportName = "rewardSummaryFinalPayment&TOTAL REWARDS: Rupees "+result+" only";
            exportToExcel.doExcel(templateName, fileFormat, excelValues, pdfReportName, heading, "Final Reward_"+month+"_"+year, response);
        }
        else{
            List<String> yearList = new ArrayList<>();
            int year = LocalDate.now().getYear();
            for(int y = year; y>=1960; y--)
            {
                yearList.add(String.valueOf(y));
            }
            model.addAttribute("yearList", yearList);
            model.addAttribute("monthList",months);
        }
        return "finalPaymentReward";
    }
 /*   @GetMapping("/finalpayment")
    public String getRewardPtax(Model model)
    {
        List<String> yearList = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for(int y = year; y>=1960; y--)
        {
            yearList.add(String.valueOf(y));
        }
        model.addAttribute("yearList", yearList);
        model.addAttribute("monthList",months);
        return "finalPayment";
    }

    List<PtaxMonth> ptaxMonthList = new ArrayList<>();

    @GetMapping("/processFinalPayment")
    public String processRewardPtax(@RequestParam(value = "yearSel") String yearSel,
                                    @RequestParam(value = "monthSel") String monthSel,
                                    Model model)
    {
        int index = months.indexOf(monthSel);
        System.out.println("Index" + index);
        int month = index;
        int pickMonth = month + 1;
        int pickYear = Integer.parseInt(yearSel);
        List<String> empList = ptaxMonthRepository.findEmpByMonthYear(pickMonth,pickYear);
        for (String empId: empList) {
            updateGross(empId,pickMonth,pickYear);
        }
        ptaxMonthList = ptaxMonthRepository.getPtax(pickMonth,pickYear);
        model.addAttribute("finalPaymentList", ptaxMonthList);
        return "finalPayment";
    }
    @PostMapping("/exportFinalPaymentData")
    public String exportIncentivePtaxData(HttpServletResponse response) throws IOException {
        System.out.println("WeaverLoomList"+ptaxMonthList);
        List<String> colHeaderList = Stream.of("EmpNo", "Salary", "Ptax Salary", "Reward", "Ptax Reward", "Incentive Amount", "Ptax Incentive", "Total Gross", "Ptax Gross").collect(Collectors.toList());
        List<List<String>> excelValues = new ArrayList<>();
        excelValues.add(colHeaderList);
        for (PtaxMonth w: ptaxMonthList) {
            List<String> valueList = new ArrayList<>();
            valueList.add(w.getPtEmpCode());
            valueList.add(String.valueOf(w.getPtSalaryGross()));
            valueList.add(String.valueOf(w.getPtSalaryPtax()));
            valueList.add(String.valueOf(w.getPtWvrRewardGross()));
            valueList.add(String.valueOf(w.getPtWvrRewardPtax()));
            valueList.add(String.valueOf(w.getPtWvgIncGross()));
            valueList.add(String.valueOf(w.getPtWvgIncPtax()));
            valueList.add(String.valueOf(w.getPtTotalGross()));
            valueList.add(String.valueOf(w.getPtTotalPtax()));
            excelValues.add(valueList);
        }
        exportToExcel.doExcel(excelValues,response);
        return "monthlyIncentive";
    }

    private void updateGross(String empId, int pickMonth, int pickYear)
    {
        List<PtaxMonth> ptaxMonth = ptaxMonthRepository.findEmpGross(empId, pickMonth, pickYear);
        if(!ptaxMonth.isEmpty()) {
            double salary = ptaxMonth.get(0).getPtSalaryGross();
            double ptax = ptaxMonth.get(0).getPtSalaryPtax();
            double reward = ptaxMonth.get(0).getPtWvrRewardGross();
            double rewardPtax = ptaxMonth.get(0).getPtWvrRewardPtax();
            double incentiveGross = ptaxMonth.get(0).getPtWvgIncGross();
            double incentivePtax = ptaxMonth.get(0).getPtWvgIncPtax();
            double gross = salary+reward+incentiveGross;
            double ptaxGross = ptax+rewardPtax+incentivePtax;
            int i = ptaxMonthRepository.updateGross(gross, ptaxGross, empId, pickMonth, pickYear);
            System.out.println("Gross updated"+i);
        }
    }*/
      /* @GetMapping("/processFinalPaymentIncentive")
    public String processFinalPaymentIncentive(@RequestParam(value = "yearSel") String yearSel,
                                               @RequestParam(value = "monthSel") String monthSel,
                                               Model model)
    {
        int index = months.indexOf(monthSel);
        System.out.println("Index" + index);
        int month = index;
        int pickMonth = month + 1;
        int pickYear = Integer.parseInt(yearSel);
      //  List<String> empList = ptaxMonthRepository.findEmpByMonthYear(pickMonth,pickYear);
        ptaxMonthIncentiveList = ptaxMonthRepository.getPtax(pickMonth,pickYear);
        int i = 1;
        for (PtaxMonth pm:ptaxMonthIncentiveList) {
            pm.setId(i);
            pm.setPtEmpName(monthlyAttendanceRepository.findEmpName(pm.getPtEmpCode()).get(0));
            i++;
        }
        model.addAttribute("finalPaymentIncentiveList", ptaxMonthIncentiveList);
        List<String> yearList = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for(int y = year; y>=1960; y--)
        {
            yearList.add(String.valueOf(y));
        }
        yearList.remove(yearSel);
        yearList.add(0,yearSel);
        model.addAttribute("yearList", yearList);
        List<String> months1 = new ArrayList<>();
        months1.add("January");
        months1.add("February");
        months1.add("March");
        months1.add("April");
        months1.add("May");
        months1.add("June");
        months1.add("July");
        months1.add("August");
        months1.add("September");
        months1.add("October");
        months1.add("November");
        months1.add("December");
        months1.remove(monthSel);
        months1.add(0,monthSel);
        model.addAttribute("monthList",months1);
        return "finalPaymentIncentive";
    }*/
     /*  @GetMapping("/processFinalPaymentReward")
    public String processFinalPaymentReward(@RequestParam(value = "yearSel") String yearSel,
                                            @RequestParam(value = "monthSel") String monthSel,
                                            Model model)
    {
        int index = months.indexOf(monthSel);
        System.out.println("Index" + index);
        int month = index;
        int pickMonth = month + 1;
        int pickYear = Integer.parseInt(yearSel);
        //  List<String> empList = ptaxMonthRepository.findEmpByMonthYear(pickMonth,pickYear);
        ptaxMonthRewardList = ptaxMonthRepository.getPtax(pickMonth,pickYear);
        int i = 1;
        for (PtaxMonth pm:ptaxMonthRewardList) {
            pm.setId(i);
            pm.setPtEmpName(monthlyAttendanceRepository.findEmpName(pm.getPtEmpCode()).get(0));
            i++;
        }
        model.addAttribute("finalPaymentRewardList", ptaxMonthRewardList);
        List<String> yearList = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for(int y = year; y>=1960; y--)
        {
            yearList.add(String.valueOf(y));
        }
        yearList.remove(yearSel);
        yearList.add(0,yearSel);
        model.addAttribute("yearList", yearList);
        List<String> months1 = new ArrayList<>();
        months1.add("January");
        months1.add("February");
        months1.add("March");
        months1.add("April");
        months1.add("May");
        months1.add("June");
        months1.add("July");
        months1.add("August");
        months1.add("September");
        months1.add("October");
        months1.add("November");
        months1.add("December");
        months1.remove(monthSel);
        months1.add(0,monthSel);
        model.addAttribute("monthList",months1);
        return "finalPaymentReward";
    }*/

}
