package wu.ui.controllers;

import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import com.sun.istack.internal.NotNull;
import immortal.half.wu.LogUtil;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import wu.ui.adapters.BaseListViewAdapter;
import wu.ui.events.ProductItemClickEvent;
import wu.ui.events.ProductItemDelClickEvent;
import wu.ui.events.ProductItemEditClickEvent;
import wu.ui.models.MainModel;
import wu.ui.models.beans.UIDevAppProductBindBean;
import wu.ui.models.interfaces.MainModelListener;
import wu.ui.utils.BeanUtil;
import wu.ui.utils.MEventBus;
import wu.ui.utils.ThreadUtil;
import wu.ui.weights.DeviceListItemView;
import wu.ui.weights.ProductListItemView;
import wu.ui.weights.beans.DeviceItemViewBean;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;

@ViewController(value = "/fxml/MainFxml.fxml", title = "我的demo")
public final class MainController implements MainModelListener {

    private static final String TAG = "MainController";

    @FXMLViewFlowContext
    private ViewFlowContext context;
    @FXML
    private JFXRippler optionsRippler;
    @FXML
    private StackPane optionsBurger;
    @FXML
    private ListView<DeviceListItemView> deviceListView;
    @FXML
    private JFXButton sendProductBt;
    @FXML
    private JFXButton searchDeviceBt;
    @FXML
    private ListView<ProductListItemView> productListView;
    @FXML
    private BorderPane borderPane;

    private MainModel mainModel;
    private LinkedHashMap<String, DeviceListItemView> deviceIdItemViewMap = new LinkedHashMap<>();
    private LinkedHashMap<DeviceListItemView, ObservableList<ProductListItemView>> deviceProductMap = new LinkedHashMap<>();
    private DeviceListItemView choiceDeviceItemView;

    @PostConstruct
    public void init() throws Exception {

        MEventBus.register(this);
        mainModel = new MainModel(this);

        setBtIco(sendProductBt, "icomoon.svg.paper-plane-o, send-o");
        setBtIco(searchDeviceBt, "icomoon.svg.search2");

        deviceListView.getStyleClass().add("device-list");
        deviceListView.setPrefWidth(185);
        deviceListView.setCellFactory(new BaseListViewAdapter<>());
        deviceListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DeviceListItemView>() {
            @Override
            public void changed(ObservableValue<? extends DeviceListItemView> observable, DeviceListItemView oldValue, DeviceListItemView newValue) {
                ObservableList<ProductListItemView> newProducts = deviceProductMap.get(newValue);
                LogUtil.i(TAG, "获得商品列表：" + newProducts);
                if (newProducts != null) {
                    productListView.setItems(newProducts);
                }
            }
        });

        productListView.setStyle("-fx-background-color: #eeeeee");
        productListView.setCellFactory(new BaseListViewAdapter<>());

    }

    private void setBtIco(JFXButton bt, String icoName) throws Exception {
        SVGGlyph icoMoonGlyph = SVGGlyphLoader.getIcoMoonGlyph(icoName);
        icoMoonGlyph.setFill(Color.WHITE);
        icoMoonGlyph.setSize(20, 20);
        bt.setGraphic(icoMoonGlyph);
    }

    @Subscribe
    public void productClick(ProductItemClickEvent deviceListItemClickEvent) {
        System.out.println("点击商品：" + deviceListItemClickEvent.getProductItemViewBean());
//        todo 点击商品
    }

    @Subscribe
    public void productDelClick(ProductItemDelClickEvent delClickEvent) {
        System.out.println("点击删除商品：" + delClickEvent.getProductItemViewBean());
//        todo 点击删除商品
    }

    @Subscribe
    public void productEditClick(ProductItemEditClickEvent editClickEvent) {
        System.out.println("点击编辑商品：" + editClickEvent.getProductItemViewBean());
//        todo 点击编辑商品
    }

    @Override
    public void loadUserConfigOver(List<UIDevAppProductBindBean> devAppProductBindBeans) {
//        初始化设备列表， 全部未登录
        refreshView(devAppProductBindBeans);
    }

    @Override
    public void deviceConnectLogout(List<UIDevAppProductBindBean> devAppProductBindBeans, UIDevAppProductBindBean bean) {
//        有设备连接， 未登录闲鱼用户
        refreshView(devAppProductBindBeans);
    }

    @Override
    public void deviceConnectLogin(List<UIDevAppProductBindBean> devAppProductBindBeans, UIDevAppProductBindBean bean) {
//        有设备连接， 并登录了闲鱼用户
        refreshView(devAppProductBindBeans);
    }

    @Override
    public void deviceDisconnectLogin(List<UIDevAppProductBindBean> devAppProductBindBeans, UIDevAppProductBindBean bean) {
//        从连接登录状态  转为  未连接状态
        refreshView(devAppProductBindBeans);
    }

    @Override
    public void deviceDisconnectLogout(List<UIDevAppProductBindBean> devAppProductBindBeans, UIDevAppProductBindBean bean) {
//        设备未登录状态下断开连接， 移除设备UI即可
        DeviceListItemView removeDeviceListItemView = deviceIdItemViewMap.remove(bean.getDeviceId());
        if (removeDeviceListItemView == null) {
            return;
        }
        deviceProductMap.remove(removeDeviceListItemView);
        deviceListView.getItems().remove(removeDeviceListItemView);
    }

    private void refreshView(@NotNull List<UIDevAppProductBindBean> devAppProductBindBeans) {

        deviceProductMap.clear();
        deviceIdItemViewMap.clear();

        List<String> disconnectUser = mainModel.getAllUserName();

        devAppProductBindBeans.forEach(bean -> {
            boolean logout = bean.getUserName().isLogout();
            // 连接 未 登录
            if (bean.getDevice().isConnect() && logout) {
                addNewDeviceItemView(
                    new DeviceListItemView(DeviceItemViewBean.createConnectUnLogoutBean(bean, bean.getDeviceId())),
                    bean
                );
                return;
            }

            String name = bean.getUserName().getName();
            disconnectUser.remove(name);

            // 未 连接 已 登录
            if (!bean.getDevice().isConnect() && !logout) {
                addNewDeviceItemView(
                        new DeviceListItemView(DeviceItemViewBean.createDisconnectLoginBean(bean, bean.getUserName().getName())),
                        bean
                );
                return;
            }

            // 连接 已 登录
            if (bean.getDevice().isConnect() && !logout) {
                addNewDeviceItemView(
                        new DeviceListItemView(DeviceItemViewBean.createConnectLoginBean(
                                bean,
                                bean.getUserName().getName(),
                                bean.getDeviceId())),
                        bean
                );
            }
        });

        ThreadUtil.runInMain(() -> deviceListView.setItems(FXCollections.observableArrayList(deviceProductMap.keySet())));

        ThreadUtil.runInMain(() -> deviceListView.refresh(), 50);
    }

    private void addNewDeviceItemView(
        DeviceListItemView newDeviceItemView,
        UIDevAppProductBindBean bean
        ) {

        deviceProductMap.put(newDeviceItemView, FXCollections.observableArrayList(
            BeanUtil.productBeanConvertToProductItemView(bean.getProducts())
        ));
        deviceIdItemViewMap.put(bean.getDeviceId(), newDeviceItemView);
    }

}
