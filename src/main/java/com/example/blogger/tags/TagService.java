package com.example.blogger.tags;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    public List<TagEntity> getAllTags();
    public TagEntity createTag(String tag);
    public List<TagEntity> createTags(List<String> tags);
}
