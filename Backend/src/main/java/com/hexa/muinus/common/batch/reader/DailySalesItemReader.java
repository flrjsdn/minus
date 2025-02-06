package com.hexa.muinus.common.batch.reader;

import com.hexa.muinus.common.batch.exeption.BatchErrorCode;
import com.hexa.muinus.common.batch.exeption.BatchProcessingException;
import com.hexa.muinus.store.domain.transaction.DailySales;
import com.hexa.muinus.store.domain.transaction.DailySalesId;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class DailySalesItemReader extends JdbcCursorItemReader<DailySales> {

    private final static String query
            = "SELECT CURDATE() - INTERVAL 1 DAY AS sale_date, tt.store_no, tt.item_id, SUM(tt.quantity) AS total_quantity, SUM(tt.sub_total) AS total_amount "
            + "FROM ((  SELECT  si.store_no, si.item_id, IFNULL(td.quantity, 0) AS quantity , IFNULL(td.sub_total, 0) AS sub_total "
            + "         FROM hexa.transaction_details td "
            + "         JOIN hexa.transactions t ON td.transaction_id = t.transaction_id "
            + "         AND t.created_at >= CURDATE() - INTERVAL 1 DAY "
            + "         AND t.created_at < CURDATE() "
            + "         RIGHT JOIN hexa.store_item si ON td.store_item_id = si.store_item_id "
            + "         JOIN hexa.item i ON si.item_id = i.item_id "
            + "     ) UNION "
            + "     (   SELECT  si.store_no, si.item_id, IFNULL(td.quantity, 0) AS quantity , IFNULL(td.sub_total, 0) AS sub_total "
            + "         FROM hexa.guest_transaction_details td "
            + "         JOIN hexa.guest_transactions t ON td.transaction_id = t.transaction_id "
            + "         AND t.created_at >= CURDATE() - INTERVAL 1 DAY "
            + "         AND t.created_at < CURDATE() "
            + "         RIGHT JOIN hexa.store_item si ON td.store_item_id = si.store_item_id "
            + "         JOIN hexa.item i ON si.item_id = i.item_id "
            + "         )) tt"
            + "         GROUP BY tt.store_no, tt.item_id "
            + "         ORDER BY tt.store_no, tt.item_id "
            ;


    public DailySalesItemReader(DataSource dataSource) {
        setDataSource(dataSource);
        setSql(query);

        try {
            setRowMapper(new RowMapper<DailySales>() {
                @Override
                public DailySales mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        LocalDate saleDate = (rs.getDate("sale_date") != null)
                                ? rs.getDate("sale_date").toLocalDate()
                                : LocalDate.now().minusDays(1); // 기본값: 어제 날짜

                        int storeNo = rs.getInt("store_no");
                        int itemId = rs.getInt("item_id");

                        // NULL 값 방지
                        int totalQuantity = rs.getObject("total_quantity", Integer.class) != null ? rs.getInt("total_quantity") : 0;
                        int totalAmount = rs.getObject("total_amount", Integer.class) != null ? rs.getInt("total_amount") : 0;

                        DailySalesId id = new DailySalesId(saleDate, storeNo, itemId);

                        return DailySales.builder()
                                .id(id)
                                .totalQuantity(totalQuantity)
                                .totalAmount(totalAmount)
                                .build();

                    } catch (Exception e) {
                        throw new BatchProcessingException(BatchErrorCode.ROW_MAPPER_ERROR, e);
                    }
                }
            });
        } catch (Exception e) {
            throw new BatchProcessingException(BatchErrorCode.SQL_EXECUTION_ERROR, e);
        }
    }
}
