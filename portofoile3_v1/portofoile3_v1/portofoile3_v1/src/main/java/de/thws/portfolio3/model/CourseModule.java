    package de.thws.portfolio3.model;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import jakarta.persistence.*;

    @Entity // to map this class to our database, this for   hibernate
    @Table(name = "course_module")
    public class CourseModule  { // extends RepresentationModel<CourseModule>
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String moduleName;
        private int semester;
        private int creditPoints;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "university_id")
        @JsonBackReference
        private University university;


        public CourseModule(String moduleName, int semester, int creditPoints, University university)  {

            this.moduleName = moduleName;
            this.semester = semester;
            this.creditPoints = creditPoints;
            this.university = university;

        }

        public CourseModule() {

        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public University getUniversity() {
            return university;
        }

        public void setUniversity(University university) {
            this.university = university;
        }

        // Getters and Setters
        public String getModuleName() {
            return moduleName;
        }

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }

        public int getSemester() {
            return semester;
        }

        public void setSemester(int semester) {
            if (semester == 1 || semester == 2) {
                this.semester = semester;
            } else {
                throw new IllegalArgumentException("Semester must be 1 (spring) or 2 (autumn)");
            }
        }

        public int getCreditPoints() {
            return creditPoints;
        }

        public void setCreditPoints(int creditPoints) {
            if (creditPoints > 0) {
                this.creditPoints = creditPoints;
            } else {
                throw new IllegalArgumentException("Credit points must be greater than 0");
            }
        }
    }
