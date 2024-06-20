    package de.thws.portfolio3.model;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;

    import java.time.LocalDate;
    import java.util.List;

    @Entity // to map this class to our database, this for   hibernate
    @Table(name = "university")
    public class University  { //  extends RepresentationModel<University>

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String country;
        private String departmentName;
        private String departmentUrl;
        private String contactPerson;
        private int studentsWeCanSend;
        private int studentsWeCanAccept;
        private LocalDate nextSpringSemesterStart;
        private LocalDate nextAutumnSemesterStart;
        @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        @JsonManagedReference
        private List<CourseModule> courseModules;

        public University( String name, String country, String departmentName, String departmentUrl,
                          String contactPerson, int studentsWeCanSend, int studentsWeCanAccept,
                          LocalDate nextSpringSemesterStart, LocalDate nextAutumnSemesterStart, List<CourseModule> courseModules) {


            this.name = name;
            this.country = country;
            this.departmentName = departmentName;
            this.departmentUrl = departmentUrl;
            this.contactPerson = contactPerson;
            this.studentsWeCanSend = studentsWeCanSend;
            this.studentsWeCanAccept = studentsWeCanAccept;
            this.nextSpringSemesterStart = nextSpringSemesterStart;
            this.nextAutumnSemesterStart = nextAutumnSemesterStart;
            this.courseModules = courseModules;
        }
        public University() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public List<CourseModule> getCourseModules() {
            return courseModules;
        }

        public void setCourseModules(List<CourseModule> courseModules) {
            this.courseModules = courseModules;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setUniversityName(String universityName) {
            this.name = universityName;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getDepartmentUrl() {
            return departmentUrl;
        }

        public void setDepartmentUrl(String departmentUrl) {
            this.departmentUrl = departmentUrl;
        }

        public String getContactPerson() {
            return contactPerson;
        }

        public void setContactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
        }

        public int getStudentsWeCanSend() {
            return studentsWeCanSend;
        }

        public void setStudentsWeCanSend(int studentsWeCanSend) {
            if (studentsWeCanSend >= 0) {
                this.studentsWeCanSend = studentsWeCanSend;
            } else {
                throw new IllegalArgumentException("Number of students we can send must be >= 0");
            }
        }

        public int getStudentsWeCanAccept() {
            return studentsWeCanAccept;
        }

        public void setStudentsWeCanAccept(int studentsWeCanAccept) {
            if (studentsWeCanAccept >= 0) {
                this.studentsWeCanAccept = studentsWeCanAccept;
            } else {
                throw new IllegalArgumentException("Number of students we can accept must be >= 0");
            }
        }

        public LocalDate getNextSpringSemesterStart() {
            return nextSpringSemesterStart;
        }

        public void setNextSpringSemesterStart(LocalDate nextSpringSemesterStart) {
            this.nextSpringSemesterStart = nextSpringSemesterStart;
        }

        public LocalDate getNextAutumnSemesterStart() {
            return nextAutumnSemesterStart;
        }

        public void setNextAutumnSemesterStart(LocalDate nextAutumnSemesterStart) {
            this.nextAutumnSemesterStart = nextAutumnSemesterStart;
        }


    }

