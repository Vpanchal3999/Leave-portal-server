package com.synoverge.leave.portal.security;

import com.synoverge.leave.portal.models.entity.Employee;
import com.synoverge.leave.portal.repository.EmployeeRepository;
import com.synoverge.leave.portal.utility.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {


    private final EmployeeRepository employeeRepository;


//    private final PasswordEncoder bcryptEncoder;

    @Autowired
    public JwtUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String employeeCode) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByEmployeeCode(employeeCode);
        if (employee.isEmpty()) {
            throw new UsernameNotFoundException(ErrorMessages.EMPLOYEE_CODE_NOT_FOUND + employeeCode);
        }
        else {
            Employee employeeDetails = employee.get();
            return new User(employeeDetails.getEmployeeCode(), employeeDetails.getPassword(),getAuthority(employeeDetails));
        }
    }

    private ArrayList<SimpleGrantedAuthority> getAuthority(Employee employee) {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(employee.getRoles().getRoleName()));
        return authorities;
    }
}
