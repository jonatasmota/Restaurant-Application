package mo.nbcc.resto.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import mo.nbcc.resto.model.Employee;
import mo.nbcc.resto.repository.EmployeeRepository;


@Service
public class EmployeeService {
	private EmployeeRepository empRepo;

	public EmployeeService(EmployeeRepository empRepo) {
		super();
		this.empRepo = empRepo;
	}

	public List<Employee> getByUsername(String username) {
		return empRepo.findByUsername(username);
	}

	public Employee getSingleByUsername(String username) {
		if (!empRepo.findByUsername(username).isEmpty()) {
			return empRepo.findByUsername(username).get(0);
		}
		return null;
	}

	public List<Employee> getAllEmployees() {
		return empRepo.findAll();
	}

	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return empRepo.findAll(pageable);
	}

	public Employee getEmployeebyId(long id) {
		return empRepo.getReferenceById(id);
	}

	public Employee addNewEmployee(Employee employee) {
		return this.empRepo.save(employee);
	}

	public void deleteEmployeeById(long id) {
		empRepo.deleteById(id);
	}
	
	public boolean existByUsername(String username) {
		return empRepo.existsByUsername(username);
	}
	
	public Employee findByEmpId(long id) {
		return this.empRepo.findById(id);
	}

	public boolean existById(long id) {
		return this.empRepo.existsById(id);
	}

}
