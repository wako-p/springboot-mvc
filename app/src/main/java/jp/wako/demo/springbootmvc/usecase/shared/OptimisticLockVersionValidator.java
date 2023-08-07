package jp.wako.demo.springbootmvc.usecase.shared;

public interface OptimisticLockVersionValidator {
    boolean validate(final int version1, final int version2);
}
