package jp.wako.demo.springbootmvc.presentation.shared.viewmodel;

import lombok.Data;

@Data
public class SearchableVM {
    protected int page;
    protected int size;
}
