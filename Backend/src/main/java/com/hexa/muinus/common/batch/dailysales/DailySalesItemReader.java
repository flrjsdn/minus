package com.hexa.muinus.common.batch.dailysales;

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

        // RowMapper 설정
        setRowMapper(new RowMapper<DailySales>() {
            @Override
            public DailySales mapRow(ResultSet rs, int rowNum) throws SQLException {
                LocalDate saleDate = rs.getDate("sale_date").toLocalDate();
                int storeNo = rs.getInt("store_no");
                int itemId = rs.getInt("item_id");

                DailySalesId id = new DailySalesId(saleDate, storeNo, itemId);

                return DailySales.builder()
                        .id(id)
                        .totalQuantity(rs.getInt("total_quantity"))
                        .totalAmount(rs.getInt("total_amount"))
                        .build();
            }
        });
    }
}
