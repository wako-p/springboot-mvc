package jp.wako.demo.springbootmvc.infra.shared.sort;

import lombok.Getter;

@Getter
public enum Order {

    ASC("asc"),
    DESC("desc");

    private final String statement;

    private Order(final String statement) {
        this.statement = statement;
    }

    public static Order parseBy(final String statement) {
        for (final Order order : values()) {
            if (order.statement.equals(statement)) {
                return order;
            }
        }
        throw new IllegalArgumentException("未定義の並び順です。");
    }

}
