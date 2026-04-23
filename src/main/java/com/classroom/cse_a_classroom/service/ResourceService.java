package com.classroom.cse_a_classroom.service;

import com.classroom.cse_a_classroom.model.Resource;
import com.classroom.cse_a_classroom.model.Classroom;
import com.classroom.cse_a_classroom.model.User;
import com.classroom.cse_a_classroom.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public Resource createResource(String title, String topic, String description, String fileType, String fileUrl, User teacher, com.classroom.cse_a_classroom.model.Classroom classroom) {
        Resource resource = Resource.builder()
                .title(title)
                .topic(topic)
                .description(description)
                .fileType(fileType)
                .fileUrl(fileUrl)
                .teacher(teacher)
                .classroom(classroom)
                .build();
        return resourceRepository.save(resource);
    }

    public List<Resource> getClassroomResources(Classroom classroom) {
        return resourceRepository.findByClassroom(classroom);
    }

    public List<Resource> getTeacherResources(User teacher) {
        return resourceRepository.findByTeacher(teacher);
    }

    public void deleteResource(Long id) {
        Resource r = resourceRepository.findById(id).orElse(null);
        if (r != null) {
            // Physically delete the file FIRST
            String url = r.getFileUrl();
            if (url != null && url.startsWith("/uploads/")) {
                String fileName = url.substring("/uploads/".length());
                fileStorageService.deleteFile(fileName);
            }
            // Direct delete from DB - safe due to PERSIST/MERGE cascades
            resourceRepository.delete(r);
            resourceRepository.flush(); 
        }
    }
}
