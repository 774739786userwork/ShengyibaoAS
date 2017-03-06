package com.bangware.shengyibao.customer.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.customer.model.entity.RegionalArea;

import java.util.List;

/**
 * Created by ccssll on 2016/7/31.
 */
public interface RegionalAreaView extends BaseView{
    void queryRegionalArea(List<RegionalArea> regionalAreas);
}
