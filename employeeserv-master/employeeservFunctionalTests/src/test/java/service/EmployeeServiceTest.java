package service;

import com.paypal.bfs.test.employeeserv.api.ResourceUtils;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalBadRequestException;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalEntityNotFoundException;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalHibernateDAOException;
import com.paypal.bfs.test.employeeserv.impl.EmployeeServiceImpl;
import com.paypal.bfs.test.employeeserv.repository.IEmployeeRepository;
import com.paypal.bfs.test.employeeserv.repository.IIdempotencyRepository;
import com.paypal.bfs.test.employeeserv.utils.EntityValidator;
import com.paypal.bfs.test.employeeserv.utils.IdempotencyValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * Contains unit test cases for EmployeeService
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private IEmployeeRepository employeeRepository;

    @Mock
    private IIdempotencyRepository idempotencyRepository;

    @Mock
    private IdempotencyValidator idempotencyValidator;

    @Mock
    private ResourceUtils resourceUtils;

    @Mock
    private EntityValidator entityValidator;

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
        employeeEntity.setEmployeeId(employeeId);
        employeeEntity.setFirstName(firstName);
        employeeEntity.setLastName(lastName);
        employeeEntity.setDateOfBirth(dateOfBirth);

        employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDateOfBirth(dateOfBirth);
    }

    /**
     * Method to test create employee. This is happy test case means we test with valid employee entity here.
     *
     * @throws PaypalHibernateDAOException
     */
    @Test
    public void createEmployeeHappyTest() throws PaypalHibernateDAOException, PaypalBadRequestException {
        when(employeeRepository.save(employeeEntity)).thenReturn(employeeEntity);
        when(resourceUtils.toEmployee(employeeEntity)).thenReturn(employee);

        Employee employee = employeeService.createEmployee(httpHeaders, employeeEntity);

        Assert.assertEquals(employee.getFirstName(), employeeEntity.getFirstName());
        Assert.assertEquals(employee.getLastName(), employeeEntity.getLastName());
        Assert.assertEquals(employee.getDateOfBirth(), employeeEntity.getDateOfBirth());
    }

    /**
     * Method to test create employee. This is non happy test case means we test with invalid employee entity here.
     *
     * @throws PaypalHibernateDAOException
     */
    @Test(expected = PaypalHibernateDAOException.class)
    public void createEmployeeNonHappyTest() throws PaypalHibernateDAOException, PaypalBadRequestException {
        when(employeeRepository.save(employeeEntity)).thenReturn(null);

        employeeService.createEmployee(httpHeaders, employeeEntity);
    }

    /**
     * Method to test get employee by an id. This is happy test case means we test with valid id.
     *
     * @throws PaypalEntityNotFoundException
     */
    @Test
    public void getEmployeeHappyTest() throws PaypalEntityNotFoundException {
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(employeeEntity));
        when(resourceUtils.toEmployee(employeeEntity)).thenReturn(employee);

        Employee employee = employeeService.getByEmployeeId(employeeId);

        Assert.assertEquals(employee.getFirstName(), employeeEntity.getFirstName());
        Assert.assertEquals(employee.getLastName(), employeeEntity.getLastName());
        Assert.assertEquals(employee.getDateOfBirth(), employeeEntity.getDateOfBirth());
    }

    /**
     * Method to test get employee by an id. This is non happy test case means we test with invalid id.
     *
     * @throws PaypalEntityNotFoundException
     */
    @Test(expected = PaypalEntityNotFoundException.class)
    public void getEmployeeNonHappyTest() throws PaypalEntityNotFoundException {
        EmployeeEntity employeeEntity = null;

        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.ofNullable(employeeEntity));

        employeeService.getByEmployeeId(employeeId);
    }
}
