package net.stealthcat.util.ctrip.pms.stock;

public enum UsageType {

    /// 默认
    Default(0),

    /// 订单
    Order(1),

    /// 保养
    Maintain(2),

    /// 维修
    Repair(3),

    /// 同供应商调度车辆
    Dispatch(4),

    /// 跨供应商调度车辆
    AcrossDispatch(5),

    /// 保险事故 
    InsuranceAccident(6),

    /// 临时下线车辆
    OfflineCarTemp(110),

    /// 系统下线
    SystemOfflineCar(120);

    private int value;

    UsageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
