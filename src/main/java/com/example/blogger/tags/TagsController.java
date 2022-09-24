package com.example.blogger.tags;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TagsController {
    private final TagService tags;

    public TagsController(TagService tags) {
        this.tags = tags;
    }

    @GetMapping("/tags")
    ResponseEntity<List<TagEntity>> getTags(){
        return ResponseEntity.ok(tags.getAllTags());
    }
}
