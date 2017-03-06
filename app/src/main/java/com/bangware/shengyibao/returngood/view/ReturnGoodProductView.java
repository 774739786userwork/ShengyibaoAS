package com.bangware.shengyibao.returngood.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.returngood.model.entity.ReturnNoteGoods;

import java.util.List;


public interface ReturnGoodProductView extends BaseView {
 public void queryReturnNoteProduct(List<ReturnNoteGoods> noteGoodsList);
}
