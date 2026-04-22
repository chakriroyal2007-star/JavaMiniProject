const API = {
    // Auth
    login: async (credentials) => {
        // Handled by Form POST in this version for simplicity with Spring Security
    },
    register: async (user) => {
        const res = await fetch('/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(user)
        });
        if (!res.ok) throw await res.text();
        return res.json();
    },
    getCurrentUser: async () => {
        const res = await fetch('/auth/me');
        if (!res.ok) return null;
        return res.json();
    },

    // Classrooms
    createClassroom: async (name) => {
        const res = await fetch(`/api/classrooms/create?name=${encodeURIComponent(name)}`, { method: 'POST' });
        return res.json();
    },
    joinClassroom: async (joinCode) => {
        const res = await fetch(`/api/classrooms/join?joinCode=${encodeURIComponent(joinCode)}`, { method: 'POST' });
        if (!res.ok) throw await res.text();
        return res.json();
    },

    // Resources
    getClassroomResources: async (classroomId) => {
        const res = await fetch(`/student/resources?classroomId=${classroomId}`);
        return res.json();
    },

    // Quizzes
    getClassroomQuizzes: async (classroomId) => {
        const res = await fetch(`/student/quizzes?classroomId=${classroomId}`);
        return res.json();
    },
    validateQuiz: async (quizId, password) => {
        const res = await fetch(`/student/quiz/validate?quizId=${quizId}&password=${encodeURIComponent(password)}`, { method: 'POST' });
        if (!res.ok) throw await res.text();
        return true;
    },
    submitQuiz: async (quizId, answers) => {
        const res = await fetch(`/student/quiz/submit?quizId=${quizId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(answers)
        });
        return res.json();
    },

    // Announcements
    getAnnouncements: async (classroomId) => {
        const res = await fetch(`/api/announcements?classroomId=${classroomId}`);
        return res.json();
    }
};
