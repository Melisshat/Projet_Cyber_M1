package com.livefox.video.controller;

import com.livefox.video.exception.VideoNotFoundException;
import com.livefox.video.model.Video;
import com.livefox.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping(value = "/video")
    public List<Video> listVideo(){
        List<Video> video = videoRepository.findAll();
        if(video.isEmpty()) throw new VideoNotFoundException("Aucune video disponible");
        return video ;
    }

    @GetMapping(value = "/autofilled")
    public void autofilled(){
        Video video = new Video(1,"Video drole","video drole path","https://i.pinimg.com/originals/cd/cd/df/cdcddfdd339538200ff675e47c62ab4a.jpg");
        videoRepository.save(video);

    }

    @GetMapping(value = "/video/{id}")
    public Video showVideo(@PathVariable int id){
        Video video = videoRepository.findById(id);

        if(video == null) throw new VideoNotFoundException("Cette video n'existe pas");
        return video;
    }

    @PostMapping(value = "/video/add")
    public void addVideo (@Valid @RequestBody Video video){
        videoRepository.save(video);
    }

    @DeleteMapping(value = "/video/delete/{id]")
    public void deleteVideo(@PathVariable int id){
        videoRepository.deleteById(id);
    }

    @PutMapping(value = "/video/update/{id}")
    public void updateVideo (@RequestBody Video video){
        videoRepository.save(video);
    }
}
