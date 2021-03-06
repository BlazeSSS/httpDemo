package com.example.http.controller;

import com.example.http.model.FormModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("requestMatch")
//@CrossOrigin // 可在 WebMvcConfigurer 的 addCorsMappings 中配置
public class RequestMatchController {

    @RequestMapping
    public ModelAndView requestMatch() {
        ModelAndView modelAndView = new ModelAndView("views/requestMatch");
        return modelAndView;
    }

    /**
     * GET
     * @param query
     * @return
     */
    @GetMapping("getForDefault")
    public String getForDefault(String query) {
        return "query=" + query;
    }

    @GetMapping("getForDefaultModel")
    public String getForDefaultModel(FormModel model) {
        return "model=" + model;
    }

    @GetMapping("getForRequestParam")
    public String getForRequestParam(@RequestParam("query") String querys) {
        return "query=" + querys;
    }

    /**
     * POST JSON
     * @param model
     * @param query
     * @return
     */
    @PostMapping("postJsonForDefaultModel")
    public String postJsonForDefaultModel(FormModel model, String query) {
        return "model=" + model + "; query=" + query;
    }

    @PostMapping("postJsonForDefaultMap")
    public String postJsonForDefaultMap(Map<String, Object> params, String query) {
        return "param=" + params + "; query=" + query;
    }

    @PostMapping("postJsonForRequestBodyMap")
    public String postJsonForRequestBodyMap(@RequestBody Map<String, Object> params, String query) {
        return "param=" + params + "; query=" + query;
    }

    @PostMapping("postJsonForRequestBodyModel")
    public String postJsonForRequestBodyModel(@RequestBody FormModel model, String query) {
        return "model=" + model + "; query=" + query;
    }

    /**
     * POST Form
     * @param param
     * @param query
     * @return
     */
    @PostMapping("postFormForDefault")
    public String postFormForDefault(String param, String query) {
        return "param=" + param + "; query=" + query;
    }

    @PostMapping("postFormForDefaultModel")
    public String postFormForDefaultModel(FormModel model, String query) {
        return "model=" + model + "; query=" + query;
    }

    @PostMapping("postFormForDefaultMap")
    public String postFormForDefaultMap(Map<String, Object> params, String query) {
        return "params=" + params + "; query=" + query;
    }
}
