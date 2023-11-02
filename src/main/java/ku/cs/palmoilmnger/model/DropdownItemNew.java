package ku.cs.palmoilmnger.model;

import lombok.Data;

@Data
public class DropdownItemNew {
    String name;
    String href;

    public DropdownItemNew(String name, String href) {
        this.name = name;
        this.href = href;
    }
}