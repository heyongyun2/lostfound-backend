package org.example.springboot.controller;

import org.example.springboot.common.Result;
import org.example.springboot.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/print")
public class PrintController {

    @Autowired
    private PrintService printService;

    @PostMapping("/submit")
    public Result submitPrint(
            @RequestParam String fileName,
            @RequestParam int copies,
            @RequestParam boolean isDuplex) {
        try {
            printService.submitPrint(fileName, copies, isDuplex);
            return Result.success("打印任务已提交");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }
}
