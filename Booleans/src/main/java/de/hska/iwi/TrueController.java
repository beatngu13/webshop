package de.hska.iwi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TrueController {

    @Autowired
    private BooleanService service;

    @RequestMapping(value = "/true", method = RequestMethod.GET)
    public BooleanResponse getTrue() {
        return new BooleanResponse(service.getTrue());
    }

    @RequestMapping(value = "/true", method = RequestMethod.GET, params = "n")
    public Map<String, Integer> getTrueNumeric(@RequestParam("n") String n) {
        Map<String, Integer> response = new HashMap<>();
        response.put("value", 1);
        return response;
    }

    @RequestMapping(value = "/false", method = RequestMethod.GET)
    public Map<String, Boolean> getFalse() {
        Map<String, Boolean> response = new HashMap<>();
        response.put("value", service.getFalse());
        return response;
    }

    @RequestMapping(value = "/false", method = RequestMethod.GET, params = "n")
    public Map<String, Integer> getFalseNumeric(@RequestParam("n") String n) {
        Map<String, Integer> response = new HashMap<>();
        response.put("value", 0);
        return response;
    }

}
