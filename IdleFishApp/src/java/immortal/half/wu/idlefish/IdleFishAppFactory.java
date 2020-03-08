package immortal.half.wu.idlefish;

import com.sun.istack.internal.NotNull;
import immortal.half.wu.executor.interfaces.ITimeOutExecutorService;
import immotal.half.wu.appManager.beans.IdleFishProductBean;
import immotal.half.wu.appManager.interfaces.IApp;
import immotal.half.wu.appManager.pagers.beans.UserInfoBean;

public class IdleFishAppFactory {

    @NotNull
    public static IApp<IdleFishProductBean, UserInfoBean> createFishIdleApp(
            @NotNull String deviceId,
            @NotNull ITimeOutExecutorService executorService) throws NullPointerException {

        return new FishIdleApp(deviceId, executorService);
    }

}
