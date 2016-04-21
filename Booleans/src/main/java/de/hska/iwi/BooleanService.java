package de.hska.iwi;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class BooleanService {

    private Map<Integer, Boolean> service;

    public BooleanService() {
        service = new HashMap<>();
    }

    @PostConstruct
    private void init() {
        service.put(0, false);
        service.put(1, true);
    }

    public boolean getFalse() {
        return service.get(0);
    }

    public boolean getTrue() {
        return service.get(1);
    }

}
