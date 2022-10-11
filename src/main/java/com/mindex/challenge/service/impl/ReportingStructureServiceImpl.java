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
    int count = 0;
    boolean done = false;
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ReportingStructure read(String id) {


        ReportingStructure structure = new ReportingStructure(id);

        Employee root = employeeService.read(id);
        List<Employee> rootDirectReports = root.getDirectReports();

        if (rootDirectReports.isEmpty())
        {
            structure.setNumOfReports(Integer.toString(count));
        } else  // If I get here, I have reports to count
        {
            // Call the recursive function
            // The recursive function will walk the tree
            // and return the total number of reports found
            // for the 'sub-employees'

            // This is the count of the root node
            count += rootDirectReports.size();

            // This adds the counts of the sub-nodes
            recursive(rootDirectReports);


            LOG.debug("Count is  [{}]", count);
            structure.setNumOfReports(Integer.toString(count));
        }

        return structure;
    }

    private int recursive(List<Employee> list)
    {
        // create an iterator for the list
        Iterator<Employee> it = list.iterator();
        Employee emp = it.next();
        List<Employee> nodeReports = emp.getDirectReports();

        do
        {
            if(nodeReports != null)
            {
                LOG.debug("I'm in node reports not null");
                count += nodeReports.size();
                LOG.debug("Count inside of recursive is [{}]", count);
            }
            else
            {
                LOG.debug("I'm in node reports null");
                if (!it.hasNext())
                {
                    done = true;
                }
                else
                    emp = it.next();
            }

        } while (!done);

        return count;
    }
}
