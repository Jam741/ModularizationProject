package com.yingwumeijia.baseywmj.function.extra;



import com.yingwumeijia.baseywmj.base.JBasePresenter;
import com.yingwumeijia.baseywmj.base.JBaseView;
import com.yingwumeijia.baseywmj.entity.bean.BillServiceInfo;
import com.yingwumeijia.baseywmj.entity.bean.BillSimpleInfo;
import com.yingwumeijia.baseywmj.entity.bean.PayBillInfo;

/**
 * Created by Jam on 2017/3/21 下午3:05.
 * Describe:
 */

public interface CreateBillServicContract {

    interface View extends JBaseView {


        void initCreateService(BillServiceInfo billServiceInfo);

        void showCompanyName(String companyName);

        void showCompanyPortrait(String portrait);

        void toCashier(String bill);

        void initBillSimple(BillSimpleInfo billSimpleInfo);
    }

    interface Presenter extends JBasePresenter {


        void getBillService(String sessionId);

        void checkBillSimple(String billId);

        void createPayBill(PayBillInfo payBillInfo);
    }
}
