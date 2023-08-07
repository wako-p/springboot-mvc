package jp.wako.demo.springbootmvc.infra.shared;

public class OptimisticLockVersionValidator implements jp.wako.demo.springbootmvc.usecase.shared.OptimisticLockVersionValidator {
    public boolean validate(final int version1, final int version2) {
        return version1 == version2;
    }
}
