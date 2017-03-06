package com.bangware.shengyibao.activity.Suggest.presenter;

import com.bangware.shengyibao.user.model.entity.User;

/**
 * Created by bangware on 2016/8/23.
 */
public interface SuggestPresenter {
    void loadData(User user,String content);
    void destory();
}
