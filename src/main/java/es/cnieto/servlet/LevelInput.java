package es.cnieto.servlet;

import es.cnieto.domain.CourseLevel;
import es.cnieto.domain.CourseLevelsReadService;

import javax.servlet.http.HttpServletRequest;

class LevelInput {
    private static final String PARAMETER_NAME = "level";
    private final CourseLevelsReadService courseLevelsReadService;

    LevelInput(CourseLevelsReadService courseLevelsReadService) {
        this.courseLevelsReadService = courseLevelsReadService;
    }

    String getHtml() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<label>Nivel: <select name=\"" + PARAMETER_NAME + "\"> ");
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
