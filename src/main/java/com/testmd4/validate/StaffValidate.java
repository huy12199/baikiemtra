package com.testmd4.validate;

import com.testmd4.model.Staff;
import com.testmd4.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class StaffValidate implements Validator {
    @Autowired
    StaffService staffService;
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
    @Override
    public void validate(Object target, Errors errors) {
        Staff staff = (Staff) target;
        Optional<Staff> optional = staffService.findByStaffCode(staff.getStaffCode());
        if (optional.isPresent()){
            errors.rejectValue("staffCode", "", "Employee duplicate code");
        }

    }
}
