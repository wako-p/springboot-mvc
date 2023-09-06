package jp.wako.demo.springbootmvc.infra.issues.sort;

import jp.wako.demo.springbootmvc.infra.shared.sort.Order;

public final class Sort {

    private final Column column;
    private final Order order;

    public Sort(final Column column, final Order order) {
        this.column = column;
        this.order = order;
    }

    @Override
    public String toString() {
        return column.getName() + " " + order.getStatement();
    }

}
