package com.technoride.RewardIncentiveSystem.controller;

import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;
import com.technoride.RewardIncentiveSystem.entity.*;
import com.technoride.RewardIncentiveSystem.model.LoggedUser;
import com.technoride.RewardIncentiveSystem.model.WeaverReward;
import com.technoride.RewardIncentiveSystem.repository.*;
import com.technoride.RewardIncentiveSystem.utility.ExportToExcel;
import com.technoride.RewardIncentiveSystem.utility.RewardSlab;
import com.technoride.RewardIncentiveSystem.utility.TranslateNumberToWords;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class RewardController {

    @Autowired
    private DesignationwiseRateRepository designationwiseRateRepository;
    @Autowired
    private FeltProdLoomWeaverRepository feltProdLoomWeaverRepository;
    @Autowired
    private FeltWeaverRewardRepository feltWeaverRewardRepository;
    @Autowired
    private WeaverProductionTargetsRepository weaverProductionTargetsRepository;
    @Autowired
    private WeaverRewardDateLoomShiftRepository rewardDateLoomShiftRepository;
    @Autowired
    private PtaxMonthRepository ptaxMonthRepository;
    @Autowired
    private RewardLockRepository rewardLockRepository;
    @Autowired
    private CompanyUserRepository companyUserRepository;
    @Autowired
    private ExportToExcel exportToExcel;
    @Autowired
    private RewardSlab rewardSlab;
    @Autowired
    private TranslateNumberToWords translateNumberToWords;

    List<String> months = Arrays.asList("January", "February", "March", "April", "May","June","July","August","September","October","November","December");
    List<String> months1 = new ArrayList<>();

    String ctryCd = "IN";
    String lang = "en";
    Map<String, List<List<Integer>>> weaverProdTargetMapAB = new HashMap<>();
    Map<String, List<List<Integer>>> weaverProdTargetMapC = new HashMap<>();
    List<String> yearList = new ArrayList<>();
    @GetMapping("/rewards")
    public String getRewards(Model model)
    {
        int year = LocalDate.now().getYear();
        for(int y = year; y>=2021; y--)
        {
            yearList.add(String.valueOf(y));
        }
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
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        return "rewards";
    }
    List<FeltWeaverReward> feltWeaverRewardList1 = new ArrayList<>();

    @GetMapping("/monthlyRewards")
    public String getDailyRewards(Model model)
    {
        List<WeaverProductionTargets> weaverProductionTargets = weaverProductionTargetsRepository.findWeaverProdDetails();
        List<Map<String, List<List<Integer>>>> weaverProdTargetList = rewardSlab.FindABRewardSlab(weaverProductionTargets);
        weaverProdTargetMapAB = weaverProdTargetList.get(0);
        weaverProdTargetMapC = weaverProdTargetList.get(1);
        model.addAttribute("yearList", yearList);
        model.addAttribute("monthList",months);
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        return "monthlyRewards";
    }

   // List<WeaverReward> weaverRewardList = new ArrayList<>();
    List<FeltWeaverReward> feltWeaverRewardList = new ArrayList<>();
    String yearSelected = "2021";
    String monthSelected = "January";


    @GetMapping("/rewardHierarchyBasedForm")
    public String getHierarchyForm(Model model)
    {
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
       return "rewardHierarchyBasedForm";
    }

    List<WeaverRewardDateLoomShiftWise> weaverRewardDateLoomShiftWiseList = new ArrayList<>();
    LocalDate sd = LocalDate.now();
    LocalDate ed = LocalDate.now();
    @RequestMapping(value="/index/processRewardHierarchyBasedForm", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processHierarchyForm(@RequestParam(value = "startdate") String startdate,
                                       @RequestParam(value = "enddate") String enddate) throws ParseException {
        weaverRewardDateLoomShiftWiseList.clear();
        double totalReward = 0;
        String message = "";
        System.out.println("Form input"+startdate+"::"+enddate);
        if(startdate.equals("")||enddate.equals(""))
        {
            message = "Please select date";
          //  model.addAttribute("message","Please select date");
          //  model.addAttribute("alertClass", "alert-danger");
        }
        else {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
            sd = new java.sql.Date(startDate.getTime()).toLocalDate();
            ed = new java.sql.Date(endDate.getTime()).toLocalDate();
            List<String> empList = rewardDateLoomShiftRepository.findEmpList(sd, ed);
            for (String empNo: empList) {
                List<WeaverRewardDateLoomShiftWise> weaverRewardDateLoomShiftWiseList1 = rewardDateLoomShiftRepository.findWithCriteria(sd, ed, empNo);
                for (WeaverRewardDateLoomShiftWise w:weaverRewardDateLoomShiftWiseList1) {
                    weaverRewardDateLoomShiftWiseList.add(w);
                }

            }
         //   weaverRewardDateLoomShiftWiseList = rewardDateLoomShiftRepository.findWithCriteria(sd, ed);

            int i = 1;
            for (WeaverRewardDateLoomShiftWise w:weaverRewardDateLoomShiftWiseList) {
                totalReward = totalReward+w.getRewardAmount();
                LocalDate d = w.getRewardDate();
                String formattedDate = d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                w.setDateInString(formattedDate);
                w.setSrNo(i);
                i++;
            }
            if(weaverRewardDateLoomShiftWiseList.size()==0){
                message = "No Result Found";
            }

         //   model.addAttribute("startdate", startdate);
          //  model.addAttribute("enddate", enddate);
          //  model.addAttribute("totalReward",totalReward);
           // model.addAttribute("weaverList", weaverRewardDateLoomShiftWiseList);
        }
        String weaverRewDateWise = new Gson().toJson(weaverRewardDateLoomShiftWiseList);
        String totalRew = new Gson().toJson(totalReward);
        String messageAlert = new Gson().toJson(message);
        String bothJson = "[" + weaverRewDateWise + "," + totalRew + "," + messageAlert + "]";
        return bothJson;
    }
    @GetMapping("/employeeWiseForm")
    public String getEmployeeWiseForm(Model model)
    {
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
       // List<String> empList = feltProdLoomWeaverRepository.findEmpId("");
       // System.out.println("emplist"+empList);
        List<FeltProdLoomWeaver> feltProdLoomWeavers = feltProdLoomWeaverRepository.findEmpList();
        List<String> empList = new ArrayList<>();
        for (FeltProdLoomWeaver f:feltProdLoomWeavers) {
            empList.add(f.getEmpNo()+"-"+f.getEmpName());
        }
        model.addAttribute("empList",empList);
        return "rewardEmployeeWiseForm";
    }

    List<WeaverRewardDateLoomShiftWise> weaverRewardListEmpWise = new ArrayList<>();

    @RequestMapping(value="/index/processEmployeeWiseForm", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processEmployeeWiseForm(@RequestParam(value = "empNo") String empNo,
                                          @RequestParam(value = "startdate") String startdate,
                                          @RequestParam(value = "enddate") String enddate) throws ParseException {
        System.out.println(empNo+startdate+enddate);
        String message = "";
        double totalReward = 0;
        if(empNo.equals("") || startdate.equals("")||enddate.equals(""))
        {
            // List<String> empList = feltProdLoomWeaverRepository.findEmpId("");
            List<FeltProdLoomWeaver> feltProdLoomWeavers = feltProdLoomWeaverRepository.findEmpList();
            List<String> empList = new ArrayList<>();
            for (FeltProdLoomWeaver f:feltProdLoomWeavers) {
                empList.add(f.getEmpNo()+"-"+f.getEmpName());
            }
            message = "Please Fill All Details";
           // model.addAttribute("empList",empList);
          //  model.addAttribute("message","Please select Employee Id/Date");
          //  model.addAttribute("alertClass", "alert-danger");
        }
        else
        {
            String s[] = empNo.split("-");
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
            LocalDate sd = new java.sql.Date(startDate.getTime()).toLocalDate();
            LocalDate ed = new java.sql.Date(endDate.getTime()).toLocalDate();
            weaverRewardListEmpWise = rewardDateLoomShiftRepository.findByEmpNo(s[0], sd, ed);
            int i = 1;
            for (WeaverRewardDateLoomShiftWise w:weaverRewardListEmpWise) {
                totalReward = totalReward+w.getRewardAmount();
                LocalDate d = w.getRewardDate();
                String formattedDate = d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                w.setDateInString(formattedDate);
                w.setSrNo(i);
                i++;
            }
            if(weaverRewardListEmpWise.size()==0){
                message = "No Result Found ";
            }
          //  model.addAttribute("empNo", empNo);
            //   List<String> empList = feltProdLoomWeaverRepository.findEmpId("");
            List<FeltProdLoomWeaver> feltProdLoomWeavers = feltProdLoomWeaverRepository.findEmpList();
            List<String> empList = new ArrayList<>();
            for (FeltProdLoomWeaver f:feltProdLoomWeavers) {
                empList.add(f.getEmpNo()+"-"+f.getEmpName());
            }
            empList.remove(empNo);
            empList.add(0, empNo);
          //  model.addAttribute("empList", empList);
         //   model.addAttribute("startdate", startdate);
          //  model.addAttribute("enddate", enddate);
          //  model.addAttribute("totalReward", totalReward);
         //   model.addAttribute("weaverRewardList", weaverRewardListEmpWise);
        }
        String weaverRewEmpWise = new Gson().toJson(weaverRewardListEmpWise);
        String totalRew = new Gson().toJson(totalReward);
        String messageAlert = new Gson().toJson(message);
        String bothJson = "[" + weaverRewEmpWise + "," + totalRew + "," + messageAlert + "]";
        return bothJson;
    }


    @GetMapping("/loomWiseForm")
    public String getLoomWiseForm(Model model)
    {
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        List<String> loomList = weaverProductionTargetsRepository.findLoomId();
        model.addAttribute("loomList", loomList);
        return "rewardLoomWiseForm";
    }

    List<WeaverRewardDateLoomShiftWise> weaverRewardList1 = new ArrayList<>();



    @RequestMapping(value="/index/processLoomWiseForm", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processLoomWiseForm(@RequestParam(value = "loomSel") String loomSel,
                                      @RequestParam(value = "startdate") String startdate,
                                      @RequestParam(value = "enddate") String enddate) throws ParseException
    {
        double totalReward = 0;
        String message = "";
        System.out.println("Param"+loomSel+startdate+enddate);
        //  System.out.println("Excel"+excel);
        if(loomSel.equals("") || startdate.equals("")||enddate.equals(""))
        {
            List<String> loomList = weaverProductionTargetsRepository.findLoomId();
            message = "Please Fill All Details";
         //   model.addAttribute("loomList", loomList);
          //  model.addAttribute("message","Please select Loom No/Date");
           // model.addAttribute("alertClass", "alert-danger");
        }
        else {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
            LocalDate sd = new java.sql.Date(startDate.getTime()).toLocalDate();
            LocalDate ed = new java.sql.Date(endDate.getTime()).toLocalDate();
            weaverRewardList1 = rewardDateLoomShiftRepository.findByLoom(Integer.parseInt(loomSel), sd, ed);

            int i = 1;
            for (WeaverRewardDateLoomShiftWise w:weaverRewardList1) {
                totalReward = totalReward+w.getRewardAmount();
                LocalDate d = w.getRewardDate();
                String formattedDate = d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                w.setDateInString(formattedDate);
                w.setSrNo(i);
                i++;
            }
            if(weaverRewardList1.size()==0){
                message = "No Result Found ";
            }
            List<String> loomList = weaverProductionTargetsRepository.findLoomId();
            loomList.remove(loomSel);
            loomList.add(0, loomSel);
           // model.addAttribute("loomList", loomList);
          //  model.addAttribute("startdate", startdate);
          //  model.addAttribute("enddate", enddate);
          //  model.addAttribute("totalReward", totalReward);
          //  model.addAttribute("weaverRewardList", weaverRewardList1);
        }
        String weaverRewLoomWise = new Gson().toJson(weaverRewardList1);
        System.out.println("weaver"+weaverRewLoomWise);
        String totalRew = new Gson().toJson(totalReward);
        String messageAlert = new Gson().toJson(message);
        String bothJson = "[" + weaverRewLoomWise + "," + totalRew + "," + messageAlert + "]";
        return bothJson;
    }
    @GetMapping("/unlockRewards")
    public String getUnlockRewards(Model model)
    {
       // String userId = LoggedUser.getInstance().getUserId();
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
            List<String> yearList1 = yearList;
            yearList1.remove(yearSelected);
            yearList1.add(0, yearSelected);
            List<String> months2 = months1;
            months2.remove(monthSelected);
            months2.add(0, monthSelected);
            model.addAttribute("yearList", yearList1);
            model.addAttribute("monthList", months2);
            return "unlockRewards";
        }
        else {
            model.addAttribute("message", "You are not authorised user to unlock");
            model.addAttribute("alertClass", "alert-danger");
            return "rewards";
        }
    }

    @RequestMapping(value="/index/processUnlockRewards", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processUnlockRewards(@RequestParam(value = "yearSel") String yearSel,
                                       @RequestParam(value = "monthSel") String monthSel)
    {
        String message = "";
        int index = months.indexOf(monthSel);
        System.out.println("Index" + index);
        int month = index;
        int pickMonth = month + 1;
        int pickYear = Integer.parseInt(yearSel);
        RewardLock rewardLock = rewardLockRepository.findByMonthAndYear(pickMonth, pickYear);
        List<String> empNoList = feltProdLoomWeaverRepository.findEmpNoByMonth(pickMonth,pickYear,"");
        if(rewardLock != null && rewardLock.isRewardFlag())
        {
            rewardDateLoomShiftRepository.deleteByMonthYear(pickMonth, pickYear);
            feltWeaverRewardRepository.deleteByMonthYear(pickMonth, pickYear);
            double reward = 0;
            for (String empNo:empNoList) {
                ptaxMonthRepository.updateRewardPtax(reward, empNo,pickMonth, pickYear);
            }
            rewardLockRepository.updateRewardFlag(false, false, pickMonth, pickYear);
        }
        message = "Reward Unlocked";
       // model.addAttribute("message", "Reward Unlocked");
       // model.addAttribute("alertClass", "alert-success");
        List<String> yearList1 = yearList;
        yearList1.remove(yearSel);
        yearList1.add(0,yearSel);
     //   model.addAttribute("yearList", yearList1);
        List<String> months2 = months1;
        months2.remove(monthSel);
        months2.add(0,monthSel);
       // model.addAttribute("monthList",months2);
        String messageAlert = new Gson().toJson(message);
        String bothJson = "[" + messageAlert + "]";
        return bothJson;
    }

    @PostMapping("/exportLoomData")
    public String exportDataToExcel(@RequestParam(value = "frmSubmit") String format, HttpServletResponse response, Model model) throws IOException, ParseException, DocumentException {
      //  System.out.println("ModelPath"+weaverRewardList);
        String heading = "";
        LocalDate cDate = LocalDate.now();
        String currentDate = cDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String templateName = "LoomwiseReward";
        String fileFormat = "pdf";
        if(format.contains("Pdf"))
        {
            fileFormat = "pdf";
        }
        else if(format.contains("Excel"))
        {
            fileFormat = "excel";
        }
        System.out.println("WeaverLoomList"+weaverRewardList1);
        if(weaverRewardList1.size()>0) {
            int loom = weaverRewardList1.get(0).getLoom_no();
            Month month = weaverRewardList1.get(0).getRewardDate().getMonth();
            int year = weaverRewardList1.get(0).getRewardDate().getYear();
            int size = (weaverRewardList1.size())-1;
            String startDate = String.valueOf(weaverRewardList1.get(0).getRewardDate());
            String endDate = String.valueOf(weaverRewardList1.get(size).getRewardDate());
            List<String> colHeaderList = Stream.of("Sr No","Emp No", "Emp Name", "Date", "Reward", "Reward Amount Slab").collect(Collectors.toList());
            List<List<String>> excelValues = new ArrayList<>();
            excelValues.add(colHeaderList);
            int i =1;
            double totalReward=0;
            for (WeaverRewardDateLoomShiftWise w : weaverRewardList1) {
                List<String> valueList = new ArrayList<>();
                String empNo = w.getEmpNo();
                String empName = w.getEmpName();
               // String Date = String.valueOf(w.getRewardDate());
                LocalDate d = w.getRewardDate();
                String formattedDate = d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String reward = String.valueOf(w.getRewardAmount());
                String rewardAmountSlab = w.getRewardAmountSlab();
                valueList.add(String.valueOf(i));
                valueList.add(empNo);
                valueList.add(empName);
                valueList.add(formattedDate);
                valueList.add(reward);
                valueList.add(rewardAmountSlab);
                excelValues.add(valueList);
                totalReward = totalReward+w.getRewardAmount();
                i++;
            }
            String result = translateNumberToWords.translate(ctryCd, lang, String.valueOf(totalReward));
            heading="PAID FOR              : REWARDS                                                                                                                              Date :"+currentDate+"&"+"DEPARTMENT     : FELT WEAVING                                                                                                                       Loom No. : "+loom;
            String pdfReportName = "loomwiseReward&TOTAL REWARDS: Rupees "+result+" only";
            exportToExcel.doExcel(templateName, fileFormat, excelValues, pdfReportName, heading,"Reward_"+loom+"_"+startDate+"_"+endDate, response);
        }
        else {
            List<String> loomList = weaverProductionTargetsRepository.findLoomId();
            model.addAttribute("loomList", loomList);
        }
        return "rewardLoomWiseForm";
    }

    @PostMapping("/exportEmployeeData")
    public String exportEmployeeDataToExcel(@RequestParam(value = "frmSubmit") String format, HttpServletResponse response, Model model) throws IOException, ParseException, DocumentException {
        //  System.out.println("ModelPath"+weaverRewardList);
       // System.out.println("WeaverLoomList"+weaverRewardListEmpWise);
        String heading = "";
        LocalDate cDate = LocalDate.now();
        String currentDate = cDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String templateName = "EmployeewiseReward";
        String fileFormat = "pdf";
        if(format.contains("Pdf"))
        {
            fileFormat = "pdf";
        }
        else if(format.contains("Excel"))
        {
            fileFormat = "excel";
        }
        List<String> colHeaderList = Stream.of("Sr No","Emp No", "Emp Name", "Designation", "Date", "Shift", "Loom No", "Pick meter", "Reward", "Reward Amount Slab").collect(Collectors.toList());
        List<List<String>> excelValues = new ArrayList<>();
        excelValues.add(colHeaderList);
        String empNo="";
        int i =1;
        double totalReward = 0;
        if(weaverRewardListEmpWise.size()>0) {
            Month month = weaverRewardListEmpWise.get(0).getRewardDate().getMonth();
            int year = weaverRewardListEmpWise.get(0).getRewardDate().getYear();
            int size = (weaverRewardListEmpWise.size())-1;
            String startDate = String.valueOf(weaverRewardListEmpWise.get(0).getRewardDate());
            String endDate = String.valueOf(weaverRewardListEmpWise.get(size).getRewardDate());
            for (WeaverRewardDateLoomShiftWise w : weaverRewardListEmpWise) {
                List<String> valueList = new ArrayList<>();
                empNo = w.getEmpNo();
                String empName = w.getEmpName();
              //  String designation = w.getDesignation();
                String category = w.getCategory();
                LocalDate d = w.getRewardDate();
                String formattedDate = d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
               // String Date = String.valueOf(w.getRewardDate());
                String shift = w.getShift();
                String loom = String.valueOf(w.getLoom_no());
                String pickmeter = w.getPickMeter();
                String reward = String.valueOf(w.getRewardAmount());
                String rewardAmountSlab = w.getRewardAmountSlab();
                valueList.add(String.valueOf(i));
                valueList.add(empNo);
                valueList.add(empName);
                valueList.add(category);
                valueList.add(formattedDate);
                valueList.add(shift);
                valueList.add(loom);
                valueList.add(pickmeter);
                valueList.add(reward);
                valueList.add(rewardAmountSlab);
                excelValues.add(valueList);
                totalReward = totalReward+w.getRewardAmount();
                i++;
            }
            String result = translateNumberToWords.translate(ctryCd, lang, String.valueOf(totalReward));
            heading="PAID FOR              : REWARDS                                                                                                                              Date :"+currentDate+"&"+"DEPARTMENT     : FELT WEAVING                                                                                                                   Emp No. : "+empNo;
            String pdfReportName = "employeewiseReward&TOTAL REWARDS: Rupees "+result+" only";
            exportToExcel.doExcel(templateName, fileFormat, excelValues, pdfReportName, heading, "Reward_"+empNo+"_"+startDate+"_"+endDate, response);
        }
        else {
            List<String> empList = feltProdLoomWeaverRepository.findEmpId("");
            model.addAttribute("empList", empList);
        }
        return "rewardEmployeeWiseForm";
    }
    @PostMapping("/exportRewardData")
    public String exportRewardDataToExcel(@RequestParam(value = "frmSubmit") String format, HttpServletResponse response) throws IOException, ParseException, DocumentException {
        //  System.out.println("ModelPath"+weaverRewardList);
        String templateName = "DatewiseReward";
        String heading = "";
        String fileFormat = "pdf";
        if(format.contains("Pdf"))
        {
            fileFormat = "pdf";
        }
        else if(format.contains("Excel"))
        {
            fileFormat = "excel";
        }
        System.out.println("WeaverLoomList"+weaverRewardDateLoomShiftWiseList);
        if(weaverRewardDateLoomShiftWiseList.size()>0) {
            int size = weaverRewardDateLoomShiftWiseList.size()-1;
            Month month = weaverRewardDateLoomShiftWiseList.get(0).getRewardDate().getMonth();
            int year = weaverRewardDateLoomShiftWiseList.get(0).getRewardDate().getYear();
            LocalDate cDate = LocalDate.now();
            String currentDate = cDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
          //  LocalDate sDate = weaverRewardDateLoomShiftWiseList.get(0).getRewardDate();
           // LocalDate eDate = weaverRewardDateLoomShiftWiseList.get(size).getRewardDate();
            String startDate = sd.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String endDate = ed.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

         //   String startDate = String.valueOf(weaverRewardDateLoomShiftWiseList.get(0).getRewardDate());
          //  String endDate = String.valueOf(weaverRewardDateLoomShiftWiseList.get(size).getRewardDate());
            List<String> colHeaderList = Stream.of("Sr No","Emp No", "Emp Name", "Category", "Date", "Shift", "Loom No", "Pick meter", "Reward in Rs.", "Reward Amount Slab").collect(Collectors.toList());
            List<List<String>> excelValues = new ArrayList<>();
            excelValues.add(colHeaderList);
            int i = 1;
            double totalReward = 0;
            for (WeaverRewardDateLoomShiftWise w : weaverRewardDateLoomShiftWiseList) {
                List<String> valueList = new ArrayList<>();
                String empNo = w.getEmpNo();
                String empName = w.getEmpName();
              //  String designation = w.getDesignation();
              //  String Date = String.valueOf(w.getRewardDate());
                String category = w.getCategory();
                LocalDate d = w.getRewardDate();
                String formattedDate = d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String shift = w.getShift();
                String loom = String.valueOf(w.getLoom_no());
                String pickmeter = w.getPickMeter();
                String reward = String.valueOf(w.getRewardAmount());
                String rewardAmountSlab = w.getRewardAmountSlab();
                valueList.add(String.valueOf(i));
                valueList.add(empNo);
                valueList.add(empName);
                valueList.add(category);
                valueList.add(formattedDate);
                valueList.add(shift);
                valueList.add(loom);
                valueList.add(pickmeter);
                valueList.add(reward);
                valueList.add(rewardAmountSlab);
                excelValues.add(valueList);
                totalReward = totalReward+w.getRewardAmount();
                i++;
            }
            String result = translateNumberToWords.translate(ctryCd, lang, String.valueOf(totalReward));
            String pdfReportName = "rewardForm&TOTAL REWARDS: Rupees "+result+" only";
            heading="PAID FOR              : REWARDS                                                                                                                               Date :"+currentDate+"&"+"DEPARTMENT     : FELT WEAVING                                                                                                                  "+startDate+" To "+endDate;
            exportToExcel.doExcel(templateName, fileFormat, excelValues, pdfReportName, heading, "Reward_"+startDate+"_"+endDate, response);
        }
        return "rewardHierarchyBasedForm";
    }

    @PostMapping("/exportMonthlyRewardData")
    public String exportMonthlyRewardDataToExcel(@RequestParam(value = "frmSubmit") String format, HttpServletResponse response, Model model) throws IOException, ParseException, DocumentException {
        //  System.out.println("ModelPath"+weaverRewardList);
        LocalDate cDate = LocalDate.now();
        String currentDate = cDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.println("Fileformat"+format);
        String templateName = "MonthlyReward";
        String heading = "";
        String fileFormat = "pdf";
        if(format.contains("Pdf"))
        {
            fileFormat = "pdf";
        }
        else if(format.contains("Excel"))
        {
            fileFormat = "excel";
        }
        System.out.println("WeaverLoomList"+feltWeaverRewardList2);
        String month1 = "";
        if(feltWeaverRewardList2.size()>0) {
            int m = feltWeaverRewardList2.get(0).getRewardMonth() - 1;
            month1 = months.get(m);
            int year = feltWeaverRewardList2.get(0).getRewardYear();
            List<String> colHeaderList = Stream.of("Sr No","Emp No", "Emp Name", "Total Shift", "Reward in Rs.").collect(Collectors.toList());
            List<List<String>> excelValues = new ArrayList<>();
            excelValues.add(colHeaderList);
            int i =1;
            double totalReward = 0;
            double totalShift = 0;
            for (FeltWeaverReward w : feltWeaverRewardList2) {
                List<String> valueList = new ArrayList<>();
                String empNo = w.getEmpNo();
                String empName = w.getEmpName();
                int month = w.getRewardMonth();
                int totalshift = (int)w.getTotalShift();
                String reward = String.valueOf((int)w.getRewardAmount());
                valueList.add(String.valueOf(i));
                valueList.add(empNo);
                valueList.add(empName);
                valueList.add(String.valueOf(totalshift));
                valueList.add(reward);
                excelValues.add(valueList);
                totalReward = totalReward+w.getRewardAmount();
                totalShift = totalShift+w.getTotalShift();
                i++;
            }
            List<String> valList = new ArrayList<>();
            valList.add("");
            valList.add("");
            valList.add("Total");
            valList.add(String.valueOf((int)totalShift));
            valList.add(String.valueOf((int)totalReward));
            excelValues.add(valList);
            String result = translateNumberToWords.translate(ctryCd, lang, String.valueOf(totalReward));
            heading="PAID FOR              : REWARDS                                                                                                                               Date :"+currentDate+"&"+"DEPARTMENT     : FELT WEAVING                                                                                                                        Month  : "+month1+"-"+year;
           String pdfReportName = "monthlyReward&TOTAL REWARDS : Rupees "+result+" only";
            exportToExcel.doExcel(templateName, fileFormat, excelValues, pdfReportName, heading, "Reward_"+month1+"_"+year, response);
        }
        else{
            List<String> yearList = new ArrayList<>();
            int year = LocalDate.now().getYear();
            for(int y = year; y>=2021; y--)
            {
                yearList.add(String.valueOf(y));
            }
            model.addAttribute("yearList", yearList);
            model.addAttribute("monthList",months);
        }
        return "monthlyRewards";
    }
    List<FeltWeaverReward> feltWeaverRewardList2 = new ArrayList<>();
    @RequestMapping(value="/index/processMonthlyRewards1", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processMonthlyRewards1(@RequestParam(value = "yearSel") String yearSel,
                                                                       @RequestParam(value = "monthSel") String monthSel) throws ParseException {

        String message = "";
        feltWeaverRewardList.clear();
        System.out.println("Year and month sel*****" + yearSel + "::" + monthSel);
        int index = months.indexOf(monthSel);
        System.out.println("Index" + index);
        int month = index;
        int pickMonth = month + 1;
        int pickYear = Integer.parseInt(yearSel);
        String empCategory = "Weaver";
        double totalMonthReward = 0;
        RewardLock rewardLock = rewardLockRepository.findByMonthAndYear(pickMonth, pickYear);
        if(rewardLock != null && rewardLock.isRewardFlag())
        {
            System.out.println("Please press unlock");
            message = "Rewards already calculated";
            yearSelected = yearSel;
            monthSelected = monthSel;
            feltWeaverRewardList = feltWeaverRewardRepository.findByMonthYear(pickMonth, pickYear);
           // double totalMonthReward = 0;
            int i = 1;
            feltWeaverRewardList2 = feltWeaverRewardRepository.findNonZeroRewards(pickMonth,pickYear);
            for (FeltWeaverReward f : feltWeaverRewardList2) {
                f.setSrNo(i);
                totalMonthReward = totalMonthReward + f.getRewardAmount();
                i++;

            }
        }
        else if(rewardLock == null || !rewardLock.isRewardFlag()) {
          //  try{
                List<String> empNoList = feltProdLoomWeaverRepository.findEmpNoByMonth(pickMonth, pickYear, "");

                rewardDateLoomShiftRepository.deleteByMonthYear(pickMonth, pickYear);
                feltWeaverRewardRepository.deleteByMonthYear(pickMonth, pickYear);
                double reward1 = 0;
                for (String empNo : empNoList) {
                    ptaxMonthRepository.updateRewardPtax(reward1, empNo, pickMonth, pickYear);
                }

                if(empNoList.size()>0) {
                    for (String empId : empNoList) {
                        double reward = 0;
                        String empName = "";
                        String designation = "";
                        String category = "";
                        System.out.printf("Emp: " + empId + "\n");
                        List<FeltProdLoomWeaver> feltProdLoomWeaverList = feltProdLoomWeaverRepository.findEmpByIdMonthYear(empId, pickMonth, pickYear, "", "", "", "", "", "");
                        int loomNo = 0;
                        String pick = "";
                        String shiftId = "";
                        String rewardAmountSlab = "";
                        String weftDetails = "";
                        if (feltProdLoomWeaverList.size() > 0) {
                            for (FeltProdLoomWeaver f : feltProdLoomWeaverList) {
                                try {
                                    double reward2 = 0;
                                    double rw = 0;
                                    empName = f.getEmpName();
                                    designation = f.getDesignation();
                                    category = f.getCategory();
                                    loomNo = Integer.parseInt(f.getLoomEng());
                                    String lm = String.valueOf(loomNo);
                                    weftDetails = f.getWeftDetails();
                                    pick = f.getPick();
                                    shiftId = f.getShiftId();
                                    String shift = "";
                                    Map<String, List<List<Integer>>> rewardSlabMap = new HashMap<>();
                                    if (shiftId.equals("1")) {
                                        shift = "A";
                                        rewardSlabMap = weaverProdTargetMapAB;
                                    } else if (shiftId.equals("2")) {
                                        shift = "B";
                                        rewardSlabMap = weaverProdTargetMapAB;
                                    } else if (shiftId.equals("3")) {
                                        shift = "C";
                                        rewardSlabMap = weaverProdTargetMapC;
                                    }
                                    String loomWeftKey = "";
                                    for (String s : rewardSlabMap.keySet()) {
                                        String str[] = s.split("_");
                                        if (str[0].equals(lm)) {
                                            if (str[1].contains(weftDetails)) {
                                                loomWeftKey = s;
                                            }
                                        }
                                    }
                                    if (loomWeftKey != "") {
                                        List<List<Integer>> slabrewardList = rewardSlabMap.get(loomWeftKey);
                                        List<Integer> slabList = slabrewardList.get(0);
                                        List<Integer> rewardList = slabrewardList.get(1);
                                        int p = Integer.valueOf(pick);
                                        int size = (slabList.size()) - 1;
                                        for (int i = 0; i <= size; i++) {
                                            if (p < slabList.get(0)) {
                                                rw = 0;
                                                rewardAmountSlab = "";
                                            }
                                            if (i < size) {
                                                if (p >= slabList.get(i) && p < slabList.get(i + 1)) {
                                                    rw = rewardList.get(i);
                                                    rewardAmountSlab = String.valueOf(slabList.get(i));
                                                }
                                            } else {
                                                if (i == size && p >= slabList.get(i)) {
                                                    rw = rewardList.get(i);
                                                    rewardAmountSlab = String.valueOf(slabList.get(i));
                                                }
                                            }

                                        }

                                    }

                                    WeaverRewardDateLoomShiftWise weaver = new WeaverRewardDateLoomShiftWise();
                                    weaver.setEmpNo(empId);
                                    weaver.setEmpName(empName);
                                    weaver.setDesignation(designation);
                                    weaver.setCategory(category);
                                    //    LocalDate d = new java.sql.Date(rwDate.getTime()).toLocalDate();
                                    LocalDate d = f.getDocDate().toLocalDate();
                                    System.out.println("Local date" + d);
                                    weaver.setRewardDate(d);
                                    weaver.setLoom_no(loomNo);
                                    weaver.setPickMeter(pick);
                                    weaver.setShift(shiftId);
                                    weaver.setRewardAmount(rw);
                                    if(rw>0)
                                    {
                                        weaver.setShiftAchieved("1");
                                    }
                                    else {
                                        weaver.setShiftAchieved("0");
                                    }
                                    weaver.setRewardAmountSlab(rewardAmountSlab);
                                    weaver.setWeftDetail(weftDetails);
                                    rewardDateLoomShiftRepository.save(weaver);
                                    System.out.println("Saved records" + empId + ":" + d + ":" + loomNo + ":" + pick + rw);
                                    reward2 = reward2 + rw;
                                }
                                catch (Exception e)
                                {
                                    Logger.getGlobal().info("Problem during calculating reward"+e+" Emp no"+empId+":"+f.getDocDate().toLocalDate());
                                }
                            }

                        } else {
                            Logger.getGlobal().info("Emp:" + empId + " No record found in felt production table");
                        }
                        //reward = reward +reward2;
                    }

                    for (String empId : empNoList) {
                        List<WeaverRewardDateLoomShiftWise> weaverRewardDetailList = rewardDateLoomShiftRepository.findByEmpNoAndMonthYear(empId, pickMonth, pickYear);
                        if (weaverRewardDetailList.size() > 0) {
                            double totalReward = 0;
                            double totalShift = 0;
                            for (WeaverRewardDateLoomShiftWise w : weaverRewardDetailList) {
                                totalReward = totalReward + w.getRewardAmount();
                                if(w.getShiftAchieved().equals("1")) {
                                    totalShift = totalShift + 1;
                                }
                            }
                            System.out.println("Total Reward" + empId + "::" + totalReward+"::"+totalShift);

                            FeltWeaverReward feltWeaverReward = new FeltWeaverReward();
                            feltWeaverReward.setEmpNo(empId);
                            feltWeaverReward.setEmpName(weaverRewardDetailList.get(0).getEmpName());
                            feltWeaverReward.setDesignation(weaverRewardDetailList.get(0).getDesignation());
                            feltWeaverReward.setRewardMonth(pickMonth);
                            feltWeaverReward.setRewardYear(pickYear);
                            feltWeaverReward.setRewardAmount(totalReward);
                            feltWeaverReward.setTotalShift(totalShift);
                            feltWeaverRewardList.add(feltWeaverReward);
                            feltWeaverRewardRepository.save(feltWeaverReward);
                            int i = ptaxMonthRepository.updateRewardPtax(totalReward, empId, pickMonth, pickYear);
                            System.out.println("Ptax gets updated" + i);
                        }
                    }

                    int i = 1;
                    feltWeaverRewardList2 = feltWeaverRewardRepository.findNonZeroRewards(pickMonth,pickYear);
                    for (FeltWeaverReward f : feltWeaverRewardList2) {
                            f.setSrNo(i);
                            totalMonthReward = totalMonthReward + f.getRewardAmount();
                            i++;

                    }

                    /* Here we lock the reward calculation by setting reward lock to true*/
                    if (rewardLock == null) {
                        RewardLock rewardLock1 = new RewardLock();
                        rewardLock1.setMonth(pickMonth);
                        rewardLock1.setYear(pickYear);
                        rewardLock1.setRewardFlag(true);
                        rewardLock1.setRewardLock(true);
                        rewardLockRepository.save(rewardLock1);
                    } else if (!rewardLock.isRewardLock()) {
                        rewardLockRepository.updateRewardFlag(true, true, pickMonth, pickYear);
                    }
                }
        }

        List<String> yearList2 = yearList;
        yearList2.remove(yearSel);
        yearList2.add(0,yearSel);
        List<String> months2 = months1;
        months2.remove(monthSel);
        months2.add(0,monthSel);
        if(feltWeaverRewardList.size()==0){
            message = "No Result Found";
        }
        String feltList = new Gson().toJson(feltWeaverRewardList2);
        String totalReward = new Gson().toJson(totalMonthReward);
        String meaasgeAlert = new Gson().toJson(message);
        String bothJson = "[" + feltList + "," + totalReward + "," + meaasgeAlert +"]";
        System.out.println("bothjson"+bothJson);
        return bothJson;
    }
    @RequestMapping(value="/getmsg", method= RequestMethod.POST)
    public @ResponseBody
    String display1(@RequestParam(value = "empNo") String empNo)
    {
        System.out.println("welcome"+empNo);
        return empNo;
    }

    @GetMapping("/rewardSlabMasterForm")
    public String getRewardSlabMaster(Model model)
    {
        List<WeaverProductionTargets> weaverProductionTargetsList = weaverProductionTargetsRepository.findAll();
        model.addAttribute("weaverProductionList",weaverProductionTargetsList);
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        return "rewardSlabMasterForm";
    }

    @GetMapping("/addNewRewardSlabForm")
    public String addNewSlab(Model model)
    {
        model.addAttribute("weaverProductionTargets", new WeaverProductionTargets());
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        return "addNewRewardSlabForm";
    }

    @GetMapping("/updateRewardSlabForm")
    public String updateSlab(Model model)
    {
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        model.addAttribute("weaverProductionTargets", new WeaverProductionTargets());
        List<String> idList = weaverProductionTargetsRepository.findLoomPrimaryList();
        model.addAttribute("idList",idList);
        return "updateRewardSlabForm";
    }

    @GetMapping("/deleteRewardSlabForm")
    public String deleteSlab(Model model)
    {
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        model.addAttribute("weaverProductionTargets", new WeaverProductionTargets());
        List<String> idList = weaverProductionTargetsRepository.findLoomPrimaryList();
        model.addAttribute("idList",idList);
        return "deleteRewardSlabForm";
    }
    @PostMapping("/processNewRewardSlab")
    public String processNewRewardSlab(WeaverProductionTargets weaverProductionTargets, Model model)
    {
        System.out.println("************************");
        System.out.println(weaverProductionTargets);
        weaverProductionTargetsRepository.save(weaverProductionTargets);
        List<WeaverProductionTargets> weaverProductionTargetsList = weaverProductionTargetsRepository.findAll();
        model.addAttribute("weaverProductionList",weaverProductionTargetsList);
        return "rewardSlabMasterForm";
    }
    @RequestMapping(value="/index/processRewardSlabId", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processRewardSlabId(@RequestParam(value = "idSel") String idSel)
    {
        System.out.println("Welcome in unlock2"+idSel);

        String message = "Incentive Unlocked";
        WeaverProductionTargets weaverProductionTargets = weaverProductionTargetsRepository.findRewById(idSel);
        double loom = weaverProductionTargets.getLoom();
        String rpm = weaverProductionTargets.getRpm();
        String eff = weaverProductionTargets.getEffPer();
        String prodper = weaverProductionTargets.getProdnPerHr();
        String weft = weaverProductionTargets.getWeftDetails();

        String shiftAB = new Gson().toJson(weaverProductionTargets.getShiftAB());
        String shiftC = new Gson().toJson(weaverProductionTargets.getShiftC());

        String shiftABReward = new Gson().toJson(weaverProductionTargets.getShiftABPicksForRewards());
        String shiftCReward = new Gson().toJson(weaverProductionTargets.getShiftCPicksForRewards());
        String reward1 = new Gson().toJson(weaverProductionTargets.getReward1());

        String shiftABReward2 = new Gson().toJson(weaverProductionTargets.getShiftABPicksForRewards2());
        String shiftCReward2 = new Gson().toJson(weaverProductionTargets.getShiftCPicksForRewards2());
        String reward2 = new Gson().toJson(weaverProductionTargets.getReward2());

        String shiftABReward3 = new Gson().toJson(weaverProductionTargets.getShiftABPicksForRewards3());
        String shiftCReward3 = new Gson().toJson(weaverProductionTargets.getShiftCPicksForRewards3());
        String reward3 = new Gson().toJson(weaverProductionTargets.getReward3());

        String shiftABReward4 = new Gson().toJson(weaverProductionTargets.getShiftABPicksForRewards4());
        String shiftCReward4 = new Gson().toJson(weaverProductionTargets.getShiftCPicksForRewards4());
        String reward4 = new Gson().toJson(weaverProductionTargets.getReward4());

        String shiftABReward5 = new Gson().toJson(weaverProductionTargets.getShiftABPicksForRewards5());
        String shiftCReward5 = new Gson().toJson(weaverProductionTargets.getShiftCPicksForRewards5());
        String reward5 = new Gson().toJson(weaverProductionTargets.getReward5());

        String shiftABReward6 = new Gson().toJson(weaverProductionTargets.getShiftABPicksForRewards6());
        String shiftCReward6 = new Gson().toJson(weaverProductionTargets.getShiftCPicksForRewards6());
        String reward6 = new Gson().toJson(weaverProductionTargets.getReward6());

        String shiftABReward7 = new Gson().toJson(weaverProductionTargets.getShiftABPicksForRewards7());
        String shiftCReward7 = new Gson().toJson(weaverProductionTargets.getShiftCPicksForRewards7());
        String reward7 = new Gson().toJson(weaverProductionTargets.getReward7());

        String shiftABReward8 = new Gson().toJson(weaverProductionTargets.getShiftABPicksForRewards8());
        String shiftCReward8 = new Gson().toJson(weaverProductionTargets.getShiftCPicksForRewards8());
        String reward8 = new Gson().toJson(weaverProductionTargets.getReward8());

        String shiftABReward9 = new Gson().toJson(weaverProductionTargets.getShiftABPicksForRewards9());
        String shiftCReward9 = new Gson().toJson(weaverProductionTargets.getShiftCPicksForRewards9());
        String reward9 = new Gson().toJson(weaverProductionTargets.getReward9());

        String shiftABReward10 = new Gson().toJson(weaverProductionTargets.getShiftABPicksForRewards10());
        String shiftCReward10 = new Gson().toJson(weaverProductionTargets.getShiftCPicksForRewards10());
        String reward10 = new Gson().toJson(weaverProductionTargets.getReward10());

        //  String prod = new Gson().toJson(loom);
        String prod1 = new Gson().toJson(loom);
        String rpm1 = new Gson().toJson(rpm);
        String eff1 = new Gson().toJson(eff);
        String prodper1 = new Gson().toJson(prodper);
        String weft1 = new Gson().toJson(weft);


        String bothJson = "[" + prod1 + "," + rpm1 + ","+eff1+","+prodper1+","+weft1+","+shiftAB+","+shiftC+","+shiftABReward+","+shiftCReward+","+reward1+","+shiftABReward2+","+shiftCReward2+","+reward2+","+shiftABReward3+","+shiftCReward3+","+reward3+","+shiftABReward4+","+shiftCReward4+","+reward4+","+shiftABReward5+","+shiftCReward5+","+reward5+","+shiftABReward6+","+shiftCReward6+","+reward6+","+shiftABReward7+","+shiftCReward7+","+reward7+","+shiftABReward8+","+shiftCReward8+","+reward8+","+shiftABReward9+","+shiftCReward9+","+reward9+","+shiftABReward10+","+shiftCReward10+","+reward10+"]";
        return bothJson;
    }
    @RequestMapping(value="/index/processRewardUpdate", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processRewardUpdate(@RequestParam(value = "idSel") String idSel, @RequestParam(value = "loom") String loom, @RequestParam(value = "rpm") String rpm, @RequestParam(value = "effPer") String effPer, @RequestParam(value = "prodnPerHr") String prodnPerHr, @RequestParam(value = "weftDetails") String weftDetails, @RequestParam(value = "shiftAB") String shiftAB, @RequestParam(value = "shiftC") String shiftC, @RequestParam(value = "shiftABPicksForRewards") String shiftABPicksForRewards, @RequestParam(value = "shiftCPicksForRewards") String shiftCPicksForRewards, @RequestParam(value = "reward1") String reward1
            , @RequestParam(value = "shiftABPicksForRewards2") String shiftABPicksForRewards2, @RequestParam(value = "shiftCPicksForRewards2") String shiftCPicksForRewards2, @RequestParam(value = "reward2") String reward2
            , @RequestParam(value = "shiftABPicksForRewards3") String shiftABPicksForRewards3, @RequestParam(value = "shiftCPicksForRewards3") String shiftCPicksForRewards3, @RequestParam(value = "reward3") String reward3
            , @RequestParam(value = "shiftABPicksForRewards4") String shiftABPicksForRewards4, @RequestParam(value = "shiftCPicksForRewards4") String shiftCPicksForRewards4, @RequestParam(value = "reward4") String reward4
            , @RequestParam(value = "shiftABPicksForRewards5") String shiftABPicksForRewards5, @RequestParam(value = "shiftCPicksForRewards5") String shiftCPicksForRewards5, @RequestParam(value = "reward5") String reward5
            , @RequestParam(value = "shiftABPicksForRewards6") String shiftABPicksForRewards6, @RequestParam(value = "shiftCPicksForRewards6") String shiftCPicksForRewards6, @RequestParam(value = "reward6") String reward6
            , @RequestParam(value = "shiftABPicksForRewards7") String shiftABPicksForRewards7, @RequestParam(value = "shiftCPicksForRewards7") String shiftCPicksForRewards7, @RequestParam(value = "reward7") String reward7
            , @RequestParam(value = "shiftABPicksForRewards8") String shiftABPicksForRewards8, @RequestParam(value = "shiftCPicksForRewards8") String shiftCPicksForRewards8, @RequestParam(value = "reward8") String reward8
            , @RequestParam(value = "shiftABPicksForRewards9") String shiftABPicksForRewards9, @RequestParam(value = "shiftCPicksForRewards9") String shiftCPicksForRewards9, @RequestParam(value = "reward9") String reward9
            , @RequestParam(value = "shiftABPicksForRewards10") String shiftABPicksForRewards10, @RequestParam(value = "shiftCPicksForRewards10") String shiftCPicksForRewards10, @RequestParam(value = "reward10") String reward10)
    {
        System.out.println("Welcome in reward update"+idSel+loom+rpm+effPer+prodnPerHr+weftDetails+shiftAB+shiftC+shiftABPicksForRewards+shiftCPicksForRewards+reward1+shiftABPicksForRewards2+shiftCPicksForRewards2+reward2+shiftABPicksForRewards3+shiftCPicksForRewards3+reward3+shiftABPicksForRewards4+shiftCPicksForRewards4+reward4+shiftABPicksForRewards5+shiftCPicksForRewards5+reward5+shiftABPicksForRewards6+shiftCPicksForRewards6+reward6+shiftABPicksForRewards7+shiftCPicksForRewards7+reward7+shiftABPicksForRewards8+shiftCPicksForRewards8+reward8+shiftABPicksForRewards9+shiftCPicksForRewards9+reward9+shiftABPicksForRewards10+shiftCPicksForRewards10+reward10);
        //weaverProductionTargetsRepository.updateRewardSlab(loom,rpm,effPer,prodnPerHr,weftDetails,shiftAB,shiftC,shiftABPicksForRewards,shiftCPicksForRewards,reward1,shiftABPicksForRewards2,shiftCPicksForRewards2,reward2,shiftABPicksForRewards3,shiftCPicksForRewards3,reward3,shiftABPicksForRewards4,shiftCPicksForRewards4,reward4,shiftABPicksForRewards5,shiftCPicksForRewards5,reward5,shiftABPicksForRewards6,shiftCPicksForRewards6,reward6,shiftABPicksForRewards7,shiftCPicksForRewards7,reward7,shiftABPicksForRewards8,shiftCPicksForRewards8,reward8,shiftABPicksForRewards9,shiftCPicksForRewards9,reward9,shiftABPicksForRewards10,shiftCPicksForRewards10,reward10,idSel);
        weaverProductionTargetsRepository.updateRewardSlab(Integer.parseInt(loom),rpm,effPer,prodnPerHr,weftDetails,shiftAB,shiftC,shiftABPicksForRewards,shiftCPicksForRewards,reward1,shiftABPicksForRewards2,shiftCPicksForRewards2,reward2,shiftABPicksForRewards3,shiftCPicksForRewards3,reward3,shiftABPicksForRewards4,shiftCPicksForRewards4,reward4,shiftABPicksForRewards5,shiftCPicksForRewards5,reward5,shiftABPicksForRewards6,shiftCPicksForRewards6,reward6,shiftABPicksForRewards7,shiftCPicksForRewards7,reward7,shiftABPicksForRewards8,shiftCPicksForRewards8,reward8,shiftABPicksForRewards9,shiftCPicksForRewards9,reward9,shiftABPicksForRewards10,shiftCPicksForRewards10,reward10, idSel);
        String message = "Reward Updated";
        String messageAlert = new Gson().toJson(message);
        //   String messageAlert1 = new Gson().toJson(fact);
        String bothJson = "[" + messageAlert + "]";
        //  String bothJson = "[" + messageAlert + "," + messageAlert1 + "]";
        return bothJson;

    }
    @RequestMapping(value="/index/processRewardDelete", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processRewardDelete(@RequestParam(value = "idSel") String idSel)
    {
       // System.out.println("Welcome in reward update"+idSel+loom+rpm+effPer+prodnPerHr+weftDetails+shiftAB+shiftC+shiftABPicksForRewards+shiftCPicksForRewards+reward1+shiftABPicksForRewards2+shiftCPicksForRewards2+reward2+shiftABPicksForRewards3+shiftCPicksForRewards3+reward3+shiftABPicksForRewards4+shiftCPicksForRewards4+reward4+shiftABPicksForRewards5+shiftCPicksForRewards5+reward5+shiftABPicksForRewards6+shiftCPicksForRewards6+reward6+shiftABPicksForRewards7+shiftCPicksForRewards7+reward7+shiftABPicksForRewards8+shiftCPicksForRewards8+reward8+shiftABPicksForRewards9+shiftCPicksForRewards9+reward9+shiftABPicksForRewards10+shiftCPicksForRewards10+reward10);
        //weaverProductionTargetsRepository.updateRewardSlab(loom,rpm,effPer,prodnPerHr,weftDetails,shiftAB,shiftC,shiftABPicksForRewards,shiftCPicksForRewards,reward1,shiftABPicksForRewards2,shiftCPicksForRewards2,reward2,shiftABPicksForRewards3,shiftCPicksForRewards3,reward3,shiftABPicksForRewards4,shiftCPicksForRewards4,reward4,shiftABPicksForRewards5,shiftCPicksForRewards5,reward5,shiftABPicksForRewards6,shiftCPicksForRewards6,reward6,shiftABPicksForRewards7,shiftCPicksForRewards7,reward7,shiftABPicksForRewards8,shiftCPicksForRewards8,reward8,shiftABPicksForRewards9,shiftCPicksForRewards9,reward9,shiftABPicksForRewards10,shiftCPicksForRewards10,reward10,idSel);
     //   weaverProductionTargetsRepository.updateRewardSlab(Integer.parseInt(loom),rpm,effPer,prodnPerHr,weftDetails,shiftAB,shiftC,shiftABPicksForRewards,shiftCPicksForRewards,reward1,shiftABPicksForRewards2,shiftCPicksForRewards2,reward2,shiftABPicksForRewards3,shiftCPicksForRewards3,reward3,shiftABPicksForRewards4,shiftCPicksForRewards4,reward4,shiftABPicksForRewards5,shiftCPicksForRewards5,reward5,shiftABPicksForRewards6,shiftCPicksForRewards6,reward6,shiftABPicksForRewards7,shiftCPicksForRewards7,reward7,shiftABPicksForRewards8,shiftCPicksForRewards8,reward8,shiftABPicksForRewards9,shiftCPicksForRewards9,reward9,shiftABPicksForRewards10,shiftCPicksForRewards10,reward10, idSel);
        weaverProductionTargetsRepository.deleteRewardSlab(idSel);
        String message = "Reward Deleted";
        String messageAlert = new Gson().toJson(message);
        //   String messageAlert1 = new Gson().toJson(fact);
        String bothJson = "[" + messageAlert + "]";
        //  String bothJson = "[" + messageAlert + "," + messageAlert1 + "]";
        return bothJson;

    }


/*   @GetMapping("/processLoomWiseForm")
    public String processLoomWiseForm(@RequestParam(value = "loomSel") String loomSel,
                                      @RequestParam(value = "startdate") String startdate,
                                      @RequestParam(value = "enddate") String enddate, Model model) throws ParseException
    {
        System.out.println("Param"+loomSel+startdate+enddate);
      //  System.out.println("Excel"+excel);
        if(loomSel.equals("") || startdate.equals("")||enddate.equals(""))
        {
            List<String> loomList = weaverProductionTargetsRepository.findLoomId();
            model.addAttribute("loomList", loomList);
            model.addAttribute("message","Please select Loom No/Date");
            model.addAttribute("alertClass", "alert-danger");
        }
        else {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
            LocalDate sd = new java.sql.Date(startDate.getTime()).toLocalDate();
            LocalDate ed = new java.sql.Date(endDate.getTime()).toLocalDate();
            weaverRewardList1 = rewardDateLoomShiftRepository.findByLoom(Integer.parseInt(loomSel), sd, ed);
            double totalReward = 0;
            int i = 1;
            for (WeaverRewardDateLoomShiftWise w:weaverRewardList1) {
                totalReward = totalReward+w.getRewardAmount();
                LocalDate d = w.getRewardDate();
                String formattedDate = d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                w.setDateInString(formattedDate);
                w.setSrNo(i);
                i++;
            }
            List<String> loomList = weaverProductionTargetsRepository.findLoomId();
            loomList.remove(loomSel);
            loomList.add(0, loomSel);
            model.addAttribute("loomList", loomList);
            model.addAttribute("startdate", startdate);
            model.addAttribute("enddate", enddate);
            model.addAttribute("totalReward", totalReward);
            model.addAttribute("weaverRewardList", weaverRewardList1);
        }
        return "rewardLoomWiseForm";
    }*/
     /*  @GetMapping("/processUnlockRewards")
    public String processUnlockRewards(@RequestParam(value = "yearSel") String yearSel,
                                       @RequestParam(value = "monthSel") String monthSel, Model model)
    {
        int index = months.indexOf(monthSel);
        System.out.println("Index" + index);
        int month = index;
        int pickMonth = month + 1;
        int pickYear = Integer.parseInt(yearSel);
        RewardLock rewardLock = rewardLockRepository.findByMonthAndYear(pickMonth, pickYear);
        List<String> empNoList = feltProdLoomWeaverRepository.findEmpNoByMonth(pickMonth,pickYear,"");
        if(rewardLock != null && rewardLock.isRewardFlag())
        {
            rewardDateLoomShiftRepository.deleteByMonthYear(pickMonth, pickYear);
            feltWeaverRewardRepository.deleteByMonthYear(pickMonth, pickYear);
            double reward = 0;
            for (String empNo:empNoList) {
                ptaxMonthRepository.updateRewardPtax(reward, empNo,pickMonth, pickYear);
            }
            rewardLockRepository.updateRewardFlag(false, false, pickMonth, pickYear);
        }
        model.addAttribute("message", "Reward Unlocked");
        model.addAttribute("alertClass", "alert-success");
        List<String> yearList1 = yearList;
        yearList1.remove(yearSel);
        yearList1.add(0,yearSel);
        model.addAttribute("yearList", yearList1);
        List<String> months2 = months1;
        months2.remove(monthSel);
        months2.add(0,monthSel);
        model.addAttribute("monthList",months2);
        return "unlockRewards";
    }*/
/*    @GetMapping("/processRewardHierarchyBasedForm")
    public String processHierarchyForm(@RequestParam(value = "startdate") String startdate,
                                       @RequestParam(value = "enddate") String enddate,
                                       Model model) throws ParseException {
        System.out.println("Form input"+startdate+"::"+enddate);
        if(startdate.equals("")||enddate.equals(""))
        {
            model.addAttribute("message","Please select date");
            model.addAttribute("alertClass", "alert-danger");
        }
        else {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
            LocalDate sd = new java.sql.Date(startDate.getTime()).toLocalDate();
            LocalDate ed = new java.sql.Date(endDate.getTime()).toLocalDate();
            weaverRewardDateLoomShiftWiseList = rewardDateLoomShiftRepository.findWithCriteria(sd, ed);
            double totalReward = 0;
            int i = 1;
            for (WeaverRewardDateLoomShiftWise w:weaverRewardDateLoomShiftWiseList) {
                totalReward = totalReward+w.getRewardAmount();
                LocalDate d = w.getRewardDate();
                String formattedDate = d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                w.setDateInString(formattedDate);
                w.setSrNo(i);
                i++;
            }
            model.addAttribute("startdate", startdate);
            model.addAttribute("enddate", enddate);
            model.addAttribute("totalReward",totalReward);
            model.addAttribute("weaverList", weaverRewardDateLoomShiftWiseList);
        }
            return "rewardHierarchyBasedForm";

    }*/
    /*    @GetMapping("/processEmployeeWiseForm")
    public String processEmployeeWiseForm(@RequestParam(value = "empNo") String empNo,
                                          @RequestParam(value = "startdate") String startdate,
                                          @RequestParam(value = "enddate") String enddate, Model model) throws ParseException {
        System.out.println(empNo+startdate+enddate);
        if(empNo.equals("") || startdate.equals("")||enddate.equals(""))
        {
           // List<String> empList = feltProdLoomWeaverRepository.findEmpId("");
            List<FeltProdLoomWeaver> feltProdLoomWeavers = feltProdLoomWeaverRepository.findEmpList();
            List<String> empList = new ArrayList<>();
            for (FeltProdLoomWeaver f:feltProdLoomWeavers) {
                empList.add(f.getEmpNo()+"-"+f.getEmpName());
            }
            model.addAttribute("empList",empList);
            model.addAttribute("message","Please select Employee Id/Date");
            model.addAttribute("alertClass", "alert-danger");
        }
        else
        {
            String s[] = empNo.split("-");
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
            LocalDate sd = new java.sql.Date(startDate.getTime()).toLocalDate();
            LocalDate ed = new java.sql.Date(endDate.getTime()).toLocalDate();
            weaverRewardListEmpWise = rewardDateLoomShiftRepository.findByEmpNo(s[0], sd, ed);
            double totalReward = 0;
            int i = 1;
            for (WeaverRewardDateLoomShiftWise w:weaverRewardListEmpWise) {
                totalReward = totalReward+w.getRewardAmount();
                LocalDate d = w.getRewardDate();
                String formattedDate = d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                w.setDateInString(formattedDate);
                w.setSrNo(i);
                i++;
            }
            model.addAttribute("empNo", empNo);
         //   List<String> empList = feltProdLoomWeaverRepository.findEmpId("");
            List<FeltProdLoomWeaver> feltProdLoomWeavers = feltProdLoomWeaverRepository.findEmpList();
            List<String> empList = new ArrayList<>();
            for (FeltProdLoomWeaver f:feltProdLoomWeavers) {
                empList.add(f.getEmpNo()+"-"+f.getEmpName());
            }
            empList.remove(empNo);
            empList.add(0, empNo);
            model.addAttribute("empList", empList);
            model.addAttribute("startdate", startdate);
            model.addAttribute("enddate", enddate);
            model.addAttribute("totalReward", totalReward);
            model.addAttribute("weaverRewardList", weaverRewardListEmpWise);
        }
        return "rewardEmployeeWiseForm";
    }*/
}



