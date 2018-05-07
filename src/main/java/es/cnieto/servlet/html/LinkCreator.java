package es.cnieto.servlet.html;

import java.io.Serializable;

public class LinkCreator implements Serializable {
    public LinkCreator() {
    }

    String createLinkFor(String text, int page, OrderParameter orderParameter) {
        return "<a href=\"" + hrefFor(page, orderParameter) + "\" >" + text + "</a>";
    }

    String hrefFor(int page, OrderParameter orderParameter) {
        return "?page=" + page + "&order=" + orderParameter.getValue();
    }
}