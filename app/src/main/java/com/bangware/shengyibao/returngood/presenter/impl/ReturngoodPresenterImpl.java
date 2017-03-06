package com.bangware.shengyibao.returngood.presenter.impl;
import com.bangware.shengyibao.returngood.model.ReturnGoodQueryModel;
import com.bangware.shengyibao.returngood.model.entity.ReturnNote;
import com.bangware.shengyibao.returngood.model.impl.ReturnGoodQueryModelImpl;
import com.bangware.shengyibao.returngood.presenter.OnReturnGoodListener;
import com.bangware.shengyibao.returngood.presenter.ReturngoodPresenter;
import com.bangware.shengyibao.returngood.view.ReturnGoodView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;

/**
 * 退货查询的请求实现
 * @author ccssll
 *
 */
public class ReturngoodPresenterImpl implements ReturngoodPresenter,OnReturnGoodListener {
	public static final String REQUEST_TAG = "returnGood";
	private ReturnGoodQueryModel goodModel;
	private ReturnGoodView goodView;
	private String requestTag;
	
	public ReturngoodPresenterImpl(ReturnGoodView goodView) {
		this.requestTag = REQUEST_TAG+System.currentTimeMillis();
		this.goodView=goodView;
		this.goodModel = new ReturnGoodQueryModelImpl();
	}
	//加载查询请求方法
	@Override
	public void loadreturnGood(User user,String begin_date, String end_date, int nPage,
							   int nSpage) {
		// TODO Auto-generated method stub
		goodView.showLoading();
		goodModel.loadReturnGood(requestTag, begin_date, end_date, nPage, nSpage, user, this);
	}

	//销毁请求队列
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		DataRequest.getInstance().cancelAllRequest();
	}
	//请求得到list并传递
	@Override
	public void onLoadDataSuccess(List<ReturnNote> returnGoodlist) {
		// TODO Auto-generated method stub
		goodView.hideLoading();
		goodView.loadReturnGoodData(returnGoodlist);
	}
	@Override
	public void onLoadDataFailure(String errorMessage) {
		// TODO Auto-generated method stub
		goodView.hideLoading();
		goodView.showFailureMsg(errorMessage);
	}

}
