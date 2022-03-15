package com.buddiend.buddiend.apis;

import java.util.*;
import com.buddiend.buddiend.models.Topic;
import com.buddiend.buddiend.services.TopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    private final TopicService topicsService;

    public TestController(TopicService topicsService) {
        this.topicsService = topicsService;
    }

    @GetMapping
    public List<Topic> getTest() {
        return this.topicsService.findAll();
    }
}
