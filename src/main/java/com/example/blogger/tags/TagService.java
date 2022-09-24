package com.example.blogger.tags;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagsRepository) {
        this.tagRepository = tagsRepository;
    }

    public List<TagEntity> getAllTags(){
        return tagRepository.findAll();
    }

    public TagEntity createTag(String tag){
        return tagRepository.save(new TagEntity(tag));
    }

    public List<TagEntity> createTags(List<String> tags){
        List<TagEntity> tagEntities = new ArrayList<>();
        for(String tag: tags){
            if(!tagRepository.findByName(tag).isPresent())
                this.createTag(tag);
            tagEntities.add(tagRepository.findByName(tag).get());
        }
        return tagEntities;
    }

}
