package com.mindex.challenge.data;

public class ReportingStructure
{

    private String employeeId;
    private String numOfReports;

    public ReportingStructure(String id) {
        this.employeeId = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getNumOfReports(){
        return numOfReports;
    }

    public void setNumOfReports(String numb){
        this.numOfReports = numb;
    }

}

