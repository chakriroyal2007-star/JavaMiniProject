package com.classroom.cse_a_classroom.service;

import com.classroom.cse_a_classroom.model.Classroom;
import com.classroom.cse_a_classroom.model.User;
import com.classroom.cse_a_classroom.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    public Classroom createClassroom(String name, User teacher) {
        Classroom classroom = Classroom.builder()
                .name(name)
                .teacher(teacher)
                .build();
        return classroomRepository.save(classroom);
    }

    public Classroom joinClassroom(String joinCode, User student) {
        Classroom classroom = classroomRepository.findByJoinCode(joinCode)
                .orElseThrow(() -> new RuntimeException("Invalid Join Code"));
        
        if (!classroom.getMembers().contains(student)) {
            classroom.getMembers().add(student);
            return classroomRepository.save(classroom);
        }
        return classroom;
    }

    public List<Classroom> getTeacherClassrooms(User teacher) {
        return classroomRepository.findByTeacher(teacher);
    }

    public List<Classroom> getStudentClassrooms(User student) {
        return classroomRepository.findByMembersContaining(student);
    }

    public Classroom getClassroomById(Long id) {
        return classroomRepository.findById(id).orElseThrow(() -> new RuntimeException("Classroom not found"));
    }
}
