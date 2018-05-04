package es.cnieto.servlet.html;

import es.cnieto.domain.CourseLevelsReadService;

import javax.servlet.http.HttpServletRequest;

public class CreateCourseBox {
    private final TitleInput titleInput;
    private final HoursInput hoursInput;
    private final LevelInput levelInput;
    private final ActiveInput activeInput;

    public CreateCourseBox(CourseLevelsReadService courseLevelsReadService) {
        this.titleInput = new TitleInput();
        this.hoursInput = new HoursInput();
        this.activeInput = new ActiveInput();
        this.levelInput = new LevelInput(courseLevelsReadService);
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
                "<h1>A&ntilde;adir nuevo curso</h1>" +
                "<form method=\"post\">" +
                titleInput.getHtml() +
                hoursInput.getHtml() +
                activeInput.getHtml() +
                levelInput.getHtml() +
                "<input type=\"submit\" value=\"Crear\" />" +
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
}
