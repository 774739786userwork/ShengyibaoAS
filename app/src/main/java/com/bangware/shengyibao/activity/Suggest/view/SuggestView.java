package com.bangware.shengyibao.activity.Suggest.view;

import com.bangware.shengyibao.activity.BaseView;

/**
 * Created by bangware on 2016/8/23.
 */
public interface SuggestView extends BaseView{
    void onLoadSuccess(String successMessage);
    void onLoadError(String ErrorMessage);
}
