package com.xiaotao.yygh.hosp.controller;

import com.xiaotao.yygh.hosp.service.HospitalSetService;
import com.xiaotao.yygh.model.hosp.HospitalSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    @GetMapping("findAll")
    public List<HospitalSet> list() {
        List<HospitalSet> list = hospitalSetService.list();
        return list;
    }

    @DeleteMapping("{id}")
    public boolean delete(@PathVariable Long id) {
        return hospitalSetService.removeById(id);
    }

}
