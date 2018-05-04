package es.cnieto.domain;

import java.util.Objects;

public class Teacher {
    private final int id;
    private final String name;
    private final String mail;

    public Teacher(int id, String name, String mail) {
        this.id = id;
        this.name = name;
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id &&
                Objects.equals(name, teacher.name) &&
                Objects.equals(mail, teacher.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mail);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
