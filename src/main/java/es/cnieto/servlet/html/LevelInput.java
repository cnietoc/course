package es.cnieto.servlet.html;

import es.cnieto.domain.CourseLevel;
import es.cnieto.domain.CourseLevelsReadService;

import javax.servlet.http.HttpServletRequest;

public class LevelInput {
    private static final String PARAMETER_NAME = "level";
    private static final String INPUT_LABEL_TEXT = "Nivel: ";
    private final CourseLevelsReadService courseLevelsReadService;

    public LevelInput(CourseLevelsReadService courseLevelsReadService) {
        this.courseLevelsReadService = courseLevelsReadService;
    }

    String getHtml() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<label>" + INPUT_LABEL_TEXT + "<select name=\"" + PARAMETER_NAME + "\"> ");
        for (CourseLevel courseLevel : courseLevelsReadService.readLevels()) {
            stringBuilder.append("<option value=\"");
            stringBuilder.append(courseLevel.getId());
            stringBuilder.append("\">");
            stringBuilder.append(courseLevel.getLevel());
            stringBuilder.append("</option>");
        }
        stringBuilder.append("</select></label>");

        return stringBuilder.toString();
    }

    Integer getValueFrom(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter(PARAMETER_NAME));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
