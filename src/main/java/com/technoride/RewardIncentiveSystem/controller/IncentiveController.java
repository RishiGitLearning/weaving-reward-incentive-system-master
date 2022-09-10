package com.technoride.RewardIncentiveSystem.controller;

import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;
import com.technoride.RewardIncentiveSystem.entity.*;
import com.technoride.RewardIncentiveSystem.entity.EmpMonthlyIncentiveAttendance;
import com.technoride.RewardIncentiveSystem.model.EmpIncAttendanceModel;
import com.technoride.RewardIncentiveSystem.model.EmployeeIncentiveModel;
import com.technoride.RewardIncentiveSystem.model.LoggedUser;
import com.technoride.RewardIncentiveSystem.repository.*;
import com.technoride.RewardIncentiveSystem.utility.ExportToExcel;
import com.technoride.RewardIncentiveSystem.utility.TranslateNumberToWords;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
public class IncentiveController {
    @Value("${daysOfMonth}")
    private int daysOfMonth;
    @Autowired
    private DesignationwiseRateRepository designationwiseRateRepository;

    @Autowired
    private MonthTotalWeavingProdRepository monthTotalWeavingProdRepository;

    @Autowired
    private MonthlyAttendanceRepository monthlyAttendanceRepository;

    @Autowired
    private FeltProdLoomWeaverRepository feltProdLoomWeaverRepository;

    @Autowired
    private PtaxMonthRepository ptaxMonthRepository;

    @Autowired
    private EmployeeIncentiveRepository employeeIncentiveRepository;

    @Autowired
    private IncentiveLockRepository incentiveLockRepository;

    @Autowired
    private EmployeeIncentiveDailyDetailsRepository employeeIncentiveDailyDetailsRepository;

    @Autowired
    private CompanyUserRepository companyUserRepository;

    @Autowired
    private DepartmentIncentiveCriteriaRepository departmentIncentiveCriteriaRepository;

    @Autowired
    private EmployeeIncentiveDetailsRepository employeeIncentiveDetailsRepository;

    @Autowired
    private EmpMonthlyIncentiveAttendanceRepository empMonthlyIncentiveAttendanceRepository;
    @Autowired
    private ExportToExcel exportToExcel;
    @Autowired
    private TranslateNumberToWords translateNumberToWords;

    List<String> months1 = new ArrayList<>();
    List<String> yearList = new ArrayList<>();
    List<EmployeeIncentive> employeeIncentiveList = new ArrayList<>();
    List<EmployeeIncentiveDetails> employeeIncentiveDetailsList = new ArrayList<>();
    List<String> months = Arrays.asList("January", "February", "March", "April", "May","June","July","August","September","October","November","December");

    String ctryCd = "IN";
    String lang = "en";

