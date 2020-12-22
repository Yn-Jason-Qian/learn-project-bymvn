package net.stealthcat.test.canal;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;

import java.net.InetSocketAddress;
import java.util.List;

public class CanalTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("localhost", 11111), "example", "", "");
        connector.connect();
        connector.subscribe(".*\\..*");
        connector.rollback();
        Message message = connector.getWithoutAck(100); // 获取指定数量的数据
        List<CanalEntry.Entry> entries = message.getEntries();

        for (CanalEntry.Entry entry : entries) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
            System.out.println(entry.getHeader().getSchemaName() + "." + entry.getHeader().getTableName());
            System.out.println(entry.getHeader().getEventType());

            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            for (CanalEntry.RowData rowData : rowDatasList) {
                List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
                for (CanalEntry.Column column : afterColumnsList) {
                    System.out.println(column.getName() + ":" + column.getValue() + "    update=" + column.getUpdated() + "  type=" + column.getMysqlType());
                }
            }

            System.out.println("-------------");
        }
        System.out.println(entries.size());
    }

}
