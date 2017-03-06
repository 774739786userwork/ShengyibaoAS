package com.bangware.shengyibao.activity.Suggest.presenter;

/**
 * Created by bangware on 2016/8/23.
 */
public interface SuggestListener {
    void onSubmitSuccess(String successMessage);
    void onSubmitFailure(String errorMessage);
}