    @GetMapping("/incentive")
    public String getIncentive(Model model)
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
       return "incentive";
    }


    @GetMapping("/monthlyIncentive")
    public String findMonthlyIncentive(Model model)
    {
        model.addAttribute("yearList", yearList);
        model.addAttribute("monthList",months);
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        return "monthlyIncentive";
    }

    List<EmployeeIncentiveModel> employeeIncentiveModelList = new ArrayList<>();
    String yearSelected = "2021";
    String monthSelected = "January";



    @GetMapping("/incentiveEmployeewise")
    public String getIncentiveEmployeewise(Model model)
    {
        List<MonthlyAttendance> empMonthlyList = monthlyAttendanceRepository.findEmpMonthly("","");
        List<String> empList = new ArrayList<>();
        for (MonthlyAttendance e:empMonthlyList) {

            String s = e.getMasEmpid()+"-"+e.getMasEmpname();
            empList.add(s);
        }
       // System.out.println("EmpList"+empList);
        model.addAttribute("empList",empList);
        model.addAttribute("yearList", yearList);
        model.addAttribute("monthList",months);
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        return "incentiveEmployeewise";
    }


    String empNoSelInc = "";
    String yearSelInc = "";
    String monthSelInc = "";

    @PostMapping("/exportIncentiveEmployeewise")
    public String exportIncentiveEmployeewise(@RequestParam(value = "frmSubmit") String format, HttpServletResponse response, Model model) throws IOException, DocumentException {
        System.out.println("WeaverLoomList"+employeeIncentiveDetailsList1);
        String heading = "";
        LocalDate cDate = LocalDate.now();
        String currentDate = cDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String templateName = "EmployeewiseIncentive";
        String fileFormat = "pdf";
        String empId = "";
        String month = "";
        int year = 1950;
        if(format.contains("Pdf"))
        {
            fileFormat = "pdf";
        }
        else if(format.contains("Excel"))
        {
            fileFormat = "excel";
        }
        int totalIncentive = 0;
       // EmployeeIncentiveDetails employeewiseIncentive = employeeIncentiveDetailsList1.get(0);
        if(employeeIncentiveDetailsList1.size()>0){
            List<List<String>> excelValues = new ArrayList<>();
            List<String> colHeaderList = Stream.of("Emp No", "Emp Name", "Monthly Basic Rate", "Attendance Days", "Basic rate Per Day", "Actual Basic Amount", "Incentive Amount", "Total Incentive Amount", "Round").collect(Collectors.toList());
            excelValues.add(colHeaderList);
        for (EmployeeIncentiveDetails employeewiseIncentive:employeeIncentiveDetailsList1) {
            empId = employeewiseIncentive.getEmpId();
            int m = employeewiseIncentive.getIncMonth() - 1;
            month = months.get(m);
            year = employeewiseIncentive.getIncYear();
            List<String> valueList = new ArrayList<>();
            valueList.add(employeewiseIncentive.getEmpId());
            valueList.add(employeewiseIncentive.getEmpName());
            valueList.add(String.valueOf(employeewiseIncentive.getMonthlyBasicRate()));
            valueList.add(String.valueOf(employeewiseIncentive.getAttendance()));
            valueList.add(String.valueOf(employeewiseIncentive.getBasicRatePerDay()));
            valueList.add(String.valueOf(employeewiseIncentive.getActualBasicAmount()));
            valueList.add(String.valueOf(employeewiseIncentive.getIncentive()));
            valueList.add(String.valueOf(employeewiseIncentive.getTotalIncentiveAmount()));
            valueList.add(String.valueOf(employeewiseIncentive.getRound()));
            excelValues.add(valueList);
            totalIncentive = totalIncentive+employeewiseIncentive.getRound();
        }
            List<String> valueList = new ArrayList<>();
            valueList.add("");
            valueList.add("TOTAL");
            valueList.add("");
            valueList.add("");
            valueList.add("");
            valueList.add("");
            valueList.add("");
            valueList.add("");
            valueList.add(String.valueOf(totalIncentive));
            excelValues.add(valueList);
            String result = translateNumberToWords.translate(ctryCd, lang, String.valueOf(totalIncentive));
            heading = "FELT WEAVING INCENTIVE                                                                                                                              Date: " + currentDate + "&EMPLOYEE NO.: " + empId + "                                                                                                                     MONTH : " + month+"-"+year;
            String pdfReportName = "employeewiseIncentive&TOTAL INCENTIVE: Rupees "+result+" only";
            exportToExcel.doExcel(templateName, fileFormat, excelValues, pdfReportName, heading, "Incentive_" + empId + "_" + month + "_" + year, response);
        }
        else{
        }
            model.addAttribute("yearList", yearList);
            model.addAttribute("monthList",months);
        return "incentiveEmployeewise";
    }
    @PostMapping("/exportIncentiveData")
    public String exportIncentiveData(@RequestParam(value = "frmSubmit") String format, HttpServletResponse response, Model model) throws IOException, DocumentException {
        System.out.println("WeaverLoomList"+employeeIncentiveDetailsList);
        String heading = "";
        String templateName = "MonthlyIncentive";
        String fileFormat = "pdf";
        if(format.contains("Pdf"))
        {
            fileFormat = "pdf";
        }
        else if(format.contains("Excel"))
        {
            fileFormat = "excel";
        }
        int totalIncentive = 0;
        double totalAttendance = 0;
        double totalActualBasic = 0;
        double totalincentiveAmount = 0;
        double totalincAmount = 0;
        int totalRound = 0;
        if(employeeIncentiveDetailsList.size()>0) {
            int m = employeeIncentiveDetailsList.get(0).getIncMonth() - 1;
            String month = months.get(m);
            int year = employeeIncentiveDetailsList.get(0).getIncYear();
            List<String> colHeaderList = Stream.of("SrNo","Pay Cat","Emp No", "Emp Name", "Monthly Basic Rate", "Attendance Days", "Basic rate Per Day", "Actual Basic Amount", "Incentive Amount", "Total Incentive Amount", "Round").collect(Collectors.toList());
            List<List<String>> excelValues = new ArrayList<>();
            excelValues.add(colHeaderList);
            int i = 1;
            for (EmployeeIncentiveDetails w : employeeIncentiveDetailsList) {
                List<String> valueList = new ArrayList<>();
                valueList.add(String.valueOf(i));
                valueList.add(w.getPayCat());
                valueList.add(w.getEmpId());
                valueList.add(w.getEmpName());
                valueList.add(String.valueOf(w.getMonthlyBasicRate()));
                valueList.add(String.valueOf(w.getAttendance()));
                valueList.add(String.valueOf(w.getBasicRatePerDay()));
                valueList.add(String.valueOf(w.getActualBasicAmount()));
                valueList.add(String.valueOf(w.getIncentive()));
                valueList.add(String.valueOf(w.getTotalIncentiveAmount()));
                valueList.add(String.valueOf((int)w.getRound()));
                excelValues.add(valueList);
                totalAttendance = totalAttendance + w.getAttendance();
                totalActualBasic = totalActualBasic + w.getActualBasicAmount();
                totalincentiveAmount = totalincentiveAmount + w.getIncentive();
                totalincAmount = totalincAmount + w.getTotalIncentiveAmount();
                totalIncentive = totalIncentive+w.getRound();
                i++;
            }
            List<String> valList = new ArrayList<>();
            valList.add("");
            valList.add("");
            valList.add("");
            valList.add("Total");
            valList.add("");
            valList.add(String.valueOf(Precision.round(totalAttendance/2,2)));
            valList.add("");
            valList.add(String.valueOf(Precision.round((totalActualBasic/2),2)));
            valList.add(String.valueOf(Precision.round((totalincentiveAmount/2),2)));
            valList.add(String.valueOf(Precision.round((totalincAmount/2),2)));
            valList.add(String.valueOf((int)Precision.round((totalIncentive/2),2)));
            excelValues.add(valList);
            String result = translateNumberToWords.translate(ctryCd, lang, String.valueOf((int)(totalIncentive/2)));
            heading = "FELT WEAVING INCENTIVE REPORT                                                                                                                                    "+month+"-"+year+"&1 TOTAL PRODUCTION :-"+totalProduction+"&2 TOTAL INCENTIVE FOR THE DEPT :-"+(int)totalIncForDept+"&3 TOTAL BASIC AMOUNT :-"+totalBasicAmount+"&4 INCENTIVE AMT. PER RP OF BAS AMT:-"+incentiveAmountPer+"&5 STD DAYS :-"+daysOfMonth;
           String pdfReportName = "monthlyIncentive&TOTAL INCENTIVE: Rupees "+result+" only";
            exportToExcel.doExcel(templateName, fileFormat, excelValues,pdfReportName , heading, "Incentive_"+month+"_"+year, response);
        }
        else{
            model.addAttribute("yearList", yearList);
            model.addAttribute("monthList",months);
        }
        return "monthlyIncentive";
    }


    public double calculatePtax(double incentive, String empId, int month, int year)
    {
        double incPtax=0;
        List<PtaxMonth> ptaxMonth = ptaxMonthRepository.findEmpGross(empId,month,year);
        if(!ptaxMonth.isEmpty()) {
            double salary = ptaxMonth.get(0).getPtSalaryGross();
            double ptax = ptaxMonth.get(0).getPtSalaryPtax();
            double reward = ptaxMonth.get(0).getPtWvrRewardGross();
            double rewardPtax = ptaxMonth.get(0).getPtWvrRewardPtax();
            double updatedPtax = 0;
            double salaryReward = salary + reward + incentive;
            /*if (salaryReward > 0 && salaryReward <= 6000) {
                updatedPtax = 0;
            } else if (salaryReward >= 6001 && salaryReward <= 9000) {
                updatedPtax = 80;
            } else if (salaryReward >= 9001 && salaryReward <= 12000) {
                updatedPtax = 150;
            } else if (salaryReward >= 12001) {
                updatedPtax = 200;
            }*/
            if (salaryReward > 0 && salaryReward <= 12000) {
                updatedPtax = 0;
            } else if (salaryReward > 12000) {
                updatedPtax = 200;
            }
            double pt = ptax+rewardPtax;
            if (updatedPtax > pt) {
                incPtax = updatedPtax - pt;
            }
        }
        return incPtax;
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
    }

    @GetMapping("/incentiveUnlock")
    public String geIncentiveUnlock(Model model)
    {
      //  String userId = LoggedUser.getInstance().getUserId();
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
            List<String> yearList1 = yearList;
            yearList1.remove(yearSelected);
            yearList1.add(0, yearSelected);
            model.addAttribute("yearList", yearList1);
            List<String> months2 = months1;
            months2.remove(monthSelected);
            months2.add(0, monthSelected);
            model.addAttribute("monthList", months2);
            return "incentiveUnlock";
        }
        else {
            model.addAttribute("message", "You are not authorised user to unlock");
            model.addAttribute("alertClass", "alert-danger");
            return "incentive";
        }
    }

    @GetMapping("/processIncentiveUnlock")
    public String processIncentiveUnlock(@RequestParam(value = "yearSel") String yearSel,
                                         @RequestParam(value = "monthSel") String monthSel,
                                         Model model)
    {
        int index = months.indexOf(monthSel);
        System.out.println("Index" + index);
        int month = index;
        int pickMonth = month + 1;
        int pickYear = Integer.parseInt(yearSel);
        IncentiveLock incentiveLock = incentiveLockRepository.findByMonthAndYear(pickMonth, pickYear);
        List<String> empNoList = monthlyAttendanceRepository.findEmpListForInc(pickMonth, pickYear, "");
        if(incentiveLock != null && incentiveLock.isIncentiveFlag())
        {
            employeeIncentiveRepository.deleteEmpIncentive(pickMonth, pickYear);
            double incentive = 0;
            double incentiveGross = 0;
            for (String empNo:empNoList) {
                ptaxMonthRepository.updateIncentivePtax(incentive, incentiveGross, empNo, pickMonth, pickYear);
                employeeIncentiveDailyDetailsRepository.deleteEmpInc(pickMonth, pickYear);
                employeeIncentiveDetailsRepository.deleteByMonthYear(pickMonth, pickYear);
            }

            incentiveLockRepository.updateIncentiveFlag(false, false, pickMonth, pickYear);
        }
        model.addAttribute("message", "Incentive Unlocked");
        model.addAttribute("alertClass", "alert-success");
        List<String> yearList1 = yearList;
        yearList1.remove(yearSel);
        yearList1.add(0,yearSel);
        model.addAttribute("yearList", yearList1);
        List<String> months2 = months1;
        months2.remove(monthSel);
        months2.add(0,monthSel);
        model.addAttribute("monthList",months2);
        return "incentiveUnlock";
    }
    @GetMapping("/incentiveEmployeewiseDetails")
    public String getIncentiveEmployeewiseDetails(Model model)
    {
        model.addAttribute("empNo", empNoSelInc);
        List<MonthlyAttendance> empMonthlyList = monthlyAttendanceRepository.findEmpMonthly("","");
        List<String> empList = new ArrayList<>();
        for (MonthlyAttendance e:empMonthlyList) {

            String s = e.getMasEmpid()+"-"+e.getMasEmpname();
            empList.add(s);
        }
        System.out.println("EmpList"+empList);
        empList.remove(empNoSelInc);
        empList.add(0,empNoSelInc);
        model.addAttribute("empList",empList);
        yearList.remove(yearSelInc);
        yearList.add(0,yearSelInc);
        months1.remove(monthSelInc);
        months1.add(0,monthSelInc);
        model.addAttribute("yearList",yearList);
        model.addAttribute("monthList",months1);
      //  model.addAttribute("emp",empNoSelInc);
      //  model.addAttribute("year", yearSelInc);
      //  model.addAttribute("month",monthSelInc);
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        return "incentiveEmployeewiseDetails";
    }

    double totalProduction =0;
    double incentiveAmount =0;
    double totalIncForDept =0;
    double totalBasicAmount =0;
    double incentiveAmountPer =0;

    @Autowired
    private RewardLockRepository rewardLockRepository;
    @RequestMapping(value="/index/processMonthlyIncentive1", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String calculateMonthlyIncentive1(@RequestParam(value = "yearSel") String yearSel,
                                            @RequestParam(value = "monthSel") String monthSel) throws ParseException {

        employeeIncentiveDetailsList.clear();
        String rewardF = "true";
        System.out.println("Year and month sel" + yearSel + "::" + monthSel);
        String messageAlert = "";
        int index = months.indexOf(monthSel);
        System.out.println("Index" + index);
        int month = index;
        int pickMonth = month + 1;
        int pickYear = Integer.parseInt(yearSel);
        int totalMonthIncentive = 0;
        IncentiveLock incentiveLock = incentiveLockRepository.findByMonthAndYear(pickMonth, pickYear);
        if (incentiveLock != null && incentiveLock.isIncentiveFlag()) {
            System.out.println("Please press unlock");
            messageAlert = "Incentive already calculated";
            yearSelected = yearSel;
            monthSelected = monthSel;
            int i = 1;
            int ttlIncRound = 0;
            List<String> empNoList = monthlyAttendanceRepository.findEmpListForInc(pickMonth, pickYear, "");
            for (String empId : empNoList) {
                List<EmployeeIncentiveDetails> employeeIncentiveDetails = employeeIncentiveDetailsRepository.findByMonthAndYearAndId(pickMonth, pickYear, "R", empId);
                if (employeeIncentiveDetails.size() > 0) {
                    double totalAttendance = 0;
                    double actaulBasicAmount = 0;
                    double incTotal = 0;
                    double totalincAmount = 0;
                    double totalRound = 0;
                    for (EmployeeIncentiveDetails e2 : employeeIncentiveDetails) {
                        employeeIncentiveDetailsList.add(e2);
                        totalAttendance = totalAttendance + e2.getAttendance();
                        actaulBasicAmount = actaulBasicAmount + e2.getActualBasicAmount();
                        incTotal = incTotal + e2.getIncentive();
                        totalincAmount = totalincAmount + e2.getTotalIncentiveAmount();
                        totalRound = totalRound + e2.getRound();
                        ttlIncRound = ttlIncRound + e2.getRound();
                    }
                    EmployeeIncentiveDetails e3 = new EmployeeIncentiveDetails();
                    e3.setPayCat("R");
                    e3.setEmpId(empId);
                    e3.setEmpName("TOTAL");
                    e3.setAttendance(Precision.round(totalAttendance, 2));
                    e3.setActualBasicAmount(Precision.round(actaulBasicAmount, 2));
                    e3.setIncentive(Precision.round(incTotal, 2));
                    e3.setTotalIncentiveAmount(Precision.round(totalincAmount, 2));
                    e3.setRound((int) totalRound);
                    employeeIncentiveDetailsList.add(e3);
                }
            }

            for (String empId : empNoList) {
                List<EmployeeIncentiveDetails> employeeIncentiveDetails = employeeIncentiveDetailsRepository.findByMonthAndYearAndId(pickMonth, pickYear, "V", empId);
                if (employeeIncentiveDetails.size() > 0) {
                    double totalAttendance = 0;
                    double actaulBasicAmount = 0;
                    double incTotal = 0;
                    double totalincAmount = 0;
                    double totalRound = 0;
                    for (EmployeeIncentiveDetails e2 : employeeIncentiveDetails) {
                        employeeIncentiveDetailsList.add(e2);
                        totalAttendance = totalAttendance + e2.getAttendance();
                        actaulBasicAmount = actaulBasicAmount + e2.getActualBasicAmount();
                        incTotal = incTotal + e2.getIncentive();
                        totalincAmount = totalincAmount + e2.getTotalIncentiveAmount();
                        totalRound = totalRound + e2.getRound();
                        ttlIncRound = ttlIncRound + e2.getRound();
                    }
                    EmployeeIncentiveDetails e3 = new EmployeeIncentiveDetails();
                    e3.setPayCat("V");
                    e3.setEmpId(empId);
                    e3.setEmpName("TOTAL");
                    e3.setAttendance(Precision.round(totalAttendance, 2));
                    e3.setActualBasicAmount(Precision.round(actaulBasicAmount, 2));
                    e3.setIncentive(Precision.round(incTotal, 2));
                    e3.setTotalIncentiveAmount(Precision.round(totalincAmount, 2));
                    e3.setRound((int) totalRound);
                    employeeIncentiveDetailsList.add(e3);
                }
            }

         /*   List<EmployeeIncentiveDetails> employeeIncentiveDetails = new ArrayList<>();
            employeeIncentiveDetails = employeeIncentiveDetailsRepository.findByMonthAndYear(pickMonth,pickYear,"R");
            List<EmployeeIncentiveDetails> employeeIncentiveDetails1 = new ArrayList<>();
            employeeIncentiveDetails1 = employeeIncentiveDetailsRepository.findByMonthAndYear(pickMonth,pickYear,"V");
            for(EmployeeIncentiveDetails e:employeeIncentiveDetails)
            {
                employeeIncentiveDetailsList.add(e);
            }
            for (EmployeeIncentiveDetails e1:employeeIncentiveDetails1)
            {
                employeeIncentiveDetailsList.add(e1);
            }*/
         //   employeeIncentiveDetailsList = employeeIncentiveDetailsRepository.findByMonthAndYear(pickMonth, pickYear);
            for (EmployeeIncentiveDetails emp:employeeIncentiveDetailsList) {
                emp.setSrNo(i);
              //  totalMonthIncentive = totalMonthIncentive+emp.getRound();
                i++;
            }
            totalMonthIncentive=ttlIncRound;
            MonthTotalWeavingProduction monthTotalWeavingProduction = monthTotalWeavingProdRepository.getTotalWeavingProd(pickMonth, pickYear);
            double totalMonthProd = Precision.round(monthTotalWeavingProduction.getPickKg(),3);
            double totalDeptIncentive = findTotalDeptIncentive(totalMonthProd);
            totalProduction = totalMonthProd;
            totalIncForDept = totalDeptIncentive;
            totalBasicAmount = Precision.round(employeeIncentiveDetailsRepository.findActualBasicAmount(pickMonth,pickYear),3);
            incentiveAmountPer = Precision.round(totalIncForDept/totalBasicAmount,6);
        } else if (incentiveLock == null || !incentiveLock.isIncentiveFlag()) {
            RewardLock rewardLock = rewardLockRepository.findByMonthAndYear(pickMonth, pickYear);
            if (rewardLock != null && rewardLock.isRewardFlag()){
                System.out.println("Inside inc calculation");
            try {
                employeeIncentiveModelList.clear();
                MonthTotalWeavingProduction monthTotalWeavingProduction = monthTotalWeavingProdRepository.getTotalWeavingProd(pickMonth, pickYear);
                double totalMonthProd = Precision.round(monthTotalWeavingProduction.getPickKg(), 3);
                System.out.println("Total month prod" + totalMonthProd);
                double totalDeptIncentive = findTotalDeptIncentive(totalMonthProd);
                System.out.println("TotalDeptIncentive" + totalDeptIncentive);
                totalProduction = totalMonthProd;
                totalIncForDept = totalDeptIncentive;

                System.out.println("*******");
                int empcount = 0;
                List<String> empNoList = monthlyAttendanceRepository.findEmpListForInc(pickMonth, pickYear, "");
                System.out.println("EmpNoList" + empNoList.size());
                double totalDeptBasicAmt = 0;
                employeeIncentiveRepository.deleteEmpIncentive(pickMonth, pickYear);
                double incentive = 0;
                double incentiveGross = 0;
                for (String empNo : empNoList) {
                    ptaxMonthRepository.updateIncentivePtax(incentive, incentiveGross, empNo, pickMonth, pickYear);
                    employeeIncentiveDailyDetailsRepository.deleteEmpInc(pickMonth, pickYear);
                    employeeIncentiveDetailsRepository.deleteByMonthYear(pickMonth, pickYear);
                }

                for (String empId : empNoList) {
                   //    String empId = "BRD306656";
                    double actualWorkingPerDay = 0;
                    double workerPerDayBasic = 0;
                    //if (!empId.equals("")) {
                    empcount++;
                    String empVType = "false";
                    System.out.printf("Emp: " + empId + "\n");
                    List<MonthlyAttendance> monthlyAttendanceList = monthlyAttendanceRepository.findMonthAttendance("", empId, pickMonth, pickYear);
                    System.out.println("monthlyAttendanceList" + monthlyAttendanceList);
                    MonthlyAttendance monthlyAttendance = new MonthlyAttendance();
                    MonthlyAttendance monthlyAttendanceV = new MonthlyAttendance();
                    for (MonthlyAttendance m : monthlyAttendanceList) {
                        if (m.getPayCatg().equals("R")) {
                            monthlyAttendance = m;
                        }
                        if (m.getPayCatg().equals("V")) {
                            monthlyAttendanceV = m;
                            empVType = "true";
                        }
                    }
                    double presentDays = Precision.round(monthlyAttendance.getPresentDays(), 3);
                    double rokdiDays = Precision.round(monthlyAttendance.getRokdiDays(), 3);
                    double attendanceDay = Precision.round(monthlyAttendance.getPresentDays() + monthlyAttendance.getRokdiDays(), 3);


                    System.out.println("presentDays Days" + presentDays);
                    System.out.println("rokdiDays Days" + rokdiDays);

                    System.out.println("Attendance Days" + attendanceDay);

                    List<DesignationwiseRate> drtList = designationwiseRateRepository.findRateByGrade(monthlyAttendance.getDesignationGrade());
                    System.out.println("drtList Days" + drtList);
                    double designationRt = drtList.get(0).getRate();
                    //   List<FeltProdLoomWeaver> feltProdLoomWeaverList = feltProdLoomWeaverRepository.findEmpByIdMonthYear(empId, pickMonth, pickYear, "", "", "", "", "", "");
                    List<FeltProdLoomWeaver> feltProdLoomWeaverList = feltProdLoomWeaverRepository.findEmpByIdMonthYearList(empId, pickMonth, pickYear, "", "", "");
                    double presentD = 0;
                    double totalPresent = 0;
                    double totalRokdi = 0;
                    double woringPerDay = 0;
                    String empName = monthlyAttendance.getMasEmpname();
                    if (feltProdLoomWeaverList.size() > 0) {
                        for (FeltProdLoomWeaver fw : feltProdLoomWeaverList) {
                            //   if (fw.getPresentStatus().equals("PP")) {
                            double cr = fw.getRokdi() + fw.getPresent();
                            if (cr > 0) {
                                totalPresent = totalPresent + fw.getPresent();
                                totalRokdi = totalRokdi + fw.getRokdi();
                                presentD = presentD + fw.getPresent() + fw.getRokdi();
                                double categoryGrade = fw.getCategoryGrade();
                                double designationGrade = fw.getDesignationGrade();
                                double dailyBasicRate = 0;
                                double perDayRate = 0;
                                double selectionGrade = 0;
                                if (categoryGrade <= designationGrade) {
                                    selectionGrade = categoryGrade;
                                    List<DesignationwiseRate> desRateList = designationwiseRateRepository.findRateByGrade(selectionGrade);
                                    dailyBasicRate = desRateList.get(0).getRate();
                                    perDayRate = dailyBasicRate / 26;
                                } else {
                                    selectionGrade = designationGrade;
                                    List<DesignationwiseRate> desRateList = designationwiseRateRepository.findRateByGrade(selectionGrade);
                                    dailyBasicRate = desRateList.get(0).getRate();
                                    perDayRate = dailyBasicRate / 26;
                                }
                                woringPerDay = Precision.round(woringPerDay + (perDayRate * fw.getPresent()), 3);
                                EmployeeIncentiveDailyDetails employeeIncentiveDailyDetails = new EmployeeIncentiveDailyDetails();
                                employeeIncentiveDailyDetails.setEmpId(empId);
                                employeeIncentiveDailyDetails.setEmpName(empName);
                                employeeIncentiveDailyDetails.setIncDate(fw.getDocDate().toLocalDate());
                                employeeIncentiveDailyDetails.setIncDesignation(String.valueOf(designationGrade));
                                employeeIncentiveDailyDetails.setIncCategory(String.valueOf(categoryGrade));
                                employeeIncentiveDailyDetails.setIncSelectionGrade(String.valueOf(selectionGrade));
                                employeeIncentiveDailyDetails.setIncDailyRate(dailyBasicRate);
                                employeeIncentiveDailyDetails.setIncMonth(pickMonth);
                                employeeIncentiveDailyDetails.setIncYear(pickYear);
                                employeeIncentiveDailyDetails.setPresentrokadi(cr);
                                if (fw.getRokdi() > 0) {
                                    employeeIncentiveDailyDetails.setRokdi(fw.getRokdi());
                                } else if (fw.getPresent() > 0) {
                                    employeeIncentiveDailyDetails.setPresent(fw.getPresent());
                                }
                                employeeIncentiveDailyDetailsRepository.save(employeeIncentiveDailyDetails);
                            }
                        }
                    }
                    List<List<String>> attendanceCountWithRate = employeeIncentiveDailyDetailsRepository.findByEmpNoAndInceRate(empId, pickMonth, pickYear);
                    System.out.println("output" + attendanceCountWithRate);
                    double temp = 0;
                    for (List<String> s : attendanceCountWithRate) {
                        System.out.println("INcrate" + s.get(0) + "Atten" + s.get(1));
                        double incRate = Double.valueOf(s.get(0));
                        double attendaceCount = Double.valueOf(s.get(1));
                        EmployeeIncentiveDetails employeeIncentiveDetails = new EmployeeIncentiveDetails();
                        employeeIncentiveDetails.setEmpId(empId);
                        employeeIncentiveDetails.setEmpName(empName);
                        employeeIncentiveDetails.setMonthlyBasicRate(incRate);
                        employeeIncentiveDetails.setAttendance(Precision.round(attendaceCount, 2));
                        double worKingPerDay = Precision.round(incRate / daysOfMonth, 4);
                        employeeIncentiveDetails.setBasicRatePerDay(worKingPerDay);
                        double actualBasicAmount = Precision.round(attendaceCount * worKingPerDay, 2);
                        employeeIncentiveDetails.setActualBasicAmount(actualBasicAmount);
                        totalDeptBasicAmt = Precision.round(totalDeptBasicAmt + actualBasicAmount, 4);
                        employeeIncentiveDetails.setIncMonth(pickMonth);
                        employeeIncentiveDetails.setIncYear(pickYear);
                        employeeIncentiveDetails.setPayCat("R");
                        employeeIncentiveDetailsRepository.save(employeeIncentiveDetails);
                    }

                    double attendanceDiff = 0;
                    double presentDiff = 0;
                    double rokdiDiff = 0;
                    if (presentD < attendanceDay) {
                        attendanceDiff = attendanceDay - presentD;
                        if (totalPresent < presentDays) {
                            presentDiff = presentDays - totalPresent;
                        }
                        if (totalRokdi < rokdiDays) {
                            rokdiDiff = rokdiDays - totalRokdi;
                        }

                        //   woringPerDay = woringPerDay + ((designationRt / 26) * attendanceDiff);
                        woringPerDay = Precision.round(designationRt / 26, 2) * attendanceDiff;
                        EmployeeIncentiveDetails employeeIncentiveDetails = new EmployeeIncentiveDetails();
                        employeeIncentiveDetails.setEmpId(empId);
                        employeeIncentiveDetails.setEmpName(empName);
                        employeeIncentiveDetails.setMonthlyBasicRate(designationRt);
                        employeeIncentiveDetails.setAttendance(Precision.round(attendanceDiff, 2));
                        double worKingPerDay = Precision.round(designationRt / daysOfMonth, 4);
                        employeeIncentiveDetails.setBasicRatePerDay(worKingPerDay);
                        double actualBasicAmount = Precision.round(attendanceDiff * worKingPerDay, 2);
                        employeeIncentiveDetails.setActualBasicAmount(actualBasicAmount);
                        totalDeptBasicAmt = Precision.round(totalDeptBasicAmt + actualBasicAmount, 4);
                        employeeIncentiveDetails.setIncMonth(pickMonth);
                        employeeIncentiveDetails.setIncYear(pickYear);
                        employeeIncentiveDetails.setPayCat("R");
                        employeeIncentiveDetailsRepository.save(employeeIncentiveDetails);
                    }
                    if (empVType.equals("true")) {
                        double attendanceDay1 = Precision.round(monthlyAttendanceV.getPresentDays() + monthlyAttendanceV.getRokdiDays(), 2);
                        System.out.println("empVType Attendance Days" + attendanceDay1);
                        woringPerDay = Precision.round(designationRt / 26, 2) * attendanceDay1;
                        EmployeeIncentiveDetails employeeIncentiveDetails = new EmployeeIncentiveDetails();
                        employeeIncentiveDetails.setEmpId(empId);
                        employeeIncentiveDetails.setEmpName(empName);
                        employeeIncentiveDetails.setMonthlyBasicRate(designationRt);
                        employeeIncentiveDetails.setAttendance(Precision.round(attendanceDay1, 2));
                        double worKingPerDay = Precision.round(designationRt / daysOfMonth, 4);
                        employeeIncentiveDetails.setBasicRatePerDay(worKingPerDay);
                        double actualBasicAmount = Precision.round(attendanceDay1 * worKingPerDay, 2);
                        employeeIncentiveDetails.setActualBasicAmount(actualBasicAmount);
                        totalDeptBasicAmt = Precision.round(totalDeptBasicAmt + actualBasicAmount, 3);
                        employeeIncentiveDetails.setIncMonth(pickMonth);
                        employeeIncentiveDetails.setIncYear(pickYear);
                        employeeIncentiveDetails.setPayCat("V");
                        employeeIncentiveDetailsRepository.save(employeeIncentiveDetails);
                    }

                }
                System.out.println("EmpCount" + empcount);

                System.out.println("TotalDeptBasicAmt" + totalDeptBasicAmt);
                double totalDeptIncentivePerPerson = Precision.round(totalDeptIncentive / totalDeptBasicAmt, 6);
                System.out.println("TotalDeptIncPerPerson" + totalDeptIncentivePerPerson);
                totalBasicAmount = totalDeptBasicAmt;
                incentiveAmountPer = totalDeptIncentivePerPerson;
                for (String empId : empNoList) {
                    List<EmployeeIncentiveDetails> employeeIncentiveDetailsList = employeeIncentiveDetailsRepository.findByEmpNoMonthYear(empId, pickMonth, pickYear);
                    if (employeeIncentiveDetailsList.size() > 0) {
                        double totalIncentive = 0;
                        for (EmployeeIncentiveDetails e : employeeIncentiveDetailsList) {
                            double actaulBasicAmount = e.getActualBasicAmount();
                            double incentive1 = actaulBasicAmount * totalDeptIncentivePerPerson;
                            e.setIncentive(Precision.round(incentive1, 2));
                            totalIncentive = totalIncentive + incentive1;
                            employeeIncentiveDetailsRepository.save(e);
                            System.out.println("EMPINCDETAIL saved" + empId);
                            e.setTotalDeptIncentive(totalDeptIncentive);
                            e.setTotalDeptBasicAmt(totalDeptBasicAmt);
                        }
                        EmployeeIncentiveDetails e1 = employeeIncentiveDetailsList.get(0);
                        e1.setTotalIncentiveAmount(Precision.round(totalIncentive, 2));
                        e1.setRound((int) (Precision.round(totalIncentive, 0)));
                        employeeIncentiveDetailsRepository.save(e1);
                        System.out.println("EMP INC DETAIL incentive saved" + empId);
                        EmployeeIncentive employeeIncentive = new EmployeeIncentive();
                        employeeIncentive.setEmpId(empId);
                        employeeIncentive.setEmpName(e1.getEmpName());
                        employeeIncentive.setTotalIncentiveAmount(Precision.round(totalIncentive, 2));
                        employeeIncentive.setRound((int) (Precision.round(totalIncentive, 0)));

                        PtaxMonth ptaxMonth = new PtaxMonth();
                        double incPtax = calculatePtax(Precision.round(totalIncentive, 0), empId, pickMonth, pickYear);
                        System.out.println("EmpId" + empId + "::" + incPtax);
                        List<PtaxMonth> ptaxMonth1 = ptaxMonthRepository.findEmpGross(empId, pickMonth, pickYear);
                        int i = ptaxMonthRepository.updateIncentivePtax(Precision.round(totalIncentive, 0), incPtax, empId, pickMonth, pickYear);
                        System.out.println("Ptax gets updated" + i);
                    }
                }


                int ttlIncRound = 0;
                for (String empId : empNoList) {
                    List<EmployeeIncentiveDetails> employeeIncentiveDetails = employeeIncentiveDetailsRepository.findByMonthAndYearAndId(pickMonth, pickYear, "R", empId);
                    if (employeeIncentiveDetails.size() > 0) {
                        double totalAttendance = 0;
                        double actaulBasicAmount = 0;
                        double incTotal = 0;
                        double totalincAmount = 0;
                        double totalRound = 0;
                        for (EmployeeIncentiveDetails e2 : employeeIncentiveDetails) {
                            employeeIncentiveDetailsList.add(e2);
                            totalAttendance = totalAttendance + e2.getAttendance();
                            actaulBasicAmount = actaulBasicAmount + e2.getActualBasicAmount();
                            incTotal = incTotal + e2.getIncentive();
                            totalincAmount = totalincAmount + e2.getTotalIncentiveAmount();
                            totalRound = totalRound + e2.getRound();
                            ttlIncRound = ttlIncRound + e2.getRound();
                        }
                        EmployeeIncentiveDetails e3 = new EmployeeIncentiveDetails();
                        e3.setPayCat("R");
                        e3.setEmpId(empId);
                        e3.setEmpName("TOTAL");
                        e3.setAttendance(Precision.round(totalAttendance, 2));
                        e3.setActualBasicAmount(Precision.round(actaulBasicAmount, 2));
                        e3.setIncentive(Precision.round(incTotal, 2));
                        e3.setTotalIncentiveAmount(Precision.round(totalincAmount, 2));
                        e3.setRound((int) totalRound);
                        employeeIncentiveDetailsList.add(e3);
                    }
                }

                for (String empId : empNoList) {
                    List<EmployeeIncentiveDetails> employeeIncentiveDetails = employeeIncentiveDetailsRepository.findByMonthAndYearAndId(pickMonth, pickYear, "V", empId);
                    if (employeeIncentiveDetails.size() > 0) {
                        double totalAttendance = 0;
                        double actaulBasicAmount = 0;
                        double incTotal = 0;
                        double totalincAmount = 0;
                        double totalRound = 0;
                        for (EmployeeIncentiveDetails e2 : employeeIncentiveDetails) {
                            employeeIncentiveDetailsList.add(e2);
                            totalAttendance = totalAttendance + e2.getAttendance();
                            actaulBasicAmount = actaulBasicAmount + e2.getActualBasicAmount();
                            incTotal = incTotal + e2.getIncentive();
                            totalincAmount = totalincAmount + e2.getTotalIncentiveAmount();
                            totalRound = totalRound + e2.getRound();
                            ttlIncRound = ttlIncRound + e2.getRound();
                        }
                        EmployeeIncentiveDetails e3 = new EmployeeIncentiveDetails();
                        e3.setPayCat("V");
                        e3.setEmpId(empId);
                        e3.setEmpName("TOTAL");
                        e3.setAttendance(Precision.round(totalAttendance, 2));
                        e3.setActualBasicAmount(Precision.round(actaulBasicAmount, 2));
                        e3.setIncentive(Precision.round(incTotal, 2));
                        e3.setTotalIncentiveAmount(Precision.round(totalincAmount, 2));
                        e3.setRound((int) totalRound);
                        employeeIncentiveDetailsList.add(e3);
                    }
                }
              /*  List<EmployeeIncentiveDetails> employeeIncentiveDetails = new ArrayList<>();
                employeeIncentiveDetails = employeeIncentiveDetailsRepository.findByMonthAndYear(pickMonth,pickYear,"R");
                List<EmployeeIncentiveDetails> employeeIncentiveDetails1 = new ArrayList<>();
                employeeIncentiveDetails1 = employeeIncentiveDetailsRepository.findByMonthAndYear(pickMonth,pickYear,"V");
                for(EmployeeIncentiveDetails e:employeeIncentiveDetails)
                {
                    employeeIncentiveDetailsList.add(e);
                }
                for (EmployeeIncentiveDetails e:employeeIncentiveDetails1)
                {
                    employeeIncentiveDetailsList.add(e);
                }*/
                // employeeIncentiveDetailsList = employeeIncentiveDetailsRepository.findByMonthAndYear(pickMonth,pickYear);
                System.out.println("SIZE" + employeeIncentiveDetailsList.size());

                int i = 1;
                for (EmployeeIncentiveDetails emp : employeeIncentiveDetailsList) {
                    emp.setSrNo(i);
                    //  totalMonthIncentive = totalMonthIncentive+emp.getRound();
                    i++;
                }
                totalMonthIncentive = ttlIncRound;
                if (incentiveLock == null) {
                    IncentiveLock incentiveLock1 = new IncentiveLock();
                    incentiveLock1.setMonth(pickMonth);
                    incentiveLock1.setYear(pickYear);
                    incentiveLock1.setIncentiveFlag(true);
                    incentiveLock1.setIncentiveLock(true);
                    incentiveLockRepository.save(incentiveLock1);
                } else if (!incentiveLock.isIncentiveLock()) {
                    incentiveLockRepository.updateIncentiveFlag(true, true, pickMonth, pickYear);
                }

            } catch (Exception e) {
                List<String> yearList1 = yearList;
                yearList1.remove(yearSel);
                yearList1.add(0, yearSel);
                List<String> months2 = months1;
                months2.remove(monthSel);
                months2.add(0, monthSel);
                return "monthlyIncentive";
            }
        }
            else{
                System.out.println("Please press unlock");
                messageAlert = "Rewards not calculated";
                rewardF = "false";
            }
        }
        List<String> yearList1 = yearList;
        yearList1.remove(yearSel);
        yearList1.add(0,yearSel);
        List<String> months2 = months1;
        months2.remove(monthSel);
        months2.add(0,monthSel);
        if(rewardF.equals("true")) {
            if (employeeIncentiveDetailsList.size() == 0) {
                messageAlert = "No Result Found";
            }
        }
        String incList = new Gson().toJson(employeeIncentiveDetailsList);
        String totalIncentive = new Gson().toJson(totalMonthIncentive);
        String messageAlt = new Gson().toJson(messageAlert);
        String bothJson = "[" + incList + "," + totalIncentive + "," + messageAlt +"]";
        System.out.println("bothjson"+bothJson);
        return bothJson;
    }
    public double findTotalDeptIncentive(double totalMonthProd)
    {
        System.out.println("Inside finding total dept inc");
       List<DepartmentIncentiveCriteria> departmentIncentiveCriteriaList = departmentIncentiveCriteriaRepository.findAll();
        double multiplyFactor =0;
       int size = departmentIncentiveCriteriaList.size();
       int s = size-1;
       double incCritetia0 = departmentIncentiveCriteriaList.get(0).getMonthlyAchievedProduction();
        if(totalMonthProd<incCritetia0){
            multiplyFactor = 0;
        }
       for (int i =0;i<size;i++){

           if(i==s)
           {
               double incCritetiaLast = departmentIncentiveCriteriaList.get(i).getMonthlyAchievedProduction();
              if(totalMonthProd > incCritetiaLast)
              {
                  multiplyFactor = departmentIncentiveCriteriaList.get(i).getFactorToMultiply();
              }
           }
           else {
               double incCriteriaFirst = departmentIncentiveCriteriaList.get(i).getMonthlyAchievedProduction();
               double incCriteriaSecond = departmentIncentiveCriteriaList.get(i+1).getMonthlyAchievedProduction();
               if(totalMonthProd > incCriteriaFirst && totalMonthProd <= incCriteriaSecond){
                   multiplyFactor = departmentIncentiveCriteriaList.get(i).getFactorToMultiply();
               }
           }
       }
        System.out.println("Multiply factor"+multiplyFactor);
       double totIncPerDept = Precision.round(multiplyFactor*totalMonthProd,0);
       return totIncPerDept;
    }
    List<EmployeeIncentiveDetails> employeeIncentiveDetailsList1 = new ArrayList<>();
    @RequestMapping(value="/index/processIncentiveEmployeewise1", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processIncentiveEmployeewise1(@RequestParam(value = "empNo") String empNo,
                                               @RequestParam(value = "yearSel") String yearSel,
                                               @RequestParam(value = "monthSel") String monthSel)
    {
        // String empNo = nameInput;
        employeeIncentiveDetailsList1.clear();
        System.out.println(empNo+yearSel+monthSel);
        String message = "";
        if(empNo.equals(""))
        {
            List<MonthlyAttendance> empMonthlyList = monthlyAttendanceRepository.findEmpMonthly("","");
            List<String> empList = new ArrayList<>();
            for (MonthlyAttendance e:empMonthlyList) {
                String s = e.getMasEmpid()+"-"+e.getMasEmpname();
                empList.add(s);
            }
            message = "Please fill all details";
        }
        else {
            String s[] = empNo.split("-");
            int index = months.indexOf(monthSel);
            System.out.println("Index" + index);
            int month = index;
            int pickMonth = month + 1;
            int pickYear = Integer.parseInt(yearSel);
            employeeIncentiveDetailsList1 = employeeIncentiveDetailsRepository.findByEmpNoMonthYear(s[0], pickMonth, pickYear);
            empNoSelInc = empNo;
            yearSelInc = yearSel;
            monthSelInc = monthSel;
            System.out.println("empinc"+employeeIncentiveDetailsList1);
            if(employeeIncentiveDetailsList1.size()==0)
            {
                message = "No Result Found";
            }
        }

        List<MonthlyAttendance> empMonthlyList = monthlyAttendanceRepository.findEmpMonthly("","");
        List<String> empList = new ArrayList<>();
        for (MonthlyAttendance e:empMonthlyList) {

            String s = e.getMasEmpid()+"-"+e.getMasEmpname();
            empList.add(s);
        }
        empList.remove(empNo);
        empList.add(0, empNo);
        List<String> yearList1 = yearList;
        yearList1.remove(yearSel);
        yearList1.add(0, yearSel);
        List<String> months2 = months1;
        months2.remove(monthSel);
        months2.add(0, monthSel);
        String empInc = new Gson().toJson(employeeIncentiveDetailsList1);
        String messageAlert = new Gson().toJson(message);
        String bothJson = "[" + empInc + "," + messageAlert + "]";
        empNo="";
        return bothJson;
    }

    @RequestMapping(value="/index/processIncentiveEmployeewiseDetails1", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processIncentiveEmployeewiseDetails1(@RequestParam(value = "empNo") String empNo,
                                                      @RequestParam(value = "yearSel") String yearSel,
                                                      @RequestParam(value = "monthSel") String monthSel)
    {
        List<EmployeeIncentiveDailyDetails> employeewiseIncentiveDetails = new ArrayList<>();
        String message = "";
        String daysDetailsWithRate = "";
        if(empNo.equals(""))
        {
            List<MonthlyAttendance> empMonthlyList = monthlyAttendanceRepository.findEmpMonthly("","");
            List<String> empList = new ArrayList<>();
            for (MonthlyAttendance e:empMonthlyList) {

                String s = e.getMasEmpid()+"-"+e.getMasEmpname();
                empList.add(s);
            }
            System.out.println("EmpList"+empList);
            message = "Please Fill All Details";
          //  model.addAttribute("empList",empList);
           // model.addAttribute("message","Please enter Employee Id");
           // model.addAttribute("alertClass", "alert-danger");
        }
        else
        {
            String s[] = empNo.split("-");
            int index = months.indexOf(monthSel);
            System.out.println("Index" + index);
            int month = index;
            int pickMonth = month + 1;
            int pickYear = Integer.parseInt(yearSel);
            employeewiseIncentiveDetails = employeeIncentiveDailyDetailsRepository.findByEmpNoAndMonthAndYear(s[0], pickMonth, pickYear);
            int i = 1;
            for (EmployeeIncentiveDailyDetails e:employeewiseIncentiveDetails ) {
                e.setSrNo(i);
                LocalDate d = e.getIncDate();
                String formattedDate = d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                e.setDateInString(formattedDate);
                i++;
            }
          //  model.addAttribute("employeewiseIncentiveDetailsList", employeewiseIncentiveDetails);
            String empVType = "false";
            List<MonthlyAttendance> monthlyAttendanceList = monthlyAttendanceRepository.findMonthAttendance("", s[0], pickMonth, pickYear);
            MonthlyAttendance monthlyAttendance =new MonthlyAttendance();
            MonthlyAttendance monthlyAttendanceV =new MonthlyAttendance();
            for (MonthlyAttendance m:monthlyAttendanceList) {
                if(m.getPayCatg().equals("R"))
                {
                    monthlyAttendance = m;
                }
                if(m.getPayCatg().equals("V"))
                {
                    monthlyAttendanceV = m;
                    empVType = "true";
                }
            }
            double attendanceDay = Precision.round(monthlyAttendance.getPresentDays() + monthlyAttendance.getRokdiDays(),2);
            double remAtten = attendanceDay-employeewiseIncentiveDetails.size();
            System.out.println("Attendance Days" + attendanceDay);
            List<DesignationwiseRate> drtList = designationwiseRateRepository.findRateByGrade(monthlyAttendance.getDesignationGrade());
            double designationRt = drtList.get(0).getRate();
            daysDetailsWithRate = "Total Attendance Days:"+attendanceDay+" Days with designation wise rate:"+remAtten+" Designtion Basic Rate:"+designationRt;

        }

     //   model.addAttribute("empNo", empNo);
        List<MonthlyAttendance> empMonthlyList = monthlyAttendanceRepository.findEmpMonthly("","");
        List<String> empList = new ArrayList<>();
        for (MonthlyAttendance e:empMonthlyList) {

            String s = e.getMasEmpid()+"-"+e.getMasEmpname();
            empList.add(s);
        }
        empList.remove(empNo);
        empList.add(0, empNo);
       // model.addAttribute("empList", empList);
        List<String> yearList1 = yearList;
        yearList1.remove(yearSel);
        yearList1.add(0, yearSel);
       // model.addAttribute("yearList", yearList1);
        List<String> months2 = months1;
        months2.remove(monthSel);
        months2.add(0, monthSel);
        //model.addAttribute("monthList", months2);

        String empInc = new Gson().toJson(employeewiseIncentiveDetails);
        String daysDetails = new Gson().toJson(daysDetailsWithRate);
        String messageAlert = new Gson().toJson(message);
        String bothJson = "[" + empInc + "," + daysDetails + "," + messageAlert + "]";
        empNo="";
        return bothJson;

    }

    @RequestMapping(value="/index/processIncentiveUnlock1", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processIncentiveUnlock1(@RequestParam(value = "yearSel") String yearSel,
                                         @RequestParam(value = "monthSel") String monthSel)
    {
        String message = "";
        int index = months.indexOf(monthSel);
        System.out.println("Index" + index);
        int month = index;
        int pickMonth = month + 1;
        int pickYear = Integer.parseInt(yearSel);
        IncentiveLock incentiveLock = incentiveLockRepository.findByMonthAndYear(pickMonth, pickYear);
        List<String> empNoList = monthlyAttendanceRepository.findEmpListForInc(pickMonth, pickYear, "");
        if(incentiveLock != null && incentiveLock.isIncentiveFlag())
        {
            employeeIncentiveRepository.deleteEmpIncentive(pickMonth, pickYear);
            employeeIncentiveDetailsRepository.deleteByMonthYear(pickMonth, pickYear);
            double incentive = 0;
            double incentiveGross = 0;
            for (String empNo:empNoList) {
                ptaxMonthRepository.updateIncentivePtax(incentive, incentiveGross, empNo, pickMonth, pickYear);
                employeeIncentiveDailyDetailsRepository.deleteEmpInc(pickMonth, pickYear);
               // employeeIncentiveDetailsRepository.deleteByMonthYear(pickMonth, pickYear);
            }

            incentiveLockRepository.updateIncentiveFlag(false, false, pickMonth, pickYear);

        }
        message = "Incentive Unlocked";
     //   model.addAttribute("message", "Incentive Unlocked");
    //    model.addAttribute("alertClass", "alert-success");
        List<String> yearList1 = yearList;
        yearList1.remove(yearSel);
        yearList1.add(0,yearSel);
       // model.addAttribute("yearList", yearList1);
        List<String> months2 = months1;
        months2.remove(monthSel);
        months2.add(0,monthSel);
      //  model.addAttribute("monthList",months2);
        String messageAlert = new Gson().toJson(message);
        String bothJson = "[" + messageAlert + "]";
        return bothJson;

    }

    @RequestMapping(value="/index/processIncentiveUnlock2", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processIncentiveUnlock2(@RequestParam(value = "idSel") String idSel)
    {
        System.out.println("Welcome in unlock2"+idSel);

        String message = "Incentive Unlocked";
        DepartmentIncentiveCriteria departmentIncentiveCriteria = departmentIncentiveCriteriaRepository.findIncById(Integer.parseInt(idSel));
        double production = departmentIncentiveCriteria.getMonthlyAchievedProduction();
        double factor = departmentIncentiveCriteria.getFactorToMultiply();
        String prod = new Gson().toJson(production);
        String fact = new Gson().toJson(factor);

        String messageAlert = new Gson().toJson(prod);
        String messageAlert1 = new Gson().toJson(fact);
       // String bothJson = "[" + messageAlert + "]";
        String bothJson = "[" + messageAlert + "," + messageAlert1 + "]";
        return bothJson;

    }
    @RequestMapping(value="/index/processIncentiveUnlock3", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processIncentiveUnlock2(@RequestParam(value = "idSel") String idSel,@RequestParam(value = "production") String production,@RequestParam(value = "factor") String factor)
    {
        System.out.println("Welcome in unlock2"+idSel+production+factor);
        DepartmentIncentiveCriteria departmentIncentiveCriteria = new DepartmentIncentiveCriteria();
        int id = Integer.parseInt(idSel);
        double prod = Double.parseDouble(production);
        double fact = Double.parseDouble(factor);
        departmentIncentiveCriteriaRepository.updateIncentiveCriteria(prod,fact,id);
        String message = "Incentive Updated";
        String messageAlert = new Gson().toJson(message);
     //   String messageAlert1 = new Gson().toJson(fact);
         String bothJson = "[" + messageAlert + "]";
      //  String bothJson = "[" + messageAlert + "," + messageAlert1 + "]";
        return bothJson;

    }


    @GetMapping("/incentiveCriteria")
    public String getIncentiveCriteria(Model model)
    {
        List<DepartmentIncentiveCriteria> departmentIncentiveCriteriaList = departmentIncentiveCriteriaRepository.findAll();
        model.addAttribute("departmentIncentiveCriteriaList",departmentIncentiveCriteriaList);
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        return "incentiveCriteria";
    }

    @GetMapping("/addNewIncentiveCriteria")
    public String addNewIncentiveCriteria(Model model)
    {
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        model.addAttribute("departmentIncentiveCriteria",new DepartmentIncentiveCriteria());
        return "addNewIncentiveCriteria";
    }

    @PostMapping("/processNewIncentiveCriteria")
    public String processNewIncentiveCriteria(DepartmentIncentiveCriteria departmentIncentiveCriteria, Model model)
    {
        System.out.println(departmentIncentiveCriteria);
        int id = departmentIncentiveCriteria.getId();
        double production = departmentIncentiveCriteria.getMonthlyAchievedProduction();
        double factor = departmentIncentiveCriteria.getFactorToMultiply();
        System.out.println(id+production+factor);
        departmentIncentiveCriteriaRepository.save(departmentIncentiveCriteria);
        List<DepartmentIncentiveCriteria> departmentIncentiveCriteriaList = departmentIncentiveCriteriaRepository.findAll();
        model.addAttribute("departmentIncentiveCriteriaList",departmentIncentiveCriteriaList);
        return "incentiveCriteria";
    }

    @GetMapping("/updateIncentiveCriteria")
    public String updateIncentiveCriteria(Model model)
    {
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        model.addAttribute("departmentIncentiveCriteria",new DepartmentIncentiveCriteria());
        List<Integer> idList1 = departmentIncentiveCriteriaRepository.findAllId();
        List<String> idList = new ArrayList<>();
        for (int i:idList1)
        {
            idList.add(String.valueOf(i));
        }
        model.addAttribute("idList",idList);
        model.addAttribute("yearList",yearList);
        model.addAttribute("monthList",months);
        return "updateIncentiveCriteria";
    }

    @GetMapping("/deleteIncentiveCriteria")
    public String deleteIncentiveCriteria(Model model)
    {
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        model.addAttribute("departmentIncentiveCriteria",new DepartmentIncentiveCriteria());
        List<Integer> idList1 = departmentIncentiveCriteriaRepository.findAllId();
        List<String> idList = new ArrayList<>();
        for (int i:idList1)
        {
            idList.add(String.valueOf(i));
        }
        model.addAttribute("idList",idList);
        return "deleteIncentiveCriteria";
    }
    @RequestMapping(value="/index/processIncentiveUnlock4", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processIncentiveUnlock4(@RequestParam(value = "idSel") String idSel,@RequestParam(value = "production") String production,@RequestParam(value = "factor") String factor)
    {
        System.out.println("Welcome in unlock2"+idSel+production+factor);
        DepartmentIncentiveCriteria departmentIncentiveCriteria = new DepartmentIncentiveCriteria();
        int id = Integer.parseInt(idSel);
        double prod = Double.parseDouble(production);
        double fact = Double.parseDouble(factor);
        departmentIncentiveCriteriaRepository.deleteIncentiveCriteria(id);
        String message = "Incentive Deleted";
        String messageAlert = new Gson().toJson(message);
        //   String messageAlert1 = new Gson().toJson(fact);
        String bothJson = "[" + messageAlert + "]";
        //  String bothJson = "[" + messageAlert + "," + messageAlert1 + "]";
        return bothJson;

    }

    @RequestMapping(value="/index/processUpdateIncentiveCriteria", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String processUpdateIncentiveCriteria(@RequestParam(value = "idSel") String idSel) {
        System.out.println("inside id"+idSel);
        DepartmentIncentiveCriteria departmentIncentiveCriteria = departmentIncentiveCriteriaRepository.findIncById(Integer.parseInt(idSel));
        double production = departmentIncentiveCriteria.getMonthlyAchievedProduction();
        double factor = departmentIncentiveCriteria.getFactorToMultiply();
        String prod = new Gson().toJson(production);
        String fact = new Gson().toJson(factor);
        String bothJson = "[" + prod + "," + fact + "]";
        return bothJson;
    }

    @GetMapping("/monthlyIncentiveAttendance")
    public String findMonthlyIncentiveAttendance(Model model)
    {
        model.addAttribute("yearList", yearList);
        model.addAttribute("monthList",months);
        CompanyUser user = LoggedUser.getInstance().getUser();
        if(user.getRewardAccessRightsLevel()>=1) {
            model.addAttribute("message1", "Unlock access");
        }
        return "monthlyIncentiveAttendance";
    }
    List<EmpMonthlyIncentiveAttendance> empMonthAttendanceList = new ArrayList<>();
    @RequestMapping(value="/index/processMonthlyIncentiveAttendance", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String processMonthlyIncentiveAttendance(@RequestParam(value = "yearSel") String yearSel,
                                                              @RequestParam(value = "monthSel") String monthSel) {
        // String empNo = nameInput;
        empMonthAttendanceList.clear();
        System.out.println(yearSel + monthSel);
        String message = "";
        int index = months.indexOf(monthSel);
        System.out.println("Index" + index);
        int month = index;
        int pickMonth = month + 1;
        int pickYear = Integer.parseInt(yearSel);
        empMonthlyIncentiveAttendanceRepository.deleteByMonthYear(pickMonth,pickYear);
        List<String> empNoList = monthlyAttendanceRepository.findEmpListForInc(pickMonth, pickYear, "");
        System.out.println("EmpNoList" + empNoList.size());
        List<List<Double>> categoryPresentRokdiList = new ArrayList<>();
      //  String empId = "BRD306656";
        for (String empId : empNoList) {
            String empVType ="";
            List<MonthlyAttendance> monthlyAttendanceList = monthlyAttendanceRepository.findMonthAttendance("", empId, pickMonth, pickYear);
            MonthlyAttendance monthlyAttendance =new MonthlyAttendance();
            MonthlyAttendance monthlyAttendanceV =new MonthlyAttendance();
            for (MonthlyAttendance m:monthlyAttendanceList) {
                if(m.getPayCatg().equals("R"))
                {
                    monthlyAttendance = m;
                }
                if(m.getPayCatg().equals("V"))
                {
                    monthlyAttendanceV = m;
                    empVType = "true";
                }
            }
            double presentDays = Precision.round(monthlyAttendance.getPresentDays(),2);
            double rokdiDays = Precision.round(monthlyAttendance.getRokdiDays(),2);
            double attendanceDay = Precision.round((monthlyAttendance.getPresentDays() + monthlyAttendance.getRokdiDays()), 2);
            System.out.println("Attendance Days" + attendanceDay);
            String payCat = monthlyAttendance.getPayCatg();
            String department = monthlyAttendance.getMasEmpdept();
            String empName = monthlyAttendance.getMasEmpname();
            double designationGrade = monthlyAttendance.getDesignationGrade();
            List<DesignationwiseRate> drtList = designationwiseRateRepository.findRateByGrade(monthlyAttendance.getDesignationGrade());
            double designationRt = drtList.get(0).getRate();
        //    categoryPresentRokdiList = feltProdLoomWeaverRepository.findCategoryPresentRokdi(empId, pickMonth, pickYear, "", "", "", "", "", "");
        categoryPresentRokdiList = feltProdLoomWeaverRepository.findCategoryPresentRokdiList(empId, pickMonth, pickYear, "", "", "");

        System.out.println("Emp"+empId+"Category present rokadi list"+categoryPresentRokdiList);

            double totalPresentRokdi = 0;
            double totalPresent = 0;
            double totalRokdi = 0;
            for (List<Double> l:categoryPresentRokdiList) {
               EmpMonthlyIncentiveAttendance empMonthlyIncentiveAttendance = new EmpMonthlyIncentiveAttendance();
                double categoryGrade = l.get(0);
                double present = Precision.round(l.get(1),2);
                double rokdi = Precision.round(l.get(2),2);
                double total = Precision.round((present+rokdi),2);
                totalPresent = Precision.round((totalPresent+present),2);
                totalRokdi = Precision.round((totalRokdi+rokdi),2);
                totalPresentRokdi = Precision.round((totalPresentRokdi+total),2);
                double selectionGrade = designationGrade;
                double eligibleBasicRate = designationRt;
                if (categoryGrade <= designationGrade) {
                    selectionGrade = categoryGrade;
                    List<DesignationwiseRate> desRateList = designationwiseRateRepository.findRateByGrade(selectionGrade);
                    eligibleBasicRate = desRateList.get(0).getRate();
                } else {
                    selectionGrade = designationGrade;
                    List<DesignationwiseRate> desRateList = designationwiseRateRepository.findRateByGrade(selectionGrade);
                    eligibleBasicRate = desRateList.get(0).getRate();
                }
                empMonthlyIncentiveAttendance.setPayCat(payCat);
                empMonthlyIncentiveAttendance.setDept(department);
                empMonthlyIncentiveAttendance.setEmpCode(empId);
                empMonthlyIncentiveAttendance.setEmpName(empName);
                empMonthlyIncentiveAttendance.setCategoryGrade(String.valueOf(categoryGrade));
                empMonthlyIncentiveAttendance.setDesignationGrade(String.valueOf(designationGrade));
                empMonthlyIncentiveAttendance.setEligibleIncGrade(String.valueOf(selectionGrade));
                empMonthlyIncentiveAttendance.setEligibleRate(String.valueOf(eligibleBasicRate));
                empMonthlyIncentiveAttendance.setPresent(String.valueOf(present));
                empMonthlyIncentiveAttendance.setRokdi(String.valueOf(rokdi));
                empMonthlyIncentiveAttendance.setTotal(String.valueOf(total));
                empMonthlyIncentiveAttendance.setPickMonth(pickMonth);
                empMonthlyIncentiveAttendance.setPickYear(pickYear);
                empMonthlyIncentiveAttendanceRepository.save(empMonthlyIncentiveAttendance);
              //  empMonthAttendanceList.add(empMonthlyIncentiveAttendance);
            }
            if(totalPresentRokdi<attendanceDay)
            {
                EmpMonthlyIncentiveAttendance empMonthlyIncentiveAttendance = new EmpMonthlyIncentiveAttendance();
                double attendanceDiff = Precision.round((attendanceDay - totalPresentRokdi),2);
                double presentDiff = 0;
                double rokdiDiff = 0;
                if(totalPresent<presentDays){
                     presentDiff = Precision.round((presentDays-totalPresent),2);
                }
                if(totalRokdi<rokdiDays){
                     rokdiDiff = Precision.round((rokdiDays-totalRokdi),2);
                }
                totalPresent = totalPresent+attendanceDiff;
                totalPresentRokdi = totalPresentRokdi + attendanceDiff;
                empMonthlyIncentiveAttendance.setPayCat(payCat);
                empMonthlyIncentiveAttendance.setDept(department);
                empMonthlyIncentiveAttendance.setEmpCode(empId);
                empMonthlyIncentiveAttendance.setEmpName(empName);
                empMonthlyIncentiveAttendance.setCategoryGrade(String.valueOf(designationGrade));
                empMonthlyIncentiveAttendance.setDesignationGrade(String.valueOf(designationGrade));
                empMonthlyIncentiveAttendance.setEligibleIncGrade(String.valueOf(designationGrade));
                empMonthlyIncentiveAttendance.setEligibleRate(String.valueOf(designationRt));
                empMonthlyIncentiveAttendance.setPresent(String.valueOf(presentDiff));
                empMonthlyIncentiveAttendance.setRokdi(String.valueOf(rokdiDiff));
                empMonthlyIncentiveAttendance.setTotal(String.valueOf(attendanceDiff));
                empMonthlyIncentiveAttendance.setPickMonth(pickMonth);
                empMonthlyIncentiveAttendance.setPickYear(pickYear);
                empMonthlyIncentiveAttendanceRepository.save(empMonthlyIncentiveAttendance);
            //    empMonthAttendanceList.add(empMonthlyIncentiveAttendance);
            }
            if(empVType.equals("true"))
            {
                EmpMonthlyIncentiveAttendance empMonthlyIncentiveAttendance = new EmpMonthlyIncentiveAttendance();
                double attendanceDay1 = Precision.round(monthlyAttendanceV.getPresentDays() + monthlyAttendanceV.getRokdiDays(), 2);
                System.out.println("Attendance Days" + attendanceDay);
                String payCat1 = monthlyAttendanceV.getPayCatg();
                empMonthlyIncentiveAttendance.setPayCat(payCat1);
                empMonthlyIncentiveAttendance.setDept(department);
                empMonthlyIncentiveAttendance.setEmpCode(empId);
                empMonthlyIncentiveAttendance.setEmpName(empName);
                empMonthlyIncentiveAttendance.setCategoryGrade(String.valueOf(designationGrade));
                empMonthlyIncentiveAttendance.setDesignationGrade(String.valueOf(designationGrade));
                empMonthlyIncentiveAttendance.setEligibleIncGrade(String.valueOf(designationGrade));
                empMonthlyIncentiveAttendance.setEligibleRate(String.valueOf(designationRt));
                empMonthlyIncentiveAttendance.setPresent(String.valueOf(attendanceDay1));
                empMonthlyIncentiveAttendance.setRokdi(String.valueOf(0));
                empMonthlyIncentiveAttendance.setTotal(String.valueOf(attendanceDay1));
                empMonthlyIncentiveAttendance.setPickMonth(pickMonth);
                empMonthlyIncentiveAttendance.setPickYear(pickYear);
                empMonthlyIncentiveAttendanceRepository.save(empMonthlyIncentiveAttendance);
            }

        }
        List<String> empIdList = empMonthlyIncentiveAttendanceRepository.findEmpIdByMonthYear(pickMonth,pickYear);
        if(empIdList.size()>0)
        {
            for (String empId:empIdList) {
                List<EmpIncAttendanceModel> empMonthlyIncentiveAttendanceList = empMonthlyIncentiveAttendanceRepository.findByMonthAndYear(empId, pickMonth, pickYear, "R");
               // List<EmpIncAttendanceModel> empMonthlyIncentiveAttendanceListVType = empMonthlyIncentiveAttendanceRepository.findByMonthAndYear(empId, pickMonth, pickYear, "V");
                String payCat = "";
                String dept = "";
                String empName = "";
                double totalPresent = 0;
                double totalRokdi = 0;
                double totalPresentRokdi = 0;
                for (EmpIncAttendanceModel e : empMonthlyIncentiveAttendanceList) {
                    EmpMonthlyIncentiveAttendance empMonthlyIncentiveAttendance = new EmpMonthlyIncentiveAttendance();
                    payCat = e.getPayCat();
                    dept = e.getDept();
                    empName = e.getEmpName();
                    empMonthlyIncentiveAttendance.setPayCat(e.getPayCat());
                    empMonthlyIncentiveAttendance.setDept(e.getDept());
                    empMonthlyIncentiveAttendance.setEmpCode(empId);
                    empMonthlyIncentiveAttendance.setEmpName(e.getEmpName());
                    empMonthlyIncentiveAttendance.setCategoryGrade(String.valueOf(e.getCategoryGrade()));
                    empMonthlyIncentiveAttendance.setDesignationGrade(String.valueOf(e.getDesignationGrade()));
                    empMonthlyIncentiveAttendance.setEligibleIncGrade(String.valueOf(e.getEligibleIncGrade()));
                    empMonthlyIncentiveAttendance.setEligibleRate(String.valueOf(e.getEligibleRate()));
                    double p = Precision.round(Double.valueOf(e.getPresentSum()),2);
                    double r = Precision.round(Double.valueOf(e.getRokdiSum()),2);
                    double t = Precision.round(Double.valueOf(e.getTotalSum()),2);
                    totalPresent = Precision.round(totalPresent + Double.valueOf(e.getPresentSum()),2);
                    totalRokdi = Precision.round(totalRokdi + Double.valueOf(e.getRokdiSum()),2);
                    totalPresentRokdi = Precision.round(totalPresentRokdi + Double.valueOf(e.getTotalSum()),2);
                    empMonthlyIncentiveAttendance.setPresent(String.valueOf(p));
                    empMonthlyIncentiveAttendance.setRokdi(String.valueOf(r));
                    empMonthlyIncentiveAttendance.setTotal(String.valueOf(t));
                    empMonthlyIncentiveAttendance.setPickMonth(pickMonth);
                    empMonthlyIncentiveAttendance.setPickYear(pickYear);
                    empMonthAttendanceList.add(empMonthlyIncentiveAttendance);
                }
                EmpMonthlyIncentiveAttendance empMonthlyIncentiveAttendance = new EmpMonthlyIncentiveAttendance();
                empMonthlyIncentiveAttendance.setPayCat(payCat);
                empMonthlyIncentiveAttendance.setDept(dept);
                empMonthlyIncentiveAttendance.setEmpCode(empId);
                empMonthlyIncentiveAttendance.setEmpName(empName);
                empMonthlyIncentiveAttendance.setCategoryGrade("TOTAL");
                empMonthlyIncentiveAttendance.setDesignationGrade("TOTAL");
                empMonthlyIncentiveAttendance.setEligibleIncGrade("TOTAL");
                empMonthlyIncentiveAttendance.setEligibleRate("TOTAL");
                empMonthlyIncentiveAttendance.setPresent(String.valueOf(totalPresent));
                empMonthlyIncentiveAttendance.setRokdi(String.valueOf(totalRokdi));
                empMonthlyIncentiveAttendance.setTotal(String.valueOf(totalPresentRokdi));
                empMonthAttendanceList.add(empMonthlyIncentiveAttendance);
                empMonthlyIncentiveAttendance.setPickMonth(pickMonth);
                empMonthlyIncentiveAttendance.setPickYear(pickYear);


            }
        }
        for (String empId:empIdList) {
         //   List<EmpIncAttendanceModel> empMonthlyIncentiveAttendanceList = empMonthlyIncentiveAttendanceRepository.findByMonthAndYear(empId, pickMonth, pickYear, "R");
            List<EmpIncAttendanceModel> empMonthlyIncentiveAttendanceListVType = empMonthlyIncentiveAttendanceRepository.findByMonthAndYear(empId, pickMonth, pickYear, "V");

            String payCatV = "";
            String deptV = "";
            String empNameV = "";
            double totalPresentV = 0;
            double totalRokdiV = 0;
            double totalPresentRokdiV = 0;
            if (empMonthlyIncentiveAttendanceListVType.size() > 0) {
                for (EmpIncAttendanceModel e1 : empMonthlyIncentiveAttendanceListVType) {
                    EmpMonthlyIncentiveAttendance empMonthlyIncentiveAttendanceV = new EmpMonthlyIncentiveAttendance();
                    payCatV = e1.getPayCat();
                    deptV = e1.getDept();
                    empNameV = e1.getEmpName();
                    empMonthlyIncentiveAttendanceV.setPayCat(e1.getPayCat());
                    empMonthlyIncentiveAttendanceV.setDept(e1.getDept());
                    empMonthlyIncentiveAttendanceV.setEmpCode(empId);
                    empMonthlyIncentiveAttendanceV.setEmpName(e1.getEmpName());
                    empMonthlyIncentiveAttendanceV.setCategoryGrade(String.valueOf(e1.getCategoryGrade()));
                    empMonthlyIncentiveAttendanceV.setDesignationGrade(String.valueOf(e1.getDesignationGrade()));
                    empMonthlyIncentiveAttendanceV.setEligibleIncGrade(String.valueOf(e1.getEligibleIncGrade()));
                    empMonthlyIncentiveAttendanceV.setEligibleRate(String.valueOf(e1.getEligibleRate()));
                    totalPresentV = totalPresentV + Double.valueOf(e1.getPresentSum());
                    totalRokdiV = totalRokdiV + Double.valueOf(e1.getRokdiSum());
                    totalPresentRokdiV = totalPresentRokdiV + Double.valueOf(e1.getTotalSum());
                    empMonthlyIncentiveAttendanceV.setPresent(String.valueOf(e1.getPresentSum()));
                    empMonthlyIncentiveAttendanceV.setRokdi(String.valueOf(e1.getRokdiSum()));
                    empMonthlyIncentiveAttendanceV.setTotal(String.valueOf(e1.getTotalSum()));
                    empMonthlyIncentiveAttendanceV.setPickMonth(pickMonth);
                    empMonthlyIncentiveAttendanceV.setPickYear(pickYear);
                    empMonthAttendanceList.add(empMonthlyIncentiveAttendanceV);
                }
                EmpMonthlyIncentiveAttendance empMonthlyIncentiveAttendanceV = new EmpMonthlyIncentiveAttendance();
                empMonthlyIncentiveAttendanceV.setPayCat(payCatV);
                empMonthlyIncentiveAttendanceV.setDept(deptV);
                empMonthlyIncentiveAttendanceV.setEmpCode(empId);
                empMonthlyIncentiveAttendanceV.setEmpName(empNameV);
                empMonthlyIncentiveAttendanceV.setCategoryGrade("TOTAL");
                empMonthlyIncentiveAttendanceV.setDesignationGrade("TOTAL");
                empMonthlyIncentiveAttendanceV.setEligibleIncGrade("TOTAL");
                empMonthlyIncentiveAttendanceV.setEligibleRate("TOTAL");
                empMonthlyIncentiveAttendanceV.setPresent(String.valueOf(totalPresentV));
                empMonthlyIncentiveAttendanceV.setRokdi(String.valueOf(totalRokdiV));
                empMonthlyIncentiveAttendanceV.setTotal(String.valueOf(totalPresentRokdiV));
                empMonthAttendanceList.add(empMonthlyIncentiveAttendanceV);
                empMonthlyIncentiveAttendanceV.setPickMonth(pickMonth);
                empMonthlyIncentiveAttendanceV.setPickYear(pickYear);
            }
        }
        int i =1;
        for (EmpMonthlyIncentiveAttendance e:empMonthAttendanceList) {
            e.setSrNo(String.valueOf(i));
            i++;
        }
        String empInc = new Gson().toJson(empMonthAttendanceList);
        String messageAlert = new Gson().toJson(message);
        String bothJson = "[" + empInc + "," + messageAlert + "]";
        return bothJson;
    }
    @PostMapping("/exportMonthlyIncentiveAttendanceData")
    public String exportMonthlyIncentiveAttendanceData(@RequestParam(value = "frmSubmit") String format, HttpServletResponse response, Model model) throws IOException, DocumentException {
        System.out.println("WeaverLoomList"+empMonthAttendanceList);
        String heading = "";
        LocalDate cDate = LocalDate.now();
        String currentDate = cDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String templateName = "MonthlyIncentiveAttendance";
        String fileFormat = "pdf";
        if(format.contains("Pdf"))
        {
            fileFormat = "pdf";
        }
        else if(format.contains("Excel"))
        {
            fileFormat = "excel";
        }
        if(empMonthAttendanceList.size()>0) {
            int m = empMonthAttendanceList.get(0).getPickMonth() - 1;
            String month = months.get(m);
            int year = empMonthAttendanceList.get(0).getPickYear();
            List<String> colHeaderList = Stream.of("SrNo","PayCat","Department", "Emp No", "Emp Name", "Category Code", "Designation Code", "Eligible Incentive", "Rate", "Present", "Rokdi", "Total Present+Rokdi").collect(Collectors.toList());
            List<List<String>> excelValues = new ArrayList<>();
            excelValues.add(colHeaderList);
            int i = 1;
            for (EmpMonthlyIncentiveAttendance w : empMonthAttendanceList) {
                List<String> valueList = new ArrayList<>();
                valueList.add(String.valueOf(i));
                valueList.add(w.getPayCat());
                valueList.add(w.getDept());
                valueList.add(w.getEmpCode());
                valueList.add(w.getEmpName());
                valueList.add(w.getCategoryGrade());
                valueList.add(w.getDesignationGrade());
                valueList.add(w.getEligibleIncGrade());
                valueList.add(w.getEligibleRate());
                valueList.add(w.getPresent());
                valueList.add(w.getRokdi());
                valueList.add(w.getTotal());
                excelValues.add(valueList);
                i++;
            }
            heading="ATTENDANCE           : INCENTIVE                                                                                                                             Date :"+currentDate+"&"+"DEPARTMENT     : FELT WEAVING                                                                                                                   MONTH : "+month+"-"+year;
            String pdfReportName = "monthlyIncentiveAttendance& ";
            exportToExcel.doExcel(templateName, fileFormat, excelValues, pdfReportName, heading, "IncentiveAttendance_"+month+"_"+year, response);
        }
        else{
            model.addAttribute("yearList", yearList);
            model.addAttribute("monthList",months);
        }
        return "monthlyIncentiveAttendance";
    }

     /* @GetMapping("/processIncentiveEmployeewise")
    public String processIncentiveEmployeewise(@RequestParam(value = "empNo") String empNo,
                                               @RequestParam(value = "yearSel") String yearSel,
                                               @RequestParam(value = "monthSel") String monthSel,
                                               Model model)
    {
       // String empNo = nameInput;
        if(empNo.equals(""))
        {
            List<MonthlyAttendance> empMonthlyList = monthlyAttendanceRepository.findEmpMonthly("","");
            List<String> empList = new ArrayList<>();
            for (MonthlyAttendance e:empMonthlyList) {
                String s = e.getMasEmpid()+"-"+e.getMasEmpname();
                empList.add(s);
            }
            model.addAttribute("empList",empList);
            model.addAttribute("message","Please enter Employee Id");
            model.addAttribute("alertClass", "alert-danger");
        }
        else {
            String s[] = empNo.split("-");
            int index = months.indexOf(monthSel);
            System.out.println("Index" + index);
            int month = index;
            int pickMonth = month + 1;
            int pickYear = Integer.parseInt(yearSel);
            employeewiseIncentive = employeeIncentiveRepository.findByEmpNoAndMonthYear(s[0], pickMonth, pickYear);
            model.addAttribute("employeeIncentiveList", employeewiseIncentive);
            model.addAttribute("empNo", empNo);
            empNoSelInc = empNo;
            yearSelInc = yearSel;
            monthSelInc = monthSel;
        }

        List<MonthlyAttendance> empMonthlyList = monthlyAttendanceRepository.findEmpMonthly("","");
        List<String> empList = new ArrayList<>();
        for (MonthlyAttendance e:empMonthlyList) {

            String s = e.getMasEmpid()+"-"+e.getMasEmpname();
            empList.add(s);
        }
        empList.remove(empNo);
        empList.add(0, empNo);
        model.addAttribute("empList", empList);
        List<String> yearList1 = yearList;
            yearList1.remove(yearSel);
            yearList1.add(0, yearSel);
            model.addAttribute("yearList", yearList1);
            List<String> months2 = months1;
            months2.remove(monthSel);
            months2.add(0, monthSel);
            model.addAttribute("monthList", months2);

        return "incentiveEmployeewise";
    }*/
    //List<EmployeeIncentiveDailyDetails> employeewiseIncentiveDetails = new ArrayList<>();

 /*   @GetMapping("/processIncentiveEmployeewiseDetails")
    public String processIncentiveEmployeewiseDetails(@RequestParam(value = "empNo") String empNo,
                                               @RequestParam(value = "yearSel") String yearSel,
                                               @RequestParam(value = "monthSel") String monthSel,
                                               Model model)
    {
        if(empNo.equals(""))
        {
            List<MonthlyAttendance> empMonthlyList = monthlyAttendanceRepository.findEmpMonthly("","");
            List<String> empList = new ArrayList<>();
            for (MonthlyAttendance e:empMonthlyList) {

                String s = e.getMasEmpid()+"-"+e.getMasEmpname();
                empList.add(s);
            }
            System.out.println("EmpList"+empList);
            model.addAttribute("empList",empList);
            model.addAttribute("message","Please enter Employee Id");
            model.addAttribute("alertClass", "alert-danger");
        }
        else
        {
            String s[] = empNo.split("-");
            int index = months.indexOf(monthSel);
            System.out.println("Index" + index);
            int month = index;
            int pickMonth = month + 1;
            int pickYear = Integer.parseInt(yearSel);
            employeewiseIncentiveDetails = employeeIncentiveDailyDetailsRepository.findByEmpNoAndMonthAndYear(s[0], pickMonth, pickYear);
            int i = 1;
            for (EmployeeIncentiveDailyDetails e:employeewiseIncentiveDetails ) {
                e.setSrNo(i);
                LocalDate d = e.getIncDate();
                String formattedDate = d.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                e.setDateInString(formattedDate);
                i++;
            }
            model.addAttribute("employeewiseIncentiveDetailsList", employeewiseIncentiveDetails);
            MonthlyAttendance monthlyAttendance = monthlyAttendanceRepository.findMonthAttendance("", s[0], pickMonth, pickYear);
            double attendanceDay = Precision.round(monthlyAttendance.getPresentDays() + monthlyAttendance.getRokdiDays(),2);
            double remAtten = attendanceDay-employeewiseIncentiveDetails.size();
            System.out.println("Attendance Days" + attendanceDay);
            List<DesignationwiseRate> drtList = designationwiseRateRepository.findRateByGrade(monthlyAttendance.getDesignationGrade());
            double designationRt = drtList.get(0).getRate();
            model.addAttribute("totaldays",attendanceDay);
            model.addAttribute("days",remAtten);
            model.addAttribute("rate",designationRt);
            model.addAttribute("emp", empNo);
            model.addAttribute("year", yearSel);
            model.addAttribute("month", monthSel);
        }

        model.addAttribute("empNo", empNo);
       List<MonthlyAttendance> empMonthlyList = monthlyAttendanceRepository.findEmpMonthly("","");
        List<String> empList = new ArrayList<>();
        for (MonthlyAttendance e:empMonthlyList) {

            String s = e.getMasEmpid()+"-"+e.getMasEmpname();
            empList.add(s);
        }
        empList.remove(empNo);
        empList.add(0, empNo);
        model.addAttribute("empList", empList);
        List<String> yearList1 = yearList;
        yearList1.remove(yearSel);
        yearList1.add(0, yearSel);
        model.addAttribute("yearList", yearList1);
        List<String> months2 = months1;
        months2.remove(monthSel);
        months2.add(0, monthSel);
        model.addAttribute("monthList", months2);

        return "incentiveEmployeewiseDetails";
    }*/
}
