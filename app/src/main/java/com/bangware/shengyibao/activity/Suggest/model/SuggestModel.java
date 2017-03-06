package com.bangware.shengyibao.activity.Suggest.model;

import com.bangware.shengyibao.activity.Suggest.presenter.SuggestListener;
import com.bangware.shengyibao.user.model.entity.User;

/**
 * Created by bangware on 2016/8/23.
 */
public interface SuggestModel {
    void onLoadsubmit(String requestTag,String content, User user, SuggestListener suggestListener);
}
