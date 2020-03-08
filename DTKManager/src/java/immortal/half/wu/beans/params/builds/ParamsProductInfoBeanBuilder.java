package immortal.half.wu.beans.params.builds;

import org.jetbrains.annotations.NotNull;
import immortal.half.wu.beans.params.ParamsProductInfoBean;

public class ParamsProductInfoBeanBuilder {
    private String id;
    private String goodsId;

    @NotNull
    public ParamsProductInfoBeanBuilder setId(String id) {
        this.id = id;
        return this;
    }

    @NotNull
    public ParamsProductInfoBeanBuilder setGoodsId(String goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    @NotNull
    public ParamsProductInfoBean createParamsProductInfoBean() {
        return new ParamsProductInfoBean(id, goodsId);
    }
}