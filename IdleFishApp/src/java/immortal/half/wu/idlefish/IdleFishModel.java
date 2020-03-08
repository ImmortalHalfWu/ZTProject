package immortal.half.wu.idlefish;

import com.sun.istack.internal.NotNull;
import immortal.half.wu.idlefish.pagers.*;
import immortal.half.wu.idlefish.pagers.posted.PostedPageProcess;
import immortal.half.wu.idlefish.pagers.posted.deletes.PostedDeleteOkPageProcess;
import immortal.half.wu.idlefish.pagers.posted.deletes.PostedDeleteSelectPageProcess;
import immotal.half.wu.appManager.pagers.IPagerFactory;
import immotal.half.wu.appManager.pagers.beans.UserInfoBean;
import immotal.half.wu.appManager.pagers.impls.DefaultPager;

import java.util.ArrayList;

class IdleFishModel {

    final static String IDLE_FISH_PACKAGE_NAME = "com.taobao.idlefish";

    private final static String IDLE_FISH_MAIN_PATH = "com.taobao.fleamarket.home.activity.MainActivity";
    private final static String IDLE_FISH_POSTED_PATH = ".webview.WeexWebViewActivity";
    private final static String IDLE_FISH_POSTED_CHOICE = ".post.activity.publishEntry.PublishEntryActivity";
    private final static String IDLE_FISH_POSTED_CHOICE_IMG = ".mms.activitys.MultiMediaStudioActivity";
    private final static String IDLE_FISH_POSTED_IMG_PROCESS = ".mms.activitys.MultiMediaEditorActivity";
    private final static String IDLE_FISH_POSTED_IMG_TAG = "com.idlefish.flutterbridge.flutterboost.FishFlutterActivity";
    private final static String IDLE_FISH_POSTED_PRODUCT_INFO = "com.idlefish.flutterbridge.flutterboost.FishFlutterActivity";

    @NotNull DefaultPager<Boolean> 去主页面 = IPagerFactory.create(
            IDLE_FISH_MAIN_PATH,
            "去主页面",
            new StartMainPageProcess(IDLE_FISH_PACKAGE_NAME, IDLE_FISH_MAIN_PATH));

    @NotNull DefaultPager<Boolean> 取消升级 = IPagerFactory.create(
            IDLE_FISH_MAIN_PATH,
            "取消升级",
            new RemoveUpdatePageProcess());

    @NotNull DefaultPager<Boolean> 前往首页我的 = IPagerFactory.create(
            IDLE_FISH_MAIN_PATH,
            "前往首页我的",
            MainPageProcess.createToMyPageProcess());

    @NotNull DefaultPager<Boolean> 是否登录fishIdle = IPagerFactory.create(
            IDLE_FISH_MAIN_PATH,
            "是否登录fishIdle",
            MyPagerProcess.createGetIsLoginPageProcess()
    );

    @NotNull DefaultPager<Boolean> 前往发布类型选择页 = IPagerFactory.create(
            IDLE_FISH_MAIN_PATH,
            "前往发布类型选择页",
            MainPageProcess.createToPostPageProcess());

    @NotNull DefaultPager<Boolean> 前往发布闲置 = IPagerFactory.create(
            IDLE_FISH_POSTED_CHOICE,
            "前往发布闲置",
            PostedChoicePagerProcess.createToPost());

    @NotNull DefaultPager<Boolean> 发布闲置图片选择imgs(int count) {
        return IPagerFactory.create(
                IDLE_FISH_POSTED_CHOICE_IMG,
                "发布闲置图片选择",
                ImagePagerProcess.createChoiceImgPageProcess(count));
    }

    @NotNull DefaultPager<Boolean> 前往图片编辑页 = IPagerFactory.create(
            IDLE_FISH_POSTED_CHOICE_IMG,
            "前往图片编辑页",
            ImagePagerProcess.createToImgPageProcess());

    @NotNull DefaultPager<Boolean> 前往图片标签选择页 = IPagerFactory.create(
            IDLE_FISH_POSTED_IMG_PROCESS,
            "前往图片标签选择页",
            ImageControlPageProcess.createToImageTag());

    @NotNull DefaultPager<Boolean> 输入商品Tag(@NotNull String tag) {
        return IPagerFactory.create(
                IDLE_FISH_POSTED_IMG_TAG,
                "输入商品tag",
                ImageTagPageProcess.createInputTag(tag));
    }

    @NotNull DefaultPager<Boolean> 选择第一个imgTAG(@NotNull String tag) {
        return IPagerFactory.create(
                        IDLE_FISH_POSTED_IMG_TAG,
                        "选择第一个img TAG",
                        ImageTagPageProcess.createChoiceTag(tag));
    }

