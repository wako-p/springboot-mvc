package jp.wako.demo.springbootmvc.infra.issues.sort;

import lombok.Getter;

@Getter
public enum Column {

    ID("id"),
    TITLE("title"),
    UPDATEDAT("updated_at");

    private final String name;

    private Column(final String column) {
        this.name = column;
    }

    public static Column parseBy(final String name) {
        for (final Column column : values()) {
            if (column.name.equals(name)) {
                return column;
            }
        }
        throw new IllegalArgumentException("未定義の列です。");
    }

}
