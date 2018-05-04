package es.cnieto.servlet.html;

import es.cnieto.domain.Teacher;
import es.cnieto.domain.TeachersReadService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class TeacherInput {
    private static final String PARAMETER_NAME = "teacher";
    private static final String INPUT_LABEL_TEXT = "Profesor: ";
    private static final String NOT_TEACHER_TEXT = "Sin profesor asignado";
    private final TeachersReadService teachersReadService;

    public TeacherInput(TeachersReadService teachersReadService) {
        this.teachersReadService = teachersReadService;
    }

    String getHtml() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<label>" + INPUT_LABEL_TEXT + "<select name=\"" + PARAMETER_NAME + "\"> ");
        stringBuilder.append("<option value=\"\">" + NOT_TEACHER_TEXT + "</option>");
        for (Teacher teacher : teachersReadService.readTeachers()) {
            stringBuilder.append("<option value=\"").append(teacher.getId()).append("\">")
                    .append(teacher.getName()).append(" (").append(teacher.getMail()).append(")")
                    .append("</option>");
        }
        stringBuilder.append("</select></label>");

        return stringBuilder.toString();
    }

    Optional<Integer> getValueFrom(HttpServletRequest request) {
        try {
            return Optional.of(Integer.parseInt(request.getParameter(PARAMETER_NAME)));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
