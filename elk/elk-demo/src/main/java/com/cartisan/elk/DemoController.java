package com.cartisan.elk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author colin
 */
@RestController
@RequestMapping
@Slf4j
public class DemoController {
    @GetMapping("/info")
    public String info() {
        log.info("日志信息[info]");
        return "test";
    }

    @GetMapping("/debug")
    public String debug() {
        log.debug("日志信息[debug]");
        return "test";
    }

    @GetMapping("/warn")
    public String warn() {
        log.warn("日志信息[warn]");
        return "test";
    }

    @GetMapping("/error")
    public String error() {
        log.error("日志信息[error]");
        return "test";
    }

    @GetMapping("/exception")
    public String exception() {
        int i = 1 / 0;
        return "test";
    }


}
