package org.fundacionjala.bdd.api.utils.alejandraNeolopan;

import java.util.ArrayList;
import java.util.List;

public class PicoContainer {
    private List<String> ids;
    public PicoContainer() {
        ids = new ArrayList<>();
    }

    public void addNewId(final String id) {
        ids.add(id);
    }
    public List<String> getIds() {
        return ids;
    }
}
