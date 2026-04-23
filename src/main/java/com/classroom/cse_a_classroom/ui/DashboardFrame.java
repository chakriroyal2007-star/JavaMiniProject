package com.classroom.cse_a_classroom.ui;

import com.classroom.cse_a_classroom.model.*;
import com.classroom.cse_a_classroom.service.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class DashboardFrame extends JFrame {
    private User currentUser;
    private JPanel sidebar;
    private JPanel contentArea;
    private Classroom currentClassroom;

    // Theme Colors (Premium Indigo Style)
    private final Color CLR_INDIGO = new Color(79, 70, 229);
    private final Color CLR_GREEN = new Color(34, 197, 94);
    private final Color CLR_BG = new Color(248, 250, 252);
    private final Color CLR_SIDEBAR = new Color(30, 41, 59);

    public DashboardFrame(User user) {
        this.currentUser = user;
        setTitle("CSE A Classroom Desktop - " + user.getName());
        setSize(1100, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setupUI();
        showDashboardHome();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setBackground(CLR_SIDEBAR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(30, 20, 30, 20));

        JLabel brand = new JLabel("CSE A CLASSROOM");
        brand.setForeground(new Color(99, 102, 241));
        brand.setFont(new Font("SansSerif", Font.BOLD, 18));
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(brand);
        sidebar.add(Box.createVerticalStrut(40));

        addNavBtn("🏠  Dashboard", this::showDashboardHome);
        addNavBtn("📚  Classrooms", this::showClassrooms);
        addNavBtn("📁  Resources", this::showMaterials);
        addNavBtn("📝  Quizzes", this::showQuizzes);
        addNavBtn("📢  Announcements", this::showAnnouncements);
        if (currentUser.getRole() == Role.TEACHER) {
            addNavBtn("📊  Results", this::showResults);
        }
        
        sidebar.add(Box.createVerticalGlue());
        addNavBtn("🚪  Logout", () -> { dispose(); new LoginFrame().setVisible(true); });

        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(CLR_BG);
        add(sidebar, BorderLayout.WEST);
        add(contentArea, BorderLayout.CENTER);
    }

    private void addNavBtn(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(210, 45));
        btn.setBackground(CLR_SIDEBAR);
        btn.setForeground(new Color(203, 213, 225));
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorder(new EmptyBorder(0, 10, 0, 0));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> action.run());
        sidebar.add(btn);
        sidebar.add(Box.createVerticalStrut(5));
    }

    private void showDashboardHome() {
        contentArea.removeAll();
        JPanel p = new JPanel(new BorderLayout(0, 30));
        p.setOpaque(false); p.setBorder(new EmptyBorder(30,30,30,30));
        JLabel h = new JLabel("Welcome back, " + currentUser.getName());
        h.setFont(new Font("SansSerif", Font.BOLD, 28));
        p.add(h, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 3, 20, 0)); cards.setOpaque(false);
        cards.add(createCard("Active Rooms", "5 Classes", CLR_INDIGO));
        cards.add(createCard("Materials", "12 Docs", CLR_GREEN));
        cards.add(createCard("Assessments", "3 Pending", new Color(249, 115, 22)));
        p.add(cards, BorderLayout.CENTER);

        contentArea.add(p); contentArea.revalidate(); contentArea.repaint();
    }

    private JPanel createCard(String title, String val, Color c) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE); card.setBorder(new EmptyBorder(20,20,20,20));
        card.putClientProperty("FlatLaf.style", "arc: 15");
        JLabel t = new JLabel(title); t.setForeground(Color.GRAY);
        JLabel v = new JLabel(val); v.setFont(new Font("SansSerif", Font.BOLD, 22)); v.setForeground(c);
        card.add(t, BorderLayout.NORTH); card.add(v, BorderLayout.CENTER);
        return card;
    }

    private void showClassrooms() {
        contentArea.removeAll();
        JPanel p = new JPanel(new BorderLayout(0, 20)); p.setOpaque(false); p.setBorder(new EmptyBorder(30,30,30,30));
        JLabel title = new JLabel("Your Classrooms"); title.setFont(new Font("SansSerif", Font.BOLD, 24));
        p.add(title, BorderLayout.NORTH);

        DefaultListModel<Classroom> model = new DefaultListModel<>();
        List<Classroom> list = (currentUser.getRole() == Role.TEACHER) 
            ? SpringContext.getBean(ClassroomService.class).getTeacherClassrooms(currentUser)
            : SpringContext.getBean(ClassroomService.class).getStudentClassrooms(currentUser);
        for(Classroom c : list) model.addElement(c);

        JList<Classroom> jlist = new JList<>(model);
        jlist.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean f) {
                JLabel lab = (JLabel) super.getListCellRendererComponent(l, ((Classroom)v).getName(), i, s, f);
                lab.setBorder(new EmptyBorder(10,10,10,10)); return lab;
            }
        });
        p.add(new JScrollPane(jlist), BorderLayout.CENTER);

        JButton enter = new JButton("Enter Selected Room");
        enter.addActionListener(e -> {
            if(jlist.getSelectedValue() != null) {
                currentClassroom = jlist.getSelectedValue();
                showMaterials();
            }
        });
        p.add(enter, BorderLayout.SOUTH);
        contentArea.add(p); contentArea.revalidate(); contentArea.repaint();
    }

    private void showMaterials() {
        if(currentClassroom == null) { showClassrooms(); return; }
        contentArea.removeAll();
        JPanel p = new JPanel(new BorderLayout(0, 20)); p.setOpaque(false); p.setBorder(new EmptyBorder(30,30,30,30));
        p.add(new JLabel("📦 " + currentClassroom.getName() + " Resources", SwingConstants.LEFT), BorderLayout.NORTH);
        
        JPanel grid = new JPanel(new GridLayout(0, 3, 15, 15)); grid.setOpaque(false);
        List<Resource> rs = SpringContext.getBean(ResourceService.class).getClassroomResources(currentClassroom);
        for(Resource r : rs) {
            JPanel card = new JPanel(new BorderLayout(0, 5)); card.setBackground(Color.WHITE); card.setBorder(new EmptyBorder(15,15,15,15));
            card.putClientProperty("FlatLaf.style", "arc: 12");
            card.add(new JLabel(r.getFileType()), BorderLayout.NORTH);
            card.add(new JLabel(r.getTitle()), BorderLayout.CENTER);
            JButton view = new JButton("Open"); 
            view.addActionListener(e -> { 
                try {
                    String url = r.getFileUrl();
                    if(url.startsWith("/")) url = "http://localhost:8080" + url;
                    Desktop.getDesktop().browse(new java.net.URI(url));
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(this, "Could not open: " + ex.getMessage());
                }
            });
            card.add(view, BorderLayout.SOUTH);
            grid.add(card);
        }
        p.add(new JScrollPane(grid), BorderLayout.CENTER);
        
        if (currentUser.getRole() == Role.TEACHER) {
            JButton uploadBtn = new JButton("+ Upload PDF/PPT File");
            uploadBtn.setBackground(CLR_GREEN);
            uploadBtn.setForeground(Color.WHITE);
            uploadBtn.addActionListener(e -> uploadMaterialAction());
            p.add(uploadBtn, BorderLayout.SOUTH);
        }

        contentArea.add(p); contentArea.revalidate(); contentArea.repaint();
    }

    private void uploadMaterialAction() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select File (PDF, PPT, EXCEL)");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            String title = JOptionPane.showInputDialog(this, "Title:", selectedFile.getName());
            String topic = JOptionPane.showInputDialog(this, "Topic:");
            
            if (title != null && !title.isEmpty()) {
                try {
                    FileStorageService fs = SpringContext.getBean(FileStorageService.class);
                    String fileName = fs.storeFile(selectedFile);
                    
                    String ext = selectedFile.getName().toLowerCase();
                    String type = "PDF";
                    if(ext.endsWith(".ppt") || ext.endsWith(".pptx")) type = "PPT";
                    else if(ext.endsWith(".xls") || ext.endsWith(".xlsx")) type = "EXCEL";

                    SpringContext.getBean(ResourceService.class).createResource(
                        title, topic, "Native Upload", type, "/uploads/" + fileName, currentUser, currentClassroom
                    );
                    JOptionPane.showMessageDialog(this, "File Uploaded Successfully!");
                    showMaterials();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        }
    }

    private void showQuizzes() {
        if(currentClassroom == null) { showClassrooms(); return; }
        contentArea.removeAll();
        JPanel p = new JPanel(new BorderLayout(0, 20)); p.setOpaque(false); p.setBorder(new EmptyBorder(30,30,30,30));
        p.add(new JLabel("🎲 " + currentClassroom.getName() + " Assessments", SwingConstants.LEFT), BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 3, 15, 15)); grid.setOpaque(false);
        List<Quiz> quizzes = SpringContext.getBean(QuizService.class).getClassroomQuizzes(currentClassroom);
        for(Quiz q : quizzes) {
            JPanel card = new JPanel(new BorderLayout(0, 5)); card.setBackground(Color.WHITE); card.setBorder(new EmptyBorder(15,15,15,15));
            card.putClientProperty("FlatLaf.style", "arc: 12");
            card.add(new JLabel(q.getDifficulty()), BorderLayout.NORTH);
            card.add(new JLabel(q.getTitle()), BorderLayout.CENTER);
            JButton start = new JButton("Attempt Quiz");
            start.addActionListener(e -> {
                String pass = JOptionPane.showInputDialog(this, "Enter Quiz Key:");
                if(SpringContext.getBean(QuizService.class).validatePassword(q.getId(), pass)) {
                    new QuizAttemptFrame(q, currentUser).setVisible(true);
                } else JOptionPane.showMessageDialog(this, "Invalid Quiz Key!");
            });
            card.add(start, BorderLayout.SOUTH);
            grid.add(card);
        }
        p.add(new JScrollPane(grid), BorderLayout.CENTER);
        contentArea.add(p); contentArea.revalidate(); contentArea.repaint();
    }

    private void showAnnouncements() {
        if(currentClassroom == null) { showClassrooms(); return; }
        contentArea.removeAll();
        JPanel p = new JPanel(new BorderLayout(0, 20)); p.setOpaque(false); p.setBorder(new EmptyBorder(30,30,30,30));
        p.add(new JLabel("📢 Announcements Feed"), BorderLayout.NORTH);
        contentArea.add(p); contentArea.revalidate(); contentArea.repaint();
    }

    private void showResults() {
        contentArea.removeAll();
        JPanel p = new JPanel(new BorderLayout(0, 20)); p.setOpaque(false); p.setBorder(new EmptyBorder(30,30,30,30));
        p.add(new JLabel("📊 Student Performance Analytics"), BorderLayout.NORTH);
        contentArea.add(p); contentArea.revalidate(); contentArea.repaint();
    }
}
