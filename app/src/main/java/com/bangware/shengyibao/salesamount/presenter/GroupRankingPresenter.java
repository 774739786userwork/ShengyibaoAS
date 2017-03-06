package com.bangware.shengyibao.salesamount.presenter;

import com.bangware.shengyibao.user.model.entity.User;

/**
 * Created by bangware on 2016/8/25.
 */
public interface GroupRankingPresenter {
    void loadGroupRankingData(User user,String begin_date, String end_date);
    void destory();
}
