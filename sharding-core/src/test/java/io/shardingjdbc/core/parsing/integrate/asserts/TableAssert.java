/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingjdbc.core.parsing.integrate.asserts;

import com.google.common.base.Optional;
import io.shardingjdbc.core.parsing.integrate.jaxb.table.ExpectedTable;
import io.shardingjdbc.core.parsing.parser.context.table.Table;
import io.shardingjdbc.core.parsing.parser.context.table.Tables;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Table assert.
 *
 * @author zhangliang
 */
@RequiredArgsConstructor
final class TableAssert {
    
    private final SQLStatementAssertMessage assertMessage;
    
    void assertTables(final Tables actual, final List<ExpectedTable> expected) {
        assertThat(assertMessage.getFullAssertMessage("Tables size assertion error: "), actual.getTableNames().size(), is(expected.size()));
        for (ExpectedTable each : expected) {
            Optional<Table> table;
            if (null != each.getAlias()) {
                table = actual.find(each.getAlias());
            } else {
                table = actual.find(each.getName());
            }
            assertTrue(assertMessage.getFullAssertMessage("Table should exist: "), table.isPresent());
            assertTable(table.get(), each);
        }
    }
    
    private void assertTable(final Table actual, final ExpectedTable expected) {
        assertThat(assertMessage.getFullAssertMessage("Table name assertion error: "), actual.getName(), is(expected.getName()));
        assertThat(assertMessage.getFullAssertMessage("Table alias assertion error: "), actual.getAlias().orNull(), is(expected.getAlias()));
    }
}
