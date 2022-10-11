package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService
{
    int count;

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ReportingStructure read(String id) {

        count = 0;
        ReportingStructure structure = new ReportingStructure(id);

        Employee root = employeeService.read(id);
        List<Employee> rootDirectReports = root.getDirectReports();

        if (rootDirectReports == null)
        {
            // Count is zero
            structure.setNumOfReports(Integer.toString(count));
        }
        else  // If I get here, I have reports to count
        {
            // Call the recursive function
            // The recursive function will walk the tree
            // and return the total number of reports found
            // for the 'sub-employees'

            // This is the count of the root node
            count += rootDirectReports.size();

            LOG.debug("Count is before [{}]", count);
            // This adds the counts of the sub-nodes
            recursive(rootDirectReports);
            LOG.debug("Count is after [{}]", count);
        }

        structure.setNumOfReports(Integer.toString(count));
        return structure;
    }

    private void recursive(List<Employee> list)
    {
        // create an iterator for the list
        Iterator<Employee> it = list.iterator();
        Employee emp;
        List<Employee> nodeReports;

        // Fixed here
        while(it.hasNext())
        {
            LOG.debug("I'm in the while loop");
            emp = employeeService.read(it.next().getEmployeeId());
            nodeReports = emp.getDirectReports();

            if(nodeReports != null)
            {
                LOG.debug("I'm in node reports not null");
                count += nodeReports.size();
                LOG.debug("Count inside of recursive is [{}]", count);
            }
        }
    }
}
