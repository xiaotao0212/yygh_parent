package com.xiaotao.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaotao.yygh.common.result.Result;
import com.xiaotao.yygh.common.utils.MD5;
import com.xiaotao.yygh.hosp.service.HospitalSetService;
import com.xiaotao.yygh.model.hosp.HospitalSet;
import com.xiaotao.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

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
        return Result.bool(flag);
    }

    @ApiOperation("条件查询医院设置信息")
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result<Page<HospitalSet>> page(@PathVariable long current,
                                          @PathVariable long limit,
                                          @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        Page<HospitalSet> hospitalSetPage = new Page<>(current, limit);
        LambdaQueryWrapper<HospitalSet> hospitalSetLambdaQueryWrapper = new LambdaQueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
        hospitalSetLambdaQueryWrapper.like(StringUtils.isNotBlank(hosname), HospitalSet::getHosname, hosname);
        hospitalSetLambdaQueryWrapper.like(StringUtils.isNotBlank(hoscode), HospitalSet::getHoscode, hoscode);
        Page<HospitalSet> page = hospitalSetService.page(hospitalSetPage, hospitalSetLambdaQueryWrapper);
        return Result.ok(page);
    }

    @ApiOperation("保存医院设置")
    @PostMapping("saveHospitalSet")
    public Result save(@RequestBody HospitalSet hospitalSet) {
        hospitalSet.setStatus(1);
        Random random = new Random();
        String encrypt = MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000));
        hospitalSet.setSignKey(encrypt);
        boolean save = hospitalSetService.save(hospitalSet);
        return Result.bool(save);
    }

    @ApiOperation("根据id获取医院设置")
    @GetMapping("getHospSet/{id}")
    public Result<HospitalSet> getById(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    @ApiOperation("修改医院设置")
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        return Result.bool(flag);
    }

    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        boolean flag = hospitalSetService.removeByIds(idList);
        return Result.bool(flag);
    }

    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        LambdaUpdateWrapper<HospitalSet> hospitalSetLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        hospitalSetLambdaUpdateWrapper.eq(HospitalSet::getId, id).set(HospitalSet::getStatus, status);
        boolean update = hospitalSetService.update(hospitalSetLambdaUpdateWrapper);
        return Result.bool(update);
    }

    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }


}