    @NotNull DefaultPager<Boolean> 前往发布商品信息页 = IPagerFactory.create(
            IDLE_FISH_POSTED_IMG_PROCESS,
            "前往发布商品信息页",
            ImageControlPageProcess.createToProductInfo());

    @NotNull DefaultPager<Boolean> 商品信息页输入商品信息info(@NotNull String info) {
            return IPagerFactory.create(
                IDLE_FISH_POSTED_PRODUCT_INFO,
                "商品信息页输入商品信息",
                ProductInfoPagerProcess.createInputInfo(info));
    }

    @NotNull DefaultPager<Boolean> 商品信息页前往金额设置 = IPagerFactory.create(
            IDLE_FISH_POSTED_PRODUCT_INFO,
            "商品信息页前往金额设置",
            ProductInfoPagerProcess.createGoProductMoney());

    @NotNull DefaultPager<Boolean> 商品信息页设置包邮 = IPagerFactory.create(
            IDLE_FISH_POSTED_PRODUCT_INFO,
            "商品信息页设置包邮",
            ProductMoneyPagerProcess.createClickMill());

    @NotNull DefaultPager<Boolean> 商品信息页设置入手价 = IPagerFactory.create(
            IDLE_FISH_POSTED_PRODUCT_INFO,
            "商品信息页设置入手价",
            ProductMoneyPagerProcess.createClickBuyMoney());

    @NotNull DefaultPager<Boolean> 商品信息页设置一口价 = IPagerFactory.create(
            IDLE_FISH_POSTED_PRODUCT_INFO,
            "商品信息页设置一口价",
            ProductMoneyPagerProcess.createClickSellMoney());

    @NotNull DefaultPager<Boolean> 商品信息页完成金额设置 = IPagerFactory.create(
            IDLE_FISH_POSTED_PRODUCT_INFO,
            "商品信息页完成金额设置",
            ProductMoneyPagerProcess.createClickSuccess());

    @NotNull DefaultPager<Boolean> 商品信息页前往其他选项 = IPagerFactory.create(
            IDLE_FISH_POSTED_PRODUCT_INFO,
            "商品信息页前往其他选项",
            ProductInfoPagerProcess.createGoProductOther());

    @NotNull DefaultPager<Boolean> 商品信息页设置其他选项 = IPagerFactory.create(
            IDLE_FISH_POSTED_PRODUCT_INFO,
            "商品信息页设置其他选项",
            new PostProductOtherPagerProcess());

    @NotNull DefaultPager<Boolean> 完成闲置商品设置并发布 = IPagerFactory.create(
            IDLE_FISH_POSTED_PRODUCT_INFO,
            "完成闲置商品设置并发布",
            ProductInfoPagerProcess.createSendProduct());

    @NotNull DefaultPager<UserInfoBean> 获取登录信息 = IPagerFactory.create(
            IDLE_FISH_MAIN_PATH,
            "获取登录信息",
            MyPagerProcess.createGetUserInfoPageProcess()
    );

    @NotNull DefaultPager<Boolean> 前往已发布页 = IPagerFactory.create(
            IDLE_FISH_MAIN_PATH,
            "前往已发布页",
            MyPagerProcess.createToPostedPageProcess()
    );

    @NotNull DefaultPager<ArrayList<String>> 获取已发布商品 = IPagerFactory.create(
            IDLE_FISH_POSTED_PATH,
            "获取已发布商品",
            PostedPageProcess.createGetPostedNamesPageProcess()
    );

    @NotNull DefaultPager<Boolean> 刷新所有商品 = IPagerFactory.create(
            IDLE_FISH_POSTED_PATH,
            "刷新所有商品",
            PostedPageProcess.createRefreshProductPageProcess()
    );

    @NotNull DefaultPager<Boolean> 选择删除指定商品del(@NotNull String delName) {
        return IPagerFactory.create(
                IDLE_FISH_POSTED_PATH,
                "选择删除指定商品",
                PostedPageProcess.createDeleteProductPageProcess(delName));
    }

    @NotNull DefaultPager<Boolean> 进入更多列表 = IPagerFactory.create(
            IDLE_FISH_POSTED_PATH,
            "进入更多列表",
            new PostedDeleteSelectPageProcess()
    );

    @NotNull DefaultPager<Boolean> 确定删除商品 = IPagerFactory.create(
            IDLE_FISH_POSTED_PATH,
            "确定删除商品",
            new PostedDeleteOkPageProcess()
    );

    @NotNull DefaultPager<Boolean> 调用发布闲置键盘输入价格price(@NotNull String price) {
        return IPagerFactory.create(
                IDLE_FISH_POSTED_PRODUCT_INFO,
                "调用键盘输入价格",
                PostProductNumKeyProcess.create(price));
    }

}
