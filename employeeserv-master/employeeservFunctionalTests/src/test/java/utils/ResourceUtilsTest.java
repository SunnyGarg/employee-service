package utils;

import com.paypal.bfs.test.employeeserv.api.ResourceUtils;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.AddressEntity;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class ResourceUtilsTest {

    @InjectMocks
    private ResourceUtils resourceUtils;

    private EmployeeEntity employeeEntity;
    private AddressEntity addressEntity;

    private Employee employee;
    private Address address;

    private String employeeId = "E1";

    /**
     * Things to initialize before tests execution.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        String firstName = "Test First Name";
        String lastName = "Test Last Name";
        String dateOfBirth = "28 October, 1987";

        String city = "Test City";
        String country = "Test Country";

        addressEntity = AddressEntity.builder().city(city).country(country).build();

        address = new Address();
        address.setCity(city);
        address.setCountry(country);

        employeeEntity = new EmployeeEntity();
        employeeEntity.setEmployeeId(employeeId);
        employeeEntity.setDateOfBirth(dateOfBirth);
        employeeEntity.setFirstName(firstName);
        employeeEntity.setLastName(lastName);

        Set<AddressEntity> addressEntitySet = new HashSet<>();
        addressEntitySet.add(addressEntity);
        employeeEntity.setAddresses(addressEntitySet);

        employee = new Employee();
        employee.setId(employeeId);
        employee.setDateOfBirth(dateOfBirth);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);

        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        employee.setAddresses(addressList);
    }

    @Test
    public void toEmployeeTest() {
        Employee employee = resourceUtils.toEmployee(employeeEntity);

        Assert.assertEquals(employee.getFirstName(), employeeEntity.getFirstName());
        Assert.assertEquals(employee.getLastName(), employeeEntity.getLastName());
        Assert.assertEquals(employee.getDateOfBirth(), employeeEntity.getDateOfBirth());
        Assert.assertEquals(employee.getId(), employeeEntity.getEmployeeId());
    }

    @Test
    public void toEmployeesTest() {
        List<Employee> employees = resourceUtils.toEmployees(Arrays.asList(employeeEntity));

        Assert.assertEquals(employees.get(0).getFirstName(), employeeEntity.getFirstName());
        Assert.assertEquals(employees.get(0).getLastName(), employeeEntity.getLastName());
        Assert.assertEquals(employees.get(0).getDateOfBirth(), employeeEntity.getDateOfBirth());
        Assert.assertEquals(employees.get(0).getId(), employeeEntity.getEmployeeId());
    }

    @Test
    public void toAddressTest() {
        Address address = resourceUtils.toAddress(addressEntity);

        Assert.assertEquals(address.getCity(), addressEntity.getCity());
        Assert.assertEquals(address.getState(), addressEntity.getState());
    }
}