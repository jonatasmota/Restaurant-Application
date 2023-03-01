package mo.nbcc.resto.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mo.nbcc.resto.model.Employee;
import mo.nbcc.resto.model.Event;
import mo.nbcc.resto.services.EmployeeService;
import mo.nbcc.resto.services.EventService;
import mo.nbcc.resto.services.ReservationService;
import mo.nbcc.resto.services.TimeSlotService;

@Controller
public class LoginController {
	
	private EmployeeService empSvc;
	
	ApplicationContext ac;
	ReservationService rs;
	EventService es;
	TimeSlotService ts;

	@Autowired
	public LoginController(EmployeeService empSvc, ApplicationContext ac, ReservationService rs, EventService es, TimeSlotService ts) {
		super();
		this.empSvc = empSvc;
		this.ac = ac;
		this.rs = rs;
		this.es = es;
		this.ts = ts;
	}
	
	
	/**
	 * Gets session and redirects to home page
	 * 
	 * @param model   Holder for model attribute
	 * @param session HttpSession
	 * @return If session is not null redirect to dashboard, else return to home page
	 */
	@GetMapping("/dashboard")
	public String toIndex(Model model, HttpSession session) {
		if (session.getAttribute("loggedIn") != null
				&& (session.getAttribute("loggedIn").toString().equalsIgnoreCase("loggedin"))) {
			return "dashboard";
		}
		return "home";
	}

	/**
	 * Authenticates user
	 * 
	 * @param username User username
	 * @param password User password
	 * @param model    Holder for model attribute
	 * @param session  HttpSession
	 * @param rd       Add the given flash attribute
	 * @return If employee username and password is found, log into the application,
	 *         otherwise shows the error and redirects to the home page.
	 */
	@PostMapping("/authenticate")
	public String authenticate(String username, String password, Model model, HttpSession session,
			RedirectAttributes rd) {
		List<Employee> emp = empSvc.getByUsername(username);

		for (Employee employee : emp) {
			if (employee != null) {
				if (employee.getPassword().equals(password)) {
					session.setAttribute("loggedIn", "loggedIn");
					session.setAttribute("currentEmployee", employee);
					return "redirect:/dashboard";
				}
				
				rd.addFlashAttribute("error_login", "Password Not Found");
			}
			
		}
		return "redirect:/home";	
	}
		
		

	/**
	 * Filters employee access level and directs to the respective home page
	 * 
	 * @param model   Holder for model attribute
	 * @param session HttpSession
	 * @return Respective home page
	 */
	/*
	 * @GetMapping("/") public String home(Model model, HttpSession session) {
	 * List<Event> events = es.getAllEvents(); model.addAttribute("events", events);
	 * 
	 * return "home"; }
	 */
	/**
	 * Log out
	 * 
	 * @param model   Holder for model attribute
	 * @param session HttpSession
	 * @param request Creates a HttpServletRequest
	 * @return If session not null invalidate session, else redirect home page
	 */
	@GetMapping("/signoff")
	public String logout(Model model, HttpSession session) {
		session.removeAttribute("loggedIn");
		session.removeAttribute("currentEmployee");
		
		if (session != null) {
			session.invalidate();
		}

		return "redirect:/";
	}

//	this is only used to initialized administrator account hence not public
//	once admin account is registered it can start adding lower tier admin(warehouse,store etc)
	/**
	 * Form to register new user
	 * 
	 * @param model Holder for model attribute
	 * @return register page
	 */
	@GetMapping("/register")
	public String register(Model model) {
		// create model attribute to bind form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "register";
	}

	/**
	 * Persists new employee to database
	 * 
	 * @param employee Employee to be added
	 * @param rd       Add the given flash attribute
	 * @return If employee not exists in the database, add new employee, else shows
	 *         error and redirect to register page
	 */
	@PostMapping("/addNewAdmin")
	public String addNewAdmin(@ModelAttribute("employee") Employee employee, RedirectAttributes rd) {
		//
		if (!empSvc.existByUsername(employee.getUsername())) {
			empSvc.addNewEmployee(employee);
			rd.addFlashAttribute("employee", employee);
			return "redirect:/";
		} else {
			rd.addFlashAttribute("error_employee", employee);
			return "redirect:/register";
		}
	}
}
