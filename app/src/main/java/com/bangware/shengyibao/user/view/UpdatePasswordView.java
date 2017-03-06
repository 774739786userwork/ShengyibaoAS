package com.bangware.shengyibao.user.view;

import com.bangware.shengyibao.activity.BaseView;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface UpdatePasswordView extends BaseView{
    void updateSuccess();
    void onUpdateError(String ErrorMessage);
}
