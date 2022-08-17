package com.xiaotao.yygh.hosp.controller;

import com.xiaotao.yygh.common.result.Result;
import com.xiaotao.yygh.hosp.service.HospitalSetService;
import com.xiaotao.yygh.model.hosp.HospitalSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "获取所有医院设置信息")
    @GetMapping("findAll")
    public Result<List<HospitalSet>> list() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    @ApiOperation(value = "逻辑删除医院设置信息")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

}
