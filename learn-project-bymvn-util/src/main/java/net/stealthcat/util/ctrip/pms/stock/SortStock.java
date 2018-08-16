package net.stealthcat.util.ctrip.pms.stock;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import net.stealthcat.util.FileReadUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author y_qiana
 * @date 2018/8/16
 */
public class SortStock {

    public static void main(String[] args) throws ParseException {
        writeDataToExcel("stock.json", 1000);
    }

    public static void writeDataToExcel (String file, long interval) throws ParseException {
        List<List<VbkChannelStockUsage>> usages = FileReadUtil.readAllLines(file).stream().map(s -> JSON.parseArray(s, VbkChannelStockUsage.class)).collect(Collectors.toList());
        usages.forEach(SortStock::writeDataToExcel);

        Date begin = DateUtils.parseDate("2018-08-19 10:00:00", "yyyy-MM-dd HH:mm:ss");
        Date end = DateUtils.parseDate("2018-08-26 10:00:00", "yyyy-MM-dd HH:mm:ss");
        usages.forEach(usages1 -> writeDataToExcel(getMaxProxy2(usages1, begin, end, interval), "sort1"));
        usages.forEach(usages1 -> writeDataToExcel(getMaxProxy3(usages1, begin, end, interval), "sort2"));
    }

    private static void writeDataToExcel (List<VbkChannelStockUsage> usages) {
        if (CollectionUtils.isEmpty(usages)) {
            return;
        }
        usages.sort(Comparator.comparing(VbkChannelStockUsage::getBeginTime).thenComparing(VbkChannelStockUsage::getEndTime));
        String fileNamePrefix = "";

        // 创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建工作表
        HSSFSheet sheet = workbook.createSheet("sheet1");
        HSSFRow title = sheet.createRow(0);
        title.createCell(0).setCellValue("id");
        title.createCell(1).setCellValue("carId");
        title.createCell(2).setCellValue("begin");
        title.createCell(3).setCellValue("end");
        title.createCell(4).setCellValue("usageType");
        title.createCell(5).setCellValue("containsCurrent");
        title.createCell(6).setCellValue("lastMinutes");
        title.createCell(7).setCellValue("durationHours");

        for (int row = 1; row <= usages.size(); row++) {
            HSSFRow rows = sheet.createRow(row);
            VbkChannelStockUsage usage = usages.get(row - 1);
            fileNamePrefix = usage.getStoreID() + "_" + usage.getVehicleID();
            long interval = usage.getEndTime().getTime() - usage.getBeginTime().getTime();
            long intervalMinute = interval / (60 * 1000);
            long intervalHour = interval / (60 * 60 *1000);

            rows.createCell(0).setCellValue(usage.getID());
            rows.createCell(1).setCellValue(usage.getCarID());
            rows.createCell(2).setCellValue(DateFormatUtils.format(usage.getBeginTime(), "yyyy/MM/dd HH:mm:ss"));
            rows.createCell(3).setCellValue(DateFormatUtils.format(usage.getEndTime(), "yyyy/MM/dd HH:mm:ss"));
            rows.createCell(4).setCellValue(usage.getUsageType());
            rows.createCell(5).setCellValue(usage.getBeginTime().getTime() <= System.currentTimeMillis() ? "是" : "否");
            rows.createCell(6).setCellValue(intervalMinute);
            rows.createCell(7).setCellValue(intervalHour);
        }

        File xlsFile = new File(fileNamePrefix + ".xls");
        try (FileOutputStream xlsStream = new FileOutputStream(xlsFile)){
            workbook.write(xlsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeDataToExcel (Map<Long, List<UsedStock>> usages, String fileName) {
        if (CollectionUtils.isEmpty(usages)) {
            return;
        }

        // 创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet1");
        // 创建工作表
        HSSFRow title = sheet.createRow(0);
        title.createCell(0).setCellValue("id");
        title.createCell(1).setCellValue("carId");
        title.createCell(2).setCellValue("begin");
        title.createCell(3).setCellValue("end");
        title.createCell(4).setCellValue("usageType");
        title.createCell(5).setCellValue("containsCurrent");

        int rowNum = 1;
        for (List<UsedStock> used : usages.values()) {
            Collections.sort(used);
            for (int i = 0; i < used.size(); i++) {
                HSSFRow row = sheet.createRow(rowNum);
                rowNum++;

                row.createCell(0).setCellValue(used.get(i).id);
                row.createCell(1).setCellValue(used.get(i).carId);
                row.createCell(2).setCellValue(DateFormatUtils.format(used.get(i).begin, "yyyy/MM/dd HH:mm:ss"));
                row.createCell(3).setCellValue(DateFormatUtils.format(used.get(i).end, "yyyy/MM/dd HH:mm:ss"));
                row.createCell(4).setCellValue(used.get(i).useType);
                row.createCell(5).setCellValue(used.get(i).begin.getTime() <= System.currentTimeMillis() ? "是" : "否");
            }
            sheet.createRow(rowNum++);
        }

        File xlsFile = new File(fileName + ".xls");
        try (FileOutputStream xlsStream = new FileOutputStream(xlsFile)){
            workbook.write(xlsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static Map<Long, List<UsedStock>> getMaxProxy2(List<VbkChannelStockUsage> stockUsages, Date begin, Date end, long interval) {
        List<UsedStock> usedStocks = Lists.newArrayList();
        for (VbkChannelStockUsage usage : stockUsages) {
            usedStocks.add(new UsedStock(usage.getBeginTime(), usage.getEndTime(), usage.getID(), usage.getCarID(), usage.getUsageType()));
        }
        Map<Long, List<UsedStock>> result = getMaxStock5(usedStocks, interval);
        UsedStock usedStock = new UsedStock(begin, end, 0, 0, 0);
        System.out.println("getMaxProxy2 : " + (int) result.values().stream().filter(s -> s.stream().anyMatch(a -> isConflict(a, usedStock, interval))).count());
        return result;
    }

    private static Map<Long, List<UsedStock>> getMaxProxy3(List<VbkChannelStockUsage> stockUsages, Date begin, Date end, long interval) {
        List<UsedStock> usedStocks = Lists.newArrayList();
        for (VbkChannelStockUsage usage : stockUsages) {
            usedStocks.add(new UsedStock(usage.getBeginTime(), usage.getEndTime(), usage.getID(), usage.getCarID(), usage.getUsageType()));
        }
        Map<Long, List<UsedStock>> result = getMaxStock6(usedStocks, interval);
        UsedStock usedStock = new UsedStock(begin, end, 0, 0, 0);
        System.out.println("getMaxProxy2 : " + (int) result.values().stream().filter(s -> s.stream().anyMatch(a -> isConflict(a, usedStock, interval))).count());
        return result;
    }

    private static Map<Long, List<UsedStock>> getMaxStock5(List<UsedStock> stocks, long interval) {
        if (CollectionUtils.isEmpty(stocks)) {
            return Collections.emptyMap();
        }

        Map<Long, List<UsedStock>> sortedStocks = new HashMap<>();
        List<UsedStock> sortedList = new ArrayList<>();
        List<UsedStock> commonOrders = new ArrayList<>();

        for (UsedStock u : stocks) {
            if (isOccupy(u.useType)) {
                sortedStocks.computeIfAbsent(u.carId, v -> new ArrayList<>()).add(u);
                sortedList.add(u);
                u.merged = true;
            } else {
                commonOrders.add(u);
            }
        }

        // 判断当前时间订单是否不可移动
        Iterator<UsedStock> iterator = commonOrders.iterator();
        while (iterator.hasNext()) {
            UsedStock commonOrder = iterator.next();
            if (isOccupy(commonOrder)) {
                List<UsedStock> sortedStocksOfCar = sortedStocks.get(commonOrder.carId);
                if (sortedStocksOfCar == null) {
                    commonOrder.merged = true;
                    sortedStocks.put(commonOrder.carId, Lists.newArrayList(commonOrder));
                    iterator.remove();
                } else if (!isConflict(sortedStocksOfCar, commonOrder, interval)){
                    commonOrder.merged = true;
                    sortedStocksOfCar.add(commonOrder);
                    iterator.remove();
                }
            }
        }

        // 如果有不可移动订单，记录普通订单的冲突数
        if (!sortedStocks.isEmpty()) {
            for (UsedStock u1 : commonOrders) {
                for (UsedStock u2 : sortedList) {
                    if (isConflict(u1, u2, interval)) {
                        u1.conflictCount++;
                    }
                }
            }
        }

        // 普通订单按照冲突数，所有订单冲突数，起始时间，结束时间排序
        commonOrders.sort((o1, o2) -> {
            int i = o2.conflictCount - o1.conflictCount;
            if (i == 0) {
                return o1.compareTo(o2);
            } else {
                return i;
            }
        });

        // 优先排冲突数最大，起始时间最前，结束最早的订单
        long time = System.currentTimeMillis();
        int i = 0;
        for (UsedStock u1 : commonOrders) {
            if (u1.merged) {
                continue;
            }

            // 优先排到时间间隔最小的分组
            long minInterval = -1;
            List<UsedStock> suitable = null;
            for (List<UsedStock> us : sortedStocks.values()) {
                if (!isConflict(us, u1, interval)) {
                    long thisMin = getMinInterval(us, u1, interval);
                    if (thisMin < 0) {
                        continue;
                    }
                    if (minInterval == -1 || thisMin < minInterval) {
                        minInterval = thisMin;
                        suitable = us;
                    }
                }
            }

            sortedList.add(u1);
            u1.merged = true;
            if (suitable != null) {
                suitable.add(u1);
            } else {
                List<UsedStock> newSeq = Lists.newArrayList(u1);
                sortedStocks.put(time + i++, newSeq);
            }
        }

        return sortedStocks;
    }

    private static Map<Long, List<UsedStock>> getMaxStock6(List<UsedStock> stocks, long interval) {
        if (CollectionUtils.isEmpty(stocks)) {
            return Collections.emptyMap();
        }

        Map<Long, List<UsedStock>> sortedStocks = new HashMap<>();
        List<UsedStock> sortedList = new ArrayList<>();
        List<UsedStock> commonOrders = new ArrayList<>();

        for (UsedStock u : stocks) {
            if (isOccupy(u.useType)) {
                sortedStocks.computeIfAbsent(u.carId, v -> new ArrayList<>()).add(u);
                sortedList.add(u);
                u.merged = true;
            } else {
                commonOrders.add(u);
            }
        }

        // 判断当前时间订单是否不可移动
        Iterator<UsedStock> iterator = commonOrders.iterator();
        while (iterator.hasNext()) {
            UsedStock commonOrder = iterator.next();
            if (isOccupy(commonOrder)) {
                List<UsedStock> sortedStocksOfCar = sortedStocks.get(commonOrder.carId);
                if (sortedStocksOfCar == null) {
                    commonOrder.merged = true;
                    sortedStocks.put(commonOrder.carId, Lists.newArrayList(commonOrder));
                    iterator.remove();
                } else if (!isConflict(sortedStocksOfCar, commonOrder, interval)){
                    commonOrder.merged = true;
                    sortedStocksOfCar.add(commonOrder);
                    iterator.remove();
                }
            }
        }

        // 如果有不可移动订单，记录普通订单的冲突数
//        if (!sortedStocks.isEmpty()) {
//            for (UsedStock u1 : commonOrders) {
//                for (UsedStock u2 : sortedList) {
//                    if (isConflict(u1, u2, interval)) {
//                        u1.conflictCount++;
//                    }
//                }
//            }
//        }

        // 普通订单按照冲突数，所有订单冲突数，起始时间，结束时间排序
        Collections.sort(commonOrders);

        // 优先排冲突数最大，起始时间最前，结束最早的订单
        long time = System.currentTimeMillis();
        int i = 0;
        for (UsedStock u1 : commonOrders) {
            if (u1.merged) {
                continue;
            }

            // 优先排到时间间隔最小的分组
            long minInterval = -1;
            List<UsedStock> suitable = null;
            for (List<UsedStock> us : sortedStocks.values()) {
                if (!isConflict(us, u1, interval)) {
                    long thisMin = getMinInterval(us, u1, interval);
                    if (thisMin < 0) {
                        continue;
                    }
                    if (minInterval == -1 || thisMin < minInterval) {
                        minInterval = thisMin;
                        suitable = us;
                    }
                }
            }

            sortedList.add(u1);
            u1.merged = true;
            if (suitable != null) {
                suitable.add(u1);
            } else {
                List<UsedStock> newSeq = Lists.newArrayList(u1);
                sortedStocks.put(time + i++, newSeq);
            }
        }

        return sortedStocks;
    }

    private static long getMinInterval (List<UsedStock> stocks, UsedStock u, long interval) {
        long min = -1;
        for (UsedStock stock : stocks) {
            if (isConflict(stock, u, interval)) {
                continue;
            }
            long thisMin = getMinInterval(stock, u, interval);
            if (thisMin < 0) {
                continue;
            }
            if (min == -1 || thisMin < min) {
                min = thisMin;
            }
        }
        return min;
    }

    private static long getMinInterval (UsedStock u1, UsedStock u2, long interval) {
        if (u1.compareTo(u2) < 0) {
            return u2.begin.getTime() - u1.end.getTime() - interval;
        } else {
            return u1.begin.getTime() - u2.end.getTime() - interval;
        }
    }

    private static boolean isConflict (List<UsedStock> usedStocks, UsedStock u, long interval) {
        for (UsedStock u1 : usedStocks) {
            if (isConflict(u1, u, interval)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isConflict (UsedStock u1, UsedStock u2, long interval) {
        int i = u1.compareTo(u2);
        if (i == 0) {
            return true;
        } else if (i < 0) {
            return u1.end.after(new Date(u2.begin.getTime() - interval));
        } else {
            return u2.end.after(new Date(u1.begin.getTime() - interval));
        }
    }

    private static boolean isOccupy(int useType) {
        return useType == UsageType.Maintain.getValue() ||
                useType == UsageType.Repair.getValue() ||
                useType == UsageType.InsuranceAccident.getValue() ||
                useType == UsageType.OfflineCarTemp.getValue() ||
                useType == UsageType.SystemOfflineCar.getValue();
    }

    private static boolean isOccupy (UsedStock used) {
        return (used.begin.getTime() <= System.currentTimeMillis() && used.end.getTime() >= System.currentTimeMillis() && used.carId > 0)
                || isOccupy(used.useType);
    }

    private static class UsedStock implements Comparable<UsedStock>{
        Date begin;
        Date end;
        long id;
        long carId;
        int useType;

        boolean merged;
        int conflictCount;
        int allConflictCount;

        UsedStock(Date begin, Date end, long id, long carId, int useType) {
            this.begin = begin;
            this.end = end;
            this.id = id;
            this.carId = carId;
            this.useType = useType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UsedStock usedStock = (UsedStock) o;

            if (id != usedStock.id) return false;
            if (carId != usedStock.carId) return false;
            if (useType != usedStock.useType) return false;
            if (begin != null ? !begin.equals(usedStock.begin) : usedStock.begin != null) return false;
            return end != null ? end.equals(usedStock.end) : usedStock.end == null;
        }

        @Override
        public int hashCode() {
            int result = begin != null ? begin.hashCode() : 0;
            result = 31 * result + (end != null ? end.hashCode() : 0);
            result = 31 * result + (int) (id ^ (id >>> 32));
            result = 31 * result + (int) (carId ^ (carId >>> 32));
            result = 31 * result + useType;
            return result;
        }

        @Override
        public int compareTo(UsedStock o) {
            if (this.begin.before(o.begin)) {
                return -1;
            } else if (this.begin.after(o.begin)) {
                return 1;
            } else {
                if (this.end.before(o.end)) {
                    return -1;
                } else if (this.end.after(o.end)){
                    return 1;
                } else {
                    return 0;
                }
            }
        }

        @Override
        public String toString() {
            return String.valueOf(id);
        }
    }

}
