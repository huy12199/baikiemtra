package com.testmd4.service;

import com.testmd4.model.Staff;
import com.testmd4.repositories.IStaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StaffService {
    @Autowired
    IStaffRepo iStaffRepo;

    public Page<Staff> getAll(Pageable pageable) {
        return iStaffRepo.findAll(pageable);
    }

    public void save(Staff staff) {
        iStaffRepo.save(staff);
    }

    public void delete(long id) {
        iStaffRepo.deleteById(id);
    }

    public Staff findById(long id) {
        Optional<Staff> optional = iStaffRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return new Staff();
    }

    public List<Staff> findByStaffNameContaining(String name) {
        return iStaffRepo.findByStaffNameContaining(name);
    }

    public Optional<Staff> findByNameStaff(String name) {
        return iStaffRepo.findByStaffName(name);
    }
    public Optional<Staff> findByStaffCode(String name) {
        return iStaffRepo.findByStaffCode(name);
    }
}
