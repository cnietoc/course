package es.cnieto.servlet.html;

import es.cnieto.domain.CourseLevelsReadService;
import es.cnieto.domain.TeachersReadService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CreateCourseBox {
    private static final String BR = "<br />";
    private static final String TITLE_TEXT = "A&ntilde;adir nuevo curso";
    private static final String SUBMIT_TEXT = "Crear";
    private final TitleInput titleInput;
    private final HoursInput hoursInput;
    private final LevelInput levelInput;
    private final ActiveInput activeInput;
    private final TeacherInput teacherInput;

    public CreateCourseBox(TitleInput titleInput, HoursInput hoursInput, LevelInput levelInput, ActiveInput activeInput, TeacherInput teacherInput) {
        this.titleInput = titleInput;
        this.hoursInput = hoursInput;
        this.levelInput = levelInput;
        this.activeInput = activeInput;
        this.teacherInput = teacherInput;
    }

    public String getHtml(String errorMessage) {
        StringBuilder html = new StringBuilder();
        if (errorMessage != null)
            html.append(getErrorMessageHtml(errorMessage));
        html.append(getInputs());
        return html.toString();
    }

    private String getErrorMessageHtml(String errorMessage) {
        return "<div><h2>Error: " + errorMessage + "</h1></div>";
    }

    private String getInputs() {
        return "<div>" +
                "<h1>" + TITLE_TEXT + "</h1>" +
                "<form method=\"post\">" +
                titleInput.getHtml() + BR +
                hoursInput.getHtml() + BR +
                activeInput.getHtml() + BR +
                levelInput.getHtml() + BR +
                teacherInput.getHtml() + BR +
                "<input type=\"submit\" value=\"" + SUBMIT_TEXT + "\" />" +
                "</form>" +
                "</div>";
    }

    public String getCourseTitleFrom(HttpServletRequest request) {
        return titleInput.getValueFrom(request);
    }

    public Boolean getCourseActiveFrom(HttpServletRequest request) {
        return activeInput.getValueFrom(request);
    }

    public Integer getCourseHoursFrom(HttpServletRequest request) {
        return hoursInput.getValueFrom(request);
    }

    public Integer getCourseLevelFrom(HttpServletRequest request) {
        return levelInput.getValueFrom(request);
    }

    public Optional<Integer> getCourseTeacherFrom(HttpServletRequest request) {
        return teacherInput.getValueFrom(request);
    }
}
