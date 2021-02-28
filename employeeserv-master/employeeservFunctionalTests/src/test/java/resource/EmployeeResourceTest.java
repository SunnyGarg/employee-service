package resource;

import com.paypal.bfs.test.employeeserv.api.EmployeeService;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalBadRequestException;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalEntityNotFoundException;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalHibernateDAOException;
import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;
import com.paypal.bfs.test.employeeserv.model.PaypalResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Contains unit test cases for EmployeeResource
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeeResourceTest {

    @InjectMocks
    private EmployeeResourceImpl employeeResource;

    @Mock
    private EmployeeService employeeService;

    private EmployeeEntity employeeEntity;
    private Employee employee;

    private String employeeId = "E1";

    private HttpHeaders httpHeaders;

    /**
     * Things to initialize before tests execution.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        httpHeaders = new HttpHeaders();

        String firstName = "Test First Name";
        String lastName = "Test Last Name";
        String dateOfBirth = "28 October, 1987";

        employeeEntity = new EmployeeEntity();
        employeeEntity.setFirstName(firstName);
        employeeEntity.setLastName(lastName);
        employeeEntity.setDateOfBirth(dateOfBirth);

        employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDateOfBirth(dateOfBirth);
    }

    /**
     * Method to test get employee by an id. This is happy test case means we test with valid id.
     *
     * @throws PaypalEntityNotFoundException
     */
    @Test
    public void employeeGetByIdHappyTest() throws PaypalEntityNotFoundException {
        when(employeeService.getByEmployeeId(employeeId)).thenReturn(employee);

        ResponseEntity<PaypalResponse> responseEntity = employeeResource.employeeGetById(employeeId);
        employee = (Employee) responseEntity.getBody().getPayload();
        Assert.assertEquals(employee.getFirstName(), employeeEntity.getFirstName());
        Assert.assertEquals(employee.getLastName(), employeeEntity.getLastName());
        Assert.assertEquals(employee.getDateOfBirth(), employeeEntity.getDateOfBirth());
    }

    /**
     * Method to test get employee by an id. This is non happy test case means we test with in valid id.
     *
     * @throws PaypalEntityNotFoundException
     */
    @Test
    public void employeeGetByIdNonHappyTest() throws PaypalEntityNotFoundException {
        when(employeeService.getByEmployeeId(employeeId)).thenThrow(PaypalEntityNotFoundException.class);

        ResponseEntity<PaypalResponse> responseEntity = employeeResource.employeeGetById(employeeId);
        assertTrue(responseEntity.getBody() != null);
    }

    /**
     * Method to test create employee. This is happy test case means we test with valid employee entity here.
     *
     * @throws PaypalHibernateDAOException
     */
    @Test
    public void createEmployeeHappyTest() throws PaypalHibernateDAOException, PaypalBadRequestException {
        when(employeeService.createEmployee(httpHeaders, employeeEntity)).thenReturn(employee);

        ResponseEntity<PaypalResponse> responseEntity = employeeResource.createEmployee(httpHeaders, employeeEntity);
        employee = (Employee) responseEntity.getBody().getPayload();
        Assert.assertEquals(employee.getFirstName(), employeeEntity.getFirstName());
        Assert.assertEquals(employee.getLastName(), employeeEntity.getLastName());
        Assert.assertEquals(employee.getDateOfBirth(), employeeEntity.getDateOfBirth());
    }

    /**
     * Method to test create employee. This is non happy test case means we test with invalid employee entity here.
     *
     * @throws PaypalHibernateDAOException
     */
    @Test
    public void createEmployeeNonHappyTest() throws PaypalHibernateDAOException, PaypalBadRequestException {
        when(employeeService.createEmployee(httpHeaders, employeeEntity)).thenThrow(PaypalHibernateDAOException.class);

        ResponseEntity<PaypalResponse> responseEntity = employeeResource.createEmployee(httpHeaders, employeeEntity);
        Assert.assertTrue(responseEntity.getBody() != null);
    }

}
